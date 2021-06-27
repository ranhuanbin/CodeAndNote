package com.lib.monitor.largeimage

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.module.R
import kotlinx.android.synthetic.main.activity_largeimage.*
import java.io.File
import java.util.*

class LargeImageMonitorActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_largeimage)
//        Glide.with(this).load(R.drawable.ic_test_two).addListener(GlideLargeImageListener()).into(iv_glide)

        Glide.with(this).load(R.drawable.ic_test_two).into(img1)

        val sdFile =
            "" + Environment.getExternalStorageDirectory() + File.separator + "ic_test_two.jpg"

//        Log.v("AndroidTest", "file = ${File(sdFile)}, exist = ${File(sdFile).exists()}")
        Glide.with(this)
            .load(sdFile)
            .into(img2)
    }
}
