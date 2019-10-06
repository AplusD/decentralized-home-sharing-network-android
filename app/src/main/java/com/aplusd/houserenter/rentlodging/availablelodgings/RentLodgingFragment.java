package com.aplusd.houserenter.rentlodging.availablelodgings;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.model.Lodging;
import com.aplusd.houserenter.rentlodging.contract.RentHouseActivity;

/**
 * @author Azamat Dzhonov
 * @date 09.04.2018
 */

public class RentLodgingFragment extends Fragment {

    private Button btSend = null;
    private TextView tvPrice = null;

    private View view = null;

    private Lodging lodging = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ViewModelLodgingInformation viewModelLodgingInformation = ViewModelProviders.of(getActivity()).get(ViewModelLodgingInformation.class);


        btSend.setText(R.string.send_request);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RentHouseActivity.class);
                intent.putExtra(RentHouseActivity.INTENT_LODGING, lodging);
                startActivity(intent);
                if(getActivity() != null)
                    getActivity().finish();
            }
        });




    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_rent_house, container, false);
        btSend = view.findViewById(R.id.btContract);
        tvPrice = view.findViewById(R.id.tvPrice);
        return view;
    }

    public void setLodging(final Lodging lodging)
    {
        this.lodging = lodging;
        tvPrice.setText(String.format(getContext().getString(R.string.money_per_day), lodging.getDayPrice()));
    }
}
