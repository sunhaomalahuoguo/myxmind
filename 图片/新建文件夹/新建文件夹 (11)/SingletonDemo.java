package juc;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/3/27
 * @since 1.0.0
 */
public class SingletonDemo {

    private static SingletonDemo singletonDemo = null;

    //私有化构造方法
    private SingletonDemo(){
        System.out.println("SingletonDemo被"+Thread.currentThread().getName()+"创建");
    }

    //双重检查机制
    public static SingletonDemo getInstence(){
        if (singletonDemo == null) {
            /*在这里如果有两条线程都进来了，即便只有一条线程拿到锁创建实例，
            但是另一条还会将实例再创建一遍，所以还要对singletonDemo进行一次分开校验*/
            synchronized (SingletonDemo.class){
                if (singletonDemo == null) {
                    singletonDemo = new SingletonDemo();
                }
            }
        }
        return singletonDemo;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                SingletonDemo.getInstence();
            },String.valueOf(i)).start();
        }
    }
}