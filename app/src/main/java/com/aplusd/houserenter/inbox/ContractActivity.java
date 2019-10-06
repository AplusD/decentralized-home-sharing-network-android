package com.aplusd.houserenter.inbox;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.chat.ChatActivity;
import com.aplusd.houserenter.inbox.fragments.ContractStatusFragment;
import com.aplusd.houserenter.inbox.fragments.HouseInfoFragment;
import com.aplusd.houserenter.inbox.fragments.PartnerInfoFragment;
import com.aplusd.houserenter.inbox.model.Offering;

import static com.aplusd.houserenter.chat.ChatActivity.CONTRACT_ID;
import static com.aplusd.houserenter.chat.ChatActivity.PARTNER_NAME;

/**
 * @author Azamat Dzhonov
 * @date 25.04.2018
 */

public class ContractActivity extends AppCompatActivity {

    public static String IS_YOUR_HOST = "isyourhost";
    public static String OFFERING_INTENT = "offering";

    private Offering offering = null;
    private Boolean isyourhost = null;

    private YourContractsViewModel yourContractsViewModel = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_contract);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        yourContractsViewModel = ViewModelProviders.of(this).get(YourContractsViewModel.class);
    }


    @Override
    protected void onResume() {
        super.onResume();

        HouseInfoFragment houseInfoFragment =
                (HouseInfoFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentFragmentHouseInfo);

        PartnerInfoFragment partnerInfoFragment =
                (PartnerInfoFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentPartnerInfo);

        ContractStatusFragment contractStatusFragment =
                (ContractStatusFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContractStatus);

        if(getIntent().hasExtra(OFFERING_INTENT)) {
            offering = (Offering) getIntent().getSerializableExtra(OFFERING_INTENT);
            isyourhost = getIntent().getBooleanExtra(IS_YOUR_HOST, false);

            houseInfoFragment.setOffering(offering);
            partnerInfoFragment.setOffering(offering);
            contractStatusFragment.setOffering(offering, isyourhost);

            if(isyourhost)
                getSupportActionBar().setTitle(R.string.your_offering);
            else
                getSupportActionBar().setTitle(R.string.your_trip);
        }
        else
            throw new IllegalArgumentException("Not offering or trip");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contract, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra(CONTRACT_ID, offering.getContractId() );
                intent.putExtra(PARTNER_NAME, offering.getPartnerName());
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void setProgress(boolean isProgress, String msg)
    {
        if(isProgress)
        {
            findViewById(R.id.layoutProgress).setVisibility(View.VISIBLE);
            ((TextView)findViewById(R.id.tvStatus)).setText(msg);
        }
        else
        {
            findViewById(R.id.layoutProgress).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.tvStatus)).setText("");
        }
    }
}
