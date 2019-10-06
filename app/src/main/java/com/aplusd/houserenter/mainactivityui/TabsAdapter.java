package com.aplusd.houserenter.mainactivityui;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aplusd.houserenter.model.Tab;

import java.util.ArrayList;

/**
 * @author Azamat Dzhonov
 * @date 20.02.2018
 */

public class TabsAdapter extends FragmentStatePagerAdapter
{
    private ArrayList<Tab> tabs = null;
    private Context context = null;

    public TabsAdapter(FragmentManager fragmentManager, Context context, ArrayList<Tab> tabs)
    {
        super(fragmentManager);
        this.context = context;
        this.tabs = tabs;
    }


    @Override
    public int getCount() {
       return  tabs.size();
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position).getFragment();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return "";
       // return tabs.get(position).getTitle();
    }
}
