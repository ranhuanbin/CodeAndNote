package com.hb.test.algorithm

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.hb.test.R
import com.hb.test.algorithm.ff.ff01.ForOffer01
import com.hb.test.algorithm.ff.ff02.ForOffer02
import com.hb.test.algorithm.ff.foroffer07.ForOffer07
import com.hb.test.algorithm.ff.foroffer08.ForOffer08
import com.hb.test.algorithm.ff.foroffer13.ForOffer13
import com.hb.test.algorithm.ff.foroffer18.ForOffer18
import com.hb.test.algorithm.ff.foroffer28.ForOffer28
import com.hb.test.algorithm.lt.lt04.Lt04
import com.hb.test.algorithm.lt.lt07.Lt07
import com.hb.test.algorithm.lt.lt10.Lt10
import com.hb.test.algorithm.lt.lt14.Lt14
import com.hb.test.algorithm.lt.lt15.Lt15
//import kotlinx.android.synthetic.main.activity_test_algorithm.*

class AlgorithmTestActivity : FragmentActivity() {
    var TAG = "AlgorithmTestActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_algorithm)
//        forOffer01.setOnClickListener { forOffer01() }
//        forOffer02.setOnClickListener { forOffer02() }
//        forOffer07.setOnClickListener { forOffer07() }
//        forOffer08.setOnClickListener { forOffer08() }
//        forOffer13.setOnClickListener { forOffer13() }
//        forOffer18.setOnClickListener { forOffer18() }
//        forOffer28.setOnClickListener { forOffer28() }
//        lt04.setOnClickListener { lt04() }
//        lt07.setOnClickListener { lt07() }
//        lt10.setOnClickListener { lt10() }
//        lt14.setOnClickListener { lt(14) }
//        lt15.setOnClickListener { lt(15) }
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
        return 1
//        return Integer.valueOf(input.text.toString().trim())
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

    private fun lt04() {
        Lt04.testLt04(121)
    }

    private fun lt07() {
        Lt07.testForLt07()
    }

    private fun lt10() {
        Lt10.testForLt10()
    }

    private fun lt(index: Int) {
        SystemClock.sleep(1000);
        if (index == 14) {
            Lt14.testForLt14()
        } else if (index == 15) {
            Lt15.testForLt15();
        }
    }
}