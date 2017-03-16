package com.aceplus.samparoo.marketing;

/**
 * Created by ACEPLU049 on 2/24/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }




    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                OutletExternalCheckFragment tab1 = new OutletExternalCheckFragment();
                return tab1;
            case 1:
                DisplayAssessmentFragment tab2 = new DisplayAssessmentFragment();
                return tab2;
            case 2:
                TabFragment3 tab3 = new TabFragment3();
                return tab3;
            case 3:
                TabFragment5 tab4 = new TabFragment5();
                return tab4;
            case 4:
                TabFragment4 tab5 = new TabFragment4();
                return tab5;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
