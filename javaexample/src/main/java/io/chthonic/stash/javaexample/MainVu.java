package io.chthonic.stash.javaexample;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jakewharton.rxbinding.support.design.widget.RxNavigationView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.chthonic.mythos.mvp.Vu;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by jhavatar on 3/30/2016.
 */
public class MainVu extends Vu implements NavigationView.OnNavigationItemSelectedListener {

    static final String TAG = "MainVu";

    NavigationView navigationView;
    DrawerLayout drawer;
    EditText noteInput;
    TextView timestamp;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    private Subscription navSelectionSubs;

    public PublishSubject<Void> onSave = PublishSubject.create();
    public PublishSubject<Void> onLoad = PublishSubject.create();
    public PublishSubject<Void> onDelete = PublishSubject.create();
    public PublishSubject<Void> onSerializeGson = PublishSubject.create();
    public PublishSubject<Void> onSerializeMoshi = PublishSubject.create();

    @Override
    public int getRootViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    public MainVu(@NotNull LayoutInflater inflater, @NotNull Activity activity, @Nullable Fragment fragment, @Nullable ViewGroup parentView) {
        super(inflater, activity, fragment, parentView);

        Toolbar toolbar = (Toolbar) getRootView().findViewById(R.id.toolbar);
        ((AppCompatActivity) activity).setSupportActionBar(toolbar);

        drawer = (DrawerLayout) getRootView().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(getActivity(), drawer, toolbar, 0, 0) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(noteInput.getWindowToken(), 0);
            }

        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) getRootView().findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        noteInput = (EditText) getRootView().findViewById(R.id.note);
        noteInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (noteInput.getRight() - noteInput.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        noteInput.setText("");
                        return true;
                    }
                }
                return false;
            }
        });
        timestamp = (TextView) getRootView().findViewById(R.id.timestamp);

        this.navSelectionSubs = RxNavigationView.itemSelections(navigationView).subscribe(new Action1<MenuItem>(){

            @Override
            public void call(MenuItem menuItem) {
                int menuId = menuItem.getItemId();

                switch(menuId) {
                    case (R.id.nav_save):
                        onSave.onNext(null);
                        break;
                    case (R.id.nav_load):
                        onLoad.onNext(null);
                        break;
                    case (R.id.nav_delete):
                        onDelete.onNext(null);
                        break;
                    case (R.id.nav_gson):
                        Log.d(TAG, "clicked Gson");
                        onSerializeGson.onNext(null);
                        break;
                    case (R.id.nav_moshi):
                        Log.d(TAG, "clicked moshi");
                        onSerializeMoshi.onNext(null);
                        break;
                }

                drawer.closeDrawers();
            }
        });

        setNote(new Note());
    }

    @Override
    public void onDestroy() {
        if (this.navSelectionSubs != null) {
            this.navSelectionSubs.unsubscribe();
        }
        super.onDestroy();
    }

    public void updateSerializers(boolean moshiEnabled) {
    Log.d(TAG, "updateSerializers moshiEnabled = " + moshiEnabled);
    updateMenItem(R.id.nav_moshi, moshiEnabled);
    updateMenItem(R.id.nav_gson, !moshiEnabled);
}

    void updateMenItem(int itemId, boolean enabled) {
        MenuItem item = navigationView.getMenu().findItem(itemId);
        item.setChecked(enabled);
//        updateNavigationView(navigationView);
    }

    public void setNote(Note note) {
        noteInput.setText(note.text);
        String dateTime = note.timestamp > 0 ? sdf.format(new Date(note.timestamp)) : "";
        timestamp.setText(getActivity().getResources().getString(R.string.timestamp_label) + " " + dateTime);
    }

    public Note getNote() {
        return new Note(noteInput.getText().toString(), System.currentTimeMillis());
    }

    public static void updateNavigationView(NavigationView navView) {

        for (int i = 0; i < navView.getChildCount(); i++) {
            final View child = navView.getChildAt(i);
            if (child != null && child instanceof ListView) {
                final ListView menuView = (ListView) child;
                final HeaderViewListAdapter adapter = (HeaderViewListAdapter) menuView.getAdapter();
                final BaseAdapter wrapped = (BaseAdapter) adapter.getWrappedAdapter();
                wrapped.notifyDataSetChanged();
            }
        }

    }

}
