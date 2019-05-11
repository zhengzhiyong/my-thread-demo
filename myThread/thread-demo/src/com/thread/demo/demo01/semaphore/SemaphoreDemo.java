package com.thread.demo.demo01.semaphore;

import com.thread.demo.demo01.future.BankClient1;
import com.thread.demo.demo01.future.BankClient2;
import org.junit.Test;

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

    @Test
    public void test1(){
        Semaphore semaphore = new Semaphore(3);
        ExecutorService service = newFixedThreadPool(20);
        for (int i = 0 ;i<20;i++){
            service.submit(new BankClient1("用户"+i,semaphore));
        }
        service.shutdown();
    }
    @Test

    public void test2(){
        Semaphore semaphore = new Semaphore(3);
        ExecutorService service = newFixedThreadPool(20);
        for (int i = 0 ;i<20;i++){
            service.submit(new BankClient2("用户"+i,semaphore));
        }
        service.shutdown();

    }

    public static void main(String[] args) {

    }
}
