package com.thread.demo.demo01.future;

/**
 * @author ZhiYong
 * @date 10点58分
 * @desc 模拟future异步处理数据,wait()/notify()/synchronized三者结合处理
 *        类似于ajax方式
 */
public class MyFutureDemo {

    interface Data{
        //获取返回结果
        String getResponse();
    }

    class RealData implements Data{

        private String result;

        public RealData(String requestParam){
            System.out.println("根据请求参数:"+requestParam+"，去查询相应的结果，大概耗时5s");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("请求处理完毕");
            result = "查询结果为：数据正确";
        }

        @Override
        public String getResponse() {
            return result;
        }
    }

    class FutureData implements Data{
        private RealData realData;
        private boolean isReady = false;
        public synchronized void setRealData(RealData realData){
            if(isReady){
                return;
            }
            this.realData = realData;
            isReady = true;
            notify();
        }

        @Override
        public synchronized String getResponse() {
            while (!isReady){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return this.realData.getResponse();
        }
    }

    class FutureClient{
        public Data request(final String requestStr){
            final FutureData futureData = new FutureData();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    RealData realData = new RealData(requestStr);
                    futureData.setRealData(realData);
                }
            }).start();
            return futureData;
        }
    }

    public static void main(String[] args) {
        MyFutureDemo futureDemo = new MyFutureDemo();
        MyFutureDemo.FutureClient futureClient = futureDemo.new FutureClient();
        MyFutureDemo.Data data = futureClient.request("zhangsan");
        System.out.println("请发送理完毕");
        System.out.println("做其他的业务逻辑");
        String result = data.getResponse();
        System.out.println(result);
    }
}
