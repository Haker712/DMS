package com.aceplus.samparoo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aceplus.samparoo.customer.CustomerActivity;
import com.aceplus.samparoo.customer.CustomerLoactionActivity;
import com.aceplus.samparoo.customer.SaleActivity;
import com.aceplus.samparoo.marketing.MainFragmentActivity;
import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.model.CustomerLocation;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by haker on 2/4/17.
 */
public class MarketingActivity extends AppCompatActivity {
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
    Button posmButton, okButton;
    ImageView cancelImg;
    SQLiteDatabase database;

    ArrayList<Customer> customers;
    ArrayList<Customer> customerListForArrayAdapter;
    CustomerListArrayAdapter customerListArrayAdapter;

    Customer customer;
    ArrayList<String> customerIdArrayList = new ArrayList<String>();
    ArrayList<CustomerLocation> visitRecordArrayList = new ArrayList<>();
//    SimpleDateFormat fmtForTodayStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private double latitude = 0.0;
    private double longitude = 0.0;
    private int visitRecord = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        // Hide keyboard on startup.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        database = new Database(this).getDataBase();

        ButterKnife.inject(this);

        registerIDs();

        customerDatas();

        catchEvents();
    }

    @OnClick(R.id.cancel_img)
    void back() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Utils.backToHome(this);
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
        saleButton.setText("OK");
        saleOrderButton = (Button) findViewById(R.id.sale_order);
        saleOrderButton.setVisibility(View.GONE);
        unsellReasonButton = (Button) findViewById(R.id.unsell_reason);
        unsellReasonButton.setVisibility(View.GONE);
        saleReturnButton = (Button) findViewById(R.id.sale_return);
        saleReturnButton.setVisibility(View.GONE);
        posmButton = (Button) findViewById(R.id.btn_posm);
        posmButton.setVisibility(View.GONE);
        okButton = (Button) findViewById(R.id.ok);
        okButton.setVisibility(View.GONE);
        visitRecordButton = (Button) findViewById(R.id.visit_record);
        visitRecordButton.setVisibility(View.GONE);
    }

    private void customerDatas() {
        // Initial setup customers list view
        customers = new ArrayList<Customer>();
        customerListForArrayAdapter = new ArrayList<Customer>();
        Cursor cursor = database.rawQuery("SELECT * FROM CUSTOMER", null);
        while (cursor.moveToNext()) {
            Customer customer = new Customer(
                    cursor.getString(cursor.getColumnIndex("CUSTOMER_ID"))
                    , cursor.getString(cursor.getColumnIndex("CUSTOMER_NAME"))
                    , cursor.getString(cursor.getColumnIndex("CUSTOMER_TYPE_ID"))
                    , cursor.getString(cursor.getColumnIndex("CUSTOMER_TYPE_NAME"))
                    , cursor.getString(cursor.getColumnIndex("ADDRESS"))
                    , cursor.getString(cursor.getColumnIndex("PH"))
                    , cursor.getString(cursor.getColumnIndex("TOWNSHIP"))
                    , cursor.getString(cursor.getColumnIndex("CREDIT_TERM"))
                    , cursor.getDouble(cursor.getColumnIndex("CREDIT_LIMIT"))
                    , cursor.getDouble(cursor.getColumnIndex("CREDIT_AMT"))
                    , cursor.getDouble(cursor.getColumnIndex("DUE_AMT"))
                    , cursor.getDouble(cursor.getColumnIndex("PREPAID_AMT"))
                    , cursor.getString(cursor.getColumnIndex("PAYMENT_TYPE"))
                    , cursor.getString(cursor.getColumnIndex("IS_IN_ROUTE"))
                    , cursor.getDouble(cursor.getColumnIndex("LATITUDE"))
                    , cursor.getDouble(cursor.getColumnIndex("LONGITUDE"))
                    , cursor.getInt(cursor.getColumnIndex("VISIT_RECORD")));
            customer.setShopTypeId(cursor.getInt(cursor.getColumnIndex("shop_type_id")));
            customer.setId(cursor.getInt(cursor.getColumnIndex("id")));
            customer.setFlag(cursor.getInt(cursor.getColumnIndex("flag")));
            customers.add(customer);
            customerListForArrayAdapter.add(customer);
        }

        customerListArrayAdapter = new CustomerListArrayAdapter(this);
        customersListView.setAdapter(customerListArrayAdapter);

        customersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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

        phoneTextView.setOnClickListener(new View.OnClickListener() {
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

        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketingActivity.this, MainFragmentActivity.class);
                intent.putExtra(MainFragmentActivity.CUSTOMER_INFO_KEY, customer);
                startActivity(intent);
                finish();
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
                    .setIcon(android.R.drawable.ic_dialog_alert)
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
