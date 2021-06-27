package com.lib.monitor.largeimage;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.concurrent.atomic.AtomicInteger;

public class LargeImageInfo implements Parcelable {
    private String url;
    private double fileSize;
    private double memorySize;
    private int width;
    private int height;
    private String framework;
    private int targetWidth;
    private int targetHeight;
    private AtomicInteger unUseCount = new AtomicInteger();

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    public double getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(double memorySize) {
        this.memorySize = memorySize;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public int getTargetWidth() {
        return targetWidth;
    }

    public void setTargetWidth(int targetWidth) {
        this.targetWidth = targetWidth;
    }

    public int getTargetHeight() {
        return targetHeight;
    }

    public void setTargetHeight(int targetHeight) {
        this.targetHeight = targetHeight;
    }

    public AtomicInteger getUnUseCount() {
        return unUseCount;
    }

    public void setUnUseCount(AtomicInteger unUseCount) {
        this.unUseCount = unUseCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeDouble(this.fileSize);
        dest.writeDouble(this.memorySize);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.framework);
        dest.writeInt(this.targetWidth);
        dest.writeInt(this.targetHeight);
        dest.writeSerializable(this.unUseCount);
    }

    public void readFromParcel(Parcel source) {
        this.url = source.readString();
        this.fileSize = source.readDouble();
        this.memorySize = source.readDouble();
        this.width = source.readInt();
        this.height = source.readInt();
        this.framework = source.readString();
        this.targetWidth = source.readInt();
        this.targetHeight = source.readInt();
        this.unUseCount = (AtomicInteger) source.readSerializable();
    }

    public LargeImageInfo() {
    }

    protected LargeImageInfo(Parcel in) {
        this.url = in.readString();
        this.fileSize = in.readDouble();
        this.memorySize = in.readDouble();
        this.width = in.readInt();
        this.height = in.readInt();
        this.framework = in.readString();
        this.targetWidth = in.readInt();
        this.targetHeight = in.readInt();
        this.unUseCount = (AtomicInteger) in.readSerializable();
    }

    public static final Creator<LargeImageInfo> CREATOR = new Creator<LargeImageInfo>() {
        @Override
        public LargeImageInfo createFromParcel(Parcel source) {
            return new LargeImageInfo(source);
        }

        @Override
        public LargeImageInfo[] newArray(int size) {
            return new LargeImageInfo[size];
        }
    };

    @Override
    public String toString() {
        return "LargeImageInfo{" +
                "url='" + url + '\'' +
                ", fileSize=" + fileSize +
                ", memorySize=" + memorySize +
                ", width=" + width +
                ", height=" + height +
                ", framework='" + framework + '\'' +
                ", targetWidth=" + targetWidth +
                ", targetHeight=" + targetHeight +
                ", unUseCount=" + unUseCount +
                '}';
    }
}
