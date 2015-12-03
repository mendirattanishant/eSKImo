package com.theavalanche.eskimo.info.api;

import com.theavalanche.eskimo.info.model.EventInfo;
import com.theavalanche.eskimo.info.model.UserInfo;
import com.theavalanche.eskimo.models.Event;
import com.theavalanche.eskimo.models.User;

import java.util.List;

import retrofit.Retrofit;
import retrofit.http.Field;
import retrofit.Call;

public class EventRESTClient {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://eskimo-cmpe277.rhcloud.com")
            .build();

    private EventAPI service = retrofit.create(EventAPI.class);

    public Call<EventInfo> createEvent(Event e){
        return service.createEvent(
                e.getDesc(),
                e.getTitle(),
                e.getLocation(),
                e.getStartTime(),
                e.getEndTime()
        );
    }

    public Call<List<EventInfo>> getAttendingEvents(EventInfo e){
        return service.getAttendingEvents(e.getEvent_id(), e.getUser_id());
    }

    public Call<List<EventInfo>> getMyEvents(){
        return service.getMyEvents();
    }

    public Call<Event> getEventById(String id){
        return service.getEventById(id);
    }

}
