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
     * 弊端：允许的请求队列长度为Integer.MAX_VALUE,可能会堆积大量的请求，造成OOM(内存溢出)
     */
    //ExecutorService executorService = newFixedThreadPool(10);
    /**
     * 每次初始化的时候创建1线程
     * 弊端：允许的请求队列长度为Integer.MAX_VALUE,可能会堆积大量的请求，造成OOM(内存溢出)
     */
    //ExecutorService executorService = newSingleThreadExecutor();

    /**
     * 每次初始化的时候不创建线程
     * 弊端：允许的创建线程数量为Integer.MAX_VALUE,可能会创建大量的线程，造成OOM(内存溢出)
     */
    //ExecutorService executorService = newCachedThreadPool();
    /**
     * 每次初始化的时候，创建固定个数的线程，但是有延迟,定时器机制
     * 弊端：允许的创建线程数量为Integer.MAX_VALUE,可能会创建大量的线程，造成OOM(内存溢出)
     */
    //ExecutorService executorService = newScheduledThreadPool(10);

    //无界队列的线程池的核心就在于coreSize，而maxSize不起作用

    /**
     *     public ThreadPoolExecutor(
     *                               int corePoolSize,//线程池里的核心线程数量，初始化线程池的时候创建线程的数量
     *                               int maximumPoolSize,//线程池里允许有的最大线程数量
     *                               long keepAliveTime,//如果 当前线程数量 > corePoolSize，多出来的线程会在keepAliveTime之后就被释放掉
     *                               TimeUnit unit,//keepAliveTime的时间单位,比如分钟,小时等
     *                               BlockingQueue<Runnable> workQueue,//每当需要创建新的线程放入线程池的时候,就是通过这个线程工厂来创建的
     *                               RejectedExecutionHandler handler//就是说当线程,队列都满了,之后采取的策略,比如抛出异常等策略
     *                               ) {
     *         this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
     *              Executors.defaultThreadFactory(), handler);
     *     }
     *
     *
     *   如：dubbo中就使用了ThreadPoolExecutor，并且使用阻塞队列(LinkedBlockingQueue)来处理,并且给定了队列大小100
     *   com.alibaba.dubbo.common.utils.ExecutorUtil (LinkedBlockingQueue)
     *   com.alibaba.dubbo.common.threadpool.support.fixed.LinkedBlockingQueue (LinkedBlockingQueue)
     *   com.alibaba.dubbo.common.threadpool.support.cached.CachedThreadPool (LinkedBlockingQueue)
     *   com.alibaba.dubbo.common.threadpool.support.limited.LimitedThreadPool   (SynchronousQueue) 生产者-消费者模式队列
     */

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
