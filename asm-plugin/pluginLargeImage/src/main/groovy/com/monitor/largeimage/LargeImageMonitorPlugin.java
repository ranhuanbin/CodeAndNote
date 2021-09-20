package com.monitor.largeimage;

import com.android.build.gradle.AppExtension;
import com.monitor.largeimage.glide.GlideTransform;
import com.monitor.largeimage.okhttp.OkHttpTransform;
import com.monitor.method.normal.McNormalTransform;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.Collections;
import java.util.List;

public class LargeImageMonitorPlugin implements Plugin<Project> {
    private static final String EXT_LARGEIMAGE = "largeImageMonitor";

    @Override
    public void apply(Project project) {
        List<String> taskNames = project.getGradle().getStartParameter().getTaskNames();
        AppExtension appExtension = (AppExtension) project.getProperties().get("android");
        // 创建自定义扩展
        project.getExtensions().create(EXT_LARGEIMAGE, LargeImageExtension.class);
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(Project project) {
                // 使用之前需要先create, 之后才能使用
                LargeImageExtension extension = project.getExtensions().getByType(LargeImageExtension.class);
                Config.getInstance().init(extension);
            }
        });
        appExtension.registerTransform(new OkHttpTransform(project), Collections.EMPTY_LIST);
        appExtension.registerTransform(new GlideTransform(project), Collections.EMPTY_LIST);
        appExtension.registerTransform(new McNormalTransform(project), Collections.EMPTY_LIST);
    }
}
