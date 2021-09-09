package com.lib.template;

import android.app.Application;
import android.content.Context;
import com.dywx.plugin.lib.ContextHolder;

public class MyApp extends Application {

  public static Context context;

  @Override
  public void onCreate() {
    super.onCreate();
    context = this;
    ContextHolder.setContext(this);
  }
}
