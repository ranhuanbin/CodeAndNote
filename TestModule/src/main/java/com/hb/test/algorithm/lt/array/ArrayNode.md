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

##### ArrayLt04 (常规窗口滑动, for循环)
[https://leetcode-cn.com/problems/kids-with-the-greatest-number-of-candies/](https://leetcode-cn.com/problems/kids-with-the-greatest-number-of-candies/)
给你一个数组 candies 和一个整数 extraCandies ，其中 candies[i] 代表第 i 个孩子拥有的糖果数目。
对每一个孩子，检查是否存在一种方案，将额外的 extraCandies 个糖果分配给孩子们之后，此孩子有 最多 的糖果。注意，允许有多个孩子同时拥有 最多 的糖果数目。

##### ArrayLt05 (排列组合, 需要回头再看的题型)
[https://leetcode-cn.com/problems/sum-of-all-odd-length-subarrays/](https://leetcode-cn.com/problems/sum-of-all-odd-length-subarrays/)
给你一个正整数数组arr，请你计算所有可能的奇数长度子数组的和。
子数组定义为原数组中的一个连续子序列。
请你返回arr中所有奇数长度子数组的和。

**`这个题有个很妙的地方, 总数为total奇数个数为total/2, 偶数个数为(total+1)/2, 这一步省略了判断如果total为奇偶的情况`**