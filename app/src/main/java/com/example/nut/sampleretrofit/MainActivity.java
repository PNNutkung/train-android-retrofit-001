package com.example.nut.sampleretrofit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.nut.sampleretrofit.Model.User;
import com.example.nut.sampleretrofit.callback.OnFragmentListener;

import org.parceler.Parcels;

import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements OnFragmentListener {
    private static User user;
    public static final String KEY_USER = "KEY_USER";
    public static final String TAG = "MainActivity";
    private String data;
    private static MainActivity instance;

    public static User getUser() {
        return user;
    }

    public static void setUser(User newUser) {
        user = newUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            showForm();
        } else {
            showData();
            setDataToView(getData());
        }
    }

    private String getData() {
        return this.data = "Github Name: " + user.getName() +
                "\nWebsite: " + user.getBlog() +
                "\nCompany Name: " + user.getCompany();
    }

    private void callServer() {
        showLoading();
    }

    public void showForm() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_fragment_container, MainFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showLoading() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment_container, LoadingFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showData() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment_container, ResultFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setDataToView(String data) {
        ResultFragment.newInstance().setDataToView(data);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        user = Parcels.unwrap(savedInstanceState.getParcelable(KEY_USER));
        Log.i(TAG, "Restore!");
        setUser(user);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_USER, Parcels.wrap(this.user));
        Log.i(TAG, "Saved!");
    }

    @OnClick({R.id.main_username_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_username_btn:
                callServer();
                break;
        }
    }

    public void replaceFragments(Class fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragment)
                .commit();
    }

    public void replaceFragments(Fragment fragmentClass) {

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_fragment_container, fragmentClass)
                .commit();
    }

    @Override
    public void onClick(Fragment fragment) {
        replaceFragments(fragment);
    }
}
