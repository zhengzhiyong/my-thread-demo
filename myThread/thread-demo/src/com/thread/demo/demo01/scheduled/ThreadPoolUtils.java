package com.thread.demo.demo01.scheduled;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ThreadPoolUtils {

    private static ScheduledExecutorService executorService;

    private ThreadPoolUtils() {
        //手动创建线程池.
//        executorService = new ScheduledThreadPoolExecutor(10,
//                new BasicThreadFactory.Builder().namingPattern("syncdata-schedule-pool-%d").daemon(true).build()
//        );
        executorService = new ScheduledThreadPoolExecutor(10);
    }

    private static class PluginConfigHolder {
        private final static ThreadPoolUtils INSTANCE = new ThreadPoolUtils();
    }

    public static ThreadPoolUtils getInstance() {
        return PluginConfigHolder.INSTANCE;
    }

    public ScheduledExecutorService getThreadPool(){
        return executorService;
    }


}
