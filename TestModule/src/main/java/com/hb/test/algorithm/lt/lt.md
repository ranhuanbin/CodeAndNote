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

###### lt04
判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
[https://leetcode-cn.com/problems/palindrome-number/](https://leetcode-cn.com/problems/palindrome-number/)

###### lt05
编写一个函数来查找字符串数组中的最长公共前缀。
如果不存在公共前缀，返回空字符串 ""。
[https://leetcode-cn.com/problems/longest-common-prefix/](https://leetcode-cn.com/problems/longest-common-prefix/)

###### lt06(循环、递归)
将两个升序链表合并为一个新的升序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
[https://leetcode-cn.com/problems/merge-two-sorted-lists/](https://leetcode-cn.com/problems/merge-two-sorted-lists/)

###### lt07(快慢指针模式, LeetCode上面排名第一的解题思路牛皮)
给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
[https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array/](https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array/)

###### lt08(双指针模式, 与lt07非常类型)
[https://leetcode-cn.com/problems/remove-element/](https://leetcode-cn.com/problems/remove-element/)

**`特点: `** 
&emsp;&emsp;这种题与lt07非常类似, 双指针模式, 而且都是删除数组中指定元素, 最终解法用的是虚拟数组, 装入符合条件的元素.

**`描述: `** 
&emsp;&emsp;给你一个数组nums和一个值val，你需要原地移除所有数值等于val的元素，并返回移除后数组的新长度。
不要使用额外的数组空间，你必须仅使用O(1)额外空间并原地修改输入数组。
元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。


###### lt09(滑动窗口)
[https://leetcode-cn.com/problems/implement-strstr/](https://leetcode-cn.com/problems/implement-strstr/)

**`描述: `** 
&emsp;&emsp;给定一个haystack字符串和一个needle字符串，在haystack字符串中找出needle字符串出现的第一个位置 (从0开始)。如果不存在，则返回-1。

###### lt10(二分查找注意边界问题)
[https://leetcode-cn.com/problems/search-insert-position/](https://leetcode-cn.com/problems/search-insert-position/)

给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
你可以假设数组中无重复元素。

###### lt11(动态规划)
[https://leetcode-cn.com/problems/maximum-subarray/](https://leetcode-cn.com/problems/maximum-subarray/)

给定一个整数数组nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。

###### lt12
[https://leetcode-cn.com/problems/length-of-last-word/](https://leetcode-cn.com/problems/length-of-last-word/)
给定一个仅包含大小写字母和空格 ' ' 的字符串 s，返回其最后一个单词的长度。如果字符串从左向右滚动显示，那么最后一个单词就是最后出现的单词。
如果不存在最后一个单词，请返回 0 。
说明：一个单词是指仅由字母组成、不包含任何空格字符的 最大子字符串。

###### lt13(进一的问题, 非常经典, 自增求余==0, 说明自增)
[https://leetcode-cn.com/problems/plus-one/](https://leetcode-cn.com/problems/plus-one/)
给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
最高位数字存放在数组的首位，数组中每个元素只存储单个数字。
你可以假设除了整数0之外，这个整数不会以零开头。

###### lt14(for循环中可以设置多个变量, char运算与int的关系)
[https://leetcode-cn.com/problems/add-binary/](https://leetcode-cn.com/problems/add-binary/)
给你两个二进制字符串，返回它们的和（用二进制表示）。
输入为非空字符串且只包含数字1和0。

##### lt15(这道题需要注意的是第二种解法, 注意链表尾节点问题)
[https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/](https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/)
给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。





