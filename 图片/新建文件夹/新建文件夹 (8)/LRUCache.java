package com.mengxuegu.springboot.redis;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 〈手写一个LRU算法〉<br>
 * 〈〉
 *
 * @author sunhao
 * @create 2020/3/16
 * @since 1.0.0
 */
public class LRUCache<K,V> extends LinkedHashMap<K,V> {

    //缓存大小
    private final int CACHE_SIZE;

    /**
     * 传递进来最多能缓存多少数据
     * @param cache_size
     */
    public LRUCache(int cache_size) {
        // true 表示让 linkedHashMap 按照访问顺序来进行排序，最近访问的放在头部，最老访问的放在尾部
        super((int) Math.ceil(cache_size / 0.75),0.75f,true);
        CACHE_SIZE = cache_size;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // 当 map中的数据量大于指定的缓存个数的时候，linkedHashMap就自动删除最老的数据。
        return size() > CACHE_SIZE;
    }
}