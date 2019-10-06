package com.aplusd.houserenter.inbox.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.inbox.ContractActivity;
import com.aplusd.houserenter.inbox.model.Offering;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.aplusd.houserenter.model.Constants.SIMPLE_DATE_FORMAT;

/**
 * @author Azamat Dzhonov
 * @date 24.04.2018
 */

public class YourOfferingsAdapter extends RecyclerView.Adapter<YourOfferingsAdapter.ViewHolder> {

    private ArrayList<Offering> offerings = null;
    private Context context = null;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SIMPLE_DATE_FORMAT);

    public YourOfferingsAdapter(ArrayList<Offering> offerings, Context context)
    {
        this.offerings = offerings;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder
                (LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_offering, parent, false));
    }

    @Override
    public int getItemCount() {
        return offerings.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvDates.setText(offerings.get(position).getDates(context));
        holder.tvPrice.setText(offerings.get(position).getPrice() + "$");
        holder.tvStatus.setText(Offering.parseStatus(offerings.get(position).getContractStatus(), context));
        Picasso.with(context)
                .load(offerings.get(position).getPartnerAvatar())
                .error(R.drawable.noimage)
                .centerInside()
                .resize(context.getResources().getDisplayMetrics().widthPixels,250)
                .into(holder.ivHousePhoto);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContractActivity.class);
                intent.putExtra(ContractActivity.OFFERING_INTENT, offerings.get(position));
                intent.putExtra(ContractActivity.IS_YOUR_HOST, true);
                context.startActivity(intent);
            }
        });
    }



    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView ivHousePhoto = null;
        private View view = null;
        private TextView tvDates = null;
        private TextView tvPrice = null;
        private TextView tvStatus = null;

        public ViewHolder(View v)
        {
            super(v);
            view = v;
            ivHousePhoto = v.findViewById(R.id.ivGuestPhoto);
            tvDates = v.findViewById(R.id.tvDates);
            tvPrice = v.findViewById(R.id.tvPrice);
            tvStatus = v.findViewById(R.id.tvStatus);
        }
    }
}
