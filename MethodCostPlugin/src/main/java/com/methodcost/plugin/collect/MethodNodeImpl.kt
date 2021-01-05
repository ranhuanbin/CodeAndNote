package com.methodcost.plugin.collect

import com.methodcost.plugin.util.*
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.MethodNode


class MethodNodeImpl(var className: String, access: Int, name: String?, desc: String?,
                     signature: String?, exceptions: Array<out String>?)
    : MethodNode(Opcodes.ASM6, access, name, desc, signature, exceptions) {
    override fun visitEnd() {
        super.visitEnd()
        println("[MethodNodeImpl.visitEnd] [1] [className = $className, name = $name]")
        // 如果是构造方法
        if ("<init>" == name) {
            println("[MethodNodeImpl.visitEnd] [2] [className = $className, name = $name]")
            return
        }
        /**
         * 1、忽略空方法
         * 2、忽略get/set方法
         * 3、忽略没有局部变量的简单方法
         *
         * 这个地方目前有点疑问, 测试方法居然在inSingleMethod判断时, 返回了true, 导致插桩执行到这里时
         * 直接走了return方法, 所以为了方便测试, 这里先把isSingleMethod注释掉, 后续如果有时间再回过来
         * 搞
         */
        if (isEmptyMethod() || isGetSetMethod()) {
//                || isSingleMethod()) {
            println("[MethodNodeImpl.visitEnd] [3] [className = $className, name = $name]")
            return
        }
        println("[MethodNodeImpl.visitEnd] [4] [className = $className, name = $name]")
        MethodCollector.addMethod(className, name)
    }
}