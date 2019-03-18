package com.thread.demo.demo01;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * @author ZhiYong
 * @date 20点29分
 * @desc CyclicBarrier demo
 * CyclicBarrier 是属于多个线程阻塞等待
 */
public class CyclicBarrierDemo {

    static class Runner implements Runnable{

        private CyclicBarrier cyclicBarrier;

        private String name;

        public Runner(CyclicBarrier cyclicBarrier, String name) {
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
        }

        public CyclicBarrier getCyclicBarrier() {
            return cyclicBarrier;
        }

        public void setCyclicBarrier(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000* new Random().nextInt(4));
                System.out.println(this.name+"准备完成");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(this.name+" 出发");
        }
    }

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        Runner runner1 = new Runner(cyclicBarrier,"张三");
        Runner runner2 = new Runner(cyclicBarrier,"李四");
        Runner runner3 = new Runner(cyclicBarrier,"王五");

        ExecutorService service = newFixedThreadPool(3);
        service.execute(runner1);
        service.execute(runner2);
        service.execute(runner3);
        /**区别：
         * submit有返回值，并且submit中传入的参数可以是实现了Callable接口的类；
         * execute没有返回值，只能传入实现了Runnable接口的类
         */
        //service.submit(runner3);
        service.shutdown();
    }

}
