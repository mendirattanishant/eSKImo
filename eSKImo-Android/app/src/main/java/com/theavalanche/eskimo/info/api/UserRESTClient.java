package com.theavalanche.eskimo.info.api;

import com.theavalanche.eskimo.info.model.EventAttendeesInfo;
import com.theavalanche.eskimo.models.User;

import java.util.*;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class UserRESTClient {

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://eskimo-cmpe277.rhcloud.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private UserAPI service = retrofit.create(UserAPI.class);

    public Call<List<User>> getUser(){
        return  service.getUsers();
    }

    public Call<User> createUser(User u){
        return service.createUser(u.getEmail(), u.getPassword(), u.getName(), u.getTagline(), u.getDpUrl());
    }

    public Call<User> login(User u){
        return service.login(u.getEmail(), u.getPassword());
    }

    public Call<EventAttendeesInfo> attendEvent(String eventId, String userId){
        return service.attendEvent(eventId, userId);
    }

    public Call<String> deleteEvent(String eventId, String userId){
        return service.deleteEvent(eventId, userId);
    }

}
