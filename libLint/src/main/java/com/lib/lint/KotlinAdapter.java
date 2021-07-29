package com.lib.lint;

import com.android.tools.lint.detector.api.ApiKt;

import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UReferenceExpression;
import org.jetbrains.uast.UastUtils;
import org.jetbrains.uast.util.UastExpressionUtils;

public class KotlinAdapter {
    public static boolean isMethodCall(UCallExpression node) {
        return UastExpressionUtils.isMethodCall(node);
    }

    public static boolean isConstructorCall(UCallExpression node) {
        return UastExpressionUtils.isConstructorCall(node);
    }

    public static String getQualifiedName(UReferenceExpression classRef) {
        return UastUtils.getQualifiedName(classRef);
    }

    public static int getApi() {
        return ApiKt.CURRENT_API;
    }
}
