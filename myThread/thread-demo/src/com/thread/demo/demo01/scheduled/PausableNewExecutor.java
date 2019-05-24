package com.thread.demo.demo01.scheduled;

import java.util.concurrent.*;

public class PausableNewExecutor extends ScheduledThreadPoolExecutor {

    private Continue cont;

    private ScheduledExecutorService service;

    private ThreadPoolExecutor executor;

    public PausableNewExecutor(int corePoolSize, Continue cont ) {
        super(corePoolSize, Executors.defaultThreadFactory());
        this.cont = cont;
        this.service = new ScheduledThreadPoolExecutor(corePoolSize);
        //闯将线程池
        executor = this;
    }

    protected void beforeExecute(Thread t, Runnable r) {
        try {
            cont.checkIn();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.beforeExecute(t, r);
    }

    public void start(){
        service.scheduleWithFixedDelay(new Runnable() {
           @Override
           public void run() {
           }
        },//需要执行的任务
        0,//启动后延迟多少时间开始执行第一个任务
        1,//上一个任务执行结束后，延迟多少时间开始执行下一个任务
        TimeUnit.SECONDS//时间单位
        );
    }

    public static void main(String[] args) {
        PausableNewExecutor  pausableNewExecutor = new PausableNewExecutor(10,new Continue());
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
