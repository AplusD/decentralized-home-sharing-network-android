package com.aplusd.houserenter.leaselodging;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.leaselodging.lodginginformationfragments.AddressStepFragment;
import com.aplusd.houserenter.leaselodging.lodginginformationfragments.HouseInformationStepFragment;
import com.aplusd.houserenter.leaselodging.lodginginformationfragments.LodgingTypeFragment;
import com.aplusd.houserenter.leaselodging.lodginginformationfragments.MapStepFragment;
import com.aplusd.houserenter.leaselodging.lodginginformationfragments.PhotoFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

/**
 * @author Azamat Dzhonov
 * @date 29.03.2018
 */

public class StepsAdapter extends AbstractFragmentStepAdapter {

    private Context context = null;

    public StepsAdapter(FragmentManager fm, Context context) {
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
                builder.setTitle(R.string.houseTypeInformation);
                break;
            case 1:
                builder.setTitle(R.string.houseAddress);
                break;
            case 2:
                builder.setTitle(R.string.houseMap);
                break;
            case 3:
                builder.setTitle(R.string.houseInformation);
                builder.setEndButtonLabel(R.string.registar_lodging);
                break;
            case 4:
                builder.setTitle(R.string.loading_photo);
                builder.setEndButtonLabel(R.string.publish_house);
                break;
        }

        return  builder.create();
    }

    @Override
    public Step createStep(int position) {
        switch (position)
        {
            case 0:
                return new LodgingTypeFragment();
            case 1:
                return new AddressStepFragment();
            case 2:
                return new MapStepFragment();
            case 3:
                return new HouseInformationStepFragment();
            case 4:
                return new PhotoFragment();

            default:
                throw new IllegalArgumentException("Unsupported position: " + position);

        }
    }

    @Override
    public int getCount() {
        return 5;
    }


}
