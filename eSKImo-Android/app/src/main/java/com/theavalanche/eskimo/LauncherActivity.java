package com.theavalanche.eskimo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.theavalanche.eskimo.info.api.UserRESTClient;
import com.theavalanche.eskimo.info.model.UserInfo;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.theavalanche.eskimo.models.User;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LauncherActivity extends Activity {

    public static String TAG = "LauncherActivity";

    private CallbackManager callbackManager;
    private LoginButton fb_loginButton;
    private UserRESTClient userRESTClient;

    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_launcher);


        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        userRESTClient = new UserRESTClient();

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

                User user = new User(null, etEmail.getText().toString(), etPassword.getText().toString());

                userRESTClient.login(user).enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Response<UserInfo> response, Retrofit retrofit) {
                        Log.d(TAG, "Successful email login");
                        Log.d(TAG, response.body().getName());
                        Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(TAG, "Problem with login");
                        Toast.makeText(
                                LauncherActivity.this,
                                "Unable to login. Check credentials.",
                                Toast.LENGTH_SHORT
                        ).show();
                        t.printStackTrace();
                    }
                });

            }
        });

        fb_loginButton = (LoginButton)findViewById(R.id.fb_login_button);

        fb_loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
