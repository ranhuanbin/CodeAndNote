package com.module.base_android.double_proxy.java;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicInteger;

public class DemoServiceProxy implements InvocationHandler {

  private static final AtomicInteger INTEGER = new AtomicInteger(1);
  private Object target;
  private Integer in;

  public DemoServiceProxy() {
    in = INTEGER.getAndIncrement();
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Object object = method.invoke(target, args);
    return object;
  }

  public Object newProxy(Object target) {
    this.target = target;
    return Proxy.newProxyInstance(this.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
  }
}

