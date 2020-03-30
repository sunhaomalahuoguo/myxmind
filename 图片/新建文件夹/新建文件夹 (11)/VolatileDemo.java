package juc;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/3/27
 * @since 1.0.0
 */
public class VolatileDemo {
    public static void main(String[] args) {
        //1.用来观察volatile对线程间可见性的影响
//        seeOkVolatile();
        //2.验证volatile不保证原子性
        seeNoAtomic();


    }

    private static void seeNoAtomic() {
        MyData myData = new MyData();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData.addPlusPlus();
                }
            },String.valueOf(i)).start();
        }

        //用来判断上面的20个线程是不是全部结束了，这里面的2是因为，程序中默认有两个线程：1.main线程，2.GC线程
        while (Thread.activeCount() > 2){
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + "\t finally number value:" + myData.number);
    }

    private static void seeOkVolatile() {
        MyData myData = new MyData();

        //第一个线程
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t come in");
            try {
                Thread.sleep(3L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + "\t updated number value:" + myData.number);
        },"AAA").start();

        //第二个线程，也就是main线程
        while (myData.number == 0){

        }

        System.out.println(Thread.currentThread().getName() + "\t mission is over");
    }
}
class MyData{
    //观察此处加或不加volatile，程序运行结果的区别
    volatile int number = 0;
    public void addTo60(){
        this.number = 60;
    }

    public void addPlusPlus(){
        this.number ++;
    }
}