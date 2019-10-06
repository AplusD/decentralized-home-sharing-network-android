package com.aplusd.houserenter.leaselodging.lodginginformationfragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.model.LodgingType;

import java.util.ArrayList;

/**
 * @author Azamat Dzhonov
 * @date 09.04.2018
 */

public class FragmentRecyclerView extends Fragment {

    private RecyclerView recyclerView = null;
    private ElementSelectedCallBack callBack = null;

    public interface ElementSelectedCallBack
    {
         void selectedItem(int i);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    public void setList(ArrayList<LodgingType> lodgingTypes, ElementSelectedCallBack callBack)
    {
        if(recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(new RecyclerViewAdapter(lodgingTypes));
        }
        this.callBack = callBack;
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
    {
        private  ArrayList<LodgingType> lodgingTypes = null;

        public RecyclerViewAdapter(ArrayList<LodgingType> lodgingTypes)
        {
            this.lodgingTypes = lodgingTypes;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerViewAdapter.ViewHolder(
                    (LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_recycler_view, parent, false)));
        }

        @Override
        public int getItemCount() {
            return lodgingTypes.size();
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.itemName.setText(lodgingTypes.get(position).getHouseOrderTypeDescription(getContext()));
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.selectedItem(position);
                }
            });
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {
            private View view  = null;
            private TextView itemName = null;

            public ViewHolder(View view)
            {
                super(view);
                this.view = view;
                this.itemName = view.findViewById(R.id.tvOptionValue);
            }
        }
    }
}
