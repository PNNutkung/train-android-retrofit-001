package com.example.nut.sampleretrofit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nut.sampleretrofit.Model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultFragment extends Fragment implements Callback<User> {


    @BindView(R.id.main_text_view_result)
    TextView mainTextViewResult;
    private Unbinder unbinder;

    public static ResultFragment newInstance() {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(MainActivity.TAG, ""+getActivity());
        View rootView = inflater.inflate(R.layout.fragment_result, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setDataToView();
        return rootView;
    }

    public void setDataToView() {
        User user = MainActivity.getUser();
        String data = "Github Name: " + user.getName() +
                "\nWebsite: " + user.getBlog() +
                "\nCompany Name: " + user.getCompany();
        mainTextViewResult.setText(data);
    }

    public void setDataToView(String data) {
        mainTextViewResult.setText(data);
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {

    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        setDataToView("Throw: " + t.getMessage());
    }
}
