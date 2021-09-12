package com.lib.template;

import android.util.Log;
import com.demo.plugin2.R;
import com.dywx.plugin.lib.BaseDialogFragment;

public class DialogFragmentImpl extends BaseDialogFragment {

  public DialogFragmentImpl() {
    super();
    Log.v("AndroidTest", "DialogFragmentImpl");
  }

  @Override
  protected void initView() {
//    ReflectUtils.Companion.setPluginContext(this);
    Log.v("AndroidTest", "context = " + getPluginContext());
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override
  protected void initData() {

  }
}
