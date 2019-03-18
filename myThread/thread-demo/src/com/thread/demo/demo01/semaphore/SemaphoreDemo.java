package com.thread.demo.demo01.semaphore;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * @author ZhiYong
 * @date 22点33分
 * @desc 信号量应用
 *        比如银行一个共有5个窗口，每个窗口一个工作人员，每个工作人接待一个客户的时间都是一样的(2s)。
 */
public class SemaphoreDemo {


    static class BankClient implements Runnable{
        /**
         * 柜台编号
         */
        private String name;

        /**
         *
         */
        private Semaphore semaphore;

        public BankClient(String name, Semaphore semaphore) {
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

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        ExecutorService service = newFixedThreadPool(20);
        for (int i = 0 ;i<20;i++){
            service.submit(new BankClient("用户"+i,semaphore));
        }
        service.shutdown();
    }
}
