package com.thread.demo.demo01.scheduled;

import com.google.common.util.concurrent.Monitor;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class PausableExecutor extends ScheduledThreadPoolExecutor {

    private boolean isPaused;

    private final Monitor monitor = new Monitor();

    private final Monitor.Guard pasued = new Monitor.Guard(monitor) {
        @Override
        public boolean isSatisfied() {
            return isPaused;
        }
    };

    private final Monitor.Guard notPaused = new Monitor.Guard(monitor) {
        @Override
        public boolean isSatisfied() {
            return !isPaused;
        }
    };

    public PausableExecutor(int corePoolSize, ThreadFactory threadFactory) {
        super(corePoolSize, threadFactory);
    }

    protected void beforeExecute(Thread thread,Runnable runnable){
        super.beforeExecute(thread,runnable);
        monitor.enterWhenUninterruptibly(notPaused);
        try{
            monitor.waitForUninterruptibly(notPaused);
        }finally {

        }
    }

    public void pause(){
        monitor.enterIf(notPaused);
        try {
            isPaused = true;
        }finally {
            monitor.leave();
        }
    }

    public void resume(){
        monitor.enterIf(pasued);
        try {
            isPaused = false;
        }finally {
            monitor.leave();
        }
    }
}
