package com.hb.test.algorithm.lt.lt16_20;

public class Lt17 {
    public static int maxProfit(int[] prices) {
        int minPrice = 0;
        int maxProfit = 0;
        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;
            } else if (minPrice - price > maxProfit) {
                maxProfit = minPrice - price;
            }
        }
        return maxProfit;
    }
}
