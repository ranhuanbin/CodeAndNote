package com.lib.monitor.largeimage.new

import android.os.Handler
import android.os.Looper
import com.lib.monitor.largeimage.LargeImage
import com.lib.monitor.largeimage.LargeImageInfo
import com.lib.monitor.largeimage.NewLargeImageManager
import com.lib.monitor.largeimage.utils.ConvertUtils
import com.lib.monitor.largeimage.utils.LargeMonitorDialog
import com.tencent.mmkv.MMKV

class NewLargeImageManager(val mmkv: MMKV = MMKV.mmkvWithID("NewLargeImageManager")) {
    private val mainHandler = Handler(Looper.getMainLooper())

    // 是否开启弹窗
    @Volatile
    var openDialog = false

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

    fun saveImageInfo(
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
            if (largeImageInfo.fileSize > LargeImage.getInstance().fileSizeThreshold) {
                largeImageInfo.width = width
                largeImageInfo.height = height
                largeImageInfo.memorySize = size
                largeImageInfo.targetWidth = viewWidth
                largeImageInfo.targetHeight = viewHeight
                mmkv.encode(imageUrl, largeImageInfo)
            } else {
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
                mmkv.encode(imageUrl, largeImageInfo)
            }
        }
        if (openDialog) {
            showAlarm(imageUrl)
        }
    }

    private fun showAlarm(imageUrl: String) {
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

    fun getInstance(): NewLargeImageManager? {
        return Holder.instance
    }

    private object Holder {
        val instance = NewLargeImageManager()
    }

}