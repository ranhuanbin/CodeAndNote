package com.test.apm.leakcanary;

import android.os.Debug;

import com.test.apm.leak.watcher.HeapDumper;
import com.test.apm.leakcanary.privoder.CustomLeakDirectoryProvider;
import com.test.utils.AppUtils;
import com.test.utils.LogUtils;

import java.io.File;

public class CustomHeapDumper implements HeapDumper {

    private CustomLeakDirectoryProvider leakDirectoryProvider;

    public CustomHeapDumper() {
        this.leakDirectoryProvider = new CustomLeakDirectoryProvider(AppUtils.getContext());
    }

    @SuppressWarnings("ReferenceEquality") // Explicitly checking for named null.
    @Override
    public File dumpHeap() {
        LogUtils.leakLog(CustomHeapDumper.class, "dumpHeap()-----1");
        File heapDumpFile = leakDirectoryProvider.newHeapDumpFile();
        LogUtils.leakLog(CustomHeapDumper.class, "dumpHeap()-----2-----heapDumpFile: " + heapDumpFile);
        if (heapDumpFile == RETRY_LATER) {
            return RETRY_LATER;
        }
        try {
            long time = System.currentTimeMillis();
            // Dump "hprof" data to the specified file.  This may cause a GC.
            Debug.dumpHprofData(heapDumpFile.getAbsolutePath());
            LogUtils.leakLog(CustomHeapDumper.class, "dumpHeap()-----dumpHprofData-----interval: " + (System.currentTimeMillis() - time));
            return heapDumpFile;
        } catch (Exception e) {
            LogUtils.leakLog(CustomHeapDumper.class, "dumpHeap()-----3-----exception: " + e.getMessage());
            return RETRY_LATER;
//        }
        }
    }
}
