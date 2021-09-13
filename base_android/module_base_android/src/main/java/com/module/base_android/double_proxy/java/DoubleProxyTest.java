package com.module.base_android.double_proxy.java;

public class DoubleProxyTest {

  public static void test() {
    DemoServiceImpl demoService = new DemoServiceImpl();
    DemoServiceProxy proxy = new DemoServiceProxy();
    DemoService service = (DemoService) proxy.newProxy(demoService);
    service.saiHi("999");
  }
}
