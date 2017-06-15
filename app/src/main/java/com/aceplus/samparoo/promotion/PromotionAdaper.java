package com.aceplus.samparoo.promotion;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by haker on 2/23/17.
 */
public class PromotionAdaper extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PromotionAdaper(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TabFragmentPromotionPrice tab1 = new TabFragmentPromotionPrice();
                return tab1;
            case 1:
                TabFragmentPromotionGift tab2 = new TabFragmentPromotionGift();
                return tab2;
            case 2:
                TabFragmentVolumeDiscount tab3 = new TabFragmentVolumeDiscount();
                return tab3;
            case 3:
                TabFragmentVolumeDiscountFilter tab4 = new TabFragmentVolumeDiscountFilter();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
