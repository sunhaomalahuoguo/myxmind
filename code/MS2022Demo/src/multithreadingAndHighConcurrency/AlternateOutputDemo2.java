package multithreadingAndHighConcurrency;

import java.util.concurrent.CountDownLatch;

public class AlternateOutputDemo2 {

	static Thread t1 = null;
	static Thread t2 = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		CountDownLatch latch = new CountDownLatch(1);
		Object o = new Object();

		char[] charArray = "ABCDE".toCharArray();
		char[] numArray = "12345".toCharArray();

		t1 = new Thread(() -> {
			try {
				latch.await();//为了保证t2先执行
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			synchronized (o) {
				for (char n : numArray) {
					System.out.print(n);
					try {
						o.notify();
						o.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
				
				o.notify();//两个线程此处都要加唤醒对方，否则两个都在等待，程序就结束不了了
			}

		}, "t1");

		t2 = new Thread(() -> {
			latch.countDown();//为了保证t2先执行
			
			synchronized (o) {
				for (char c : charArray) {
					System.out.print(c);
					try {
						o.notify();
						o.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				o.notify();//两个线程此处都要加唤醒对方，否则两个都在等待，程序就结束不了了
			}
			
		}, "t2");

		t1.start();
		t2.start();
	}
}
