package com.thread.demo.demo01.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhiYong
 * @date 23点35分
 * @desc 重入锁 demo
 */
public class ReentrantLockDemo {
    /**
     *
     * 在jdk1.8之前lock更快一点在1.8之后synchronized 也做了优化，基本差不多。但是lock 比synchronized 更灵活,一个lock可以创建多个condition，
     * 应为是针对一把锁而lock ，synchronized 是针对于某一个类
     *
     */

    final Lock lock = new ReentrantLock();
    private void method1(){
        lock.lock();
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"-------进入method1");
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
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+"-------退出method2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final ReentrantLockDemo demo = new ReentrantLockDemo();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                demo.method1();
                demo.method2();
            }
        });
        t1.start();
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+"-------执行完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
