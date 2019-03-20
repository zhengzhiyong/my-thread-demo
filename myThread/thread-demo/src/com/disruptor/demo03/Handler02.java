package com.disruptor.demo03;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class Handler02 implements WorkHandler<TradeObject>, EventHandler<TradeObject> {
    @Override
    public void onEvent(TradeObject tradeObject, long l, boolean b) throws Exception {
        this.onEvent(tradeObject);
    }

    @Override
    public void onEvent(TradeObject tradeObject) throws Exception {
        System.out.println("handler2: set name");
        tradeObject.setName("TradeObject_"+tradeObject.getId());
        Thread.sleep(1000);
    }
}
