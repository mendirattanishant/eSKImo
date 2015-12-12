package com.theavalanche.eskimo.info.api;


import com.theavalanche.eskimo.models.SkiRecord;

import java.util.List;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Christopher on 12/1/2015.
 */
public class SkiRecordRESTClient {
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://eskimo-cmpe277.rhcloud.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    SkiRecordAPI service = retrofit.create(SkiRecordAPI.class);

    public Call<SkiRecord> createSkiRecord(SkiRecord sk){
        return  service.createSkiRecord(
                sk.getUserId(),
                sk.getStartTime(),
                sk.getEndTime(),
                sk.getDistance(),
                sk.getStartLocation().toJson(),
                sk.getEndLocation().toJson(),
                sk.getPath());
    }

    public Call<List<SkiRecord>> getSkiRecordsByUserId(String userId){
        return service.getSkiRecordsByUserId(userId);
    }

    public Call<List<SkiRecord>> getSkiRecordsByEventId(String eventId){
        return service.getSkiRecordsByEventId(eventId);
    }
}
