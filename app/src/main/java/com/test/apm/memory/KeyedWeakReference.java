package com.test.apm.memory;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;


public class KeyedWeakReference extends WeakReference<Object> {
    public final String key;

    KeyedWeakReference(Object referent, String key, ReferenceQueue<Object> referenceQueue) {
        super(referent, referenceQueue);
        this.key = key;
    }
}
