package com.aceplus.samparoo.saleexchange;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by phonelin on 2/23/17.
 */

public class SaleExchangePagerAdapter extends FragmentStatePagerAdapter {

    int NoofTabs;

    public SaleExchangePagerAdapter(FragmentManager fm, int NoofTabs) {

        super(fm);
        this.NoofTabs=NoofTabs;


    }



    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                SaleExchangeTab1 tab1 = new SaleExchangeTab1();
                return tab1;
            case 1:
                SaleExchangeTab2 tab2 = new SaleExchangeTab2();
                return tab2;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return NoofTabs;
    }
}
