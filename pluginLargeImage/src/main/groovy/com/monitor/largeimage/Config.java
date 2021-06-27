package com.monitor.largeimage;

public class Config {
    private boolean largeImagePluginSwitch = true;

    private static class Holder {
        private static Config INSTANCE = new Config();
    }

    public static Config getInstance() {
        return Holder.INSTANCE;
    }

    public boolean largeImagePluginSwitch() {
        return largeImagePluginSwitch;
    }

    public void init(LargeImageExtension extension) {
        if (null != extension) {
            this.largeImagePluginSwitch = extension.largeImagePluginSwitch;
        }
    }
}
