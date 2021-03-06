package com.aceplus.samparoo.promotion;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.aceplus.samparoo.marketing.TabFragment1;
import com.aceplus.samparoo.marketing.TabFragment2;
import com.aceplus.samparoo.marketing.TabFragment3;
import com.aceplus.samparoo.marketing.TabFragment4;
import com.aceplus.samparoo.marketing.TabFragment5;

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
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
