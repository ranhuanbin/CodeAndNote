package com.lib.monitor.largeimage

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.module.mst.R
import kotlinx.android.synthetic.main.activity_largeimage.*

class LargeImageMonitorActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_largeimage)
//        Glide.with(this).load(R.drawable.ic_test_two).addListener(GlideLargeImageListener()).into(iv_glide)
        Glide.with(this).load(R.drawable.ic_test_two).into(iv_glide)
    }
}
