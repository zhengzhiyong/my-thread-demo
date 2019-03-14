package com.thread.demo.demo01;

/**
 * @author zheng
 * @date 2019年3月14日 15:43:28
 * @dasc 锁重入
 */

public class ThreadDubbo01 {

    public synchronized void method1(){
        System.out.println("method1......");
        method2();
    }

    public synchronized void method2(){
        System.out.println("method2......");
        method3();
    }

    public synchronized void method3(){
        System.out.println("method3......");
    }

    public static void main(String args[]){
        final ThreadDubbo01 thread = new ThreadDubbo01();
        new Thread(new Runnable() {
            @Override
            public void run() {
              thread.method1();
            }
        }).start();
    }
}
