package com.aplusd.houserenter.rentlodging.contract;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.rentlodging.contract.fragments.ContractDetailFragment;
import com.aplusd.houserenter.rentlodging.contract.fragments.ContractHostMessageFragment;
import com.aplusd.houserenter.rentlodging.contract.fragments.ContractPaymentFragment;
import com.aplusd.houserenter.rentlodging.contract.fragments.ContractRulesFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

/**
 * @author Azamat Dzhonov
 * @date 10.04.2018
 */

public class ContractSteps extends AbstractFragmentStepAdapter {

    private Context context = null;

    public ContractSteps(FragmentManager fm, Context context) {
        super(fm, context);
        this.context = context;
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(int position) {
        StepViewModel.Builder builder = new StepViewModel.Builder(context);
        builder.setEndButtonLabel(R.string.next);
        builder.setBackButtonLabel(R.string.back);
        switch (position)
        {
            case 0:
                builder.setTitle(R.string.trip_detail);
                break;
            case 1:
                builder.setTitle(R.string.terms_of_use);
                builder.setEndButtonLabel(R.string.confirm);
                break;
            case 2:
                builder.setTitle(R.string.message_for_host);
                break;
            case 3:
                builder.setTitle(R.string.paymant);
                builder.setEndButtonLabel(R.string.confirm_contract);
                break;
        }
        return  builder.create();
    }

    @Override
    public Step createStep(int position) {
        switch (position)
        {
            case 0:
                return new ContractDetailFragment();
            case 1:
                return new ContractRulesFragment();
            case 2:
                return new ContractHostMessageFragment();
            case 3:
                return new ContractPaymentFragment();

            default:
                throw new IllegalArgumentException("Unsupported position: " + position);

        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
