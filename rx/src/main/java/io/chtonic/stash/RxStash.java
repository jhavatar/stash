package io.chtonic.stash;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.chthonic.stash.Stash;
import io.chthonic.stash.StashComponents;
import io.chthonic.stash.cache.StorageCache;
import io.chthonic.stash.serializers.ObjectSerializer;
import io.chthonic.stash.storage.Storage;
import rx.Completable;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by jhavatar on 3/27/2016.
 */
public class RxStash implements StashComponents {

    private final Stash stash;

    public Stash getStash() {
        return stash;
    }

    @Nullable
    @Override
    public StorageCache getCache() {
        return null;
    }

    @NotNull
    @Override
    public Storage getStorage() {
        return stash.getStorage();
    }

    @Override
    public boolean hasCache() {
        return stash.hasCache();
    }


    public RxStash(Builder builder) {
        this(builder.getStorage(), builder.getCache());
    }

    public RxStash(Storage storage, StorageCache cache) {
        this.stash = new Stash(storage, cache);
    }


    public synchronized Observable<Boolean> contains(@NotNull final String key) {

        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    subscriber.onNext(stash.contains(key));
                    subscriber.onCompleted();
                } catch (Throwable t) {
                    subscriber.onError(t);
                }
            }
        });
    }

    public synchronized Completable putBoolean(@NotNull final String key, final boolean val) {

        return Completable.create(new Completable.CompletableOnSubscribe() {

            @Override
            public void call(Completable.CompletableSubscriber completableSubscriber) {
                try {
                    stash.putBoolean(key, val);
                    completableSubscriber.onCompleted();
                } catch (Throwable t) {
                    completableSubscriber.onError(t);
                }
            }
        });
    }

    public synchronized Completable putInt(@NotNull final String key, final int val) {

        return Completable.create(new Completable.CompletableOnSubscribe() {

            @Override
            public void call(Completable.CompletableSubscriber completableSubscriber) {
                try {
                    stash.putInt(key, val);
                    completableSubscriber.onCompleted();
                } catch (Throwable t) {
                    completableSubscriber.onError(t);
                }
            }
        });
    }

    public synchronized Completable putLong(@NotNull final String key, final long val) {

        return Completable.create(new Completable.CompletableOnSubscribe() {

            @Override
            public void call(Completable.CompletableSubscriber completableSubscriber) {
                try {
                    stash.putLong(key, val);
                    completableSubscriber.onCompleted();
                } catch (Throwable t) {
                    completableSubscriber.onError(t);
                }
            }
        });
    }


    public synchronized Completable putFloat(@NotNull final String key, final float val) {

        return Completable.create(new Completable.CompletableOnSubscribe() {

            @Override
            public void call(Completable.CompletableSubscriber completableSubscriber) {
                try {
                    stash.putFloat(key, val);
                    completableSubscriber.onCompleted();
                } catch (Throwable t) {
                    completableSubscriber.onError(t);
                }
            }
        });
    }

    public synchronized Completable putDouble(@NotNull final String key, final double val) {

        return Completable.create(new Completable.CompletableOnSubscribe() {

            @Override
            public void call(Completable.CompletableSubscriber completableSubscriber) {
                try {
                    stash.putDouble(key, val);
                    completableSubscriber.onCompleted();
                } catch (Throwable t) {
                    completableSubscriber.onError(t);
                }
            }
        });
    }


    public synchronized Completable putString(@NotNull final String key, final String val) {

        return Completable.create(new Completable.CompletableOnSubscribe() {

            @Override
            public void call(Completable.CompletableSubscriber completableSubscriber) {
                try {
                    stash.putString(key, val);
                    completableSubscriber.onCompleted();
                } catch (Throwable t) {
                    completableSubscriber.onError(t);
                }
            }
        });
    }


    public synchronized Completable putObject(@NotNull final String key, final Object val, final ObjectSerializer serializer) {

        return Completable.create(new Completable.CompletableOnSubscribe() {

            @Override
            public void call(Completable.CompletableSubscriber completableSubscriber) {
                try {
                    stash.putObject(key, val, serializer);
                    completableSubscriber.onCompleted();
                } catch (Throwable t) {
                    completableSubscriber.onError(t);
                }
            }
        });
    }



    public synchronized Observable<Boolean> getBoolean(@NotNull final String key, final boolean backupVal) {

        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    subscriber.onNext(stash.getBoolean(key, backupVal));
                    subscriber.onCompleted();
                } catch (Throwable t) {
                    subscriber.onError(t);
                }
            }
        });
    }


    public synchronized Observable<Integer> getInt(@NotNull final String key, final int backupVal) {

        return Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    subscriber.onNext(stash.getInt(key, backupVal));
                    subscriber.onCompleted();
                } catch (Throwable t) {
                    subscriber.onError(t);
                }
            }
        });
    }


    public synchronized Observable<Long> getLong(@NotNull final String key, final long backupVal) {

        return Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                try {
                    subscriber.onNext(stash.getLong(key, backupVal));
                    subscriber.onCompleted();
                } catch (Throwable t) {
                    subscriber.onError(t);
                }
            }
        });
    }


    public synchronized Observable<Float> getFloat(@NotNull final String key, final float backupVal) {

        return Observable.create(new Observable.OnSubscribe<Float>() {
            @Override
            public void call(Subscriber<? super Float> subscriber) {
                try {
                    subscriber.onNext(stash.getFloat(key, backupVal));
                    subscriber.onCompleted();
                } catch (Throwable t) {
                    subscriber.onError(t);
                }
            }
        });
    }


    public synchronized Observable<Double> getDouble(@NotNull final String key, final double backupVal) {

        return Observable.create(new Observable.OnSubscribe<Double>() {
            @Override
            public void call(Subscriber<? super Double> subscriber) {
                try {
                    subscriber.onNext(stash.getDouble(key, backupVal));
                    subscriber.onCompleted();
                } catch (Throwable t) {
                    subscriber.onError(t);
                }
            }
        });
    }


    @Nullable
    public synchronized Observable<String> getString(@NotNull final String key, @Nullable final String backupVal) {

        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext(stash.getString(key, backupVal));
                    subscriber.onCompleted();
                } catch (Throwable t) {
                    subscriber.onError(t);
                }
            }
        });
    }


    @Nullable
    public synchronized Observable<Object> getObject(@NotNull final String key, @Nullable final Object backupVal, @NotNull final ObjectSerializer serializer) {

        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                try {
                    subscriber.onNext(stash.getObject(key, backupVal, serializer));
                    subscriber.onCompleted();
                } catch (Throwable t) {
                    subscriber.onError(t);
                }
            }
        });
    }


    public synchronized Completable del(final String key) {
        return Completable.create(new Completable.CompletableOnSubscribe() {

            @Override
            public void call(Completable.CompletableSubscriber completableSubscriber) {
                try {
                    stash.del(key);
                    completableSubscriber.onCompleted();
                } catch (Throwable t) {
                    completableSubscriber.onError(t);
                }
            }
        });
    }


    public synchronized Completable close() {
        return Completable.create(new Completable.CompletableOnSubscribe() {

            @Override
            public void call(Completable.CompletableSubscriber completableSubscriber) {
                try {
                    stash.close();
                    completableSubscriber.onCompleted();
                } catch (Throwable t) {
                    completableSubscriber.onError(t);
                }
            }
        });
    }

    public static class Builder {
        Storage storage = null;
        StorageCache cache = null;

        public Storage getStorage() {
            return storage;
        }

        public void setStorage(Storage storage) {
            this.storage = storage;
        }

        public StorageCache getCache() {
            return cache;
        }

        public void setCache(StorageCache cache) {
            this.cache = cache;
        }

        public RxStash build() {
            return new RxStash(this);
        }
    }
}
