package com.aplusd.houserenter.mainactivityui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.inbox.InboxFragment;
import com.aplusd.houserenter.model.Tab;
import com.aplusd.houserenter.leaselodging.FragmentYourLodgings;
import com.aplusd.houserenter.rentlodging.availablelodgings.FragmentAvailableLodgings;
import com.aplusd.houserenter.user.FragmentProfile;

import java.util.ArrayList;

/**
 * @author Azamat Dzhonov
 * @date 20.02.2018
 */

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tabs);
        ViewPager viewPager = findViewById(R.id.viewpager);

        ArrayList<Tab> tabs = new ArrayList<>();

        tabs.add(new Tab(new FragmentAvailableLodgings(), getString(R.string.trips), R.drawable.ic_tab_availablehouses));
//        tabs.add(new Tab(new BookmarksFragment(), getString(R.string.bookmarks), R.drawable.ic_tab_bookmarks));
        tabs.add(new Tab(new FragmentYourLodgings(), getString(R.string.listings), R.drawable.ic_tab_yourhouses));
        tabs.add(new Tab(new InboxFragment(), getString(R.string.inbox), R.drawable.ic_tab_inbox));
        tabs.add(new Tab(new FragmentProfile(), getString(R.string.profile), R.drawable.ic_tab_profile));

        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(), getBaseContext(), tabs));
        viewPager.setOffscreenPageLimit(tabs.size());
        tabLayout.setupWithViewPager(viewPager);

        for(int i = 0; i < tabLayout.getTabCount(); i++)
             tabLayout.getTabAt(i).setIcon(tabs.get(i).getImageRecouse());


    }
}
