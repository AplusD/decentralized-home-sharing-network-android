package com.aplusd.houserenter.rentlodging.contract;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.model.Lodging;
import com.aplusd.houserenter.user.SPHelper;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

/**
 * @author Azamat Dzhonov
 * @date 09.04.2018
 */

public class RentHouseActivity extends AppCompatActivity  implements StepperLayout.StepperListener{

    public static String INTENT_LODGING = "lodging";

    private ViewModelContract viewModelLodgingInformation = null;

    private Lodging lodging = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(R.string.order_detail);

        if(getIntent().hasExtra(INTENT_LODGING))
            lodging = (Lodging)getIntent().getSerializableExtra(INTENT_LODGING);

        viewModelLodgingInformation = ViewModelProviders.of(this).get(ViewModelContract.class);
        viewModelLodgingInformation.setLodging(lodging);

        StepperLayout mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new ContractSteps(getSupportFragmentManager(), this));
        mStepperLayout.setListener(this);


    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }

    @Override
    public void onCompleted(View completeButton) {
        if(SPHelper.getPrivateKey(getBaseContext()).equals("none") || SPHelper.getPublicKey(getBaseContext()).equals("none"))
        {
            Snackbar.make(completeButton, R.string.enter_your_private_key_in_settings, Snackbar.LENGTH_LONG).show();
            return;
        }

        findViewById(R.id.layoutStartContract).setVisibility(View.VISIBLE);
        findViewById(R.id.stepperLayout).setVisibility(View.GONE);

        if(getSupportActionBar() != null)
            getSupportActionBar().hide();



         viewModelLodgingInformation.startContract(getBaseContext(), new ViewModelContract.CallBackStartContract() {
             @Override
             public void success() {
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         finish();
                     }
                 });
             }

             @Override
             public void failed() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            findViewById(R.id.layoutStartContract).setVisibility(View.GONE);
                            findViewById(R.id.stepperLayout).setVisibility(View.VISIBLE);
                            Snackbar.make(findViewById(R.id.stepperLayout),
                                    R.string.face_with_problem_try_again, Snackbar.LENGTH_SHORT).show();
                        }
                    });
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
