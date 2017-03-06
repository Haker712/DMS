package com.aceplus.samparoo.report;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.marketing.PagerAdapter;
import com.aceplus.samparoo.saleexchange.SaleExchangePagerAdapter;

/**
 * Created by phonelin on 2/23/17.
 */

public class FragmentSaleExchangeReport extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_saleexchange_report, container, false);


        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.saleexchangetab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Sale Return"));
        tabLayout.addTab(tabLayout.newTab().setText("Sale"));

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.saleexchangepager);
        final SaleExchangePagerAdapter adapter = new SaleExchangePagerAdapter
                (getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }
}
