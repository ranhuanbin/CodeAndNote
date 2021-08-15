package com.module.jetpack

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class FragmentA : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModelA = activity?.let {
            ViewModelProvider(it).get(ViewModelA::class.java)
        }
        viewModelA?.getCount()
    }
}