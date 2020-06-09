package com.test.apm.leakcanary.privoder;

import android.os.SystemClock;

import com.test.apm.leakcanary.iservice.GcTrigger;

public class LeakDefaultProvider {
    public static GcTrigger defaultGcTrigger = new GcTrigger() {
        @Override
        public void runGc() {
            Runtime.getRuntime().gc();
            enqueueReferences();
            System.runFinalization();
        }

        private void enqueueReferences() {
            SystemClock.sleep(100);
        }
    };
}
