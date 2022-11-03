package multithreadingAndHighConcurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AliQuestionAboutCf {

	private static enum Result{
		SUCCESS,FAIL,CANCELLED
	}
	
	static List<MyTask> tasks = new ArrayList<>();
	
	public static void main(String[] args) throws Exception {
		MyTask task1 = new MyTask("task1",2,Result.SUCCESS);
		MyTask task2 = new MyTask("task2",4,Result.SUCCESS);
		MyTask task3 = new MyTask("task3",1,Result.FAIL);
		
		tasks.add(task1);
		tasks.add(task2);
		tasks.add(task3);
		
		for (MyTask task : tasks) {
			CompletableFuture f = CompletableFuture.supplyAsync(()->task.runTask())
					.thenAccept((result)->callback(result,task));
		}
		TimeUnit.SECONDS.sleep(10);

	}
	
	private static Result callback(Result result, MyTask task) {
		if (Result.FAIL == result) {
			for (MyTask myTask : tasks) {
				if (myTask != task) {
					try {
						myTask.cancel();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return result;
	}

	static class MyTask {
		
		private String name;
		private int timeInSeconde;
		private Result result;
		
		boolean cancelling = false;
		volatile boolean cancelled = false;

		public MyTask(String name, int timeInSeconde, Result result) {
			this.name = name;
			this.timeInSeconde = timeInSeconde;
			this.result = result;
		}

		public void cancel() throws Exception {
			cancelling = true;
			synchronized (this) {
				System.out.println(name + "-cancelling");
				TimeUnit.MILLISECONDS.sleep(50);
				System.out.println(name + "-cancelled");
			}
			cancelled = true;
		}

		public Result runTask() {
			int interval = 100;
			int total = 0;
			
			try {
				for (;;) {
					Thread.sleep(interval);
					if (cancelling) {
						continue;
					}
					total += interval;
					if (total >= timeInSeconde) {
						break;
					}
					
					if (cancelled) {
						return Result.CANCELLED;
					}

				} 
			} catch (Exception e) {
				
			}
			
			System.out.println(name + "end!");
			return result;
		}

	}
}

