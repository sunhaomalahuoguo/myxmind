package juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/3/27
 * @since 1.0.0
 */
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(Thread.currentThread().getName() + " " + atomicInteger.compareAndSet(5, 2019)
                + " current data = " + atomicInteger.get());

        System.out.println(Thread.currentThread().getName() + " " + atomicInteger.compareAndSet(5, 2019)
                + " current data = " + atomicInteger.get());
    }

}