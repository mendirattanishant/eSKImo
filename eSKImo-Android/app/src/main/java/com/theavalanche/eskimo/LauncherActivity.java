package com.theavalanche.eskimo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

public class LauncherActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        // Register
        findViewById(R.id.tvRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LauncherActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // Login with Facebook
        findViewById(R.id.btn_facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LauncherActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // Login with Google
        findViewById(R.id.btn_google).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LauncherActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
