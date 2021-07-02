package test;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * 1. 确定一维dp数组来保存递归的结果: 确定dp数组以及下标的含义
 * 2. dp[i]的定义: 确定递推公式
 * 3. dp数组如何初始化
 * 4. 确定遍历顺序
 * 5. 举例推导dp数组
 */
public class DpTest extends TestCase {
    @Test
    public void test() {
        int beibao = DbTestImpl.beibao(new int[]{1, 4, 5, 7}, new int[]{1, 3, 4, 5}, 7);
        System.out.println(beibao);
    }
}
