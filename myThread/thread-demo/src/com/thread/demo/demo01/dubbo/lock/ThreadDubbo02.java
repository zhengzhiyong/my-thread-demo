package com.thread.demo.demo01.dubbo.lock;

/**
 * @author zheng
 * @date 2019年3月14日 16:35:53
 * @desc 锁重入
 */
public class ThreadDubbo02 {
    class Parent{
        public int count = 10;

        public synchronized void parentPrint(){
            if(count > 0){
                count -- ;
                System.out.println(Thread.currentThread().getName()+"  parent class print count :" + count);
            }
        }
    }

    class Child extends Parent{
        public synchronized  void childPrint(){
            while (count > 0){
                count -- ;
                System.out.println(Thread.currentThread().getName()+"  child class print count :" + count);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.parentPrint();
            }
        }
    }

    public static void main(String args[]){
        ThreadDubbo02 threadDubbo02 = new ThreadDubbo02();
        ThreadDubbo02.Child child = threadDubbo02.new Child();
        new Thread(new Runnable() {
            @Override
            public void run() {
                child.childPrint();
            }
        }).start();
    }
}
