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
            memorySize = ConvertUtils.byte2MemorySize(resource.byteCount.toLong(), ConvertUtils.KB)
            LargeImageManager.getInstance()
                .transform(model.toString(), resource as Bitmap, "Glide", width, height)
        } else if (resource is BitmapDrawable) {
            imageWidth = (resource as BitmapDrawable).intrinsicWidth
            imageHeight = (resource as BitmapDrawable).intrinsicHeight
            memorySize =
                ConvertUtils.byte2MemorySize(
//                    (imageWidth * imageHeight * (if (resource.opacity != PixelFormat.OPAQUE) 4 else 2)).toLong(),
                    resource.bitmap.byteCount.toLong(),
                    ConvertUtils.KB
                )
//            LargeImageManager.getInstance()
//                .transform(model.toString(), resource as BitmapDrawable, "Glide", width, height)
        }
        Log.v(
            "AndroidTest", "[onResourceReady] " +
                    "\n{" +
                    "\n\timageUrl = ${model.toString()}" +
                    "\n\tsize = $memorySize" +
                    "\n\timageWidth = $imageWidth" +
                    "\n\timageHeight = $imageHeight" +
                    "\n\tviewWidth = $width" +
                    "\n\tviewHeight = $height" +
                    "\n}"
        )
        return false
    }
}
