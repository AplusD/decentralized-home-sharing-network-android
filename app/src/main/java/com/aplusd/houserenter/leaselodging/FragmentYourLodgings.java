package com.aplusd.houserenter.leaselodging;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.model.Lodging;
import com.aplusd.houserenter.rentlodging.availablelodgings.AdapterLodging;
import com.aplusd.houserenter.rentlodging.availablelodgings.ViewModelLodgingInformation;
import com.aplusd.houserenter.user.SPHelper;

import java.util.ArrayList;

/**
 * @author Azamat Dzhonov
 * @date 21.02.2018
 */

public class FragmentYourLodgings extends Fragment{

    private  View view = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        final AdapterLodging adapterLodging = new AdapterLodging(getContext());
        adapterLodging.setShowMode(true);
        recyclerView.setAdapter(adapterLodging);


        ViewModelLodgingInformation viewModelLodgingInformation = ViewModelProviders.of(getActivity()).get(ViewModelLodgingInformation.class);
        viewModelLodgingInformation.getAvailableLeases(getContext()).observe(this, new Observer<ArrayList<Lodging>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Lodging> lodgings) {
                if(lodgings == null)
                    return;
                else
                {
                    ArrayList<Lodging> userLodgings = new ArrayList<>();
                    for(int i = 0; i < lodgings.size(); i++)
                    {
                        if(lodgings.get(i).getUserId() == SPHelper.getUserId(getContext()))
                            userLodgings.add(lodgings.get(i));
                    }
                    adapterLodging.setLodgings(userLodgings);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_your_leases, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.your_leases);

        toolbar.inflateMenu(R.menu.add_leases_items);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                startActivityForResult(new Intent(getContext(), AddNewLodgingActivity.class), 111);
                return  true;
            }
        });
        return view;
    }





}
