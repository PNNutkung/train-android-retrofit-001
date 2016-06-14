package com.example.nut.sampleretrofit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nut.sampleretrofit.Model.User;
import com.example.nut.sampleretrofit.callback.OnFragmentListener;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoadingFragment extends Fragment implements Callback<User> {

    @BindView(R.id.main_progress_bar)
    ProgressBar mainProgressBar;
    @BindView(R.id.main_progress_text_view)
    TextView mainProgressTextView;
    @BindView(R.id.main_layout_progress)
    LinearLayout mainLayoutProgress;
    private Unbinder unbinder;

    OnFragmentListener mCallback;

    public static LoadingFragment newInstance() {
        LoadingFragment fragment = new LoadingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_loading, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void showData() {
        mCallback.onClick(ResultFragment.newInstance());
//        ulreplaceFragments(ResultFragment.class);
        /*FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, ResultFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();*/
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        User user = response.body();

        if (user == null) {
            ResponseBody responseBody = response.errorBody();
            if (responseBody != null) {
                try {
                    ResultFragment.newInstance().setDataToView("responseBody = " + responseBody.string());
                    showData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                ResultFragment.newInstance().setDataToView("responseBody = null");
            }
        } else {
            MainActivity.setUser(user);
            showData();
        }
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}
