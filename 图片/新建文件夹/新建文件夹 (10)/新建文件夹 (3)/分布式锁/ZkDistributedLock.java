package zklock;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/3/3
 * @since 1.0.0
 */
public class ZkDistributedLock extends ZkAbstractTemplateLock{

    @Override
    public boolean tryZkLock() {
        try {
            zkClient.createEphemeral(path);
            return true;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void waitZkLock() {
        IZkDataListener iZkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                //监听节点是否被删除
                if(countDownLatch != null){
                    countDownLatch.countDown();
                }
            }
        };
        //添加监听器
        zkClient.subscribeDataChanges(path,iZkDataListener);

        if(zkClient.exists(path)){
            //节点被占，只能等着
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //解除监听
            zkClient.unsubscribeDataChanges(path,iZkDataListener);
        }
    }
}