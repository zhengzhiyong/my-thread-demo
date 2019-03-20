package com.disruptor.demo03;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.Random;

public class Handler05 implements WorkHandler<TradeObject>, EventHandler<TradeObject> {
    final Random random = new Random();
    @Override
    public void onEvent(TradeObject tradeObject, long l, boolean b) throws Exception {
        this.onEvent(tradeObject);
    }

    @Override
    public void onEvent(TradeObject tradeObject) throws Exception {
        System.out.println("handler5: set price");
        tradeObject.setName("TradeObject_price:"+random.nextDouble()*234);
        Thread.sleep(1000);
    }
}
