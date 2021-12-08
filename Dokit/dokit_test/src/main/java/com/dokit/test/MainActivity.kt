package com.dokit.test

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.test.kotlin.block.BlockTest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        blockTest.setOnClickListener {
            BlockTest.test1 {
                Log.v("AndroidTest", "MainActivity===>test1")
            }

            BlockTest.test2 {
                Log.v("AndroidTest", "MainActivity===>test2")
                "1"
            }

            BlockTest.test3 { x, y ->
                Log.v("AndroidTest", "MainActivity===>test3===>x = $x, y = $y")
                x + y
            }

            BlockTest.test4 { x, y ->
                Log.v("AndroidTest", "MainActivity===>test4===>x = $x, y = $y")
            }
        }
    }
}