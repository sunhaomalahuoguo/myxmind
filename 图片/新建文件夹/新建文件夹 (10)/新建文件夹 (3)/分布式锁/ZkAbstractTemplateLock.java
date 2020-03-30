package zklock;

import org.I0Itec.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/3/3
 * @since 1.0.0
 */
public abstract class ZkAbstractTemplateLock implements ZkLock {

    //zookeeper连接地址
    public static final String ZKSERVER = "192.168.111.144:2181";
    //超时时间
    public static final int TIME_OUT = 45*1000;

    protected String path = "zklock0401";

    protected CountDownLatch countDownLatch = null;

    ZkClient zkClient = new ZkClient(ZKSERVER,TIME_OUT);

    @Override
    public void zkLock() {

        if(tryZkLock()){
            System.out.println(Thread.currentThread().getName()+"\t 占用锁成功");
        }else {
            waitZkLock();
            //递归
            zkLock();
        }
    }

    //下面两个抽象方法，利用的是模板方法设计模式
    public abstract void waitZkLock();

    public abstract boolean tryZkLock();

    @Override
    public void zkUnlock() {

        if(zkClient != null){
            zkClient.close();
        }
        System.out.println(Thread.currentThread().getName()+"\t 释放锁成功");

        System.out.println();
        System.out.println();

    }
}