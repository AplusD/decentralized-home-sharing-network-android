package com.aplusd.houserenter.user.registration;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.user.registration.fragments.UserInfoFragment;
import com.aplusd.houserenter.user.registration.fragments.UserPhotoFragment;
import com.aplusd.houserenter.user.registration.fragments.UserWalletFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

/**
 * @author Azamat Dzhonov
 * @date 12.05.2018
 */
public class RegistrationStepperAdapter extends AbstractFragmentStepAdapter {

    private Context context = null;

    public RegistrationStepperAdapter(FragmentManager fm, Context context) {
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
                builder.setTitle(R.string.user_info);
                break;
            case 1:
                builder.setTitle(R.string.create_a_wallet);
                break;
            case 2:
                builder.setTitle(R.string.load_photo);
                builder.setEndButtonLabel(R.string.create_user);
                break;
        }
        return  builder.create();
    }

    @Override
    public Step createStep(int position) {
        switch (position)
        {
            case 0:
                return new UserInfoFragment();
            case 1:
                return new UserWalletFragment();
            case 2:
                return new UserPhotoFragment();
            default:
                throw new IllegalArgumentException("Unsupported position: " + position);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
