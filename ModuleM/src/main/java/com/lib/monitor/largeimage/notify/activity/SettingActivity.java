package com.lib.monitor.largeimage.notify.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lib.monitor.largeimage.LargeImageManager;
import com.module.mst.R;


/**
 * ================================================
 * 描    述：设置界面
 * ================================================
 */
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
        mMenuSwitch.setChecked(LargeImageManager.getInstance().isOpenDialog());
        mMenuSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LargeImageManager.getInstance().setOpenDialog(!LargeImageManager.getInstance().isOpenDialog());
            }
        });
    }
}
