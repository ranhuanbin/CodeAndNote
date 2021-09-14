package com.module.base_android.feature;

import android.util.Log;
import com.module.base_android.utils.LogUtils;

//public class FeatureImpl implements IFeature, ITestInterface {
public class FeatureImpl implements AbstractFeature, ITestInterface {

  @Override
  public void test() {
    LogUtils.debug_v("【FeatureImpl test】");
  }

  @Override
  public String getString(int a, int b) {
    Log.v("AndroidTest", "【FeatureImpl getString】");
    ExceptionTest.exceptionTest();
    return "" + (1 + 2);
  }

  @Override
  public void testInterface() {
    LogUtils.debug_v("【FeatureImpl】testInterface");
  }

  private static class ExceptionTest {

    public static void exceptionTest() {
      if (true) {
        throw new RuntimeException("运行时崩溃");
      }
    }
  }
}
