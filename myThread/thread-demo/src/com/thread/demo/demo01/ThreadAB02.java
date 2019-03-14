package com.thread.demo.demo01;

/**
 * @author zhengzy
 * @date 2019年3月14日 10:30:34
 * @desc
 */
public class ThreadAB02 {

	class Flag{
		private volatile boolean  flag = true;
	}

	class ThreadA implements Runnable{
		private Flag flag;
		public ThreadA(Flag flag) {
			this.flag = flag;
		}

		@Override
		public void run() {
			for(int i = 1; i<=50; i++) {
				synchronized(flag) {
					for(int n = 1; n<=10; n++) {
						System.out.println(Thread.currentThread().getName()+" ------ 第"+i+"-"+n+"次");
					}
					if(flag.flag) {
						flag.flag = false;
						flag.notify();
						if(i<=49) {
							try {
								flag.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	class ThreadB implements Runnable{
		private Flag flag;
		public ThreadB(Flag flag) {
			this.flag = flag;
		}

		@Override
		public void run() {
			for(int i = 1; i<=50; i++) {
				synchronized(flag) {
					for(int n = 1; n<=20; n++) {
						System.out.println(Thread.currentThread().getName()+" ------ 第"+i+"-"+n+"次");
					}
					if(!flag.flag) {
						flag.flag = true;
						flag.notify();
						if(i<=49) {
							try {
								flag.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		ThreadAB02 thread = new ThreadAB02();
		ThreadAB02.Flag flag = thread.new Flag();
		new Thread(thread.new ThreadA(flag),"thread-A").start();
		new Thread(thread.new ThreadB(flag),"thread-B").start();
	}
}
