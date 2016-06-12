package com.example.nut.sampleretrofit.Remote;

import com.example.nut.sampleretrofit.Model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nut on 12/6/2559.
 */
public class NetworkConnectionManager {
    public NetworkConnectionManager() {

    }

    public void callServer(final OnNetworkCallbackListener listener, String username) {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GithubService githubService = retrofit.create(GithubService.class);
        Call call = githubService.getUser(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

                if(user == null) {
                    ResponseBody responseBody = response.errorBody();
                    if(responseBody != null) {
                        listener.onBodyError(responseBody);
                    } else {
                        listener.onBodyErrorIsNull();
                    }
                } else {
                    listener.onResponse(user, retrofit);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}
