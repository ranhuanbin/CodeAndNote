package com.lib.monitor.largeimage

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.target.ViewTarget
import com.lib.monitor.largeimage.utils.ConvertUtils

class LargeImageListener<R> : RequestListener<R> {
    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<R>?,
        isFirstResource: Boolean
    ): Boolean {
        Log.v("AndroidTest", "onLoadFailed e = $e")
        return false
    }

    override fun onResourceReady(
        resource: R,
        model: Any,
        target: Target<R>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ): Boolean {
        var width = 0
        var height = 0
        if (target is ViewTarget<*, *>) {
            val view = (target as ViewTarget<*, *>).view
            width = view.width
            height = view.height
        }
        var imageWidth = 0
        var imageHeight = 0
        var memorySize = 0.0
        if (resource is Bitmap) {
            imageWidth = resource.width
            imageHeight = resource.height
            memorySize = resource.byteCount.toDouble()
//            LargeImageManager.getInstance()
//                .transform(model.toString(), resource as Bitmap, "Glide", width, height)
        } else if (resource is BitmapDrawable) {
            imageWidth = (resource as BitmapDrawable).intrinsicWidth
            imageHeight = (resource as BitmapDrawable).intrinsicHeight
            memorySize = resource.bitmap.byteCount.toDouble()
//            LargeImageManager.getInstance()
//                .transform(model.toString(), resource as BitmapDrawable, "Glide", width, height)
        }
        NewLargeImageManager.process(
            model.toString(),
            imageWidth,
            imageHeight,
            width,
            height,
            memorySize
        )
        Log.v(
            "AndroidTest", "[onResourceReady] " +
                    "\n{" +
                    "\n\timageUrl = $model" +
                    "\n\tsize = ${
                        ConvertUtils.byte2MemorySize(
                            memorySize.toLong(),
                            ConvertUtils.KB
                        )
                    }" +
                    "\n\timageWidth = $imageWidth" +
                    "\n\timageHeight = $imageHeight" +
                    "\n\tviewWidth = $width" +
                    "\n\tviewHeight = $height" +
                    "\n}"
        )
        return false
    }
}
