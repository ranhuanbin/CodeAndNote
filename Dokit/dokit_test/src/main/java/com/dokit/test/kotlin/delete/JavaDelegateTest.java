package com.dokit.test.kotlin.delete;

public class JavaDelegateTest {
    interface Printer {
        void printSomething(String something);
    }

    static class PrinterDelegate implements Printer {
        @Override
        public void printSomething(String something) {
            System.out.println("print from delegate: " + something);
        }
    }

    static class PrintingHouse implements Printer {
        Printer mPrinter;

        public PrintingHouse(Printer printer) {
            mPrinter = printer;
        }

        @Override
        public void printSomething(String something) {
            mPrinter.printSomething(something);
        }
    }

    public static void main(String[] args) {
        PrinterDelegate delegate = new PrinterDelegate();
        PrintingHouse printingHouse = new PrintingHouse(delegate);
        printingHouse.printSomething("newspaper");
    }
}
