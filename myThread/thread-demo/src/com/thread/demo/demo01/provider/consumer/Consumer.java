package com.thread.demo.demo01.provider.consumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{

    private static Random random = new Random();

    private BlockingQueue<Data> queue;

    public Consumer(BlockingQueue<Data> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            try {
                //获取数据
                Data data = this.queue.take();
                //进行数据处理，休眠0-1000毫秒
                Thread.sleep(random.nextInt(1000));
                System.out.println("当前线程:"+Thread.currentThread().getName()+"，消费数据成功，数据ID："+data.getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
