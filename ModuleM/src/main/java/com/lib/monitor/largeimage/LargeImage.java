package com.lib.monitor.largeimage;

import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;

import com.lib.monitor.largeimage.notify.activity.LargeImageListActivity;
import com.module.R;
import com.tencent.mmkv.MMKV;
import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.MoveType;
import com.yhao.floatwindow.Screen;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;

public class LargeImage {
    public static Application APPLICATION;
    private List<Interceptor> okHttpInterceptors = new ArrayList<>();
    private double memorySizeThreshold = 0;
    private double fileSizeThreshold = 0;
    private boolean largeImageOpen;

    public void setLargeImageOpen(boolean largeImageOpen) {
        this.largeImageOpen = largeImageOpen;
    }

    public boolean isLargeImageOpen() {
        return largeImageOpen;
    }

    public void setMemorySizeThreshold(double memorySizeThreshold) {
        this.memorySizeThreshold = memorySizeThreshold;
    }

    public double getMemorySizeThreshold() {
        return memorySizeThreshold;
    }

    public LargeImage setFileSizeThreshold(double fileSizeThreshold) {
        this.fileSizeThreshold = fileSizeThreshold;
        return this;
    }

    public double getFileSizeThreshold() {
        return fileSizeThreshold;
    }

    private static class Holder {
        private static LargeImage INSTANCE = new LargeImage();

    }

    public static LargeImage getInstance() {
        return Holder.INSTANCE;
    }

    public List<Interceptor> getOkHttpInterceptors() {
        return okHttpInterceptors;
    }

    public LargeImage install(Application app) {
        APPLICATION = app;
        okHttpInterceptors.add(new LargeImageInterceptor());
        MMKV.initialize(app);
        ImageView ivIcon = new ImageView(app);
        ivIcon.setImageResource(R.drawable.ic_logo);
        FloatWindow.with(app)
                .setView(ivIcon)
                .setWidth(Screen.width, 0.2f)
                .setHeight(Screen.height, 0.2f)
                .setX(Screen.width, 0.8f)
                .setY(Screen.height, 0.3f)
                .setMoveType(MoveType.slide, 0, 0)
                .setMoveStyle(500, new BounceInterpolator())
                .setDesktopShow(false)
                .build();
        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(APPLICATION, LargeImageListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                APPLICATION.startActivity(intent);
            }
        });
        setLargeImageOpen(true);
        return this;
    }
}
