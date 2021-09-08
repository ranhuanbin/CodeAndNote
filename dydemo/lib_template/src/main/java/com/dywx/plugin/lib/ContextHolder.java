package com.dywx.plugin.lib;

import android.content.Context;

public class ContextHolder {

  private static Context context;

  public static void setContext(Context context) {
    ContextHolder.context = context;
  }

  public static Context getContext() {
    return context;
  }

  public static String getPluginName() {
    return context.getApplicationInfo().packageName;
  }
}
