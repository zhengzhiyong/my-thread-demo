package com.disruptor.demo03;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
/**
 * @author Zhengzy
 * @param 
 * @date 2019/3/20 10:07
 * @desc 描述
 * @return 
 * @exception 
 */
public class TradePublisher implements Runnable {
    private CountDownLatch latch;
    private Disruptor<TradeObject> disruptor;
    //模拟多次交易
    static int LOOP = 1;
    public TradePublisher(CountDownLatch latch, Disruptor<TradeObject> disruptor) {
        this.latch = latch;
        this.disruptor = disruptor;
    }

    @Override
    public void run() {
        TradeEventTranslator translator = new TradeEventTranslator();
        for (int i = 0; i < LOOP; i++) {
            disruptor.publishEvent(translator);
        }
        latch.countDown();
    }
}

class TradeEventTranslator implements EventTranslator<TradeObject>{

    private final Random random = new Random();

    @Override
    public void translateTo(TradeObject tradeObject, long l) {
        tradeObject.setPrice(random.nextDouble()*123);
    }
}