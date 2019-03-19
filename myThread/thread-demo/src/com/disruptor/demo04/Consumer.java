package com.disruptor.demo04;

import com.lmax.disruptor.WorkHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements WorkHandler<Order> {

    private String customerId;

    private static AtomicInteger count = new AtomicInteger(0);

    public Consumer(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public void onEvent(Order order) throws Exception {
        System.out.println(Thread.currentThread().getName()+"----当前消费者是："+this.customerId+",消费数据："+order.toString());
        count.incrementAndGet();
    }

    public int getCount(){
        return count.get();
    }
}
