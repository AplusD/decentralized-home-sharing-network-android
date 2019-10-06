package com.aplusd.houserenter.user.auth;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.mainactivityui.MainActivity;
import com.aplusd.houserenter.user.UserViewModel;
import com.aplusd.houserenter.user.registration.CreateAccountActivity;

/**
 * @author Azamat Dzhonov
 * @date 20.04.2018
 */

public class AuthActivity extends AppCompatActivity implements UserViewModel.CallBackAuth {


    private UserViewModel userViewModel = null;

    private UserViewModel.CallBackAuth callBackAuth = this;

    private int authAttempts = 0;

    private EditText edEmail;
    private EditText edPassword;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity);

        if(getSupportActionBar() != null)
             getSupportActionBar().hide();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        111);
            }
        }

         edEmail = findViewById(R.id.edEmail);
         edPassword = findViewById(R.id.edPassword);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        findViewById(R.id.buttonAuth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edEmail.getText().toString().isEmpty() || edPassword.getText().toString().isEmpty()) {
                    Snackbar.make(view, R.string.fill_fields, Snackbar.LENGTH_LONG).show();
                    return;
                }

                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                authAttempts++;
                userViewModel.auth(getBaseContext(),
                        edEmail.getText().toString(),
                        edPassword.getText().toString(), callBackAuth);

            }
        });

        findViewById(R.id.buttonCreateAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), CreateAccountActivity.class));
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

    }

    @Override
    public void authSucced() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }
        });

    }

    @Override
    public void authFailed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(authAttempts <= 1) {
                    authAttempts++;
                    userViewModel.auth(getBaseContext(),
                            edEmail.getText().toString(),
                            edPassword.getText().toString(), callBackAuth);
                }
                else {
                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                    findViewById(R.id.tvMainMsg).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.tvMainMsg)).setText(R.string.problem_with_login);
                }
            }
        });

    }
}
