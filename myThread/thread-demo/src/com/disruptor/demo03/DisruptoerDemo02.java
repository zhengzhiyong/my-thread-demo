package com.disruptor.demo03;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;
/**
 * @author ZhiYong
 * @date  21点01分
 * @desc eventProcessor 模拟多任务错操作demo02
 */
public class DisruptoerDemo02 {
    public static void main(String[] args) throws InterruptedException {
        final int BUFFER_SIZE=1024;
        final int THREAD_NUM = 8;
        final long beginTime = System.currentTimeMillis();
        final ExecutorService pool = newFixedThreadPool(THREAD_NUM);

        final Disruptor<TradeObject> disruptor = new Disruptor<TradeObject>(
                new EventFactory() {
                    @Override
                    public TradeObject newInstance() {
                        return new TradeObject();
                    }
                },
                BUFFER_SIZE,
                pool,
                ProducerType.SINGLE,
                new BusySpinWaitStrategy()
        );
        //handler01/handler02/handler03 顺序执行
        disruptor
                .handleEventsWith(new Handler01())
                .handleEventsWith(new Handler02())
                .handleEventsWith(new Handler03());
        disruptor.start();

        CountDownLatch latch = new CountDownLatch(1);
        pool.submit(new TradePublisher(latch,disruptor));
        latch.await();
        disruptor.shutdown();
        pool.shutdown();
        System.out.println("总共耗时:"+(System.currentTimeMillis()-beginTime)/1000);
    }
}
