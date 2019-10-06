package com.aplusd.houserenter.rentlodging.contract.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.rentlodging.model.Contract;
import com.aplusd.houserenter.model.Lodging;
import com.aplusd.houserenter.rentlodging.contract.ViewModelContract;
import com.aplusd.houserenter.wallet.ViewModelWallet;
import com.aplusd.houserenter.wallet.model.CurrencyExchange;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

/**
 * @author Azamat Dzhonov
 * @date 10.04.2018
 */

public class ContractPaymentFragment extends Fragment implements Step {

    private View view = null;

    private ViewModelContract viewModel = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(ViewModelContract.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_contract_payment, container, false);
        return view;
    }

    @Override
    public void onSelected() {
        Lodging lodging = viewModel.getLodging();
        Contract contract = viewModel.getNewContract().getValue();
        final TextView tvContractPrice = view.findViewById(R.id.tvContractPrice);
        TextView tvContractDetail = view.findViewById(R.id.tvContractDetail);

        tvContractDetail.setText(lodging.getDayPrice() + "$" + " x " + contract.getDates().size());

        final int price = lodging.getDayPrice() * contract.getDates().size();

        tvContractPrice.setText(String.valueOf(price + "$"));

        ViewModelProviders.of(this).get(ViewModelWallet.class).getCurrencyExchange().observe(this, new Observer<CurrencyExchange>() {
            @Override
            public void onChanged(@Nullable CurrencyExchange currencyExchange) {
                if(currencyExchange == null)
                    return;

                String ethers = currencyExchange.getEther(price);
                Contract contract1 =  viewModel.getNewContract().getValue();
                contract1.setPriceWei(Double.parseDouble(ethers));
                viewModel.getNewContract().setValue(contract1);

                tvContractPrice.setText(String.valueOf(price + "$ (~" + ethers + " Ether)"));
            }
        });

    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}