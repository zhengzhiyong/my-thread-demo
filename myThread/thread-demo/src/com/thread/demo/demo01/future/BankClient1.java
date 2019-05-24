package com.thread.demo.demo01.future;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class BankClient1  implements Runnable{
    /**
     * 柜台编号
     */
    private String name;

    /**
     *
     */
    private Semaphore semaphore;

    public BankClient1(String name, Semaphore semaphore) {
        this.name = name;
        this.semaphore = semaphore;
    }

    private Random random = new Random();
    @Override
    public void run() {
        try {
            semaphore.acquire();
            Thread.sleep(2000);
            System.out.println("工作人员开始处理["+name+"]开始处理:["+ random.nextInt(100) +"]");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
