package com.billy.cc.core.component;

import java.util.concurrent.ConcurrentHashMap;

public class ComponentManager {
    /**
     * 当前进程中的组件集合
     */
    private static final ConcurrentHashMap<String, IComponent> COMPONENTS = new ConcurrentHashMap<>();

    /**
     * 注册组件
     */
    static void registerComponent(IComponent component) {
        if (component != null) {
            try {
                String name = component.getName();
                COMPONENTS.put(name, component);
            } catch (Exception e) {
            }
        }
    }
}
