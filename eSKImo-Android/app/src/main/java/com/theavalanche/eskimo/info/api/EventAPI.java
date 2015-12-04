package com.theavalanche.eskimo.info.api;

import com.theavalanche.eskimo.models.Event;

import java.util.List;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.Call;
import retrofit.http.Path;

public interface EventAPI {

    @FormUrlEncoded
    @POST("/createEvent")
    Call<Event> createEvent(
            @Field("event_details") String  event_details,
            @Field("event_name") String event_name,
            @Field("location") String location,
            @Field("start_time") String start_time,
            @Field("end_time") String end_time
    );

    @GET("/getAttendingEvents/{user_id}")
    Call<List<Event>> getAttendingEvents(@Path("user_id") String user_id);

    @GET("/getEventDetail/{event_id}")
    Call<Event> getEventById(@Path("event_id") String event_id);

}
