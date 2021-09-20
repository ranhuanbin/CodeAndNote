package com.dikit.test;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.testSlowMethod).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        testSlowMethod();
      }
    });
    String[] split = "".split("");
  }

}
