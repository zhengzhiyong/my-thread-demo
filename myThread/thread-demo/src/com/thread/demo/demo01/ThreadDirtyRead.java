package com.thread.demo.demo01;

public class ThreadDirtyRead {
    private String name = "zhangsan";

    private String password = "123";

    private synchronized void setValue(String name,String password){
        this.name = name;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.password = password;
        System.out.println("name:"+name+"    password:"+password);
    }

    /**
     * 如果想去除脏读，需要在getValue方法上添加synchronized关键字
     *
     */
//    private synchronized void getValue(){
    private void getValue(){
        System.out.println("name:"+name+"    password:"+password);
    }

    public static void main(String args[]){
        final ThreadDirtyRead threadDirtyRead = new ThreadDirtyRead();
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadDirtyRead.setValue("lisi","456");
            }
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadDirtyRead.getValue();
    }
}
