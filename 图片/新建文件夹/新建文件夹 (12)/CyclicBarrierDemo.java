package juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/3/28
 * @since 1.0.0
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        CyclicBarrier cb = new CyclicBarrier(7,()->{System.out.println("集齐七龙珠，召唤神龙");});

        for (int i = 1; i <= 7 ; i++) {
            int finalI = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"集齐第"+ finalI + "颗龙珠");
                try {
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}