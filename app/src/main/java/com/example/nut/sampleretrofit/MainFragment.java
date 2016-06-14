package com.example.nut.sampleretrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.nut.sampleretrofit.Model.User;
import com.example.nut.sampleretrofit.Remote.GithubService;
import com.example.nut.sampleretrofit.Remote.RetrofitManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment {


    @BindView(R.id.main_username_edit_text)
    EditText mainUsernameEditText;
    @BindView(R.id.main_username_btn)
    Button mainUsernameBtn;
    @BindView(R.id.main_layout_form)
    LinearLayout mainLayoutForm;
    private Unbinder unbinder;
    
    GithubService service;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = RetrofitManager.getInstance().getRetrofit().create(GithubService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(MainActivity.TAG, ""+getActivity());
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    public String getTextFromView() {
        return mainUsernameEditText.getText().toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.main_username_btn)
    public void onClick() {
        Call<User> call = service.getUser("1");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    ((MainActivity) getActivity()).replaceFragments(LoadingFragment.class);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
//        RetrofitManager.getInstance().callServer(getTextFromView());
        /*FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, LoadingFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();*/
    }
}
