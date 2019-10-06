package com.aplusd.houserenter.user.registration.fragments;

import android.arch.lifecycle.ViewModelProviders;
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
import com.aplusd.houserenter.user.registration.ViewModelRegistration;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

/**
 * @author Azamat Dzhonov
 * @date 12.05.2018
 */
public class UserInfoFragment extends Fragment implements Step{

    private View view = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final ViewModelRegistration userViewModel = ViewModelProviders.of(getActivity()).get(ViewModelRegistration.class);

        final EditText edName = (EditText)view.findViewById(R.id.edName);
        edName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().isEmpty())
                {

                }
                else
                {
                   UserInfo userInfo =  userViewModel.getUserInfoMutableLiveData().getValue();
                   userInfo.setUserName(charSequence.toString());
                   userViewModel.getUserInfoMutableLiveData().setValue(userInfo);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final EditText edSurname = (EditText)view.findViewById(R.id.edSurname);
        edSurname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                UserInfo userInfo =  userViewModel.getUserInfoMutableLiveData().getValue();
                userInfo.setUserSurname(charSequence.toString());
                userViewModel.getUserInfoMutableLiveData().setValue(userInfo);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        final EditText edEmail = (EditText)view.findViewById(R.id.edEmail);
        edEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                UserInfo userInfo =  userViewModel.getUserInfoMutableLiveData().getValue();
                userInfo.setUserEmail(charSequence.toString());
                userViewModel.getUserInfoMutableLiveData().setValue(userInfo);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final EditText edPhoneNumber = (EditText)view.findViewById(R.id.edPhoneNumber);
        edPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                UserInfo userInfo =  userViewModel.getUserInfoMutableLiveData().getValue();
                userInfo.setUserPhoneNumber(charSequence.toString());
                userViewModel.getUserInfoMutableLiveData().setValue(userInfo);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final EditText edPassword = (EditText)view.findViewById(R.id.edPassword);
        edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                UserInfo userInfo =  userViewModel.getUserInfoMutableLiveData().getValue();
                userInfo.setPassword(charSequence.toString());
                userViewModel.getUserInfoMutableLiveData().setValue(userInfo);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final EditText edBirthDay = (EditText)view.findViewById(R.id.edBirthDay);
        edBirthDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                UserInfo userInfo =  userViewModel.getUserInfoMutableLiveData().getValue();
                userInfo.setUserBirthDay(charSequence.toString());
                userViewModel.getUserInfoMutableLiveData().setValue(userInfo);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final EditText edBio = (EditText)view.findViewById(R.id.edBio);
        edBio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                UserInfo userInfo =  userViewModel.getUserInfoMutableLiveData().getValue();
                userInfo.setUserDescription(charSequence.toString());
                userViewModel.getUserInfoMutableLiveData().setValue(userInfo);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =   inflater.inflate(R.layout.fragment_user_info, container, false);
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
