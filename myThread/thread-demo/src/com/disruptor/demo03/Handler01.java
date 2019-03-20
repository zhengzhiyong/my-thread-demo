package com.disruptor.demo03;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.UUID;

public class Handler01 implements WorkHandler<TradeObject>, EventHandler<TradeObject> {
    @Override
    public void onEvent(TradeObject tradeObject, long l, boolean b) throws Exception {
        this.onEvent(tradeObject);
    }

    @Override
    public void onEvent(TradeObject tradeObject) throws Exception {
        System.out.println("handler1: set id");
        tradeObject.setId(UUID.randomUUID().toString());
        tradeObject.setName("TradeObject_"+tradeObject.getId());
        Thread.sleep(1000);
    }
}