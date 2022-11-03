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
				latch.await();//Ϊ�˱�֤t2��ִ��
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
				
				o.notify();//�����̴߳˴���Ҫ�ӻ��ѶԷ��������������ڵȴ�������ͽ���������
			}

		}, "t1");

		t2 = new Thread(() -> {
			latch.countDown();//Ϊ�˱�֤t2��ִ��
			
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
				o.notify();//�����̴߳˴���Ҫ�ӻ��ѶԷ��������������ڵȴ�������ͽ���������
			}
			
		}, "t2");

		t1.start();
		t2.start();
	}
}
