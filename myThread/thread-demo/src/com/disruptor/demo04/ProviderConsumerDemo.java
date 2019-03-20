package com.disruptor.demo04;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;
/**
 * @author Zhengzy
 * @param
 * @date 2019/3/20 10:06
 * @desc 描述
 * @return
 * @exception
 */
public class ProviderConsumerDemo {

    public static void main(String[] args) throws InterruptedException {
        final int BUFFER_SIZE=1024;
        final int THREAD_NUM = 8;
        final Random random = new Random();
        final RingBuffer<Order> ringBuffer = RingBuffer.create(
                ProducerType.MULTI,
                new EventFactory<Order>() {
                    @Override
                    public Order newInstance() {
                        return new Order();
                    }
                },
                BUFFER_SIZE,
                new YieldingWaitStrategy()
        );

        SequenceBarrier barriers = ringBuffer.newBarrier();

        Consumer [] consumers = new Consumer[3];
        for (int i = 0; i <consumers.length ; i++) {
            consumers[i] = new Consumer(String.valueOf(random.nextInt(10000)));
        }
        ExecutorService pool = newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        WorkerPool<Order> workerPool = new WorkerPool<>(ringBuffer,barriers ,new IntEventExceptionHandler(),consumers);
        //多个消费者，需要使用该方法
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
        workerPool.start(pool);

        final CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < 100; i++) {
            final Provider provider = new Provider(ringBuffer);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int j = 0; j < 100; j++) {
                        provider.call(String.valueOf(random.nextInt(1000000000)));
                    }
                }
            }).start();
        }
        Thread.sleep(2000);
        System.out.println("---------------开始生产-----------------");
        latch.countDown();
        Thread.sleep(5000);
        for (int i = 0; i < consumers.length; i++) {
            System.out.println("总数:" + consumers[i].getCount() );
        }
        workerPool.halt();
        pool.shutdown();
    }
}
