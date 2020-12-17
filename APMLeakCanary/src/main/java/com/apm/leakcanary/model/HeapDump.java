package com.apm.leakcanary.model;

import java.io.File;
import java.io.Serializable;

public class HeapDump implements Serializable {
    private File hprofFile;
    private String refKey;
    private String activityName;

    public HeapDump(File hprofFile, String refKey, String activityName) {
        this.hprofFile = hprofFile;
        this.refKey = refKey;
        this.activityName = activityName;
    }

    public File getHprofFile() {
        return hprofFile;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getRefKey() {
        return refKey;
    }
}
