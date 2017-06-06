package com.aceplus.samparoo.customer;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aceplus.samparoo.CustomerVisitActivity;
import com.aceplus.samparoo.LoginActivity;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.myinterface.OnActionClickListener;
import com.aceplus.samparoo.utils.Constant;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddNewCustomerActivity extends FragmentActivity implements OnActionClickListener {

    public static final String USER_INFO_KEY = "user-info-key";
    private JSONObject userInfo;

    SQLiteDatabase database;

    EditText customerNameEditText;
    EditText contactPersonEditText;
    EditText phoneNumberEditText;
    EditText addressEditText;
    Spinner townshipSpinner;
    Spinner customerCategorySpinner;
    Spinner zoneSpinner;
    Spinner districtSpinner;
    Spinner statedivisionSpinner;
    Spinner shoptypeSpinner;
    TextView customerLocationTxt;
    ImageView cancelImg, resetImg, addImg, nextImg;

    ArrayList<JSONObject> townshipList;
    //  ArrayList<JSONObject> customerCategoryList;
    ArrayList<JSONObject> zoneList;
    ArrayList<JSONObject> districtList;
    ArrayList<JSONObject> statedivisionList;
    ArrayList<JSONObject> shoptypeList;

    public static String customerLat = null;
    public static String customerLng = null;
    public static String putCustomerName = null;
    public static String putcustomerPhone = null;
    public static String putcustomerAddress = null;
    public static String putcontactPerson = null;
    public static String putzonePosition = null;
    public static String putcustomerCategoryPosition = null;
    public static String puttownshipPosition = null;
    public static String customerId = null;

    String userId = "";
    String townshipId = "";
    String districtId = "";
    String statedivisionId = "";
    String shoptypeId = "";
    String customerCategoryId = "";
    String customerName = "";
    String contactPerson = "";
    String phoneNo = "";
    String address = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_customer);

        // Hide keyboard on startup.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Utils.setOnActionClickListener(this);

        database = new Database(this).getDataBase();
        registerIDs();
        catchEvents();

        try {


            boolean noDataFlg = false;

            if (LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, "") != null) {
                userId = LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, "");
            } else {
                noDataFlg = true;
            }
            if (shoptypeList != null && shoptypeList.size() != 0) {
                shoptypeId = shoptypeList.get(shoptypeSpinner.getSelectedItemPosition()).getString("shoptypeId");
            } else {
                noDataFlg = true;

            }

            if (townshipList != null && townshipList.size() != 0) {
                townshipId = townshipList.get(townshipSpinner.getSelectedItemPosition()).getString("townshipId");
            } else {
                noDataFlg = true;
            }

//            if(customerCategoryList != null && customerCategoryList.size() != 0) {
//                customerCategoryId=customerCategoryList.get(customerCategorySpinner.getSelectedItemPosition()).getString("id");
//            } else {
//                noDataFlg = true;
//            }

            if (districtList != null && districtList.size() != 0) {
                districtId = districtList.get(districtSpinner.getSelectedItemPosition()).getString("districtId");
            } else {
                noDataFlg = true;
            }

            if (statedivisionList != null && statedivisionList.size() != 0) {
                statedivisionId = statedivisionList.get(statedivisionSpinner.getSelectedItemPosition()).getString("statedivisionId");
            } else {
                noDataFlg = true;
            }

            if (noDataFlg) {
                new AlertDialog.Builder(this)
                        .setTitle("")
                        .setMessage("Need to download information")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                             Intent intent=new Intent(getApplication(),CustomerVisitActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch(NullPointerException e){
            e.printStackTrace();
            Utils.backToLogin(this);
        }
    }

    private void registerIDs() {
        customerNameEditText = (EditText) findViewById(R.id.customerName);
        contactPersonEditText = (EditText) findViewById(R.id.contactPerson);
        phoneNumberEditText = (EditText) findViewById(R.id.phoneNumber);
        addressEditText = (EditText) findViewById(R.id.address);
        townshipSpinner = (Spinner) findViewById(R.id.township);
        //customerCategorySpinner = (Spinner) findViewById(R.id.customerCategoryList);
        districtSpinner = (Spinner) findViewById(R.id.districtlist);
        statedivisionSpinner = (Spinner) findViewById(R.id.statedivisionlist);
        shoptypeSpinner = (Spinner) findViewById(R.id.shoptypespn);
        customerLocationTxt = (TextView) findViewById(R.id.customer_location);


        cancelImg = (ImageView) findViewById(R.id.cancel_img);
        resetImg = (ImageView) findViewById(R.id.reset_img);
        addImg = (ImageView) findViewById(R.id.add_img);
        nextImg = (ImageView) findViewById(R.id.next_img);
    }

    private void catchEvents() {
        cancelImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                AddNewCustomerActivity.this.onBackPressed();
            }
        });

        townshipList = getTownshipList();

        String[] townshipNames = new String[townshipList.size()];
        for (int i = 0; i < townshipNames.length; i++) {

            try {

                townshipNames[i] = townshipList.get(i).getString("townshipName");

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

        ArrayAdapter<String> townshipNamesArrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, townshipNames);
        townshipNamesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        townshipSpinner.setAdapter(townshipNamesArrayAdapter);

//        customerCategoryList = getCustomerCategoryList();
//        String[] customerCategoryNames = new String[customerCategoryList.size()];
//        for (int i = 0; i < customerCategoryNames.length; i++) {
//
//            try {
//
//                customerCategoryNames[i] = customerCategoryList.get(i).getString("name");
//            } catch (JSONException e) {
//
//                e.printStackTrace();
//            }
//        }
//        ArrayAdapter<String> customerCategoryNamesArrayAdapter =
//                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, customerCategoryNames);
//        customerCategoryNamesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        customerCategorySpinner.setAdapter(customerCategoryNamesArrayAdapter);


//        zoneList = getZoneList();
//        String[] zones = new String[zoneList.size()];
//        for (int i = 0; i < zones.length; i++) {
//
//            try {
//
//                zones[i] = zoneList.get(i).getString("zoneName");
//            } catch (JSONException e) {
//
//                e.printStackTrace();
//            }
//        }
//        ArrayAdapter<String> zonesArrayAdapter =
//                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, zones);
//        zonesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        zoneSpinner.setAdapter(zonesArrayAdapter);


        districtList = getDistrictList();
        String[] districts = new String[districtList.size()];
        for (int i = 0; i < districts.length; i++) {

            try {

                districts[i] = districtList.get(i).getString("districtName");
            } catch (JSONException e) {

                e.printStackTrace();
            }
        }
        ArrayAdapter<String> districtAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, districts);
        districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSpinner.setAdapter(districtAdapter);


        statedivisionList = getStatedivisionList();
        String[] statedivisions = new String[statedivisionList.size()];
        for (int i = 0; i < statedivisions.length; i++) {

            try {

                statedivisions[i] = statedivisionList.get(i).getString("statedivisionName");
            } catch (JSONException e) {

                e.printStackTrace();
            }
        }
        ArrayAdapter<String> statedivisionAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statedivisions);
        statedivisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statedivisionSpinner.setAdapter(statedivisionAdapter);


        shoptypeList = getShoptype();
        String[] shoptypes = new String[shoptypeList.size()];
        for (int i = 0; i < shoptypes.length; i++) {


            try {
                shoptypes[i] = shoptypeList.get(i).getString("shoptypeName");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        ArrayAdapter<String> shoptypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, shoptypes);
        shoptypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shoptypeSpinner.setAdapter(shoptypeAdapter);


        resetImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        addImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.askConfirmationDialog("Save", "Do you want to add?", "", AddNewCustomerActivity.this);
            }
        });

        nextImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewCustomerActivity.this
                        , AddNewCustomerLocationActivity.class);
                /*intent.putExtra(AddNewCustomerLocationActivity.USER_INFO_KEY
                        , userInfo.toString());*/
                intent.putExtra("customerName", customerNameEditText.getText().toString());
                intent.putExtra("phoneNumber", phoneNumberEditText.getText().toString());
                intent.putExtra("customerAddress", addressEditText.getText().toString());
                intent.putExtra("contactPerson", contactPersonEditText.getText().toString());
                intent.putExtra("zonePosition", "");
                intent.putExtra("townshipPosition", String.valueOf(townshipSpinner.getSelectedItemPosition()));
                //intent.putExtra("customerCategoryPosition", String.valueOf(customerCategorySpinner.getSelectedItemPosition()));
                intent.putExtra("customerLat", customerLat);
                intent.putExtra("customerLng", customerLng);
                startActivity(intent);
                finish();
            }
        });

        if (!(customerLat == null)) {
            customerLocationTxt.setText(customerLat + "," + customerLng);
        }
        if (!(putCustomerName == null)) {
            customerNameEditText.setText(putCustomerName);
        }
        if (!(putcustomerPhone == null)) {
            phoneNumberEditText.setText(putcustomerPhone);
        }
        if (!(putcustomerAddress == null)) {
            addressEditText.setText(putcustomerAddress);
        }
        if (!(putcontactPerson == null)) {
            contactPersonEditText.setText(putcontactPerson);
        }
        /*
        if (!(putzonePosition == null)) {
            zoneSpinner.setSelection(Integer.parseInt(putzonePosition));
        }
        if (!(putcustomerCategoryPosition == null)) {
            customerCategorySpinner.setSelection(Integer.parseInt(putcustomerCategoryPosition));
        }
        */

        if (!(puttownshipPosition == null)) {
            townshipSpinner.setSelection(Integer.parseInt(puttownshipPosition));
        }
    }

    private ArrayList<JSONObject> getTownshipList() {

        ArrayList<JSONObject> townshipList = new ArrayList<JSONObject>();

        Cursor cursor = database.rawQuery("SELECT * FROM TOWNSHIP", null);
        while (cursor.moveToNext()) {

            JSONObject townshipJsonObject = new JSONObject();
            try {

                townshipJsonObject.put("townshipId"
                        , cursor.getString(cursor.getColumnIndex("TOWNSHIP_ID")));
                townshipJsonObject.put("townshipName"
                        , cursor.getString(cursor.getColumnIndex("TOWNSHIP_NAME")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            townshipList.add(townshipJsonObject);
        }

        return townshipList;
    }

    private ArrayList<JSONObject> getCustomerCategoryList() {

        ArrayList<JSONObject> customerCategoryList = new ArrayList<JSONObject>();

        Cursor cursor = database.rawQuery("SELECT * FROM CUSTOMER_CATEGORY", null);
        while (cursor.moveToNext()) {

            JSONObject customerCategoryJsonObject = new JSONObject();
            try {
                customerCategoryJsonObject.put("id"
                        , cursor.getString(cursor.getColumnIndex("CUSTOMER_CATEGORY_ID")));
                customerCategoryJsonObject.put("name"
                        , cursor.getString(cursor.getColumnIndex("CUSTOMER_CATEGORY_NAME")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            customerCategoryList.add(customerCategoryJsonObject);
        }

        return customerCategoryList;
    }

//    private ArrayList<JSONObject> getZoneList() {
//
//        ArrayList<JSONObject> zoneList = new ArrayList<JSONObject>();
//
//        Cursor cursor = database.rawQuery("SELECT * FROM ZONE", null);
//        while (cursor.moveToNext()) {
//
//            JSONObject zoneJsonObject = new JSONObject();
//            try {
//                zoneJsonObject.put("zoneCode"
//                        , cursor.getString(cursor.getColumnIndex("ZONE_CODE")));
//                zoneJsonObject.put("zoneName"
//                        , cursor.getString(cursor.getColumnIndex("ZONE_NAME")));
//            } catch (JSONException e) {
//
//                e.printStackTrace();
//            }
//            zoneList.add(zoneJsonObject);
//        }
//
//        return zoneList;
//    }

    private ArrayList<JSONObject> getDistrictList() {

        ArrayList<JSONObject> districtList = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM DISTRICT", null);

        while (cursor.moveToNext()) {

            JSONObject districtObject = new JSONObject();

            try {
                districtObject.put("districtId", cursor.getString(cursor.getColumnIndex("ID")));
                districtObject.put("districtName", cursor.getString(cursor.getColumnIndex("NAME")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            districtList.add(districtObject);


        }
        return districtList;

    }

    private ArrayList<JSONObject> getStatedivisionList() {

        ArrayList<JSONObject> statedivisionList = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM STATE_DIVISION", null);

        while (cursor.moveToNext()) {

            JSONObject statedivisionObject = new JSONObject();

            try {
                statedivisionObject.put("statedivisionId", cursor.getString(cursor.getColumnIndex("ID")));
                statedivisionObject.put("statedivisionName", cursor.getString(cursor.getColumnIndex("NAME")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            statedivisionList.add(statedivisionObject);


        }
        return statedivisionList;

    }

    private ArrayList<JSONObject> getShoptype() {

        ArrayList<JSONObject> shoptypeList = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM SHOP_TYPE", null);

        while (cursor.moveToNext()) {

            JSONObject shoptypeObject = new JSONObject();

            try {
                shoptypeObject.put("shoptypeId", cursor.getString(cursor.getColumnIndex("ID")));
                shoptypeObject.put("shoptypeName", cursor.getString(cursor.getColumnIndex("SHOP_TYPE_NAME")));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            shoptypeList.add(shoptypeObject);


        }

        return shoptypeList;


    }

    private void reset() {
        putCustomerName = null;
        putcustomerPhone = null;
        putcustomerAddress = null;
        putcontactPerson = null;
        putzonePosition = null;
        putcustomerCategoryPosition = null;
        puttownshipPosition = null;
        customerLat = null;
        customerLng = null;
        customerNameEditText.setText("");
        customerNameEditText.setError(null);
        contactPersonEditText.setText("");
        contactPersonEditText.setError(null);
        phoneNumberEditText.setText("");
        phoneNumberEditText.setError(null);
        addressEditText.setText("");
        addressEditText.setError(null);
        customerLocationTxt.setText("");
        customerLocationTxt.setError(null);
//        if (customerCategoryList.size() > 0) {
//            customerCategorySpinner.setSelection(0);
//        }
        customerNameEditText.requestFocus();
    }

    @Override
    public void onBackPressed() {
        Utils.backToCustomerVisit(this);
    }

    @Override
    public void onActionClick(String type) {
        boolean isErrorFlag = false;
        if (customerNameEditText.getText().length() == 0) {

            customerNameEditText.setError("Customer name is required.");
            isErrorFlag = true;
        }

        if (contactPersonEditText.getText().length() == 0) {

            contactPersonEditText.setError("Contact person is required.");
            isErrorFlag = true;
        }

        if (phoneNumberEditText.getText().length() == 0) {

            phoneNumberEditText.setError("Phone number is required.");
            isErrorFlag = true;
        }

        if (addressEditText.getText().length() == 0) {

            addressEditText.setError("Address is required.");
            isErrorFlag = true;
        }

        if (customerLocationTxt.getText().length() == 0) {
            customerLocationTxt.setError("Custoemr Location is required.");
            isErrorFlag = true;
        }

        String currentTime = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date());

        customerName = customerNameEditText.getText().toString();
        contactPerson = contactPersonEditText.getText().toString();
        phoneNo = phoneNumberEditText.getText().toString();
        address = addressEditText.getText().toString();
        String[] location = customerLocationTxt.getText().toString().split(",");

        Log.i("CS", Constant.SALEMAN_ID);


        //customerId = Preferences.getNextNewCustomerId(AddNewCustomerActivity.this, userId); // TODO: 2/3/17 customerIdAutoIncrement

        if (isErrorFlag) {

            return;
        }

        try {
            customerId = userId + Utils.getCurrentDate(false) + new DecimalFormat("00").format(LoginActivity.mySharedPreference.getInt(Constant.ADDNEWCUSTOMERCOUNT, 0) + 1.0);

            LoginActivity.myEditor.putInt(Constant.ADDNEWCUSTOMERCOUNT, LoginActivity.mySharedPreference.getInt(Constant.ADDNEWCUSTOMERCOUNT, 0) + 1);
            LoginActivity.myEditor.commit();

            ContentValues contentValues = new ContentValues();
            int custId = 0;

            String tempCusId = LoginActivity.mySharedPreference.getInt(Constant.TABLET_KEY, 0) + "" + (LoginActivity.mySharedPreference.getInt(Constant.MAX_KEY, 0) + 1);

            if (tempCusId != null && !tempCusId.equals("")) {
                custId = Integer.parseInt(tempCusId);
            }

            //custId = custId + getNumberOfNewCustomer() + 1;

            contentValues.put("id", custId);
            contentValues.put("township_number", townshipId);
            Log.i("TownshipId", townshipId);
            contentValues.put("district_id", districtId);
            contentValues.put("state_division_id", statedivisionId);
            contentValues.put("shop_type_id", shoptypeId);
            contentValues.put("CUSTOMER_NAME", customerName);
            contentValues.put("CUSTOMER_ID", customerId);
            contentValues.put("contact_person", contactPerson);
            contentValues.put("PH", phoneNo);
            contentValues.put("ADDRESS", address);
            contentValues.put("LATITUDE", location[0]);
            contentValues.put("LONGITUDE", location[1]);
            contentValues.put("flag", 1);

            database.insert("CUSTOMER", null, contentValues);
            LoginActivity.myEditor.putInt(Constant.MAX_KEY, LoginActivity.mySharedPreference.getInt(Constant.MAX_KEY, 0) + 1);
            LoginActivity.myEditor.commit();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Utils.backToLogin(this);
        }
        reset();
        Utils.commonDialog("Customer has been successfully created", AddNewCustomerActivity.this);

        //finish();
    }

    private int getNumberOfNewCustomer() {
        Cursor cursor = database.rawQuery("SELECT COUNT(*) AS COUNT FROM CUSTOMER WHERE flag = 1", null);
        int count = 0;
        if (cursor.moveToNext()) {
            count = cursor.getInt(cursor.getColumnIndex("COUNT"));
        }

        if(count > 0) {
            return count;
        } else {
            return count;
        }
    }
}
