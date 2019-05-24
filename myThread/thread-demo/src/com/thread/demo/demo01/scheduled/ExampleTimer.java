package com.thread.demo.demo01.scheduled;

public class ExampleTimer extends Timer{

    public ExampleTimer() {
        super();
    }

    public ExampleTimer(long interval, long duration){
        super(interval, duration);
    }

    @Override
    protected void onTick() {
        System.out.println("onTick called!");
    }

    @Override
    protected void onFinish() {
        System.out.println("onFinish called!");
    }

    public static void main(String[] args){
        Timer timer = new ExampleTimer(2000l, 20000l);
        timer.start();
        timer.pause();
        timer.resume();
    }
}
