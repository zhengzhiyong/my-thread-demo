package com.disruptor.demo01;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import static java.util.concurrent.Executors.*;

/**
 * @author zheng
 * @date 2019年3月19日 13:34:16
 * @desc
 */
public class LongEventMain {

    public static void main(String[] args) {
        /**
         * 1、创建缓冲线程池
         */
        ExecutorService executor = newCachedThreadPool();

        /**
         * 2、创建工厂类，来构建实例对象
         */
        LongEventFactory factory = new LongEventFactory();

        /**
         * 3、创建bufferSize，也就是RingBuffer的大小，必须为2的N次方
         */
        int ringBufferSize = 1024*1024;

        /**
         * BlockingWaitStrategy 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现
         * WaitStrategy BLOCKING_WAIT = new BlockingWaitStrategy();
         * SleepingWaitStrategy 的性能表现跟BlockingWaitStrategy差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适合用于异步日志类似的场景
         * WaitStrategy SLEEPING_WAIT = new SleepingWaitStrategy();
         * YieldingWaitStrategy 的性能是最好的，适合用于低延迟的系统。在要求极高性能且事件处理线数小于CPU逻辑核心数的场景中，推荐使用此策略；例如，CPU开启超线程的特性
         * WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
         */

        /**
         * 4、创建disruptor
         */
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory,ringBufferSize,executor, ProducerType.SINGLE, new YieldingWaitStrategy());

        /**
         * 5、连接消费事件方式,把消息推送consumer
         */
        disruptor.handleEventsWith(new LongEventHandler());

        /**
         * 6、启动disruptor
         */
        disruptor.start();

        /**
         * 7、Disruptor 的事件发布过程是一个两阶段提交的过程： provider
         *    发布事件
         */
        /**
         * ringBuffer环形队列
         */
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        //LongEventProducer producer = new LongEventProducer(ringBuffer);
        LongEventProducerWithTranslator producer = new LongEventProducerWithTranslator(ringBuffer);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for (long i = 1;i<=100;i++){
            byteBuffer.putLong(0,i);
            producer.call(byteBuffer);
        }
        /**
         * 8、关闭 disruptor，方法会堵塞，直至所有的事件都得到处理；
         */
        disruptor.shutdown();
        /**
         * 9、关闭 disruptor 使用的线程池；如果需要的话，必须手动关闭， disruptor 在 shutdown 时不会自动关闭；
         */
        executor.shutdown();
    }
}
