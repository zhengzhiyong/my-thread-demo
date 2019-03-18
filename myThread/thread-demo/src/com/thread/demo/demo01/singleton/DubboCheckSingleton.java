package com.thread.demo.demo01.singleton;

/**
 * @author ZhiYong
 * @date 00点00分
 * @desc 懒汉模式-单例模式- 双重确认模式
 */
public class DubboCheckSingleton {
    private static DubboCheckSingleton singleton = null;

    public static DubboCheckSingleton getInstance(){
        if (null == singleton){
            //模式某业务初始化过程，可能需要两秒执行完成
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (DubboCheckSingleton.class){
                if (null == singleton){
                    singleton = new DubboCheckSingleton();
                }
            }
        }
        return singleton;
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(DubboCheckSingleton.getInstance().hashCode());
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(DubboCheckSingleton.getInstance().hashCode());
            }
        });

        t1.start();
        t2.start();
    }
}
