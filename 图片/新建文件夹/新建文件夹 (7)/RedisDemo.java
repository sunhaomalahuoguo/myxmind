package redis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/3/13
 * @since 1.0.0
 */
public class RedisDemo {

    public static void main(String[] args) {
        Set<HostAndPort> set = new HashSet<>();
        set.add(new HostAndPort("192.168.10.11",6379));
        set.add(new HostAndPort("192.168.10.11",6380));
        set.add(new HostAndPort("192.168.10.11",6381));
        set.add(new HostAndPort("192.168.10.11",6382));
        set.add(new HostAndPort("192.168.10.11",6383));
        set.add(new HostAndPort("192.168.10.11",6384));
        JedisCluster jedisCluster = new JedisCluster(set);

        try {
            jedisCluster.set("{user}:name","张三");
            jedisCluster.set("{user}:age","36");
//            String name = jedisCluster.get("name");
//            System.out.println(name);
            List<String> list = jedisCluster.mget("{user}:name", "{user}:age");
            list.stream().forEach(System.out::println);
        } finally {
            try {
                jedisCluster.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}