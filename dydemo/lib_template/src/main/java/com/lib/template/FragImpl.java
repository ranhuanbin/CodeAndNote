package com.lib.template;

import android.util.Log;
import com.demo.plugin2.R;
import com.dywx.plugin.lib.BaseDialogFragment;

public class FragImpl extends BaseDialogFragment {

  public FragImpl() {
  }

  @Override
  protected void initView() {
    Log.v("AndroidTest", "context = " + getPluginContext());
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_main;
  }

}
