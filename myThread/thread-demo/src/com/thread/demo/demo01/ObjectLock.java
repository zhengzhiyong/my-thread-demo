package com.thread.demo.demo01;

public class ObjectLock {
    private int count,count2,count3;

    public void method1(){
        synchronized (this){
            count ++ ;
            System.out.println(Thread.currentThread().getName()+"  method1 count :"+count);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Object object = new Object();
    public void method2(){
        synchronized (object){
            count2 ++ ;
            System.out.println(Thread.currentThread().getName()+"  method2 count :"+count2);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void method3(){
        synchronized (ObjectLock.class){
            count3 ++ ;
            System.out.println(Thread.currentThread().getName()+"  method3 count :"+count3);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]){
        final ObjectLock objectLock = new ObjectLock();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                objectLock.method1();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                objectLock.method1();
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                objectLock.method1();
            }
        });
        t1.start();
        t2.start();
        t3.start();




        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                objectLock.method2();
            }
        });
        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                objectLock.method2();
            }
        });
        Thread t6 = new Thread(new Runnable() {
            @Override
            public void run() {
                objectLock.method2();
            }
        });
        t4.start();
        t5.start();
        t6.start();



        Thread t7 = new Thread(new Runnable() {
            @Override
            public void run() {
                objectLock.method3();
            }
        });
        Thread t8 = new Thread(new Runnable() {
            @Override
            public void run() {
                objectLock.method3();
            }
        });
        Thread t9 = new Thread(new Runnable() {
            @Override
            public void run() {
                objectLock.method3();
            }
        });
        t7.start();
        t8.start();
        t9.start();
    }
}
