package com.module.base_android.feature;

import com.module.base_android.utils.LogUtils;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class FeatureProxy implements InvocationHandler {

  private IFeature feature;
  private final Set<Class<?>> featuresSet = new HashSet<>();

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) {
    Object obj = null;
    try {
      obj = method.invoke(feature, args);
    } catch (Throwable e) {
      LogUtils.debug_v("【FeatureProxy invoke】e = " + e.getCause());
    }
    return obj;
  }

  public Object newProxy(IFeature feature) {
    featuresSet.clear();
    this.feature = feature;
    collectAllInterface(feature.getClass());
    LogUtils.debug_v("feature = " + feature + "，feature.getInterfaces = " + Arrays.toString(featuresSet.toArray()));
    return Proxy.newProxyInstance(feature.getClass().getClassLoader(), convert(), this);
  }

  /**
   * 搜集 IFeature 类型的所有 Interface
   */
  private void collectAllInterface(Class<?> aClass) {
    if (aClass == null || aClass.getName().equals("java.lang.object")) {
      return;
    }
    Class<?> superclass = aClass.getSuperclass();
    if (superclass != null && IFeature.class.isAssignableFrom(superclass)) {
      // 地柜查找 IFeature
      collectAllInterface(aClass.getSuperclass());
    }
    // 迭代查找 IFeature
    collectAllInterfaceByIteration(aClass);
  }

  /**
   * 迭代查找所有父接口，找到目标 IFeature 接口
   */
  private void collectAllInterfaceByIteration(Class<?> clazz) {
    if (clazz == null || clazz.getInterfaces() == null || clazz.getInterfaces().length <= 0) {
      return;
    }
    LinkedList<Class<?>> queue = new LinkedList<>(Arrays.asList(clazz.getInterfaces()));
    while (!queue.isEmpty()) {
      int size = queue.size();
      while (size != 0) {
        Class<?> poll = queue.poll();
        if (poll != null) {
          if (poll.getInterfaces() != null && poll.getInterfaces().length > 0) {
            Collections.addAll(queue, poll.getInterfaces());
          }
          if (IFeature.class.isAssignableFrom(poll)) {
            featuresSet.add(poll);
          }
        }
        size--;
      }
    }
  }

  private Class<?>[] convert() {
    Iterator<Class<?>> iterator = featuresSet.iterator();
    Class<?>[] classes = new Class[featuresSet.size()];
    int index = 0;
    while (iterator.hasNext()) {
      classes[index++] = iterator.next();
    }
    return classes;
  }
}
