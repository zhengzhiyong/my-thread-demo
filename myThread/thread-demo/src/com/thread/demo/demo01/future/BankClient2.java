package com.thread.demo.demo01.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

public class BankClient2 implements Callable<String> {
    /**
     * 柜台编号
     */
    private String name;

    /**
     *
     */
    private Semaphore semaphore;

    public BankClient2(String name, Semaphore semaphore) {
        this.name = name;
        this.semaphore = semaphore;
    }

    private Random random = new Random();

    @Override
    public String call() throws Exception {
        try {
            semaphore.acquire();
            Thread.sleep(2000);
            System.out.println("工作人员开始处理["+name+"]开始处理:["+ random.nextInt(100) +"]");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }
}
