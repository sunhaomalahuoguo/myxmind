package multithreadingAndHighConcurrency;

import java.util.concurrent.locks.LockSupport;

public class AlternateOutputDemo {

	static Thread t1 = null;
	static Thread t2 = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		char[] charArray = "ABCDE".toCharArray();
		char[] numArray = "12345".toCharArray();
		
		t1 = new Thread(()->{
			for (char c : charArray) {
				System.out.print(c);
				LockSupport.unpark(t2);
				LockSupport.park();
			}
			
		}, "t1") ;
		
		t2 = new Thread(()->{
			for (char n : numArray) {
				LockSupport.park();
				System.out.print(n);
				LockSupport.unpark(t1);
			}
		}, "t2") ;
		
		t1.start();
		t2.start();
	}
}
