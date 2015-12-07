package com.theavalanche.eskimo.info.api;

import com.theavalanche.eskimo.models.SkiRecord;

import java.util.Date;
import java.util.List;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Christopher on 12/1/2015.
 */
public interface SkiRecordAPI {

    @FormUrlEncoded
    @POST("/createSkiRecord")
    Call<SkiRecord> createSkiRecord(
            @Field("user_id") int user_id,
            @Field("ski_start_time") Date ski_start_time,
            @Field("ski_stop_time") Date ski_stop_time,
            @Field("ski_distance") double ski_distance,
            @Field("start_location") String start_location,
            @Field("end_location") String end_location,
            @Field("path") String path
    );

    @GET("/getSkiRecordsByUserId/{user_id}")
    Call<List<SkiRecord>> getSkiRecordsByUserId(@Path("user_id") String user_id);

    @GET("/getSkiRecordsByEventId/{event_id}")
    Call<List<SkiRecord>> getSkiRecordsByEventId(@Path("event_id") String event_id);
}
