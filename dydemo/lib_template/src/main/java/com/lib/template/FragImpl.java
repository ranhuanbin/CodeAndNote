package com.lib.template;

import android.util.Log;
import com.demo.plugin2.R;
import com.dywx.plugin.lib.BaseDialogFragment;
import java.lang.reflect.Method;

public class FragImpl extends BaseDialogFragment {

  public FragImpl() {
    super();
  }

  @Override
  protected void initView() {
    Log.v("AndroidTest", "context = " + getPluginContext());
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override
  protected void initData() {

    getText(R.string.app_name);

    MyApp.context.getText(R.string.app_name);

    Class<? extends FragImpl> aClass = getClass();
    try {
      Method resourcesMethod = aClass.getDeclaredMethod("getResources");
      resourcesMethod.setAccessible(true);
      Log.v("AndroidTest", "resourceMethod = " + resourcesMethod);
    } catch (Exception e) {
      Log.v("AndroidTest", "exception = " + e);
    }
  }
}
