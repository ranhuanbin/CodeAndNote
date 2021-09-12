package com.dywx.plugin.lib;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public abstract class BaseDialogFragment extends DialogFragment {

  private View rootView;

  private Context pluginContext;

  protected Context getPluginContext() {
    return pluginContext;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    int layoutId = getLayoutId();
    inflater = LayoutInflater.from(ContextHolder.getContext());
    rootView = inflater.inflate(layoutId, null);
    initView();
    initData();
    return rootView;
  }

  protected View getRootView() {
    return rootView;
  }


  protected abstract void initView();

  protected void initData() {

//getText();
//getString();

  }

  @LayoutRes
  protected abstract int getLayoutId();

}
