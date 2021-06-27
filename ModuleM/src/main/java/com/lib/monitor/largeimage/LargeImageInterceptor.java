package com.lib.monitor.largeimage;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LargeImageInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 发出请求时不拦截
        Request request = chain.request();
        Response response = chain.proceed(request);
        // 拦截响应
        String header = response.header("Content-Type");
        // 如果是图片类型则拦截
        if (isImage(header)) {
            process(response);
        }
        return response;
    }

    private void process(Response response) {
        String header = response.header("Content-Length");
        if (!TextUtils.isEmpty(header)) {
            try {
                LargeImageManager.getInstance().saveImageInfo(response.request().url().toString(), Integer.parseInt(header));
            } catch (Exception e) {

            }
        }
    }

    private boolean isImage(String contentType) {
        return stripContentExtras(contentType).startsWith("image");
    }

    private String stripContentExtras(String contentType) {
        int index = contentType.indexOf(";");
        return (index >= 0) ? contentType.substring(0, index) : contentType;
    }
}
