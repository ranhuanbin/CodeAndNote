package com.lib.monitor.largeimage;

import android.util.Log;

import com.blankj.utilcode.util.ReflectUtils;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.SingleRequest;

import java.util.ArrayList;
import java.util.List;

public class GlideHook {
    public static void process(Object singleRequest) {
        Log.v("AndroidTest", "GlideHook process");
        try {
            List<RequestListener> requestListeners = null;
            if (singleRequest instanceof SingleRequest) {
                requestListeners = ReflectUtils.reflect(singleRequest).field("requestListeners").get();
            }
            if (requestListeners == null) {
                requestListeners = new ArrayList<>();
                requestListeners.add(new LargeImageListener());
            } else {
                requestListeners.add(new LargeImageListener());
            }
            if (singleRequest instanceof SingleRequest) {
                ReflectUtils.reflect(singleRequest).field("requestListeners", requestListeners);
            }
        } catch (Exception e) {
            Log.v("AndroidTest", "process exception = " + e);
        }
    }
}
