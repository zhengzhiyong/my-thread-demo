package com.thread.demo.demo01.practice;

/**
 *
 * @author zhengzy
 * @date 2019年3月14日 10:29:50
 * @desc 线程A循环10次，线程B循环100，接着又回到线程A循环10次，接着再回到线程B又循环100，如此循环50次。
 *
 */
public class ThreadAB01 {
	// 两个线程，交替执行的一个标志
	volatile boolean flag = true;

	public static void main(String[] args) {
		final ThreadAB01 thread = new ThreadAB01();

		//线程A
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int k = 1 ;k<=50 ;k++) {
					synchronized(thread) {
						for(int i=1;i<=10;i++) {
							System.out.println(Thread.currentThread().getName()+" ------ 第"+k+"-"+i+"次");
						}
						if(thread.flag) {
							thread.flag = false;
							thread.notify();
							//此处和49作比较，是希望只循环50次
							if(k <=49) {
								try {
									thread.wait();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		},"Thread-A").start();


		//线程B
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(int k = 1 ;k<=50 ;k++) {
					synchronized(thread) {
						for(int i=1;i<=100;i++) {
							System.out.println(Thread.currentThread().getName()+" ------ 第"+k+"-"+i+"次");
						}
						if(!thread.flag) {
							thread.flag = true;
							thread.notify();
							//此处和49作比较，是希望只循环50次
							if(k <=49) {
								try {
									thread.wait();
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		},"Thread-B").start();

	}

}
