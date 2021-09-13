package com.module.base_android.double_proxy.java;

public class DemoServiceImpl implements DemoService {

  @Override
  public void saiHi(String name) {
    System.out.println("hi:" + name);
  }
}