package com.thread.demo.demo01.masterworker;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * @author ZhiYong
 * @date 11点37分
 * @desc master-worker并行计算demo
 */
public class Worker implements Runnable{

    private ConcurrentLinkedQueue<Task> linkedQueue;

    private ConcurrentHashMap<String, Object> resultMap;

    public void setLinkedQueue(ConcurrentLinkedQueue<Task> linkedQueue) {
        this.linkedQueue = linkedQueue;
    }

    public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    @Override
    public void run() {
        while (true){
            Task input = this.linkedQueue.poll();
            if (null == input){
                break;
            }
            //handle方法处理真正的业务
            Object output = handle(input);
            this.resultMap.put(Integer.toString(input.getId()),output);
        }
    }

    public Object handle(Task input) {
        //此处处理真正的业务逻辑
      return null;
    }


}
