package com.aplusd.houserenter.rentlodging.availablelodgings;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.model.Lodging;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Azamat Dzhonov
 * @date 15.03.2018
 */

public class AdapterLodging extends RecyclerView.Adapter<AdapterLodging.ViewHolder> {

    private ArrayList<Lodging> lodgings = null;
    private Context context = null;
    private Boolean showMode = false;

    public void setShowMode(Boolean flag)
    {
        showMode = flag;
    }

    public AdapterLodging(Context context)
    {
        this.context = context;
        this.lodgings = new ArrayList<>();
    }

    public void setLodgings(ArrayList<Lodging> lodgings)
    {
        this.lodgings = lodgings;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return lodgings.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.houseName.setText(lodgings.get(position).getHouseName());
        holder.housePrice.setText(String.format(context.getString(R.string.money_per_day), lodgings.get(position).getDayPrice()));
         holder.houseInfo.setText(Lodging.getHouseType(context, lodgings.get(position).getHouseOrderType()));
        holder.rating.setRating(new Random().nextInt(5));


        Picasso.with(context)
                .load(lodgings.get(position).getHouseMainImg())
                .error(R.drawable.noimage)
                .centerInside()
                .resize(context.getResources().getDisplayMetrics().widthPixels,250)
                .into(holder.houseImage);


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LodgingDetailInformation.class);
                intent.putExtra(LodgingDetailInformation.INTENT_LODGING, lodgings.get(position));
                if(showMode)
                    intent.putExtra(LodgingDetailInformation.INTENT_SHOWMODE, true);
                context.startActivity(intent);
            }
        });

        holder.isFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.isFavorite.setImageResource(R.drawable.icon_bookmarks_activated);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
          return new ViewHolder
                  (LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_lodging, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView houseImage;
        private ImageView isFavorite;
        private TextView houseName;
        private TextView houseInfo;
        private TextView housePrice;
        private RatingBar rating;

        private View view;

        public ViewHolder(View view)
        {
            super(view);
            this.view = view;
            this.houseImage = view.findViewById(R.id.ivMain);
            this.houseName = view.findViewById(R.id.tvHouseName);
            this.houseInfo = view.findViewById(R.id.tvMainHouseInfo);
            this.housePrice = view.findViewById(R.id.tvPrice);
            this.rating = view.findViewById(R.id.ratingBar);
            this.isFavorite = view.findViewById(R.id.ivFavorite);

        }
    }
}
