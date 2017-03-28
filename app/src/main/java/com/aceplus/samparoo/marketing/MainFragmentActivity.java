package com.aceplus.samparoo.marketing;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.SyncActivity;
import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.utils.Utils;
import com.firetrap.permissionhelper.action.OnDenyAction;
import com.firetrap.permissionhelper.action.OnGrantAction;
import com.firetrap.permissionhelper.helper.PermissionHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by haker on 2/4/17.
 */

public class MainFragmentActivity extends AppCompatActivity {
    public static final String CUSTOMER_INFO_KEY = "customer-info-key";
    public static Customer customer;

    public static final String USER_INFO_KEY = "user-info-key";
    public static JSONObject userInfo;

    final int mode = Activity.MODE_PRIVATE;
    final String MyPREFS = "MyPreference";
    SharedPreferences mySharedPreference;
    SharedPreferences.Editor myEditor;

    public static String customerId = "";

    private PermissionHelper.PermissionBuilder permissionRequest;
    private static final int REQUEST_CAMERA = 411;
    private OnDenyAction onDenyAction = new OnDenyAction() {
        @Override
        public void call(int i, boolean b) {
            Toast.makeText(MainFragmentActivity.this, "Camera Access Denied", Toast.LENGTH_SHORT).show();
        }
    };
    private OnGrantAction onGrantAction = new OnGrantAction() {
        @Override
        public void call(int i) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing);

        if (Utils.isOsMarshmallow()) {
            askPermission();
        }

        customer = (Customer) getIntent().getSerializableExtra(CUSTOMER_INFO_KEY);

        mySharedPreference = getSharedPreferences(MyPREFS, mode);
        myEditor = mySharedPreference.edit();

        customerId = customer.getCustomerId();

        myEditor.putString("CustomerID", customerId);
        myEditor.commit();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        // tabLayout.addTab(tabLayout.newTab().setText("Outlet External Check"));
        tabLayout.addTab(tabLayout.newTab().setText("Display Assessment"));
        tabLayout.addTab(tabLayout.newTab().setText("POSM"));
        // tabLayout.addTab(tabLayout.newTab().setText("Competitors' Activities"));
        tabLayout.addTab(tabLayout.newTab().setText("Size in Store Share"));
        tabLayout.addTab(tabLayout.newTab().setText("Outlet Stock Availability"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(Color.WHITE, Color.RED);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        /*viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
                    getSupportActionBar().setTitle("Outlet");
                }
                if(position == 1) {
                    getSupportActionBar().setTitle("Display");
                }
                if(position == 2) {
                    getSupportActionBar().setTitle("Com");
                }
                if(position == 3) {
                    getSupportActionBar().setTitle("Size");
                }
                if(position == 4) {
                    getSupportActionBar().setTitle("Stock");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/

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

        customer = (Customer) getIntent().getSerializableExtra(CUSTOMER_INFO_KEY);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    void askPermission() {
        permissionRequest = PermissionHelper.with(MainFragmentActivity.this).build(Manifest.permission.CAMERA).onPermissionsGranted(onGrantAction).onPermissionsDenied(onDenyAction).request(REQUEST_CAMERA);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Destroy", "True");
    }

    @Override
    public void onBackPressed() {

        Utils.backToCustomer(this);
    }
}
