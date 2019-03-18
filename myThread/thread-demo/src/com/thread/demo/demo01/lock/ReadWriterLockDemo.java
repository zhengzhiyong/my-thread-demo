package com.thread.demo.demo01.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ZhiYong
 * @date 00点23分
 * @desc 读写锁口诀：读读共享，读写互斥，写写互斥
 *
 * 分布式锁需要来地方插件，比如zookeeper，单纯的依赖于Java代码是无法实现的。
 */
public class ReadWriterLockDemo {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    private void method1(){
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"-------进入method1");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+"-------退出method1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            readLock.unlock();
        }
    }

    private void method2(){
        readLock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"-------进入method2");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+"-------退出method2");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            readLock.unlock();
        }
    }

    private void method3(){
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"-------进入method3");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+"-------退出method3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            writeLock.unlock();
        }
    }

    private void method4(){
        writeLock.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"-------进入method4");
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+"-------退出method4");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        final ReadWriterLockDemo demo = new ReadWriterLockDemo();
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
       /* t2.start();
        t3.start();*/
        t4.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
