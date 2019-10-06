package com.aplusd.houserenter.inbox.fragments;

import android.os.Bundle;
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

public class PartnerInfoFragment extends Fragment {

    private View view = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_partner_info, container, false);
        return view;
    }


    public void setOffering(Offering offering )
    {
        ((TextView)view.findViewById(R.id.tvUserName)).setText(offering.getPartnerName());
        ((TextView)view.findViewById(R.id.tvUserSurname)).setText(offering.getPartnerSurname());

        Picasso.with(getContext())
                .load(offering.getPartnerAvatar())
                .error(R.drawable.noimage)
                .centerInside()
                .resize(getContext().getResources().getDisplayMetrics().widthPixels,250)
                .into((ImageView)view.findViewById(R.id.ivGuestPhoto));

    }


}
