package com.thread.demo.synchronizedpack;

/**
 *
 *
 *  javap -verbose SynchronizedDemo
 *
 */
public class SynchronizedDemo {
    public void method() {
        synchronized (this) {
            System.out.println("Method 1 start");
        }
    }
}
