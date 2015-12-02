package com.theavalanche.eskimo.info.api;

import com.theavalanche.eskimo.info.model.SkiRecordInfo;
import com.theavalanche.eskimo.info.model.UserInfo;

import java.util.List;

import retrofit.Call;
import retrofit.Retrofit;

/**
 * Created by Christopher on 12/1/2015.
 */
public class SkiRESTClient {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://eskimo-cmpe277.rhcloud.com")
            .build();

    SkiAPI service = retrofit.create(SkiAPI.class);

    public Call<SkiRecordInfo> createSkiRecord(SkiRecordInfo sk){
        return  service.createSkiRecord(sk.getUser_id(),sk.getStart_time(),sk.getEvent_id());
    }
}
