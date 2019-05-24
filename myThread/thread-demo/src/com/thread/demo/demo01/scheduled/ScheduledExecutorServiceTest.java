package com.thread.demo.demo01.scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ScheduledExecutorServiceTest {

    public static void main(String[] args) throws Exception {
        //test1();
        //test2();
        test3();
    }


    //使用scheduleAtFixedRate()方法实现周期性执行
    public static void test1(){
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(
        (Runnable) () -> {
            System.out.println("run"+System.currentTimeMillis());
         },//Runnable 为方法所需要执行的任务
        0,//initialDelay为延迟启动的时间间隔，配合unit一起使用
        3,//period 任务执行的频率，多久执行一次
         TimeUnit.SECONDS//单位
        );
    }


    //ScheduledExecutorService使用Callable延迟运行
    public static void test2() throws ExecutionException, InterruptedException {
        List<Callable> callableList = new ArrayList<>(2);
        callableList.add(new MyCallableA());
        callableList.add(new MyCallableB());
        //ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

        ScheduledFuture futureA = service.schedule(callableList.get(0),1,TimeUnit.SECONDS);
        ScheduledFuture futureB = service.schedule(callableList.get(1),1,TimeUnit.SECONDS);

        System.out.println("            X = " + System.currentTimeMillis());
        System.out.println("返回值A：" + futureA.get());
        System.out.println("返回值B：" + futureB.get());
        System.out.println("            Y = " + System.currentTimeMillis());
    }

    static class MyCallableA implements Callable<String> {
        @Override
        public String call(){
            try {
                System.out.println("callA  " + Thread.currentThread().getName() + ", " + System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "returnA";
        }
    }

    static class MyCallableB implements Callable<String>  {
        @Override
        public String call(){
            System.out.println("callB begin " + Thread.currentThread().getName() + ", " + System.currentTimeMillis());
            System.out.println("callB end   " + Thread.currentThread().getName() + ", " + System.currentTimeMillis());
            return "returnB";
        }
    }


    public static void test3(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        System.out.println("          x = " + System.currentTimeMillis());
        executorService.scheduleWithFixedDelay(new MyRunable(), 1, 2, TimeUnit.SECONDS);
        System.out.println("          y = " + System.currentTimeMillis());
    }

    static class MyRunable implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("     begin = " + System.currentTimeMillis() + ", name: " + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(4);
                System.out.println("     end   = " + System.currentTimeMillis() + ", name: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
