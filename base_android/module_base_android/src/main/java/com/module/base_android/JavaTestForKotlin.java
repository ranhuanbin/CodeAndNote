package com.module.base_android;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JavaTestForKotlin {

  public void dynamicProxyTest() {
    Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{IFeature.class}, new InvocationHandler() {
      @Override
      public Object invoke(Object proxy, Method method, Object[] args) {
        return null;
      }
    });
  }
}
