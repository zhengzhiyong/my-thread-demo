package com.thread.demo.demo01;

/**
 * @author  zhengzy
 * @date 2019-03-13
 * @desc  线程A和线程B，对于一个数值分别做增加(依次加1)和减少(依次减1)，只有等A线程执行完毕后(每次加0，依次加到10)，B线程才可以执行(每次减1，依次减到0)，不停的轮询。
 *  可以理解为死循环。
 *  和 ThreadDemo02 不同实现方式
 */
public class ThreadDemo01 extends Thread{

    class Number01{
        private boolean flag;
        private int number;
    }

    /**
     * 累加线程
     */
    class ThreadAdd implements Runnable{

        private Number01 number01;

        public ThreadAdd(Number01 number01){
            this.number01 = number01;
        }

        @Override
        public void run() {
            while (true){
               synchronized (number01){
                   if (number01.flag){
                       if (number01.number >= 10){
                           try {
                               number01.wait();
                               number01.flag = false;
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }else {
                        number01.number ++;
                        number01.flag = number01.number < 10;
                        number01.notify();
                        System.out.println("number add:"+number01.number);
                       }
                   }else {
                       number01.notifyAll();
                   }
               }
            }
        }
    }

    /**
     * 累减线程
     */
    class  ThreadMin implements Runnable{
        private Number01 number01;
        ThreadMin(Number01 number01){
            this.number01 = number01;
        }
        @Override
        public void run() {
            while (true){
                synchronized (number01){
                    if (!number01.flag){
                        if (number01.number <=0){
                            try {
                                number01.wait();
                                number01.flag = true;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }else {
                            number01.number --;
                            number01.flag = number01.number < 0;
                            number01.notify();
                            System.out.println("number min:"+number01.number);
                        }
                    }else {
                        number01.notifyAll();
                    }
                }
            }
        }
    }

    public static  void main(String args[]){
        ThreadDemo01 threadDemo01 = new ThreadDemo01();
        Number01 number01 = threadDemo01.new Number01();
        new Thread(threadDemo01.new ThreadAdd(number01)).start();
        new Thread(threadDemo01.new ThreadMin(number01)).start();
    }

}
