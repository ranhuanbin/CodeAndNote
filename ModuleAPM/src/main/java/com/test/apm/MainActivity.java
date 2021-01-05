package com.test.apm;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findLengthOfLCIS(new int[]{1,3,5,4,7});
            }
        });
    }

    public int findLengthOfLCIS(int[] nums) {
        int interval = 0;
        for (int i = 0, j = 1; j < nums.length; j++) {
            if (nums[j] > nums[j - 1]) {
                if ((j - i) > interval) {
                    interval = j - i;
                }
            } else {
                i = j;
            }
        }
        return interval;
    }
}
