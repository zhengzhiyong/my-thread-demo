package com.threadPool.demo.executer;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class UnBlockingQueueExecutorDemo implements Runnable{

    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    public void run() {
        int num = count.incrementAndGet();
        try {
            System.out.println("任务："+num);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /**
         * 在使用无界队列的时候，maxmumPoolSize基本没有什么作用，主要是依赖于corePoolSize来决定的
         */
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue();
        //ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(10);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                5,  // 核心线程数，当线程池初始化时，创建的线程的数量
                10, // 线程池中最大线程数量
                120, //线程空闲时间，空闲时最大存活时间
                TimeUnit.SECONDS, //线程存活时间单位
                queue
        );
        for (int i = 0;i<20;i++){
            pool.execute(new UnBlockingQueueExecutorDemo());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("queue的size："+queue.size());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
