package com.aplusd.houserenter.leaselodging.lodginginformationfragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.model.Lodging;
import com.aplusd.houserenter.model.LodgingType;
import com.aplusd.houserenter.leaselodging.ViewModelNewLodging;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;

/**
 * @author Azamat Dzhonov
 * @date 29.03.2018
 */

public class LodgingTypeFragment extends Fragment implements Step {

    private  View view = null;

    private BottomSheetBehavior bottomSheetBehavior = null;
    private FragmentRecyclerView fragmentRecyclerView = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ViewModelNewLodging viewModelRenting = ViewModelProviders.of(getActivity()).get(ViewModelNewLodging.class);



        viewModelRenting.getTypesHouse(getContext()).observe(this, new Observer<ArrayList<LodgingType>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<LodgingType> lodgingTypes) {
                if(lodgingTypes.size() > 0)
                    fragmentRecyclerView.setList(lodgingTypes, new FragmentRecyclerView.ElementSelectedCallBack() {
                        @Override
                        public void selectedItem(int i) {

                            Lodging lodging = viewModelRenting.getNewLodging().getValue();
                            lodging.setHouseOrderType(lodgingTypes.get(i).getHouseOrderType());
                            viewModelRenting.getNewLodging().setValue(lodging);

                            ((TextView)view.findViewById(R.id.houseTypeOption)
                                    .findViewById(R.id.tvOptionValue)).setText(lodgingTypes.get(i).getHouseOrderTypeDescription(getContext()));


                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                        }
                    });
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_housetype, container, false);

        ((TextView)view.findViewById(R.id.houseTypeOption)
                .findViewById(R.id.tvOptionName)).setText(R.string.select_house_type);
        ((TextView)view.findViewById(R.id.houseTypeOption)
                .findViewById(R.id.tvOptionValue)).setText(R.string.house);

         bottomSheetBehavior =
                BottomSheetBehavior.from(view.findViewById(R.id.houseItemOptionBottomSheet));

        bottomSheetBehavior.setHideable(true);

        fragmentRecyclerView = new FragmentRecyclerView();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, fragmentRecyclerView);
        fragmentTransaction.commit();

        view.findViewById(R.id.houseTypeOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
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
