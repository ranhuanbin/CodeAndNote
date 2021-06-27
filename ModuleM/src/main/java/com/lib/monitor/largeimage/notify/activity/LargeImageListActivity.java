package com.lib.monitor.largeimage.notify.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lib.monitor.largeimage.LargeImageInfo;
import com.lib.monitor.largeimage.LargeImageManager;
import com.lib.monitor.largeimage.notify.adapter.LargeImageListAdapter;
import com.module.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ================================================
 * 描    述：用列表的形式显示超标图片，只显示本次APP启动以
 * 来超标图片，不会显示历史的。
 */
public class LargeImageListActivity extends AppCompatActivity {
    private Toolbar mToolBar;
    private RecyclerView mRecyclerView;
    private LargeImageListAdapter mLargeImageAdapter;
    private List<LargeImageInfo> mLargeImageList;
    private SwipeRefreshLayout mRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_largeimage_list);
        initView();
        initData();
    }

    private void initView() {
        mToolBar = findViewById(R.id.toolbar);
        mRecyclerView = findViewById(R.id.rv_largeImage);
        mRefresh = findViewById(R.id.refresh);
        mToolBar.inflateMenu(R.menu.toolbar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_setting) {
                    Intent intent = new Intent(LargeImageListActivity.this, SettingActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Map<String, LargeImageInfo> stringLargeImageInfoMap = LargeImageManager.getInstance().getInfoCache();
                if (mLargeImageList != null && mLargeImageList.size() > 0) {
                    mLargeImageList.clear();
                }
                for (Map.Entry<String, LargeImageInfo> entry : stringLargeImageInfoMap.entrySet()) {
                    mLargeImageList.add(entry.getValue());
                }
                mLargeImageAdapter.notifyDataSetChanged();
                mRefresh.setRefreshing(false);
            }
        });
    }

    private void initData() {
        mLargeImageList = new ArrayList<>();
        Map<String, LargeImageInfo> stringLargeImageInfoMap = LargeImageManager.getInstance().getInfoCache();
        for (Map.Entry<String, LargeImageInfo> entry : stringLargeImageInfoMap.entrySet()) {
            mLargeImageList.add(entry.getValue());
            Log.v("AndroidTest", "imageInfo = " + entry.getValue());
        }
        mLargeImageAdapter = new LargeImageListAdapter(this, mLargeImageList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mLargeImageAdapter);
    }
}
