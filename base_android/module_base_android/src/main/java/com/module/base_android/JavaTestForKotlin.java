package com.module.base_android;

import android.util.Log;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class JavaTestForKotlin {

  public static void dynamicProxyTest() {
    IFeature feature = (IFeature) Proxy.newProxyInstance(JavaTestForKotlin.class.getClassLoader(), new Class<?>[]{IFeature.class}, new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) {
        Log.v("AndroidTest", "method = " + method + ", args = " + Arrays.toString(args));
        return 1;
      }
    });
    feature.getString(1, 2);
  }
}
