package zklock;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/3/3
 * @since 1.0.0
 */
public class OrderService {

    private OrderNumCreateUtil orderNumCreateUtil = new OrderNumCreateUtil();

    private ZkLock zkLock = new ZkDistributedLock();

    public String getOrderNumber(){
        String orderNumber = null;
        zkLock.zkLock();
        try {
            orderNumber = orderNumCreateUtil.getOrderNumber();
            System.out.println("获得编号："+orderNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderNumber;
    }

}