package com.thread.demo.demo01;

public class Thread01 implements Runnable{
    volatile int i = 10;

    @Override
    public synchronized void run() {
        i--;
        System.out.println(Thread.currentThread().getName()+":"+i);
    }

    public static void main(String args[]){
        Thread01 thread01 = new Thread01();
        new Thread(thread01,"thread-1").start();
        new Thread(thread01,"thread-2").start();
        new Thread(thread01,"thread-3").start();
        new Thread(thread01,"thread-4").start();
        new Thread(thread01,"thread-5").start();
        new Thread(thread01,"thread-6").start();
        new Thread(thread01,"thread-7").start();
        new Thread(thread01,"thread-8").start();
        new Thread(thread01,"thread-9").start();
        new Thread(thread01,"thread-10").start();
    }
}
