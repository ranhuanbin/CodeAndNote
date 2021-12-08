package com.dokit.test

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.test.kotlin.block.BlockTest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        blockTest.setOnClickListener {
            BlockTest.test1 {

            }
        }
    }
}