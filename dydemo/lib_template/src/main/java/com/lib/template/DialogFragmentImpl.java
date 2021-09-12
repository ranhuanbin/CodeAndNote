package com.lib.template;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
    getView().findViewById(R.id.invokeInitData).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
//        initData();
      }
    });
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_main;
  }

  @Override
  protected void initData() {
    String string = getString(R.string.app_name);
    Log.v("AndroidTest", "string = " + string);
  }

}
