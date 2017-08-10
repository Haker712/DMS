package com.aceplus.samparoo.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.aceplus.samparoo.CustomerVisitActivity;
import com.aceplus.samparoo.HomeActivity;
import com.aceplus.samparoo.LoginActivity;
import com.aceplus.samparoo.MarketingActivity;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.customer.CustomerActivity;
import com.aceplus.samparoo.marketing.MainFragmentActivity;
import com.aceplus.samparoo.model.CreditInvoice;
import com.aceplus.samparoo.model.Product;
import com.aceplus.samparoo.model.Promotion;
import com.aceplus.samparoo.model.SoldProduct;
import com.aceplus.samparoo.model.forApi.Invoice;
import com.aceplus.samparoo.model.forApi.InvoiceDetail;
import com.aceplus.samparoo.model.forApi.LoginRequest;
import com.aceplus.samparoo.myinterface.OnActionClickListener;
import com.google.gson.Gson;
import com.starmicronics.stario.PortInfo;
import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/**
 * Created by haker on 2/3/17.
 */

public class Utils {

    private static SQLiteDatabase database;

    private static DecimalFormat decimalFormatterWithComma = new DecimalFormat("###,##0");

    public static ProgressDialog progressDialog;

    public static final String MODE_CUSTOMER_FEEDBACK = "mode_customer_feedback";
    public static final String MODE_GENERAL_SALE = "mode_general_sale";

    public static final String forPackageSale = "for-package-sale";
    public static final String forPreOrderSale = "for-pre-order-sale";
    public static final String FOR_DELIVERY = "for-delivery";
    public static final String FOR_OTHERS = "for-others";
    public static final String FOR_SALE_RETURN = "for-sale-return";
    public static final String FOR_SALE_RETURN_EXCHANGE = "for-sale_return_exchange";
    public static final String FOR_SALE_EXCHANGE="for_sale_exchange";
    public static final String FOR_DISPLAY_ASSESSMENT="for_display_assessment";
    public static final String FOR_OUTLET_STOCK_AVAILABILITY="for_outlet_stock_availibility";
    public static final String FOR_SIZE_IN_STORE_SHARE="for_size_in_store_share";
    public static final String FOR_COMPETITORACTIVITY="for_competitoractivity";
    public static final String PRINT_FOR_NORMAL_SALE = "print-for-normal-sale";
    public static final String PRINT_FOR_C = "print-for-c";
    public static final String PRINT_FOR_PRE_ORDER = "print-for-preorder";

    private static Formatter formatter;
    static double taxPercent = 0.0;
    static String taxType = null;

    public static String getInvoiceID(Context context, String mode, String salemanID, String locationCode) {

        if (database == null) {

            database = new Database(context).getReadableDatabase();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);

        int idLength = 0;
        String prefix = locationCode + salemanID;
        long currentInvoiceNumber = 0;
        if (mode.equals(MODE_CUSTOMER_FEEDBACK)) {

            idLength = 20;
            prefix += new SimpleDateFormat("yyMMdd", Locale.ENGLISH).format(new Date());

//			currentInvoiceNumber += DatabaseUtils.queryNumEntries(database, "DID_CUSTOMER_FEEDBACK");
            currentInvoiceNumber += DatabaseUtils.queryNumEntries(database, "INVOICE");

            Date lastUploadedDate = Preferences.getCustomerFeedbackLastUploadedDate(context);
            if (lastUploadedDate != null) {

                try {
                    Date todayDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                    if (todayDate.after(lastUploadedDate)) {

                        Preferences.resetNumberOfCustomerFeedbackUploaded(context);
                    }

                    currentInvoiceNumber += Preferences.getCustomerFeedbackUploadedCount(context);
                } catch (ParseException e) {

                    e.printStackTrace();
                }
            }
            currentInvoiceNumber++;
        } else if (mode.equals(MODE_GENERAL_SALE)) {

            idLength = 20;
            prefix += new SimpleDateFormat("yyMMdd", Locale.ENGLISH).format(new Date());

            currentInvoiceNumber += DatabaseUtils.queryNumEntries(database, "DID_CUSTOMER_FEEDBACK");

            Date lastUploadedDate = Preferences.getSaleLastUploadedDate(context);
            if (lastUploadedDate != null) {

                try {
                    Date todayDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
                    if (todayDate.after(lastUploadedDate)) {

                        Preferences.resetNumberOfSaleUploaded(context);
                    }

                    currentInvoiceNumber += Preferences.getSaleUploadedCount(context);
                } catch (ParseException e) {

                    e.printStackTrace();
                }
            }
            currentInvoiceNumber++;
        }

        return prefix + String.format("%0" + (idLength - prefix.length()) + "d", currentInvoiceNumber);
    }

    public static void backToHome(Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * Go to Login activity.
     *
     * @param activity current activity name
     */
    public static void backToLogin(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void backToCustomerVisit(Activity activity) {
        Intent intent = new Intent(activity, CustomerVisitActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void backToCustomer(Activity activity) {
        Intent intent = new Intent(activity, CustomerActivity.class);
        intent.putExtra("SaleExchange", "no");
        activity.startActivity(intent);
        activity.finish();
    }

    public static void backToMarketingActivity(Activity activity) {
        Intent intent = new Intent(activity, MarketingActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public static String formatAmount(Double amount) {

        return decimalFormatterWithComma.format(amount);
    }

    public static String getCurrentDate(boolean withTime) {

        String dateFormat = "yyyy-MM-dd";
        if (withTime) {

            dateFormat += " HH:mm:ss";
        }

        return new SimpleDateFormat(dateFormat).format(new Date());
    }

    public static void callDialog(final String message, final Activity activity) {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity, ProgressDialog.THEME_HOLO_LIGHT);
        }

        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (activity.isFinishing()) {
                    return;
                } else {
                    progressDialog = new ProgressDialog(activity, ProgressDialog.THEME_HOLO_LIGHT);
                    progressDialog.setIndeterminate(false);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage(message);
                    progressDialog.show();
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            }
        });
    }

    public static void cancelDialog() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }

    public static void commonDialog(final String message, final Activity activity, final int flag) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (activity.isFinishing()) {
                    return;
                } else {
                    int statusImage = 0;
                    String title = "";
                    if(flag == 0) {
                        statusImage = R.drawable.success;
                        title = "Success";
                    } else if(flag == 1) {
                        statusImage = R.drawable.fail;
                        title = "Error";
                    } else if(flag == 2) {
                        statusImage = R.drawable.info;
                        title = "Info";
                    }

                    new AlertDialog.Builder(activity)
                            .setTitle(title)
                            .setMessage(message)
                            .setPositiveButton("OK", null)
                            .setCancelable(false)
                            .setIcon(statusImage)
                            .show();
                }
            }
        });
    }

    public static boolean isOsMarshmallow() {

        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static String encodePassword(String str) {
        byte[] data = null;
        try {
            data = str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(data, Base64.NO_WRAP);
    }

    public static String getJsonString(Object object) {
        Gson gson = new Gson();
        String jsonString = String.valueOf(gson.toJson(object));
        Log.d("jsonString>>>", jsonString);
        return jsonString;

    }

    public static String createParamData(String user_no, String password, int routeId) {
        String paramData = "";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSiteActivationKey(Constant.SITE_ACTIVATION_KEY);
        loginRequest.setTabletActivationKey(Constant.TABLET_ACTIVATION_KEY);
        loginRequest.setUserId(user_no);
        //loginRequest.setPassword(Utils.encodePassword(editTextPassword.getText().toString()));
        //String encodedPwd = Utils.encodePassword(password);
        //Log.i("encodedPwd>>>", encodedPwd);
        loginRequest.setPassword(password);
        loginRequest.setDate(Utils.getCurrentDate(false));
        loginRequest.setRoute(routeId);
        List<Object> objectList = new ArrayList<>();
        /*Object object = new Object();
        objectList.add(object);*/
        loginRequest.setData(objectList);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("site_activation_key", Constant.SITE_ACTIVATION_KEY);
            jsonObject.put("tablet_activation_key", Constant.TABLET_ACTIVATION_KEY);
            jsonObject.put("user_id", loginRequest.getUserId());
            jsonObject.put("password", loginRequest.getPassword());
            jsonObject.put("route", loginRequest.getRoute());
            jsonObject.put("date", loginRequest.getDate());
            jsonObject.put("data", loginRequest.getData());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("param_data>>>", jsonObject.toString());

        paramData = jsonObject.toString();
        return paramData;
    }

    public static String createLoginParamData(String user_no, String password, int routeId, String tabletKey) {
        String paramData = "";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSiteActivationKey(Constant.SITE_ACTIVATION_KEY);
        loginRequest.setTabletActivationKey(Constant.TABLET_ACTIVATION_KEY);
        loginRequest.setUserId(user_no);
        //loginRequest.setPassword(Utils.encodePassword(editTextPassword.getText().toString()));
        //String encodedPwd = Utils.encodePassword(password);
        //Log.i("encodedPwd>>>", encodedPwd);
        loginRequest.setPassword(password);
        loginRequest.setDate(Utils.getCurrentDate(false));
        loginRequest.setRoute(routeId);
        loginRequest.setTabletKey(tabletKey);
        List<Object> objectList = new ArrayList<>();
        /*Object object = new Object();
        objectList.add(object);*/
        loginRequest.setData(objectList);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("site_activation_key", Constant.SITE_ACTIVATION_KEY);
            jsonObject.put("tablet_activation_key", Constant.TABLET_ACTIVATION_KEY);
            jsonObject.put("user_id", loginRequest.getUserId());
            jsonObject.put("password", loginRequest.getPassword());
            jsonObject.put("route", loginRequest.getRoute());
            jsonObject.put("tablet_key", loginRequest.getTabletKey());
            jsonObject.put("date", loginRequest.getDate());
            jsonObject.put("data", loginRequest.getData());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("param_data>>>", jsonObject.toString());

        paramData = jsonObject.toString();
        return paramData;
    }

    public static String getDeviceId(Activity activity) {
        return Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);

    }

    public static String getInvoiceNo(Context context, String salemanId, String locationCode, String mode) {

        if (database == null) {

            database = (new Database(context)).getDataBase();
        }

//		int idLength = 15;
//		if (mode.equals(Utils.forPreOrderSale)) {
//
//			idLength = 16;
//		}
        int idLength = 18;

        String invoiceNo = new String();
        if (mode.equals(Utils.forPackageSale)) {

            invoiceNo += "P";
        } else if (mode.equals(Utils.forPreOrderSale)) {

            invoiceNo += "SO";
        } else if (mode.equals(Utils.FOR_DELIVERY)) {

            invoiceNo += "OS";
        } else if (mode.equals(Utils.FOR_SALE_RETURN)) {

            invoiceNo += "SR";
        } else if (mode.equals(Utils.FOR_SALE_EXCHANGE) || mode.equals(Utils.FOR_SALE_RETURN_EXCHANGE)) {

            invoiceNo += "SX";

        }else if (mode.equals(Utils.FOR_DISPLAY_ASSESSMENT)){

            invoiceNo +="DA";
        }else if (mode.equals(Utils.FOR_OUTLET_STOCK_AVAILABILITY)){

            invoiceNo +="OSA";

        }else if (mode.equals(Utils.FOR_SIZE_IN_STORE_SHARE)){

            invoiceNo +="SIS";

        }else if(mode.equals(Utils.FOR_COMPETITORACTIVITY)){

            invoiceNo +="CA";

        }

        invoiceNo += locationCode;
        invoiceNo += salemanId;
        invoiceNo += new SimpleDateFormat("yyMMdd").format(new Date());

        int next = 0;
        if (mode.equals(Utils.FOR_OTHERS) || mode.equals(Utils.forPackageSale)) {

            Cursor cursor = database.rawQuery("SELECT COUNT(*) AS COUNT FROM DID_CUSTOMER_FEEDBACK", null);
            if (cursor.moveToNext()) {

                next += cursor.getInt(cursor.getColumnIndex("COUNT"));
            }

            cursor = database.rawQuery("SELECT COUNT(*) AS COUNT FROM INVOICE", null);
            if (cursor.moveToNext()) {

                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1;
            }
        } else if (mode.equals(Utils.forPreOrderSale)) {

            Cursor cursor = database.rawQuery("SELECT COUNT(*) AS COUNT FROM PRE_ORDER", null);
            if (cursor.moveToNext()) {

                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1;
            }
        } else if (mode.equals(Utils.FOR_DELIVERY)) {

//			next = 1;
            Cursor cursor = database.rawQuery("SELECT COUNT(*) AS COUNT FROM DELIVERY_UPLOAD", null);
            if (cursor.moveToNext()) {

                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1;
            }
        } else if (mode.equals(Utils.FOR_SALE_RETURN)) {
            Cursor cursor = database.rawQuery("SELECT COUNT(*) AS COUNT FROM SALE_RETURN", null);
            if (cursor.moveToNext()) {

                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1;
            }

        }else if (mode.equals(Utils.FOR_SALE_RETURN_EXCHANGE)){

            Cursor cursor = database.rawQuery("SELECT COUNT(*) AS COUNT FROM SALE_RETURN", null);
            if (cursor.moveToNext()) {

                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1;
            }

        }else if(mode.equals(Utils.FOR_SALE_EXCHANGE)){

            Cursor cursor = database.rawQuery("SELECT COUNT(*) AS COUNT FROM INVOICE", null);
            if (cursor.moveToNext()) {

                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1;
            }

        }else if (mode.equals(Utils.FOR_DISPLAY_ASSESSMENT)) {
            Cursor cursor = database.rawQuery("SELECT COUNT(*) AS COUNT FROM OUTLET_VISIBILITY", null);
            if (cursor.moveToNext()) {

                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1;
            }

        }else if (mode.equals(Utils.FOR_OUTLET_STOCK_AVAILABILITY)){

            Cursor cursor=database.rawQuery("SELECT COUNT(*) AS COUNT FROM outlet_stock_availability", null);
            if (cursor.moveToNext()) {

                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1;
            }


        }else if (mode.equals(Utils.FOR_SIZE_IN_STORE_SHARE)){

            Cursor cursor=database.rawQuery("SELECT COUNT(*) AS COUNT FROM size_in_store_share", null);
            if (cursor.moveToNext()) {

                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1;
            }

        }else if (mode.equals(Utils.FOR_COMPETITORACTIVITY)){

            Cursor cursor=database.rawQuery("SELECT COUNT(*) AS COUNT FROM COMPETITOR_ACTIVITY",null);
            if (cursor.moveToNext()){

                next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1;

            }

        }

        return invoiceNo + String.format("%0" + (idLength - invoiceNo.length()) + "d", next);
    }

    public static String getInvoiceNoForPOSM(Context context, String saleManId, String locationCode) {

        int idLength = 14;

        if (database == null) {

            database = (new Database(context)).getDataBase();
        }
        int next = 0;

        Cursor cursor = database.rawQuery("SELECT COUNT(*) AS COUNT FROM POSM_BY_CUSTOMER", null);
        if (cursor.moveToNext()) {

            next += cursor.getInt(cursor.getColumnIndex("COUNT")) + 1;
        }

        String invoiceNo = "";

        invoiceNo += locationCode;
        invoiceNo += saleManId;
        invoiceNo += new SimpleDateFormat("yyMMdd").format(new Date());

        return invoiceNo + String.format("%0" + (idLength - invoiceNo.length()) + "d", next);
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public static OnActionClickListener onActionClickListener;

    public static void setOnActionClickListener(OnActionClickListener onActionClickListener) {
        Utils.onActionClickListener = onActionClickListener;
    }

    public static void askConfirmationDialog(String title, String message, final String type, Activity activity) {

        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        onActionClickListener.onActionClick(type);
                    }
                })
                .setNegativeButton("No", null)
                .show();

        TextView textViewYes = (TextView) alertDialog.findViewById(android.R.id.button1);
        textViewYes.setTextSize(25);
        TextView textViewNo = (TextView) alertDialog.findViewById(android.R.id.button2);
        textViewNo.setTextSize(25);
    }

    public static void print(final Activity activity, final String customerName, final String invoiceNumber
            , final String salePersonName, final String townshipName, final Invoice invoice, final List<SoldProduct> soldProductList
            , final List<Promotion> presentList, final String printFor, final String mode) {

        List<PortInfo> portInfoList = null;

        try {

            portInfoList = StarIOPort.searchPrinter("BT:Star");
        } catch (StarIOPortException e) {

            e.printStackTrace();
        }

        if (portInfoList == null || portInfoList.size() == 0) {

            return;
        }

        List<String> availableBluetoothPrinterNameList = new ArrayList<String>();
        for (PortInfo portInfo : portInfoList) {

            availableBluetoothPrinterNameList.add(portInfo.getPortName());
        }
        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(
                        activity
                        , android.R.layout.select_dialog_singlechoice
                        , availableBluetoothPrinterNameList);
        new android.app.AlertDialog.Builder(activity)
                .setTitle("Select Printer")
                .setNegativeButton("Cancel", null)
                .setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {

                        StarIOPort starIOPort = null;
                        try {

                            starIOPort = StarIOPort.getPort(arrayAdapter.getItem(position), "mini", 10000);
                            if (starIOPort.retreiveStatus().offline) {

                                if (!starIOPort.retreiveStatus().compulsionSwitch) {

                                    showToast(activity, "The Drawer is offline\nCash Drawer: Close");
                                } else {

                                    showToast(activity, "The Drawer is offline\nCash Drawer: Open");
                                }

                                return;
                            } else {

                                if (starIOPort.retreiveStatus().compulsionSwitch) {

                                    showToast(activity, "The Drawer is online\nCash Drawer: Open");
                                } else {

                                    byte[] printDataByteArray =
                                            convertFromListByteArrayTobyteArray(
                                                    getPrintDataByteArrayList(
                                                            activity
                                                            , customerName
                                                            , invoiceNumber
                                                            , salePersonName
                                                            , townshipName
                                                            , invoice
                                                            , soldProductList
                                                            , presentList
                                                            , printFor
                                                            , mode));
                                    starIOPort.writePort(printDataByteArray, 0, printDataByteArray.length);
                                }
                            }
                        } catch (StarIOPortException e) {

                            showToast(activity, "Failed to connect to drawer");
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } finally {
                            if (starIOPort != null) {

                                try {

                                    StarIOPort.releasePort(starIOPort);
                                } catch (StarIOPortException e) {

                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                })
                .show();
    }

    public static void printCredit(final Activity activity, final String customerName, final String invoiceNumber
            ,final String townshipName, final String salePersonName, final CreditInvoice creditInvoiceList) {

        List<PortInfo> portInfoList = null;

        try {

            portInfoList = StarIOPort.searchPrinter("BT:Star");
        } catch (StarIOPortException e) {

            e.printStackTrace();
        }

        if (portInfoList == null || portInfoList.size() == 0) {

            return;
        }

        List<String> availableBluetoothPrinterNameList = new ArrayList<String>();
        for (PortInfo portInfo : portInfoList) {

            availableBluetoothPrinterNameList.add(portInfo.getPortName());
        }
        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(
                        activity
                        , android.R.layout.select_dialog_singlechoice
                        , availableBluetoothPrinterNameList);
        new android.app.AlertDialog.Builder(activity)
                .setTitle("Select Printer")
                .setNegativeButton("Cancel", null)
                .setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {

                        StarIOPort starIOPort = null;
                        try {

                            starIOPort = StarIOPort.getPort(arrayAdapter.getItem(position), "mini", 10000);
                            if (starIOPort.retreiveStatus().offline) {

                                if (!starIOPort.retreiveStatus().compulsionSwitch) {

                                    showToast(activity, "The Drawer is offline\nCash Drawer: Close");
                                } else {

                                    showToast(activity, "The Drawer is offline\nCash Drawer: Open");
                                }

                                return;
                            } else {

                                if (starIOPort.retreiveStatus().compulsionSwitch) {

                                    showToast(activity, "The Drawer is online\nCash Drawer: Open");
                                } else {

                                    byte[] printDataByteArray =
                                            convertFromListByteArrayTobyteArray(
                                                    getPrintDataByteArrayListForCredit(
                                                            activity
                                                            , customerName
                                                            , invoiceNumber
                                                            , townshipName
                                                            , salePersonName
                                                            , creditInvoiceList));
                                    starIOPort.writePort(printDataByteArray, 0, printDataByteArray.length);
                                }
                            }
                        } catch (StarIOPortException e) {

                            showToast(activity, "Failed to connect to drawer");
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } finally {
                            if (starIOPort != null) {

                                try {

                                    StarIOPort.releasePort(starIOPort);
                                } catch (StarIOPortException e) {

                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                })
                .show();
    }

    public static void printDeliver(final Activity activity, final String customerName, final String orderInvoiceNo, final String orderSaleManName, final String invoiceNumber
            , final String salePersonName, final String townshipName, final Invoice invoice, final List<SoldProduct> soldProductList
            , final List<Promotion> presentList, final String printFor, final String mode) {

        List<PortInfo> portInfoList = null;

        try {

            portInfoList = StarIOPort.searchPrinter("BT:Star");
        } catch (StarIOPortException e) {

            e.printStackTrace();
        }

        if (portInfoList == null || portInfoList.size() == 0) {

            return;
        }

        List<String> availableBluetoothPrinterNameList = new ArrayList<String>();
        for (PortInfo portInfo : portInfoList) {

            availableBluetoothPrinterNameList.add(portInfo.getPortName());
        }
        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(
                        activity
                        , android.R.layout.select_dialog_singlechoice
                        , availableBluetoothPrinterNameList);
        new android.app.AlertDialog.Builder(activity)
                .setTitle("Select Printer")
                .setNegativeButton("Cancel", null)
                .setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {

                        StarIOPort starIOPort = null;
                        try {

                            starIOPort = StarIOPort.getPort(arrayAdapter.getItem(position), "mini", 10000);
                            if (starIOPort.retreiveStatus().offline) {

                                if (!starIOPort.retreiveStatus().compulsionSwitch) {

                                    showToast(activity, "The Drawer is offline\nCash Drawer: Close");
                                } else {

                                    showToast(activity, "The Drawer is offline\nCash Drawer: Open");
                                }

                                return;
                            } else {

                                if (starIOPort.retreiveStatus().compulsionSwitch) {

                                    showToast(activity, "The Drawer is online\nCash Drawer: Open");
                                } else {

                                    byte[] printDataByteArray =
                                            convertFromListByteArrayTobyteArray(
                                                    getPrintDataByteArrayListDeliver(
                                                            activity
                                                            , customerName
                                                            , orderInvoiceNo
                                                            , orderSaleManName
                                                            , invoiceNumber
                                                            , salePersonName
                                                            , townshipName
                                                            , invoice
                                                            , soldProductList
                                                            , presentList
                                                            , printFor
                                                            , mode));
                                    starIOPort.writePort(printDataByteArray, 0, printDataByteArray.length);
                                }
                            }
                        } catch (StarIOPortException e) {

                            showToast(activity, "Failed to connect to drawer");
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } finally {
                            if (starIOPort != null) {

                                try {

                                    StarIOPort.releasePort(starIOPort);
                                } catch (StarIOPortException e) {

                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                })
                .show();
    }

    private static void showToast(Activity activity, String message) {

        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    private static List<byte[]> getPrintDataByteArrayList(Activity activity, String customerName
            , String invoiceNumber, String salePersonName, String townshipName, Invoice invoice
            , List<SoldProduct> soldProductList, List<Promotion> presentList, String printFor, String mode) throws UnsupportedEncodingException {

        List<byte[]> printDataByteArrayList = new ArrayList<byte[]>();

        DecimalFormat decimalFormatterWithoutComma = new DecimalFormat("##0");
        DecimalFormat decimalFormatterWithComma = new DecimalFormat("###,##0");

        double totalAmount = 0, totalNetAmount = 0;

        String companyName = "";
        Cursor companyInfoCursor = database.rawQuery("SELECT " + DatabaseContract.CompanyInformation.CompanyName + " FROM " + DatabaseContract.CompanyInformation.tb, null);
        if(companyInfoCursor.moveToNext()) {
            companyName = companyInfoCursor.getString(companyInfoCursor.getColumnIndex(DatabaseContract.CompanyInformation.CompanyName));
        }
        companyName += " Purified Drinking Water & Soft Drink";
        String[] companyNames = companyName.split(" ");
        String names = "         ", fullName = "";
        for(String s : companyNames) {
            if(names.length() < 30) {
                names += s +" ";
            } else {
                fullName += (names + "         ");
                names = "\n         " + s;
            }
        }
        fullName += names;

        printDataByteArrayList.add((fullName + "\n\n").getBytes());
        printDataByteArrayList.add((
                "Customer       :     " + customerName + "\n").getBytes("UTF-8"));
        printDataByteArrayList.add((
                "Tsp            :     " + townshipName + "\n").getBytes("UTF-8"));
        printDataByteArrayList.add((
                "Invoice No     :     " + invoiceNumber + "\n").getBytes());
        printDataByteArrayList.add((
                "Sale Person    :     " + salePersonName + "\n").getBytes());
        printDataByteArrayList.add((
                "Sale Date      :     " + new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.US)
                        .format(new Date()) + "\n").getBytes());
        printDataByteArrayList.add("----------------------------------------------\n".getBytes());

        formatter = new Formatter(new StringBuilder(), Locale.US);
        printDataByteArrayList.add(
                formatter.format(
                        "%1$-10s \t %2$6s \t %3$5s \t %4$5s \t %5$7s\n"
                        , "Item"
                        , "Qty"
                        , "Price"
                        , "Pro:Price"
                        , "Amount").toString().getBytes());
        formatter.close();
        printDataByteArrayList.add("----------------------------------------------\n".getBytes());

        for (SoldProduct soldProduct : soldProductList) {

            String name = new String();
            int quantity = soldProduct.getQuantity();
            double pricePerUnit = 0.0, promoPrice = 0.0;

            //if(soldProduc0t.getPromotionPrice() == 0.0) {
                pricePerUnit = soldProduct.getProduct().getPrice();
            //} else {
                promoPrice = soldProduct.getPromotionPrice();
            //}

            double amount = soldProduct.getTotalAmount();
            double pricePerUnitWithDiscount;
            double netAmount;

            double discount = soldProduct.getDiscount(activity);

            pricePerUnitWithDiscount = soldProduct.getDiscountAmount();
            netAmount = soldProduct.getTotalAmount() - pricePerUnitWithDiscount;

            /*if (printFor.equals(Utils.PRINT_FOR_C)) {
                pricePerUnitWithDiscount = soldProduct.getDiscountAmount();
                netAmount = soldProduct.getTotalAmount() - pricePerUnitWithDiscount;
            } else {

                pricePerUnitWithDiscount =
                        (soldProduct.getTotalAmount() - soldProduct.getDiscountAmount(activity) - soldProduct.getExtraDiscountAmount())
                                / soldProduct.getQuantity();
                netAmount = soldProduct.getNetAmount(activity);
            }*/
//			if (printFor.equals(Utils.PRINT_FOR_C)
//					&& discount + soldProduct.getExtraDiscount() > 4) {
//
//				pricePerUnitWithDiscount =
//						(soldProduct.getTotalAmount() - soldProduct.getDiscountAmount(activity))
//							/ soldProduct.getQuantity();
//				netAmount = pricePerUnitWithDiscount * quantity;
//			} else {
//
//				pricePerUnitWithDiscount =
//						(soldProduct.getTotalAmount() - soldProduct.getDiscountAmount(activity) - soldProduct.getExtraDiscountAmount())
//							/ soldProduct.getQuantity();
//				netAmount = soldProduct.getNetAmount(activity);
//			}

            totalAmount += amount;
            totalNetAmount += netAmount;

            // Shorthand the name.
            //name = soldProduct.getProduct().getName();
            String[] nameFragments = soldProduct.getProduct().getName().split(" ");
            String lastIndex = nameFragments[nameFragments.length - 1];
            if(lastIndex.length() < 10) {
                int requiredSpace = 10 - lastIndex.length();
                for(int j = 0; j < requiredSpace; j++) {
                    nameFragments[nameFragments.length - 1] += " ";
                }
            }

            for(int i = 0; i < nameFragments.length; i++) {
                if(i != nameFragments.length -1) {
                    name += nameFragments[i] + "\n";
                } else if(i == nameFragments.length -1){
                    name += nameFragments[i];
                }
            }

            //String[] nameFragments = soldProduct.getProduct().getName().split("\\-|\\(");
            /*for (int i = 0; i < nameFragments.length; i++) {

                nameFragments[i] = nameFragments[i].replaceAll("\\s*", "").replaceAll("\\)", "");
                if (nameFragments[i].endsWith("MHZ") || nameFragments[i].endsWith("MHZ")) {

                    name += nameFragments[i].substring(0, 1);
                } else if (nameFragments[i].endsWith("KS") || nameFragments[i].endsWith("ks")) {

                    double price = Double.parseDouble(nameFragments[i]
                            .replace("KS", "")
                            .trim());
                    name += decimalFormatterWithoutComma.format(price / 1000) + "K";
                } else {

                    name += nameFragments[i].substring(0, 1);
                }
            }*/
            if (printFor.equals(Utils.PRINT_FOR_PRE_ORDER)) {
                formatter = new Formatter(new StringBuilder(), Locale.US);
                printDataByteArrayList.add(
                        formatter.format(
                                "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n\n"
                                , name
                                , quantity
                                , decimalFormatterWithoutComma.format(pricePerUnit)
                                , decimalFormatterWithoutComma.format(promoPrice)
                                , decimalFormatterWithComma.format(amount)).toString().getBytes());
                formatter.close();
            }

            if (!printFor.equals(Utils.PRINT_FOR_PRE_ORDER)) {

                formatter = new Formatter(new StringBuilder(), Locale.US);
                printDataByteArrayList.add(
                        formatter.format(
                                "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n\n"
                                , name
                                , quantity
                                , decimalFormatterWithoutComma.format(pricePerUnit)
                                , decimalFormatterWithoutComma.format(promoPrice)
                                , decimalFormatterWithComma.format(netAmount)).toString().getBytes());
                formatter.close();
            }
        }
        //printDataByteArrayList.add("----------------------------------------------\n".getBytes());

        if (presentList != null && presentList.size() > 0) {
            /*formatter = new Formatter(new StringBuilder(), Locale.US);

            printDataByteArrayList.add(
                    formatter.format(
                            "%1$-10s \t %2$6s \t %3$5s \t %4$13s \t %5$17s\n"
                            , "Gift Item"
                            , "Qty"
                            , "Price"
                            , "Amount").toString().getBytes());
            formatter.close();
            printDataByteArrayList.add("----------------------------------------------\n".getBytes());*/

             /* PRESENT LIST */
            for (Promotion invoicePresent : presentList) {
                {
                    String name = new String();
                    int quantity = invoicePresent.getPromotionQty();
                    double presentPrice = 0.0;
                    double promoPrice = 0.0;
                    // Shorthand the name.
                    String productName = getProductNameAndPrice(invoicePresent);

                    String[] nameFragments = productName.split(" ");

                    String lastIndex = nameFragments[nameFragments.length - 1];
                    if (lastIndex.length() < 10) {
                        int requiredSpace = 10 - lastIndex.length();
                        for (int j = 0; j < requiredSpace; j++) {
                            nameFragments[nameFragments.length - 1] += " ";
                        }
                    }

                    for (int i = 0; i < nameFragments.length; i++) {
                        if (i != nameFragments.length - 1) {
                            name += nameFragments[i] + "\n";
                        } else if (i == nameFragments.length - 1) {
                            name += nameFragments[i];
                        }
                    }

                    if (printFor.equals(Utils.PRINT_FOR_PRE_ORDER)) {
                        formatter = new Formatter(new StringBuilder(), Locale.US);
                        printDataByteArrayList.add(
                                formatter.format(
                                        "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n\n"
                                        , name
                                        , quantity
                                        , decimalFormatterWithComma.format(presentPrice)
                                        , decimalFormatterWithoutComma.format(promoPrice)
                                        , "0.0").toString().getBytes());
                        formatter.close();
                    }

                    if (!printFor.equals(Utils.PRINT_FOR_PRE_ORDER)) {

                        formatter = new Formatter(new StringBuilder(), Locale.US);
                        printDataByteArrayList.add(
                                formatter.format(
                                        "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n\n"
                                        , name
                                        , quantity
                                        , decimalFormatterWithComma.format(presentPrice)
                                        , decimalFormatterWithoutComma.format(promoPrice)
                                        , "0.0").toString().getBytes());
                        formatter.close();
                    }
                }
            }
            printDataByteArrayList.add("----------------------------------------------\n".getBytes());

        }
        /* END OF PRESENT LIST */

        formatter = new Formatter(new StringBuilder(), Locale.US);

        getTaxAmount();

        String taxText = "";
        if(taxType.equalsIgnoreCase("E")) {
            taxText = "(Tax " + invoice.getTaxAmount() +" Excluded)";
            totalNetAmount = invoice.getTotalAmt() - invoice.getTotalDiscountAmt() + invoice.getTaxAmount();
        } else {
            taxText = "(Tax " + invoice.getTaxAmount() +" Included)";
            totalNetAmount = invoice.getTotalAmt() - invoice.getTotalDiscountAmt();
        }

        if (printFor.equals(Utils.PRINT_FOR_PRE_ORDER)) {

            printDataByteArrayList.add(
                    formatter.format("%1$-13s%2$19s\n%3$-15s%4$17s\n%5$-13s%6$19s\n\n\n"
                            , "Total Amount    :", decimalFormatterWithComma.format(totalAmount)
                            , taxText, ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + taxPercent + "%)"*/
                            , "Discount        :", decimalFormatterWithComma.format(invoice.getTotalDiscountAmt()) + " (" + new DecimalFormat("#0.00").format(invoice.getDiscountPercent()) +"%)"
                            , "Net Amount  :", decimalFormatterWithComma.format(invoice.getTotalPayAmt())
                            , "Receive      :", decimalFormatterWithComma.format(invoice.getTotalPayAmt())).toString().getBytes());
        } else if (mode.equals(Utils.FOR_DELIVERY)) {

            printDataByteArrayList.add(
                    formatter.format("%1$-13s%2$19s\n%3$-13s%4$19s\n%5$-13s%6$19s\n%7$-13s%8$19s\n%9$-13s%10$19s\n\n\n"
                            , "Total Amount    :", decimalFormatterWithComma.format(totalAmount)
                            , taxText, ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + taxPercent + "%)"*/
                            , "Discount        :", decimalFormatterWithComma.format(invoice.getTotalDiscountAmt()) + " (" + new DecimalFormat("#0.00").format(invoice.getDiscountPercent()) +"%)"
                            , "Net Amount      :", decimalFormatterWithComma.format(totalNetAmount)
                            , "Pay Amount      :", decimalFormatterWithComma.format(invoice.getTotalPayAmt())).toString().getBytes());
        } else {
            Double crediBalance = 0.0;
            if(totalNetAmount > invoice.getTotalPayAmt()) {
                crediBalance = totalNetAmount - invoice.getTotalPayAmt();
            }

            printDataByteArrayList.add(
                    formatter.format("%1$-13s%2$19s\n%3$-13s%4$19s\n%5$-13s%6$19s\n%7$-13s%8$19s\n%9$-13s%10$19s\n%11$-13s%12$19s\n"
                            , "Total Amount    :        ", decimalFormatterWithComma.format(totalAmount)
                            , taxText, ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + new DecimalFormat("#0.00").format(taxPercent) + "%)"*/
                            , "Discount        :        ", decimalFormatterWithComma.format(invoice.getTotalDiscountAmt()) + " (" + new DecimalFormat("#0.00").format(invoice.getDiscountPercent()) +"%)"
                            , "Net Amount      :        ", decimalFormatterWithComma.format(totalNetAmount)
                            , "Receive         :        ", decimalFormatterWithComma.format(invoice.getTotalPayAmt())
                            , "Credit Balance  :        ", decimalFormatterWithComma.format(Math.abs(crediBalance))).toString().getBytes());
        }
        printDataByteArrayList.add(("\nSignature       :\n\n                 Thank You. \n\n").getBytes());
        printDataByteArrayList.add(new byte[]{0x1b, 0x64, 0x02}); // Cut
        printDataByteArrayList.add(new byte[]{0x07}); // Kick cash drawer

        return printDataByteArrayList;
    }

    private static List<byte[]> getPrintDataByteArrayListDeliver(Activity activity, String customerName, String orderInvoiceNo, String orderSaleManName
            , String invoiceNumber, String salePersonName, String townshipName, Invoice invoice
            , List<SoldProduct> soldProductList, List<Promotion> presentList, String printFor, String mode) throws UnsupportedEncodingException {

        List<byte[]> printDataByteArrayList = new ArrayList<byte[]>();

        DecimalFormat decimalFormatterWithoutComma = new DecimalFormat("##0");
        DecimalFormat decimalFormatterWithComma = new DecimalFormat("###,##0");

        double totalAmount = 0, totalNetAmount = 0;

        String companyName = "";
        Cursor companyInfoCursor = database.rawQuery("SELECT " + DatabaseContract.CompanyInformation.CompanyName + " FROM " + DatabaseContract.CompanyInformation.tb, null);
        if(companyInfoCursor.moveToNext()) {
            companyName = companyInfoCursor.getString(companyInfoCursor.getColumnIndex(DatabaseContract.CompanyInformation.CompanyName));
        }

        String[] companyNames = companyName.split(" ");
        String names = "        ", fullName = "";
        for(String s : companyNames) {
            if(names.length() < 30) {
                names += " " + s;
            } else {
                fullName += (names + "         ");
                names = "\n         " + s;
            }
        }
        fullName += names;

        printDataByteArrayList.add((fullName + "\n\n").getBytes());
        printDataByteArrayList.add((
                "Customer           :     " + customerName + "\n").getBytes("UTF-8"));
        printDataByteArrayList.add((
                "Tsp                :     " + townshipName + "\n").getBytes("UTF-8"));
        printDataByteArrayList.add((
                "Order Invoice      :     " + orderInvoiceNo + "\n").getBytes());
        printDataByteArrayList.add((
                "Order Person       :     " + orderSaleManName + "\n").getBytes());
        printDataByteArrayList.add((
                "Delivery No        :     " + invoiceNumber + "\n").getBytes());
        printDataByteArrayList.add((
                "Delivery Person    :     " + salePersonName + "\n").getBytes());
        printDataByteArrayList.add((
                "Delivery Date      :     " + new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.US)
                        .format(new Date()) + "\n").getBytes());
        printDataByteArrayList.add("----------------------------------------------\n".getBytes());

        formatter = new Formatter(new StringBuilder(), Locale.US);
        printDataByteArrayList.add(
                formatter.format(
                        "%1$-10s \t %2$6s \t %3$5s \t %4$5s \t %5$7s\n"
                        , "Item"
                        , "Qty"
                        , "Price"
                        , "Pro:Price"
                        , "Amount").toString().getBytes());
        formatter.close();
        printDataByteArrayList.add("----------------------------------------------\n".getBytes());

        for (SoldProduct soldProduct : soldProductList) {

            String name = new String();
            int quantity = soldProduct.getQuantity();
            double pricePerUnit = 0.0, promoPrice = 0.0;

            pricePerUnit = soldProduct.getProduct().getPrice();
            promoPrice = soldProduct.getPromotionPrice();
            //}

            double amount = soldProduct.getTotalAmount();
            double pricePerUnitWithDiscount;
            double netAmount;

            double discount = soldProduct.getDiscount(activity);

            pricePerUnitWithDiscount = soldProduct.getDiscountAmount();
            netAmount = soldProduct.getTotalAmount() - pricePerUnitWithDiscount;

            totalAmount += amount;
            totalNetAmount += netAmount;

            // Shorthand the name.
            String[] nameFragments = soldProduct.getProduct().getName().split(" ");
            String lastIndex = nameFragments[nameFragments.length - 1];
            if(lastIndex.length() < 10) {
                int requiredSpace = 10 - lastIndex.length();
                for(int j = 0; j < requiredSpace; j++) {
                    nameFragments[nameFragments.length - 1] += " ";
                }
            }

            for(int i = 0; i < nameFragments.length; i++) {
                if(i != nameFragments.length -1) {
                    name += nameFragments[i] + "\n";
                } else if(i == nameFragments.length -1){
                    name += nameFragments[i];
                }
            }

            if (printFor.equals(Utils.PRINT_FOR_PRE_ORDER)) {
                formatter = new Formatter(new StringBuilder(), Locale.US);
                printDataByteArrayList.add(
                        formatter.format(
                                "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n\n"
                                , name
                                , quantity
                                , decimalFormatterWithoutComma.format(pricePerUnit)
                                , decimalFormatterWithoutComma.format(promoPrice)
                                , decimalFormatterWithComma.format(amount)).toString().getBytes());
                formatter.close();
            }

            if (!printFor.equals(Utils.PRINT_FOR_PRE_ORDER)) {

                formatter = new Formatter(new StringBuilder(), Locale.US);
                printDataByteArrayList.add(
                        formatter.format(
                                "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n\n"
                                , name
                                , quantity
                                , decimalFormatterWithoutComma.format(pricePerUnit)
                                , decimalFormatterWithoutComma.format(promoPrice)
                                , decimalFormatterWithComma.format(netAmount)).toString().getBytes());
                formatter.close();
            }
        }
        //printDataByteArrayList.add("----------------------------------------------\n".getBytes());

        if (presentList != null && presentList.size() > 0) {

             /* PRESENT LIST */
            for (Promotion invoicePresent : presentList) {
                {
                    String name = new String();
                    int quantity = invoicePresent.getPromotionQty();
                    double presentPrice = 0.0;
                    double promoPrice = 0.0;
                    // Shorthand the name.
                    String productName = getProductNameAndPrice(invoicePresent);

                    String[] nameFragments = productName.split(" ");

                    String lastIndex = nameFragments[nameFragments.length - 1];
                    if (lastIndex.length() < 10) {
                        int requiredSpace = 10 - lastIndex.length();
                        for (int j = 0; j < requiredSpace; j++) {
                            nameFragments[nameFragments.length - 1] += " ";
                        }
                    }

                    for (int i = 0; i < nameFragments.length; i++) {
                        if (i != nameFragments.length - 1) {
                            name += nameFragments[i] + "\n";
                        } else if (i == nameFragments.length - 1) {
                            name += nameFragments[i];
                        }
                    }

                    if (printFor.equals(Utils.PRINT_FOR_PRE_ORDER)) {
                        formatter = new Formatter(new StringBuilder(), Locale.US);
                        printDataByteArrayList.add(
                                formatter.format(
                                        "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n\n"
                                        , name
                                        , quantity
                                        , decimalFormatterWithComma.format(presentPrice)
                                        , decimalFormatterWithoutComma.format(promoPrice)
                                        , "0.0").toString().getBytes());
                        formatter.close();
                    }

                    if (!printFor.equals(Utils.PRINT_FOR_PRE_ORDER)) {

                        formatter = new Formatter(new StringBuilder(), Locale.US);
                        printDataByteArrayList.add(
                                formatter.format(
                                        "%1$-10s \t %2$6s \t %3$5s \t %4$6s \t %5$9s\n\n"
                                        , name
                                        , quantity
                                        , decimalFormatterWithComma.format(presentPrice)
                                        , decimalFormatterWithoutComma.format(promoPrice)
                                        , "0.0").toString().getBytes());
                        formatter.close();
                    }
                }
            }
            printDataByteArrayList.add("----------------------------------------------\n".getBytes());

        }
        /* END OF PRESENT LIST */

        formatter = new Formatter(new StringBuilder(), Locale.US);

        getTaxAmount();

        String taxText = "";
        if(taxType.equalsIgnoreCase("E")) {
            taxText = "(Tax " + invoice.getTaxAmount() +" Excluded)";
            totalNetAmount = invoice.getTotalAmt() - invoice.getTotalDiscountAmt() + invoice.getTaxAmount();
        } else {
            taxText = "(Tax " + invoice.getTaxAmount() +" Included)";
            totalNetAmount = invoice.getTotalAmt() - invoice.getTotalDiscountAmt();
        }

        if (printFor.equals(Utils.PRINT_FOR_PRE_ORDER)) {

            printDataByteArrayList.add(
                    formatter.format("%1$-13s%2$19s\n%3$-15s%4$17s\n%5$-13s%6$19s\n\n\n"
                            , "Total Amount    :", decimalFormatterWithComma.format(totalAmount)
                            , taxText, ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + taxPercent + "%)"*/
                            , "Discount        :", decimalFormatterWithComma.format(invoice.getTotalDiscountAmt()) + " (" + new DecimalFormat("#0.00").format(invoice.getDiscountPercent()) +"%)"
                            , "Net Amount  :", decimalFormatterWithComma.format(invoice.getTotalPayAmt())
                            , "Receive      :", decimalFormatterWithComma.format(invoice.getTotalPayAmt())).toString().getBytes());
        } else if (mode.equals(Utils.FOR_DELIVERY)) {

            printDataByteArrayList.add(
                    formatter.format("%1$-13s%2$19s\n%3$-13s%4$19s\n%5$-13s%6$19s\n%7$-13s%8$19s\n%9$-13s%10$19s\n\n\n"
                            , "Total Amount    :", decimalFormatterWithComma.format(totalAmount)
                            , taxText, ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + taxPercent + "%)"*/
                            , "Discount        :", decimalFormatterWithComma.format(invoice.getTotalDiscountAmt()) + " (" + new DecimalFormat("#0.00").format(invoice.getDiscountPercent()) +"%)"
                            , "Net Amount      :", decimalFormatterWithComma.format(totalNetAmount)
                            , "Pay Amount      :", decimalFormatterWithComma.format(invoice.getTotalPayAmt())).toString().getBytes());
        } else {
            Double crediBalance = 0.0;
            if(totalNetAmount > invoice.getTotalPayAmt()) {
                crediBalance = totalNetAmount - invoice.getTotalPayAmt();
            }

            printDataByteArrayList.add(
                    formatter.format("%1$-13s%2$19s\n%3$-13s%4$19s\n%5$-13s%6$19s\n%7$-13s%8$19s\n%9$-13s%10$19s\n%11$-13s%12$19s\n"
                            , "Total Amount    :        ", decimalFormatterWithComma.format(totalAmount)
                            , taxText, ""/*decimalFormatterWithComma.format(invoice.getTaxAmount()) + " (" + new DecimalFormat("#0.00").format(taxPercent) + "%)"*/
                            , "Discount        :        ", decimalFormatterWithComma.format(invoice.getTotalDiscountAmt()) + " (" + new DecimalFormat("#0.00").format(invoice.getDiscountPercent()) +"%)"
                            , "Net Amount      :        ", decimalFormatterWithComma.format(totalNetAmount)
                            , "Receive         :        ", decimalFormatterWithComma.format(invoice.getTotalPayAmt())
                            , "Credit Balance  :        ", decimalFormatterWithComma.format(Math.abs(crediBalance))).toString().getBytes());
        }
        printDataByteArrayList.add(("\nSignature       :\n\n                 Thank You. \n\n").getBytes());
        printDataByteArrayList.add(new byte[]{0x1b, 0x64, 0x02}); // Cut
        printDataByteArrayList.add(new byte[]{0x07}); // Kick cash drawer

        return printDataByteArrayList;
    }

    private static byte[] convertFromListByteArrayTobyteArray(List<byte[]> ByteArray) throws UnsupportedEncodingException {
        int dataLength = 0;
        for (int i = 0; i < ByteArray.size(); i++) {
            dataLength += ByteArray.get(i).length;
        }

        int distPosition = 0;
        byte[] byteArray = new byte[dataLength];
        for (int i = 0; i < ByteArray.size(); i++) {
            System.arraycopy(ByteArray.get(i), 0, byteArray, distPosition, ByteArray.get(i).length);
            distPosition += ByteArray.get(i).length;
        }

        return byteArray;
    }

    static void getTaxAmount() {
        Cursor cursorTax = database.rawQuery("SELECT TaxType, Tax FROM COMPANYINFORMATION", null);
        while(cursorTax.moveToNext()) {
            taxType = cursorTax.getString(cursorTax.getColumnIndex("TaxType"));
            taxPercent = cursorTax.getDouble(cursorTax.getColumnIndex("Tax"));
        }
    }

    public static void backupDatabase(Context context) {
        String today = Utils.getCurrentDate(true);

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                Toast.makeText(context, "Backup database is starting...",
                        Toast.LENGTH_SHORT).show();
                String currentDBPath = "/data/com.aceplus.samparoo/databases/aceplus-dms.sqlite";

                String backupDBPath = "Samparoo_DB_Backup_" + today + ".db";
                File currentDB = new File(data, currentDBPath);

                String folderPath = "mnt/sdcard/Samparoo_DB_Backup";
                File f = new File(folderPath);
                f.mkdir();
                File backupDB = new File(f, backupDBPath);
                FileChannel source = new FileInputStream(currentDB).getChannel();
                FileChannel destination = new FileOutputStream(backupDB).getChannel();
                destination.transferFrom(source, 0, source.size());
                source.close();
                destination.close();
                Toast.makeText(context, "Backup database Successful!",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Please set Permission for Storage in Setting!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "Cannot Backup!", Toast.LENGTH_SHORT).show();
        }
    }

    static String getProductNameAndPrice(Promotion invoicePresent) {
        Cursor cursorProductName = database.rawQuery("SELECT PRODUCT_NAME, SELLING_PRICE FROM PRODUCT WHERE ID = " + invoicePresent.getPromotionProductId(), null);
        String productName = null;
        while(cursorProductName.moveToNext()) {
            productName = cursorProductName.getString(cursorProductName.getColumnIndex("PRODUCT_NAME"));
            invoicePresent.setPrice(cursorProductName.getDouble(cursorProductName.getColumnIndex("SELLING_PRICE")));
        }
        return productName;
    }

    private static List<byte[]> getPrintDataByteArrayListForCredit(Activity activity, String customerName, String invoiceNumber, String townShipName, String salePersonName, CreditInvoice creditInvoice) {
        List<byte[]> printDataByteArrayList = new ArrayList<byte[]>();

        //DecimalFormat decimalFormatterWithoutComma = new DecimalFormat("##0");
        DecimalFormat decimalFormatterWithComma = new DecimalFormat("###,##0");

        //double totalAmount = 0, totalNetAmount = 0;
        String companyName = "";
        Cursor companyInfoCursor = database.rawQuery("SELECT " + DatabaseContract.CompanyInformation.CompanyName + " FROM " + DatabaseContract.CompanyInformation.tb, null);
        if(companyInfoCursor.moveToNext()) {
            companyName = companyInfoCursor.getString(companyInfoCursor.getColumnIndex(DatabaseContract.CompanyInformation.CompanyName));
        }

        String[] companyNames = companyName.split(" ");
        String names = "        ", fullName = "";
        for(String s : companyNames) {
            if(names.length() < 30) {
                names += " " + s;
            } else {
                fullName += (names + "         ");
                names = "\n         " + s;
            }
        }
        fullName += names;

        printDataByteArrayList.add((fullName + "\n\n").getBytes());
        printDataByteArrayList.add((
                "Customer           :     " + customerName + "\n").getBytes());
        printDataByteArrayList.add((
                "Tsp                :     " + townShipName + "\n").getBytes());
        printDataByteArrayList.add((
                "Offical Receive No :     " + invoiceNumber + "\n").getBytes());
        printDataByteArrayList.add((
                "Collect Person     :     " + salePersonName + "\n").getBytes());
        printDataByteArrayList.add((
                "Total Amount       :     " + decimalFormatterWithComma.format(creditInvoice.getAmt()) + "\n").getBytes());

        printDataByteArrayList.add((
                "Discount           :     " + "0.0 (0%)" + "\n").getBytes());

        printDataByteArrayList.add((
                "Net Amount         :     " + decimalFormatterWithComma.format(creditInvoice.getAmt()) + "\n").getBytes());

        printDataByteArrayList.add((
                "Receive            :     " + decimalFormatterWithComma.format(creditInvoice.getPayAmt()) + "\n").getBytes());
        printDataByteArrayList.add((
                "\nSignature          :\n\n                 Thank You. \n\n").getBytes());

        printDataByteArrayList.add(new byte[]{0x1b, 0x64, 0x02}); // Cut
        printDataByteArrayList.add(new byte[]{0x07}); // Kick cash drawer

        return printDataByteArrayList;
    }


    public static boolean checkDuplicateInvoice(String invoiceNo, String tableName, String columnName) {
        Cursor duplicateCursor = database.rawQuery("SELECT COUNT(*) AS COUNT FROM " + tableName + " WHERE " + columnName + " = '" + invoiceNo +"'", null);
        int count = 0;

        if(duplicateCursor.moveToNext()) {
           count = duplicateCursor.getInt(duplicateCursor.getColumnIndex("COUNT"));
        }

        if(count > 0) {
            return false;
        } else {
            return true;
        }
    }

    public static void saveInvoiceImageIntoGallery(String invoiceNo, Context context, Bitmap bitmap, String directoryName)
    {
        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File (sdCard.getAbsolutePath() + "/ScreenShot/" + directoryName);
        directory.mkdirs();

        String filename =  invoiceNo + ".jpg";
        File yourFile = new File(directory, filename);
        String time = getCurrentDate(true);

        while(yourFile.exists()) {
            filename = invoiceNo + "_" + time + ".jpg";
            yourFile = new File(directory, filename);
            break;
        }

        String image=yourFile.toString();
        Log.e(image, "ImageOOO");


        FileOutputStream fos;
        try {
            fos = new FileOutputStream(yourFile, true);
            bitmap.compress(Bitmap.CompressFormat.PNG, 40, fos);//change 100 to 40

            fos.flush();
            fos.close();
            Toast.makeText(context, "Image saved to /sdcard/ScreenShot/" + directoryName + filename + ".jpg", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }

        Bitmap bitmapOrg1= BitmapFactory.decodeFile(image);
        ByteArrayOutputStream bao1 = new ByteArrayOutputStream();
        Log.e(bitmapOrg1.toString()+"","BitMap1");
        Log.e(bao1.toString(),"BAO");
        bitmapOrg1.compress(Bitmap.CompressFormat.JPEG, 40, bao1); //change 90 to 40

        Log.e("here1", "here1");
        byte [] ba1 = bao1.toByteArray();

        String imgBase64Str=Base64.encodeToString(ba1, Base64.NO_WRAP);
        Log.e("ImageBase64String",imgBase64Str.toString()+ "aa");
    }
}
