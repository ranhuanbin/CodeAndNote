package com.lib.router;

import java.util.concurrent.ConcurrentHashMap;

public class RouterComponentManager {
    private static final ConcurrentHashMap<String, IRouterComponent> COMPONENTS = new ConcurrentHashMap<>();

    public static void register(String name, IRouterComponent component) {
        COMPONENTS.put(name, component);
    }


    static {
        // 编译之后加的这个代码
        COMPONENTS.put("DemoComponent", new DemoComponent());
    }
}
