package com.lib.monitor.largeimage.utils;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lib.monitor.largeimage.LargeImage;
import com.module.R;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class LargeMonitorDialog {
    private static Map<String, Boolean> alarmInfo = new HashMap<>();
    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");


    public static void showDialog(final String url, int width, int height, double fileSize, double memorySize, int targetWidth,
                                  int targetHeigh) {
    }

    public static void showDialog(final String url, int width, int height, double fileSize,
                                  double memorySize, int targetWidth,
                                  int targetHeigh, Bitmap bitmap) {
        //判断当前URL是否已经添加进去，如果已经添加进去，则不进行添加
        if (!alarmInfo.containsKey(url)) {
            alarmInfo.put(url, false);
        }
        //如果为true说明该URL已经被弹出，则不再次弹出
        if (alarmInfo.get(url)) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(LargeImage.APPLICATION)) {
                getOverlayPermission();
                //目前如果没有权限的话，只能返回，否则会报错，这样就可能导致有些警告窗不显示
                return;
            }
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(LargeImage.APPLICATION);
        View dialogView = View.inflate(LargeImage.APPLICATION, R.layout.dialog_custom, null);
        LinearLayout llMemorySize = dialogView.findViewById(R.id.ll_memory_size);
        TextView tvMemorySize = dialogView.findViewById(R.id.tv_memory_size);
        ImageView ivMemorySize = dialogView.findViewById(R.id.iv_memory_size);
        LinearLayout llFileSize = dialogView.findViewById(R.id.ll_file_size);
        TextView tvFileSize = dialogView.findViewById(R.id.tv_file_size);
        ImageView ivFileSize = dialogView.findViewById(R.id.iv_file_size);
        TextView tvSize = dialogView.findViewById(R.id.tv_size);
        TextView tvViewSize = dialogView.findViewById(R.id.tv_view_size);
        TextView tvImageUrl = dialogView.findViewById(R.id.tv_image_url);
        ImageView ivThumb = dialogView.findViewById(R.id.iv_thumb);
        //设置文件大小
        setFileSize(fileSize, llFileSize, tvFileSize, ivFileSize);
        //设置内存大小
        setMemorySize(memorySize, llMemorySize, tvMemorySize, ivMemorySize);
        //设置图片尺寸
        setImageSize(width, height, tvSize);
        //设置View尺寸
        setViewSize(targetWidth, targetHeigh, tvViewSize);
        //设置图片地址
        setImageUrl(url, tvImageUrl);
        //设置图片
        ivThumb.setImageBitmap(bitmap);
        AlertDialog alertDialog = builder.setTitle("提示").setView(dialogView).setPositiveButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alarmInfo.put(url, false);
                        dialog.dismiss();
                    }
                }).setNegativeButton("不再提醒（直到下次重启）", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LargeImage.getInstance().setLargeImageOpen(false);
                dialog.dismiss();
            }
        }).create();
        //设置全局Dialog
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//8.0
            alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
        }
        alertDialog.show();
        //标识正在显示警告
        alarmInfo.put(url, true);
    }

    private static void setImageUrl(final String url, TextView tvImageUrl) {
        if (TextUtils.isEmpty(url)) {
            tvImageUrl.setVisibility(View.GONE);
        } else {
            tvImageUrl.setVisibility(View.VISIBLE);
            if (!url.startsWith("http") && !url.startsWith("https")) {
                String tempUrl = url;
                if (url.contains("/")) {
                    int index = url.lastIndexOf("/");
                    tempUrl = url.substring(index + 1, url.length());
                }
                try {
                    final String resourceName = LargeImage.APPLICATION.getApplicationContext().getResources().getResourceName(Integer.parseInt(tempUrl));
                    if (TextUtils.isEmpty(resourceName)) {
                        tvImageUrl.setText(ResHelper.getString(R.string.large_image_url, "本地图片"));
                    } else {
                        tvImageUrl.setText(ResHelper.getString(R.string.large_image_url, resourceName));
                    }
                    tvImageUrl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            copyToClipboard(resourceName);
                        }
                    });
                } catch (NumberFormatException e) {
                    //不是请求网络，也没用resId,统一显示为本地图片
                    e.printStackTrace();
                    tvImageUrl.setText(ResHelper.getString(R.string.large_image_url, "本地图片"));
                }
            } else {
                tvImageUrl.setText(ResHelper.getString(R.string.large_image_url, url));
                tvImageUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        copyToClipboard(url);
                    }
                });
            }
        }
    }

    private static void copyToClipboard(String resourceName) {
        try {
            //获取剪贴板管理器
            ClipboardManager cm = (ClipboardManager) LargeImage.APPLICATION.getSystemService(Context.CLIPBOARD_SERVICE);
            //创建普通字符型ClipData
            ClipData clipData = ClipData.newPlainText("Label", resourceName);
            cm.setPrimaryClip(clipData);
            Toast.makeText(LargeImage.APPLICATION.getApplicationContext(), "复制成功！", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(LargeImage.APPLICATION.getApplicationContext(), "复制失败！", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private static void setViewSize(int targetWidth, int targetHeigh, TextView tvViewSize) {
        if (targetWidth <= 0 || targetHeigh <= 0) {
            tvViewSize.setVisibility(View.GONE);
        } else {
            tvViewSize.setVisibility(View.VISIBLE);
            StringBuilder sb = new StringBuilder();
            sb.append(targetWidth).append("*").append(targetHeigh);
            tvViewSize.setText(ResHelper.getString(R.string.large_view_size, sb.toString()));
        }
    }

    private static void setImageSize(int width, int height, TextView tvSize) {
        if (width <= 0 && height <= 0) {
            tvSize.setVisibility(View.GONE);
        } else {
            tvSize.setVisibility(View.VISIBLE);
            StringBuilder sb = new StringBuilder();
            sb.append(width).append("*").append(height);
            tvSize.setText(ResHelper.getString(R.string.large_image_size, sb.toString()));
        }
    }

    private static void setMemorySize(double memorySize, LinearLayout llMemorySize, TextView tvMemorySize, ImageView ivMemorySize) {
        if (memorySize <= 0) {
            llMemorySize.setVisibility(View.GONE);
        } else {
            llMemorySize.setVisibility(View.VISIBLE);
            if (memorySize > LargeImage.getInstance().getMemorySizeThreshold()) {
                ivMemorySize.setVisibility(View.VISIBLE);
            } else {
                ivMemorySize.setVisibility(View.GONE);
            }
            double size = memorySize / 1024;
            tvMemorySize.setText(ResHelper.getString(R.string.large_image_memory_size, decimalFormat.format(size)));
        }
    }

    private static void setFileSize(double fileSize, LinearLayout llFileSize, TextView tvFileSize, ImageView ivFileSize) {
        if (fileSize <= 0) {
            llFileSize.setVisibility(View.GONE);
        } else {
            llFileSize.setVisibility(View.VISIBLE);
            if (fileSize > LargeImage.getInstance().getFileSizeThreshold()) {
                ivFileSize.setVisibility(View.VISIBLE);
            } else {
                ivFileSize.setVisibility(View.GONE);
            }
            tvFileSize.setText(ResHelper.getString(R.string.large_image_file_size, decimalFormat.format(fileSize)));
        }
    }

    private static void getOverlayPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + LargeImage.APPLICATION.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        LargeImage.APPLICATION.getApplicationContext().startActivity(intent);
    }
}
