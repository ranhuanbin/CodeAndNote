package com.methodcost.plugin.util

import org.objectweb.asm.tree.MethodNode
import org.objectweb.asm.Opcodes.*

/**
 * true: 不符合指令插入条件, 拦截. false: 符合指令插入条件, 不拦截
 * 插入代码之前进行校验, 符合条件的方法才会插入i/o指令
 * 1. MethodCostUtil类本身不执行指令插入逻辑
 * 2. 校验当前方法是否需要进行指令插入逻辑: 空方法、get/set方法、没有局部变量的简单方法
 */

object MethodVisitorUtil {
    fun insertIntercept(classname: String): Boolean {
        return if ("com/test/apm/methodcost/MethodCostUtil" == classname) {
            return true
        } else {
            false
        }
    }
}

//是否为空方法
fun MethodNode.isEmptyMethod(): Boolean {
    val iterator = instructions.iterator()
    while (iterator.hasNext()) {
        return if (-1 == iterator.next().opcode) {
            continue
        } else {
            false
        }
    }
    return true
}

//是否为get/set方法
fun MethodNode.isGetSetMethod(): Boolean {
    var ignoreCount = 0
    val iterator = instructions.iterator()
    while (iterator.hasNext()) {
        val insnNode = iterator.next()
        val opcode = insnNode.opcode
        if (-1 == opcode) {
            continue
        }
        if (opcode != GETFIELD
                && opcode != GETSTATIC
                && opcode != H_GETFIELD
                && opcode != H_GETSTATIC
                && opcode != RETURN
                && opcode != ARETURN
                && opcode != DRETURN
                && opcode != FRETURN
                && opcode != LRETURN
                && opcode != IRETURN
                && opcode != PUTFIELD
                && opcode != PUTSTATIC
                && opcode != H_PUTFIELD
                && opcode != H_PUTSTATIC
                && opcode > SALOAD) {
            if (name == "<init>" && opcode == INVOKESPECIAL) {
                ignoreCount++
                if (ignoreCount > 1) {
                    return false
                }
                continue
            }
            return false
        }
    }
    return true
}

fun MethodNode.isSingleMethod(): Boolean {
    val iterator = instructions.iterator()
    while (iterator.hasNext()) {
        val insnNode = iterator.next()
        val opcode = insnNode.opcode
        if (-1 == opcode) {
            continue
        } else if (opcode in INVOKEVIRTUAL..INVOKEDYNAMIC) {
            return false
        }
    }
    return true
}