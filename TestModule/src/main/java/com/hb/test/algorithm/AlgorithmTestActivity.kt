package com.hb.test.algorithm

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.hb.test.R
import com.hb.test.algorithm.foroffer01.ForOffer01
import com.hb.test.algorithm.foroffer02.ForOffer02
import com.hb.test.algorithm.foroffer06.ForOffer06
import kotlinx.android.synthetic.main.activity_test_algorithm.*

class AlgorithmTestActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_algorithm)
        forOffer01.setOnClickListener { forOffer01() }
        forOffer02.setOnClickListener { forOffer02() }
    }

    private fun forOffer01() {
        ForOffer01.test(3)
    }
    private fun forOffer02() {
        ForOffer02.replaceBlank()
ForOffer06.forOffer06()
    }
}