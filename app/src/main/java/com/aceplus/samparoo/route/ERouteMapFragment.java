package com.aceplus.samparoo.route;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aceplus.samparoo.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by haker on 2/6/17.
 */
public class ERouteMapFragment extends Fragment {

    View view;

    GoogleMap googleMap;

    SharedPreferences sharedPreferences;

    int locationCount = 0;

    int markerCount = 0;

    double latitude = 0.0;
    double longitude = 0.0;

    String customerLat = null;
    String customerLng = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_e_route_map, container, false);

        //setUpMapView();

        return view;
    }

    private void setUpMapView() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());//AIzaSyD1O_cBbfeGE8h54vmy_dwh1iWVyszVztE
        if (status != ConnectionResult.SUCCESS) {
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getActivity(), requestCode);
            dialog.show();

        } else {
            SupportMapFragment fm = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

            googleMap = fm.getMap();

            googleMap.setMyLocationEnabled(true);

            sharedPreferences = getActivity().getSharedPreferences("location", 0);

            locationCount = sharedPreferences.getInt("locationCount", 0);

            String zoom = sharedPreferences.getString("zoom", "15");

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));

            googleMap.animateCamera(CameraUpdateFactory.zoomTo(Float.parseFloat(zoom)));
        }

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                if (markerCount == 0) {
                    drawMarker(point);
                    customerLat = String.valueOf(point.latitude);
                    customerLng = String.valueOf(point.longitude);
                    markerCount++;
                }
            }
        });
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                googleMap.clear();
                markerCount = 0;
                customerLat = null;
                customerLng = null;
            }
        });
    }

    private void drawMarker(LatLng point) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point);
        googleMap.addMarker(markerOptions);
    }
}
