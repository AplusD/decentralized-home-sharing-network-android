package com.aplusd.houserenter.user;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.model.Constants;
import com.aplusd.houserenter.model.UserInfo;
import com.aplusd.houserenter.wallet.WalletActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.Date;

import static com.aplusd.houserenter.model.Constants.parseDateToShow;

/**
 * @author Azamat Dzhonov
 * @date 21.02.2018
 */

public class FragmentProfile extends Fragment implements View.OnClickListener {

    private View view = null;


    private View viewSurname = null;
    private View viewEmail = null;
    private View viewBio = null;
    private View viewPhoneNumber = null;
    private View viewUserBirthDay = null;
    private View viewUserWallet = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        userViewModel.getUserInfo(getContext()).observe(this, new Observer<UserInfo>() {
            @Override
            public void onChanged(@Nullable UserInfo userInfo) {
                if(userInfo == null)
                    return;

                Picasso.with(getContext())
                        .load(userInfo.getUserAvatar())
                        .into((ImageView)view.findViewById(R.id.ivUserPhoto));

                ((TextView)view.findViewById(R.id.tvUserName)).setText(userInfo.getUserName());
                ((TextView)viewSurname.findViewById(R.id.tvOptionValue)).setText(userInfo.getUserSurname());
                ((TextView)viewEmail.findViewById(R.id.tvOptionValue)).setText(userInfo.getUserEmail());
                ((TextView)viewBio.findViewById(R.id.tvOptionValue)).setText(userInfo.getUserDescription());
                ((TextView)viewPhoneNumber.findViewById(R.id.tvOptionValue)).setText(String.valueOf(userInfo.getUserPhoneNumber()));

                try {
                    Date date = Constants.parseDateFromServer.parse(userInfo.getUserBirthDay());
                    ((TextView)viewUserBirthDay.findViewById(R.id.tvOptionValue)).setText(parseDateToShow.format(date));
                }
                catch (ParseException ex)
                {
                    ((TextView)viewUserBirthDay.findViewById(R.id.tvOptionValue)).setText(userInfo.getUserBirthDay());
                }

                ((TextView)viewUserWallet.findViewById(R.id.tvOptionValue)).setText(userInfo.getUserWallet());

                viewUserWallet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getContext(), WalletActivity.class));
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.viewAskQuestion)
        {
            Intent i = new  Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_EMAIL, new String[] {"atdzhonov@edu.hse.ru"});
            i.putExtra(Intent.EXTRA_SUBJECT, "Lease/rent lodging app");
            try {
                startActivity(Intent.createChooser(i, "Send mail..."));
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }
        }
        if(view.getId() == R.id.viewLogOut)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.do_you_want_log_out);
            builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SPHelper.clearUserInformation(getContext());
                    startActivity(new Intent(getActivity(), SplashActivity.class));
                    getActivity().finish();
                }
            });

            builder.show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        View viewAskQuestion = view.findViewById(R.id.viewAskQuestion);
        viewAskQuestion.setOnClickListener(this);

        View viewLogOut = view.findViewById(R.id.viewLogOut);
        viewLogOut.setOnClickListener(this);

        viewSurname = view.findViewById(R.id.viewSurname);
        ((TextView)viewSurname.findViewById(R.id.tvOptionName)).setText(R.string.surname);

        viewEmail = view.findViewById(R.id.viewEmail);
        ((TextView)viewEmail.findViewById(R.id.tvOptionName)).setText(R.string.email);

        viewBio = view.findViewById(R.id.viewBio);
        ((TextView)viewBio.findViewById(R.id.tvOptionName)).setText(R.string.bio);

        viewPhoneNumber = view.findViewById(R.id.viewPhoneNumber);
        ((TextView)viewPhoneNumber.findViewById(R.id.tvOptionName)).setText(R.string.phone);

        viewUserBirthDay = view.findViewById(R.id.viewUserBirthDay);
        ((TextView)viewUserBirthDay.findViewById(R.id.tvOptionName)).setText(R.string.birthday);

        viewUserWallet = view.findViewById(R.id.viewUserWallet);
        ((TextView)viewUserWallet.findViewById(R.id.tvOptionName)).setText(R.string.viewUserWallet);
        viewUserWallet.findViewById(R.id.vLine).setVisibility(View.INVISIBLE);

        return view;
    }
}
