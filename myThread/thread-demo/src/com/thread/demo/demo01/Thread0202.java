package com.thread.demo.demo01;

/**
 * @author zheng
 * @date 2019年3月14日 13:48:35
 * @desc 静态方法锁定的是类，非静态方法锁定的是对象 demo1
 */
public class Thread0202 {

    private static int i;

    private static synchronized  void printNum(String tag){
        if ("a".equals(tag)){
            i  = 200;
            System.out.println("tag is a");
        }else {
            i =300;
            System.out.println("tag is b");
        }
        System.out.println("tag is  "+tag+", i is "+i);
    }

    public static void main(String args[]){
        final Thread0202 t1 = new Thread0202();
        final Thread0202 t2 = new Thread0202();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                t1.printNum("a");
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                t2.printNum("b");
            }
        });

        thread1.start();
        thread2.start();
    }

}
