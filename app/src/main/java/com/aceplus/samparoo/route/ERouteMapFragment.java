package com.aceplus.samparoo.route;

import android.Manifest;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                mMap.setMyLocationEnabled(true);

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
        });

        return view;
    }
}
