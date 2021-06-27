package com.lib.monitor.largeimage.notify.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.lib.monitor.largeimage.LargeImage;
import com.lib.monitor.largeimage.LargeImageInfo;
import com.lib.monitor.largeimage.LargeImageManager;
import com.lib.monitor.largeimage.utils.ResHelper;
import com.module.R;

import java.text.DecimalFormat;
import java.util.List;

public class LargeImageListAdapter extends RecyclerView.Adapter<LargeImageListAdapter.MyHolder> {

    private DecimalFormat mDecimalFormat = new DecimalFormat("0.00");
    private List<LargeImageInfo> mData;
    private Context mContext;

    public LargeImageListAdapter(Context context, @Nullable List<LargeImageInfo> data) {
        this.mData = data;
        this.mContext = context;
    }

    @androidx.annotation.NonNull
    @Override
    public MyHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_custom, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull MyHolder holder, int position) {
        LargeImageInfo largeImageInfo = mData.get(position);
        //设置文件大小
        setFileSize(largeImageInfo.getFileSize(), holder.llFileSize, holder.tvFileSize, holder.ivFileSize);
        //设置内存大小
        setMemorySize(largeImageInfo.getMemorySize(), holder.llMemorySize,
                holder.tvMemorySize, holder.ivMemorySize);
        //设置图片尺寸
        setImageSize(largeImageInfo.getWidth(), largeImageInfo.getHeight(), holder.tvSize);
        //设置View尺寸
        setViewSize(largeImageInfo.getTargetWidth(), largeImageInfo.getTargetHeight(), holder.tvViewSize);
        //设置图片地址
        setImageUrl(largeImageInfo.getUrl(), holder.tvImageUrl);
        Bitmap bitmap = LargeImageManager.getInstance().getBitmapCache().get(largeImageInfo.getUrl());
        //设置图片
        holder.ivThumb.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    private void setImageUrl(final String url, TextView tvImageUrl) {
        if (TextUtils.isEmpty(url)) {
            tvImageUrl.setVisibility(View.GONE);
        } else {
            tvImageUrl.setVisibility(View.VISIBLE);
            String tempUrl = url;
            if (!url.startsWith("http") && !url.startsWith("https")) {
                if (TextUtils.isDigitsOnly(url)) {
                    try {
                        tempUrl = LargeImage.APPLICATION.getApplicationContext().getResources().getResourceName(Integer.parseInt(tempUrl));
                    } catch (NumberFormatException e) {
                        //不是请求网络，也没用resId,统一显示为本地图片
                        e.printStackTrace();
                        tvImageUrl.setText(ResHelper.getString(R.string.large_image_url, "本地图片"));
                    }
                }
            }
            String finalTempUrl = tempUrl;
            tvImageUrl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    copyToClipboard(finalTempUrl);
                }
            });
            tvImageUrl.setText(ResHelper.getString(R.string.large_image_url, tempUrl));
        }
    }

    private void copyToClipboard(String resourceName) {
        try {
            //获取剪贴板管理器
            ClipboardManager cm =
                    (ClipboardManager) LargeImage.APPLICATION.getSystemService(Context.CLIPBOARD_SERVICE);
            //创建普通字符型ClipData
            ClipData clipData = ClipData.newPlainText("Label", resourceName);
            cm.setPrimaryClip(clipData);
            Toast.makeText(LargeImage.APPLICATION.getApplicationContext(), "复制成功！", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(LargeImage.APPLICATION.getApplicationContext(), "复制失败！", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void setViewSize(int targetWidth, int targetHeigh, TextView tvViewSize) {
        if (targetWidth <= 0 || targetHeigh <= 0) {
            tvViewSize.setVisibility(View.GONE);
        } else {
            tvViewSize.setVisibility(View.VISIBLE);
            StringBuilder sb = new StringBuilder();
            sb.append(targetWidth).append("*").append(targetHeigh);
            tvViewSize.setText(ResHelper.getString(R.string.large_view_size, sb.toString()));
        }
    }

    private void setImageSize(int width, int height, TextView tvSize) {
        if (width <= 0 && height <= 0) {
            tvSize.setVisibility(View.GONE);
        } else {
            tvSize.setVisibility(View.VISIBLE);
            StringBuilder sb = new StringBuilder();
            sb.append(width).append("*").append(height);
            tvSize.setText(ResHelper.getString(R.string.large_image_size, sb.toString()));
        }
    }

    private void setMemorySize(double memorySize, LinearLayout llMemorySize, TextView tvMemorySize, ImageView ivMemorySize) {
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
            tvMemorySize.setText(ResHelper.getString(R.string.large_image_memory_size, mDecimalFormat.format(size)));
        }
    }

    private void setFileSize(double fileSize, LinearLayout llFileSize, TextView tvFileSize, ImageView ivFileSize) {
        if (fileSize <= 0) {
            llFileSize.setVisibility(View.GONE);
        } else {
            llFileSize.setVisibility(View.VISIBLE);
            if (fileSize > LargeImage.getInstance().getFileSizeThreshold()) {
                ivFileSize.setVisibility(View.VISIBLE);
            } else {
                ivFileSize.setVisibility(View.GONE);
            }
            tvFileSize.setText(ResHelper.getString(R.string.large_image_file_size, mDecimalFormat.format(fileSize)));
        }
    }


    class MyHolder extends RecyclerView.ViewHolder {
        LinearLayout llMemorySize;
        TextView tvMemorySize;
        ImageView ivMemorySize;
        LinearLayout llFileSize;
        TextView tvFileSize;
        ImageView ivFileSize;
        TextView tvSize;
        TextView tvViewSize;
        TextView tvImageUrl;
        ImageView ivThumb;

        public MyHolder(@androidx.annotation.NonNull View itemView) {
            super(itemView);
            llMemorySize = itemView.findViewById(R.id.ll_memory_size);
            tvMemorySize = itemView.findViewById(R.id.tv_memory_size);
            ivMemorySize = itemView.findViewById(R.id.iv_memory_size);
            llFileSize = itemView.findViewById(R.id.ll_file_size);
            tvFileSize = itemView.findViewById(R.id.tv_file_size);
            ivFileSize = itemView.findViewById(R.id.iv_file_size);
            tvSize = itemView.findViewById(R.id.tv_size);
            tvViewSize = itemView.findViewById(R.id.tv_view_size);
            tvImageUrl = itemView.findViewById(R.id.tv_image_url);
            ivThumb = itemView.findViewById(R.id.iv_thumb);
        }
    }
}
