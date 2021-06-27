package com.lib.monitor.largeimage;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;

import com.lib.monitor.largeimage.utils.ConvertUtils;
import com.lib.monitor.largeimage.utils.LargeMonitorDialog;
import com.tencent.mmkv.MMKV;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LargeImageManager {


    private Handler mainHandler = new Handler(Looper.getMainLooper());

    private MMKV mmkv;

    // 用来保存超标图片信息是否被显示
    private Map<String, Boolean> alarmInfo = new HashMap<>();
    // 缓存超标的Bitmap, 用来弹窗显示
    private Map<String, Bitmap> bitmapCache = new ConcurrentHashMap<>();
    // 缓存大图信息, 用于列表显示
    private Map<String, LargeImageInfo> infoCache = new ConcurrentHashMap<>();
    // 是否开启弹窗
    private volatile boolean openDialog = false;

    private LargeImageManager() {
        mmkv = MMKV.mmkvWithID("LargeImageManager");
    }

    public void saveImageInfo(String imageUrl, int fileSize) {

    }

    public void saveImageInfo(String imageUrl, long memorySize, int width, int height, String framework,
                              int targetWidth, int targetHeight, Bitmap bitmap) {
        if (memorySize <= 0) {
            return;
        }
        if (!LargeImage.getInstance().isLargeImageOpen()) {
            return;
        }
        double size = ConvertUtils.byte2MemorySize(memorySize, ConvertUtils.KB);
        if (mmkv.containsKey(imageUrl)) {
            LargeImageInfo largeImageInfo = mmkv.decodeParcelable(imageUrl, LargeImageInfo.class);
            if (largeImageInfo.getFileSize() > LargeImage.getInstance().getFileSizeThreshold() ||
                    size >= LargeImage.getInstance().getMemorySizeThreshold()) {
                largeImageInfo.setWidth(width);
                largeImageInfo.setHeight(height);
                largeImageInfo.setMemorySize(size);
                largeImageInfo.setFramework(framework);
                largeImageInfo.setTargetWidth(targetWidth);
                largeImageInfo.setTargetHeight(targetHeight);
                bitmapCache.put(imageUrl, bitmap);
                infoCache.put(imageUrl, largeImageInfo);
                mmkv.encode(imageUrl, largeImageInfo);
            } else {
                bitmapCache.remove(imageUrl);
                infoCache.remove(imageUrl);
                mmkv.remove(imageUrl);
            }
        } else {
            // 如果图片从本地加载, 则没有文件大小数据, 第一次加载时也还没存储数据, 只能看内存是否超标
            if (size >= LargeImage.getInstance().getMemorySizeThreshold()) {
                LargeImageInfo largeImageInfo = new LargeImageInfo();
                largeImageInfo.setUrl(imageUrl);
                largeImageInfo.setWidth(width);
                largeImageInfo.setHeight(height);
                largeImageInfo.setMemorySize(size);
                largeImageInfo.setFramework(framework);
                largeImageInfo.setTargetWidth(targetWidth);
                largeImageInfo.setTargetHeight(targetHeight);
                largeImageInfo.getUnUseCount().set(0);
                bitmapCache.put(imageUrl, bitmap);
                infoCache.put(imageUrl, largeImageInfo);
                mmkv.encode(imageUrl, largeImageInfo);
            }
        }
        if (openDialog) {
            // 开启弹窗模式, 才显示
            isShowAlarm(imageUrl);
        }
    }

    /**
     * 最后判断文件大小或者内存大小是否有超值
     */
    private void isShowAlarm(String imageUrl) {
        final LargeImageInfo largeImageInfo = mmkv.decodeParcelable(imageUrl, LargeImageInfo.class);
        if (null == largeImageInfo) {
            return;
        }
        if (largeImageInfo.getFileSize() >= LargeImage.getInstance().getFileSizeThreshold()
                || largeImageInfo.getMemorySize() >= LargeImage.getInstance().getMemorySizeThreshold()) {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    LargeMonitorDialog.showDialog(largeImageInfo.getUrl(), largeImageInfo.getWidth(), largeImageInfo.getHeight(),
                            largeImageInfo.getFileSize(), largeImageInfo.getMemorySize(),
                            largeImageInfo.getTargetWidth(), largeImageInfo.getTargetHeight(),
                            bitmapCache.get(imageUrl));
                }
            });
        }
    }

    public Bitmap transform(String imageUrl, Bitmap sourceBitmap, String framework, int width, int height) {
        if (null == sourceBitmap) {
            return null;
        }
        if (LargeImage.getInstance().isLargeImageOpen()) {
            saveImageInfo(imageUrl, sourceBitmap.getByteCount(), sourceBitmap.getWidth(), sourceBitmap.getHeight(),
                    framework, width, height, sourceBitmap);
        }
        return sourceBitmap;
    }

    public Bitmap transform(String imageUrl, BitmapDrawable resource, String glide, int width, int height) {
        Bitmap sourceBitmap = ConvertUtils.drawable2Bitmap(resource);
        return transform(imageUrl, sourceBitmap, glide, width, height);
    }

    public Map<String, Bitmap> getBitmapCache() {
        return bitmapCache;
    }

    public Map<String, LargeImageInfo> getInfoCache() {
        return infoCache;
    }

    /**
     * @param openDialog 是否开启弹窗
     */
    public void setOpenDialog(boolean openDialog) {
        this.openDialog = openDialog;
    }

    public boolean isOpenDialog() {
        return openDialog;
    }

    private static class Holder {
        private static LargeImageManager instance = new LargeImageManager();
    }

    public static LargeImageManager getInstance() {
        return Holder.instance;
    }

    public MMKV getMmkv() {
        return mmkv;
    }

}
