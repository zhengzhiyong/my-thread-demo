package com.disruptor.demo04;

import com.lmax.disruptor.WorkHandler;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * @author Zhengzy
 * @param
 * @date 2019/3/20 10:06
 * @desc 描述
 * @return
 * @exception
 */
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
