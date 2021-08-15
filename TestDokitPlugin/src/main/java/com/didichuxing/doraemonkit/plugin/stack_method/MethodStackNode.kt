package com.didichuxing.doraemonkit.plugin.stack_method

data class MethodStackNode(var level: Int,
                           var className: String,
                           var methodName: String,
                           var desc: String,
                           var parentClassName: String,
                           var parentMethodName: String,
                           var parentDesc: String)