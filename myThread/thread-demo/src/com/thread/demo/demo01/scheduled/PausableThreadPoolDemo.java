package com.thread.demo.demo01.scheduled;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PausableThreadPoolDemo extends ThreadPoolExecutor{

    public static volatile int count ;

    public PausableThreadPoolDemo(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);

        pauseLock.lock();
        try {
            while (isPaused){
                unpaused.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            pauseLock.unlock();
        }
    }

    private static boolean isPaused;

    private static ReentrantLock pauseLock = new ReentrantLock();

    private static Condition unpaused = pauseLock.newCondition();

    public static void pause() {
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    public static void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            unpaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }

    public static void resume2(){
        if (isPaused){
            System.out.println("===============================開始休息10s");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resume();
        }
    }

    public static void main (String args[]){
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        PausableThreadPoolDemo threadPool = new PausableThreadPoolDemo(1,5,60,TimeUnit.SECONDS,new ArrayBlockingQueue(100));
        PausableThreadPoolDemo threadPool2 = new PausableThreadPoolDemo(1,5,60,TimeUnit.SECONDS,new ArrayBlockingQueue(100));
        for (int i = 0; i < 100; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count ++;
                    if (count == 10){
                        pause();
                        countDownLatch.countDown();
                    }
                    System.out.println("-----------------------"+ count);
                }
            });
        }

        threadPool2.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                resume2();
            }
        });
    }

}
