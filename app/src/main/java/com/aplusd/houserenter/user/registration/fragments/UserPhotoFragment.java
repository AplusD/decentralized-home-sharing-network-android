package com.aplusd.houserenter.user.registration.fragments;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.extra.ViewModelExtraFunction;
import com.aplusd.houserenter.model.UserInfo;
import com.aplusd.houserenter.user.registration.ViewModelRegistration;
import com.squareup.picasso.Picasso;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

/**
 * @author Azamat Dzhonov
 * @date 12.05.2018
 */
public class UserPhotoFragment extends Fragment implements Step {

    private View view = null;

    private ViewModelRegistration userViewModel = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userViewModel = ViewModelProviders.of(getActivity()).get(ViewModelRegistration.class);

        view.findViewById(R.id.cardViewPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, 1111);
            }
        });


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
                view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                extraFunction.uploadImage(getRealPathFromURI(data.getData())).observe(this, new Observer<String>() {
                        @Override
                        public void onChanged(@Nullable String s) {
                            if(s == null)
                                return;

                            view.findViewById(R.id.progressBar).setVisibility(View.GONE);

                            UserInfo userInfo =  userViewModel.getUserInfoMutableLiveData().getValue();
                            userInfo.setUserAvatar(s);
                            userViewModel.getUserInfoMutableLiveData().setValue(userInfo);

                            Picasso.with(getContext())
                                    .load(s)
                                    .error(R.drawable.noimage)
                                    .centerInside()
                                    .resize(getContext().getResources().getDisplayMetrics().widthPixels,250)
                                    .into((ImageView)view.findViewById(R.id.ivUserPhoto));

                        }
                    });

            }
        }
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_load_photo, container, false);
        return  view;
    }


    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}
