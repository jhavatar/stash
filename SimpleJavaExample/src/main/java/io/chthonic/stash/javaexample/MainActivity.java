package io.chthonic.stash.javaexample;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.chthonic.mythos.mvp.MVPActivity;
import io.chthonic.mythos.mvp.MVPDispatcher;

public class MainActivity extends MVPActivity<MainPresenter, MainVu> {
    static final String TAG = "MainActivity";

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
