package com.lib.monitor.largeimage.notify.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lib.monitor.largeimage.NewLargeImageManager;
import com.module.R;


public class SettingActivity extends AppCompatActivity {
    private Toolbar mToolBar;
    private CheckBox mMenuSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        mToolBar = findViewById(R.id.toolbar);
        mMenuSwitch = findViewById(R.id.menu_switch);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mMenuSwitch.setChecked(NewLargeImageManager.INSTANCE.isLargeImageDialogEnable());
        mMenuSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NewLargeImageManager.INSTANCE.setLargeImageDialogEnable(!NewLargeImageManager.INSTANCE.isLargeImageDialogEnable());
            }
        });
    }
}
