package com.disruptor.demo03;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.Random;

public class Handler04 implements WorkHandler<TradeObject>, EventHandler<TradeObject> {
    final Random random = new Random();
    @Override
    public void onEvent(TradeObject tradeObject, long l, boolean b) throws Exception {
        this.onEvent(tradeObject);
    }

    @Override
    public void onEvent(TradeObject tradeObject) throws Exception {
        System.out.println("handler4: set phone");
        tradeObject.setPhone("TradeObject_phone_"+random.nextInt(11));
        Thread.sleep(1000);
    }
}
