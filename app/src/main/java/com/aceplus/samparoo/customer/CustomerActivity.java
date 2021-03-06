package com.aceplus.samparoo.customer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.model.CustomerFeedback;
import com.aceplus.samparoo.model.CustomerLocation;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.prefs.Preferences;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomerActivity extends AppCompatActivity {

    // For pre order
    public static final String IS_PRE_ORDER = "is-pre-order";
    public static final String USER_INFO_KEY = "user-info-key";

    private boolean isPreOrder;
    JSONObject salemanInfo;

    EditText searchCustomersEditText;
    ListView customersListView;

    TextView customerNameTextView;
    TextView phoneTextView;
    TextView addressTextView;
    TextView townshipTextView;
    TextView creditTermsTextView;
    TextView creditLimitTextView;
    TextView creditAmountTextView;
    TextView dueAmountTextView;
    TextView prepaidAmountTextView;
    TextView paymentTypeTextView;

    Button saleButton;
    Button saleOrderButton;
    Button unsellReasonButton;
    Button saleReturnButton;
    Button visitRecordButton;
    Button posmButton;
    ImageView cancelImg;
    SQLiteDatabase database;

    ArrayList<Customer> customers;
    ArrayList<Customer> customerListForArrayAdapter;
    CustomerListArrayAdapter customerListArrayAdapter;

    Customer customer;
    ArrayList<String> customerIdArrayList = new ArrayList<String>();
    ArrayList<CustomerLocation> visitRecordArrayList = new ArrayList<>();

    View buttongp;
    String check;
    Button btnOk;
//    SimpleDateFormat fmtForTodayStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private double latitude = 0.0;
    private double longitude = 0.0;
    private int visitRecord = 0;

    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        // Hide keyboard on startup.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        database = new Database(this).getDataBase();

        database.beginTransaction();
        database.execSQL("delete from CUSTOMER where CUSTOMER_ID = '" + null + "'");
        database.setTransactionSuccessful();
        database.endTransaction();

        ButterKnife.inject(this);


        registerIDs();

        customerDatas();

        catchEvents();

        Intent intent = this.getIntent();


        if (intent != null) {

            check = intent.getExtras().getString("SaleExchange");

            if (check.equalsIgnoreCase("yes")) {

                buttongp.setVisibility(View.GONE);


            } else {

                btnOk.setVisibility(View.GONE);

            }
        }

    }

    @OnClick(R.id.cancel_img)
    void back() {
        onBackPressed();
    }

    @OnClick(R.id.ok)
    void Ok() {

        Intent intent = new Intent(this, SaleReturnActivity.class);
        intent.putExtra("SaleExchange", "yes");
        intent.putExtra(SaleActivity.CUSTOMER_INFO_KEY, customer);
        startActivity(intent);

    }


    @Override
    public void onBackPressed() {
        Utils.backToCustomerVisit(this);
    }

    private void registerIDs() {

        searchCustomersEditText = (EditText) findViewById(R.id.search);
        customersListView = (ListView) findViewById(R.id.customers);

        customerNameTextView = (TextView) findViewById(R.id.customerName);
        phoneTextView = (TextView) findViewById(R.id.phone);
        phoneTextView.setPaintFlags(phoneTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        addressTextView = (TextView) findViewById(R.id.address);
        addressTextView.setPaintFlags(addressTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        townshipTextView = (TextView) findViewById(R.id.township);
        creditTermsTextView = (TextView) findViewById(R.id.creditTerms);
        creditLimitTextView = (TextView) findViewById(R.id.creditLimit);
        creditAmountTextView = (TextView) findViewById(R.id.creditAmount);
        dueAmountTextView = (TextView) findViewById(R.id.dueAmount);
        prepaidAmountTextView = (TextView) findViewById(R.id.prepaidAmount);
        paymentTypeTextView = (TextView) findViewById(R.id.paymentType);

        cancelImg = (ImageView) findViewById(R.id.cancel_img);

        saleButton = (Button) findViewById(R.id.sale);
        saleOrderButton = (Button) findViewById(R.id.sale_order);
        unsellReasonButton = (Button) findViewById(R.id.unsell_reason);
        saleReturnButton = (Button) findViewById(R.id.sale_return);
        visitRecordButton = (Button) findViewById(R.id.visit_record);

        posmButton = (Button) findViewById(R.id.btn_posm);
        buttongp = findViewById(R.id.customer_buttonGp);
        btnOk = (Button) findViewById(R.id.ok);
    }

    private void customerDatas() {
        // Initial setup customers list view
        customers = new ArrayList<Customer>();
        customerListForArrayAdapter = new ArrayList<Customer>();
        Cursor cursor = database.rawQuery("SELECT * FROM CUSTOMER", null);
        while (cursor.moveToNext()) {
            String township_name = "";
            String township_id = cursor.getString(cursor.getColumnIndex("township_number"));
            Cursor cursorForTownship = database.rawQuery("select * from TOWNSHIP where TOWNSHIP_ID = '"+township_id+"'", null);
            while (cursorForTownship.moveToNext()) {
                township_name = cursorForTownship.getString(cursorForTownship.getColumnIndex("TOWNSHIP_NAME"));
            }

            Customer customer = new Customer(
                    cursor.getString(cursor.getColumnIndex("CUSTOMER_ID"))
                    , cursor.getString(cursor.getColumnIndex("CUSTOMER_NAME"))
                    , cursor.getString(cursor.getColumnIndex("CUSTOMER_TYPE_ID"))
                    , cursor.getString(cursor.getColumnIndex("CUSTOMER_TYPE_NAME"))
                    , cursor.getString(cursor.getColumnIndex("ADDRESS"))
                    , cursor.getString(cursor.getColumnIndex("PH"))
                    , township_name
                    , cursor.getString(cursor.getColumnIndex("CREDIT_TERM"))
                    , cursor.getDouble(cursor.getColumnIndex("CREDIT_LIMIT"))
                    , cursor.getDouble(cursor.getColumnIndex("CREDIT_AMT"))
                    , cursor.getDouble(cursor.getColumnIndex("DUE_AMT"))
                    , cursor.getDouble(cursor.getColumnIndex("PREPAID_AMT"))
                    , cursor.getString(cursor.getColumnIndex("PAYMENT_TYPE"))/*
                    , cursor.getString(cursor.getColumnIndex("IS_IN_ROUTE"))*/
                    , "false"
                    , cursor.getDouble(cursor.getColumnIndex("LATITUDE"))
                    , cursor.getDouble(cursor.getColumnIndex("LONGITUDE"))
                    , cursor.getInt(cursor.getColumnIndex("VISIT_RECORD")));
            customer.setShopTypeId(cursor.getInt(cursor.getColumnIndex("shop_type_id")));
            customers.add(customer);
            customerListForArrayAdapter.add(customer);

            Log.i("customerSize>>>", customerListForArrayAdapter.size() + "");
        }

        customerListArrayAdapter = new CustomerListArrayAdapter(this);
        customersListView.setAdapter(customerListArrayAdapter);

        customersListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                customer = customers.get(position);

                customerNameTextView.setText(customer.getCustomerName());
                phoneTextView.setText(customer.getPhone());
                addressTextView.setText(customer.getAddress());
                townshipTextView.setText(customer.getTownship());
                creditTermsTextView.setText(customer.getCreditTerms());
                creditLimitTextView.setText(customer.getCreditLimit() + "");
                creditAmountTextView.setText(customer.getCreditAmt() + "");
                dueAmountTextView.setText(customer.getDueAmt() + "");
                prepaidAmountTextView.setText(customer.getPrepaidAmt() + "");
                paymentTypeTextView.setText(customer.getPaymentType());
                latitude = customer.getLatitude();
                longitude = customer.getLongitude();
                Log.i("lat & lon", latitude + " & " + longitude);
                visitRecord = customer.getVisitRecord();
            }
        });

        searchCustomersEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence characterSequence, int arg1, int arg2, int arg3) {

                customerListForArrayAdapter.clear();

                for (Customer customer : customers) {

                    if (customer.getCustomerName().toLowerCase()
                            .contains(characterSequence.toString().toLowerCase())) {

                        customerListForArrayAdapter.add(customer);
                        customerListArrayAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
        //GPS DATA
        addressTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (didCustomerSelected()) {
                    /*CustomerLoactionActivity.latitude = latitude;
                    CustomerLoactionActivity.longitude = longitude;*/
                    CustomerLoactionActivity.latitude = 16.8487745;
                    CustomerLoactionActivity.longitude = 96.1268365;
                    CustomerLoactionActivity.customerName = customerNameTextView.getText().toString();
                    CustomerLoactionActivity.address = addressTextView.getText().toString();
                    CustomerLoactionActivity.visitRecord = visitRecord;
                    startActivity(new Intent(CustomerActivity.this, CustomerLoactionActivity.class));
                }
            }
        });
        phoneTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (didCustomerSelected()) {
                    String phoneNo = phoneTextView.getText().toString();
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNo)));
                }
            }
        });
    }

    private void catchEvents() {

        saleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (didCustomerSelected()) {
                    Intent intent = new Intent(CustomerActivity.this, SaleActivity.class);
                    intent.putExtra(SaleActivity.CUSTOMER_INFO_KEY, customer);
                    intent.putExtra("SaleExchange","no");
                    startActivity(intent);
                    finish();
                }
            }
        });

        saleOrderButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (didCustomerSelected()) {
                    Intent intent = new Intent(CustomerActivity.this, SaleOrderActivity.class);
                    intent.putExtra(SaleOrderActivity.IS_PRE_ORDER, true);
                    intent.putExtra(SaleActivity.CUSTOMER_INFO_KEY, customer);
                    startActivity(intent);
                    finish();
                }
            }
        });

        unsellReasonButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {


                int count = 0;
                Cursor cursor = database.rawQuery(
                        "SELECT COUNT(*) AS COUNT FROM DID_CUSTOMER_FEEDBACK", null);
                if (cursor.moveToNext()) {

                    count = cursor.getInt(cursor.getColumnIndex("COUNT"));
                }

                if (count > 0) {

                    new AlertDialog.Builder(CustomerActivity.this)
                            .setTitle("General sale")
                            .setMessage("Your uploaded customer feedbacks is not clear yet.\n"
                                    + "Before sale, clear all data.")
                            .setPositiveButton("OK", null)
                            .show();

                    return;
                }


                if (didCustomerSelected()) {
                    boolean customerNoDid = true;
                    Cursor cur = database.rawQuery("SELECT * FROM DID_CUSTOMER_FEEDBACK", null);
                    customerIdArrayList.clear();
                    while (cur.moveToNext()) {
                        String customerId = cur.getString(cur.getColumnIndex("CUSTOMER_NO"));
                        customerIdArrayList.add(customerId);
                    }

                    for (int i = 0; i < customerIdArrayList.size(); i++) {
                        if (customer.getCustomerId().equals(customerIdArrayList.get(i))) {
                            customerNoDid = false;
                            Toast.makeText(CustomerActivity.this, "This customer have customer feedback report.Please check!", Toast.LENGTH_LONG).show();
                        }
                    }
                    if (customerNoDid == true) {
                        LayoutInflater layoutInflater = (LayoutInflater) CustomerActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View view = layoutInflater.inflate(R.layout.dialog_box_customer_feedback, null);

                        final Spinner descriptionsSpinner = (Spinner) view.findViewById(R.id.description);
                        final EditText remarkEditText = (EditText) view.findViewById(R.id.remark);

                        final ArrayList<CustomerFeedback> customerFeedbacks = new ArrayList<CustomerFeedback>();

                        cursor = database.rawQuery("SELECT * FROM CUSTOMER_FEEDBACK", null);
                        while (cursor.moveToNext()) {

                            customerFeedbacks.add(new CustomerFeedback(
                                    cursor.getString(cursor.getColumnIndex("INV_NO"))
                                    , cursor.getString(cursor.getColumnIndex("INV_DATE"))
                                    , cursor.getString(cursor.getColumnIndex("SERIAL_NO"))
                                    , cursor.getString(cursor.getColumnIndex("DESCRIPTION"))));
                        }

                        final AlertDialog alertDialog = new AlertDialog.Builder(CustomerActivity.this)
                                .setView(view)
                                .setTitle("Feedback")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {


                                        String salemanId = "za";
                                        String deviceId = Utils.getDeviceId(CustomerActivity.this);
                                        String invoiceNumber = Utils.getInvoiceID(getApplicationContext(), Utils.MODE_CUSTOMER_FEEDBACK, salemanId, "YGN");
                                        String invoiceDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                                        String customerNumber = customer.getCustomerId();
                                        String locationNumber = "YGN";
                                        String feedbackNumber = customerFeedbacks.get(descriptionsSpinner.getSelectedItemPosition()).getInvoiceNumber();
                                        String feedbackDate = customerFeedbacks.get(descriptionsSpinner.getSelectedItemPosition()).getInvoiceDate();
                                        String serialNumber = customerFeedbacks.get(descriptionsSpinner.getSelectedItemPosition()).getSerialNumber();
                                        String description = customerFeedbacks.get(descriptionsSpinner.getSelectedItemPosition()).getDescription();
                                        String remark = remarkEditText.getText().toString();

                                        database.beginTransaction();
                                        database.execSQL("INSERT INTO DID_CUSTOMER_FEEDBACK VALUES (\""
                                                + salemanId + "\","
                                                + "\"" + deviceId + "\","
                                                + "\"" + invoiceNumber + "\","
                                                + "\"" + invoiceDate + "\","
                                                + "\"" + customerNumber + "\","
                                                + "\"" + locationNumber + "\","
                                                + "\"" + feedbackNumber + "\","
                                                + "\"" + feedbackDate + "\","
                                                + "\"" + serialNumber + "\","
                                                + "\"" + description + "\","
                                                + "\"" + remark + "\")");
                                        database.setTransactionSuccessful();
                                        database.endTransaction();

                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .create();
                        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

                            @Override
                            public void onShow(DialogInterface arg0) {

                                ArrayList<String> descriptions = new ArrayList<String>();
                                for (CustomerFeedback customerFeedback : customerFeedbacks) {

                                    descriptions.add(customerFeedback.getDescription());
                                }
                                ArrayAdapter<String> descriptionsArrayAdapter = new ArrayAdapter<String>(CustomerActivity.this, android.R.layout.simple_spinner_item, descriptions);
                                descriptionsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                descriptionsSpinner.setAdapter(descriptionsArrayAdapter);
                            }
                        });
                        alertDialog.show();
                    }
                }
            }
        });

        saleReturnButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (didCustomerSelected()) {
                    Intent intent = new Intent(CustomerActivity.this, SaleReturnActivity.class);
                    intent.putExtra(SaleActivity.CUSTOMER_INFO_KEY, customer);
                    intent.putExtra("SaleExchange","no");
                    startActivity(intent);
                    finish();
                }
            }
        });

        posmButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (didCustomerSelected()) {
                    Intent intent = new Intent(CustomerActivity.this, PosmActivity.class);
                    intent.putExtra(PosmActivity.CUSTOMER_INFO_KEY, customer);
                    startActivity(intent);
                }
            }
        });
    }

    /*private void catchEvents() {

        visitRecordButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (didCustomerSelected()) {
                    boolean haveNotVisit = true;
                    String currentTime = String.valueOf(new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date()));

                    Cursor cur = database.rawQuery("SELECT * FROM CUSTOMER", null);
                    visitRecordArrayList.clear();
                    while (cur.moveToNext()) {
                        String customerId = cur.getString(cur.getColumnIndex("CUSTOMER_ID"));
                        int visitRecrod = cur.getInt(cur.getColumnIndex("VISIT_RECORD"));

                        CustomerLocation customerLocation = new CustomerLocation();
                        customerLocation.setCustomerName(customerId);
                        customerLocation.setVisitRecord(visitRecrod);
                        visitRecordArrayList.add(customerLocation);
                    }

                    for (CustomerLocation customerLocation : visitRecordArrayList) {
                        if (customer.getCustomerId().equals(customerLocation.getCustomerName()) && customerLocation.getVisitRecord() == 1) {
                            haveNotVisit = false;
                            Toast.makeText(CustomerActivity.this, "This customer have customer visit record.Please check!", Toast.LENGTH_LONG).show();
                        }
                    }
                    if (haveNotVisit == true) {
                        database.beginTransaction();
                        String arg[] = {customer.getCustomerId()};
                        ContentValues cv = new ContentValues();
                        cv.put("VISIT_RECORD", "1");
                        database.update("CUSTOMER", cv, "CUSTOMER_ID LIKE ?", arg);
                        ContentValues visitReprotCv = new ContentValues();
                        visitReprotCv.put("customer_name", customer.getCustomerName());
                        visitReprotCv.put("address", customer.getAddress());
                        visitReprotCv.put("current_time", currentTime);
                        database.insert("customer_visit_recrod_report", null, visitReprotCv);
                        database.setTransactionSuccessful();
                        database.endTransaction();
                        Toast.makeText(CustomerActivity.this, "Successfully visit record.", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        });

        saleReturnButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (didCustomerSelected()) {
                    Intent intent = new Intent(CustomerActivity.this, SaleReturnActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        generalSaleButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (didCustomerSelected()) {


                    Intent intent = new Intent(CustomerActivity.this, GeneralSaleActivity.class);
                    intent.putExtra(GeneralSaleActivity.USER_INFO_KEY, salemanInfo.toString());
                    intent.putExtra(GeneralSaleActivity.CUSTOMER_INFO_KEY, customer);
                    startActivity(intent);
                    // We finish this activity because we need to select no customer when visit by back button.
                    finish();
                }
            }
        });

        unsellReasonButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (didCustomerSelected()) {
                    boolean customerNoDid = true;
                    Cursor cur = database.rawQuery("SELECT * FROM DID_CUSTOMER_FEEDBACK", null);
                    customerIdArrayList.clear();
                    while (cur.moveToNext()) {
                        String customerId = cur.getString(cur.getColumnIndex("CUSTOMER_NO"));
                        customerIdArrayList.add(customerId);
                    }

                    for (int i = 0; i < customerIdArrayList.size(); i++) {
                        if (customer.getCustomerId().equals(customerIdArrayList.get(i))) {
                            customerNoDid = false;
                            Toast.makeText(CustomerActivity.this, "This customer have customer feedback report.Please check!", Toast.LENGTH_LONG).show();
                        }
                    }
                    if (customerNoDid == true) {
                        LayoutInflater layoutInflater = (LayoutInflater) CustomerActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View view = layoutInflater.inflate(R.layout.dialog_box_customer_feedback, null);

                        final Spinner descriptionsSpinner = (Spinner) view.findViewById(R.id.description);
                        final EditText remarkEditText = (EditText) view.findViewById(R.id.remark);

                        final ArrayList<CustomerFeedback> customerFeedbacks = new ArrayList<CustomerFeedback>();

                        Cursor cursor = database.rawQuery("SELECT * FROM CUSTOMER_FEEDBACK", null);
                        while (cursor.moveToNext()) {

                            customerFeedbacks.add(new CustomerFeedback(
                                    cursor.getString(cursor.getColumnIndex("INV_NO"))
                                    , cursor.getString(cursor.getColumnIndex("INV_DATE"))
                                    , cursor.getString(cursor.getColumnIndex("SERIAL_NO"))
                                    , cursor.getString(cursor.getColumnIndex("DESCRIPTION"))));
                        }

                        final AlertDialog alertDialog = new AlertDialog.Builder(CustomerActivity.this)
                                .setView(view)
                                .setTitle("Feedback")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {

                                        try {

                                            String salemanId = salemanInfo.getString("userId");
                                            String deviceId = ((TelephonyManager) CustomerActivity.this.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                                            String invoiceNumber = Utils.getInvoiceID(getApplicationContext(), Utils.MODE_CUSTOMER_FEEDBACK, salemanInfo.getString("userId"), salemanInfo.getString("locationCode"));
                                            String invoiceDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                                            String customerNumber = customer.getCustomerId();
                                            String locationNumber = salemanInfo.getString("locationCode");
                                            String feedbackNumber = customerFeedbacks.get(descriptionsSpinner.getSelectedItemPosition()).getInvoiceNumber();
                                            String feedbackDate = customerFeedbacks.get(descriptionsSpinner.getSelectedItemPosition()).getInvoiceDate();
                                            String serialNumber = customerFeedbacks.get(descriptionsSpinner.getSelectedItemPosition()).getSerialNumber();
                                            String description = customerFeedbacks.get(descriptionsSpinner.getSelectedItemPosition()).getDescription();
                                            String remark = remarkEditText.getText().toString();

                                            database.beginTransaction();
                                            database.execSQL("INSERT INTO DID_CUSTOMER_FEEDBACK VALUES (\""
                                                    + salemanId + "\","
                                                    + "\"" + deviceId + "\","
                                                    + "\"" + invoiceNumber + "\","
                                                    + "\"" + invoiceDate + "\","
                                                    + "\"" + customerNumber + "\","
                                                    + "\"" + locationNumber + "\","
                                                    + "\"" + feedbackNumber + "\","
                                                    + "\"" + feedbackDate + "\","
                                                    + "\"" + serialNumber + "\","
                                                    + "\"" + description + "\","
                                                    + "\"" + remark + "\")");
                                            database.setTransactionSuccessful();
                                            database.endTransaction();
                                        } catch (JSONException e) {

                                            e.printStackTrace();
                                        }
                                    }
                                })
                                .setNegativeButton("Cancel", null)
                                .create();
                        alertDialog.setOnShowListener(new OnShowListener() {

                            @Override
                            public void onShow(DialogInterface arg0) {

                                ArrayList<String> descriptions = new ArrayList<String>();
                                for (CustomerFeedback customerFeedback : customerFeedbacks) {

                                    descriptions.add(customerFeedback.getDescription());
                                }
                                ArrayAdapter<String> descriptionsArrayAdapter = new ArrayAdapter<String>(CustomerActivity.this, android.R.layout.simple_spinner_item, descriptions);
                                descriptionsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                descriptionsSpinner.setAdapter(descriptionsArrayAdapter);
                            }
                        });
                        alertDialog.show();
                    }
                }
            }
        });


    }*/

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private boolean didCustomerSelected() {

        if (customer == null) {

            new AlertDialog.Builder(this)
                    .setTitle("Customer is required")
                    .setMessage("You need to select customer.")
                    .setPositiveButton("OK", null)
                    .show();

            return false;
        }

        return true;
    }

    private class CustomerListArrayAdapter extends ArrayAdapter<Customer> {

        public final Activity context;

        public CustomerListArrayAdapter(Activity context) {

            super(context, R.layout.custom_simple_list_item_1, customerListForArrayAdapter);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Customer customer = customerListForArrayAdapter.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.custom_simple_list_item_1, null, true);

            String address = customer.getAddress();
            Log.i("address>>>", address);
            if (customer.getAddress().length() >= 10) {
                address = customer.getAddress().substring(0, 10);
            }
            if (customer.getAddress().length() > 10) {
                address += "...";
            }

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(customer.getCustomerName() + "(" + address + ")");
            return view;
        }
    }

}
