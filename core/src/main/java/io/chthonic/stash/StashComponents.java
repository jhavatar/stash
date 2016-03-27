package io.chthonic.stash;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.chthonic.stash.cache.StorageCache;
import io.chthonic.stash.storage.Storage;

/**
 * Created by jhavatar on 3/27/2016.
 */
public interface StashComponents {

    @Nullable
    StorageCache getCache();

    @NotNull
    Storage getStorage();

    boolean hasCache();
}
