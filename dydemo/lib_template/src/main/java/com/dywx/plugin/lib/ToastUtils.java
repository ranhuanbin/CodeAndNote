package com.dywx.plugin.lib;

import android.widget.Toast;
import com.dywx.plugin.lib.ContextHolder;

public class ToastUtils {

  public static void show() {
    Toast.makeText(ContextHolder.getContext(), "弹窗测试", Toast.LENGTH_SHORT).show();
  }
}
