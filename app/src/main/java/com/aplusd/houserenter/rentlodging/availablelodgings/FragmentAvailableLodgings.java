package com.aplusd.houserenter.rentlodging.availablelodgings;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.mapactivity.LodgingsMapActivity;
import com.aplusd.houserenter.model.Lodging;

import java.util.ArrayList;

/**
 * @author Azamat Dzhonov
 * @date 20.02.2018
 */

public class FragmentAvailableLodgings extends Fragment {

    private View view = null;

    private AdapterLodging adapterLodging = null;

    private ViewModelLodgingInformation viewModelLodgingInformation = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_available_leases, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapterLodging);

        final SwipeRefreshLayout mSwipeRefreshLayout =  view.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                viewModelLodgingInformation.getAvailableLeases(getContext()).requestData(getContext());
                Snackbar.make(view, R.string.data_refreshed, Snackbar.LENGTH_SHORT).show();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mSwipeRefreshLayout.setEnabled(linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
            }
        });


        view.findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getActivity(), LodgingsMapActivity.class));
            }
        });

        view.findViewById(R.id.searchLL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view, R.string.only_moscow_now, Snackbar.LENGTH_LONG ).show();
            }
        });


        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModelLodgingInformation = ViewModelProviders.of(getActivity()).get(ViewModelLodgingInformation.class);

            viewModelLodgingInformation.getAvailableLeases(getContext()).observe(this, new Observer<ArrayList<Lodging>>() {
                @Override
                public void onChanged(@Nullable ArrayList<Lodging> lodgings) {
                    ProgressBar progressBar = view.findViewById(R.id.progressBar);

                    if(lodgings.size() > 0 && progressBar.getVisibility() == View.VISIBLE)
                        progressBar.setVisibility(View.GONE);

                    adapterLodging.setLodgings(lodgings);


                }
            });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(adapterLodging == null)
            adapterLodging = new AdapterLodging(context);
    }
}
