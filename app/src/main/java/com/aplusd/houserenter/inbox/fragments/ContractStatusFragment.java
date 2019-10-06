package com.aplusd.houserenter.inbox.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.inbox.YourContractsViewModel;
import com.aplusd.houserenter.inbox.model.Offering;
import com.aplusd.houserenter.user.SPHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * @author Azamat Dzhonov
 * @date 25.04.2018
 */

public class ContractStatusFragment extends Fragment implements View.OnClickListener{

    private View view = null;

    private Boolean isHost = null;
    private Offering offering = null;

    private Button buttonAccept = null;
    private Button buttonCancel = null;

    private YourContractsViewModel viewModel = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(YourContractsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contract_status_fragment, container, false);
        buttonAccept = view.findViewById(R.id.btConfirm);
        buttonCancel = view.findViewById(R.id.btCancel);
        return view;
    }

    public void setOffering(final Offering offering, Boolean isHost)
    {
        this.isHost = isHost;
        this.offering = offering;

        View viewPrice = view.findViewById(R.id.viewPrice);
        ((TextView)(viewPrice.findViewById(R.id.tvOptionName))).setText(R.string.date);
        ((TextView)(viewPrice.findViewById(R.id.tvOptionValue))).setText( offering.getDates(getContext()));



        final View viewDate = view.findViewById(R.id.viewDates);
        ((TextView)(viewDate.findViewById(R.id.tvOptionName))).setText(R.string.price);
        ((TextView)(viewDate.findViewById(R.id.tvOptionValue))).setText( offering.getPrice() + "$ (~" + offering.getContractPriceEther() + " Eth)");

        View viewAddress = view.findViewById(R.id.viewAddress);
        ((TextView)(viewAddress.findViewById(R.id.tvOptionName))).setText(R.string.house_address);
        ((TextView)(viewAddress.findViewById(R.id.tvOptionValue))).setText( offering.getHouseAddress());

        View viewContactPhone = view.findViewById(R.id.viewContactPhone);
        viewContactPhone.setOnClickListener(this);
        ((TextView)(viewContactPhone.findViewById(R.id.tvOptionName))).setText(R.string.phone);
        ((TextView)(viewContactPhone.findViewById(R.id.tvOptionValue))).setText( offering.getPartnerPhoneNumber());

        View viewEmail = view.findViewById(R.id.viewEmail);
        viewEmail.setOnClickListener(this);
        ((TextView)(viewEmail.findViewById(R.id.tvOptionName))).setText(R.string.email);
        ((TextView)(viewEmail.findViewById(R.id.tvOptionValue))).setText( offering.getPartnerEmail());

        view.findViewById(R.id.tvProblem).setOnClickListener(this);

        ((TextView)view.findViewById(R.id.tvStatus)).setText(Offering.parseStatus(offering.getContractStatus(), getContext()));

        if(isHost)
            parseHost(offering);
        else
            parseClient(offering);
    }

    @Override
    public void onClick(View w)
    {
        switch (w.getId())
        {
            case R.id.viewContactPhone:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + offering.getPartnerPhoneNumber()));
                startActivity(intent);
                break;

            case R.id.viewEmail:
                sendEmail(offering.getPartnerEmail(), "House renting");
                break;

            case R.id.tvProblem:
                sendEmail("atdzhonov@edu.hse.ru", "Contract: " + offering.getContractId() + " problem");
        }
    }

    public void sendEmail(String whom, String str)
    {
        Intent i = new  Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_EMAIL, new String[] {whom});
        i.putExtra(Intent.EXTRA_SUBJECT, str);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void parseClient(final Offering offering)
    {
        if(offering.getContractStatus() == 1)
        {
            buttonAccept.setVisibility(View.GONE);
            buttonCancel.setText(R.string.cancel_trip);
            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    buttonCancel.setEnabled(false);

                    viewModel.cancelContract(getContext(), offering.getContractId()).observe(getActivity(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(@Nullable Boolean aBoolean) {
                            if(aBoolean == null)
                                return;

                            if(aBoolean) {
                                Snackbar.make(view, R.string.contract_canceled, Snackbar.LENGTH_LONG).show();
                                getActivity().finish();
                            }
                            else {
                                Snackbar.make(view, R.string.problem_with_canceling, Snackbar.LENGTH_LONG).show();
                                buttonCancel.setEnabled(true);
                            }
                        }
                    });
                }
            });
        }
        if(offering.getContractStatus() == 2)
        {
            SupportMapFragment supportMapFragment = new SupportMapFragment();
            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    LatLng point = new LatLng(offering.getHouseLat(), offering.getHouseLng());

                    googleMap.addMarker(new MarkerOptions()
                            .position(point));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
                    googleMap.getUiSettings().setMyLocationButtonEnabled(true);

                }
            });

            getFragmentManager().beginTransaction().add(view.findViewById(R.id.map).getId(),
                    supportMapFragment).commitNow();
            view.findViewById(R.id.map).setVisibility(View.VISIBLE);

            view.findViewById(R.id.additionalInformation).setVisibility(View.VISIBLE);
            buttonCancel.setVisibility(View.GONE);
            buttonAccept.setVisibility(View.VISIBLE);
            buttonAccept.setText(R.string.over_deal);
            buttonAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    if(SPHelper.getPublicKey(getContext()).equals("none")) {
                        Snackbar.make(view, R.string.public_key, Snackbar.LENGTH_LONG).show();
                        return;
                    }
                    if(SPHelper.getPrivateKey(getContext()).equals("none") || SPHelper.getPrivateKey(getContext()).length() != 64)
                    {
                        Snackbar.make(view, R.string.private_key_is_null, Snackbar.LENGTH_LONG).show();
                        return;
                    }
                    buttonAccept.setEnabled(false);

                    viewModel.finishContract(getContext(), offering).observe(getActivity(), new Observer<Integer>() {
                        @Override
                        public void onChanged(@Nullable Integer result) {

                            buttonAccept.setEnabled(true);
                            if(result == null)
                                return;

                            if(result == 1) {
                                Snackbar.make(view, R.string.contract_over_money_transfered, Snackbar.LENGTH_LONG).show();
                                getActivity().finish();
                            } else
                            if(result == 0){
                                Snackbar.make(view, R.string.problem_with_sending_money, Snackbar.LENGTH_LONG).show();
                            }
                            else if (result == 2){
                                Snackbar.make(view, R.string.check_your_private_and_public_key, Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
        }
    }

    public  void parseHost(final Offering offering)
    {
        if(offering.getContractStatus() == 1)
        {

            buttonCancel.setText(R.string.cancel_to_trip);
            buttonAccept.setText(R.string.accept_contract);

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    buttonCancel.setEnabled(false);

                    viewModel.cancelContract(getContext(), offering.getContractId()).observe(getActivity(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(@Nullable Boolean aBoolean) {
                            if(aBoolean == null)
                                return;

                            if(aBoolean) {
                                Snackbar.make(view, R.string.contract_canceled, Snackbar.LENGTH_LONG).show();
                                getActivity().finish();
                            }
                            else {
                                Snackbar.make(view, R.string.problem_with_canceling, Snackbar.LENGTH_LONG).show();
                                buttonCancel.setEnabled(true);
                            }
                        }
                    });
                }
            });

            buttonAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    buttonAccept.setEnabled(false);
                    viewModel.updateContractStatus(getContext(), offering, 2).observe(getActivity(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(@Nullable Boolean aBoolean) {
                            if(aBoolean == null)
                                return;

                            if(aBoolean) {
                                offering.setContractStatus(offering.getContractStatus() + 1);
                                parseHost(offering);
                            }
                            else {
                                Snackbar.make(view, R.string.problem_with_accepting_deal, Snackbar.LENGTH_LONG).show();
                                buttonAccept.setEnabled(true);
                            }
                        }
                    });
                }
            });
        }
        if(offering.getContractStatus() == 2)
        {
            view.findViewById(R.id.additionalInformation).setVisibility(View.VISIBLE);
            view.findViewById(R.id.viewAddress).setVisibility(View.GONE);
            buttonCancel.setVisibility(View.GONE);
            buttonAccept.setVisibility(View.GONE);
        }
    }



}
