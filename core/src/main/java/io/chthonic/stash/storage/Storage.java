package io.chthonic.stash.storage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.chthonic.stash.serializers.ObjectSerializer;

/**
 * Created by jhavatar on 3/26/2016.
 */
public interface Storage {

    boolean contains(String key);

    void putBoolean(@NotNull final String key, final boolean val);
    void putInt(@NotNull final String key, final int val);
    void putLong(@NotNull final String key, final long val);
    void putFloat(@NotNull final String key, final float val);
    void putDouble(@NotNull final String key, final double val);
    void putObject(@NotNull final String key, @NotNull final Object val, @NotNull final ObjectSerializer serializer);
    void putString(@NotNull final String key, @NotNull final String val);

    boolean getBoolean(@NotNull final String key, final boolean backupVal);
    int getInt(@NotNull final String key, final int backupVal);
    long getLong(@NotNull final String key, final long backupVal);
    float getFloat(@NotNull final String key, final float backupVal);
    double getDouble(@NotNull final String key, final double backupVal);
    @Nullable
    String getString(@NotNull final String key, @Nullable final String backupVal);
    @Nullable
    Object getObject(@NotNull final String key, @Nullable final Object backupVal, @NotNull final ObjectSerializer serializer);

    void del(@NotNull final String key);

    void close();
}
