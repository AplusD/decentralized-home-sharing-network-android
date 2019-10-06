package com.aplusd.houserenter.leaselodging;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aplusd.houserenter.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * @author Azamat Dzhonov
 * @date 23.05.2018
 */
public class AdapterImage extends RecyclerView.Adapter<AdapterImage.ViewHolder> {

    private Context context = null;
    private ArrayList<String> photosPath = null;

    public AdapterImage(Context context)
    {
        this.context = context;
        photosPath = new ArrayList<>();
    }

    public void addImage(String str)
    {
        photosPath.add(str);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return  photosPath.size();
    }


    @Override
    public void onBindViewHolder(final AdapterImage.ViewHolder holder, final int position) {
            Picasso.with(context)
                                .load(photosPath.get(position))
                                .error(R.drawable.noimage)
                                .centerInside()
                                .resize(context.getResources().getDisplayMetrics().widthPixels,250)
                                .into(holder.imageView);

    }

    @Override
    public AdapterImage.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return  new ViewHolder(new ImageView(context));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView = null;

        public ViewHolder(View v)
        {
            super(v);
            imageView = (ImageView)v;
        }
    }
}
