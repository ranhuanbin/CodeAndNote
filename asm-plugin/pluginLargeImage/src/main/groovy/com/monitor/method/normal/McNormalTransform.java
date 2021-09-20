package com.monitor.method.normal;

import com.quinn.hunter.transform.HunterTransform;

import org.gradle.api.Project;

public class McNormalTransform extends HunterTransform {
    public McNormalTransform(Project project) {
        super(project);
        bytecodeWeaver = new McNormalWeaver();
    }
}
