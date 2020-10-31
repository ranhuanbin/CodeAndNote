##### ArrayLt01 (普通循环)
一维数组的动态和
给你一个数组 nums 。数组「动态和」的计算公式为：runningSum[i] = sum(nums[0]…nums[i]) 。
请返回 nums 的动态和。
例如: 输入数组 nums = [1, 2, 3, 4], 输出数组 nums = [1, 3, 6, 10]


##### ArrayLt02 (有点类似hashCode作索引, 同时这个题还用到了动态规划的原理, 与爬楼梯算法非常相似)
[https://leetcode-cn.com/problems/number-of-good-pairs/](https://leetcode-cn.com/problems/number-of-good-pairs/)
给你一个整数数组 nums 。
如果一组数字 (i,j) 满足 nums[i] == nums[j] 且 i < j ，就可以认为这是一组 好数对 。
返回好数对的数目。

##### ArrayLt03 (常规窗口滑动, 这个题误在kotlin使用不熟练导致)
[https://leetcode-cn.com/problems/shuffle-the-array/](https://leetcode-cn.com/problems/shuffle-the-array/)
给你一个数组 nums ，数组中有 2n 个元素，按 [x1,x2,...,xn,y1,y2,...,yn] 的格式排列。
请你将数组按 [x1,y1,x2,y2,...,xn,yn] 格式重新排列，返回重排后的数组。