package zklock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/3/3
 * @since 1.0.0
 */
public class OrderNumCreateUtil {

    public static AtomicInteger atomicInteger = new AtomicInteger(1);

    public String getOrderNumber() {
        int andIncrement = atomicInteger.getAndIncrement();
        System.out.println("生成订单号："+andIncrement);
        return andIncrement + "";
    }
}