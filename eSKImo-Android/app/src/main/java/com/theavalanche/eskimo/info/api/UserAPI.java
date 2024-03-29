package com.theavalanche.eskimo.info.api;

import com.theavalanche.eskimo.info.model.EventAttendeesInfo;
import com.theavalanche.eskimo.models.User;

import java.util.*;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Field;


public interface UserAPI {
    @GET("/getUsers")
    public Call<List<User>> getUsers();

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
    Call<EventAttendeesInfo>  attendEvent(@Field("event_id") String event_id, @Field("user_id") String user_id, @Field("flag") String flag);

    @FormUrlEncoded
    @POST("/goToEvent")
    Call<String>  goToEvent(@Field("event_id") String event_id, @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("/deleteEvent")
    Call<String> deleteEvent (@Field("event_id") String event_id, @Field("user_id") String user_id);



}