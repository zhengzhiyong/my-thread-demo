package com.thread.demo.demo01;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ZhiYong
 * @date 23点08分
 * @desc  没有实际大小，SynchronousQueue有两个版本的Transferer实现，一种为公平交易类型，一种为非公平交易类型，
 *        公平交易类型的实现类为TransferQueue，它使用队列来作为交易媒介，请求交易的线程总是先尝试跟队列头部（或者尾部）的线程进行交易， 如果失败再将请求的线程添加到队列尾部，
 *        而非公平类型的实现类为TransferStack，它使用一个stack来作为交易媒介，请求交易的线程总是试图与栈顶线程进行交易，失败则添加到栈顶。
 *        所以SynchronousQueue就是使用队列和栈两种数据结构来模拟公平交易和非公平交易的。
 *
 *        生产者线程对其的插入操作put必须等待消费者的移除操作take。
 */
public class SynchronousQueueTest {

    public static class SQThread extends  Thread{
        private SynchronousQueue<Object> synchronousQueue;
        private boolean mode;

          SQThread(SynchronousQueue<Object> synchronousQueue,boolean mode){
            this.synchronousQueue = synchronousQueue;
            this.mode = mode;
        }

        @Override
        public void run() {
            Object object;
            try{
                if (mode){
                    while ((object = synchronousQueue.take())!=null){
                        System.out.println(Thread.currentThread().getName()+"消费了："+object.toString());
                    }
                }
                System.out.println("synchronousQueue 线程消费结束");
            }catch (Exception e){

            }
        }
    }

    public static void main(String[] args) {
        SynchronousQueue<Object> synchronousQueue = new SynchronousQueue<Object>();

       /* for (int i = 0;i< 5 ;i++){
            new SQThread(synchronousQueue,true).start();
        }*/

        ExecutorService executor = new ThreadPoolExecutor(5,5,100, TimeUnit.SECONDS,new SynchronousQueue<>());
        for (int i = 0;i< 5 ;i++){
            executor.execute(new SQThread(synchronousQueue,true));
        }
        ((ThreadPoolExecutor) executor).prestartAllCoreThreads();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0;i< 10 ;i++){
            if (!synchronousQueue.offer(new Object())){
                System.out.println("synchronousQueue 线程生产失败");
            }
        }
    }
}
