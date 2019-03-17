package com.thread.demo.demo01.masterworker;

import java.util.Random;

/**
 * @author ZhiYong
 * @date 11点37分
 * @desc master-worker并行计算demo
 */
public class Main {

    public static void main(String[] args) {
        Master master = new Master(new MyWorker(),Runtime.getRuntime().availableProcessors());
        Random random = new Random();
        for (int i = 0;i<100;i++){
            Task t = new Task();
            t.setId(i+1);
            t.setName("任务"+t.getId());
            t.setTime(random.nextLong());
            master.submit(t);
        }
        master.execute();
        long start = System.currentTimeMillis();
        while (true){
            if(master.isComplete()){
                long end = System.currentTimeMillis();
                long result = master.getResult();
                System.out.println("最终结果:"+result +"   ,执行耗时:"+(end-start));
                break;
            }
        }
    }
}
