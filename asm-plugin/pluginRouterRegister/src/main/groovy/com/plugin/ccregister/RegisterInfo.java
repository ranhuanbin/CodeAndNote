package com.plugin.ccregister;

import org.apache.http.util.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class RegisterInfo {
    public static final String PARAM_TYPE_OBJECT = "object";
    public static final String PARAM_TYPE_CLASS = "class";
    public static final String PARAM_TYPE_CLASS_NAME = "string";

    static final String[] DEFAUT_EXCLUDE = new String[]{".*/R(\\$[^/]*)?"
            , ".*/BuildConfig$"};

    String interfaceName = "";
    List<String> superClassNames = new ArrayList<>();
    String initClassName = "";
    String initMethodName = "";
    String registerClassName = "";
    String registerMethodName = "";

    String paramType = "";
    List<String> include = new ArrayList<>();
    List<String> exclude = new ArrayList<>();

    // 不可配置的参数
    List<Pattern> includePatterns = new ArrayList<>();
    List<Pattern> excludePatterns = new ArrayList<>();
    File fileContainsInitClass;
    Set<String> classList = new HashSet<>();


    public void reset() {
        fileContainsInitClass = null;
        classList.clear();
    }

    public boolean validate() {
        return !TextUtils.isEmpty(interfaceName)
                && !TextUtils.isEmpty(registerClassName)
                && !TextUtils.isEmpty(registerMethodName);
    }

    public void init() {
        if (include == null) include = new ArrayList<>();
        if (include.isEmpty()) include.add(".*");
        if (exclude == null) exclude = new ArrayList<>();
        if (!TextUtils.isEmpty(registerClassName)) {
            registerClassName = initClassName;
        }
    }
}
