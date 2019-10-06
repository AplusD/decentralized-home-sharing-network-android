package com.aplusd.houserenter.model;

import android.support.v4.app.Fragment;

/**
 * @author Azamat Dzhonov
 * @date 22.02.2018
 */

public class Tab {

    private Fragment fragment = null;
    private String title = null;
    private int imageResource = -1;

    public Tab(Fragment fragment, String title, int imageResource)
    {
        this.fragment = fragment;
        this.title = title;
        this.imageResource = imageResource;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public String getTitle() {
        return title;
    }

    public int getImageRecouse() {
        return imageResource;
    }
}
