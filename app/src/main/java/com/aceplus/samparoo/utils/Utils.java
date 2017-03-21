package com.aceplus.samparoo.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;

import com.aceplus.samparoo.CustomerVisitActivity;
import com.aceplus.samparoo.HomeActivity;
import com.aceplus.samparoo.MarketingActivity;
import com.aceplus.samparoo.customer.CustomerActivity;
import com.aceplus.samparoo.marketing.MainFragmentActivity;
import com.aceplus.samparoo.model.forApi.LoginRequest;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public static void callDialog(String message, Activity activity) {
        progressDialog = new ProgressDialog(activity, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public static void cancelDialog() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }

    public static void commonDialog(String message, Activity activity) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
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
            Cursor cursor = database.rawQuery("SELECT COUNT(*) AS COUNT FROM DISPLAY_ASSESSMENT", null);
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

}
