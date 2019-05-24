package com.thread.demo.synchronizedpack;

public class SynchronizedDemo2 {
    public synchronized void method() {
        System.out.println("Method 1 start");
    }

    public  void method2() {
        System.out.println("Method 1 start");
    }

    public synchronized static void method3() {
        System.out.println("Method 1 start");
    }
}
