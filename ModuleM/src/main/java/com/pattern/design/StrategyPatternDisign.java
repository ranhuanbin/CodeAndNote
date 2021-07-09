package com.pattern.design;

/**
 * 策略模式
 * 定义了算法族, 分别封装起来, 让他们之间可以相互替换, 此模式让算法的变化独立于使用算法的客户
 *
 */
public class StrategyPatternDisign {
    public interface FlyBehavior {
        void fly();
    }

    public static class FlyWithWings implements FlyBehavior {
        @Override
        public void fly() {
            System.out.println("用翅膀飞");
        }
    }

    public static class FlyNoWay implements FlyBehavior {
        @Override
        public void fly() {
            System.out.println("不会飞");
        }
    }

    public interface QuackBehavior {
        void quack();
    }

    public static class Quack implements QuackBehavior {
        @Override
        public void quack() {
            System.out.println("呱呱叫");
        }
    }

    public static class MuteQuack implements QuackBehavior {
        @Override
        public void quack() {
            System.out.println("不会叫");
        }
    }

    public abstract static class Duck {
        private FlyBehavior flyBehavior;
        private QuackBehavior quackBehavior;

        public abstract void display();

        public void performQuack() {
            quackBehavior.quack();
        }

        public void performFly() {
            flyBehavior.fly();
        }

        public void setFlyBehavior(FlyBehavior flyBehavior) {
            this.flyBehavior = flyBehavior;
        }

        public void setQuackBehavior(QuackBehavior quackBehavior) {
            this.quackBehavior = quackBehavior;
        }
    }

    public static class Duck1 extends Duck {
        @Override
        public void display() {
            System.out.println("我是Duck1");
        }
    }

    public static void test() {
        Duck1 duck1 = new Duck1();
        duck1.setFlyBehavior(new FlyNoWay());
        duck1.setQuackBehavior(new Quack());

        duck1.performFly();
        duck1.performQuack();
    }
}
