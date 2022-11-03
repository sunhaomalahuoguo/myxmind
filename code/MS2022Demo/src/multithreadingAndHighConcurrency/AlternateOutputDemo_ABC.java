package multithreadingAndHighConcurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AlternateOutputDemo_ABC {

	static Thread t1 = null;
	static Thread t2 = null;
	static Thread t3 = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CountDownLatch latchB = new CountDownLatch(1);
		CountDownLatch latchC = new CountDownLatch(1);
		Lock loct = new ReentrantLock(); // 锁
		Condition conditionA = loct.newCondition(); // 就是个队列
		Condition conditionB = loct.newCondition(); // 就是个队列
		Condition conditionC = loct.newCondition(); // 就是个队列
		
		t1 = new Thread(() -> {
			try {
				loct.lock();
				for (int i = 0; i < 10; i++) {
					System.out.print("A");
					conditionB.signal();
					if (i == 0) {
						latchB.countDown();
					}
					conditionA.await();
				}
				conditionB.signal();//两个线程此处都要加唤醒对方，否则两个都在等待，程序就结束不了了
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				loct.unlock();
			}


		}, "t1");

		t2 = new Thread(() -> {
			try {
				latchB.await();
				loct.lock();
				for (int i = 0; i < 10; i++) {
					System.out.print("B");
					conditionC.signal();
					if (i == 0) {
						latchC.countDown();
					}
					conditionB.await();

				}
				conditionC.signal();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				loct.unlock();
			}

		}, "t2");
		
		t3 = new Thread(() -> {
			try {
				latchC.await();
				loct.lock();
				for (int i = 0; i < 10; i++) {
					System.out.print("C");
					conditionA.signal();
					conditionC.await();

				}
				conditionA.signal();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				loct.unlock();
			}

		}, "t3");

		t1.start();
		t2.start();
		t3.start();
	}
}
