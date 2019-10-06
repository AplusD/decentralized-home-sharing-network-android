package com.aplusd.houserenter.wallet;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.wallet.model.CurrencyExchange;
import com.aplusd.houserenter.user.SPHelper;

/**
 * @author Azamat Dzhonov
 * @date 15.05.2018
 */
public class WalletActivity  extends AppCompatActivity implements View.OnClickListener{

    private String newPrivateKey = "";
    private String newPublicKey = "";

    private AppCompatActivity appCompatActivity = this;

    private ViewModelWallet viewModelWallet = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_info);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.your_wallet_info);


        EditText edPrivateKey = findViewById(R.id.edPrivateKey);
        EditText edPublicKey = findViewById(R.id.edPublicKey);

        edPrivateKey.setText(SPHelper.getPrivateKey(getBaseContext()));
        edPublicKey.setText(SPHelper.getPublicKey(getBaseContext()));

        newPrivateKey = SPHelper.getPrivateKey(getBaseContext());
        newPublicKey = SPHelper.getPublicKey(getBaseContext());

        findViewById(R.id.buttonUpdate).setOnClickListener(this);

        edPrivateKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newPrivateKey = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edPublicKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newPublicKey = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        viewModelWallet = ViewModelProviders.of(this).get(ViewModelWallet.class);
        viewModelWallet.getAddressEthers(SPHelper.getPublicKey(getBaseContext())).observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s != null) {
                    final double ether = Double.parseDouble(s) / 1000000000000000000.0;
                    ((TextView) findViewById(R.id.tvWei)).setText("Wei: " + s);
                    ((TextView) findViewById(R.id.tvEther)).setText("Ether: " + ether);

                    viewModelWallet.getCurrencyExchange().observe(appCompatActivity, new Observer<CurrencyExchange>() {
                        @Override
                        public void onChanged(@Nullable CurrencyExchange currencyExchange) {
                            if(currencyExchange == null)
                                return;

                            ((TextView) findViewById(R.id.tvDollar)).setText("USD: " + currencyExchange.getDollar(ether));
                            ((TextView)findViewById(R.id.tvCurrencyExchange)).setText(currencyExchange.getString());
                        }
                    });
                }
            }
        });


    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == R.id.buttonUpdate )
        {
            if(newPrivateKey.isEmpty() || newPublicKey.isEmpty())
                Snackbar.make(v, R.string.error_input, Snackbar.LENGTH_SHORT).show();
            else
            {
                SPHelper.setPrivateKey(getBaseContext(), newPrivateKey);
                SPHelper.setPublicKey(getBaseContext(), newPublicKey);

                viewModelWallet.getAddressEthers(SPHelper.getPublicKey(getBaseContext())).observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        if (s != null) {
                            final double ether = Double.parseDouble(s) / 1000000000000000000.0;
                            ((TextView) findViewById(R.id.tvWei)).setText("Wei: " + s);
                            ((TextView) findViewById(R.id.tvEther)).setText("Ether: " + ether);

                            viewModelWallet.getCurrencyExchange().observe(appCompatActivity, new Observer<CurrencyExchange>() {
                                @Override
                                public void onChanged(@Nullable CurrencyExchange currencyExchange) {
                                    if(currencyExchange == null)
                                        return;

                                    ((TextView) findViewById(R.id.tvDollar)).setText("USD: " + currencyExchange.getDollar(ether));
                                    ((TextView)findViewById(R.id.tvCurrencyExchange)).setText(currencyExchange.getString());
                                }
                            });
                        }
                    }
                });
                Snackbar.make(v, R.string.wallet_updated, Snackbar.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
