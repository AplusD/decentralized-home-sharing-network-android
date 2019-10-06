package com.aplusd.houserenter.rentlodging.contract.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.model.Constants;
import com.aplusd.houserenter.rentlodging.model.Contract;
import com.aplusd.houserenter.rentlodging.contract.ViewModelContract;
import com.aplusd.houserenter.rentlodging.contract.fragments.uielements.DateFragment;
import com.aplusd.houserenter.rentlodging.contract.fragments.uielements.GuestCountFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.text.SimpleDateFormat;

/**
 * @author Azamat Dzhonov
 * @date 10.04.2018
 */

public class ContractDetailFragment extends Fragment implements Step, View.OnClickListener {

    private  View view = null;

    private BottomSheetBehavior bottomSheetBehavior = null;

    private GuestCountFragment guestCountFragment = null;
    private DateFragment dateFragment = null;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelContract viewModel =  ViewModelProviders.of(getActivity()).get(ViewModelContract.class);

        viewModel.getNewContract().observe(this, new Observer<Contract>() {
            @Override
            public void onChanged(@Nullable Contract contract) {
                ((TextView)view.findViewById(R.id.guestsCount)
                        .findViewById(R.id.tvOptionValue)).setText(String.valueOf(contract.getPeopleCount()));

                ((TextView)view.findViewById(R.id.data).findViewById(R.id.tvOptionValue))
                        .setText(simpleDateFormat.format(contract.getFromDate()) + " - " + simpleDateFormat.format(contract.getToDate()));
            }
        });


        view.findViewById(R.id.data).setOnClickListener(this);
        view.findViewById(R.id.guestsCount).setOnClickListener(this);

        guestCountFragment = new GuestCountFragment();
        dateFragment = new DateFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contract_detail, container, false);

        bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottomSheetContractDetail));

        ((TextView)view.findViewById(R.id.data).findViewById(R.id.tvOptionName)).setText(R.string.date);
        ((TextView)view.findViewById(R.id.data).findViewById(R.id.tvOptionValue)).setText(R.string.not_selected);
        ((TextView)view.findViewById(R.id.guestsCount).findViewById(R.id.tvOptionName)).setText(R.string.guests);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.data:
                getFragmentManager().beginTransaction().replace(R.id.frameLayout, dateFragment).commitNow();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                break;
            case R.id.guestsCount:
                getFragmentManager().beginTransaction().replace(R.id.frameLayout, guestCountFragment).commitNow();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
        }
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
