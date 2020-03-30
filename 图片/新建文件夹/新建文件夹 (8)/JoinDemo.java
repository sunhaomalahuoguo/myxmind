package thread;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/3/20
 * @since 1.0.0
 */
public class JoinDemo {

    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {
            System.out.println("t1线程运行");
        });
        Thread t2 = new Thread(() -> {
            System.out.println("t2线程运行");
        });
        Thread t3 = new Thread(() -> {
            System.out.println("t3线程运行");
        });

        t1.start();
        t1.join();  //表示需要等t1执行完成，其他线程才能执行
        t2.start();
        t2.join();  //表示需要等t2执行完成，其他线程才能执行
        t3.start();
        t3.join();  //表示需要等t3执行完成，其他线程才能执行
    }
}