package com.example.nut.sampleretrofit.Remote;

import com.example.nut.sampleretrofit.LoadingFragment;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nut on 13/6/2559.
 */
public class RetrofitManager {
    private static RetrofitManager ourInstance;
    private Retrofit retrofit;

    public static RetrofitManager getInstance() {
        if(ourInstance == null) {
            ourInstance = new RetrofitManager();
        }
        return ourInstance;
    }

    private RetrofitManager() {
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }

    public void callServer(String username) {
        GithubService githubService = retrofit.create(GithubService.class);
        Call call = githubService.getUser(username);
        call.enqueue(LoadingFragment.newInstance());
//        call.enqueue(callback);
    }
}
