package test;

public class DbTestImpl {
    /**
     * 菲波拉契数
     * 1. 确定一维数组dp[i]以及下标的含义
     * 2. 确定递推公式
     * 3. dp数组如何初始化
     * 4. 确定遍历顺序
     * 5. 举例推导数组
     */
    public static int fib(int n) {
        int[] arr = new int[n];
        if (n == 1) {
            return 0;
        }
        if (n == 2) {
            return 1;
        }
        arr[0] = 0;
        arr[1] = 1;
        for (int i = 2; i < arr.length; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }
        return arr[n - 1];
    }

    /**
     * 爬楼梯
     * 1. 确定dp数组以及下标的含义:
     * 2. 确定递推公式
     * 3. 递推数组如何初始化
     * 4. 确定遍历顺序
     */
    public static int paLouTi(int n) {
        if (n <= 2) {
            return n;
        }
        int[] arr = new int[n + 1];
        arr[1] = 1;
        arr[2] = 2;
        for (int i = 3; i < n + 1; i++) {
            arr[n] = arr[n - 1] + arr[n - 2];
        }
        return arr[n];
    }

    /**
     * 01背包问题
     * DbTestImpl.beibao(new int[]{1, 4, 5, 7}, new int[]{1, 3, 4, 5}, 7);
     */
    public static int beibao(int[] vals, int[] weights, int totalWeight) {
        // 物品总数量
        int n = vals.length;
        int[][] dp = new int[n + 1][totalWeight + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= totalWeight; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                } else {
                    if (j < weights[i - 1]) {
                        dp[i][j] = dp[i - 1][j];
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], vals[i - 1] + dp[i - 1][j - weights[i - 1]]);
                    }
                }
            }
        }
        return dp[n][totalWeight];
    }
}
