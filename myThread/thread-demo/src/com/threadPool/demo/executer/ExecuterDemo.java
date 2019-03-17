package com.threadPool.demo.executer;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.*;

/**
 * @author ZhiYong
 * @date 20点10分
 * @desc 线程池相关demo
 */
public class ExecuterDemo {
    //核心线程数，指的是线程池初始化的时候创建的线程数。
    /**
     * 每次创建的时候初始化固定个数的线程
     */
    //ExecutorService executorService = newFixedThreadPool(10);
    /**
     * 每次初始化的时候创建1线程
     */
    //ExecutorService executorService = newSingleThreadExecutor();

    /**
     * 每次初始化的时候不创建线程
     */
    //ExecutorService executorService = newCachedThreadPool();
    /**
     * 每次初始化的时候，创建固定个数的线程，但是有延迟,定时器机制
     */
    //ExecutorService executorService = newScheduledThreadPool(10);

    //无界队列的线程池的核心就在于coreSize，而maxSize不起作用

    static class ScheduledThreadPoolDemo{
        static class ExecutorThread implements Runnable{
            @Override
            public void run() {
                System.out.println("run");
            }
        }
        public static void main(String[] args) {
            /**
             * 类似于实现了一个定时器的功能，类似于timer
             */
            newScheduledThreadPool(1).scheduleWithFixedDelay(new ExecutorThread(),5,1, TimeUnit.SECONDS);

        }
    }

}
