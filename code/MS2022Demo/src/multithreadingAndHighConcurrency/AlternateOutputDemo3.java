package multithreadingAndHighConcurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AlternateOutputDemo3 {

	static Thread t1 = null;
	static Thread t2 = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Lock loct = new ReentrantLock(); // 锁
		Condition condition = loct.newCondition(); // 就是个队列

		char[] charArray = "ABCDE".toCharArray();
		char[] numArray = "12345".toCharArray();

		t1 = new Thread(() -> {
			try {
				loct.lock();
				for (char n : numArray) {
					System.out.print(n);

					condition.signal();
					condition.await();
				}
				condition.signal();//两个线程此处都要加唤醒对方，否则两个都在等待，程序就结束不了了
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				loct.unlock();
			}


		}, "t1");

		t2 = new Thread(() -> {
			try {
				loct.lock();
				for (char c : charArray) {
					System.out.print(c);
					condition.signal();
					condition.await();

				}
				condition.signal();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				loct.unlock();
			}

		}, "t2");

		t1.start();
		t2.start();
	}
}
