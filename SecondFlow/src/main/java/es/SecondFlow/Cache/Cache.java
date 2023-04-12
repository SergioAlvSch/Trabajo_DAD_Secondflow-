package es.SecondFlow.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class Cache {

    @Autowired
    private CacheManager cacheManager;



    @RequestMapping(value="/cacheUsuarios", method= RequestMethod.GET)
    public Map<Object, Object> getCacheUsuariosContent() {
        ConcurrentMapCacheManager cacheMgr = (ConcurrentMapCacheManager) cacheManager;
        ConcurrentMapCache cache = (ConcurrentMapCache) cacheMgr.getCache("usuarios");
        return cache.getNativeCache();
    }

    /*@RequestMapping(value="/cacheProductos", method= RequestMethod.GET)
    public Map<Object, Object> getCacheProductosContent() {
        ConcurrentMapCacheManager cacheMgr = (ConcurrentMapCacheManager) cacheManager;
        ConcurrentMapCache cache = (ConcurrentMapCache) cacheMgr.getCache("productos");
        return cache.getNativeCache();
    }*/

    @RequestMapping(value="/cacheConversaciones", method= RequestMethod.GET)
    public Map<Object, Object> getCacheConversacionesContent() {
        ConcurrentMapCacheManager cacheMgr = (ConcurrentMapCacheManager) cacheManager;
        ConcurrentMapCache cache = (ConcurrentMapCache) cacheMgr.getCache("conversaciones");
        return cache.getNativeCache();
    }
}
