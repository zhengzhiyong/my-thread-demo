package com.thread.demo.demo01.scheduled;

import java.util.concurrent.*;

public class FlexibleThreadPoolExecutor {

    enum FlexibleObj{
        MAX_QUEUE_SIZE(100),
        PER_THREAD_COUNT(10),
        MONITOR_DELAY_TIME(1);
        private final int value;
        private FlexibleObj(int value){
            this.value = value;
        }
        public int getValue(){
            return this.value;
        }
    }

    /**
     * 队列阈值，超过此值则扩大线程池
     */
    private static final int MAX_QUEUE_SIZE = FlexibleObj.MAX_QUEUE_SIZE.getValue();

    /**
     * 每次扩(缩)容自动增加(减少)线程数
     */
    private static final int PER_THREAD_COUNT = FlexibleObj.PER_THREAD_COUNT.getValue();

    /**
     * 监控积压时间频率
     */
    private static final int MONITOR_DELAY_TIME = FlexibleObj.MONITOR_DELAY_TIME.getValue();

    /**
     * 以jvm内存使用率80%为界
     */
    private static final double MEMORY_USAGE = 0.8;

    private ScheduledExecutorService service;

    private ThreadPoolExecutor executor;

    /**
     * 获取jvm内存使用率
     * @return
     */
    public static double getMemoryUsage() {
        return (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / Runtime.getRuntime().maxMemory();
    }

    public void start(){
        service = new ScheduledThreadPoolExecutor(10);
        //闯将线程池
        executor = new ThreadPoolExecutor(10,100,10, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        service.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    System.out.println("当前线程池状态！"+executor);
                    //扩容条件：
                    //1、队列大小超过限制
                    //2、线程池中线程数量小于最大线程数
                    //3、jvm内存使用率小于80%（防止无限扩容）
                    String text = "";
                    if (executor.getQueue().size() >= MAX_QUEUE_SIZE && executor.getPoolSize() < executor.getMaximumPoolSize() && getMemoryUsage() < MEMORY_USAGE){
                        executor.setCorePoolSize(executor.getPoolSize() + PER_THREAD_COUNT);
                        text = "扩容";
                    }
                    //缩容条件：
                    //1、队列中任务数量小于队列允许数量的80%
                    if (executor.getPoolSize() > 0 && executor.getQueue().size()< MAX_QUEUE_SIZE * MEMORY_USAGE){
                        executor.setCorePoolSize(executor.getPoolSize() - PER_THREAD_COUNT);
                        text = "缩容";
                    }
                    if (null != text && !"".equals(text)){
                        System.out.println(text + "后的线程池！"+executor);
                    }
                }
            },//需要执行的任务
            MONITOR_DELAY_TIME,//启动后延迟多少时间开始执行第一个任务
            MONITOR_DELAY_TIME,//上一个任务执行结束后，延迟多少时间开始执行下一个任务
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

    public static void main(String[] args) throws InterruptedException {
        FlexibleThreadPoolExecutor poolExecutor = new FlexibleThreadPoolExecutor();
        poolExecutor.start();
        for (int i = 0; i < 1000; i++) {
            poolExecutor.submit(new Runnable(){
                @Override
                public void run() {
                    //System.out.println(Thread.currentThread()+" execute!~~");
                    try {
                        Thread.sleep(1500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
//        poolExecutor.stop();
    }
}
