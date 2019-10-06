package com.aplusd.houserenter.leaselodging;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.model.Lodging;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

/**
 * @author Azamat Dzhonov
 * @date 16.03.2018
 */

public class AddNewLodgingActivity extends AppCompatActivity  implements StepperLayout.StepperListener{

    private StepperLayout mStepperLayout;

    private ViewModelNewLodging viewModelNewLodging = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lease);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.lets_ready_for_guests);

        viewModelNewLodging = ViewModelProviders.of(this).get(ViewModelNewLodging.class);

        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new StepsAdapter(getSupportFragmentManager(), this));
        mStepperLayout.setListener(this);
    }

    @Override
    public void onStepSelected(int newStepPosition) {
        if(newStepPosition == 4)
        {
            findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            viewModelNewLodging.uploadLodging(this).observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer houseId) {
                    if(houseId == null)
                        return;

                    findViewById(R.id.progressBar).setVisibility(View.GONE);
                    if(houseId == -1)
                        Snackbar.make(findViewById(R.id.progressBar), R.string.problem_with_creating_lodging, Snackbar.LENGTH_LONG).show();
                    else
                    {
                        Lodging lodging = viewModelNewLodging.getNewLodging().getValue();
                        lodging.setHouseId(houseId);
                        viewModelNewLodging.getNewLodging().setValue(lodging);
                    }
                }
            });
        }
    }

    @Override
    public void onCompleted(final View completeButton) {
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        viewModelNewLodging.publishHouse(viewModelNewLodging.getNewLodging().getValue().getHouseId()).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean == null)
                    return;

                findViewById(R.id.progressBar).setVisibility(View.GONE);
                if(aBoolean)
                    finish();
                else
                    Snackbar.make(completeButton, R.string.problem_with_creating_lodging, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onError(VerificationError verificationError) {

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
