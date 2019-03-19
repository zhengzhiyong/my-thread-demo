package com.thread.demo.demo01.syn;

public class ObjectLock {

    private int count1,count2,count3;

    // 对象锁
    public void method1(){
        synchronized (this){
            count1 ++ ;
            System.out.println(Thread.currentThread().getName()+"  method1 count1 :"+count1);
            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

    //（任何）对象锁
    private Object object = new Object();

    public void method2(){
        synchronized (object){
            count2 ++ ;
            System.out.println(Thread.currentThread().getName()+"  method2 count2 :"+count2);
            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

    //类锁
    public void method3(){
        synchronized (ObjectLock.class){
            count3 ++ ;
            System.out.println(Thread.currentThread().getName()+"  method3 count3 :"+count3);
            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

    public static void main(String args[]){
        final ObjectLock objectLock = new ObjectLock();
        for (int i = 0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    objectLock.method1();
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    objectLock.method2();
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    objectLock.method3();
                }
            }).start();
        }

    }
}
