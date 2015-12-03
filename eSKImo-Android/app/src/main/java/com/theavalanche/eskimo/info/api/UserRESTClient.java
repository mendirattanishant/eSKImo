package com.theavalanche.eskimo.info.api;

import com.theavalanche.eskimo.info.model.EventAttendeesInfo;
import com.theavalanche.eskimo.info.model.UserInfo;
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

    public Call<List<UserInfo>> getUser(){
        return  service.getUsers();
    }

    public Call<UserInfo> createUser(User u){
        return service.createUser(u.getEmail(), u.getPassword());
    }

    public Call<UserInfo> login(User u){
        return service.login(u.getEmail(), u.getPassword());
    }

    public Call<EventAttendeesInfo> attendEvent(EventAttendeesInfo u){
        return service.attendEvent(u.getEvent_id(), u.getUser_id());
    }

    public Call<String> deleteEvent(EventAttendeesInfo u){
        return service.deleteEvent(u.getEvent_id(),u.getUser_id());
    }

}
