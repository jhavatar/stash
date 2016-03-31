package io.chthonic.stash.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;

import io.chthonic.stash.serializers.ObjectSerializer;


public class SharedPrefsStorage implements Storage {

    static final String TAG = "SharedPrefsStorage";
    private SharedPreferences db;

    public SharedPrefsStorage(Context context, String name, int operatingMode) {
        db = context.getSharedPreferences(name, operatingMode);
    }

    @Override
    public synchronized boolean contains(@NotNull final String key) {
        return db.contains(key);
    }

    @Override
    public synchronized void putBoolean(@NotNull final String key, final boolean val) {
        SharedPreferences.Editor edit = db.edit();
        edit.putBoolean(key, val);
        edit.apply();
    }

    @Override
    public synchronized void putInt(@NotNull final String key, final int val) {
        SharedPreferences.Editor edit = db.edit();
        edit.putInt(key, val);
        edit.apply();
    }

    @Override
    public synchronized void putLong(@NotNull final String key, final long val) {
        SharedPreferences.Editor edit = db.edit();
        edit.putLong(key, val);
        edit.apply();
    }


    @Override
    public synchronized void putFloat(@NotNull final String key, final float val) {
        SharedPreferences.Editor edit = db.edit();
        edit.putFloat(key, val);
        edit.apply();
    }

    @Override
    public synchronized void putDouble(@NotNull final String key, final double val) {
        SharedPreferences.Editor edit = db.edit();
        edit.putLong(key, Double.doubleToLongBits(val));
        edit.apply();
    }


    @Override
    public synchronized void putObject(@NotNull final String key, @NotNull final Object val, @NotNull final ObjectSerializer serializer) {
        SharedPreferences.Editor edit = db.edit();
        String serialized = serializer.serialize(val);
        edit.putString(key, serialized);
        edit.apply();
    }

    @Override
    public synchronized void putString(@NotNull final String key, @NotNull final String val) {
        SharedPreferences.Editor edit = db.edit();
        edit.putString(key, val);
        edit.apply();
    }


    @Override
    public synchronized boolean getBoolean(@NotNull final String key, final boolean backupVal) {
        try {
            if (db.contains(key)) {
                return db.getBoolean(key, backupVal);
            }

        } catch (Exception eDB) {
            Log.v(TAG, "getBoolean error", eDB);
        }

        return backupVal;
    }


    @Override
    public synchronized int getInt(@NotNull final String key, final int backupVal) {

        try {
            if (db.contains(key)) {
                return db.getInt(key, backupVal);
            }

        } catch (Exception eDB) {
            Log.v(TAG, "getInt error", eDB);
        }

        return backupVal;
    }


    @Override
    public synchronized long getLong(@NotNull final String key, final long backupVal) {

        try {
            if (db.contains(key)) {
                return db.getLong(key, backupVal);
            }
        } catch (Exception eDB) {
            Log.v(TAG, "getLong error", eDB);
        }

        return backupVal;
    }


    @Override
    public synchronized float getFloat(@NotNull final String key, final float backupVal) {

        try {
            if (db.contains(key)) {
               return db.getFloat(key, backupVal);
            }
        } catch (Exception eDB) {
            Log.v(TAG, "getFloat error", eDB);
        }

        return backupVal;
    }


    @Override
    public synchronized double getDouble(@NotNull final String key, final double backupVal) {

        try {
            if (db.contains(key)) {
                long backupLongBits = Double.doubleToLongBits(backupVal);
                long longBits = db.getLong(key, backupLongBits);
                return Double.longBitsToDouble(longBits);
            }
        } catch (Exception eDB) {
            Log.v(TAG, "getDouble error", eDB);
        }

        return backupVal;
    }


    @Override
    @Nullable
    public synchronized String getString(@NotNull final String key, @Nullable final String backupVal) {

        try {
            if (db.contains(key)) {
                return db.getString(key, backupVal);
            }
        } catch (Exception eDB) {
            Log.v(TAG, "getString error", eDB);
        }

        return backupVal;
    }


    @Override
    @Nullable
    public synchronized Object getObject(@NotNull final String key, @Nullable final Object backupVal, @NotNull final ObjectSerializer serializer) {

        try {
            if (db.contains(key)) {
                String serialized = db.getString(key, null);
                if (serialized != null) {
                    return serializer.deserialize(serialized);
                }
            }
        } catch (Exception eDB) {
            Log.v(TAG, "getObject error", eDB);
        }

        return backupVal;
    }


    @Override
    public synchronized void del(@NotNull final String key) {
        SharedPreferences.Editor edit = db.edit();
        edit.remove(key);
        edit.apply();
    }


    @Override
    public synchronized void close() {
        db = null;
    }


    public static class Builder {
        private int operatingMode = Context.MODE_PRIVATE;
        private String name = "_storage";

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder operatingMode(int operatingMode) {
            this.operatingMode = operatingMode;
            return this;
        }

        public SharedPrefsStorage build(Context context) {
            return new SharedPrefsStorage(context, this.name, this.operatingMode);
        }
    }

}