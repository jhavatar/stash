package io.chthonic.stash.cache;

import android.support.v4.util.LruCache;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jhavatar on 3/26/2016.
 */
public class LruStorageCache implements StorageCache{
    private LruCache<String, Object> cache;
    private int cacheRequest;
    private int cacheHit;

    public LruStorageCache(int lruCacheSize) {
        cache = new LruCache<String, Object>(lruCacheSize);
    }

    @Override
    public synchronized boolean contains(final String key) {
        return cache.get(key) != null;
    }

    @Override
    public synchronized int getCacheHits() {
        return cacheHit;
    }

    @Override
    public synchronized int getCacheRequests() {
        return cacheRequest;
    }

    @Nullable
    @Override
    public synchronized Object get(@NotNull final String key) {
        cacheRequest++;
        Object val = cache.get(key);
        if (val != null) {
            cacheHit++;
        }

        return val;
    }

    @Override
    public synchronized void put(@NotNull final String key, @NotNull final Object val) {
        if ((key != null) && (val != null)) {
            cache.put(key, val);
        }
    }

    @Override
    public synchronized void del(@NotNull String key) {
        cache.remove(key);
    }

    @Override
    public synchronized void clear() {
        cache.evictAll();
    }

    @Override
    public synchronized void close() {
        clear();
    }
}
