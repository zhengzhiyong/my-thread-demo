package com.thread.demo.demo01;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZhiYong
 * @date 23点04分
 * @desc  volatile 只具备 多线程可见性，并不具备原子性(同步)
 *         atomic 具备原子性（同步）
 */
public class VolatileAtomicThread extends  Thread{

  // private volatile static int count ;

   private static AtomicInteger count = new AtomicInteger(0);

   public static void addCount(){
       for (int i = 0 ;i <10000;i++){
           //count ++;
           count.incrementAndGet();
       }
       System.out.println(count);
   }

    @Override
    public void run() {
        addCount();
    }

    public static void main(String[] args) {
        VolatileAtomicThread[] threads = new VolatileAtomicThread[10];
        for (int i = 0 ;i<threads.length;i++){
            threads[i] = new VolatileAtomicThread();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }
}
