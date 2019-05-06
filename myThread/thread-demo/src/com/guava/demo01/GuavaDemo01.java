package com.guava.demo01;

import org.junit.Test;
import java.util.concurrent.*;

public class GuavaDemo01 {

    private GuavaService guavaService = new GuavaService();
    @Test
    public void test1() throws InterruptedException {
        long start = System.currentTimeMillis();
        Long userTime = guavaService.getUserList();
        Long orderTime = guavaService.getOrderList();
        Long messageTime = guavaService.getMessageList();
        Long time = userTime + orderTime + messageTime;
        long end = System.currentTimeMillis();
        System.out.println("计算结果："+ time + "秒，共耗时：" + (end -start)+"毫秒");
    }


    @Test
    public void test2() throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        ExecutorService service = Executors.newFixedThreadPool(3);
        Future userFuture = service.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception{
                return guavaService.getUserList();
            }
        });
        Future orderFuture = service.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception{
                return guavaService.getOrderList();
            }
        });
        Future messageFuture = service.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception{
                return guavaService.getMessageList();
            }
        });

        Long userTime = (Long) userFuture.get();
        Long orderTime = (Long) orderFuture.get();
        Long messageTime = (Long) messageFuture.get();
        Long time = userTime + orderTime + messageTime;
        long end = System.currentTimeMillis();
        System.out.println("执行结果为："+time+"秒，共耗时：" + (end -start)+"毫秒");
    }
}
