package com.aceplus.samparoo.customer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.utils.Database;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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

    GoogleMap googleMap;
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
        /*try {

            userInfo = new JSONObject(getIntent().getStringExtra(USER_INFO_KEY));
        } catch (JSONException e) {

            e.printStackTrace();
        }*/
        database = new Database(this).getDataBase();
        getSaleManRouteDB();
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

            googleMap = fm.getMap();

            googleMap.setMyLocationEnabled(true);

            sharedPreferences = getSharedPreferences("location", 0);

            locationCount = sharedPreferences.getInt("locationCount", 0);

            String zoom = sharedPreferences.getString("zoom", "15");

            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));

            googleMap.animateCamera(CameraUpdateFactory.zoomTo(Float.parseFloat(zoom)));
        }

        if (!(customerLat == null)) {
            drawMarker(new LatLng(Double.valueOf(customerLat), Double.valueOf(customerLng)));
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
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

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
        googleMap.addMarker(markerOptions);
    }

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
}

