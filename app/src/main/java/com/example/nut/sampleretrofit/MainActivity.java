package com.example.nut.sampleretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nut.sampleretrofit.Model.User;
import com.example.nut.sampleretrofit.Remote.NetworkConnectionManager;
import com.example.nut.sampleretrofit.Remote.OnNetworkCallbackListener;

import org.parceler.Parcels;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private User user;
    public static final String KEY_USER = "KEY_USER";
    public static final String TAG = "MainActivity";

    @BindView(R.id.main_username_edit_text)
    EditText mainUsernameEditText;
    @BindView(R.id.main_username_btn)
    Button mainUsernameBtn;
    @BindView(R.id.main_layout_form)
    LinearLayout mainLayoutForm;
    @BindView(R.id.main_progress_bar)
    ProgressBar mainProgressBar;
    @BindView(R.id.main_progress_text_view)
    TextView mainProgressTextView;
    @BindView(R.id.main_layout_progress)
    LinearLayout mainLayoutProgress;
    @BindView(R.id.main_text_view_result_header)
    TextView mainTextViewResultHeader;
    @BindView(R.id.main_text_view_result)
    TextView mainTextViewResult;
    @BindView(R.id.main_layout_result)
    LinearLayout mainLayoutResult;

    private OnNetworkCallbackListener networkCallbackListener = new OnNetworkCallbackListener() {
        @Override
        public void onResponse(User user, Retrofit retrofit) {
            if(user != null) {
                setUserData(user);
                showData();
            }
        }

        @Override
        public void onBodyError(ResponseBody responseBodyError) {
            showData();
            try {
                setDataToView("responseBody = " + responseBodyError.string());
                showData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onBodyErrorIsNull() {
            setDataToView("responseBody = null");
            showData();
        }

        @Override
        public void onFailure(Throwable t) {
            setDataToView("Throw = " + t.getMessage());
            showData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(savedInstanceState == null) {
            showForm();
        } else {
            showData();
        }
    }

    private void callServer() {
        showLoading();
        new NetworkConnectionManager().callServer(networkCallbackListener, mainUsernameEditText.getText().toString());
    }

    private void setUserData(User user) {
        if(user != null) {
            this.user = user;
            String data = "Github Name: " +user.getName()+
                    "\nWebsite: " + user.getBlog()+
                    "\nCompany Name: " + user.getCompany();
            Log.e(TAG, data);
            setDataToView(data);
        }
    }

    private void showForm() {
        mainLayoutForm.setVisibility(View.VISIBLE);
        mainLayoutProgress.setVisibility(View.GONE);
        mainLayoutResult.setVisibility(View.GONE);
    }

    private void showLoading() {
        mainLayoutForm.setVisibility(View.GONE);
        mainLayoutProgress.setVisibility(View.VISIBLE);
        mainLayoutResult.setVisibility(View.GONE);
    }

    private void showData() {
        mainLayoutForm.setVisibility(View.GONE);
        mainLayoutProgress.setVisibility(View.GONE);
        mainLayoutResult.setVisibility(View.VISIBLE);
    }

    private void setDataToView(String data) {
        mainTextViewResult.setText(data);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.user = Parcels.unwrap(savedInstanceState.getParcelable(KEY_USER));
        Log.i(TAG,"Restore!");
        Log.e(TAG,this.user.getName());
        setUserData(user);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_USER, Parcels.wrap(this.user));
        Log.i(TAG,"Saved!");
    }

    @OnClick({R.id.main_username_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_username_btn:
                callServer();
                break;
        }
    }
}
