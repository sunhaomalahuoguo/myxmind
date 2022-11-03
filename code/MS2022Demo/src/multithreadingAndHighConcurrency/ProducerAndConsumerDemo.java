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
						System.out.println("生产线程"+Thread.currentThread().getName() + "生产能力已达到上限，进入等待状态");
					} 
					count++;
					System.out.println("生产线程"+Thread.currentThread().getName() + "生产者目前生产总数:"+count);
					//唤醒消费线程，开始消费
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
						System.out.println("消费线程"+Thread.currentThread().getName() + "可消费产品为0，进入等待状态");
						consumerCondition.await();
					} 
					count--;
					System.out.println("生产线程"+Thread.currentThread().getName() + "消费者目前消费数:"+count);
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

