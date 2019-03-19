package com.thread.demo.demo01.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {
    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger(1111);
        //integer.incrementAndGet();
        integer.addAndGet(1111);
    }
}
