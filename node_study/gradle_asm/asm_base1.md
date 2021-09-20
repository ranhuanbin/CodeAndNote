# ASM的优势
* 1、内存占用很小
* 2、运行速度非常快
* 3、操作灵活：对于字节码的操作非常地灵活，可以进行插入、删除、修改等操作。
* 4、社区牛

## 两种模型
对于ASM而言，它提供了两种模型：对象模型和事件模型

# ASM的对象模型（ASM Tree API）
对象模型的本质是一个被封装过后的事件模型，它使用了树状图的形式来描述一个类，其中包含多个节点，例如方法节点、字段节点等等，而每个节点又有子节点，例如方法节点中有操作字节码节点等。

## 优点
* （1）适宜处理简单类的修改
* （2）学习成本较低
* （3）代码量较少

## 缺点
* （1）处理大量信息会使代码变得复杂
* （2）代码难以复用

在对象模型下的 ASM 有两类操作维度，分别如下所示：
* （1）获取节点：获取指定类、字段、方法节点；
* （2）操控操作码（针对方法节点）：获取操作码位置、替换、删除、插入操作码、输出字节码。

## 获取节点
###（1）指定类的节点
```java
ClassNode node = new ClassNode();
ClassReader classReader = new ClassReader(bytes);
classReader.accept(classNode, 0);
```
在注释1处，将字节数组传入到一个新创建的 ClassReader，这时 ASM 会使用 ClassReader 来解析字节码。接着，在注释2处，ClassReader 在解析完字节码之后便可以通过 accept 方法来讲结果写入到一个 ClassNoder 对象之中。

那么一个 ClassNode 具体又包含哪些信息呢？

### 类节点信息
|类型|名称|说明|
|-|-|-|
|int|version|class文件的major版本（编译Java的版本）|
|int|access|访问级|
|String|name|类名，采用全地址，如java/lang/String|
|String|signature|签名，通常是null|
|String|superName|父类类名，采用全地址|
|List|interfaces|实现的接口，采用全地址|
|String|sourceFile|源文件，可能为null|
|String|sourceDebug|debug源，可能为null|
|String|outerClass|外部类|
|String|outerMethod|外部方法|
|String|outerMethodDesc|外部方法描述（包括方法参数和返回值）|
|List|visibleAnnotations|可见的注解|
|List|invisibleAnnotations|**`不可见的注解`**|
|List|attrs|类的Attribute|
|List|innerClasses|类的内部类列表|
|List|fields|类的字段列表|
|List|methods|类的方法列表|

### 获取指定字段的节点
获取一个字段节点的代码如下：
```java
for (FieldNode fieldNode : (List)classNode.fields) {
  // 1
  if (fieldNode.name.equals("password")) {
    // 2
    fieldNode.access = Opcodes.ACC_PUBLIC;  
  }  
}
```
字段节点列表 fields 是一个 ArrayList，它存储着类节点的所有字段。在注释1处，通过遍历fields 集合的方式来找到目标字段节点。接着，在注释2处，将目标字段节点的访问权限置位 public。

### 字段信息
|类型|名称|说明|
|-|-|-|
|int|access|访问级|
|String|name|字段名|
|String|signature|签名，通常是 null|
|String|desc|类型描述，例如 Ljava/lang/String，D（double）、F（float）|
|Object|value|初始值，通常为 null|
|List|visibleAnnotations|可见的注解|
|List|invisibleAnnotations|不可见的注解|
|List|attrs|字段的Attribute|



















