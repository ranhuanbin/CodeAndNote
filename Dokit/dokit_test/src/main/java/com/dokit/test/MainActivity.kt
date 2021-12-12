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
            val blockTest = BlockTest()
            blockTest.test1 {
                Log.v("AndroidTest", "MainActivity===>test1")
            }

            blockTest.test2 {
                Log.v("AndroidTest", "MainActivity===>test1")
            }

            blockTest.test3 {
                Log.v("AndroidTest", "MainActivity===>test2")
                "1"
            }

            blockTest.test4 { x, y ->
                Log.v("AndroidTest", "MainActivity===>test3===>x = $x, y = $y")
                x + y
            }

            blockTest.test5 { x, y ->
                Log.v("AndroidTest", "MainActivity===>test4===>x = $x, y = $y")
            }
        }
    }
}