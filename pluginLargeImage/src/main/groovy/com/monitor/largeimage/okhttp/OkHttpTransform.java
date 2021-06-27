package com.monitor.largeimage.okhttp;

import com.quinn.hunter.transform.HunterTransform;

import org.gradle.api.Project;

public class OkHttpTransform extends HunterTransform {

    public OkHttpTransform(Project project) {
        super(project);
        this.bytecodeWeaver = new OkHttpWeaver();
    }
}
