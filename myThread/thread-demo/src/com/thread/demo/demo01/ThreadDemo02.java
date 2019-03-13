package com.thread.demo.demo01;
/**
 * @author  zhengzy
 * @date 2019-03-13
 * @desc  线程A和线程B，对于一个数值分别做增加(依次加1)和减少(依次减1)，只有等A线程执行完毕后(每次加0，依次加到10)，B线程才可以执行(每次减1，依次减到0)，不停的轮询。
 *  可以理解为死循环。
 *  和 ThreadDemo01 不同实现方式
 */
public class ThreadDemo02 {

    int number;

    boolean flag;

    public static void main(String args[]){

        final ThreadDemo02 threadDemo02 = new ThreadDemo02();

        //加加 demo01
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    synchronized (threadDemo02){
                        if (threadDemo02.flag){
                            if (threadDemo02.number>=10){
                                try {
                                    threadDemo02.wait();
                                    threadDemo02.flag = false;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                threadDemo02.number ++;
                                threadDemo02.flag = threadDemo02.number < 10;
                                threadDemo02.notify();
                                System.out.println("demo01 add num="+threadDemo02.number);
                            }
                        }else {
                            threadDemo02.notifyAll();
                        }
                    }
                }
            }
        }).start();

        //减减thread

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    synchronized (threadDemo02){
                        if (!threadDemo02.flag){
                            if (threadDemo02.number<=0){
                                try {
                                    threadDemo02.wait();
                                    threadDemo02.flag = true;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                threadDemo02.number --;
                                threadDemo02.flag = threadDemo02.number < 0;
                                threadDemo02.notify();
                                System.out.println("demo01 min num="+threadDemo02.number);
                            }
                        }else {
                            threadDemo02.notifyAll();
                        }
                    }
                }
            }
        }).start();
    }
}
