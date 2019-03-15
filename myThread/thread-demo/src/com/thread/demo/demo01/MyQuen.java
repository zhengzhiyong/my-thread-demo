package com.thread.demo.demo01;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZhiYong
 * @date 23点25分
 * @desc 使用 wait()和notify()来模拟队列，存取和消费数据的一个过程
 */
public class MyQuen {

    private int minSize =0;
    private int maxSize;
    private AtomicInteger count = new AtomicInteger(0);
    private LinkedList<Object> list = new LinkedList<>();
    private MyQuen(int size){
        this.maxSize = size;
    }

    private Object object = new Object();
    private void put(Object obj){
        synchronized (object){
            while (this.maxSize == count.get()){
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(obj);
            count.incrementAndGet();
            System.out.println(Thread.currentThread().getName()+"添加元素："+obj);
            object.notify();
        }
    }


    private Object take(){
        Object o;
        synchronized (object){
            while (this.minSize == count.get()){
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            o =list.removeFirst();
            count.decrementAndGet();
            System.out.println(Thread.currentThread().getName()+"取走元素："+o);
            object.notify();
        }
        return o;
    }
    public static void main(String[] args) {
        final MyQuen quen = new MyQuen(10);
        for (int i = 0;i<10;i++){
          quen.put("a_"+i);
        }

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                quen.put("AA1");
                quen.put("AA2");
                quen.put("AA3");
            }
        });

        t1.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                quen.take();
                quen.take();
                quen.take();
            }
        });
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }
}
