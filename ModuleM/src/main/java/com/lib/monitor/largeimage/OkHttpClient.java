package com.lib.monitor.largeimage;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;

public class OkHttpClient {
    public static final class Builder {
        final List<Interceptor> interceptors = new ArrayList<>();

        public Builder() {
            interceptors.addAll(LargeImage.getInstance().getOkHttpInterceptors());
        }
    }
}
