package com.aplusd.houserenter.leaselodging.lodginginformationfragments;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.extra.ViewModelExtraFunction;
import com.aplusd.houserenter.leaselodging.AdapterImage;
import com.aplusd.houserenter.leaselodging.ViewModelNewLodging;
import com.squareup.picasso.Picasso;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

/**
 * @author Azamat Dzhonov
 * @date 19.05.2018
 */
public class PhotoFragment extends Fragment implements Step {

    private  ViewModelNewLodging viewModelNewLodging = null;
    private AdapterImage adapterImage = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModelNewLodging = ViewModelProviders.of(getActivity()).get(ViewModelNewLodging.class);

        getView().findViewById(R.id.tvAddnewLodging).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, 1112);
            }
        });

        getView().findViewById(R.id.cardViewPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, 1111);
            }
        });


        adapterImage = new AdapterImage(getContext());
        ((RecyclerView)getView().findViewById(R.id.recyclerView)).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ((RecyclerView)getView().findViewById(R.id.recyclerView)).setAdapter(adapterImage);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == 1111) {
                if (data == null)
                    return;

                ViewModelExtraFunction extraFunction = ViewModelProviders.of(this).get(ViewModelExtraFunction.class);
                getView().findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

                extraFunction.uploadImage(getRealPathFromURI(data.getData())).observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        if(s == null)
                            return;

                        getView().findViewById(R.id.progressBar).setVisibility(View.GONE);

                        viewModelNewLodging.setMainImg(viewModelNewLodging.getNewLodging().getValue().getHouseId(), s).observe(getActivity(), new Observer<Boolean>() {
                            @Override
                            public void onChanged(@Nullable Boolean aBoolean) {
                                if(aBoolean == null)
                                    return;
                            }
                        });


                        Picasso.with(getContext())
                                .load(s)
                                .error(R.drawable.noimage)
                                .centerInside()
                                .resize(getContext().getResources().getDisplayMetrics().widthPixels,250)
                                .into((ImageView)getView().findViewById(R.id.ivUserPhoto));

                    }
                });

            }

            if(requestCode == 1112)
            {
                if (data == null)
                    return;

                ViewModelExtraFunction extraFunction = ViewModelProviders.of(this).get(ViewModelExtraFunction.class);
                getView().findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

                extraFunction.uploadImage(getRealPathFromURI(data.getData())).observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        if(s == null)
                            return;

                        getView().findViewById(R.id.progressBar).setVisibility(View.GONE);

                        viewModelNewLodging.addLodgingImg(viewModelNewLodging.getNewLodging().getValue().getHouseId(), s).observe(getActivity(), new Observer<Boolean>() {
                            @Override
                            public void onChanged(@Nullable Boolean aBoolean) {
                                if(aBoolean == null)
                                    return;
                            }
                        });

                        adapterImage.addImage(s);

                    }
                });
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_load_house, container, false);
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}