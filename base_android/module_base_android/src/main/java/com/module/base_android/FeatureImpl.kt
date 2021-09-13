package com.module.base_android

class FeatureImpl : IFeature {
    override fun getString(num1: Int, num2: Int) {
        "【FeatureImpl.getString】num1 = $num1, num2 = $num2".println()
    }
}