package com.aplusd.houserenter.inbox.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.inbox.model.Offering;
import com.squareup.picasso.Picasso;

/**
 * @author Azamat Dzhonov
 * @date 25.04.2018
 */

public class HouseInfoFragment  extends Fragment{

    private View view = null;


    private Offering offering = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_house_info_contract, container, false);
       return view;
    }



    public void setOffering(Offering offering)
    {
        this.offering = offering;
        ((TextView)view.findViewById(R.id.tvHouseName)).setText(offering.getHouseName());
        ((TextView)view.findViewById(R.id.tvPrice)).setText(offering.getPrice() + "$");


        Picasso.with(getContext())
                .load(offering.getHouseMainImg())
                .error(R.drawable.noimage)
                .centerInside()
                .resize(getContext().getResources().getDisplayMetrics().widthPixels,250)
                .into((ImageView)view.findViewById(R.id.ivMain));

    }

}
