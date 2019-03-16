package com.thread.demo.demo01;

import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author ZhiYong
 * @date 23点56分
 * @desc PriorityBlockingQueue。这是一个无界有序的阻塞队列，排序规则和之前介绍的PriorityQueue一致，只是增加了阻塞操作。
 *        同样的该队列不支持插入null元素，同时不支持插入非comparable的对象。它的迭代器并不保证队列保持任何特定的顺序，如果想要顺序遍历，
 *        考虑使用Arrays.sort(pq.toArray())。该类不保证同等优先级的元素顺序，如果你想要强制顺序，就需要考虑自定义顺序或者是Comparator使用第二个比较属性
 */
public class PriorityBlockingQueueDemo {

    class Task implements Comparable<Task>{

        private int id ;

        private String name;

        public Task(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Task{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        @Override
        public int compareTo(Task o) {
            return this.id > o.id  ? 1 : (this.id < o.id ? -1 : 0);
        }
    }


    public static void main(String[] args) {
        PriorityBlockingQueueDemo demo = new PriorityBlockingQueueDemo();
        Task t1 = demo.new Task(1,"张三1");
        Task t2 = demo.new Task(6,"张三6");
        Task t3 = demo.new Task(4,"张三4");
        PriorityBlockingQueue<Task> queue = new PriorityBlockingQueue<>();
        queue.add(t1);
        queue.add(t2);
        queue.add(t3);

        for (Iterator iter = queue.iterator(); iter.hasNext();) {
            try {
                /**
                 * 排序是在调用take方法的时候才开始排序的，并不是在往队列中添加的时候开始排序的
                 */
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
