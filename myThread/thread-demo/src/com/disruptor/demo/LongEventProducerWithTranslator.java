package com.disruptor.demo;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @author zhengzy
 * @data 2019年3月19日 13:25:47
 * @desc Disruptor 3.0提供了lambda式的API。这样可以把一些复杂的操作放在Ring Buffer，
 *       所以在Disruptor3.0以后的版本最好使用Event Publisher或者Event Translator来发布事件
 */
public class LongEventProducerWithTranslator {
    /**
     * 一个translator可以看做一个事件初始化器，publicEvent方法会调用它
     * 填充Event
     */
    private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR =
            new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
                @Override
                public void translateTo(LongEvent event, long sequeue, ByteBuffer buffer) {
                    event.setName(buffer.getLong(0));
                    System.out.println("LongEventProducerWithTranslator<><><><><><><><><><><><><><><><>数据"+event.getName()+"被生产<><><><><><><><><><><><><><><><>");
                }
            };

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void call(ByteBuffer buffer){
        ringBuffer.publishEvent(TRANSLATOR, buffer);
    }
}
