package com.thread.demo.demo01.atomic;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class CASABADemo {

    @Test
    public void test1() throws InterruptedException {
        AtomicInteger number = new AtomicInteger(100);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
             number.compareAndSet(100,101);
             number.compareAndSet(101,100);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {

                }
                boolean result = number.compareAndSet(100,101);
                System.out.println("number:"+number.get()+" 交换结果:"+result);
            }
        });
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        System.out.println("number:"+number.get());
    }


    @Test
    public void test2() throws InterruptedException {
        AtomicStampedReference reference = new AtomicStampedReference(100,0);
        int stamp = reference.getStamp();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result1 =  reference.compareAndSet(100,101,stamp,stamp+1);
                System.out.println(result1+"  " +reference.getStamp()+"   "+reference.getReference());
                boolean result2 =  reference.compareAndSet(101,100,stamp,stamp+1);
                System.out.println(result2+"  " +reference.getStamp()+"   "+reference.getReference());
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean result = reference.compareAndSet(100,101,stamp,stamp+1);
                System.out.println("number:"+reference.getReference()+" 交换结果:"+result);
            }
        });

        t1.start();
        t1.join();
        t2.start();
        t2.join();
        System.out.println("number:"+reference.getReference()+" 交换结果:"+reference.getStamp());
    }



    @Test
    public void test3() throws InterruptedException {
        AtomicStampedReference reference = new AtomicStampedReference(100,0);
        int stamp = reference.getStamp();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result1 =  reference.compareAndSet(100,101,stamp,stamp+1);
                System.out.println(result1+"  " +reference.getStamp()+"   "+reference.getReference());
                boolean result2 =  reference.compareAndSet(101,100,stamp+1,stamp+2);
                System.out.println(result2+"  " +reference.getStamp()+"   "+reference.getReference());
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean result = reference.compareAndSet(100,101,stamp+2,stamp+3);
                System.out.println("number:"+reference.getReference()+" 交换结果:"+result);
            }
        });

        t1.start();
        t1.join();
        t2.start();
        t2.join();
        System.out.println("number:"+reference.getReference()+" 交换结果:"+reference.getStamp());
    }
}
