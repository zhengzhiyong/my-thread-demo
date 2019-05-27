package com.thread.demo.demo01.scheduled.timer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ExampleTimer extends Timer{

    private static ScheduledExecutorService  service;

    public ExampleTimer(long interval, long duration,ScheduledExecutorService service){
        super(interval, duration,service);
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
        service = Executors.newSingleThreadScheduledExecutor();
        Timer timer = new ExampleTimer(2000, 20000,service);
//        timer.start();
//        timer.pause();
        timer.resume();
    }
}
