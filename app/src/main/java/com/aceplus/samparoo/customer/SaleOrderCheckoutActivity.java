package com.aceplus.samparoo.customer;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aceplus.samparoo.LoginActivity;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.SyncActivity;
import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.model.Deliver;
import com.aceplus.samparoo.model.DeliverItem;
import com.aceplus.samparoo.model.PreOrder;
import com.aceplus.samparoo.model.PreOrderProduct;
import com.aceplus.samparoo.model.Promotion;
import com.aceplus.samparoo.model.SoldProduct;
import com.aceplus.samparoo.model.forApi.DeliveryApi;
import com.aceplus.samparoo.model.forApi.DeliveryItemApi;
import com.aceplus.samparoo.model.forApi.InvoiceResponse;
import com.aceplus.samparoo.model.forApi.PreOrderApi;
import com.aceplus.samparoo.model.forApi.PreOrderDetailApi;
import com.aceplus.samparoo.model.forApi.PreOrderPresentApi;
import com.aceplus.samparoo.model.forApi.PreOrderRequest;
import com.aceplus.samparoo.model.forApi.PreOrderRequestData;
import com.aceplus.samparoo.myinterface.OnActionClickListener;
import com.aceplus.samparoo.retrofit.RetrofitServiceFactory;
import com.aceplus.samparoo.retrofit.UploadService;
import com.aceplus.samparoo.utils.Constant;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;
import com.aceplus.samparoo.utils.Database;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by phonelin on 2/10/17.
 */
public class SaleOrderCheckoutActivity extends AppCompatActivity implements OnActionClickListener {

    public static final int REQUEST_SEND_SMS = 101;
    public static final String REMAINING_AMOUNT_KEY = "remaining-amount-key";
    public static final String IS_PRE_ORDER = "is-pre-order";
    public static final String IS_DELIVERY = "is-delivery";
    public static final String CUSTOMER_INFO_KEY = "customer-info-key";
    public static final String SOLD_PROUDCT_LIST_KEY = "sold-product-list-key";
    public static final String PRESENT_PROUDCT_LIST_KEY = "presnet-product-list-key";
    public static final String ORDERED_INVOICE_KEY = "ordered_invoice_key";

    /**
     * remaining amount
     */
    private double remainingAmount;

    /**
     * customer in
     */
    private Customer customer;

    SQLiteDatabase database;

    private boolean isPreOrder;

    private boolean isDelivery, access;

    private ArrayList<SoldProduct> soldProductList = new ArrayList<SoldProduct>();

    //for present product
    private ArrayList<String> products = new ArrayList<String>();

    private Deliver orderedInvoice;

    private ImageView img_back, img_confirmAndPrint;

    private TextView txt_invoiceId, txt_totalAmount, saleDateTextView, netAmountTextView;

    private TextView txt_tableHeaderDiscount, txt_tableHeaderUM, txt_tableHeaderQty, txt_table_header_foc;

    private LinearLayout advancedPaidAmountLayout, totalInfoForGeneralSaleLayout, totalInfoForPreOrderLayout, receiptPersonLayout, refundLayout, payAmountLayout, netAmountLayout, volumeDiscountLayout, volDisForPreOrderLayout;

    private LinearLayout paymentMethodLayout;

    private EditText prepaidAmt, receiptPersonEditText;

    private ListView lv_soldProductList, promotionPlanItemListView;

    private String saleman_Id = "", saleman_No = "", saleman_Pwd = "", phoneNo = "";

    TextView titleTextView;

    ArrayList<Promotion> promotionArrayList = new ArrayList<>();
    PromotionProductCustomAdapter promotionProductCustomAdapter;

    Double totalVolumeDiscount = 0.0;
    int exclude = 0;
    double totalAmount = 0.0, totalAmountForProduct = 0.0, netAmtTotal = 0.0;

    int locationCode = 0;
    String locationCodeName = "";

    double totalAmountForVolumeDiscount = 0.0;

    TextView volDisForPreOrder;

    RadioGroup bankOrCashRadioGroup;
    RadioButton bankRadio, cashRadio;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_checkout);
        database = new Database(this).getDataBase();

        Utils.setOnActionClickListener(this);

        saleman_Id = LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, "");
        saleman_No = LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NO, "");
        saleman_Pwd = LoginActivity.mySharedPreference.getString(Constant.SALEMAN_PWD, "");

        this.remainingAmount = getIntent().getDoubleExtra(this.REMAINING_AMOUNT_KEY, 0);
        this.customer = (Customer) getIntent().getSerializableExtra(this.CUSTOMER_INFO_KEY);
        this.isPreOrder = getIntent().getBooleanExtra(SaleOrderActivity.IS_PRE_ORDER, false);
        this.isDelivery = getIntent().getBooleanExtra(SaleOrderActivity.IS_DELIVERY, false);
        soldProductList = (ArrayList<SoldProduct>) getIntent().getSerializableExtra(this.SOLD_PROUDCT_LIST_KEY);
        promotionArrayList = (ArrayList<Promotion>) getIntent().getSerializableExtra(PRESENT_PROUDCT_LIST_KEY);

        if (getIntent().getSerializableExtra(this.ORDERED_INVOICE_KEY) != null) {
            orderedInvoice = (Deliver) getIntent().getSerializableExtra(this.ORDERED_INVOICE_KEY);
        }
        /*try {
            if (getIntent().getStringExtra(this.ORDERED_INVOICE_KEY) != null) {

                orderedInvoice = new JSONObject(getIntent().getStringExtra(this.ORDERED_INVOICE_KEY));
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }*/

        registerIDs();
        txt_tableHeaderQty.setText(R.string.ordered_qty);
        titleTextView.setText("SALE ORDER CHECKOUT");

        for (SoldProduct soldProduct : soldProductList) {

            totalAmount += soldProduct.getTotalAmount();
        }
        txt_totalAmount.setText(Utils.formatAmount(totalAmount));

        Cursor cursorForLocation = database.rawQuery("select * from Location", null);
        while (cursorForLocation.moveToNext()) {
            locationCode = cursorForLocation.getInt(cursorForLocation.getColumnIndex(DatabaseContract.Location.id));
            locationCodeName = cursorForLocation.getString(cursorForLocation.getColumnIndex(DatabaseContract.Location.no));
        }


        if(isDelivery) {
            titleTextView.setText(R.string.delivery_checkout);
        }
        hideUnnecessaryView();
        setSoldProductToListView();

        calculateNetAmount();

        showPromotionData();
        catchEvents();
    }

    void calculateNetAmount() {
        double totalAmountForVolumeDiscount = 0.0;
        double totalItemDiscountAmount = 0.0;
        for (SoldProduct soldProduct : soldProductList) {

//			totalAmount += soldProduct.getTotalAmount();
            if (soldProduct.getProduct().getDiscountType().equalsIgnoreCase("v")) {

                totalAmountForVolumeDiscount += soldProduct.getTotalAmount();
            }
            totalItemDiscountAmount += soldProduct.getDiscountAmount(this) + soldProduct.getExtraDiscountAmount();
        }

        txt_totalAmount.setText(Utils.formatAmount(totalAmount));

        setSoldProductInformation();

        calculateVolumeDiscount();
        calculateInvoiceDiscount();

        netAmtTotal = totalAmount - totalItemDiscountAmount - totalVolumeDiscount;
        netAmountTextView.setText(Utils.formatAmount(netAmtTotal));
    }

    /**
     * Register all view ids that are related to this activity
     */
    private void registerIDs() {
        titleTextView = (TextView) findViewById(R.id.title);
        img_back = (ImageView) findViewById(R.id.back_img);
        img_confirmAndPrint = (ImageView) findViewById(R.id.confirmAndPrint_img);
        txt_tableHeaderUM = (TextView) findViewById(R.id.tableHeaderUM);
        txt_tableHeaderQty = (TextView) findViewById(R.id.tableHeaderQty);
        txt_tableHeaderDiscount = (TextView) findViewById(R.id.tableHeaderDiscount);
        txt_table_header_foc = (TextView) findViewById(R.id.tableHeaderFoc);

        saleDateTextView = (TextView) findViewById(R.id.saleDateTextView);
        txt_invoiceId = (TextView) findViewById(R.id.invoiceId);
        txt_totalAmount = (TextView) findViewById(R.id.totalAmount);

        advancedPaidAmountLayout = (LinearLayout) findViewById(R.id.advancedPaidAmountLayout);
        totalInfoForGeneralSaleLayout =(LinearLayout) findViewById(R.id.totalInfoForGeneralSale);
        totalInfoForPreOrderLayout =(LinearLayout) findViewById(R.id.totalInfoForPreOrder);
        refundLayout = (LinearLayout) findViewById(R.id.refundLayout);
        payAmountLayout = (LinearLayout) findViewById(R.id.payAmountLayout);
        netAmountLayout = (LinearLayout) findViewById(R.id.netAmountLayout);
        volumeDiscountLayout = (LinearLayout) findViewById(R.id.volumeDiscountLayout2);
        receiptPersonLayout = (LinearLayout) findViewById(R.id.layout_receipt_person);
        prepaidAmt = (EditText) findViewById(R.id.prepaidAmount);
        receiptPersonEditText = (EditText) findViewById(R.id.receiptPerson);
        lv_soldProductList = (ListView) findViewById(R.id.soldProductList);
        netAmountTextView = (TextView) findViewById(R.id.netAmount);
        promotionPlanItemListView = (ListView) findViewById(R.id.promotion_plan_item_listview);
        volDisForPreOrderLayout = (LinearLayout) findViewById(R.id.volDisForPreOrderLayout);
        volDisForPreOrder = (TextView) findViewById(R.id.volumeDiscount);
        paymentMethodLayout = (LinearLayout) findViewById(R.id.paymentMethodLayout);
        bankOrCashRadioGroup = (RadioGroup) findViewById(R.id.activity_sale_checkout_radio_group);
        bankRadio = (RadioButton) findViewById(R.id.activity_sale_checkout_radio_bank);
        cashRadio = (RadioButton) findViewById(R.id.activity_sale_checkout_radio_cash);
    }

    /**
     * Hide views that are not related.
     */
    private void hideUnnecessaryView() {
        txt_tableHeaderUM.setVisibility(View.GONE);
        txt_tableHeaderDiscount.setVisibility(View.GONE);
        advancedPaidAmountLayout.setVisibility(View.GONE);
        netAmountLayout.setVisibility(View.VISIBLE);
        refundLayout.setVisibility(View.GONE);
        payAmountLayout.setVisibility(View.GONE);
        receiptPersonLayout.setVisibility(View.GONE);
        volDisForPreOrderLayout.setVisibility(View.GONE);
        if(isDelivery) {
            totalInfoForPreOrderLayout.setVisibility(View.GONE);
            volumeDiscountLayout.setVisibility(View.GONE);
            volDisForPreOrderLayout.setVisibility(View.GONE);
            advancedPaidAmountLayout.setVisibility(View.VISIBLE);
            receiptPersonLayout.setVisibility(View.VISIBLE);
            txt_table_header_foc.setVisibility(View.VISIBLE);
            paymentMethodLayout.setVisibility(View.GONE);
        }
    }

    /**
     * Set data to list view.
     */
    private void setSoldProductToListView() {
        lv_soldProductList.setAdapter(new SoldProductCustomAdapter(this));
    }

    private void setSoldProductInformation() {
        saleDateTextView.setText(Utils.getCurrentDate(false));
        txt_invoiceId.setText(Utils.getInvoiceNo(this, LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NO, ""), locationCode + "", Utils.forPreOrderSale));
    }

    private void calculateVolumeDiscount() {
        String volDisFilterId = "";
        String category = "", group = "";
        double discount_percent = 0.0, discount_amt = 0.0, discount_price = 0.0;

        String categoryProduct = "", groupProduct = "";

        boolean isAllAreCategory = false;

        double buy_amt = totalAmount;
        Cursor cursor = database.rawQuery("select * from VOLUME_DISCOUNT_FILTER WHERE '" + Utils.getCurrentDate(true) + "' BETWEEN START_DATE AND END_DATE", null);
        Log.i("VolumeDisFilterCursor", cursor.getCount() + "");
        while (cursor.moveToNext()) {
            volDisFilterId = cursor.getString(cursor.getColumnIndex(DatabaseContract.VolumeDiscountFilter.id));
            exclude = cursor.getInt(cursor.getColumnIndex(DatabaseContract.VolumeDiscountFilter.exclude));
            if (exclude == 0) {

                for (SoldProduct soldProduct : soldProductList) {
                    buy_amt = soldProduct.getProduct().getPrice() * soldProduct.getQuantity();
                }

               /* double promotion_price = 0.0;
                for (Promotion promotion : promotionArrayList) {
                    promotion_price += promotion.getPromotionPrice();
                }
                buy_amt += promotion_price;*/
            }
            Log.i("buy_amt", buy_amt + "");
            Log.i("volDisFilterId", volDisFilterId);
            Cursor cusorForVolDisFilterItem = database.rawQuery("SELECT * FROM VOLUME_DISCOUNT_FILTER_ITEM WHERE VOLUME_DISCOUNT_ID = '" + volDisFilterId + "' " +
                    "and FROM_SALE_AMOUNT <= " + buy_amt + " and TO_SALE_AMOUNT >= " + buy_amt + " ", null);
            Log.i("cursorForVolDisFilterItem", cusorForVolDisFilterItem.getCount() + "");
            while (cusorForVolDisFilterItem.moveToNext()) {
                category = cusorForVolDisFilterItem.getString(cusorForVolDisFilterItem.getColumnIndex(DatabaseContract.VolumeDiscountFilterItem.categoryId));
                group = cusorForVolDisFilterItem.getString(cusorForVolDisFilterItem.getColumnIndex(DatabaseContract.VolumeDiscountFilterItem.groupCodeId));
                discount_percent = cusorForVolDisFilterItem.getDouble(cusorForVolDisFilterItem.getColumnIndex(DatabaseContract.VolumeDiscountFilterItem.discountPercent));
            }

            Log.i("category", category + ", group : " + group + ", discount_percent : " + discount_percent);
        }

        //check category and group for product
        for (SoldProduct soldProduct : soldProductList) {
            Cursor cursorForProduct = database.rawQuery("select * from PRODUCT WHERE PRODUCT_ID = '" + soldProduct.getProduct().getId() + "'", null);
            while (cursorForProduct.moveToNext()) {
                categoryProduct = String.valueOf(cursorForProduct.getInt(cursorForProduct.getColumnIndex("CATEGORY_ID")));
                groupProduct = cursorForProduct.getString(cursorForProduct.getColumnIndex("GROUP_ID"));
            }

            if (category.equals(categoryProduct)) {
                isAllAreCategory = true;
            } else {
                isAllAreCategory = false;
            }
        }

        if (isAllAreCategory) {
            totalVolumeDiscount = totalAmount * (discount_percent / 100);
            Log.i("totalVolumeDiscount", totalVolumeDiscount + "");
        }

        volDisForPreOrder.setText(Utils.formatAmount(totalVolumeDiscount));
    }

    private void calculateInvoiceDiscount() {
        String volDisId;
        double buy_amt = totalAmount;
        Double fromSaleAmt, toSaleAmt, discountPercentForVolDis;

        Cursor cursor = database.rawQuery("select * from VOLUME_DISCOUNT WHERE '" + Utils.getCurrentDate(true) + "' BETWEEN START_DATE AND END_DATE", null);
        Log.i("VolumeDiscountCursor", cursor.getCount() + "");
        while (cursor.moveToNext()) {
            volDisId = cursor.getString(cursor.getColumnIndex(DatabaseContract.VolumeDiscount.id));
            exclude = cursor.getInt(cursor.getColumnIndex(DatabaseContract.VolumeDiscount.exclude));

            if (exclude == 0) {
                for (SoldProduct soldProduct : soldProductList) {
                    buy_amt = soldProduct.getProduct().getPrice() * soldProduct.getQuantity();
                }

               /* double promotion_price = 0.0;
                for (Promotion promotion : promotionArrayList) {
                    promotion_price += promotion.getPromotionPrice();
                }
                buy_amt += promotion_price;*/
            }


            Log.i("buy_amt", buy_amt + "");
            Log.i("volDisId", volDisId);

            Cursor cusorForVolDisItem = database.rawQuery("SELECT * FROM VOLUME_DISCOUNT_ITEM WHERE VOLUME_DISCOUNT_ID = '" + volDisId + "' " +
                    "and " + buy_amt + " >= FROM_SALE_AMT and "+ buy_amt +"<= TO_SALE_AMT;", null);
            Log.i("cusorForVolDisItem", cusorForVolDisItem.getCount() + "");

            while (cusorForVolDisItem.moveToNext()) {
                fromSaleAmt = cusorForVolDisItem.getDouble(cusorForVolDisItem.getColumnIndex(DatabaseContract.VolumeDiscountItem.fromSaleAmt));
                toSaleAmt = cusorForVolDisItem.getDouble(cusorForVolDisItem.getColumnIndex(DatabaseContract.VolumeDiscountItem.toSaleAmt));
                discountPercentForVolDis = cusorForVolDisItem.getDouble(cusorForVolDisItem.getColumnIndex(DatabaseContract.VolumeDiscountItem.discountPercent));

                totalVolumeDiscount = totalAmount * (discountPercentForVolDis / 100);
            }

            Log.i("totalInvoiceDiscount ----->>>>>>>", totalVolumeDiscount + "");
        }

        volDisForPreOrder.setText(Utils.formatAmount(totalVolumeDiscount));
    }

    private void showPromotionData() {

        Cursor cursor = database.rawQuery("select * from " + DatabaseContract.PromotionDate.tb + "", null);
        Log.i("cursor", cursor.getCount() + "");
        while (cursor.moveToNext()) {
            String promotionPlanId = "";

            promotionPlanId = cursor.getString(cursor.getColumnIndex(DatabaseContract.PromotionDate.promotionPlanId));
            Log.i("promotionPlanId", promotionPlanId);

            for (SoldProduct soldProduct : soldProductList) {

                String promotionProductId = "";
                String promotionProductName = "";
                int promotionProductQty = 0;
                double promotionPrice = 0.0;

                int buy_qty = soldProduct.getProduct().getSoldQty();
                String stock_id_old = soldProduct.getProduct().getId();
                Log.i("stock_id_old", stock_id_old);
                String stock_id_new = "";
                Cursor cursorForStockId = database.rawQuery("select * from PRODUCT where PRODUCT_ID = '" + stock_id_old + "'", null);
                while (cursorForStockId.moveToNext()) {
                    stock_id_new = cursorForStockId.getString(cursorForStockId.getColumnIndex("ID"));
                    Log.i("stock_id_new", stock_id_new + "");
                }
                Cursor cursorForPromotionPrice = database.rawQuery("select * from " + DatabaseContract.PromotionPrice.tb + " where " + DatabaseContract.PromotionPrice.promotionPlanId + " = '" + promotionPlanId + "'" +
                        " and " + DatabaseContract.PromotionPrice.fromQuantity + " <= " + buy_qty + " and " + DatabaseContract.PromotionPrice.toQuantity + " >= " + buy_qty + " and " + DatabaseContract.PromotionPrice.stockId + " = '" + stock_id_new + "'", null);
                Log.i("PriceCount", cursorForPromotionPrice.getCount() + "");
                while (cursorForPromotionPrice.moveToNext()) {
                    promotionPrice = cursorForPromotionPrice.getDouble(cursorForPromotionPrice.getColumnIndex(DatabaseContract.PromotionPrice.promotionPrice));
                }
                Log.i("promotionPrice", promotionPrice + "");
                Cursor cursorForPromotionGift = database.rawQuery("select * from " + DatabaseContract.PromotionGift.tb + " where " + DatabaseContract.PromotionGift.promotionPlanId + " = '" + promotionPlanId + "'" +
                        " and " + DatabaseContract.PromotionGift.fromQuantity + " <= " + buy_qty + " and " + DatabaseContract.PromotionGift.toQuantity + " >= " + buy_qty + " and " + DatabaseContract.PromotionGift.stockId + " = '" + stock_id_new + "'", null);
                Log.i("GiftCount", cursorForPromotionGift.getCount() + "");
                while (cursorForPromotionGift.moveToNext()) {
                    String promotionGiftId = cursorForPromotionGift.getString(cursorForPromotionGift.getColumnIndex(DatabaseContract.PromotionGift.promotionPlanId));
                    Log.i("promotionGiftId", promotionGiftId + "");
                    Cursor cursorForPromotionGiftItem = database.rawQuery("select * from " + DatabaseContract.PromotionGiftItem.tb + " where " + DatabaseContract.PromotionGiftItem.promotionPlanId + " = '" + promotionGiftId + "'", null);
                    while (cursorForPromotionGiftItem.moveToNext()) {
                        promotionProductId = cursorForPromotionGiftItem.getString(cursorForPromotionGiftItem.getColumnIndex(DatabaseContract.PromotionGiftItem.stockId));
                        Log.i("promotionProductId", promotionProductId + "");
                        Cursor cursorForProductName = database.rawQuery("select * from PRODUCT WHERE ID = '" + promotionProductId + "'", null);
                        while (cursorForProductName.moveToNext()) {
                            promotionProductName = cursorForProductName.getString(cursorForProductName.getColumnIndex("PRODUCT_NAME"));
                            Log.i("promotionProductName", promotionProductName + ">>not null");
                        }
                        promotionProductQty = cursorForPromotionGiftItem.getInt(cursorForPromotionGiftItem.getColumnIndex(DatabaseContract.PromotionGiftItem.quantity));
                    }
                }

                Promotion promotion = new Promotion();
                promotion.setPromotionPrice(promotionPrice);
                promotion.setPromotionProductId(promotionProductId);
                promotion.setPromotionProductName(promotionProductName);
                promotion.setPromotionQty(promotionProductQty);

                /*if (promotionPrice != 0.0) {
                    promotion.setPromotionPrice(promotionPrice);
                }
                if (promotionProductId != null || !promotionProductId.equals("")) {
                    promotion.setPromotionProductId(promotionProductId);
                }
                if (!promotionProductName.equals("") || promotionProductName != null) {
                    promotion.setPromotionProductName(promotionProductName);
                }
                if (promotionProductQty != 0) {
                    promotion.setPromotionQty(promotionProductQty);
                }*/
                if (promotion.getPromotionQty() != 0 && !promotion.getPromotionProductName().equals("")) {
                    promotionArrayList.add(promotion);
                }
            }
        }

        setPromotionProductListView();
    }

    private void setPromotionProductListView() {
        int itemLength = promotionArrayList.size() * 100;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, itemLength);
        params.setMargins(20, 0, 0, 20);
        promotionPlanItemListView.setLayoutParams(params);

        promotionProductCustomAdapter = new PromotionProductCustomAdapter(this);
        promotionPlanItemListView.setAdapter(promotionProductCustomAdapter);
        promotionProductCustomAdapter.notifyDataSetChanged();
    }

    /**
     * Check internet access in the device.
     *
     * @return true : if there is internet access, otherwise return false.
     */
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 www.google.com.mm");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }

    /**
     * Request Sending SMS permission to user.
     */
    void sendSMSMessage() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        REQUEST_SEND_SMS);
            }
        } else {
            showDialogForPhoneNumber();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showDialogForPhoneNumber();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

    private void insertSMSRecord(String phoneNo, String msg) {
        database.beginTransaction();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.SMS_RECORD.SEND_DATE, new Date().toString());
        cv.put(DatabaseContract.SMS_RECORD.MSG_BODY, msg);
        cv.put(DatabaseContract.SMS_RECORD.PHONE_NO, phoneNo);
        database.insert(DatabaseContract.SMS_RECORD.TABLE, null, cv);
        database.setTransactionSuccessful();
        database.endTransaction();
    }
    /**
     * Send SMS to the phone
     *
     * @param phoneNumber phone number
     * @param message message body
     */
    private void sendSMS(String phoneNumber, String message) {
        ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
        ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(this, SmsSentReceiver.class), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(this, SmsDeliveredReceiver.class), 0);
        try {
            SmsManager sms = SmsManager.getDefault();
            ArrayList<String> mSMSMessage = sms.divideMessage(message);
            for (int i = 0; i < mSMSMessage.size(); i++) {
                sentPendingIntents.add(i, sentPI);
                deliveredPendingIntents.add(i, deliveredPI);
            }

            sms.sendMultipartTextMessage(phoneNumber, null, mSMSMessage, sentPendingIntents, deliveredPendingIntents);
        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(getBaseContext(), "SMS sending failed...",Toast.LENGTH_SHORT).show();
        }

    }

    private String getMessageInfo() {
        String message = "";
        List<PreOrder> preOrderList = getPreOrderFromDatabase();

        List<PreOrderApi> preOrderApiList = new ArrayList<>();

        List<PreOrderPresentApi> preOrderPresentApiList = new ArrayList<>();

        for(PreOrder preOrder : preOrderList) {

            message += "Sale Order Invoice Number : " + preOrder.getInvoiceId()
                    + "\nCustomer Number : " + preOrder.getCustomerId()
                    + "\nSale Person Number : " + preOrder.getSalePersonId()
                    + "\nSale Order Date : " + preOrder.getPreOrderDate()
                    + "\nAdvanced Payment Amount : " + preOrder.getAdvancedPaymentAmount();

            for (Promotion promotion : promotionArrayList) {
                PreOrderPresentApi preOrderPresentApi = new PreOrderPresentApi();
                preOrderPresentApi.setSaleOrderId(preOrder.getInvoiceId());
                preOrderPresentApi.setProductId(Integer.parseInt(promotion.getPromotionProductId()));
                preOrderPresentApi.setQuantity(promotion.getPromotionQty());

                message += "\nPresent Product Stock Number : " + promotion.getPromotionProductId()
                        + "\nPresent Product Quantity : " + promotion.getPromotionQty();

                preOrderPresentApiList.add(preOrderPresentApi);
            }

            List<PreOrderProduct> preOrderProductList = getPreOrderProductFromDatabase(preOrder.getInvoiceId());

            List<PreOrderDetailApi> preOrderDetailApiList = new ArrayList<>();
            for(PreOrderProduct preOrderProduct : preOrderProductList) {

                message += "\nSale Order Stock Number : " + preOrderProduct.getProductId()
                        + "\nOrder Quantity : " + preOrderProduct.getOrderQty();

            }

            message += "\n";
        }
        return message;
    }

    /**
     * Action to events
     */
    private void catchEvents() {

        // back image (cross btn) event listener
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaleOrderCheckoutActivity.this.onBackPressed();
            }
        });

        // confirm image (nike btn) event listener
        img_confirmAndPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.askConfirmationDialog("Save", "Do you want to confirm?", "", SaleOrderCheckoutActivity.this);
            }
        });

        // prepaid amount edit text event listener
        prepaidAmt.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int arg1, int arg2, int arg3) {

                if (charSequence.toString().length() > 0) {

                    String convertedString = charSequence.toString();
                    convertedString = Utils.formatAmount(Double.parseDouble(charSequence.toString().replace(",", "")));
                    if (!prepaidAmt.getText().toString().equals(convertedString)
                            && convertedString.length() > 0) {

                        prepaidAmt.setText(convertedString);
                        prepaidAmt.setSelection(prepaidAmt.getText().length());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void showDialogForPhoneNumber() {

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.dialog_box_sale_quantity, null);

        LinearLayout availableQuantityLayout = (LinearLayout) view.findViewById(R.id.availableQuantityLayout);
        TextView qtyTextView = (TextView) view.findViewById(R.id.dialog_sale_qty_txtView);

        availableQuantityLayout.setVisibility(View.GONE);
        qtyTextView.setVisibility(View.GONE);

        final EditText phoneNoEditText = (EditText) view.findViewById(R.id.quantity);
        final TextView messageTextView = (TextView) view.findViewById(R.id.message);

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .setTitle("No internet access !\nPlease enter Phone Number to send message")
                .setPositiveButton("Confirm", null)
                .setNegativeButton("Cancel", null)
                .create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface arg0) {

                view.findViewById(R.id.availableQuantityLayout).setVisibility(View.GONE);

                Button confirmButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                confirmButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                        if (phoneNoEditText.getText().toString().length() == 0) {

                            messageTextView.setText("You must specify Phone Number.");
                            return;
                        }

                        phoneNo = phoneNoEditText.getText().toString();
                        String msg = getMessageInfo();

                        if(!phoneNo.equals("")) {
                            sendSMS(phoneNo, msg);
                            insertSMSRecord(phoneNo, msg);
                        }

                        alertDialog.dismiss();
                        Utils.backToCustomer(SaleOrderCheckoutActivity.this);
                    }
                });
            }
        });
        alertDialog.show();

    }

    /**
     * Insert pre-order to database.
     */
    void insertPreOrderInformation(String cashOrBank) {
        double netAmount = 0;

        PreOrder preOrder = new PreOrder();

        if(SaleOrderCheckoutActivity.this.txt_invoiceId.getText() != null) {
            preOrder.setInvoiceId(SaleOrderCheckoutActivity.this.txt_invoiceId.getText().toString());
        }

        if(SaleOrderCheckoutActivity.this.customer != null) {
            preOrder.setCustomerId(String.valueOf(SaleOrderCheckoutActivity.this.customer.getId()));
        }

        preOrder.setSalePersonId(LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, ""));
        //preOrder.setDeviceId(Utils.getDeviceId(SaleOrderCheckoutActivity.this));
        preOrder.setDeviceId("");
        preOrder.setPreOrderDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 7);    // Number of days to add.

        preOrder.setExpectedDeliveryDate(new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime()));

        double advancedPaymentAmount = prepaidAmt.getText().length() > 0 ?
                Double.parseDouble(prepaidAmt.getText().toString().replace(",", ""))
                : 0;

        preOrder.setAdvancedPaymentAmount(advancedPaymentAmount);

        List<PreOrderProduct> preOrderProductList = new ArrayList<>();

        for(SoldProduct soldProduct : soldProductList ) {
            netAmount += soldProduct.getTotalAmount();

            PreOrderProduct preOrderProduct = new PreOrderProduct();
            preOrderProduct.setSaleOrderId(preOrder.getInvoiceId());
            preOrderProduct.setProductId(soldProduct.getProduct().getStockId());
            preOrderProduct.setOrderQty(soldProduct.getQuantity());
            preOrderProduct.setPrice(soldProduct.getProduct().getPrice());
            preOrderProduct.setTotalAmt(soldProduct.getTotalAmount());
            preOrderProductList.add(preOrderProduct);

            database.execSQL("UPDATE PRODUCT SET REMAINING_QTY = REMAINING_QTY - " + soldProduct.getQuantity()
                    + ", SOLD_QTY = SOLD_QTY + " + soldProduct.getQuantity() + " WHERE PRODUCT_ID = \'" + soldProduct.getProduct().getId() + "\'");

            for (Promotion promotion : promotionArrayList) {
                database.execSQL("UPDATE PRODUCT SET PRESENT_QTY = PRESENT_QTY + " + promotion.getPromotionQty() + " WHERE PRODUCT_ID = \'" + soldProduct.getProduct().getId() + "\'");
                database.execSQL("UPDATE PRODUCT SET REMAINING_QTY = REMAINING_QTY - " + promotion.getPromotionQty() + " WHERE ID = '" + promotion.getPromotionProductId() + "'");
            }
        }

        preOrder.setNetAmount(netAmount);
        preOrder.setLocationId(locationCode);
        preOrder.setDiscount(0.0);
        preOrder.setDiscountPer(0.0);
        preOrder.setVolumeDiscount(totalVolumeDiscount);
        preOrder.setVolumeDiscountPer(0.0);

        database.beginTransaction();

        /*database.execSQL("INSERT INTO PRE_ORDER VALUES (\""
                + preOrder.getInvoiceId() + "\", \""
                + preOrder.getCustomerId() + "\", \""
                + preOrder.getSalePersonId() + "\", \""
                + preOrder.getDeviceId() + "\", \""
                + preOrder.getPreOrderDate() + "\", \""
                + preOrder.getExpectedDeliveryDate() + "\", "
                + preOrder.getAdvancedPaymentAmount() + ", \""
                + preOrder.getNetAmount() + "\""
                + ")");*/

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.PreOrder.invoice_id, preOrder.getInvoiceId());
        contentValues.put(DatabaseContract.PreOrder.customer_id, preOrder.getCustomerId());
        contentValues.put(DatabaseContract.PreOrder.saleperson_id, preOrder.getSalePersonId());
        contentValues.put(DatabaseContract.PreOrder.dev_id, preOrder.getDeviceId());
        contentValues.put(DatabaseContract.PreOrder.preorder_date, preOrder.getPreOrderDate());
        contentValues.put(DatabaseContract.PreOrder.expected_delivery_date, preOrder.getExpectedDeliveryDate());
        contentValues.put(DatabaseContract.PreOrder.advance_payment_amount, preOrder.getAdvancedPaymentAmount());
        contentValues.put(DatabaseContract.PreOrder.net_amount, preOrder.getNetAmount());
        contentValues.put(DatabaseContract.PreOrder.location_id, preOrder.getLocationId());
        contentValues.put(DatabaseContract.PreOrder.discount, preOrder.getDiscount());
        contentValues.put(DatabaseContract.PreOrder.discount_per, preOrder.getDiscountPer());
        contentValues.put(DatabaseContract.PreOrder.volume_discount, preOrder.getVolumeDiscount());
        contentValues.put(DatabaseContract.PreOrder.volume_discount_per, preOrder.getVolumeDiscountPer());

        database.insert(DatabaseContract.PreOrder.tb, null, contentValues);

        insertProductList(preOrderProductList);

        for (Promotion promotion : promotionArrayList) {
            ContentValues contentValues1 = new ContentValues();
            contentValues1.put("pre_order_id", preOrder.getInvoiceId());
            contentValues1.put("stock_id", promotion.getPromotionProductId());
            contentValues1.put("quantity", promotion.getPromotionQty());
            contentValues1.put("pc_address", preOrder.getDeviceId());
            contentValues1.put("location_id", locationCode);
            contentValues1.put("price", promotion.getPromotionPrice());
            contentValues1.put("DELETE_FLAG", 0);
            database.insert("PRE_ORDER_PRESENT", null, contentValues1);
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    /**
     * Insert pre order product to database.
     *
     * @param preOrderProductList pre order product list
     */
    private void insertProductList(List<PreOrderProduct> preOrderProductList) {
        for (PreOrderProduct preOrderProduct : preOrderProductList) {
            /*database.execSQL("INSERT INTO PRE_ORDER_PRODUCT VALUES (\""
                    + preOrderProduct.getSaleOrderId() + "\", \""
                    + preOrderProduct.getProductId() + "\", \""
                    + preOrderProduct.getOrderQty() + "\", \""
                    + preOrderProduct.getPrice() + "\", \""
                    + preOrderProduct.getTotalAmt() + "\""
                    + ")");*/

            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.PreOrderDetail.sale_order_id, preOrderProduct.getSaleOrderId());
            contentValues.put(DatabaseContract.PreOrderDetail.product_id, preOrderProduct.getProductId());
            contentValues.put(DatabaseContract.PreOrderDetail.order_qty, preOrderProduct.getOrderQty());
            contentValues.put(DatabaseContract.PreOrderDetail.price, preOrderProduct.getPrice());
            contentValues.put(DatabaseContract.PreOrderDetail.total_amt, preOrderProduct.getTotalAmt());
            contentValues.put(DatabaseContract.PreOrderDetail.promotion_price, preOrderProduct.getPromotionPrice());
            contentValues.put(DatabaseContract.PreOrderDetail.volume_discount, totalVolumeDiscount);
            contentValues.put(DatabaseContract.PreOrderDetail.volume_discount_per, 0.0);
            contentValues.put(DatabaseContract.PreOrderDetail.exclude, 1);

            database.insert(DatabaseContract.PreOrderDetail.tb, null, contentValues);

        }
    }

    /**
     * Upload pre order data to server
     */
    private void uploadPreOrderToServer() {

        Utils.callDialog("Please wait...", SaleOrderCheckoutActivity.this);

        final PreOrderRequest preOrderRequest = getPreOrderRequest();

        String paramData = getJsonFromObject(preOrderRequest);

        Log.i("ParamData",paramData);

        UploadService uploadService = RetrofitServiceFactory.createRealTimeService(UploadService.class);

        Call<InvoiceResponse> call = uploadService.uploadPreOrderData(paramData);

        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        //Toast.makeText(SaleOrderCheckoutActivity.this, response.body().getAceplusStatusMessage(), Toast.LENGTH_SHORT).show();

                        database.beginTransaction();

                        if(preOrderRequest.getData() != null && preOrderRequest.getData().get(0).getData().size() > 0) {
                            updateDeleteFlag(DatabaseContract.PreOrder.tb, 1, DatabaseContract.PreOrder.invoice_id, preOrderRequest.getData().get(0).getData().get(0).getId());
                            updateDeleteFlag(DatabaseContract.PreOrderDetail.tb, 1, DatabaseContract.PreOrderDetail.sale_order_id, preOrderRequest.getData().get(0).getData().get(0).getId());

                            /*deletePreOrderAfterUpload(preOrderRequest.getData().get(0).getData().get(0).getId());
                            deletePreOrderProductAfterUpload(preOrderRequest.getData().get(0).getData().get(0).getId());*/
                        }

                        database.setTransactionSuccessful();
                        database.endTransaction();
                        Utils.cancelDialog();

                        Snackbar snackbar = Snackbar
                                .make(lv_soldProductList, R.string.upload_succes_msg, Snackbar.LENGTH_INDEFINITE)
                                .setAction("DONE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Utils.backToCustomer(SaleOrderCheckoutActivity.this);
                                    }
                                });

                        snackbar.show();
                    }
                } else {
                    if(response.body() != null && response.body().getAceplusStatusMessage().length() != 0 ) {
                        onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                    } else {
                        Utils.cancelDialog();
                        Utils.commonDialog(getResources().getString(R.string.server_error), SaleOrderCheckoutActivity.this);
                    }
                }
            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SaleOrderCheckoutActivity.this);
            }
        });
    }

    /**
     * Upload pre order data for real time
     */
    private void uploadPreOrderRealTime() {

        final PreOrderRequest preOrderRequest = getPreOrderRequest();

        String paramData = getJsonFromObject(preOrderRequest);

        Log.i("ParamData",paramData);

        UploadService uploadService = RetrofitServiceFactory.createRealTimeService(UploadService.class);

        Call<InvoiceResponse> call = uploadService.uploadRealTimePreOrderData(paramData);

        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        database.beginTransaction();

                        if(preOrderRequest.getData() != null && preOrderRequest.getData().get(0).getData().size() > 0) {
                            updateDeleteFlag(DatabaseContract.PreOrder.tb, 1, DatabaseContract.PreOrder.invoice_id, preOrderRequest.getData().get(0).getData().get(0).getId());
                            updateDeleteFlag(DatabaseContract.PreOrderDetail.tb, 1, DatabaseContract.PreOrderDetail.sale_order_id, preOrderRequest.getData().get(0).getData().get(0).getId());
                        }

                        database.setTransactionSuccessful();
                        database.endTransaction();
                        Utils.cancelDialog();

                        Snackbar snackbar = Snackbar
                                .make(lv_soldProductList, R.string.upload_succes_msg, Snackbar.LENGTH_INDEFINITE)
                                .setAction("DONE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Utils.backToCustomer(SaleOrderCheckoutActivity.this);
                                    }
                                });

                        snackbar.show();
                    }
                } else {
                    if(response.body() != null && response.body().getAceplusStatusMessage().length() != 0 ) {
                        onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                    } else {
                        Utils.cancelDialog();
                        Utils.commonDialog(getResources().getString(R.string.server_error), SaleOrderCheckoutActivity.this);
                    }
                }
            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SaleOrderCheckoutActivity.this);
            }
        });
    }

    /**
     * Transform preOrderRequest to json.
     *
     * @param preOrderRequest PreOrderRequest
     * @return preOrderRequest pre order object for api request
     */
    private String getJsonFromObject(PreOrderRequest preOrderRequest) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonString = gson.toJson(preOrderRequest);
        return jsonString;
    }

    /**
     * Get all related data for pre order from database.
     *
     * @return pre order object for api request
     */
    private PreOrderRequest getPreOrderRequest() {
        List<PreOrder> preOrderList = getPreOrderFromDatabase();

        List<PreOrderApi> preOrderApiList = new ArrayList<>();

        List<PreOrderPresentApi> preOrderPresentApiList = new ArrayList<>();

        for(PreOrder preOrder : preOrderList) {
            PreOrderApi preOrderApi = new PreOrderApi();
            preOrderApi.setId(preOrder.getInvoiceId());
            preOrderApi.setCustomerId(Integer.parseInt(preOrder.getCustomerId()));
            preOrderApi.setSaleManId(preOrder.getSalePersonId());
            preOrderApi.setDeviceId(preOrder.getDeviceId());
            preOrderApi.setSaleOrderDate(preOrder.getPreOrderDate());
            preOrderApi.setExpectedDeliveredDate(preOrder.getExpectedDeliveryDate());
            preOrderApi.setAdvancedPaymentAmt(preOrder.getAdvancedPaymentAmount());
            preOrderApi.setNetAmt(preOrder.getNetAmount());
            preOrderApi.setLocationId(preOrder.getLocationId());
            preOrderApi.setDiscount(preOrder.getDiscount());
            preOrderApi.setDiscountPer(preOrder.getDiscountPer());
            preOrderApi.setVolumeDiscount(preOrder.getVolumeDiscount());
            preOrderApi.setVolumeDiscountPer(preOrder.getVolumeDiscountPer());

            for (Promotion promotion : promotionArrayList) {
                PreOrderPresentApi preOrderPresentApi = new PreOrderPresentApi();
                preOrderPresentApi.setSaleOrderId(preOrder.getInvoiceId());
                preOrderPresentApi.setProductId(Integer.parseInt(promotion.getPromotionProductId()));
                preOrderPresentApi.setQuantity(promotion.getPromotionQty());

                preOrderPresentApiList.add(preOrderPresentApi);
            }

            List<PreOrderProduct> preOrderProductList = getPreOrderProductFromDatabase(preOrder.getInvoiceId());

            List<PreOrderDetailApi> preOrderDetailApiList = new ArrayList<>();
            for(PreOrderProduct preOrderProduct : preOrderProductList) {
                PreOrderDetailApi preOrderDetailApi = new PreOrderDetailApi();
                preOrderDetailApi.setSaleOrderId(preOrderProduct.getSaleOrderId());
                preOrderDetailApi.setProductId(preOrderProduct.getProductId());
                preOrderDetailApi.setQty(preOrderProduct.getOrderQty());
                preOrderDetailApi.setPromotionPrice(preOrderProduct.getPromotionPrice());
                preOrderDetailApi.setVolumeDiscount(preOrderProduct.getVolumeDiscount());
                preOrderDetailApi.setVolumeDiscountPer(preOrderProduct.getVolumeDiscountPer());
                preOrderDetailApi.setExclude(preOrderProduct.getExclude());
                preOrderDetailApiList.add(preOrderDetailApi);
            }

            preOrderApi.setPreOrderDetailList(preOrderDetailApiList);
            preOrderApiList.add(preOrderApi);
        }

        List<PreOrderRequestData> preOrderRequestDataList = new ArrayList<>();

        PreOrderRequestData preOrderRequestData = new PreOrderRequestData();
        preOrderRequestData.setData(preOrderApiList);
        preOrderRequestData.setPreorderPresent(preOrderPresentApiList);
        preOrderRequestDataList.add(preOrderRequestData);

        preOrderRequestData.setData(preOrderApiList);

        PreOrderRequest preOrderRequest = new PreOrderRequest();
        preOrderRequest.setSiteActivationKey(Constant.SITE_ACTIVATION_KEY);
        preOrderRequest.setTabletActivationKey(Constant.TABLET_ACTIVATION_KEY);
        preOrderRequest.setUserId(saleman_Id);
        preOrderRequest.setSalemanId(saleman_Id);
        preOrderRequest.setPassword("");
        preOrderRequest.setRoute(String.valueOf(getRouteID(saleman_Id)));
        preOrderRequest.setData(preOrderRequestDataList);

        return preOrderRequest;
    }

    /**
     * Retrieve pre order from database.
     *
     * @return PreOrder object list
     */
    private List<PreOrder> getPreOrderFromDatabase() {
        List<PreOrder> preOrderList = new ArrayList<>();

        Cursor cursorPreOrder = database.rawQuery("select P.* from PRE_ORDER AS P WHERE P.DELETE_FLAG = 0", null);

        while (cursorPreOrder.moveToNext()) {
            PreOrder preOrder = new PreOrder();
            preOrder.setInvoiceId(cursorPreOrder.getString(cursorPreOrder.getColumnIndex("INVOICE_ID")));
            preOrder.setCustomerId(cursorPreOrder.getString(cursorPreOrder.getColumnIndex("CUSTOMER_ID")));
            preOrder.setSalePersonId(cursorPreOrder.getString(cursorPreOrder.getColumnIndex("SALEPERSON_ID")));
            preOrder.setDeviceId(cursorPreOrder.getString(cursorPreOrder.getColumnIndex("DEV_ID")));
            preOrder.setPreOrderDate(cursorPreOrder.getString(cursorPreOrder.getColumnIndex("PREORDER_DATE")));
            preOrder.setExpectedDeliveryDate(cursorPreOrder.getString(cursorPreOrder.getColumnIndex("EXPECTED_DELIVERY_DATE")));
            preOrder.setAdvancedPaymentAmount(cursorPreOrder.getDouble(cursorPreOrder.getColumnIndex("ADVANCE_PAYMENT_AMOUNT")));
            preOrder.setNetAmount(cursorPreOrder.getDouble(cursorPreOrder.getColumnIndex("NET_AMOUNT")));
            preOrder.setLocationId(cursorPreOrder.getInt(cursorPreOrder.getColumnIndex(DatabaseContract.PreOrder.location_id)));
            preOrder.setDiscount(cursorPreOrder.getDouble(cursorPreOrder.getColumnIndex(DatabaseContract.PreOrder.discount)));
            preOrder.setDiscountPer(cursorPreOrder.getDouble(cursorPreOrder.getColumnIndex(DatabaseContract.PreOrder.discount_per)));
            preOrder.setVolumeDiscount(cursorPreOrder.getDouble(cursorPreOrder.getColumnIndex(DatabaseContract.PreOrder.volume_discount)));
            preOrder.setVolumeDiscountPer(cursorPreOrder.getDouble(cursorPreOrder.getColumnIndex(DatabaseContract.PreOrder.volume_discount_per)));
            preOrderList.add(preOrder);
        }

        return preOrderList;
    }

    /**
     * Retrieve pre order product from database.
     *
     * @return PreOrderProduct object list
     */
    private List<PreOrderProduct> getPreOrderProductFromDatabase(String saleOrderId) {
        List<PreOrderProduct> preOrderProductList = new ArrayList<>();

        Cursor cursorPreOrderProduct = database.rawQuery("select * from PRE_ORDER_PRODUCT WHERE SALE_ORDER_ID = \'" + saleOrderId + "\' AND DELETE_FLAG = 0;", null);

        while (cursorPreOrderProduct.moveToNext()) {
            PreOrderProduct preOrderProduct = new PreOrderProduct();
            preOrderProduct.setSaleOrderId(cursorPreOrderProduct.getString(cursorPreOrderProduct.getColumnIndex("SALE_ORDER_ID")));
            preOrderProduct.setProductId(cursorPreOrderProduct.getInt(cursorPreOrderProduct.getColumnIndex("PRODUCT_ID")));
            preOrderProduct.setOrderQty(cursorPreOrderProduct.getInt(cursorPreOrderProduct.getColumnIndex("ORDER_QTY")));
            preOrderProduct.setPrice(cursorPreOrderProduct.getDouble(cursorPreOrderProduct.getColumnIndex("PRICE")));
            preOrderProduct.setTotalAmt(cursorPreOrderProduct.getDouble(cursorPreOrderProduct.getColumnIndex("TOTAL_AMT")));
            preOrderProduct.setPromotionPrice(cursorPreOrderProduct.getDouble(cursorPreOrderProduct.getColumnIndex(DatabaseContract.PreOrderDetail.promotion_price)));
            preOrderProduct.setVolumeDiscount(cursorPreOrderProduct.getDouble(cursorPreOrderProduct.getColumnIndex(DatabaseContract.PreOrderDetail.volume_discount)));
            preOrderProduct.setVolumeDiscountPer(cursorPreOrderProduct.getDouble(cursorPreOrderProduct.getColumnIndex(DatabaseContract.PreOrderDetail.volume_discount_per)));
            preOrderProduct.setExclude(cursorPreOrderProduct.getInt(cursorPreOrderProduct.getColumnIndex(DatabaseContract.PreOrderDetail.exclude)));
            preOrderProductList.add(preOrderProduct);
        }

        return preOrderProductList;
    }

    /**
     * Delete pre order after uploading to server.
     * @param invoiceId invoice id
     */
    private void deletePreOrderAfterUpload(String invoiceId) {
        database.execSQL("delete from PRE_ORDER WHERE INVOICE_ID = \'" + invoiceId + "\'");
    }

    private void updateDeleteFlag(String tableName, int deleteFlg, String columnName, String columnValue) {
        String query = "UPDATE " + tableName + " SET DELETE_FLAG = " + deleteFlg + " WHERE " + columnName + " = \'" + columnValue + "'";
        database.execSQL(query);
    }

    /**
     * Delete pre order product after uploading to server.
     * @param invoiceId invoice id
     */
    private void deletePreOrderProductAfterUpload(String invoiceId) {
        database.execSQL("delete from PRE_ORDER_PRODUCT WHERE SALE_ORDER_ID = \'" + invoiceId + "\'");
    }

    /**
     * Get route id related to sale man id.
     *
     * @param saleman_Id sale man id
     * @return route id
     */
    private int getRouteID(String saleman_Id) {
        int routeID = 0;
        Cursor cursor = database.rawQuery("select * from " + DatabaseContract.RouteScheduleItem.tb + " where " +
                DatabaseContract.RouteScheduleItem.saleManID + " = '" + saleman_Id + "' ", null);
        while (cursor.moveToNext()) {
            routeID = cursor.getInt(cursor.getColumnIndex(DatabaseContract.RouteScheduleItem.routeID));
        }
        Log.i("routeID>>>", routeID + "");
        return routeID;
    }

    @Override
    public void onBackPressed() {

        if(isPreOrder) {
            Intent intent = new Intent(SaleOrderCheckoutActivity.this
                    , SaleOrderActivity.class);
            intent.putExtra(SaleOrderActivity.REMAINING_AMOUNT_KEY
                    , SaleOrderCheckoutActivity.this.remainingAmount);
            intent.putExtra(SaleOrderActivity.CUSTOMER_INFO_KEY
                    , SaleOrderCheckoutActivity.this.customer);
            intent.putExtra(SaleOrderActivity.SOLD_PROUDCT_LIST_KEY
                    , SaleOrderCheckoutActivity.this.soldProductList);
            intent.putExtra(SaleOrderCheckoutActivity.PRESENT_PROUDCT_LIST_KEY
                    , SaleOrderCheckoutActivity.this.promotionArrayList);
            intent.putExtra(SaleOrderActivity.IS_PRE_ORDER
                    , SaleOrderCheckoutActivity.this.isPreOrder);
            intent.putExtra(SaleOrderActivity.IS_DELIVERY
                    , SaleOrderCheckoutActivity.this.isDelivery);
            startActivity(intent);
            finish();
        } else if(isDelivery) {
            toDeliveryActivity();
        }

        super.onBackPressed();
    }

    private void toDeliveryActivity() {
        Intent intent = new Intent(SaleOrderCheckoutActivity.this
                , DeliveryActivity.class);
        intent.putExtra(FragmentDeliveryReport.CUSTOMER_INFO_KEY
                , SaleOrderCheckoutActivity.this.customer);
        intent.putExtra(FragmentDeliveryReport.SOLD_PROUDCT_LIST_KEY
                , SaleOrderCheckoutActivity.this.soldProductList);
        intent.putExtra(FragmentDeliveryReport.IS_DELIVERY
                , SaleOrderCheckoutActivity.this.isDelivery);
        intent.putExtra(FragmentDeliveryReport.ORDERED_INVOICE_KEY
                , SaleOrderCheckoutActivity.this.orderedInvoice);
        startActivity(intent);
        finish();
    }

    private void insertDeliveryDataToDatabase(Deliver deliver) {

        String saleDate = Utils.getCurrentDate(true);
        String invoiceId = Utils.getInvoiceNo(SaleOrderCheckoutActivity.this, LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NO, ""), String.valueOf(locationCode), Utils.FOR_DELIVERY);
        double totalAmount = deliver.getAmount();
        double paidAmount = deliver.getPaidAmount();
        String salePersonId = LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, "");
        String dueDate = Utils.getCurrentDate(true);
        String invoiceTime = Utils.getCurrentDate(true);
        database.beginTransaction();
        int totalQuantity = insertDeliveryDataItemToDatabase(soldProductList, invoiceId);

        database.execSQL("INSERT INTO INVOICE VALUES (\""
                + customer.getId() + "\", \""
                + saleDate + "\", \""
                + invoiceId + "\", \""
                + totalAmount + "\", \""
                + "0.0" + "\", \""
                + paidAmount + "\", \""
                + "0.0" + "\", \""
                + receiptPersonEditText.getText().toString() + "\", \""
                + salePersonId + "\", \""
                + dueDate + "\", \""
                + "" + "\", \""
                + getLocationCode() + "\", \""
                + Utils.getDeviceId(SaleOrderCheckoutActivity.this) + "\", \""
                + invoiceTime + "\","
                + null + ", "
                + null + ", "
                + null + ", "
                + null + ", \""
                + invoiceId + "\", \""
                + totalQuantity + "\""
                + ")");

        DeliveryApi deliveryApi = new DeliveryApi();
        deliveryApi.setInvoiceNo(invoiceId);
        deliveryApi.setInvoiceDate(saleDate);
        deliveryApi.setSaleId(invoiceId);
        deliveryApi.setRemark("");
        insertDeliveryUpload(deliveryApi);

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    private int getLocationCode() {
        int locationCode = 0;
        Cursor cursorForLocation = database.rawQuery("select * from Location", null);
        while (cursorForLocation.moveToNext()) {
            locationCode = cursorForLocation.getInt(cursorForLocation.getColumnIndex(DatabaseContract.Location.id));
            locationCodeName = cursorForLocation.getString(cursorForLocation.getColumnIndex(DatabaseContract.Location.no));
        }

        return locationCode;
    }

    private int insertDeliveryDataItemToDatabase(List<SoldProduct> soldProductList, String invoiceId) {
        int totolQtyForInvoice = 0;
        for (SoldProduct soldProduct : soldProductList) {

            ContentValues cvInvoiceProduct = new ContentValues();
            cvInvoiceProduct.put("INVOICE_PRODUCT_ID", invoiceId);
            cvInvoiceProduct.put("PRODUCT_ID", soldProduct.getProduct().getId());
            cvInvoiceProduct.put("SALE_QUANTITY", soldProduct.getQuantity());
            double discount = soldProduct.getProduct().getPrice() * soldProduct.getDiscount(this) / 100;
            cvInvoiceProduct.put("DISCOUNT_AMOUNT", discount + "");
            cvInvoiceProduct.put("TOTAL_AMOUNT", soldProduct.getNetAmount(this));
            cvInvoiceProduct.put("DISCOUNT_PERCENT", soldProduct.getDiscount(this));
            cvInvoiceProduct.put("EXTRA_DISCOUNT", soldProduct.getExtraDiscount());

            totolQtyForInvoice += soldProduct.getQuantity();
            database.insert("INVOICE_PRODUCT", null, cvInvoiceProduct);

            DeliveryItemApi deliveryItemApi = new DeliveryItemApi();
            deliveryItemApi.setDeliveryId(invoiceId);
            deliveryItemApi.setStockId(soldProduct.getProduct().getStockId());
            deliveryItemApi.setDeliveryQty(soldProduct.getQuantity());
            deliveryItemApi.setFoc(Short.parseShort(soldProduct.isFocStatus() ? "1" : "0"));
            insertDeliveryItemUpload(deliveryItemApi);

            database.execSQL("UPDATE PRODUCT SET REMAINING_QTY = REMAINING_QTY - " + soldProduct.getQuantity()
                    + ", DELIVERY_QTY = DELIVERY_QTY + " + soldProduct.getQuantity() + " WHERE PRODUCT_ID = \'" + soldProduct.getProduct().getId() + "\'");

            database.execSQL("UPDATE DELIVERY_ITEM SET RECEIVED_QTY = " + soldProduct.getQuantity() + ", ORDER_QTY = ORDER_QTY - " + soldProduct.getQuantity() + " WHERE STOCK_NO = \'" + soldProduct.getProduct().getStockId() + "\'");

            database.execSQL("UPDATE DELIVERY_ITEM SET DELIVERY_FLG = 1 WHERE ORDER_QTY < 1 AND STOCK_NO = \'" + soldProduct.getProduct().getId() + "\'");

            for (Promotion promotion : promotionArrayList) {
                database.execSQL("UPDATE PRODUCT SET PRESENT_QTY = PRESENT_QTY + " + promotion.getPromotionQty() + " WHERE PRODUCT_ID = \'" + soldProduct.getProduct().getId() + "\'");
            }
        }
        return totolQtyForInvoice;
    }

    /**
     * Insert delivery to database for upload.
     *
     * @param deliveryApi Delivery model for upload
     */
    void insertDeliveryUpload(DeliveryApi deliveryApi) {
        ContentValues cvDeliveryUpload = new ContentValues();
        cvDeliveryUpload.put(DatabaseContract.DELIVERY_UPLOAD.INVOICE_NO, deliveryApi.getInvoiceNo());
        cvDeliveryUpload.put(DatabaseContract.DELIVERY_UPLOAD.INVOICE_DATE, deliveryApi.getInvoiceDate());
        cvDeliveryUpload.put(DatabaseContract.DELIVERY_UPLOAD.CUSTOMER_ID, SaleOrderCheckoutActivity.this.customer.getId());
        cvDeliveryUpload.put(DatabaseContract.DELIVERY_UPLOAD.SALE_ID, deliveryApi.getSaleId());
        cvDeliveryUpload.put(DatabaseContract.DELIVERY_UPLOAD.REMARK, deliveryApi.getRemark());
        database.insert(DatabaseContract.DELIVERY_UPLOAD.TABLE, null, cvDeliveryUpload);
    }

    /**
     * Insert delivery item to database for upload
     *
     * @param deliveryItemApi Delivery item model for upload
     */
    void insertDeliveryItemUpload(DeliveryItemApi deliveryItemApi) {
        ContentValues cvDeliveryUploadItem = new ContentValues();
        cvDeliveryUploadItem.put(DatabaseContract.DELIVERY_ITEM_UPLOAD.DELIVERY_ID, deliveryItemApi.getDeliveryId());
        cvDeliveryUploadItem.put(DatabaseContract.DELIVERY_ITEM_UPLOAD.STOCK_ID, deliveryItemApi.getStockId());
        cvDeliveryUploadItem.put(DatabaseContract.DELIVERY_ITEM_UPLOAD.QUANTITY, deliveryItemApi.getDeliveryQty());
        cvDeliveryUploadItem.put(DatabaseContract.DELIVERY_ITEM_UPLOAD.FOC, deliveryItemApi.getFoc());
        database.insert(DatabaseContract.DELIVERY_ITEM_UPLOAD.TABLE, null, cvDeliveryUploadItem);
    }

    @Override
    public void onActionClick(String type) {
        String cashOrBank = getPaymentMethod();

        if(isPreOrder) {
            if (isFullyPaid()) {
                cashOrBank = "CA";
                insertPreOrderInformation(cashOrBank);
            } else {
                insertPreOrderInformation("CR");
            }

            if(isOnline()) {
                uploadPreOrderToServer();
                //uploadPreOrderRealTime();
            } else {
                sendSMSMessage();
            }
        } else if(isDelivery) {
            if (SaleOrderCheckoutActivity.this.receiptPersonEditText.getText().length() == 0) {

                new AlertDialog.Builder(SaleOrderCheckoutActivity.this)
                        .setTitle("Delivery")
                        .setMessage("Your must provide 'Receipt Person'.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                receiptPersonEditText.requestFocus();
                            }
                        })
                        .show();

                return;
            }

            insertDeliveryDataToDatabase(orderedInvoice);
            toDeliveryActivity();
        }
    }

    /**
     * Get customer payment method.
     *
     * @return CA : cash, B : bank, CR : Credit
     */
    private String getPaymentMethod() {
        int selectedRadio = bankOrCashRadioGroup.getCheckedRadioButtonId();
        String paymentMethod = "";
        if (selectedRadio == R.id.activity_sale_checkout_radio_bank) {
            paymentMethod = "B";

        } else if (selectedRadio == R.id.activity_sale_checkout_radio_cash) {
            paymentMethod = "CA";
        }
        return paymentMethod;
    }

    /**
     * Check for fully paid
     *
     * @return true : fully paid; false : not fully paid
     */
    private boolean isFullyPaid() {
        double pay_amount = 0.0, net_amount = 0.0;

        if (!prepaidAmt.getText().toString().equals("")) {
            pay_amount = Double.parseDouble(prepaidAmt.getText().toString());
        }

        if (!netAmountTextView.getText().toString().equals("")) {
            net_amount = Double.parseDouble(netAmountTextView.getText().toString().replace(",", ""));
        }

        if (pay_amount == 0.0 || pay_amount < net_amount) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Sold Product List Adapter - to hold sold products
     */
    private class SoldProductCustomAdapter extends ArrayAdapter<SoldProduct> {

        final Activity context;

        public SoldProductCustomAdapter(Activity context) {
            super(context, R.layout.list_row_sold_product_checkout, soldProductList);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final SoldProduct soldProduct = soldProductList.get(position);
            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_sold_product_checkout, null, true);

            final TextView txt_name = (TextView) view.findViewById(R.id.name);
            final TextView txt_um = (TextView) view.findViewById(R.id.um);
            final TextView txt_qty = (TextView) view.findViewById(R.id.qty);
            final TextView txt_price = (TextView) view.findViewById(R.id.price);
            final TextView txt_discount = (TextView) view.findViewById(R.id.discount);
            final TextView txt_amount = (TextView) view.findViewById(R.id.amount);

            CheckBox checkBox_foc = (CheckBox) view.findViewById(R.id.foc);

            if(isDelivery) {
                checkBox_foc.setVisibility(View.VISIBLE);
            }

            checkBox_foc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    double totalAmt = txt_totalAmount.getText().length() > 0 ? Double.parseDouble(txt_totalAmount.getText().toString().replace(",", "")) : 0;
                    double volumeDiscount = volDisForPreOrder.getText().length() > 0 ? Double.parseDouble(volDisForPreOrder.getText().toString().replace(",", "")) : 0;
                    double productPrice = txt_amount.getText().length() > 0 ? Double.parseDouble(txt_amount.getText().toString().replace(",", "")) : 0;

                    if(isChecked) {
                        double netAmt = totalAmt - volumeDiscount - productPrice;
                        txt_totalAmount.setText(String.valueOf(totalAmt - productPrice));
                        netAmountTextView.setText(String.valueOf(netAmt));
                        soldProduct.setFocStatus(true);
                    } else {
                        double netAmt = (totalAmt - volumeDiscount) + productPrice;
                        txt_totalAmount.setText(String.valueOf(totalAmt + productPrice));
                        netAmountTextView.setText(String.valueOf(netAmt));
                        soldProduct.setFocStatus(false);
                    }
                }
            });

            txt_um.setVisibility(View.GONE);
            txt_discount.setVisibility(View.GONE);

            txt_name.setText(String.valueOf(soldProduct.getProduct().getName()));
            txt_qty.setText(String.valueOf(soldProduct.getQuantity()));

            if (soldProduct.getPromotionPrice() == 0.0) {
                txt_price.setText(Utils.formatAmount(soldProduct.getProduct().getPrice()));
            }
            else {
                txt_price.setText(Utils.formatAmount(soldProduct.getPromotionPrice()));
            }

            if (soldProduct.getPromotionPrice() == 0.0) {
                totalAmountForProduct = soldProduct.getProduct().getPrice() * soldProduct.getQuantity();
            }
            else {
                totalAmountForProduct = soldProduct.getPromotionPrice() * soldProduct.getQuantity();
            }

            double discountPercent = soldProduct.getDiscount(context) + soldProduct.getExtraDiscount();
            Double discount = totalAmountForProduct * discountPercent / 100;
            txt_amount.setText(Utils.formatAmount(totalAmountForProduct - discount));

            return view;
        }
    }

    private class PromotionProductCustomAdapter extends ArrayAdapter<Promotion> {

        final Activity context;

        public PromotionProductCustomAdapter(Activity context) {
            super(context, R.layout.list_row_promotion, promotionArrayList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Promotion promotion = promotionArrayList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_promotion, null, true);

            final TextView nameTextView = (TextView) view.findViewById(R.id.productName);
            final TextView qtyTextView = (TextView) view.findViewById(R.id.qty);
            final TextView priceTextView = (TextView) view.findViewById(R.id.price);

            if (!promotion.getPromotionProductName().equals("") || promotion.getPromotionProductName() != null) {
                nameTextView.setText(promotion.getPromotionProductName());
            } else {
                nameTextView.setVisibility(View.GONE);
            }
            if (promotion.getPromotionQty() != 0) {
                qtyTextView.setText(promotion.getPromotionQty() + "");
            } else {
                qtyTextView.setVisibility(View.GONE);
            }
            if (promotion.getPromotionPrice() != null && promotion.getPromotionPrice() != 0.0) {
                priceTextView.setText(promotion.getPromotionPrice() + "");
            } else {
                priceTextView.setVisibility(View.GONE);
            }

            return view;
        }
    }

    public class SmsDeliveredReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent arg1) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(context, "SMS delivered", Toast.LENGTH_SHORT).show();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(context, "SMS not delivered", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    public class SmsSentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent arg1) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    Toast.makeText(context, "SMS Sent", Toast.LENGTH_SHORT).show();

                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    Toast.makeText(context, "SMS generic failure", Toast.LENGTH_SHORT)
                            .show();

                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    Toast.makeText(context, "SMS no service", Toast.LENGTH_SHORT)
                            .show();

                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    Toast.makeText(context, "SMS null PDU", Toast.LENGTH_SHORT).show();
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    Toast.makeText(context, "SMS radio off", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }
}
