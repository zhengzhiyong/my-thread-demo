package com.thread.demo.demo01;

public class ThreadExceptin {
    private static int i = 0;
    public synchronized void printAdd(){
        while (true){
            i ++ ;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("i========="+i);
            if(i == 30){
                try {
                    Integer.valueOf("");
                    throw new RuntimeException();
                }catch (Exception e){
                    e.printStackTrace();
                    System.out.println("出错了，i="+i +" 错误信息:"+e.getMessage());
                    throw new RuntimeException();
                   // continue;
                }
            }
        }
    }

    public static void main(String args[]){
        final ThreadExceptin exceptin = new ThreadExceptin();
        new Thread(new Runnable() {
            @Override
            public void run() {
                exceptin.printAdd();
            }
        }).start();
    }
}
