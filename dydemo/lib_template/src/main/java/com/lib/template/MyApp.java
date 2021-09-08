package com.lib.template;

import android.app.Application;
import com.dywx.plugin.lib.ContextHolder;

public class MyApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    ContextHolder.setContext(this);
  }
}
