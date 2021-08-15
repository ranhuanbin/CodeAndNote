package com.monitor.method;

import com.monitor.largeimage.Config;

import java.util.List;

public class McUtils {
    /**
     * 是否忽略该类, true: 忽略, false: 不忽略
     */
    public static boolean ignorePackageNames(String className) {
        List<String> mcBlackList = Config.getInstance().getMcBlackList();
        for (String black : mcBlackList) {
            if (className.contains(black)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否为白名单. true: 是, false: 不是
     */
    public static boolean whiteList(String className) {
        List<String> mcWhiteList = Config.getInstance().getMcWhiteList();
        for (String white : mcWhiteList) {
            if (className.contains(white)) {
                return true;
            }
        }
        return false;
    }

}
