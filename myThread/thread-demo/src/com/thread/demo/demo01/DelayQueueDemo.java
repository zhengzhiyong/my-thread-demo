package com.thread.demo.demo01;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueDemo extends Thread{

    class Person implements Delayed{

        private String idCard;

        private String name;

        private long endTime;

        private TimeUnit timeUnit = TimeUnit.SECONDS;

        public Person(String idCard, String name, long endTime) {
            this.idCard = idCard;
            this.name = name;
            this.endTime = endTime;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        /**
         * 用来判断下机时间
         * @param unit
         * @return
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return this.endTime  - System.currentTimeMillis();
        }

        /**
         * 相互比较排序
         * @param o
         * @return
         */
        @Override
        public int compareTo(Delayed o) {
            Person p = (Person)o;
            return this.getDelay(this.timeUnit) - p.getDelay(this.timeUnit) > 0 ? 1 : 0;
        }
    }

    private boolean isWork = true;
    private DelayQueue<Person> queue = new DelayQueue();
    public void work(String name,String idCard,int money){
        Person p = new Person(idCard,name,money*1000+System.currentTimeMillis());
        System.out.println("网名："+p.getName()+" 身份证:"+p.getIdCard()+",充值"+money+"钱,开始上机消费");
        queue.add(p);
    }

    private void noWork(Person p){
        System.out.println("网名："+p.getName()+" 身份证:"+p.getIdCard()+"下机了");
    }

    @Override
    public void run() {
        while (isWork){
            try {
                Person p = queue.take();
                noWork(p);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        DelayQueueDemo demo = new DelayQueueDemo();
        demo.start();
        demo.work("zhangsan","11111",1);
        demo.work("zhangsan4","44444",4);
        demo.work("zhangsan2","22222",2);
    }

}
