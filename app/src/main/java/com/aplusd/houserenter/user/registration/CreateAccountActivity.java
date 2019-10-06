package com.aplusd.houserenter.user.registration;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.model.UserInfo;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.json.JSONObject;

/**
 * @author Azamat Dzhonov
 * @date 05.01.2018
 */

public class CreateAccountActivity extends AppCompatActivity implements StepperLayout.StepperListener{

    private StepperLayout mStepperLayout;

    ViewModelRegistration viewModelRegistration = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.registration);

        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);


        mStepperLayout.setAdapter(new RegistrationStepperAdapter(getSupportFragmentManager(), this));
        mStepperLayout.setListener(this);

        viewModelRegistration = ViewModelProviders.of(this).get(ViewModelRegistration.class);
    }


    @Override
    public void onCompleted(final View completeButton) {
        UserInfo userInfo = viewModelRegistration.getUserInfoMutableLiveData().getValue();
        if(userInfo.isUnfilledFields())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.fill_the_fields);
            builder.setMessage(userInfo.getUnfilledFields(getBaseContext()));
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();;
                }
            });
            builder.create().show();
        }
        else {
            viewModelRegistration.createNewUser().observe(this, new Observer<JSONObject>() {
                @Override
                public void onChanged(@Nullable JSONObject jsonObject) {
                    Snackbar.make(completeButton, R.string.please_login, Snackbar.LENGTH_LONG).show();
                    finish();
                }
            });
        }
    }

    @Override
    public void onError(VerificationError verificationError) {

    }

    @Override
    public void onStepSelected(int newStepPosition) {
        UserInfo userInfo = viewModelRegistration.getUserInfoMutableLiveData().getValue();
        if(newStepPosition == 2 && userInfo.isUnfilledFields())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.fill_the_fields);
            builder.setMessage(userInfo.getUnfilledFields(getBaseContext()));
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.create().show();
        }
    }

    @Override
    public void onReturn() {

    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
