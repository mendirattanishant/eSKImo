package com.theavalanche.eskimo.info.api;

import com.theavalanche.eskimo.info.model.UserInfo;
import com.theavalanche.eskimo.info.model.EventAttendeesInfo;
import com.theavalanche.eskimo.models.User;

import java.util.*;
import retrofit.Call;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Field;


public interface UserAPI {
    @GET("/getUsers")
    public Call<List<UserInfo>> getUsers();

    @FormUrlEncoded
    @POST("/createUser")
    Call<User> createUser(
            @Field("email_id") String email_id,
            @Field("password") String password,
            @Field("name") String name,
            @Field("tagline") String tagline,
            @Field("dpUrl") String dpUrl
    );

    @FormUrlEncoded
    @POST("/login")
    Call<User> login(@Field("email_id") String email_id, @Field("password") String password);

    @FormUrlEncoded
    @POST("/attendEvent")
    Call<EventAttendeesInfo>  attendEvent(@Field("event_id") int event_id, @Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("/deleteEvent")
    Call<String> deleteEvent (@Field("event_id") int event_id, @Field("user_id") int user_id);

}