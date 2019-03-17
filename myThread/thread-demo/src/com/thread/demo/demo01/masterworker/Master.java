package com.thread.demo.demo01.masterWorker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
/**
 * @author ZhiYong
 * @date 11点37分
 * @desc master-worker并行计算demo
 */
public class Master {

    //1 定义一个存储任务的队列
    private ConcurrentLinkedQueue<Task> linkedQueue = new ConcurrentLinkedQueue();

    //2 使用hashMap去存储所有的worker对象
    private HashMap<String,Thread> workers = new HashMap();

    //3 使用一个容器去存储每一个worker并行执行任务的结果集
    private ConcurrentHashMap<String,Object> resultMap = new ConcurrentHashMap();

    //4 构造方法
    public Master(Worker worker,int workerCount){
        // 每一个worker对象都需要有master的应用，linkedQuneu用于任务的读取，resultMap用于任务结果的提交
        worker.setLinkedQueue(this.linkedQueue);
        worker.setResultMap(this.resultMap);
        for (int i = 0;i<workerCount;i++){
            //key表示一个worker的名字，value表示一个线程执行的对象
            workers.put("node"+i,new Thread(worker));
        }
    }

    //5 提交任务的方法
    public void submit(Task task){
        this.linkedQueue.add(task);
    }

    //6 启动应用程序，让所有的worker进入工作状态
    public void execute(){
        for (Map.Entry<String,Thread> obj : workers.entrySet()){
            obj.getValue().start();
        }
    }

    //7 判断队列是否执行完毕
    public boolean isComplete(){
        for (Map.Entry<String,Thread> obj : workers.entrySet()){
             if (obj.getValue().getState() != Thread.State.TERMINATED){
                 return false;
             }
        }
        return true;
    }

    //8 返回结果集数据
    public long getResult(){
        long result = 0;
        for (Map.Entry<String,Object> obj : resultMap.entrySet()){
            //返回结果集的处理逻辑
            result = Long.valueOf(obj.getValue().toString());
        }
        return result;
    }

}
