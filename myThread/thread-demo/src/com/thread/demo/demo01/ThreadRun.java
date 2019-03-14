package com.thread.demo.demo01;

/**
 * @author ZhiYong
 * @date 22点39分
 * @desc volatile 关键字 线程执行流程（？？？）
 */
public class ThreadRun extends Thread{
    /**
     * volatile 多线程可见
     */
    private volatile boolean isRunning = true;
    private void setIsRunning(boolean isRunning){
        this.isRunning = isRunning;
    }

    @Override
    public void run(){
        System.out.println("执行run方法");
        while (isRunning){
            //System.out.println("............");
        }
        System.out.println("run方法执行结束");
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadRun threadRun = new ThreadRun();
        threadRun.start();
        Thread.sleep(3000);
        threadRun.setIsRunning(false);
        //Thread.sleep(1000);
        //System.out.println("isRunning:."+threadRun.isRunning);
    }
}
