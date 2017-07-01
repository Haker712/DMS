package com.aceplus.samparoo.route;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.utils.Constant;
import com.aceplus.samparoo.utils.Database;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by haker on 2/6/17.
 */
public class ERouteMapFragment extends Fragment{

    View view;

    private GoogleMap mMap;

    double latitude = 0.0, longitude = 0.0;

    FragmentActivity activity;

    SQLiteDatabase sqLiteDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_e_route_map, container, false);

        activity = getActivity();

        sqLiteDatabase = new Database(activity).getDataBase();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.getUiSettings().setMyLocationButtonEnabled(true);

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(activity,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        //Location Permission already granted

                        mMap.setMyLocationEnabled(true);
                    } else {
                        //Request Location Permission
                        checkLocationPermission();
                    }
                } else {
                    mMap.setMyLocationEnabled(true);
                }

                setCustomerLocation();
            }
        });

        return view;
    }

    private void setCustomerLocation() {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from CUSTOMER", null);
        while (cursor.moveToNext()) {
            // Add a marker in Sydney and move the camera
            //LatLng sydney = new LatLng(-34, 151);
            //LatLng sydney = new LatLng(latitude, longitude);
            LatLng latLng = new LatLng(cursor.getDouble(cursor.getColumnIndex("LATITUDE")), cursor.getDouble(cursor.getColumnIndex("LONGITUDE")));
            //MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(merchants.getName()).snippet(merchants.getOpeningTime() + " To " + merchants.getClosingTime());
            MarkerOptions marker = new MarkerOptions().position(latLng);
            // Changing marker icon
            //marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.store));
            mMap.addMarker(marker);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            //mMap.setMaxZoomPreference(Constants.KEY_MAX_ZOOM);
            mMap.animateCamera(CameraUpdateFactory.zoomTo(Float.parseFloat(Constant.KEY_MAX_ZOOM)));
        }
    }

    final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(activity)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ActivityCompat.requestPermissions(activity,
                                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                                MY_PERMISSIONS_REQUEST_LOCATION );
                                    }
                        }).create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(activity,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mMap.setMyLocationEnabled(true);
                        setCustomerLocation();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(activity, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
