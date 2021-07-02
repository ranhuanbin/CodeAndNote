package com.didichuxing.doraemonkit.aop.method_stack

import android.app.Application
import android.util.Log
import com.didichuxing.doraemonkit.aop.MethodCostUtil
import com.didichuxing.doraemonkit.aop.MethodCostUtil.TAG
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object MethodStackUtil {

    private val METHOD_STACKS: MutableList<ConcurrentHashMap<String, MethodInvokNode>> by lazy {
        Collections.synchronizedList(mutableListOf<ConcurrentHashMap<String, MethodInvokNode>>())
    }

    fun recodeObjectMethodCostStart(totalLevel: Int, thresholdTime: Int, currentLevel: Int, className: String?, methodName: String, desc: String?, classObj: Any?) {
        try {
            Log.v(TAG, "recodeObjectMethodCostStart")
            //先创建队列
            createMethodStackList(totalLevel)
            val methodInvokNode = MethodInvokNode()
            methodInvokNode.startTimeMillis = System.currentTimeMillis()
            methodInvokNode.currentThreadName = Thread.currentThread().name
            methodInvokNode.className = className
            methodInvokNode.methodName = methodName
            methodInvokNode.level = currentLevel
            METHOD_STACKS[currentLevel][String.format("%s&%s", className, methodName)] = methodInvokNode
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun recodeObjectMethodCostEnd(thresholdTime: Int, currentLevel: Int, className: String, methodName: String, desc: String?, classObj: Any?) {
        synchronized(MethodCostUtil::class.java) {
            try {
                val methodInvokNode = METHOD_STACKS[currentLevel][String.format("%s&%s", className, methodName)]
                if (methodInvokNode != null) {
                    methodInvokNode.setEndTimeMillis(System.currentTimeMillis())
                    bindNode(thresholdTime, currentLevel, methodInvokNode)
                }

                //打印函数调用栈
                if (currentLevel == 0) {
                    if (methodInvokNode != null) {
                        toStack(classObj is Application, methodInvokNode)
                    }
                    //移除对象
                    METHOD_STACKS[0].remove("$className&$methodName")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toStack(isAppStart: Boolean, methodInvokNode: MethodInvokNode) {
        val stringBuilder = StringBuilder()
        stringBuilder.append("=========DoKit函数调用栈==========").append("\n")
        stringBuilder.append(String.format("%s    %s    %s", "level", "time", "function")).append("\n")
        stringBuilder.append(String.format("%s%s%s%s%s", methodInvokNode.level, SPACE_0, methodInvokNode.getCostTimeMillis().toString() + "ms", getSpaceString(methodInvokNode.level), methodInvokNode.className + "&" + methodInvokNode.methodName)).append("\n")
        stackTravel(stringBuilder, methodInvokNode.children)
        Log.v(TAG, stringBuilder.toString())
        if (isAppStart && methodInvokNode.level == 0) {
            if (methodInvokNode.methodName == "onCreate") {
                STR_APP_ON_CREATE = stringBuilder.toString()
            }
            if (methodInvokNode.methodName == "attachBaseContext") {
                STR_APP_ATTACH_BASECONTEXT = stringBuilder.toString()
            }
        }
    }

    var STR_APP_ON_CREATE: String? = null
    var STR_APP_ATTACH_BASECONTEXT: String? = null
    private fun stackTravel(stringBuilder: StringBuilder, methodInvokNodes: List<MethodInvokNode>?) {
        if (methodInvokNodes == null) {
            return
        }
        for (methodInvokNode in methodInvokNodes) {
            stringBuilder.append(String.format("%s%s%s%s%s", methodInvokNode.level, SPACE_0, methodInvokNode.getCostTimeMillis().toString() + "ms", getSpaceString(methodInvokNode.level), methodInvokNode.className + "&" + methodInvokNode.methodName)).append("\n")
            stackTravel(stringBuilder, methodInvokNode.children)
        }
    }

    private fun bindNode(thresholdTime: Int, currentLevel: Int, methodInvokNode: MethodInvokNode?) {
        if (methodInvokNode == null) {
            return
        }

        //过滤掉小于指定阈值的函数
        if (methodInvokNode.getCostTimeMillis() <= thresholdTime) {
            return
        }
        if (currentLevel >= 1) {
            val parentMethodNode = METHOD_STACKS[currentLevel - 1][getParentMethod(methodInvokNode.className, methodInvokNode.methodName)]
            if (parentMethodNode != null) {
                methodInvokNode.parent = parentMethodNode
                parentMethodNode.addChild(methodInvokNode)
            }
        }
    }

    private fun getSpaceString(level: Int): String {
        return when (level) {
            0 -> SPACE_0
            1 -> SPACE_1
            2 -> SPACE_2
            3 -> SPACE_3
            4 -> SPACE_4
            5 -> SPACE_5
            6 -> SPACE_6
            7 -> SPACE_7
            else -> SPACE_0
        }
    }

    private fun getParentMethod(currentClassName: String?, currentMethodName: String?): String {
        val stackTraceElements = Thread.currentThread().stackTrace
        var index = 0
        for (i in stackTraceElements.indices) {
            val stackTraceElement = stackTraceElements[i]
            if (currentClassName == stackTraceElement.className && currentMethodName == stackTraceElement.methodName) {
                index = i
                break
            }
        }
        val parentStackTraceElement = stackTraceElements[index + 1]
        return String.format("%s&%s", parentStackTraceElement.className, parentStackTraceElement.methodName)
    }

    private fun createMethodStackList(totalLevel: Int) {
        if (METHOD_STACKS.size == totalLevel) {
            return
        }
        METHOD_STACKS.clear()
        for (index in 0 until totalLevel) {
            METHOD_STACKS.add(index, ConcurrentHashMap())
        }
    }

    private const val SPACE_0 = "********"
    private const val SPACE_1 = "*************"
    private const val SPACE_2 = "*****************"
    private const val SPACE_3 = "*********************"
    private const val SPACE_4 = "*************************"
    private const val SPACE_5 = "*****************************"
    private const val SPACE_6 = "*********************************"
    private const val SPACE_7 = "*************************************"
}