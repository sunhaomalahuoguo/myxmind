package multithreadingAndHighConcurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerAndConsumerDemo {

	public static int count = 0; 
	public static int MaxSize = 3; 
	ReentrantLock lock = new ReentrantLock();
	Condition producerCondition = lock.newCondition();
	Condition consumerCondition = lock.newCondition();
	
	public static void main(String[] args) {

		
		ProducerAndConsumerDemo test = new ProducerAndConsumerDemo();
		new Thread(test.new Producer()).start();
		new Thread(test.new Producer()).start();
		
		new Thread(test.new Consumer()).start();
		new Thread(test.new Consumer()).start();
	}
	
	class Producer implements Runnable {

		@Override
		public void run() {
			
			for (int i = 0; i < 10; i++) {
				
				lock.lock();
				
				try {
					while (count >= MaxSize) {
						producerCondition.await();
						System.out.println("�����߳�"+Thread.currentThread().getName() + "���������Ѵﵽ���ޣ�����ȴ�״̬");
					} 
					count++;
					System.out.println("�����߳�"+Thread.currentThread().getName() + "������Ŀǰ��������:"+count);
					//���������̣߳���ʼ����
					consumerCondition.signalAll();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
			
			
		}
	}

	class Consumer implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				lock.lock();
				try {
					while (count <= 0) {
						System.out.println("�����߳�"+Thread.currentThread().getName() + "�����Ѳ�ƷΪ0������ȴ�״̬");
						consumerCondition.await();
					} 
					count--;
					System.out.println("�����߳�"+Thread.currentThread().getName() + "������Ŀǰ������:"+count);
					producerCondition.signalAll();
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
			
		}
	}
}

