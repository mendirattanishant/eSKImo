package com.theavalanche.eskimo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.theavalanche.eskimo.info.api.UserAPI;
import com.theavalanche.eskimo.info.api.UserRESTClient;
import com.theavalanche.eskimo.info.model.UserInfo;
import com.theavalanche.eskimo.maps.RouteActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LauncherActivity extends Activity {

    private CallbackManager callbackManager;
    // private TextView info;
    private LoginButton fb_loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UserRESTClient u=new UserRESTClient();

        Call<List<UserInfo>> callback=u.getUser();
        callback.enqueue(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(Response<List<UserInfo>> response, Retrofit retrofit) {
                //Success
                for(UserInfo user:response.body())
                    Log.i("App", user.getName());
            }

            @Override
            public void onFailure(Throwable t) {
                //Check Aysc
            }
        });
        //Check Aysc

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_launcher);
        findViewById(R.id.tvRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LauncherActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.bLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Login with Facebook
        fb_loginButton = (LoginButton)findViewById(R.id.fb_login_button);

        fb_loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
               /* info.setText("User ID:  " +
                        loginResult.getAccessToken().getUserId() + "\n" +
                        "Auth Token: " + loginResult.getAccessToken().getToken());*/
            }

            @Override
            public void onCancel() {
                // info.setText("Login attempt cancelled.");
            }

            @Override
            public void onError(FacebookException e) {
                // info.setText("Login attempt failed.");
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
