package com.lib.template;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

public class MainActivity extends FragmentActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    new FragImpl().show(getSupportFragmentManager(), "12345");
  }
}
