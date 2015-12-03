package com.theavalanche.eskimo.info.api;

import com.theavalanche.eskimo.models.Event;

import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.Call;

public class EventRESTClient {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://eskimo-cmpe277.rhcloud.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private EventAPI service = retrofit.create(EventAPI.class);

    public Call<Event> createEvent(Event e){
        return service.createEvent(
                e.getEvent_details(),
                e.getEvent_name(),
                e.getLocation(),
                e.getStart_time(),
                e.getEnd_time()
        );
    }

    public Call<List<Event>> getAttendingEvents(Event e){
        return service.getAttendingEvents(e.getEvent_id(), e.getUser_id());
    }

    public Call<List<Event>> getMyEvents(){
        return service.getMyEvents();
    }

    public Call<Event> getEventById(String id){
        return service.getEventById(id);
    }

}
