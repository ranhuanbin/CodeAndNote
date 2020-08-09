package com.hb.test.algorithm

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.hb.test.R
import com.hb.test.algorithm.foroffer01.ForOffer01
import kotlinx.android.synthetic.main.activity_test_algorithm.*

class AlgorithmTestActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_algorithm)
        forOffer01.setOnClickListener { forOffer01() }
    }

    private fun forOffer01() {
        ForOffer01.test(3);
    }
}