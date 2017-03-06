package com.aceplus.samparoo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aceplus.samparoo.route.ECalenderFragment;
import com.aceplus.samparoo.route.ERouteListFragment;
import com.aceplus.samparoo.route.ERouteMapFragment;
import com.aceplus.samparoo.utils.Utils;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemSelected;

/**
 * Created by haker on 2/6/17.
 */
public class RouteActivity extends AppCompatActivity {

    @InjectView(R.id.title)
    TextView textViewTitle;

    @InjectView(R.id.spinnerRoute)
    Spinner spinnerRoute;

    String[] routes = {"e-Route : View By Map", "e-Route : View By List", "e-Calendar"};

    String selected_route = "View By Map";

    public static Fragment fragment = null;
    public static Class fragmentClass = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        ButterKnife.inject(this);

        setUpSpinner();

        fragmentClass = ERouteMapFragment.class;
        callFragment();
    }

    @OnClick(R.id.cancel_img)
    void back() {
        onBackPressed();
    }

    private void setUpSpinner() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, routes);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoute.setAdapter(arrayAdapter);
    }

    @OnItemSelected(R.id.spinnerRoute)
    void chooseRoute() {
        selected_route = spinnerRoute.getSelectedItem().toString();
        Log.i("selected_route>>>", selected_route);

        switch (selected_route) {
            case "e-Route : View By Map" : fragmentClass = ERouteMapFragment.class;
                Log.i("Map", "Map");
                break;

            case "e-Route : View By List" : fragmentClass = ERouteListFragment.class;
                Log.i("List", "List");
                break;

            case "e-Calendar" : fragmentClass = ECalenderFragment.class;
                textViewTitle.setText("E-CALENDAR");
                Log.i("Cal", "Cal");
                break;

            default: fragmentClass = ERouteMapFragment.class;
                Log.i("Map", "Map");
                break;
        }
        callFragment();
    }

    public void callFragment() {
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayoutRoute, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        Utils.backToHome(this);
    }
}
