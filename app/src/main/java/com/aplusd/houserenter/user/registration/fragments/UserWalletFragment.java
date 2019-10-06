package com.aplusd.houserenter.user.registration.fragments;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.model.UserInfo;
import com.aplusd.houserenter.user.SPHelper;
import com.aplusd.houserenter.user.registration.ViewModelRegistration;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

/**
 * @author Azamat Dzhonov
 * @date 14.05.2018
 */
public class UserWalletFragment extends Fragment implements Step{


    private View view = null;

    private AlertDialog alertDialog = null;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ViewModelRegistration userViewModel = ViewModelProviders.of(getActivity()).get(ViewModelRegistration.class);

        EditText edPublicKey = view.findViewById(R.id.edPublicKey);
        edPublicKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SPHelper.setPublicKey(getContext(), charSequence.toString());
                UserInfo userInfo =  userViewModel.getUserInfoMutableLiveData().getValue();
                userInfo.setUserWallet(charSequence.toString());
                userViewModel.getUserInfoMutableLiveData().setValue(userInfo);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        EditText edPrivateKey = view.findViewById(R.id.edPrivateKey);
        edPrivateKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                SPHelper.setPrivateKey(getContext(), charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        view.findViewById(R.id.tvCreateWallet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.myetherwallet.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_wallet, container, false);
        return view;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {
        if(alertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.create_wallet_title);
            builder.setMessage(R.string.create_wallet_text);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog = builder.create();
        }
        alertDialog.show();
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }



}
