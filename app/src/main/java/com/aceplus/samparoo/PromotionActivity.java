package com.aceplus.samparoo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.aceplus.samparoo.marketing.PagerAdapter;
import com.aceplus.samparoo.promotion.PromotionAdaper;
import com.aceplus.samparoo.utils.Utils;

/**
 * Created by haker on 2/23/17.
 */
public class PromotionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Promotion Price"));
        tabLayout.addTab(tabLayout.newTab().setText("Promotion Gift"));
        tabLayout.addTab(tabLayout.newTab().setText("Volume Discount"));
        tabLayout.addTab(tabLayout.newTab().setText("Volume Discount Filter"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(Color.WHITE, Color.RED);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PromotionAdaper adapter = new PromotionAdaper
                (getSupportFragmentManager(), tabLayout.getTabCount());
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
    }

    @Override
    public void onBackPressed() {

        Utils.backToHome(this);
    }
}
