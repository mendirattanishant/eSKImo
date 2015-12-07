package com.theavalanche.eskimo.info.api;


import com.theavalanche.eskimo.models.SkiRecord;

import java.util.List;

import retrofit.Call;
import retrofit.Retrofit;

/**
 * Created by Christopher on 12/1/2015.
 */
public class SkiRecordRESTClient {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://eskimo-cmpe277.rhcloud.com")
            .build();

    SkiRecordAPI service = retrofit.create(SkiRecordAPI.class);

    public Call<SkiRecord> createSkiRecord(SkiRecord sk){
        return  service.createSkiRecord(
                sk.getUserId(),
                sk.getEventId(),
                sk.getStartTime(),
                sk.getEndTime(),
                sk.getDistance(),
                sk.getStartLocation(),
                sk.getEndLocation(),
                sk.getPath());
    }

    public Call<List<SkiRecord>> getSkiRecordsByUserId(String userId){
        return service.getSkiRecordsByUserId(userId);
    }

    public Call<List<SkiRecord>> getSkiRecordsByEventId(String eventId){
        return service.getSkiRecordsByEventId(eventId);
    }
}
