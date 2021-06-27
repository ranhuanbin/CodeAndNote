package com.lib.monitor.largeimage

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.lib.monitor.largeimage.utils.ConvertUtils
import com.lib.monitor.largeimage.utils.LargeMonitorDialog
import com.tencent.mmkv.MMKV

object NewLargeImageManager {
    private val mainHandler = Handler(Looper.getMainLooper())
    private val mmkv: MMKV = MMKV.mmkvWithID("NewLargeImageManager")
    private val mmkvDialog: MMKV = MMKV.mmkvWithID("LargeImageDialogConfig")

    fun isLargeImageDialogEnable(): Boolean {
        return mmkvDialog.decodeBool("largeImageDialogEnable", false)
    }

    fun setLargeImageDialogEnable(enable: Boolean) {
        mmkvDialog.encode("largeImageDialogEnable", enable)
    }

    /**
     * 处理图片数据
     */
    fun process(
        imageUrl: String,
        imageWidth: Int,
        imageHeight: Int,
        viewWidth: Int,
        viewHeight: Int,
        memorySize: Double
    ) {
        if (LargeImage.getInstance().isLargeImageOpen) {
            saveImageInfo(imageUrl, imageWidth, imageHeight, viewWidth, viewHeight, memorySize)
        }
    }

    /**
     * OKHTTP获取文件数据进行保存
     */
    fun saveImageInfo(imageUrl: String, fileSize: Long) {
        Log.v("AndroidTest", "imageUrl = $imageUrl, fileSize = $fileSize")
        if (fileSize <= 0) {
            return
        }
        if (LargeImage.getInstance().isLargeImageOpen) {
            val size = ConvertUtils.byte2MemorySize(fileSize, ConvertUtils.KB)
            val largeImageInfo: LargeImageInfo
            if (mmkv.containsKey(imageUrl)) {
                largeImageInfo = mmkv.decodeParcelable(imageUrl, LargeImageInfo::class.java)
            } else {
                largeImageInfo = LargeImageInfo()
                largeImageInfo.url = imageUrl
            }
            largeImageInfo.fileSize = size
            mmkv.encode(imageUrl, largeImageInfo)
        }
    }

    private fun saveImageInfo(
        imageUrl: String,
        width: Int,
        height: Int,
        viewWidth: Int,
        viewHeight: Int,
        memorySize: Double
    ) {
        if (memorySize <= 0) return
        val size = ConvertUtils.byte2MemorySize(
            memorySize.toLong(), ConvertUtils.KB
        )
        if (mmkv.containsKey(imageUrl)) {
            val largeImageInfo = mmkv.decodeParcelable(imageUrl, LargeImageInfo::class.java)
            if (largeImageInfo.fileSize > LargeImage.getInstance().fileSizeThreshold
                || largeImageInfo.memorySize > LargeImage.getInstance().memorySizeThreshold
            ) {
                largeImageInfo.width = width
                largeImageInfo.height = height
                largeImageInfo.memorySize = size
                largeImageInfo.targetWidth = viewWidth
                largeImageInfo.targetHeight = viewHeight
                Log.v("AndroidTest", "mmkv.encode($imageUrl)")
                mmkv.encode(imageUrl, largeImageInfo)
            } else {
                Log.v("AndroidTest", "mmkv.remove($imageUrl)")
                mmkv.remove(imageUrl)
            }
        } else {
            if (size >= LargeImage.getInstance().memorySizeThreshold) {
                val largeImageInfo = LargeImageInfo()
                largeImageInfo.url = imageUrl
                largeImageInfo.width = width
                largeImageInfo.height = height
                largeImageInfo.memorySize = size
                largeImageInfo.targetWidth = viewWidth
                largeImageInfo.targetHeight = viewHeight
                Log.v("AndroidTest", "mmkv.encode($imageUrl)")
                mmkv.encode(imageUrl, largeImageInfo)
            }
        }
        Log.v("AndroidTest", "openDialog = ${isLargeImageDialogEnable()}")
        if (isLargeImageDialogEnable()) {
            showAlarm(imageUrl)
        }
    }

    private fun showAlarm(imageUrl: String) {
        //判断当前URL是否已经添加进去，如果已经添加进去，则不进行添加
        Log.v(
            "AndroidTest",
            "showAlarm-1 ${mmkv.decodeParcelable(imageUrl, LargeImageInfo::class.java)}"
        )
        mmkv.decodeParcelable(imageUrl, LargeImageInfo::class.java)?.let {
            if (it.fileSize >= LargeImage.getInstance().fileSizeThreshold
                || it.memorySize >= LargeImage.getInstance().memorySizeThreshold
            ) {
                mainHandler.post {
                    LargeMonitorDialog.showDialog(
                        it.url,
                        it.width,
                        it.height,
                        it.fileSize,
                        it.memorySize,
                        it.targetWidth,
                        it.targetHeight
                    )
                }
            }
        }
    }

    fun getCacheOversizeImg(): List<LargeImageInfo> {
        val largeImageInfos = mutableListOf<LargeImageInfo>()
        Log.v("AndroidTest", "getCacheOversizeImg allKeys = ${mmkv.allKeys()}")
        mmkv.allKeys()?.forEach {
            val largeImageInfo = mmkv.decodeParcelable(it, LargeImageInfo::class.java)
            largeImageInfos.add(largeImageInfo)
        }
        return largeImageInfos
    }
}