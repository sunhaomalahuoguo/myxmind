package zklock;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/3/3
 * @since 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        for (int i = 1; i <=50 ; i++) {
            new Thread(() -> {
                new OrderService().getOrderNumber();
            }).start();
        }
    }

}