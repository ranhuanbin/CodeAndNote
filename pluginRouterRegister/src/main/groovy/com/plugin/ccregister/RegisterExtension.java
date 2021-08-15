package com.plugin.ccregister;

import org.gradle.api.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.plugin.ccregister.CCRegisterPlugin.PLUGIN_NAME;

public class RegisterExtension {
    public Project project;

    public List<RegisterInfo> list = new ArrayList<>();
    public List<Map<String, Object>> registerInfo = new ArrayList<>();

    public void convertConfig() {
        for (Map<String, Object> map : registerInfo) {
            RegisterInfo info = new RegisterInfo();
            info.interfaceName = (String) map.get("scanInterface");
            List<String> superClasses = (List<String>) map.get("scanSuperClasses");
            if (superClasses == null) {
                superClasses = new ArrayList<>();
            }
            info.superClassNames = superClasses;
            // 代码注入的类
            info.initClassName = (String) map.get("codeInsertToClassName");
            // 代码注入的方法(默认为static块)
            info.initMethodName = (String) map.get("codeInsertToMethodName");
            info.registerMethodName = (String) map.get("registerMethodName");
            info.registerClassName = (String) map.get("registerClassName");
            info.paramType = (String) map.get("paramType");
            info.include = (List<String>) map.get("include");
            info.exclude = (List<String>) map.get("exclude");
            info.init();
            if (info.validate()) {
                list.add(info);
            } else {
                System.out.println(PLUGIN_NAME + " extension error: scanInterface, codeInsertToClassName and registerMethodName should not be null\n" + info.toString());
            }
        }
    }
}
