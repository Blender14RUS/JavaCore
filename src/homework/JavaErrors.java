package homework;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JavaErrors {
    static LinkedClass teststatic = new LinkedClass();

    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            System.out.print("1. OutOfMemoryError: Java heap space. You can use different data structures. Don’t tune heap size.\n" +
                    "2. OutOfMemoryError: Java heap space. Create big objects continuously and make them stay in memory. Do not use arrays or collections.\n" +
                    "3. StackOverflowError. Use recursive methods. Don’t tune stack size.\n" +
                    "4. StackOverflowError. Do not use recursive methods. Don’t tune stack size.\n");
            switch (in.nextInt()) {
                case 1:
                    heapDifferentData();
                    break;
                case 2:
                    heap();
                    break;
                case 3:
                    overflowRecursive();
                    break;
                case 4:
                    overflowNotRecursive();
                    break;
                default:
                    return;
            }
        } catch (OutOfMemoryError e) {
            System.out.println("OutOfMemoryError: " + e.getMessage());
        } catch (StackOverflowError e) {
            System.out.println("StackOverflowError: " + e.getMessage());
        }
    }

    //java.lang.OutOfMemoryError: Java heap space. You can use different data structures. Don’t tune heap size.
    public static void heapDifferentData() throws OutOfMemoryError {
        List<int[]> array = new ArrayList<>();
        for (; ; ) {
            array.add(new int[999999]);
        }
    }

    //java.lang.OutOfMemoryError: Java heap space. Create big objects continuously and make them stay in memory. Do not use arrays or collections.
    public static void heap() throws OutOfMemoryError {
        LinkedClass test = teststatic;
        for (; ; ) {
            test.link = new LinkedClass();
            System.out.println(test + " " + test.link);
            test = test.link;
        }
    }

    //java.lang.StackOverflowError. Use recursive methods. Don’t tune stack size.
    public static void overflowRecursive() throws StackOverflowError {
        new StackOverflow();
    }

    //java.lang.StackOverflowError. Do not use recursive methods. Don’t tune stack size.
    public static void overflowNotRecursive() throws StackOverflowError {
        new A();
    }

    static class LinkedClass {
        LinkedClass link = null;
        private String str = "abcdefghijklmnopqrstuvwxyz";
    }

    static class StackOverflow {
        StackOverflow() {
            new StackOverflow();
        }
    }

    static class A {
        B b;

        A() {
            b = new B();
        }
    }

    static class B {
        A a;

        B() {
            a = new A();
        }
    }


}

