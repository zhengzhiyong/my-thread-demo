package com.disruptor.demo03;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class Handler03 implements WorkHandler<TradeObject>, EventHandler<TradeObject> {
    @Override
    public void onEvent(TradeObject tradeObject, long l, boolean b) throws Exception {
        this.onEvent(tradeObject);
    }

    @Override
    public void onEvent(TradeObject tradeObject) throws Exception {
        System.out.println("handler3: set unit");
        tradeObject.setName("TradeObject_UNIT_A");
        Thread.sleep(1000);
    }
}
