package com.theavalanche.eskimo.info.api;

/**
 * Created by Christopher on 12/1/2015.
 */
import com.theavalanche.eskimo.info.model.UserInfo;
import java.util.*;
import retrofit.Call;
import retrofit.Retrofit;

public class UserRESTClient {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://eskimo-cmpe277.rhcloud.com")
            .build();

    UserAPI service = retrofit.create(UserAPI.class);

    public List<UserInfo> getUser(){
        return  service.getUsers();
    }

    public UserInfo saveUser(UserInfo u){
        UserInfo user = service.createUser(u.getName(), u.getPassword());
        return user;
    }
}
