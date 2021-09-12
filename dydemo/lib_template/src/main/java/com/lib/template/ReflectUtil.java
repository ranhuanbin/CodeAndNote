package com.lib.template;

import com.dywx.plugin.lib.ContextHolder;
import java.lang.reflect.Field;

public class ReflectUtil {

  public static void setPluginContext(Object classObj) {
    new ReflectUtil().field(classObj, "pluginContext", ContextHolder.getContext());
  }

  private void field(Object classObj, String name, Object value) {
    try {
      Field field = getAccessibleField(classObj, name);
      field.set(classObj, value);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private Field getAccessibleField(Object classObj, String name) {
    Class<?> type = classObj.getClass();
    try {
      return accessible(type.getField(name));
    } catch (NoSuchFieldException e) {
      do {
        try {
          return accessible(type.getDeclaredField(name));
        } catch (NoSuchFieldException ignore) {
        }
        type = type.getSuperclass();
      } while (type != null);
      throw new RuntimeException(e);
    }
  }

  private Field accessible(Field field) {
    if (field == null) {
      return null;
    }
    if (!field.isAccessible()) {
      field.setAccessible(true);
    }
    return field;
  }
}
