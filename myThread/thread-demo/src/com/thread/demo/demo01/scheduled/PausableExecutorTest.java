package com.thread.demo.demo01.scheduled;

import java.util.concurrent.*;

public class PausableExecutorTest {

    private ScheduledExecutorService service;

    private PausableExecutor executor;

    public void start(){
        service = new ScheduledThreadPoolExecutor(10);
        //闯将线程池
        executor = new PausableExecutor(10, Executors.defaultThreadFactory());
        service.scheduleWithFixedDelay(new Runnable() {
                                           @Override
                                           public void run() {
                                               System.out.println("==========================="+executor.getQueue().size());
                                               if (executor.getQueue().size()>50){
                                                   executor.pause();
                                               }

                                           }
                                       },//需要执行的任务
                10,//启动后延迟多少时间开始执行第一个任务
                10,//上一个任务执行结束后，延迟多少时间开始执行下一个任务
                TimeUnit.SECONDS//时间单位
        );
    }

    public void stop() throws InterruptedException {
        executor.shutdown();
        while (!executor.awaitTermination(1,TimeUnit.SECONDS)){
            //等待线程池中任务执行完毕
        }
        service.shutdown();
    }

    public <T> Future<T> submit(Callable<T> task) {
        return executor.submit(task);
    }

    public Future<?> submit(Runnable task) {
        return executor.submit(task);
    }

    public static void main(String[] args) {
        PausableExecutorTest executor = new PausableExecutorTest();
        executor.start();
        for (int i = 0; i < 100; i++) {
            executor.submit(new Runnable(){
                @Override
                public void run() {
                    System.out.println(Thread.currentThread()+" execute!~~");
                    try {
                        Thread.sleep(1500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
