package com.aplusd.houserenter.rentlodging.availablelodgings;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.model.Lodging;
import com.aplusd.houserenter.model.LodgingImg;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * @author Azamat Dzhonov
 * @date 03.04.2018
 */

public class LodgingDetailInformation extends AppCompatActivity implements OnMapReadyCallback {

    public static String INTENT_LODGING = "lodging";
    public static String INTENT_SHOWMODE = "showmode";


    private Lodging lodging = null;
    private GoogleMap googleMap = null;


    private SliderLayout sliderLayout;
    private TextView tvHouseType;
    private TextView tvLocation;
    private TextView tvPeopleCount;
    private TextView tvHostName;
    private ImageView IVHost;
    private TextView tvDescription;
    private RelativeLayout layoutHostInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lodging_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sliderLayout = findViewById(R.id.slider);
        tvHouseType = findViewById(R.id.tvHouseType);
        tvLocation =  findViewById(R.id.tvLocation);
        tvPeopleCount  = findViewById(R.id.tvPeopleCount);
        tvHostName  = findViewById(R.id.tvHostName);
        IVHost  = findViewById(R.id.IVHost);
        tvDescription  = findViewById(R.id.tvDescription);
        layoutHostInfo = findViewById(R.id.layoutHostInfo);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        final BottomSheetBehavior bottomSheetBehavior =
                BottomSheetBehavior.from(findViewById(R.id.orderBottomSheet));

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setPeekHeight(215);

        final boolean showMode =  getIntent().getBooleanExtra(INTENT_SHOWMODE, false);

        final RentLodgingFragment rentLodgingFragment = new RentLodgingFragment();

        if(!showMode) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frameLayout, rentLodgingFragment);
            fragmentTransaction.commitNow();
        }


        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)mapFragment.getView().getLayoutParams();
        params.bottomMargin = bottomSheetBehavior.getPeekHeight();
        mapFragment.getView().setLayoutParams(params);


        lodging = (Lodging) getIntent().getSerializableExtra(INTENT_LODGING);
        setLodgingInfo(lodging);

        layoutHostInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ViewModelLodgingInformation viewModelLodgingInformation = ViewModelProviders.of(this).get(ViewModelLodgingInformation.class);
        viewModelLodgingInformation.getLodgingInfo(lodging).observe(this, new Observer<Lodging>() {
            @Override
            public void onChanged(@Nullable Lodging updated) {
                setLodgingInfo(updated);
                if(!showMode)
                    rentLodgingFragment.setLodging(updated);
            }
        });

    }



    private void setLodgingInfo(Lodging lg)
    {
        getSupportActionBar().setTitle(lg.getHouseName());
        tvHouseType.setText(Lodging.getHouseType(getBaseContext(), lg.getHouseOrderType()));
        tvDescription.setText(lg.getHouseDescription());
        tvPeopleCount.setText(getString(R.string.people_count) + " " + lg.getHouseGuestCount());

        if(lg.getPlace() != null)
            tvLocation.setText(lg.getPlace());

        if(lg.getUserInfo() != null) {
            tvHostName.setText(lg.getUserInfo().getUserName());
            Picasso.with(getBaseContext()).load(lg.getUserInfo().getUserAvatar()).into(IVHost);
        }

        if(lg.getLatLng() != null && googleMap != null) {
            googleMap.addCircle(new CircleOptions()
                    .center(lg.getLatLng())
                    .radius(150)
                    .strokeColor(Color.BLACK)
                    .strokeWidth(1f));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lg.getLatLng(), 15));
        }

        if(lg.getLodgingImgs() != null && lg.getLodgingImgs().size() > 0)
        {
            sliderLayout.removeAllSliders();
            ArrayList<LodgingImg> images = lg.getLodgingImgs();
            for(int i = 0; i < images.size(); i++)
            {
                DefaultSliderView slider = new DefaultSliderView(this);
                slider.image(images.get(i).getImgPath());
                slider.setScaleType(BaseSliderView.ScaleType.CenterInside);
                sliderLayout.addSlider(slider);
            }
            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            sliderLayout.setDuration(5000);
        }
        else {
            sliderLayout.removeAllSliders();
            sliderLayout.addSlider(new DefaultSliderView(this).image(lg.getHouseMainImg()));
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        this.googleMap.getUiSettings().setAllGesturesEnabled(false);
        this.googleMap.getUiSettings().setZoomControlsEnabled(false);
        this.googleMap.getUiSettings().setRotateGesturesEnabled(false);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item2:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,  lodging.getLodgingInfo(getBaseContext()));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lodgingdetailinformation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}