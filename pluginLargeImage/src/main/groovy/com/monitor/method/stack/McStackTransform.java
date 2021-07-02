package com.monitor.method.stack;

import com.quinn.hunter.transform.HunterTransform;

import org.gradle.api.Project;

public class McStackTransform extends HunterTransform {
    public McStackTransform(Project project) {
        super(project);
        bytecodeWeaver = new McStackWeaver();
    }
}
