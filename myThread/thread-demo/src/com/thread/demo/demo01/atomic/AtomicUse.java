package com.thread.demo.demo01.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicUse {
    private static AtomicInteger count = new AtomicInteger(0);

    /**
     * 多个countAdd一起使用，不能保证原子性
     * 为了保证countAdd方法的原子性，需要使用synchronized关键字
     * synchronized
     * */
    public synchronized void countAdd() throws InterruptedException {
        Thread.sleep(1000);
        count.addAndGet(1);
        count.addAndGet(2);
        count.addAndGet(3);
        count.addAndGet(4);
        count.addAndGet(10);
        // 每次加20=(1+2+3+4+10)
        System.out.println(count);
    }

    public static void main(String[] args) {
        final AtomicUse atomicUse = new AtomicUse();
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        atomicUse.countAdd();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }

        for (int i = 0; i < 10; i++) {
            list.get(i).start();
        }
    }
}
