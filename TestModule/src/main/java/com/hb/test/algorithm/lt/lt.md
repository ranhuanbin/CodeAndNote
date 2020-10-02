#### 算法涉及模式
##### 1.原地链表翻转
翻转链表中某一段的节点, 通常要求都是原地翻转, 就是重复使用这些已经建好的节点, 而不使用额外的空间.
这种模式每次就翻转一个节点, 一般需要用到多个变量, 一个变量指向头结点(下图中的current), 另外一个(previous)则指向刚刚处理完的那个节点, 
在这种固定步长的方式下, 你需要先将当前节点(current)指向前一个节点(previous), 再移动到下一个, 同时, 需要将previous总是更新到你刚刚处理完的节点, 以保证正确性



###### lt01
给定一个整数数组 arrs 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
[https://leetcode-cn.com/problems/two-sum/](https://leetcode-cn.com/problems/two-sum/)

###### lt02
给定字符串J代表石头中宝石的类型，和字符串S代表你拥有的石头。S中每个字符代表了一种你拥有的石头的类型，你想知道你拥有的石头中有多少是宝石。
[https://leetcode-cn.com/problems/jewels-and-stones/](https://leetcode-cn.com/problems/jewels-and-stones/)

###### lt03(原地反转模式)
给出一个32位的有符号整数，你需要将这个整数中每位上的数字进行反转。
[https://leetcode-cn.com/problems/reverse-integer/](https://leetcode-cn.com/problems/reverse-integer/)

