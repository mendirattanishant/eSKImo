package com.theavalanche.eskimo.info.api;

/**
 * Created by Christopher on 12/1/2015.
 */
import com.theavalanche.eskimo.info.model.EventAttendeesInfo;
import com.theavalanche.eskimo.info.model.UserInfo;
import java.util.*;
import retrofit.Call;
import retrofit.Retrofit;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public class UserRESTClient {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://eskimo-cmpe277.rhcloud.com")
            .build();

    UserAPI service = retrofit.create(UserAPI.class);

    public Call<List<UserInfo>> getUser(){
        return  service.getUsers();
    }

    public Call<UserInfo> createUser(UserInfo u){
        return service.createUser(u.getName(), u.getPassword());
    }

    public Call<UserInfo>login(UserInfo u){
        return service.createUser(u.getName(), u.getPassword());
    }

    public Call<EventAttendeesInfo>attendEvent(EventAttendeesInfo u){
        return service.attendEvent(u.getEvent_id(), u.getUser_id());
    }

    public Call<String>deleteEvent(EventAttendeesInfo u){
        return service.deleteEvent(u.getEvent_id(),u.getUser_id());
    }



}
