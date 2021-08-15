package com.plugin.ccregister;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * 1. 扫描出所有的IComponent的子类IComponentImpl
 * 2. 读取IComponentImpl的getName方法
 * 3. 将getName方法返回的数据与IComponentImpl进行绑定, 添加到ComponentManager中
 */
public class CCRegisterPlugin implements Plugin<Project> {
    public static final String PLUGIN_NAME = "register";
    public static final String EXT_NAME = "register";

    @Override
    public void apply(Project project) {
        LogUtils.log("project = " + project.getName());
        RegisterTransform transform = new RegisterTransform(project);
        AppExtension appExtension = project.getExtensions().getByType(AppExtension.class);
        appExtension.registerTransform(transform);
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
//                RegisterExtension config = init(project, transform);
            }
        });
    }

    static RegisterExtension init(Project project, RegisterTransform transform) {
        // 读取build中的配置的内容到RegisterExtension, 完成build中的json配置到Plugin的Config对象的转换
        RegisterExtension extension = (RegisterExtension) project.getExtensions().findByName(EXT_NAME);
        return extension;
    }
}
