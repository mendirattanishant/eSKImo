
package com.theavalanche.eskimo.info.api;

public interface UserAPI {
    @GET("/users/{user}")
    public void getFeed(@Path("user") String user,Callback<UserInfo> response);
}