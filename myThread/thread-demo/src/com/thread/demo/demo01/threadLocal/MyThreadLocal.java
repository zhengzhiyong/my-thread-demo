package com.thread.demo.demo01.threadLocal;

/**
 * @author ZhiYong
 * @date 23点38分
 * @desc ThreadLocal 对于每一个线程来说都是独立的存在，可以理解为堆栈中的栈，属于每一个线程自己的一块区域的存在
 */
public class MyThreadLocal {

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    private void set(String name){
        this.threadLocal.set(name);
    }

    private void get(){
        System.out.println(Thread.currentThread().getName()+":"+threadLocal.get());
    }

    public static void main(String[] args) {
        final MyThreadLocal myThreadLocal = new MyThreadLocal();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                myThreadLocal.set("郑志勇");
                myThreadLocal.get();
            }
        });


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                myThreadLocal.get();
            }
        });
        t1.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }
}
