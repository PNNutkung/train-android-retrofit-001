package com.example.nut.sampleretrofit.Remote;

import com.example.nut.sampleretrofit.Model.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by nut on 12/6/2559.
 */
public interface GithubService {
    @GET("/users/{username}")
    Call<User> getUser(@Path("username") String username);
}
