package com.aceplus.samparoo.customer;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.utils.Database;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by ESeries on 12/10/2015.
 */
public class AddNewCustomerLocationActivity extends FragmentActivity {

    public static final String USER_INFO_KEY = "user-info-key";
    private JSONObject userInfo;

    SQLiteDatabase database;

    GoogleMap map;
    SharedPreferences sharedPreferences;

    ImageView backImg;
    EditText addressEdit;
    ImageView searchImg;

    String customerLat = null;
    String customerLng = null;

    String customerName = null;
    String customerPhone = null;
    String customerAddress = null;
    String contactPerson = null;
    String zonePosition;
    String customerCategoryPosition;
    int locationCount = 0;
    int markerCount = 0;
    String townshipPositon;
    double latitude = 0.0;
    double longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_customer_map);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        database = new Database(this).getDataBase();
        //getSaleManRouteDB();
        registerIDs();
        catchEvents();
    }

    private void registerIDs() {
        backImg = (ImageView) findViewById(R.id.back_img);
        addressEdit = (EditText) findViewById(R.id.address_txt);
        searchImg = (ImageView) findViewById(R.id.search_img);
    }

    private void catchEvents() {
        customerName = getIntent().getStringExtra("customerName");
        customerPhone = getIntent().getStringExtra("phoneNumber");
        customerAddress = getIntent().getStringExtra("customerAddress");
        contactPerson = getIntent().getStringExtra("contactPerson");
        zonePosition = getIntent().getStringExtra("zonePosition");
        townshipPositon = getIntent().getStringExtra("townshipPosition");
        customerCategoryPosition = getIntent().getStringExtra("customerCategoryPosition");
        customerLat = getIntent().getStringExtra("customerLat");
        customerLng = getIntent().getStringExtra("customerLng");
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());//AIzaSyD1O_cBbfeGE8h54vmy_dwh1iWVyszVztE
        if (status != ConnectionResult.SUCCESS) {
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        } else {
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            fm.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    map = googleMap;

                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(AddNewCustomerLocationActivity.this,
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {
                            //Location Permission already granted

                            map.setMyLocationEnabled(true);
                        } else {
                            //Request Location Permission
                            checkLocationPermission();
                        }
                    }

                    map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

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
                    map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                        @Override
                        public void onMapLongClick(LatLng point) {
                            map.clear();
                            markerCount = 0;
                            customerLat = null;
                            customerLng = null;
                        }
                    });

                    if (!(customerLat == null)) {
                        drawMarker(new LatLng(Double.valueOf(customerLat), Double.valueOf(customerLng)));
                    }
                }
            });
        }



        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = addressEdit.getText().toString();
                if (location != null || !location.equals("")) {
                    List<Address> addressList = null;
                    Geocoder geocoder = new Geocoder(AddNewCustomerLocationActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        android.location.Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(customerLat == null)) {
                    AddNewCustomerActivity.customerLat = customerLat;
                    AddNewCustomerActivity.customerLng = customerLng;
                    AddNewCustomerActivity.putCustomerName = customerName;
                    AddNewCustomerActivity.putcustomerPhone = customerPhone;
                    AddNewCustomerActivity.putcustomerAddress = customerAddress;
                    AddNewCustomerActivity.putcontactPerson = contactPerson;
                    AddNewCustomerActivity.putzonePosition = zonePosition;
                    AddNewCustomerActivity.putcustomerCategoryPosition = customerCategoryPosition;
                    AddNewCustomerActivity.puttownshipPosition = townshipPositon;

                    Intent intent = new Intent(AddNewCustomerLocationActivity.this
                            , AddNewCustomerActivity.class);
                    /*intent.putExtra(AddNewCustomerLocationActivity.USER_INFO_KEY
                            , userInfo.toString());*/
                    startActivity(intent);
                    finish();
                } else {
                    new AlertDialog.Builder(AddNewCustomerLocationActivity.this)
                            .setTitle("Customer Location")
                            .setMessage("You should choice your location?")
                            .setNeutralButton("Ok", null)
                            .show();
                }
            }
        });
    }

    private void drawMarker(LatLng point) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point);
        map.addMarker(markerOptions);
    }
/*

    private void getSaleManRouteDB() {
        database.beginTransaction();
        Cursor cursor = database.rawQuery("SELECT * FROM SALE_MAN", null);
        while (cursor.moveToNext()) {
            double getlatitude = cursor.getDouble(cursor.getColumnIndex("LATITUDE"));
            double getlongitude = cursor.getDouble(cursor.getColumnIndex("LONGITUDE"));
            this.latitude = getlatitude;
            this.longitude = getlongitude;
        }
        cursor.close();
        database.setTransactionSuccessful();
        database.endTransaction();
    }
*/

    final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new android.support.v7.app.AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(AddNewCustomerLocationActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        }).create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
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
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        map.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}

