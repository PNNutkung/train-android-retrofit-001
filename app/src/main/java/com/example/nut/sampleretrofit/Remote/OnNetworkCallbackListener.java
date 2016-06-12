package com.example.nut.sampleretrofit.Remote;

import com.example.nut.sampleretrofit.Model.User;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Created by nut on 12/6/2559.
 */
public interface OnNetworkCallbackListener {
    public void onResponse(User user, Retrofit retrofit);
    public void onBodyError(ResponseBody responseBodyError);
    public void onBodyErrorIsNull();
    public void onFailure(Throwable t);
}
