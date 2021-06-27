package com.monitor.largeimage.glide;

import com.quinn.hunter.transform.HunterTransform;

import org.gradle.api.Project;

public class GlideTransform extends HunterTransform {
    public GlideTransform(Project project) {
        super(project);
        this.bytecodeWeaver = new GlideWeaver();
    }
}
