package com.thread.demo.demo01.masterworker;
/**
 * @author ZhiYong
 * @date 11点37分
 * @desc master-worker并行计算demo
 */
public class MyWorker extends Worker {

    @Override
    public Object handle(Task input) {
        Object output = null;
        try {
            //真正处理业务逻辑的代码，比如请求数据库业务处理....
            Thread.sleep(500);
            output = input.getTime();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return output;
    }
}
