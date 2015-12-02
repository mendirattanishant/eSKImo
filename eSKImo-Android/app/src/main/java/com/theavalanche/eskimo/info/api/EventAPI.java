package com.theavalanche.eskimo.info.api;

import com.theavalanche.eskimo.info.model.EventInfo;
import com.theavalanche.eskimo.info.model.UserInfo;

import java.util.List;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.Call;

/**
 * Created by Christopher on 12/1/2015.
 */
public interface EventAPI {
    @FormUrlEncoded
    @POST("/createEvent")
    Call<EventInfo> createEvent(@Field("event_id") int event_id, @Field("user_id") int user_id, @Field("event_details") String  event_details,
                          @Field("event_name") String event_name, @Field("location") String location,
                          @Field("start_time") String start_time,  @Field("end_time") String end_time);

    @FormUrlEncoded
    @POST("/getAttendingEvents")
    Call<List<EventInfo>> getAttendingEvents  (@Field("event_id") int event_id, @Field("user_id") int user_id);

}
