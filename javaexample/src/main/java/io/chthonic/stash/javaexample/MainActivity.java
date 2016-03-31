package io.chthonic.stash.javaexample;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.style.SubscriptSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.JsonSerializer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.chthonic.mythos.mvp.MVPActivity;
import io.chthonic.mythos.mvp.MVPDispatcher;
import io.chthonic.stash.cache.LruStorageCache;
import io.chthonic.stash.serializers.GsonObjectSerializer;
import io.chthonic.stash.serializers.MoshiObjectSerializer;
import io.chthonic.stash.serializers.ObjectSerializer;
import io.chthonic.stash.serializers.ObjectStreamSerializer;
import io.chthonic.stash.storage.SharedPrefsStorage;
import io.chthonic.stash.storage.Storage;
import io.chtonic.stash.RxStash;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends MVPActivity<MainPresenter, MainVu> {
    static final String TAG = "MainActivity";

//    boolean viewsInited = false;
//    boolean persistenceInited = false;



//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, 0, 0);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//
//        navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        initViews();
//        initPersistence();
//    }


//    protected void onPause () {
//        if (viewsInited && persistenceInited) {
//            setPerson(false);
//        }
//        super.onPause();
//    }
//
//
//    void initViews() {
//        Log.d(TAG, "initViews");
//        nameInput = (EditText) this.findViewById(R.id.name);
//        ageInput = (EditText) this.findViewById(R.id.age);
//        newsletterInput = (CheckBox) this.findViewById(R.id.newsletter);
//
//        MenuItem item = null;
//        if (OBJECT_STREAM_SERIALIZER == serializerType) {
//            item = navigationView.getMenu().findItem(R.id.nav_objstream);
//        } else if (GSON_SERIALIZER == serializerType) {
//            item = navigationView.getMenu().findItem(R.id.nav_gson);
//        } else {
//            item = navigationView.getMenu().findItem(R.id.nav_moshi);
//        }
//        item.setChecked(true);
//
//        viewsInited = true;
//    }
//
//    void initPersistence() {
//        Log.d(TAG, "initPersistence");
//        stash = new RxStash.Builder(new SharedPrefsStorage.Builder().name("myperson").build(this))
//                .cache(new LruStorageCache(100))
//                .build();
//
//        fetchPerson();
//    }
//
//    void fetchPerson() {
//        Log.d(TAG, "fetchPerson");
//        stash.getObject(PERSON_KEY, null, getSerializer())
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Object>() {
//            @Override
//            public void call(Object val) {
//                persistenceInited = true;
//                Log.d(TAG, "Note fetched, person = " +  ((val != null) ? val.toString() : "null"));
//
//                if (val != null) {
//                    showPerson((Note) val);
//                } else {
//                    clearPerson();
//                }
//            }
//        });
//    }
//
//    void setPerson(boolean doAsync) {
//        int age = -1;
//        try {
//            age = Integer.parseInt(ageInput.getText().toString());
//        } catch (Exception e) {
//            // ignore
//        }
//        Note person = new Note(nameInput.getText().toString(), age, newsletterInput.isChecked());
//        Log.d(TAG, "setPerson " + person.toString());
//        stash.putObject(PERSON_KEY, person, getSerializer())
//                .subscribeOn(doAsync ? Schedulers.computation() : AndroidSchedulers.mainThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        Log.d(TAG, "Persistence updated");
//                    }
//                });
//    }
//
//    void showPerson(Note person) {
//        Log.d(TAG, "showPerson " + ((person != null) ? person.toString() : "null"));
//        nameInput.setText(person.name);
//        ageInput.setText((person.age > 0) ? person.age + "" : "");
//        newsletterInput.setChecked(person.newsletter);
//    }
//
//    void clearPerson() {
//        nameInput.setText("");
//        ageInput.setText("");
//        newsletterInput.setChecked(false);
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        boolean oldIsJson = isJsonPersistence();
//
//        switch(id) {
//            case R.id.nav_objstream:
//                serializerType = OBJECT_STREAM_SERIALIZER;
//                break;
//            case R.id.nav_gson:
//                serializerType = GSON_SERIALIZER;
//                break;
//            case R.id.nav_moshi:
//                serializerType = MOSHI_SERIALIZER;
//                break;
//        }
//
//        if (isJsonPersistence() != oldIsJson) {
//            setPerson(true);
//        }
//
//
//        return true;
//    }


    @NotNull
    @Override
    public MVPDispatcher<MainPresenter, MainVu> createMVPDispatcher() {
        return new MVPDispatcher<MainPresenter, MainVu>() {
            @NotNull
            @Override
            protected MainPresenter createPresenter() {
                return new MainPresenter();
            }

            @NotNull
            @Override
            protected MainVu createVu(@NotNull LayoutInflater layoutInflater, @NotNull Activity activity, @Nullable Fragment fragment, @Nullable ViewGroup viewGroup) {
                return new MainVu(layoutInflater, activity, null, null);
            }
        };
    }
}
