package com.lib.monitor.largeimage;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;

public class GlideLargeImageListener<R> implements RequestListener<R> {
    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<R> target, boolean isFirstResource) {
        Log.v("AndroidTest", "onLoadFailed");
        return false;
    }

    @Override
    public boolean onResourceReady(R resource, Object model, Target<R> target, DataSource dataSource, boolean isFirstResource) {
        Log.v("AndroidTest", "onResourceReady");
        int width = 0;
        int height = 0;
        if (target instanceof ViewTarget) {
            View view = ((ViewTarget) target).getView();
            width = view.getWidth();
            height = view.getHeight();
        }
        if (resource instanceof Bitmap) {
            LargeImageManager.getInstance().transform(model.toString(), (Bitmap) resource, "Glide", width, height);
        } else if (resource instanceof BitmapDrawable) {
            LargeImageManager.getInstance().transform(model.toString(), (BitmapDrawable) resource, "Glide", width, height);
        }
        return false;
    }
}
