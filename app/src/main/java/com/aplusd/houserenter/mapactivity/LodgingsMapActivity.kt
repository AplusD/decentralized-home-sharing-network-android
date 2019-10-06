package com.aplusd.houserenter.mapactivity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.aplusd.houserenter.R
import com.aplusd.houserenter.rentlodging.availablelodgings.ViewModelLodgingInformation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

/**
 * @author Azamat Dzhonov
 * @date 03.04.2018
 */

class LodgingsMapActivity : AppCompatActivity() {

    internal var lodgingsMapActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leasesmap)

        supportActionBar!!.setTitle(R.string.available_lodgings)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val viewModelLodgingInformation = ViewModelProviders.of(this).get(ViewModelLodgingInformation::class.java)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment



        mapFragment.getMapAsync { googleMap ->
            googleMap.uiSettings.isCompassEnabled = false

            viewModelLodgingInformation.getMarkers(baseContext).observe(lodgingsMapActivity, Observer { lodgingMarkers ->
                findViewById<View>(R.id.progressBar).visibility = View.GONE

                val bounds = LatLngBounds.Builder()
                for (i in lodgingMarkers!!.indices) {

                    bounds.include(LatLng(lodgingMarkers[i].houseLat!!, lodgingMarkers[i].houseLng!!))
                    googleMap.addCircle(
                            CircleOptions().center(LatLng(lodgingMarkers[i].houseLat!!,
                                    lodgingMarkers[i].houseLng!!))
                                    .radius(150.0)
                                    .strokeWidth(5f)
                                    .strokeColor(Color.BLACK))


                    googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 120))
                }
            })
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}
