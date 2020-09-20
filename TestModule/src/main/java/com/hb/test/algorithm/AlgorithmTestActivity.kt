package com.hb.test.algorithm

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.hb.test.R
import com.hb.test.algorithm.foroffer01.ForOffer01
import com.hb.test.algorithm.foroffer02.ForOffer02
import com.hb.test.algorithm.foroffer07.ForOffer07
import com.hb.test.algorithm.foroffer08.ForOffer08
import com.hb.test.algorithm.foroffer13.ForOffer13
import com.hb.test.algorithm.foroffer18.ForOffer18
import com.hb.test.algorithm.foroffer28.ForOffer28
import kotlinx.android.synthetic.main.activity_test_algorithm.*

class AlgorithmTestActivity : FragmentActivity() {
    var TAG = "AlgorithmTestActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_algorithm)
        forOffer01.setOnClickListener { forOffer01() }
        forOffer02.setOnClickListener { forOffer02() }
        forOffer07.setOnClickListener { forOffer07() }
        forOffer08.setOnClickListener { forOffer08() }
        forOffer13.setOnClickListener { forOffer13() }
        forOffer18.setOnClickListener { forOffer18() }
        forOffer28.setOnClickListener { forOffer28() }
    }

    private fun forOffer01() {
        ForOffer01.test(3)
    }

    private fun forOffer02() {
        ForOffer02.replaceBlank()
    }

    private fun forOffer07() {
        val xunhuan = ForOffer07.xunhuan(7)
        val digui = ForOffer07.digui(7)
        Log.v("AndroidTest", "xunhuan: " + xunhuan + ", digui: " + digui)
    }

    private fun forOffer08() {
        ForOffer08.forOffer08(getInput())
    }

    private fun getInput(): Int {
        return Integer.valueOf(input.text.toString().trim())
    }

    private fun forOffer13() {
        ForOffer13.forOffer13Test();
    }

    private fun forOffer18() {
        ForOffer18.testForOffer18(getInput());
    }

    private fun forOffer28() {
        val findSubArray = ForOffer28.findSubArray(arrayOf(1, -2, 3, 10, -4, 7, 2, -5, 6))
        Log.v(TAG, "findSubArray: " + findSubArray)
    }
}