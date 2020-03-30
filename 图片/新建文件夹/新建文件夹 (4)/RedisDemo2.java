package com.mengxuegu.springboot.redis;

import org.redisson.Redisson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁2.0版
 */
public class RedisDemo2 {
    @Autowired
    private Redisson redisson;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/deduct_stock")
    public String deductStock(){
        String lockKey = "lockKey";
        /*1.这里的setIfAbsent()方法就相当于redis中的SETNX方法，这里就是利用SETNX方法的特性来实现分布式锁的
        将key设置值为value，如果key不存在，这种情况下等同SET命令。 当key存在时，什么也不做。SETNX是”SET if Not eXists”的简写。
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "sunhao");//jedis.setnx(key,value);
        //2.防止获得分布式锁后，web应用服务器宕机，造成锁无法释放
        stringRedisTemplate.expire(lockKey,10, TimeUnit.SECONDS); //设置redis中lockKey 超时时间为10秒*/

        //1.每条线程都有自己的标识
        String clientId = UUID.randomUUID().toString();
        //2.该方法将获得锁、设置锁超时时间的操作，放在一个方法中，使这两步实现了原子性操作
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey,clientId,10,TimeUnit.SECONDS);

        if (!result){
            return "error";
        }

        try {
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock")); //jedis.get("stock")
            if (stock > 0){
                int realStock = stock - 1;
                stringRedisTemplate.opsForValue().set("stock",realStock + ""); //jedis.set(key,value)
                System.out.println("扣减成功，剩余库存：" +  realStock);
            }else {
                System.out.println("库存扣减失败，库存不足！");
            }
        } finally {
            //当锁是自己的锁时，才允许删除
            if (clientId.equals(stringRedisTemplate.opsForValue().get(lockKey))){
                //3.将释放锁的动作放在finally中，以防在上面减库存的过程中出现异常，使分布式锁无法释放
                stringRedisTemplate.delete(lockKey);
            }
        }
        return "end";
    }
}