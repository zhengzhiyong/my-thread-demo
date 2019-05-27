package com.thread.demo.demo01.scheduled;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PausableNewExecutor extends ScheduledThreadPoolExecutor {

    private Condition condition;

    private ScheduledExecutorService service;

    private ThreadPoolExecutor executor;

    private AtomicInteger i = new AtomicInteger(0) ;

    public PausableNewExecutor(int corePoolSize, Condition condition) {
        super(corePoolSize, Executors.defaultThreadFactory());
        this.condition = condition;
        this.service = new ScheduledThreadPoolExecutor(corePoolSize);
        //闯将线程池
        executor = this;
    }

    protected void beforeExecute(Thread t, Runnable r) {
        try {
            condition.checkIn();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.beforeExecute(t, r);
    }

    public void start(){
        /**
         * public ScheduledFuture<?> scheduleWithFixedDelay(
         *       Runnable command, //需要执行的任务
         *       long initialDelay, //启动后延迟多少时间开始执行第一个任务
         *       long delay, //上一个任务执行结束后，延迟多少时间开始执行下一个任务
         *       TimeUnit unit //时间单位
         *);*/
        service.scheduleWithFixedDelay(
            new Runnable() {
               @Override
               public void run() {
                   i.incrementAndGet();
                   int temp = i.get() ;
                   if (temp == 5){
                       condition.pause();
                       System.out.println("condition.pause();=============================" + temp);
                   }else{
                       System.out.println("=============================" + temp);
                   }
               }
            },
            0,
            1,
            TimeUnit.SECONDS
        );
    }

    public static void main(String[] args) {
        PausableNewExecutor  pausableNewExecutor = new PausableNewExecutor(10,new Condition());
        pausableNewExecutor.start();
        for (int i = 0; i < 100; i++) {
            pausableNewExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
