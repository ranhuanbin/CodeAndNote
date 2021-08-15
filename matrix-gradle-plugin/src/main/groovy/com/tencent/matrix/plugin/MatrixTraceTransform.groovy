package com.tencent.matrix.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.build.gradle.internal.pipeline.TransformTask
import com.android.build.gradle.internal.scope.GlobalScope
import com.android.build.gradle.internal.scope.VariantScope
import com.android.build.gradle.internal.variant.BaseVariantData
import com.android.utils.FileUtils
import com.google.common.base.Charsets
import com.google.common.base.Joiner
import com.google.common.hash.Hashing
import com.tencent.matrix.javalib.util.IOUtil
import com.tencent.matrix.javalib.util.Log
import com.tencent.matrix.javalib.util.ReflectUtil
import com.tencent.matrix.javalib.util.Util
import com.tencent.matrix.trace.Configuration
import com.tencent.matrix.trace.MethodCollector
import com.tencent.matrix.trace.MethodTracer
import com.tencent.matrix.trace.TraceBuildConstants
import com.tencent.matrix.trace.extension.MatrixTraceExtension
import com.tencent.matrix.trace.item.TraceMethod
import com.tencent.matrix.trace.retrace.MappingCollector
import com.tencent.matrix.trace.retrace.MappingReader
import org.gradle.api.Project
import org.gradle.api.Task

import java.lang.reflect.Field
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

import static com.android.builder.model.AndroidProject.FD_OUTPUTS

/**
 * 该类hook了系统构建Dex的Transform并配合ASM框架，插入方法执行时间记录的字节码
 * todo:
 * 1. 编译时混淆对应哪个task, 在什么时间执行
 */
class MatrixTraceTransform extends Transform {

    private static final String TAG = "MatrixTraceTransform"
    private Configuration config
    private Transform origTransform
    // 创建线程大小为16的线程池
    private ExecutorService executor = Executors.newFixedThreadPool(16);

    static void inject(Project project, MatrixTraceExtension extension, VariantScope variantScope) {

        GlobalScope globalScope = variantScope.getGlobalScope()
        BaseVariantData variant = variantScope.getVariantData()
        /**
         * mapping文件存储目录 如： app\build\outputs\mapping\debug
         * 在该目录下Trace会生成两个mapping文件 一个是methodMapping.txt，一个是ignoreMethodMapping.txt
         */
        String mappingOut = Joiner.on(File.separatorChar).join(
                String.valueOf(globalScope.getBuildDir()),
                FD_OUTPUTS,
                "mapping",
                variantScope.getVariantConfiguration().getDirName())
        /**
         * 插桩后的class存储目录 如：app\build\outputs\mapping\debug\traceClass
         */
        String traceClassOut = Joiner.on(File.separatorChar).join(
                String.valueOf(globalScope.getBuildDir()),
                FD_OUTPUTS,
                "traceClassOut",
                variantScope.getVariantConfiguration().getDirName())
        println("[MatrixTraceTransform] [inject] [mappintOut: $mappingOut, traceClassOut: $traceClassOut]")
        /**
         * 收集配置信息
         */
        Configuration config = new Configuration.Builder()
                .setPackageName(variant.getApplicationId())//包名
                .setBaseMethodMap(extension.getBaseMethodMapFile())// build.gradle中配置的baseMethodMapFile, 保存的是我们指定要插桩的方法
                .setBlackListFile(extension.getBlackListFile())// build.gradle中配置的blackListFile, 保存的是不需要插桩的文件
                .setMethodMapFilePath(mappingOut + "/methodMapping.txt")// 记录插桩的methodId和method的关系
                .setIgnoreMethodMapFilePath(mappingOut + "/ignoreMethodMapping.txt")// 记录没有被插桩的方法
                .setMappingPath(mappingOut)// mapping文件存储目录
                .setTraceClassOut(traceClassOut)// 插桩后的class存储目录
                .build()
        try {
            /**
             * 获取 TransformTask.. 具体名称 如：transformClassesWithDexBuilderForDebug 和 transformClassesWithDexForDebug
             */
            String[] hardTask = getTransformTaskName(extension.getCustomDexTransformName(), variant.getName());
            for (Task task : project.getTasks()) {
                for (String str : hardTask) {
                    // 找到task并进行hook
                    if (task.getName().equalsIgnoreCase(str) && task instanceof TransformTask) {
                        TransformTask transformTask = (TransformTask) task
                        Log.i(TAG, "successfully inject task:" + transformTask.getName())
                        Field field = TransformTask.class.getDeclaredField("transform")
                        field.setAccessible(true)
                        // 将指定的task转换为MatrixTraceTransform
                        // todo {RHB} 暂时有一个疑问, 直接用MatrixTraceTransform进行插桩不可以吗? 为什么要进行hook?
                        field.set(task, new MatrixTraceTransform(config, transformTask.getTransform()))
                        break
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


    private static String[] getTransformTaskName(String customDexTransformName, String buildTypeSuffix) {
        if (!Util.isNullOrNil(customDexTransformName)) {
            return [customDexTransformName + "For" + buildTypeSuffix]
        } else {
            String[] names = ["transformClassesWithDexBuilderFor" + buildTypeSuffix, "transformClassesWithDexFor" + buildTypeSuffix]
            return names
        }
    }

    MatrixTraceTransform(Configuration config, Transform origTransform) {
        this.config = config;
        this.origTransform = origTransform;
    }

    @Override
    String getName() {
        return TAG;
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    boolean isIncremental() {
        return true
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        long start = System.currentTimeMillis()
        try {
            doTransform(transformInvocation) // hack
        } catch (ExecutionException e) {
            e.printStackTrace()
        }
        long cost = System.currentTimeMillis() - start
        long begin = System.currentTimeMillis()
        /**
         * 执行原origTransform的transform方法, 包括virtualApk的hook, 都是构造函数里面持有原对象的引用,
         * 在特定场景触发
         */
        origTransform.transform(transformInvocation)
        long origTransformCost = System.currentTimeMillis() - begin
        Log.i("Matrix." + getName(), "[transform] cost time: %dms %s:%sms MatrixTraceTransform:%sms", System.currentTimeMillis() - start, origTransform.getClass().getSimpleName(), origTransformCost, cost);
    }

    private void doTransform(TransformInvocation transformInvocation) throws ExecutionException, InterruptedException {
        final boolean isIncremental = transformInvocation.isIncremental() && this.isIncremental()
        /**
         * step 1
         */
        long start = System.currentTimeMillis()

        List<Future> futures = new LinkedList<>()
        // 存储方法混淆前后的映射关系
        final MappingCollector mappingCollector = new MappingCollector()
        // methodId计数器
        final AtomicInteger methodId = new AtomicInteger(0)
        // 存储需要插桩的方法名和方法封装的方法对象
        final ConcurrentHashMap<String, TraceMethod> collectedMethodMap = new ConcurrentHashMap<>()
        // 将ParseMappingTask放入线程池
        futures.add(executor.submit(new ParseMappingTask(mappingCollector, collectedMethodMap, methodId)))
        // 存储原始jar文件和输出jar文件的对应关系
        Map<File, File> dirInputOutMap = new ConcurrentHashMap<>()
        Map<File, File> jarInputOutMap = new ConcurrentHashMap<>()
        Collection<TransformInput> inputs = transformInvocation.getInputs()

        for (TransformInput input : inputs) {
            for (DirectoryInput directoryInput : input.getDirectoryInputs()) {
                futures.add(executor.submit(new CollectDirectoryInputTask(dirInputOutMap, directoryInput, isIncremental)))
            }
            for (JarInput inputJar : input.getJarInputs()) {
                futures.add(executor.submit(new CollectJarInputTask(inputJar, isIncremental, jarInputOutMap, dirInputOutMap)))
            }
        }
        for (Future future : futures) {
            // 等待所有线程运行完毕
            future.get()
        }
        futures.clear()
        Log.i(TAG, "[doTransform] Step(1)[Parse]... cost:%sms", System.currentTimeMillis() - start)
        /**
         * step 2
         * 1. 收集需要插桩和不需要插桩的方法, 并记录在mapping文件中
         * 2. 收集类之间的继承关系
         */
        start = System.currentTimeMillis()
        // 收集需要插桩的方法信息, 将该方法信息封装到TraceMethod中
        MethodCollector methodCollector = new MethodCollector(executor, mappingCollector, methodId, config, collectedMethodMap)
        methodCollector.collect(dirInputOutMap.keySet(), jarInputOutMap.keySet());
        Log.i(TAG, "[doTransform] Step(2)[Collection]... cost:%sms", System.currentTimeMillis() - start)

        /**
         * step 3 进行插桩
         */
        start = System.currentTimeMillis()
        // 执行插桩逻辑, 在需要进行插桩的方法的入口与结束处分别调用AppMethodBeat的i/o方法
        MethodTracer methodTracer = new MethodTracer(executor, mappingCollector, config, methodCollector.getCollectedMethodMap(), methodCollector.getCollectedClassExtendMap());
        methodTracer.trace(dirInputOutMap, jarInputOutMap);
        Log.i(TAG, "[doTransform] Step(3)[Trace]... cost:%sms", System.currentTimeMillis() - start);
    }

    // 解析app\build\outputs\mapping\debug\mapping.txt文件
    private class ParseMappingTask implements Runnable {
        // 存储混淆前和混淆后方法的映射关系
        final MappingCollector mappingCollector
        // String: 需要插桩的方法名, TraceMethod将方法相关信息进行封装
        final ConcurrentHashMap<String, TraceMethod> collectedMethodMap
        // 计数器
        private final AtomicInteger methodId

        ParseMappingTask(MappingCollector mappingCollector, ConcurrentHashMap<String, TraceMethod> collectedMethodMap, AtomicInteger methodId) {
            this.mappingCollector = mappingCollector;
            this.collectedMethodMap = collectedMethodMap;
            this.methodId = methodId;
        }

        @Override
        public void run() {
            try {
                long start = System.currentTimeMillis()
                // 获取mapping.txt文件(该文件记录了混淆前后的映射关系)
                File mappingFile = new File(config.mappingDir, "mapping.txt")
                if (mappingFile.exists() && mappingFile.isFile()) {
                    // 如果文件存在, 解析该文件, 并将解析结果保存到MappingCollector中
                    MappingReader mappingReader = new MappingReader(mappingFile)
                    mappingReader.read(mappingCollector)
                }
                // 解析黑名单文件并保存到Configuration.blackSet中
                int size = config.parseBlackFile(mappingCollector)
                //
                File baseMethodMapFile = new File(config.baseMethodMapPath)
                getMethodFromBaseMethod(baseMethodMapFile, collectedMethodMap)
                retraceMethodMap(mappingCollector, collectedMethodMap)

                Log.i(TAG, "[ParseMappingTask#run] cost:%sms, black size:%s, collect %s method from %s", System.currentTimeMillis() - start, size, collectedMethodMap.size(), config.baseMethodMapPath);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void retraceMethodMap(MappingCollector processor, ConcurrentHashMap<String, TraceMethod> methodMap) {
            if (null == processor || null == methodMap) {
                return;
            }
            HashMap<String, TraceMethod> retraceMethodMap = new HashMap<>(methodMap.size());
            for (Map.Entry<String, TraceMethod> entry : methodMap.entrySet()) {
                TraceMethod traceMethod = entry.getValue();
                traceMethod.proguard(processor);
                retraceMethodMap.put(traceMethod.getMethodName(), traceMethod);
            }
            methodMap.clear();
            methodMap.putAll(retraceMethodMap);
            retraceMethodMap.clear();
        }

        private void getMethodFromBaseMethod(File baseMethodFile, ConcurrentHashMap<String, TraceMethod> collectedMethodMap) {
            if (!baseMethodFile.exists()) {
                Log.w(TAG, "[getMethodFromBaseMethod] not exist!%s", baseMethodFile.getAbsolutePath());
                return;
            }
            Scanner fileReader = null;
            try {
                fileReader = new Scanner(baseMethodFile, "UTF-8");
                while (fileReader.hasNext()) {
                    String nextLine = fileReader.nextLine();
                    if (!Util.isNullOrNil(nextLine)) {
                        nextLine = nextLine.trim();
                        if (nextLine.startsWith("#")) {
                            Log.i("[getMethodFromBaseMethod] comment %s", nextLine);
                            continue;
                        }
                        String[] fields = nextLine.split(",");
                        TraceMethod traceMethod = new TraceMethod();
                        traceMethod.id = Integer.parseInt(fields[0]);
                        traceMethod.accessFlag = Integer.parseInt(fields[1]);
                        String[] methodField = fields[2].split(" ");
                        traceMethod.className = methodField[0].replace("/", ".");
                        traceMethod.methodName = methodField[1];
                        if (methodField.length > 2) {
                            traceMethod.desc = methodField[2].replace("/", ".");
                        }
                        collectedMethodMap.put(traceMethod.getMethodName(), traceMethod);
                        if (methodId.get() < traceMethod.id && traceMethod.id != TraceBuildConstants.METHOD_ID_DISPATCH) {
                            methodId.set(traceMethod.id);
                        }

                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "[getMethodFromBaseMethod] err!");
            } finally {
                if (fileReader != null) {
                    fileReader.close();
                }
            }
        }
    }


    private class CollectDirectoryInputTask implements Runnable {

        Map<File, File> dirInputOutMap;
        DirectoryInput directoryInput;
        boolean isIncremental;
        String traceClassOut;

        CollectDirectoryInputTask(Map<File, File> dirInputOutMap, DirectoryInput directoryInput, boolean isIncremental) {
            this.dirInputOutMap = dirInputOutMap;
            this.directoryInput = directoryInput;
            this.isIncremental = isIncremental;
            this.traceClassOut = config.traceClassOut;
        }

        @Override
        public void run() {
            try {
                handle();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Matrix." + getName(), "%s", e.toString());
            }
        }

        private void handle() throws IOException, IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
            final File dirInput = directoryInput.getFile();
            final File dirOutput = new File(traceClassOut, dirInput.getName());
            final String inputFullPath = dirInput.getAbsolutePath();
            final String outputFullPath = dirOutput.getAbsolutePath();

            if (!dirOutput.exists()) {
                dirOutput.mkdirs();
            }

            if (!dirInput.exists() && dirOutput.exists()) {
                if (dirOutput.isDirectory()) {
                    FileUtils.deleteIfExists(dirOutput);
                } else {
                    FileUtils.delete(dirOutput);
                }
            }

            if (isIncremental) {
                Map<File, Status> fileStatusMap = directoryInput.getChangedFiles();
                final Map<File, Status> outChangedFiles = new HashMap<>();

                for (Map.Entry<File, Status> entry : fileStatusMap.entrySet()) {
                    final Status status = entry.getValue();
                    final File changedFileInput = entry.getKey();

                    final String changedFileInputFullPath = changedFileInput.getAbsolutePath();
                    final File changedFileOutput = new File(changedFileInputFullPath.replace(inputFullPath, outputFullPath));

                    if (status == Status.ADDED || status == Status.CHANGED) {
                        dirInputOutMap.put(changedFileInput, changedFileOutput);
                    } else if (status == Status.REMOVED) {
                        changedFileOutput.delete();
                    }
                    outChangedFiles.put(changedFileOutput, status);
                }
                replaceChangedFile(directoryInput, outChangedFiles);

            } else {
                dirInputOutMap.put(dirInput, dirOutput);
            }
            replaceFile(directoryInput, dirOutput);
        }
    }

    private class CollectJarInputTask implements Runnable {
        JarInput inputJar;
        boolean isIncremental;
        Map<File, File> jarInputOutMap;
        Map<File, File> dirInputOutMap;

        CollectJarInputTask(JarInput inputJar, boolean isIncremental, Map<File, File> jarInputOutMap, Map<File, File> dirInputOutMap) {
            this.inputJar = inputJar;
            this.isIncremental = isIncremental;
            this.jarInputOutMap = jarInputOutMap;
            this.dirInputOutMap = dirInputOutMap;
        }

        @Override
        public void run() {
            try {
                handle();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Matrix." + getName(), "%s", e.toString());
            }
        }

        private void handle() throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException, IOException {
            String traceClassOut = config.traceClassOut;

            final File jarInput = inputJar.getFile();
            final File jarOutput = new File(traceClassOut, getUniqueJarName(jarInput));
            if (jarOutput.exists()) {
                jarOutput.delete();
            }
            if (!jarOutput.getParentFile().exists()) {
                jarOutput.getParentFile().mkdirs();
            }

            if (IOUtil.isRealZipOrJar(jarInput)) {
                if (isIncremental) {
                    if (inputJar.getStatus() == Status.ADDED || inputJar.getStatus() == Status.CHANGED) {
                        jarInputOutMap.put(jarInput, jarOutput);
                    } else if (inputJar.getStatus() == Status.REMOVED) {
                        jarOutput.delete();
                    }

                } else {
                    jarInputOutMap.put(jarInput, jarOutput);
                }

            } else {
                Log.i(TAG, "Special case for WeChat AutoDex. Its rootInput jar file is actually a txt file contains path list.");
                // Special case for WeChat AutoDex. Its rootInput jar file is actually
                // a txt file contains path list.
                BufferedReader br = null;
                BufferedWriter bw = null;
                try {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(jarInput)));
                    bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(jarOutput)));
                    String realJarInputFullPath;
                    while ((realJarInputFullPath = br.readLine()) != null) {
                        // src jar.
                        final File realJarInput = new File(realJarInputFullPath);
                        // dest jar, moved to extra guard intermediate output dir.
                        final File realJarOutput = new File(traceClassOut, getUniqueJarName(realJarInput));

                        if (realJarInput.exists() && IOUtil.isRealZipOrJar(realJarInput)) {
                            jarInputOutMap.put(realJarInput, realJarOutput);
                        } else {
                            realJarOutput.delete();
                            if (realJarInput.exists() && realJarInput.isDirectory()) {
                                File realJarOutputDir = new File(traceClassOut, realJarInput.getName());
                                if (!realJarOutput.exists()) {
                                    realJarOutput.mkdirs();
                                }
                                dirInputOutMap.put(realJarInput, realJarOutputDir);
                            }

                        }
                        // write real output full path to the fake jar at rootOutput.
                        final String realJarOutputFullPath = realJarOutput.getAbsolutePath();
                        bw.write(realJarOutputFullPath);
                        bw.newLine();
                    }
                } catch (FileNotFoundException e) {
                    Log.e("Matrix." + getName(), "FileNotFoundException:%s", e.toString());
                } finally {
                    IOUtil.closeQuietly(br);
                    IOUtil.closeQuietly(bw);
                }
                jarInput.delete(); // delete raw inputList
            }

            replaceFile(inputJar, jarOutput);

        }
    }

    protected String getUniqueJarName(File jarFile) {
        final String origJarName = jarFile.getName();
        final String hashing = Hashing.sha1().hashString(jarFile.getPath(), Charsets.UTF_16LE).toString();
        final int dotPos = origJarName.lastIndexOf('.');
        if (dotPos < 0) {
            return String.format("%s_%s", origJarName, hashing);
        } else {
            final String nameWithoutDotExt = origJarName.substring(0, dotPos);
            final String dotExt = origJarName.substring(dotPos);
            return String.format("%s_%s%s", nameWithoutDotExt, hashing, dotExt);
        }
    }

    private void replaceFile(QualifiedContent input, File newFile) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        final Field fileField = ReflectUtil.getDeclaredFieldRecursive(input.getClass(), "file");
        fileField.set(input, newFile);
    }

    private void replaceChangedFile(DirectoryInput dirInput, Map<File, Status> changedFiles) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        final Field changedFilesField = ReflectUtil.getDeclaredFieldRecursive(dirInput.getClass(), "changedFiles");
        changedFilesField.set(dirInput, changedFiles);
    }

}
