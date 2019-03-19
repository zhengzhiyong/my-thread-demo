package com.thread.demo.demo01.semaphore;

import java.util.concurrent.CountDownLatch;

/**
 * @author zheng
 * @date 2019年3月15日 08:53:13
 * @desc countdownlatch 是属于一个线程A等待，其他的线程去发出通知，一个线程A执行
 */
public class CountDownLatchDemo {
    private static volatile int count ;

    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        Thread t1 =  new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    count ++;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"---------------------------------------------count的值目前为:"+count);
                    if (count == 5){
                        /**
                         * 计数减一
                         */
                        countDownLatch.countDown();
                        System.out.println(Thread.currentThread().getName()+"---------------------------------------------发出请求通知:count="+count);
                    }
                }
            }
        });

        Thread t2 =  new Thread(new Runnable() {
            @Override
            public void run() {
                if (count != 5) {
                    try {
                        /**
                         * 线程阻塞，直到计数为0的时候唤醒；可以响应线程中断退出阻塞
                         *
                         * await(） 不是 wait()
                         */
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + "---------------------------------------------接收到通知:count=" + count);
                throw new RuntimeException();
            }
        });

        t2.start();
        t1.start();
    }
}
