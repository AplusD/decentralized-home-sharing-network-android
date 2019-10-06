package com.aplusd.houserenter.leaselodging.lodginginformationfragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.model.Lodging;
import com.aplusd.houserenter.leaselodging.ViewModelNewLodging;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

/**
 * @author Azamat Dzhonov
 * @date 29.03.2018
 */

public class HouseInformationStepFragment extends Fragment implements Step {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ViewModelNewLodging viewModelRenting = ViewModelProviders.of(getActivity()).get(ViewModelNewLodging.class);


        ((EditText)getView().findViewById(R.id.edHouseName)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Lodging lodging = viewModelRenting.getNewLodging().getValue();
                lodging.setHouseName(charSequence.toString());
                viewModelRenting.getNewLodging().setValue(lodging);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ((EditText)getView().findViewById(R.id.guestCount)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().isEmpty())
                    return;

                Lodging lodging = viewModelRenting.getNewLodging().getValue();
                lodging.setHouseGuestCount(Integer.valueOf(charSequence.toString()));
                viewModelRenting.getNewLodging().setValue(lodging);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ((EditText)getView().findViewById(R.id.edPricePerDay)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Lodging lodging = viewModelRenting.getNewLodging().getValue();
                lodging.setDayPrice(Integer.valueOf(charSequence.toString()));
                viewModelRenting.getNewLodging().setValue(lodging);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ((EditText)getView().findViewById(R.id.edHouseBio)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Lodging lodging = viewModelRenting.getNewLodging().getValue();
                lodging.setHouseDescription(charSequence.toString());
                viewModelRenting.getNewLodging().setValue(lodging);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_houseinformation, container, false);
        return view;
    }


    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }
}
