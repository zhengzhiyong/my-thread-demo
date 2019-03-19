package com.thread.demo.demo01.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 *  Vector /Hashtable/Collections.synchronizedList 等同步类容器效率低下，无法满足高并发下的需求，可能会出现经典的ConcurrentModificationException
 *  可用并发类容器来替代，如：CopyOnWriteArrayList/CopyOnWriteArraySet/ConcurrentHashMap
 *
 *  高并发类容器，底层使用的是Segment来划分了多个小空间，细化了锁的粒度，针对于访问不同的空间来加锁，这样如果访问的不是同一个空间，速度自然就会加快；如果访问的是同一个空间，很不幸依然会处于等待锁释放的状态；
 */
public class SynchronizedContainer {

    private static void concurrentLinkedQueueTest(){
        //非阻塞无界队列，高性能,先进先出原则,元素非空
        ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
        queue.add("A");
        queue.add("B");
        queue.add("C");
        queue.add("D");
        queue.add("E");
        System.out.println(queue.size());
        queue.offer("F");
        System.out.println(queue.size());
        Object object = queue.peek();
        System.out.println(object);
        System.out.println(queue.size());
        Object object1 = queue.poll();
        System.out.println(object1);
        System.out.println(queue.size());
    }

    private static void linkedBlockingQueueTest(){
        //阻塞无界队列，高性能,先进先出原则,元素非空
        //之所以可以实现高性能并发是基于读写分离锁 putLock 和takeLock
        //LinkedBlockingQueue queue = new LinkedBlockingQueue();
        //如果给定界限则，只能存储界限内的个数
        LinkedBlockingQueue queue = new LinkedBlockingQueue(5);
        queue.add("A");
        queue.add("B");
        queue.add("C");
        queue.add("D");
        queue.offer("E");
        //queue.add("F");
        System.out.println(queue.size());
        List<String> list = new ArrayList();
        int size = queue.drainTo(list,2);
        System.out.println(size);
        for (int i = 0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }

    private static void arrayBlockingQueueTest(){
        //阻塞，读和写公用一把锁
        ArrayBlockingQueue queue = new ArrayBlockingQueue(5);
        queue.add("A");
        queue.add("B");
        queue.add("C");
        try {
            queue.put("D");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        queue.offer("E");
        System.out.println(queue.size());
        try {
            System.out.println(queue.offer("F", 2,TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(queue.size());
    }

    private static void synchronousQueueTest(){
        //没有实际大小，SynchronousQueue有两个版本的Transferer实现，一种为公平交易类型，一种为非公平交易类型，
        // 公平交易类型的实现类为TransferQueue，它使用队列来作为交易媒介，请求交易的线程总是先尝试跟队列头部（或者尾部）的线程进行交易，
        // 如果失败再将请求的线程添加到队列尾部，
        // 而非公平类型的实现类为TransferStack，它使用一个stack来作为交易媒介，请求交易的线程总是试图与栈顶线程进行交易，失败则添加到栈顶。
        // 所以SynchronousQueue就是使用队列和栈两种数据结构来模拟公平交易和非公平交易的。

        //生产者线程对其的插入操作put必须等待消费者的移除操作take，反过来也一样。
        SynchronousQueue queue = new SynchronousQueue();
        queue.offer("AAAA");

        //只有当有线程调用take方法的时候，才能去调用add方法。
        //类似于你去火车站买票，当你发出你的需求你要购票需求的时候，售票员才会帮你去查询相关车票信息
        //queue.add(new Object());
    }

    public static void main(String[] args) {
        synchronousQueueTest();
    }
}
