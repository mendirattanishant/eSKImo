package com.theavalanche.eskimo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.theavalanche.eskimo.info.api.UserRESTClient;
import com.theavalanche.eskimo.models.User;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class RegisterActivity extends Activity {

    public static final String TAG = "RegisterActivity";

    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;

    private Button bRegister;

    private UserRESTClient userRESTClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);

        bRegister = (Button) findViewById(R.id.bRegister);

        userRESTClient = new UserRESTClient();

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();
                if(!confirmPassword.equals(password)){
                    Toast.makeText(
                            RegisterActivity.this,
                            "Passwords don't match",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }
                User user = new User(null, email, password);
                user.setName(etName.getText().toString());

                userRESTClient.createUser(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Response<User> response, Retrofit retrofit) {
                        Log.d(TAG, "User registered successfully");
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.e(TAG, "Problem with register");
                        Toast.makeText(
                                RegisterActivity.this,
                                "Unable to register.",
                                Toast.LENGTH_SHORT
                        ).show();
                        t.printStackTrace();
                    }
                });
            }
        });

        findViewById(R.id.tvLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LauncherActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
