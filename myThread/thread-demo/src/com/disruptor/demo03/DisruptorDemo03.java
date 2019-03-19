package com.disruptor.demo03;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * @author ZhiYong
 * @date  21点01分
 * @desc eventProcessor 模拟多任务错操作demo03
 */
public class DisruptorDemo03 {
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

        //模拟六边形操作，先执行handler01/handler02,
        // handler01执行完后再执行handler03，handler02执行完后再执行handler04，
        // 最后等handler03和handler04执行完后再执行handler05
        Handler01 h1 = new Handler01();
        Handler02 h2 = new Handler02();
        Handler03 h3 = new Handler03();
        Handler04 h4 = new Handler04();
        Handler05 h5 = new Handler05();
        disruptor.handleEventsWith(h1,h2);
        disruptor.after(h1).handleEventsWith(h3);
        disruptor.after(h2).handleEventsWith(h4);
        disruptor.after(h3,h4).handleEventsWith(h5);

        disruptor.start();

        CountDownLatch latch = new CountDownLatch(1);
        pool.submit(new TradePublisher(latch,disruptor));
        latch.await();
        disruptor.shutdown();
        pool.shutdown();
        System.out.println("总共耗时:"+(System.currentTimeMillis()-beginTime)/1000);
    }
}
