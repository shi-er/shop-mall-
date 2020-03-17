package com.li.yun.biao.goods.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import javax.annotation.Resource;

/**
 * <p>
 * ehcache 缓存工具类
 * </p>
 * <p>
 * cacheName在ehcache.xml中配置
 * </p>
 */
public class EhcacheHelper {

    @Resource
    private static CacheManager cacheManager;

    public static Object get(String cacheName, Object key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            Element element = cache.get(key);
            if (element != null) {
                return element.getObjectValue();
            }
        }
        return null;
    }

    public static void put(String cacheName, Object key, Object value) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(new Element(key, value));
        }
    }

    public static boolean remove(String cacheName, Object key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            return cache.remove(key);
        }
        return false;
    }

    public static boolean cleanall() {
        cacheManager.clearAll();
        return true;
    }

}
