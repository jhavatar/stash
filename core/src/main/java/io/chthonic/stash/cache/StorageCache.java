package io.chthonic.stash.cache;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jhavatar on 3/26/2016.
 */
public interface StorageCache {

    boolean contains(String key);
    int getCacheHits();
    int getCacheRequests();

    @Nullable
    Object get(@NotNull final String key);
    void put(@NotNull final String key, @NotNull final Object value);
    void del(@NotNull final String key);
    void clear();

    void close();
}
