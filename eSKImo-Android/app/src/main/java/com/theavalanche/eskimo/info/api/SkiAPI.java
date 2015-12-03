package com.theavalanche.eskimo.info.api;

import com.theavalanche.eskimo.info.model.SkiRecordInfo;
import java.util.List;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Christopher on 12/1/2015.
 */
public interface SkiAPI {

    @FormUrlEncoded
    @POST("/createSkiRecord")
    Call<SkiRecordInfo> createSkiRecord(@Field("user_id") int user_id , @Field("ski_start_time") String ski_start_time,
                        @Field("event_id") int event_id );

}
