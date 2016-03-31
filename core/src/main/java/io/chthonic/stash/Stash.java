package io.chthonic.stash;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.chthonic.stash.cache.StorageCache;
import io.chthonic.stash.serializers.ObjectSerializer;
import io.chthonic.stash.storage.Storage;

/**
 * Created by jhavatar on 3/27/2016.
 */
public class Stash implements Storage, StashComponents{
    static final String TAG = "SyncAdapter";
    private final Storage storage;
    private final StorageCache cache;

    @Nullable
    @Override
    public StorageCache getCache() {
        return cache;
    }

    @NotNull
    @Override
    public Storage getStorage() {
        return storage;
    }

    @Override
    public boolean hasCache() {
        return cache != null;
    }

    @Nullable
    public Object getCached(@NotNull final String key) {
        if (hasCache()) {
            return getCache().get(key);
        } else {
            return null;
        }
    }

    public Stash(@NotNull Storage storage, StorageCache cache) {
        this.storage = storage;
        this.cache = cache;
    }

    @Override
    public synchronized boolean contains(@NotNull final String key) {
        return getStorage().contains(key);
    }

    @Override
    public synchronized void putBoolean(@NotNull final String key, final boolean val) {
        if (hasCache()) {
            getCache().put(key, val);
        }

        getStorage().putBoolean(key, val);
    }

    @Override
    public synchronized void putInt(@NotNull final String key, final int val) {
        if (hasCache()) {
            getCache().put(key, val);
        }

        getStorage().putInt(key, val);
    }

    @Override
    public synchronized void putLong(@NotNull final String key, final long val) {
        if (hasCache()) {
            getCache().put(key, val);
        }

        getStorage().putLong(key, val);
    }

    @Override
    public synchronized void putFloat(@NotNull final String key, final float val) {
        if (hasCache()) {
            getCache().put(key, val);
        }

        getStorage().putFloat(key, val);
    }

    @Override
    public synchronized void putDouble(@NotNull final String key, final double val) {
        if (hasCache()) {
            getCache().put(key, val);
        }

        getStorage().putDouble(key, val);
    }

    @Override
    public synchronized void putObject(@NotNull final String key, @NotNull final Object val, @NotNull final ObjectSerializer serializer) {
        if (hasCache()) {
            getCache().put(key, val);
        }

        getStorage().putObject(key, val, serializer);
    }

    public synchronized void putString(@NotNull final String key, @NotNull final String val) {
        if (hasCache()) {
            getCache().put(key, val);
        }

        getStorage().putString(key, val);
    }

    @Override
    public synchronized boolean getBoolean(@NotNull final String key, final boolean backupVal) {

        try {
            Object val = getCached(key);
            if (val != null) {
                return (boolean) val;
            }
        } catch (Exception eCache) {
            Log.v(TAG, "cache.getBoolean error", eCache);
        }

        try {
            boolean val = getStorage().getBoolean(key, backupVal);
            if (hasCache()) {
                getCache().put(key, val);
            }
            return val;

        } catch (Exception eDB) {
            Log.v(TAG, "storage.getBoolean error", eDB);
        }

        return backupVal;
    }

    @Override
    public synchronized int getInt(@NotNull final String key, final int backupVal) {

        try {
            Object val = getCached(key);
            if (val != null) {
                return (int) val;
            }
        } catch (Exception eCache) {
            Log.v(TAG, "cache.getInt error", eCache);
        }

        try {
            int val = getStorage().getInt(key, backupVal);
            if (hasCache()) {
                getCache().put(key, val);
            }
            return val;

        } catch (Exception eDB) {
            Log.v(TAG, "storage.getInt error", eDB);
        }

        return backupVal;
    }

    @Override
    public synchronized long getLong(@NotNull final String key, final long backupVal) {

        try {
            Object val = getCached(key);
            if (val != null) {
                return (long) val;
            }
        } catch (Exception eCache) {
            Log.v(TAG, "cache.getLong error", eCache);
        }

        try {
            long val = getStorage().getLong(key, backupVal);
            if (hasCache()) {
                getCache().put(key, val);
            }
            return val;

        } catch (Exception eDB) {
            Log.v(TAG, "storage.getLong error", eDB);
        }

        return backupVal;
    }

    @Override
    public synchronized float getFloat(@NotNull final String key, final float backupVal) {

        try {
            Object val = getCached(key);
            if (val != null) {
                return (float) val;
            }
        } catch (Exception eCache) {
            Log.v(TAG, "cache.getFloat error", eCache);
        }

        try {
            float val = getStorage().getFloat(key, backupVal);
            if (hasCache()) {
                getCache().put(key, val);
            }
            return val;

        } catch (Exception eDB) {
            Log.v(TAG, "storage.getFloat error", eDB);
        }

        return backupVal;
    }

    @Override
    public synchronized double getDouble(@NotNull final String key, final double backupVal) {

        try {
            Object val = getCached(key);
            if (val != null) {
                return (double) val;
            }
        } catch (Exception eCache) {
            Log.v(TAG, "cache.getDouble error", eCache);
        }

        try {
            double val = getStorage().getDouble(key, backupVal);
            if (hasCache()) {
                getCache().put(key, val);
            }
            return val;

        } catch (Exception eDB) {
            Log.v(TAG, "storage.getDouble error", eDB);
        }

        return backupVal;
    }


    @Nullable
    @Override
    public synchronized String getString(@NotNull final String key, @Nullable final String backupVal) {

        try {
            Object val = getCached(key);
            if (val != null) {
                return (String) val;
            }
        } catch (Exception eCache) {
            Log.v(TAG, "cache.getString error", eCache);
        }

        try {
            String val = getStorage().getString(key, backupVal);
            if (hasCache() && (val != null)) {
                getCache().put(key, val);
            }
            return val;

        } catch (Exception eDB) {
            Log.v(TAG, "storage.getString error", eDB);
        }

        return backupVal;
    }


    @Nullable
    @Override
    public synchronized Object getObject(@NotNull final String key, @Nullable final Object backupVal, @NotNull final ObjectSerializer serializer) {

        try {
            Object val = getCached(key);
            if (val != null) {
                return val;
            }
        } catch (Exception eCache) {
            Log.v(TAG, "cache.getObject error", eCache);
        }

        try {
            Object val = getStorage().getObject(key, backupVal, serializer);
            if (hasCache() && (val != null)) {
                getCache().put(key, val);
            }
            return val;

        } catch (Exception eDB) {
            Log.v(TAG, "storage.getObject error", eDB);
        }

        return backupVal;
    }


    @Override
    public synchronized void del(@NotNull final String key) {
        getStorage().del(key);
        if (hasCache()) {
            getCache().del(key);
        }
    }

    @Override
    public synchronized void close() {
        getStorage().close();
        if (hasCache()) {
            getCache().close();
        }
    }


    public static class Builder {
        private Storage storage = null;
        private StorageCache cache = null;

        public Builder(Storage storage) {
            this.storage = storage;
        }

        public Builder cache(StorageCache cache) {
            this.cache = cache;
            return this;
        }

        public Stash build() {
            return new Stash(this.storage, this.cache);
        }
    }
}
