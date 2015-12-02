package com.theavalanche.eskimo.info.api;

import com.theavalanche.eskimo.info.model.EventInfo;
import com.theavalanche.eskimo.info.model.UserInfo;

import java.util.List;

import retrofit.Retrofit;
import retrofit.http.Field;
import retrofit.Call;

/**
 * Created by Christopher on 12/1/2015.
 */
public class EventRESTClient {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://eskimo-cmpe277.rhcloud.com")
            .build();

    EventAPI service = retrofit.create(EventAPI.class);

    public Call<EventInfo> createEvent(EventInfo e){
        return service.createEvent(e.getEvent_id(), e.getUser_id(),e.getEvent_details(),e.getEvent_name(),
                e.getLocation(),e.getStart_time(),e.getEnd_time());
    }

    public Call<List<EventInfo>> getAttendingEvents(EventInfo e){
        return service.getAttendingEvents(e.getEvent_id(), e.getUser_id());
    }

}
