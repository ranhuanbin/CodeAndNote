package com.module.base_android.double_proxy.java;

import android.util.Log;
import com.module.base_android.feature.AbstractFeature;
import com.module.base_android.feature.FeatureImpl;
import com.module.base_android.feature.FeatureProxy;
import com.module.base_android.feature.IFeature;

public class DoubleProxyTest {

  public static void test() {
    DemoServiceImpl demoService = new DemoServiceImpl();
    DemoServiceProxy proxy = new DemoServiceProxy();
    DemoService service = (DemoService) proxy.newProxy(demoService);
    service.saiHi("999");
  }

  @SuppressWarnings("unchecked")
  public static <T extends IFeature> T getFeature() {
    FeatureProxy proxy = new FeatureProxy();
    FeatureImpl featureImpl = new FeatureImpl();
    return (T) proxy.newProxy(featureImpl);
  }

  public static void test1() {
    FeatureProxy proxy = new FeatureProxy();
    FeatureImpl featureImpl = new FeatureImpl();
//    IFeature feature = (IFeature) proxy.newProxy(featureImpl);
    AbstractFeature feature = (AbstractFeature) proxy.newProxy(featureImpl);
//    IFeature feature = (IFeature) proxy.newProxy(sFeature);
    feature.getString(1, 2);
    feature.test();
  }

  private static IFeature sFeature = new IFeature() {
    @Override
    public String getString(int a, int b) {
      Log.v("AndroidTest", "【DoubleProxyTest getString 1】");
      return "a = " + a + ", b = " + b;
    }
  };
}
