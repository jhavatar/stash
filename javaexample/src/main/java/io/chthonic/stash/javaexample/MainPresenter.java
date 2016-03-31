package io.chthonic.stash.javaexample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import io.chthonic.mythos.mvp.Presenter;
import io.chthonic.stash.cache.LruStorageCache;
import io.chthonic.stash.serializers.GsonObjectSerializer;
import io.chthonic.stash.serializers.MoshiObjectSerializer;
import io.chthonic.stash.serializers.ObjectSerializer;
import io.chthonic.stash.storage.SharedPrefsStorage;
import io.chtonic.stash.RxStash;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jhavatar on 3/30/2016.
 */
public class MainPresenter extends Presenter<MainVu> {
    static final String TAG = "MainPresenter";
    static final String NOTE_KEY = "my_note";

    static final int GSON_SERIALIZER = 0;
    static final int MOSHI_SERIALIZER = 1;

    RxStash stash;
    int serializerType = MOSHI_SERIALIZER;

    private CompositeSubscription vuSubs;
    private CompositeSubscription logicSubs;


    @Override
    public void initialize(Activity activity, Bundle args, Bundle inState) {
        super.initialize(activity, args, inState);
        logicSubs = new CompositeSubscription();

        stash = new RxStash.Builder(new SharedPrefsStorage.Builder().name("myperson").build(getActivity()))
                .cache(new LruStorageCache(100))
                .build();

    }

    @Override
    public void attachVu(MainVu vu) {
        super.attachVu(vu);

        vuSubs = new CompositeSubscription();
        vuSubs.add(
                vu.onSave.subscribe(new Action1<Void>() {

                    @Override
                    public void call(Void aVoid) {
                        if (hasVu()) {
                            save(getVu().getNote());
                        }
                    }
                })
        );

        vuSubs.add(
                vu.onLoad.subscribe(new Action1<Void>() {

                    @Override
                    public void call(Void aVoid) {
                        load();
                    }
                })
        );

        vuSubs.add(
                vu.onDelete.subscribe(new Action1<Void>() {

                    @Override
                    public void call(Void aVoid) {
                        delete();
                    }
                })
        );


        vuSubs.add(
                vu.onSerializeGson.subscribe(new Action1<Void>() {

                    @Override
                    public void call(Void aVoid) {
                        serializerType = GSON_SERIALIZER;
                        getVu().updateSerializers(serializerType == MOSHI_SERIALIZER);
                    }
                })
        );

        vuSubs.add(
                vu.onSerializeMoshi.subscribe(new Action1<Void>() {

                    @Override
                    public void call(Void aVoid) {
                        serializerType = MOSHI_SERIALIZER;
                        getVu().updateSerializers(serializerType == MOSHI_SERIALIZER);
                    }
                })
        );

        getVu().updateSerializers(serializerType == MOSHI_SERIALIZER);
    }


    @Override
    public void detachVu() {
        vuSubs.unsubscribe();
        super.detachVu();
    }

    @Override
    public void onDestroy() {
        logicSubs.unsubscribe();
        stash.getStash().close();
        super.onDestroy();
    }


    ObjectSerializer<Note> getSerializer() {
        if (GSON_SERIALIZER == serializerType) {
            return new GsonObjectSerializer<Note>(Note.class);

        } else {
            return new MoshiObjectSerializer<Note>(Note.class);
        }
    }


    private void save(final Note note) {
        if (this.getAttached()) {
            logicSubs.add(
                stash.putObject(NOTE_KEY, note, getSerializer())
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Throwable>() {
                        @Override
                        public void call(Throwable t) {
                            Log.e(TAG, "save error", t);
                        }
                    }, new Action0() {

                        @Override
                        public void call() {
                            Log.d(TAG, "save success");
                            if (hasVu()) {

                                // updated saved date
                                getVu().setNote(note);
                            }
                        }
                    })
            );
        }
    }


    private void load() {
        if (this.hasVu()) {
            logicSubs.add(
                    stash.getObject(NOTE_KEY, null, getSerializer())
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Object>() {
                                @Override
                                public void call(Object o) {
                                    Log.d(TAG, "load success " + o);
                                    if (hasVu()) {
                                        getVu().setNote((Note) o);
                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable t) {
                                    Log.e(TAG, "load error", t);
                                }
                            })
            );
        }
    }


    private void delete() {
        if (this.getAttached()) {
            logicSubs.add(
                    stash.del(NOTE_KEY)
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Throwable>() {
                                @Override
                                public void call(Throwable t) {
                                    Log.e(TAG, "delete error", t);
                                }
                            }, new Action0() {

                                @Override
                                public void call() {
                                    Log.d(TAG, "delete success");
                                    if (hasVu()) {
                                        getVu().setNote(new Note());
                                    }
                                }
                            })
            );
        }
    }
}
