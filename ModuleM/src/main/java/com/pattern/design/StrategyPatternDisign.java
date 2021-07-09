package com.pattern.design;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import static com.pattern.design.StrategyPatternDisign.StrategyManager.*;

/**
 * 策略模式
 * 算法的变化独立于调用者
 */
public class StrategyPatternDisign {
    public interface IStrategy {
        void operator();
    }

    public static class StrategyA implements IStrategy {
        @Override
        public void operator() {
            System.out.println("StrategyA operator");
        }
    }

    public static class StrategyB implements IStrategy {
        @Override
        public void operator() {
            System.out.println("StrategyB operator");
        }
    }

    public static class StrategyC implements IStrategy {
        @Override
        public void operator() {
            System.out.println("StrategyC operator");
        }
    }

    public static class StrategyManager {
        public static String TAG_1 = "1";
        public static String TAG_2 = "2";
        public static String TAG_3 = "3";
        private static Map<String, IStrategy> maps = new HashMap<>();

        static {
            maps.put(TAG_1, new StrategyA());
            maps.put(TAG_2, new StrategyA());
            maps.put(TAG_3, new StrategyA());
        }

        public static void invoke(String name) {
            if (TextUtils.isEmpty(name)) return;
            IStrategy iStrategy = maps.get(name);
            if (iStrategy == null) return;
            iStrategy.operator();
        }
    }

    public static void test() {
        StrategyManager.invoke(TAG_1);
        StrategyManager.invoke(TAG_2);
        StrategyManager.invoke(TAG_3);
    }
}
