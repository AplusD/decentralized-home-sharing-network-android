package com.aplusd.houserenter.inbox;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.inbox.Adapters.YourOfferingsAdapter;
import com.aplusd.houserenter.inbox.Adapters.YourTripsAdapter;
import com.aplusd.houserenter.inbox.model.Offering;

import java.util.ArrayList;

/**
 * @author Azamat Dzhonov
 * @date 22.03.2018
 */

public class InboxFragment extends Fragment {

    private View view = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final YourContractsViewModel viewModel = ViewModelProviders.of(this).get(YourContractsViewModel.class);

        final RecyclerView recyclerViewYourOfferings = view.findViewById(R.id.recyclerViewYourOfferings);
        recyclerViewYourOfferings.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        final RecyclerView recylerViewYourTrips = view.findViewById(R.id.recylerViewYourTrips);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recylerViewYourTrips.setLayoutManager(linearLayoutManager);

        final SwipeRefreshLayout mSwipeRefreshLayout =  view.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                Snackbar.make(view, R.string.data_refreshed, Snackbar.LENGTH_SHORT).show();

                viewModel.getOfferings(getContext()).observe(getActivity(), new Observer<ArrayList<Offering>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Offering> offerings) {
                        if(offerings == null)
                            return;
                        recyclerViewYourOfferings.setAdapter(new YourOfferingsAdapter(offerings, getContext()));

                    }
                });

                viewModel.getTrips(getContext()).observe(getActivity(), new Observer<ArrayList<Offering>>() {
                    @Override
                    public void onChanged(@Nullable ArrayList<Offering> trips) {
                        if(trips == null)
                            return;

                        recylerViewYourTrips.setAdapter(new YourTripsAdapter(trips, getContext()));
                    }
                });
            }
        });


        recylerViewYourTrips.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mSwipeRefreshLayout.setEnabled(linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
            }
        });



        viewModel.getOfferings(getContext()).observe(this, new Observer<ArrayList<Offering>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Offering> offerings) {
                if(offerings == null)
                    return;
                recyclerViewYourOfferings.setAdapter(new YourOfferingsAdapter(offerings, getContext()));

            }
        });

        viewModel.getTrips(getContext()).observe(this, new Observer<ArrayList<Offering>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Offering> trips) {
                if(trips == null)
                    return;

                recylerViewYourTrips.setAdapter(new YourTripsAdapter(trips, getContext()));
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_inbox, container, false);
       return view;
    }
}
