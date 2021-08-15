package com.module.jetpack;

import androidx.lifecycle.ViewModel;

public class ViewModelA extends ViewModel {
    public int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
