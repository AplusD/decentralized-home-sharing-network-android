package com.aplusd.houserenter.leaselodging.lodginginformationfragments;

import android.arch.lifecycle.MutableLiveData;
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

public class AddressStepFragment extends Fragment implements Step {

    private View view = null;
    private ViewModelNewLodging viewModelLodgingInformation = null;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModelLodgingInformation = ViewModelProviders.of(getActivity()).get(ViewModelNewLodging.class);

        EditText edNumber = getView().findViewById(R.id.edStreet);
        edNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MutableLiveData<Lodging> newLodging = viewModelLodgingInformation.getNewLodging();
                Lodging lodging = newLodging.getValue();
                lodging.setHouseAddress(charSequence.toString());
                newLodging.setValue(lodging);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_houseaddress, container, false);
        return  view;
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
