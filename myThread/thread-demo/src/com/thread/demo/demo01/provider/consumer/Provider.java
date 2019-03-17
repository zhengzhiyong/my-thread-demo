package com.thread.demo.demo01.provider.consumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Provider implements Runnable{
    //多线程间是否启动的变量
    private volatile  boolean isRunning = true;
    //id生成器
    private static AtomicInteger count = new AtomicInteger();
    //随机对象
    private static Random random = new Random();
    //共享缓存区
    private BlockingQueue<Data> queue;

    public Provider(BlockingQueue<Data> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (isRunning){
            try {
                //随机休眠0-1000毫秒，表示数据获取的过程（生产数据所需要的时间）
                Thread.sleep(random.nextInt(1000));
                //获取的数据进行累计
                int id = count.incrementAndGet();
                //比如通过一个getData方法获取
                Data data = new Data(Integer.toString(id),"数据"+id);
                System.out.println("当前线程："+Thread.currentThread().getName()+",获取了数据，id是："+data.getId()+",准备放入了缓冲区...");
                if (!this.queue.offer(data,2, TimeUnit.SECONDS)){
                    System.out.println("当前线程："+Thread.currentThread().getName()+",获取了数据，id是："+data.getId()+",放入了缓冲区失败...");
                    //do something  如：重新提交或者记录日志
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        isRunning = false;
    }
}
