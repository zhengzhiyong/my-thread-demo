package com.thread.demo.demo01.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhiYong
 * @date 23点35分
 * @desc 多condition实例
 */
public class ManyConditionDemo {
    /**
     *
     * 在jdk1.8之前lock更快一点在1.8之后synchronized 也做了优化，基本差不多。但是lock 比synchronized 更灵活，
     * 应为是针对一把锁而lock ，synchronized 是针对于某一个类
     *
     */

    final Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    Condition condition2 = lock.newCondition();
    private void method1(){
        lock.lock();
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"-------进入method1");
            condition.await();//类似于object wait()
            System.out.println(Thread.currentThread().getName()+"-------继续执行method1");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"-------退出method1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    private void method2(){
        lock.lock();
        try {
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+"-------进入method2");
            condition2.await();
            System.out.println(Thread.currentThread().getName()+"-------method2继续执行");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+"-------退出method2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    private void method3(){
        lock.lock();
        try {
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+"-------进入method3");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+"-------退出method3");
            condition.signal();//类似于object notfiy()
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }


    private void method4(){
        lock.lock();
        try {
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+"-------进入method4");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+"-------退出method4");
            condition2.signal();//类似于object notfiy()
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public static void main(String[] args) {
        final ManyConditionDemo demo = new ManyConditionDemo();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                demo.method1();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                demo.method2();
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                demo.method3();
            }
        });
        Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
                demo.method4();
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"-------执行完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

