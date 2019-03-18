package com.thread.demo.demo01.future;

import java.util.concurrent.*;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class FutureDemo implements Callable<String> {

    /**
     * 请求处理参数
     */
    private String paramStr;

    public FutureDemo(String paramStr) {
        this.paramStr = paramStr;
    }

    @Override
    public String call() throws Exception {
        /**
         * 此处休眠5秒钟，模拟处理耗时业务逻辑
         */
        Thread.sleep(5000);
        return "请求处理完毕，对那个请求参数："+this.paramStr;
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        String paramStr = "【name=张三】";
        FutureDemo futureDemo = new FutureDemo(paramStr);
        /**
         *  这里提交任务future,则开启线程执行RealData的call()方法执行
         * 	submit和execute的区别： 第一点是submit可以传入实现Callable接口的实例对象， 第二点是submit方法有返回值
         *
         *
         *  单独启动一个线程去执行的,和主线程无关，后续主线程可以做自己的业务
         */
        FutureTask<String> futureTask1 = new FutureTask<>(futureDemo);
        FutureTask<String> futureTask2 = new FutureTask<>(futureDemo);
        ExecutorService service = newFixedThreadPool(2);
        Future future1 = service.submit(futureTask1);
        Future future2 = service.submit(futureTask2);
        try {
            /**
             * 提交请求后可以处理自己剩余的业务逻辑
             */
            Thread.sleep(1000);
            /**
             * 调用获取数据的方法，如果call()方法没有执行完，此处依然会进入等待状态继续等待
             */
            System.out.println("futureTask1获取到数据:"+futureTask1.get());
            System.out.println("futureTask2获取到数据:"+futureTask2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("主线程共耗时："+(System.currentTimeMillis() - start)/1000+"s");
        service.shutdown();
    }
}
