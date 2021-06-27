package com.lib.monitor.largeimage;

import android.os.Handler;
import android.os.Looper;

import com.tencent.mmkv.MMKV;

public class NewLargeImageManager {
    public static NewLargeImageManager getInstance() {
        return Holder.instance;
    }

    private static class Holder {
        private static final NewLargeImageManager instance = new NewLargeImageManager();
    }

    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private MMKV mmkv;

    public NewLargeImageManager() {
        mmkv = MMKV.mmkvWithID("NewLargeImageManager");
    }
}
