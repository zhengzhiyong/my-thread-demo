package com.disruptor.demo02;

import com.lmax.disruptor.*;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * @author ZhiYong
 * @date  21点01分
 * @desc eventProcessor 消息处理器方式处理
 */
public class EventProcessorDo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final Random random = new Random();
        //定义缓冲区域的大小
        final int BUFFER_SIZE=1024;
        //定义线程的数量
        final int THREAD_NUMBERS=4;
        //创建一个固定大小的线程池
        ExecutorService pool = newFixedThreadPool(THREAD_NUMBERS);
        /**
         * createSingleProducer创建一个单生产者的RingBuffer
         * 第一个参数叫EventFactory，从名字上理解就是"事件工厂"，其实它的职责就是产生数据填充RingBuffer的区块。
         * 第二个参数是RingBuffer的大小，它必须是2的指数倍 目的是为了将求模运算转为&运算提高效率
         * 第三个参数是RingBuffer的生产都在没有可用区块的时候(可能是消费者（或者说是事件处理器） 太慢了)的等待策略
         */
        final RingBuffer<Trade> ringBuffer = RingBuffer.createSingleProducer(
                new EventFactory<Trade>() {
                    @Override
                    public Trade newInstance() {
                        return new Trade();
                    }
                },
                BUFFER_SIZE,
                new YieldingWaitStrategy()
        );

        //创建sequenceBarrier，类似于一个屏障
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        //闯将消息处理器
        BatchEventProcessor processor = new BatchEventProcessor(ringBuffer,sequenceBarrier,new TradeHandler());
        //这一步的目的就是把消费者的位置信息引用注入到生产者,如果只有一个消费者的情况可以省略
        //ringBuffer.addGatingSequences(processor.getSequence());

        //把消息提交到线程池去处理
        pool.submit(processor);
        
        Future future = pool.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                long sequence;
                for (int i = 0; i < 10; i++) {
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
                return null;
            }
        });

        //等待生产者，生产数据结束
        future.get();
        //等1S，等待消费者消费完数据
        Thread.sleep(1000);
        //通知事件或者消息处理器，可以结束了，但并不是马上就结束掉
        processor.halt();
        //关闭线程池
        pool.shutdown();
    }
}
