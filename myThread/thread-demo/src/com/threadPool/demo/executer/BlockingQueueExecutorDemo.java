package com.threadPool.demo.executer;

import java.util.concurrent.*;

public class BlockingQueueExecutorDemo {
    class Task implements Runnable{
        private int taskId;
        private String taskName;

        public Task(int taskId, String taskName) {
            this.taskId = taskId;
            this.taskName = taskName;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public String toString() {
            return "Task{" +
                    "taskId=" + taskId +
                    ", taskName='" + taskName + '\'' +
                    '}';
        }

        @Override
        public void run() {
            System.out.println("run taskid:"+this.taskId);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class MyRejected implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("自定义拒绝策略来处理.....");
            System.out.println("被拒绝处理的任务为:"+r.toString());
            /**
             * 如果被拒绝可以记录日志到数据库，利用定时任务去批处理；
             * 或者可以根据相应条件去数据更改对应数据的状态，标识为被拒绝，然后再利用其他的线程去处理；
             * 或者可以利用http请求，发送一个请求给发送请求的客户端，告诉调用方当前为高峰期，无法处理该请求，请稍后再试。（一般不建议用该种方式，因为已经在高峰期了，不建议占用网络带宽）
             */
        }
    }

    public static void main(String[] args) {
        /**
         * 在使用有界队列时，若有新的任务需要执行，如果线程池实际线程数小于corePoolSize，则优先创建线程，
         * 若大于corePoolSize，则会将任务加入队列
         * 若队列已满，则在总线程数不大于maxmunPoolSize的前提下，创建新的线程
         * 若线程数大于maxmumPoolSize,则执行默认拒绝策略，或或自定义拒绝策略。
         */
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                1,  // 核心线程数，当线程池初始化时，创建的线程的数量
                2, // 线程池中最大线程数量
                60, //线程空闲时间，空闲时最大存活时间
                 TimeUnit.SECONDS, //线程存活时间单位
                 new ArrayBlockingQueue<Runnable>(3) // 指定一个有界队列
                //new LinkedBlockingQueue<Runnable>()
                ,new MyRejected()
                //,new ThreadPoolExecutor.DiscardOldestPolicy()
                );

        BlockingQueueExecutorDemo demo = new BlockingQueueExecutorDemo();
        int n = 6;
        for (int i = 0;i<n;i++){
            pool.execute(demo.new Task(i,"任务"+i));
        }
        pool.shutdown();
    }
}
