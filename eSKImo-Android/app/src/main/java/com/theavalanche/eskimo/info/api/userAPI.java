package com.theavalanche.eskimo.info.api;

import com.theavalanche.eskimo.info.model.UserInfo;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface UserAPI {
    @GET("/users/{user}")
    public void getFeed(@Path("user") String user, Callback<UserInfo> response);

}

