package com.lib.template

import com.dywx.plugin.lib.ContextHolder
import java.lang.reflect.Field


class ReflectUtils {
    companion object {
        fun setPluginContext(classObj: Any) {
            ReflectUtils().fieldInner(classObj, "pluginContext", ContextHolder.getContext())
        }
    }

    private fun fieldInner(classObj: Any, name: String, value: Any) {
        try {
            val field = getAccessibleField(classObj, name)
            field!![classObj] = value
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun getAccessibleField(classObj: Any, name: String): Field? {
        var type: Class<*>? = classObj.javaClass
        return try {
            accessible(type!!.getField(name))
        } catch (e: NoSuchFieldException) {
            do {
                try {
                    return accessible(type!!.getDeclaredField(name))
                } catch (ignore: NoSuchFieldException) {
                }
                type = type!!.superclass
            } while (type != null)
            throw RuntimeException(e)
        }
    }

    private fun accessible(field: Field?): Field? {
        if (field == null) {
            return null
        }
        if (!field.isAccessible) {
            field.isAccessible = true
        }
        return field
    }
}