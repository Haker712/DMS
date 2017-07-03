package com.aceplus.samparoo.customer;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

import com.aceplus.samparoo.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CustomerLoactionActivity extends ActionBarActivity {

    private ImageView cancelImg;
    GoogleMap googleMap;
    SharedPreferences sharedPreferences;

    public static String customerName = null;
    public static String address = null;
    public static int visitRecord = 0;
    int locationCount = 0;
    public static double latitude = 0.0;
    public static double longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_loaction);
        registerIDs();
        catchEvents();
    }

    private void registerIDs() {
        cancelImg = (ImageView) findViewById(R.id.cancel_img);
    }

    private void catchEvents() {
        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerLoactionActivity.this.onBackPressed();
            }
        });

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        if (status != ConnectionResult.SUCCESS) {

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        } else {
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            googleMap = fm.getMap();

            googleMap.setMyLocationEnabled(true);

            sharedPreferences = getSharedPreferences("location", 0);

            locationCount = sharedPreferences.getInt("locationCount", 0);

            GPSTracker gpsTracker = new GPSTracker(CustomerLoactionActivity.this);
            Double lat = 0.0, lon = 0.0;

            if (gpsTracker.canGetLocation()) {
                lat = gpsTracker.getLatitude();
                lon = gpsTracker.getLongitude();
            }

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lon)));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
        /*if (visitRecord == 0) {
            drawMarker(new LatLng(latitude, longitude), customerName, address);
        } else if (visitRecord == 1) {
            drawMarker2(new LatLng(latitude, longitude), customerName, address);
        }*/
    }

    private void drawMarker(LatLng point, String title, String snippet) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point).title(title)
                .snippet(snippet);
        googleMap.addMarker(markerOptions);
    }

    private void drawMarker2(LatLng point, String title, String snippet) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point).title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .snippet(snippet);
        googleMap.addMarker(markerOptions);
    }
}
