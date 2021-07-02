package com.monitor.largeimage;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private boolean largeImagePluginSwitch = true;
    private int thresholdTime = 500;
    private List<String> mcBlackList = new ArrayList<>();
    private List<String> mcWhiteList = new ArrayList<>();
    // 0: normal, 1: stack
    private int strategy = 0;

    public int getStrategy() {
        return strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }

    public List<String> getMcWhiteList() {
        return mcWhiteList;
    }

    public void setMcWhiteList(List<String> mcWhiteList) {
        this.mcWhiteList = mcWhiteList;
    }

    public List<String> getMcBlackList() {
        return mcBlackList;
    }

    public void setMcBlackList(List<String> mcBlackList) {
        this.mcBlackList = mcBlackList;
    }

    private static class Holder {
        private static Config INSTANCE = new Config();
    }

    public static Config getInstance() {
        return Holder.INSTANCE;
    }

    public boolean largeImagePluginSwitch() {
        return largeImagePluginSwitch;
    }

    public int getThresholdTime() {
        return thresholdTime;
    }

    public void init(LargeImageExtension extension) {
        if (null != extension) {
            this.largeImagePluginSwitch = extension.largeImagePluginSwitch;
            setMcBlackList(extension.mcBlackList);
        }
    }
}
