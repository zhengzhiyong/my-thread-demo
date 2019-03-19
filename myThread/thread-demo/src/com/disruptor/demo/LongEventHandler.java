package com.disruptor.demo;

import com.lmax.disruptor.EventHandler;

/**
 * @author zheng
 * @date 2019年3月19日 13:33:56
 * @desc
 *    我们还需要一个事件消费者，也就是一个事件处理器。这个事件处理器简单地把事件中存储的数据打印到终端：
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<消费数据："+longEvent.getName()+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
