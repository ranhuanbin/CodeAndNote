package com.lib.template;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.demo.plugin2.R;

public class MainActivity extends FragmentActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    findViewById(R.id.showDialogFragment).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        new DialogFragmentImpl().show(getSupportFragmentManager(), "12345");
      }
    });
  }
}
