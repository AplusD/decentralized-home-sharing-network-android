package com.aplusd.houserenter.rentlodging.contract.fragments.uielements;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.rentlodging.model.Contract;
import com.aplusd.houserenter.rentlodging.contract.ViewModelContract;

/**
 * @author Azamat Dzhonov
 * @date 10.04.2018
 */

public class GuestCountFragment extends Fragment implements View.OnClickListener {

    private ViewModelContract viewModel = null;

    private View view = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel =  ViewModelProviders.of(getActivity()).get(ViewModelContract.class);

        viewModel.getNewContract().observe(this, new Observer<Contract>() {
            @Override
            public void onChanged(@Nullable Contract contract) {
                ((TextView)view.findViewById(R.id.itemPlusMinus).findViewById(R.id.tvCount))
                        .setText(String.valueOf(contract.getPeopleCount()));
            }
        });

        view.findViewById(R.id.itemPlusMinus).findViewById(R.id.ivMinus).setOnClickListener(this);
        view.findViewById(R.id.itemPlusMinus).findViewById(R.id.ivPlus).setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_guest_count, container, false);
        ((TextView)view.findViewById(R.id.itemPlusMinus).findViewById(R.id.tvOptionName)).setText(R.string.adult_count);
        return view;
    }

    @Override
    public void onClick(View view) {
        Contract contract =  viewModel.getNewContract().getValue();
         switch (view.getId())
         {
             case R.id.ivMinus:
                 if(contract.getPeopleCount() != 0)
                    contract.setPeopleCount(contract.getPeopleCount() - 1);
                 break;
             case R.id.ivPlus:
                 if(contract.getPeopleCount() <= viewModel.getLodging().getHouseGuestCount())
                     contract.setPeopleCount(contract.getPeopleCount() + 1);
                 break;
         }
        viewModel.getNewContract().setValue(contract);
    }
}
