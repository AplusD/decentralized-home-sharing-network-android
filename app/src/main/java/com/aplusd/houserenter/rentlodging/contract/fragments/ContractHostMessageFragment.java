package com.aplusd.houserenter.rentlodging.contract.fragments;

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
import com.aplusd.houserenter.rentlodging.model.Contract;
import com.aplusd.houserenter.rentlodging.contract.ViewModelContract;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

/**
 * @author Azamat Dzhonov
 * @date 10.04.2018
 */

public class ContractHostMessageFragment extends Fragment implements Step {

    private ViewModelContract viewModel = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(ViewModelContract.class);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_host_message, container, false);

        EditText editText = view.findViewById(R.id.etMsgForHouseHolder);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Contract contract = viewModel.getNewContract().getValue();
                contract.setHostMsg(editable.toString());
                viewModel.getNewContract().setValue(contract);
            }
        });

        return view;
    }


    @Override
    public void onSelected() {

    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}