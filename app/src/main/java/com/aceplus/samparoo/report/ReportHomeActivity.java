package com.aceplus.samparoo.report;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class ReportHomeActivity extends FragmentActivity {

    public static final String USER_INFO_KEY = "user-info-key";

    JSONObject userInfo;

    ImageView cancelImg, printImg;
    Spinner reportsSpinner;

    SQLiteDatabase database;

    TextView reportTitle;

    String cus_name, product_name, shop_name;

    String cusName, address;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_home);

        printImg = (ImageView) findViewById(R.id.print_img);
        cancelImg = (ImageView) findViewById(R.id.cancel_img);
        reportsSpinner = (Spinner) findViewById(R.id.reports);

        database = new Database(this).getDataBase();

        reportTitle = (TextView) findViewById(R.id.reportTitle);

        reportTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                backupDB();

            }
        });


        cancelImg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                ReportHomeActivity.this.onBackPressed();

            }
        });

        final ArrayList<JSONObject> productBalanceReportsArrayList = new ArrayList<JSONObject>();
        final ArrayList<JSONObject> saleInvoiceReportsArrayList = new ArrayList<JSONObject>();
        final ArrayList<JSONObject> promotionReportsArrayList = new ArrayList<JSONObject>();
        final ArrayList<JSONObject> customerFeedbackReportsArrayList = new ArrayList<JSONObject>();
        final ArrayList<JSONObject> preOrderReportsArrayList = new ArrayList<JSONObject>();
        final ArrayList<JSONObject> saleReturnReportsArrayList = new ArrayList<>();
        final ArrayList<JSONObject> POSMReportArrayList = new ArrayList<>();
        final ArrayList<JSONObject> DeliveryReportArrayList = new ArrayList<>();
        final ArrayList<JSONObject> CustomerReportArrayList = new ArrayList<>();


        final String[] reportNames = {
                "Product Balance Report"
                , "Sale Invoice Report"
                , "Unsell Reason Report"
                , "Sale Return Report"
                , "Sale Exchange Report"
                , "POSM Report"
                , "Deliver Report"
                , "Pre-order Report"
                , "Sale Target & Actual Sale Report"
                , "Sale Target & Actual Sale Product Report"
                , "Display Program Report"
                , "Incentive Program Report"
                , "Size And Stock Report"
                , "Sale History Report"
                , "Sale Order History Report"
                , "Sale Visit History Report"
                , "End of Day Report"
        };
        ArrayAdapter<String> reportsSpinnerAdapter
                = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, reportNames);
        reportsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reportsSpinner.setAdapter(reportsSpinnerAdapter);
        reportsSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (position == 0) {


                    if (productBalanceReportsArrayList.size() == 0) {

                        for (JSONObject productBalanceReportJsonObject : getProductBalanceReports()) {

                            productBalanceReportsArrayList.add(productBalanceReportJsonObject);
                        }
                    }

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                    FragmentProductBalancesReport productBalancesReportFragment = new FragmentProductBalancesReport();
                    productBalancesReportFragment.productBalanceReportsArrayList = productBalanceReportsArrayList;
                    fragmentTransaction.replace(R.id.fragment_report, productBalancesReportFragment);

                    /*fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);*/
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } else if (position == 1) {

                    if (CustomerReportArrayList.size() == 0) {

                        for (JSONObject customerReportJsonObject : getCustomerReports()) {

                            CustomerReportArrayList.add(customerReportJsonObject);
                        }
                    }

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                    FragmentSaleInvoiceReport saleInvoiceFragment = new FragmentSaleInvoiceReport();
                    saleInvoiceFragment.customerReportsArrayList = CustomerReportArrayList;
                    fragmentTransaction.replace(R.id.fragment_report, saleInvoiceFragment);
                    /*fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.addToBackStack(null);*/
                    fragmentTransaction.commit();
                }

                else if (position == 2) {

                    if (customerFeedbackReportsArrayList.size() == 0) {

                        for (JSONObject customerFeedbackReportJsonObject : getCustomerFeedbackReports()) {

                            customerFeedbackReportsArrayList.add(customerFeedbackReportJsonObject);
                        }
                    }

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                    FragmentCustomerFeedbackReport customerFeedbackReportFragment = new FragmentCustomerFeedbackReport();
                    customerFeedbackReportFragment.customerFeedbackReportsArrayList = customerFeedbackReportsArrayList;
                    fragmentTransaction.replace(R.id.fragment_report, customerFeedbackReportFragment);
                    /*fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.addToBackStack(null);*/
                    fragmentTransaction.commit();
                }

                else if (position == 3) {
                    if (saleReturnReportsArrayList.size() == 0) {

                        for (JSONObject saleReturnReportJsonObject : getSaleReturnReports()) {

                            saleReturnReportsArrayList.add(saleReturnReportJsonObject);
                        }
                    }

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                    FragmentSaleReturnReport saleReturnReportFragment = new FragmentSaleReturnReport();
                    saleReturnReportFragment.saleReturnReportsArrayList = saleReturnReportsArrayList;
                    fragmentTransaction.replace(R.id.fragment_report, saleReturnReportFragment);
                   /* fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.addToBackStack(null);*/
                    fragmentTransaction.commit();


                }

                else if (position == 4) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                    FragmentSaleExchangeReport fragmentSaleExchangeReport = new FragmentSaleExchangeReport();
                    fragmentTransaction.replace(R.id.fragment_report, fragmentSaleExchangeReport);
                    //fragmentTransaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    //Toast.makeText(ReportHomeActivity.this, "Toast", Toast.LENGTH_SHORT).show();

                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }

                else if (position == 5) {

                    if (POSMReportArrayList.size() == 0) {

                        for (JSONObject POSMReportJSONObject : getPOSMReports()) {

                            POSMReportArrayList.add(POSMReportJSONObject);

                        }
                    }

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    FragmentPOSMReport fragmentPOSMReport = new FragmentPOSMReport();
                    fragmentPOSMReport.POSMReportArrayList = POSMReportArrayList;
                    fragmentTransaction.replace(R.id.fragment_report, fragmentPOSMReport);
                    fragmentTransaction.commit();


                } else if (position == 6) {

                    if (DeliveryReportArrayList.size() == 0) {

                        for (JSONObject DeliveryJSONObject : getDeliveryInvoiceReports()) {


                            DeliveryReportArrayList.add(DeliveryJSONObject);

                        }

                    }

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    FragmentDeliveryInvoiceReport fragmentDeliveryInvoiceReport = new FragmentDeliveryInvoiceReport();
                    fragmentDeliveryInvoiceReport.DeliveryReportArrayList = DeliveryReportArrayList;
                    fragmentTransaction.replace(R.id.fragment_report, fragmentDeliveryInvoiceReport);
                    fragmentTransaction.commit();
                }

                else if (position == 7) {

                    // Used lazy loading
                    if (preOrderReportsArrayList.size() == 0) {

                        // Need to add implicitly because preOrderReportsArrayList is final
                        for (JSONObject preOrderReportJsonObject : getPreOrderReports()) {

                            preOrderReportsArrayList.add(preOrderReportJsonObject);
                        }
                    }

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                    FragmentPreOrderReport preOrderReportFragment = new FragmentPreOrderReport();
                    preOrderReportFragment.preOrderReportsArrayList = preOrderReportsArrayList;
                    fragmentTransaction.replace(R.id.fragment_report, preOrderReportFragment);
                   /* fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.addToBackStack(null);*/
                    fragmentTransaction.commit();

                } else if (position == 8) {

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    FragmentSaleComparisonReport fragmentSaleComparisonReport = new FragmentSaleComparisonReport();
                    //fragmentDeliveryInvoiceReport.DeliveryReportArrayList = DeliveryReportArrayList;
                    fragmentTransaction.replace(R.id.fragment_report, fragmentSaleComparisonReport);
                    fragmentTransaction.commit();

                } else if (position == 9) {

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    FragmentProductComparisonReport fragmentProductComparisonReport = new FragmentProductComparisonReport();
                    //fragmentDeliveryInvoiceReport.DeliveryReportArrayList = DeliveryReportArrayList;
                    fragmentTransaction.replace(R.id.fragment_report, fragmentProductComparisonReport);
                    fragmentTransaction.commit();
                }

                else if (position == 10) {

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    FragmentDisplayProgram fragmentDisplayProgram = new FragmentDisplayProgram();
                    fragmentTransaction.replace(R.id.fragment_report, fragmentDisplayProgram);
                    fragmentTransaction.commit();
                }

                else if(position == 11) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    FragmentIncentiveProgramReport fragmentIncentiveProgram = new FragmentIncentiveProgramReport();
                    fragmentTransaction.replace(R.id.fragment_report, fragmentIncentiveProgram);
                    fragmentTransaction.commit();
                }

                else if(position == 12) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    FragmentSizeAndStockReport fragmentSizeAndStockReport = new FragmentSizeAndStockReport();
                    fragmentTransaction.replace(R.id.fragment_report, fragmentSizeAndStockReport);
                    fragmentTransaction.commit();
                } else if (position == 13) {

                    if (CustomerReportArrayList.size() == 0) {

                        for (JSONObject customerReportJsonObject : getCustomerReports()) {

                            CustomerReportArrayList.add(customerReportJsonObject);
                        }
                    }

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                    FragmentSaleInvoiceReport saleInvoiceFragment = new FragmentSaleInvoiceReport();
                    saleInvoiceFragment.customerReportsArrayList = CustomerReportArrayList;
                    saleInvoiceFragment.saleHistory = 1;
                    fragmentTransaction.replace(R.id.fragment_report, saleInvoiceFragment);
                    fragmentTransaction.commit();
                } else if (position == 14) {

                    if (CustomerReportArrayList.size() == 0) {

                        for (JSONObject customerReportJsonObject : getCustomerReports()) {

                            CustomerReportArrayList.add(customerReportJsonObject);
                        }
                    }

                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                    FragmentSaleOrderHistoryReport saleInvoiceFragment = new FragmentSaleOrderHistoryReport();
                    saleInvoiceFragment.customerReportsArrayList = CustomerReportArrayList;
                    saleInvoiceFragment.saleHistory = 1;
                    fragmentTransaction.replace(R.id.fragment_report, saleInvoiceFragment);
                    fragmentTransaction.commit();
                } else if(position == 15) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    FragmentSaleVisitReport fragmentSaleVisitReport = new FragmentSaleVisitReport();
                    fragmentTransaction.replace(R.id.fragment_report, fragmentSaleVisitReport);
                    fragmentTransaction.commit();
                } else if(position == 16){
                    printImg.setVisibility(View.VISIBLE);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    FragmentDailyReportForSaleMan fragmentDailyReportForSaleMan = new FragmentDailyReportForSaleMan();
                    fragmentTransaction.replace(R.id.fragment_report, fragmentDailyReportForSaleMan);
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @SuppressLint("SdCardPath")
    private void backupDB() {
        /*Calendar now = Calendar.getInstance();
        String today = now.get(Calendar.DATE) + "." + (now.get(Calendar.MONTH) + 1)
                + "." + now.get(Calendar.YEAR);*/
        String today = Utils.getCurrentDate(true);

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                Toast.makeText(getApplicationContext(), "Backup database is starting...",
                        Toast.LENGTH_SHORT).show();
                String currentDBPath = "/data/com.aceplus.samparoo/databases/aceplus-dms.sqlite";
                //String currentDBPath = "/data/com.aceplus.myanmar_padauk/databases/myanmar-padauk.db";

                String backupDBPath = "Parami_DB_Backup_" + today + ".db";
                File currentDB = new File(data, currentDBPath);

                String folderPath = "mnt/sdcard/Parami_DB_Backup";
                File f = new File(folderPath);
                f.mkdir();
                File backupDB = new File(f, backupDBPath);
                FileChannel source = new FileInputStream(currentDB).getChannel();
                FileChannel destination = new FileOutputStream(backupDB).getChannel();
                destination.transferFrom(source, 0, source.size());
                source.close();
                destination.close();
                Toast.makeText(getApplicationContext(), "Backup database Successful!",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Please set Permission for Storage in Setting!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Cannot Backup!",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private ArrayList<JSONObject> getSaleInvoiceReports(String customerId) {

        ArrayList<JSONObject> saleInvoiceReportsArrayList = new ArrayList<JSONObject>();

        Cursor cursor = database.rawQuery("SELECT * FROM INVOICE where INVOICE_ID not like 'SX%' and INVOICE_ID not like 'OS%' and CUSTOMER_ID = '" + customerId + "'", null);
        while (cursor.moveToNext()) {

            JSONObject saleInvoiceReportJsonObject = new JSONObject();
            try {

                saleInvoiceReportJsonObject.put("invoiceId", cursor.getString(cursor.getColumnIndex("INVOICE_ID")));

                Cursor cursorForCustomer = database.rawQuery(
                        "SELECT CUSTOMER_NAME, ADDRESS FROM CUSTOMER"
                                + " WHERE CUSTOMER_ID = \"" + cursor.getString(cursor.getColumnIndex("CUSTOMER_ID")) + "\""
                        , null);
                if (cursorForCustomer.moveToNext()) {

                    saleInvoiceReportJsonObject.put("customerName"
                            , cursorForCustomer.getString(cursorForCustomer.getColumnIndex("CUSTOMER_NAME")));
                    saleInvoiceReportJsonObject.put("address"
                            , cursorForCustomer.getString(cursorForCustomer.getColumnIndex("ADDRESS")));

                    double totalAmount = cursor.getDouble(cursor.getColumnIndex("TOTAL_AMOUNT"));
                    double discount = cursor.getDouble(cursor.getColumnIndex("TOTAL_DISCOUNT_AMOUNT"));
                    Log.i("TotalDis", discount + "");
                    saleInvoiceReportJsonObject.put("totalAmount", totalAmount);
                    saleInvoiceReportJsonObject.put("discount", discount);
                    saleInvoiceReportJsonObject.put("netAmount", totalAmount - discount);
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }

            saleInvoiceReportsArrayList.add(saleInvoiceReportJsonObject);
        }

        return saleInvoiceReportsArrayList;
    }

    private ArrayList<JSONObject> getPromotionReports() {

        ArrayList<JSONObject> promotionPriceReportsArrayList = new ArrayList<JSONObject>();

        String invoiceId, productId;
        String customerId = "", customerName = "", customerAddress = "";

        Cursor cursor = database.rawQuery("SELECT * FROM PROMOTION_INVOICE", null);
        while (cursor.moveToNext()) {
            invoiceId = cursor.getString(cursor.getColumnIndex("ID"));
            productId = cursor.getString(cursor.getColumnIndex("PROMOTION_PRODUCT_ID"));

            cursor = database.rawQuery("SELECT * FROM INVOICE WHERE INVOICE_ID='" + invoiceId + "'", null);
            while (cursor.moveToNext()) {
                customerId = cursor.getString(cursor.getColumnIndex("CUSTOMER_ID"));
            }

            cursor = database.rawQuery("SELECT * FROM CUSTOMER WHERE CUSTOMER_ID='" + customerId + "'", null);
            while (cursor.moveToNext()) {
                customerName = cursor.getString(cursor.getColumnIndex("CUSTOMER_NAME"));
                customerAddress = cursor.getString(cursor.getColumnIndex("ADDRESS"));
            }

            JSONObject promotionReportJsonObject = new JSONObject();
            try {
                promotionReportJsonObject.put("invoiceId", invoiceId);
                promotionReportJsonObject.put("productId", productId);
                promotionReportJsonObject.put("customerName", customerName);
                promotionReportJsonObject.put("customerAddress", customerAddress);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            promotionPriceReportsArrayList.add(promotionReportJsonObject);
        }



        /*Cursor cursor = database.rawQuery("SELECT * FROM PROMOTION", null);
        while (cursor.moveToNext()) {

            JSONObject promotionReportJsonObject = new JSONObject();
            try {

                JSONArray itemProductListJSONArray = new JSONArray(cursor.getString(cursor.getColumnIndex("PRO_ITEM_PRODUCTS")));
                JSONArray presentProductListJSONArray = new JSONArray(cursor.getString(cursor.getColumnIndex("PRO_PRESENT_PRODUCTS")));

                Cursor cursorForCustomer = database.rawQuery(
                        "SELECT CUSTOMER_NAME, ADDRESS FROM CUSTOMER"
                                + " WHERE CUSTOMER_ID = \"" + cursor.getString(cursor.getColumnIndex("PRO_CUSTOMER_ID")) + "\""
                        , null);
                if (cursorForCustomer.moveToNext()) {
                    int totalAmoutn = cursor.getInt(cursor.getColumnIndex("PRO_TOTAL_AMOUNT"));
                    if (totalAmoutn != 0) {
                        promotionReportJsonObject.put("invoiceId", cursor.getString(cursor.getColumnIndex("PRO_INVOICE_ID")));
                        promotionReportJsonObject.put("customerName"
                                , cursorForCustomer.getString(cursorForCustomer.getColumnIndex("CUSTOMER_NAME")));
                        promotionReportJsonObject.put("address"
                                , cursorForCustomer.getString(cursorForCustomer.getColumnIndex("ADDRESS")));

                        promotionReportJsonObject.put("totalAmount", cursor.getString(cursor.getColumnIndex("PRO_TOTAL_AMOUNT")));
                        promotionReportJsonObject.put("itemProductList", itemProductListJSONArray);
                        promotionReportJsonObject.put("presentProductList", presentProductListJSONArray);
                        promotionPriceReportsArrayList.add(promotionReportJsonObject);
                    }
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }*/

        return promotionPriceReportsArrayList;
    }

    private ArrayList<JSONObject> getSaleReturnReports() {

        ArrayList<JSONObject> saleReturnReportsArrayList = new ArrayList<JSONObject>();

        Cursor cursor = database.rawQuery("SELECT * FROM SALE_RETURN WHERE ` ", null);
        while (cursor.moveToNext()) {

            JSONObject saleReturnReportJsonObject = new JSONObject();
            try {
                /*saleReturnReportJsonObject.put("customerAddress", cursor.getString(cursor.getColumnIndex("ADDRESS")));
                saleReturnReportJsonObject.put("customerName"
                        , cursor.getString(cursor.getColumnIndex("CUSTOMER_NAME")));
                saleReturnReportJsonObject.put("date", cursor.getString(cursor.getColumnIndex("DATE")));
                JSONArray productListJSONArray = new JSONArray(cursor.getString(cursor.getColumnIndex("RETURN_PRODUCT")));
                saleReturnReportJsonObject.put("returnProductList", productListJSONArray);
                //saleReturnReportJsonObject.put("returnProductList", cursor.getString(cursor.getColumnIndex("RETURN_PRODUCT")));*/

                saleReturnReportJsonObject.put("saleReturnId", cursor.getString(cursor.getColumnIndex("SALE_RETURN_ID")));

                Log.i("SRID", cursor.getString(cursor.getColumnIndex("SALE_RETURN_ID")));
                saleReturnReportJsonObject.put("returnedDate", cursor.getString(cursor.getColumnIndex("RETURNED_DATE")));

                //  saleReturnReportJsonObject.put("customerId", cursor.getString(cursor.getColumnIndex("CUSTOMER_ID")));

                String customer_Id = cursor.getString(cursor.getColumnIndex("CUSTOMER_ID"));
                saleReturnReportJsonObject.put("returnAmount", cursor.getString(cursor.getColumnIndex("PAY_AMT")));
                saleReturnReportJsonObject.put("customerId", cursor.getString(cursor.getColumnIndex("CUSTOMER_ID")));

            } catch (JSONException e) {

                e.printStackTrace();
            }

            Cursor cur_CusId = database.rawQuery("select * from CUSTOMER WHERE ID = '" + cursor.getString(cursor.getColumnIndex("CUSTOMER_ID")) + "'", null);

            while (cur_CusId.moveToNext()) {


                String cusName = cur_CusId.getString(cur_CusId.getColumnIndex("CUSTOMER_NAME"));
                String cusAddress = cur_CusId.getString(cur_CusId.getColumnIndex("ADDRESS"));

                try {
                    saleReturnReportJsonObject.put("customerName", cusName);
                    saleReturnReportJsonObject.put("customerAddress", cusAddress);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            saleReturnReportsArrayList.add(saleReturnReportJsonObject);
        }

        return saleReturnReportsArrayList;
    }

    private ArrayList<JSONObject> getProductBalanceReports() {

        ArrayList<JSONObject> productBalanceReportsArrayList = new ArrayList<JSONObject>();

        Cursor cursor = database.rawQuery("SELECT * FROM PRODUCT", null);
        while (cursor.moveToNext()) {

            JSONObject productBalanceReportJsonObject = new JSONObject();
            try {

                double totalQuantity = cursor.getDouble(cursor.getColumnIndex("TOTAL_QTY"));
                double remainingQuanity = cursor.getDouble(cursor.getColumnIndex("REMAINING_QTY"));
                double orderQuantity = cursor.getDouble(cursor.getColumnIndex("ORDER_QTY"));
                double soldQuantity = cursor.getDouble(cursor.getColumnIndex("SOLD_QTY"));
                double exchangeQuantity = cursor.getDouble(cursor.getColumnIndex("EXCHANGE_QTY"));
                double returnQuantity = cursor.getDouble(cursor.getColumnIndex("RETURN_QTY"));
                double deliveryQuantity = cursor.getDouble(cursor.getColumnIndex("DELIVERY_QTY"));
                double presentQuantity = cursor.getDouble(cursor.getColumnIndex("PRESENT_QTY"));


                productBalanceReportJsonObject.put("productName"
                        , cursor.getString(cursor.getColumnIndex("PRODUCT_NAME")));
                productBalanceReportJsonObject.put("totalQuantity", totalQuantity);
                productBalanceReportJsonObject.put("soldQuantity", soldQuantity);
                productBalanceReportJsonObject.put("remainingQuantity", remainingQuanity);
                productBalanceReportJsonObject.put("orderQuantity", orderQuantity);
                productBalanceReportJsonObject.put("returnQuantity", returnQuantity);
                productBalanceReportJsonObject.put("deliveryQuantity", deliveryQuantity);
                productBalanceReportJsonObject.put("presentQuantity", presentQuantity);
                productBalanceReportJsonObject.put("exchangeQuantity", exchangeQuantity);
            } catch (JSONException e) {

                e.printStackTrace();
            }

            productBalanceReportsArrayList.add(productBalanceReportJsonObject);
        }

        return productBalanceReportsArrayList;
    }

    private ArrayList<JSONObject> getCustomerFeedbackReports() {

        ArrayList<JSONObject> customerFeedbackReportsArrayList = new ArrayList<JSONObject>();

        Cursor cursor = database.rawQuery("SELECT * FROM DID_CUSTOMER_FEEDBACK ", null);
        while (cursor.moveToNext()) {

            JSONObject customerFeedbackReportJsonObject = new JSONObject();
            try {

                Cursor cursorForCustomer = database.rawQuery(
                        "SELECT * FROM CUSTOMER"
                                + " WHERE id = \"" + cursor.getString(cursor.getColumnIndex("CUSTOMER_NO")) + "\""
                        , null);
                if (cursorForCustomer.moveToNext()) {

                    customerFeedbackReportJsonObject.put("customerName"
                            , cursorForCustomer.getString(cursorForCustomer.getColumnIndex("CUSTOMER_NAME")));
                }

                customerFeedbackReportJsonObject.put("description"
                        , cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
                customerFeedbackReportJsonObject.put("remark", cursor.getString(cursor.getColumnIndex("REMARK")));
            } catch (JSONException e) {

                e.printStackTrace();
            }

            customerFeedbackReportsArrayList.add(customerFeedbackReportJsonObject);
        }

        return customerFeedbackReportsArrayList;
    }

    private ArrayList<JSONObject> getPreOrderReports() {

        ArrayList<JSONObject> preOrderReportsArrayList = new ArrayList<JSONObject>();

        Cursor cursor = database.rawQuery(
                "SELECT CUSTOMER.CUSTOMER_NAME, ADVANCE_PAYMENT_AMOUNT, NET_AMOUNT, INVOICE_ID"
                        + " FROM PRE_ORDER"
                        + " INNER JOIN CUSTOMER"
                        + " ON CUSTOMER.id = PRE_ORDER.CUSTOMER_ID WHERE SALE_FLAG = 0"
                , null);
        Log.i("PRE OREDER COUNT ", cursor.getCount() + "");
        while (cursor.moveToNext()) {

            JSONObject preOrderReportJSONObject = new JSONObject();
            try {

                preOrderReportJSONObject.put("customerName", cursor.getString(cursor.getColumnIndex("CUSTOMER_NAME")));
                preOrderReportJSONObject.put("prepaidAmount", cursor.getDouble(cursor.getColumnIndex("ADVANCE_PAYMENT_AMOUNT")));
                preOrderReportJSONObject.put("totalAmount", cursor.getDouble(cursor.getColumnIndex("NET_AMOUNT")));

                preOrderReportJSONObject.put("invoice_Id", cursor.getString(cursor.getColumnIndex("INVOICE_ID")));

//                JSONArray productListJSONArray = new JSONArray(cursor.getString(cursor.getColumnIndex("PRODUCT_LIST")));
//                for (int i = 0; i < productListJSONArray.length(); i++) {
//
//                    JSONObject productJSONObject = productListJSONArray.getJSONObject(i);
//                    System.out.println("SELECT PRODUCT_NAME FROM PRODUCT"
//                            + " WHERE PRODUCT_ID = '" + productJSONObject.getString("productId") + "'");
//                    Cursor cursorForProduct = database.rawQuery(
//                            "SELECT PRODUCT_NAME FROM PRODUCT"
//                                    + " WHERE PRODUCT_ID = '" + productJSONObject.getString("productId") + "'", null);
//                    if (cursorForProduct.moveToNext()) {
//
//                        productJSONObject.put("productName", cursorForProduct.getString(cursorForProduct.getColumnIndex("PRODUCT_NAME")));
//                    }
//                }
//                preOrderReportJSONObject.put("productList", productListJSONArray);

            } catch (JSONException e) {

                e.printStackTrace();
                Log.e("ERR :", e.getMessage());
            }

            preOrderReportsArrayList.add(preOrderReportJSONObject);
        }

        return preOrderReportsArrayList;
    }

    private ArrayList<JSONObject> getPOSMReports() {

        ArrayList<JSONObject> POSMReportArrayList = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from POSM_BY_CUSTOMER", null);

        while (cursor.moveToNext()) {

            JSONObject POSMReportJSONObject = new JSONObject();


            String qty = cursor.getString(cursor.getColumnIndex("QUANTITY"));
            String cus_Id = cursor.getString(cursor.getColumnIndex("CUSTOMER_ID"));

            Cursor cursor1 = database.rawQuery("select * from CUSTOMER where id='" + cus_Id + "'", null);

            while (cursor1.moveToNext()) {

                cus_name = cursor1.getString(cursor1.getColumnIndex("CUSTOMER_NAME"));

            }

            String stock_Id = cursor.getString(cursor.getColumnIndex("STOCK_ID"));

            Cursor cursor2 = database.rawQuery("select * from PRODUCT where ID='" + stock_Id + "'", null);

            while (cursor2.moveToNext()) {

                product_name = cursor2.getString(cursor2.getColumnIndex("PRODUCT_NAME"));
                Log.i("ProductName", product_name);

            }

            String shop_Id = cursor.getString(cursor.getColumnIndex("SHOP_TYPE_ID"));

            Cursor cursor3 = database.rawQuery("select * from SHOP_TYPE where ID='" + shop_Id + "'", null);

            while (cursor3.moveToNext()) {

                shop_name = cursor3.getString(cursor3.getColumnIndex("SHOP_TYPE_NAME"));


            }


            try {

                POSMReportJSONObject.put("CustomerName", cus_name);
                POSMReportJSONObject.put("ProductName", product_name);
                POSMReportJSONObject.put("ShopName", shop_name);
                POSMReportJSONObject.put("Quantity", qty);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            POSMReportArrayList.add(POSMReportJSONObject);


        }

        return POSMReportArrayList;

    }

    private ArrayList<JSONObject> getDeliveryInvoiceReports() {


        ArrayList<JSONObject> DeliveryReportArrayList = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from DELIVERY_UPLOAD", null);

        while (cursor.moveToNext()) {

            JSONObject DeliveryReportJSONObject = new JSONObject();

            String invoiceNo = cursor.getString(cursor.getColumnIndex("INVOICE_NO"));
            String cus_Id = cursor.getString(cursor.getColumnIndex("CUSTOMER_ID"));

            Cursor cursor1 = database.rawQuery("select * from CUSTOMER where id='" + cus_Id + "'", null);
            while (cursor1.moveToNext()) {

                cusName = cursor1.getString(cursor1.getColumnIndex("CUSTOMER_NAME"));
                address = cursor1.getString(cursor1.getColumnIndex("ADDRESS"));

            }


            try {
                DeliveryReportJSONObject.put("InvoiceNo", invoiceNo);
                DeliveryReportJSONObject.put("CustomerName", cusName);
                DeliveryReportJSONObject.put("Address", address);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            DeliveryReportArrayList.add(DeliveryReportJSONObject);


        }


        return DeliveryReportArrayList;

    }


    private ArrayList<JSONObject> getCustomerVisitRecordData() {

        ArrayList<JSONObject> visitRecordArrayList = new ArrayList<JSONObject>();
        Cursor cursor = database.rawQuery("SELECT * FROM customer_visit_recrod_report", null);
        while (cursor.moveToNext()) {

            JSONObject visitRecordJsonObject = new JSONObject();
            try {

                visitRecordJsonObject.put("customerName", cursor.getString(cursor.getColumnIndex("customer_name")));
                visitRecordJsonObject.put("address", cursor.getString(cursor.getColumnIndex("address")));
                visitRecordJsonObject.put("currentTime", cursor.getString(cursor.getColumnIndex("current_time")));

            } catch (JSONException e) {

                e.printStackTrace();
            }

            visitRecordArrayList.add(visitRecordJsonObject);
        }

        return visitRecordArrayList;
    }

    private ArrayList<JSONObject> getDeliveryReports() {

        ArrayList<JSONObject> deliveryReportsArrayList = new ArrayList<JSONObject>();

        Cursor cursor = database.rawQuery(
                "SELECT CUSTOMER.CUSTOMER_NAME, DELIVERY.AMOUNT, DELIVERY.FIRST_PAID_AMOUNT, DELIVERY.REMAINING_AMOUNT, DELIVERY.SALE_ORDER_ITEMS"
                        + " FROM DELIVERY"
                        + " INNER JOIN CUSTOMER"
                        + " ON CUSTOMER.CUSTOMER_ID = DELIVERY.CUSTOMER_ID"
                , null);
        while (cursor.moveToNext()) {

            JSONObject preOrderReportJSONObject = new JSONObject();
            try {

                JSONArray productListJSONArray = new JSONArray(
                        cursor.getString(cursor
                                .getColumnIndex("SALE_ORDER_ITEMS")));

                // We should care incomplete order vouchers.
                if (productListJSONArray.length() == 0) {

                    continue;
                }

                preOrderReportJSONObject.put("customerName", cursor.getString(cursor.getColumnIndex("CUSTOMER_NAME")));
                preOrderReportJSONObject.put("amount", cursor.getDouble(cursor.getColumnIndex("AMOUNT")));
                preOrderReportJSONObject.put("firstPaidAmount", cursor.getDouble(cursor.getColumnIndex("FIRST_PAID_AMOUNT")));
                preOrderReportJSONObject.put("remainingAmount", cursor.getDouble(cursor.getColumnIndex("REMAINING_AMOUNT")));

                for (int i = 0; i < productListJSONArray.length(); i++) {

                    JSONObject productJSONObject = productListJSONArray.getJSONObject(i);
                    Cursor cursorForProduct = database.rawQuery(
                            "SELECT PRODUCT_NAME FROM PRODUCT"
                                    + " WHERE PRODUCT_ID = '" + productJSONObject.getString("productId") + "'", null);
                    if (cursorForProduct.moveToNext()) {

                        productJSONObject.put("productName", cursorForProduct.getString(cursorForProduct.getColumnIndex("PRODUCT_NAME")));
                    }
                }
                preOrderReportJSONObject.put("saleOrderItems", productListJSONArray);
            } catch (JSONException e) {

                e.printStackTrace();
            }

            deliveryReportsArrayList.add(preOrderReportJSONObject);
        }

        return deliveryReportsArrayList;
    }

    private ArrayList<JSONObject> getDeliveredInvoiceReports() {

        ArrayList<JSONObject> deliveredInvoiceReportsArrayList = new ArrayList<JSONObject>();

        Cursor cursor = database.rawQuery("SELECT * FROM DELIVERED_DATA", null);
        while (cursor.moveToNext()) {

            JSONObject deliveredInvoiceReportJSONObject = new JSONObject();
            try {

                deliveredInvoiceReportJSONObject.put("invoiceId"
                        , cursor.getString(cursor.getColumnIndex("DELIVERY_INVOICE_ID")));

                Cursor cursorForCustomer = database.rawQuery(
                        "SELECT CUSTOMER_NAME, ADDRESS FROM CUSTOMER"
                                + " WHERE CUSTOMER_ID = \"" + cursor.getString(cursor.getColumnIndex("CUSTOMER_ID")) + "\""
                        , null);
                if (cursorForCustomer.moveToNext()) {

                    deliveredInvoiceReportJSONObject.put("customerName"
                            , cursorForCustomer.getString(cursorForCustomer.getColumnIndex("CUSTOMER_NAME")));
                    deliveredInvoiceReportJSONObject.put("address"
                            , cursorForCustomer.getString(cursorForCustomer.getColumnIndex("ADDRESS")));
                }

                double totalAmount = cursor.getDouble(cursor.getColumnIndex("TOTAL_AMOUNT"));
                double discount = cursor.getDouble(cursor.getColumnIndex("TOTAL_DISCOUNT_AMOUNT"));
                deliveredInvoiceReportJSONObject.put("totalAmount", totalAmount);
                deliveredInvoiceReportJSONObject.put("discount", discount);
                deliveredInvoiceReportJSONObject.put("netAmount", totalAmount - discount);
            } catch (JSONException e) {

                e.printStackTrace();
            }

            deliveredInvoiceReportsArrayList.add(deliveredInvoiceReportJSONObject);
        }

        return deliveredInvoiceReportsArrayList;
    }

    private ArrayList<JSONObject> getSaleExchangeSendReports() {

        ArrayList<JSONObject> saleExchangeSendReportsArrayList = new ArrayList<JSONObject>();

        Cursor cursor = database.rawQuery("SELECT * FROM SALE_EXCHANGE WHERE SEND_OR_RECEIVE=0", null);
        while (cursor.moveToNext()) {

            JSONObject saleExchangeSendReportJsonObject = new JSONObject();
            try {
                saleExchangeSendReportJsonObject.put("id", cursor.getString(cursor.getColumnIndex("SALE_EXCHANGE_ID")));
                saleExchangeSendReportJsonObject.put("date", cursor.getString(cursor.getColumnIndex("DATE")));
                saleExchangeSendReportJsonObject.put("salemanID", cursor.getString(cursor.getColumnIndex("SENDER_SALEMAN_CODE")));
                saleExchangeSendReportJsonObject.put("receiverSalemanID", cursor.getString(cursor.getColumnIndex("RECEIVER_SALEMAN_CODE")));
            } catch (JSONException e) {

                e.printStackTrace();
            }

            saleExchangeSendReportsArrayList.add(saleExchangeSendReportJsonObject);
        }

        return saleExchangeSendReportsArrayList;
    }

    private ArrayList<JSONObject> getSaleExchangeReceiveReports() {

        ArrayList<JSONObject> saleExchangeReceiveReportsArrayList = new ArrayList<JSONObject>();

        Cursor cursor = database.rawQuery("SELECT * FROM SALE_EXCHANGE_RECEIVE WHERE SEND_OR_RECEIVE=1", null);
        while (cursor.moveToNext()) {

            JSONObject saleExchangeReceiveReportJsonObject = new JSONObject();
            try {
                saleExchangeReceiveReportJsonObject.put("id", cursor.getString(cursor.getColumnIndex("SALE_EXCHANGE_ID")));
                saleExchangeReceiveReportJsonObject.put("date", cursor.getString(cursor.getColumnIndex("DATE")));
                saleExchangeReceiveReportJsonObject.put("salemanID", cursor.getString(cursor.getColumnIndex("SENDER_SALEMAN_CODE")));
                saleExchangeReceiveReportJsonObject.put("receiverSalemanID", cursor.getString(cursor.getColumnIndex("RECEIVER_SALEMAN_CODE")));
            } catch (JSONException e) {

                e.printStackTrace();
            }

            saleExchangeReceiveReportsArrayList.add(saleExchangeReceiveReportJsonObject);
        }

        return saleExchangeReceiveReportsArrayList;
    }

    private ArrayList<JSONObject> getCustomerReports() {
        ArrayList<JSONObject> customerReportsArrayList = new ArrayList<JSONObject>();
        Cursor cursor = database.rawQuery("SELECT * FROM CUSTOMER", null);
        while (cursor.moveToNext()) {
            JSONObject customerReceiveReportJsonObject = new JSONObject();
            try {
                customerReceiveReportJsonObject.put("customerId", cursor.getString(cursor.getColumnIndex("id")));
                customerReceiveReportJsonObject.put("customerName", cursor.getString(cursor.getColumnIndex("CUSTOMER_NAME")));
            } catch (JSONException e) {

                e.printStackTrace();
            }

            customerReportsArrayList.add(customerReceiveReportJsonObject);
        }

        return customerReportsArrayList;
    }

    @Override
    public void onBackPressed() {
        Utils.backToHome(this);
    }
}
