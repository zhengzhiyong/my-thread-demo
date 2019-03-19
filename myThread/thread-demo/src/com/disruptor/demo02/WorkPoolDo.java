package com.disruptor.demo02;

import com.lmax.disruptor.*;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * @author ZhiYong
 * @date  21点01分
 * @desc workPool 工作池方式处理
 */
public class WorkPoolDo {
    public static void main(String[] args) throws InterruptedException {
        final Random random = new Random();
        //指定ringbuffer缓冲区的大小
        final int BUFFER_SIZE = 1024;
        //指定线程池大小
        final int THREAD_NUMBERS = 20;
        //创建对应的eventFactory工厂，来生产实例化event对象
        EventFactory<Trade> factory = new EventFactory<Trade>() {
            @Override
            public Trade newInstance() {
                return new Trade();
            }
        };
        //创建缓冲区，类似queue
        RingBuffer<Trade> ringBuffer = RingBuffer.createSingleProducer(factory,BUFFER_SIZE,new YieldingWaitStrategy());
        //创建sequenceBarrier，类似于一个屏障
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
        //创建线程池
        ExecutorService pool = newFixedThreadPool(THREAD_NUMBERS);
        //实例化一个workhandler，来消费数据，consumer的功能
        WorkHandler<Trade> handler = new TradeHandler();
        //创建一个工作池，来消费数据
        WorkerPool<Trade> workerPool = new WorkerPool(ringBuffer,sequenceBarrier,new IgnoreExceptionHandler(),handler);
        //将线程池的实例传入到工作池中去工作，来消费数据。调用start方法去工作
        workerPool.start(pool);

        long sequence;
        for (int i = 0; i < 1000; i++) {
            //占个坑 --ringBuffer下一个可用区块
            sequence = ringBuffer.next();
            Trade trade = ringBuffer.get(sequence);
            trade.setPrice(Math.random()*20);
            trade.setId(UUID.randomUUID().toString());
            trade.setName("Trade-Name-"+(random.nextInt(1000)));
            trade.setCount(new AtomicInteger(random.nextInt(10000)));
            //发布这个区域块的数据是handler(consumer)可见
            ringBuffer.publish(sequence);
        }

        //休眠一秒钟，等待消费者消费数据结束
        Thread.sleep(1000);
        //通知事件或者消息处理器，可以结束了，但并不是马上就结束掉
        workerPool.halt();
        //关闭线程池
        pool.shutdown();
    }
}
