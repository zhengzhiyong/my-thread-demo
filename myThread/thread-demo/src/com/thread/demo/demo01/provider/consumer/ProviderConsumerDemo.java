package com.thread.demo.demo01.provider.consumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.concurrent.Executors.*;

public class ProviderConsumerDemo {

    public static void main(String[] args) {
        //内存缓冲区
        BlockingQueue<Data> queue = new LinkedBlockingQueue<Data>(10);
        //生产者
        Provider p1 =  new Provider(queue);
        Provider p2 =  new Provider(queue);
        Provider p3 =  new Provider(queue);
        //消费者
        Consumer c1 = new Consumer(queue);
        Consumer c2 = new Consumer(queue);
        Consumer c3 = new Consumer(queue);

        //创建一个线程池，这是一个缓存线程池，可以创建无限的线程，没有创建不成功的时候 ，空闲的话默认60s(默认值)
        ExecutorService pool = newCachedThreadPool();
        pool.execute(p1);
        pool.execute(p2);
        pool.execute(p3);
        pool.execute(c1);
        pool.execute(c2);
        pool.execute(c3);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        p1.stop();
        p2.stop();
        p3.stop();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
