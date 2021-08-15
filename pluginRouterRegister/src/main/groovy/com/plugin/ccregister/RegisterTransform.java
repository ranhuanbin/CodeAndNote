package com.plugin.ccregister;

import com.quinn.hunter.transform.HunterTransform;

import org.gradle.api.Project;

public class RegisterTransform extends HunterTransform {
    public RegisterTransform(Project project) {
        super(project);
        this.bytecodeWeaver = new RegisterWear();
    }
}
