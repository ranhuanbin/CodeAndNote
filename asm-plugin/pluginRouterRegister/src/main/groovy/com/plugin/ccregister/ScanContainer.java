package com.plugin.ccregister;

import java.util.ArrayList;
import java.util.List;

/**
 * 扫描容器
 */
public class ScanContainer {
    private static List<String> classNames = new ArrayList<>();

    public static void add(String className) {
        classNames.add(className);
    }

    public static List<String> getClassNames() {
        return classNames;
    }
}
