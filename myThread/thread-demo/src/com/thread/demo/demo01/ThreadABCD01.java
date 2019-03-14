package com.thread.demo.demo01;

/**
 * @author zheng
 * @date 2019年3月14日 10:31:04
 * @desc 要求, 同时启动四个线程, 按顺序输出ABCD, 且未无限循环输出。
 */
public class ThreadABCD01 {

	class ThreadNum {
		private volatile int i;
	}

	class ThreadA implements Runnable {
		ThreadNum threadNum;

		public ThreadA(ThreadNum threadNum) {
			this.threadNum = threadNum;
		}

		@Override
		public void run() {
			while (true) {
				synchronized (threadNum) {
					if (threadNum.i % 4 == 0) {
						System.out.println(Thread.currentThread().getName() + "----A");
						threadNum.i++;
					}
				}
			}
		}
	}

	class ThreadB implements Runnable {
		ThreadNum threadNum;

		public ThreadB(ThreadNum threadNum) {
			this.threadNum = threadNum;
		}

		@Override
		public void run() {
			while (true) {
				synchronized (threadNum) {
					if (threadNum.i % 4 == 1) {
						System.out.println(Thread.currentThread().getName() + "----B");
						threadNum.i++;
					}
				}
			}
		}
	}

	class ThreadC implements Runnable {
		ThreadNum threadNum;

		public ThreadC(ThreadNum threadNum) {
			this.threadNum = threadNum;
		}

		@Override
		public void run() {
			while (true) {
				synchronized (threadNum) {
					if (threadNum.i % 4 == 2) {
						System.out.println(Thread.currentThread().getName() + "----C");
						threadNum.i++;
					}
				}
			}
		}
	}

	class ThreadD implements Runnable {
		ThreadNum threadNum;

		public ThreadD(ThreadNum threadNum) {
			this.threadNum = threadNum;
		}

		@Override
		public void run() {
			while (true) {
				synchronized (threadNum) {
					if (threadNum.i % 4 == 3) {
						System.out.println(Thread.currentThread().getName() + "----D");
						threadNum.i++;
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		ThreadABCD01 abcd = new ThreadABCD01();
		ThreadABCD01.ThreadNum threadNum = abcd.new ThreadNum();
		new Thread(abcd.new ThreadA(threadNum), "thread-A").start();
		new Thread(abcd.new ThreadB(threadNum), "thread-B").start();
		new Thread(abcd.new ThreadC(threadNum), "thread-C").start();
		new Thread(abcd.new ThreadD(threadNum), "thread-D").start();
	}

}