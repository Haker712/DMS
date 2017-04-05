package com.aceplus.samparoo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aceplus.samparoo.customer.SaleOrderCheckoutActivity;
import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.model.Posm;
import com.aceplus.samparoo.model.PosmByCustomer;
import com.aceplus.samparoo.model.Promotion;
import com.aceplus.samparoo.model.SaleReturn;
import com.aceplus.samparoo.model.SaleReturnDetail;
import com.aceplus.samparoo.model.ShopType;
import com.aceplus.samparoo.model.forApi.AddnewCustomerRequest;
import com.aceplus.samparoo.model.PreOrder;
import com.aceplus.samparoo.model.PreOrderProduct;
import com.aceplus.samparoo.model.forApi.CashReceiveApi;
import com.aceplus.samparoo.model.forApi.CashReceiveItemApi;
import com.aceplus.samparoo.model.forApi.CashReceiveRequest;
import com.aceplus.samparoo.model.forApi.CashReceiveRequestData;
import com.aceplus.samparoo.model.forApi.ClassOfProduct;
import com.aceplus.samparoo.model.forApi.CreditForApi;
import com.aceplus.samparoo.model.forApi.CreditResponse;
import com.aceplus.samparoo.model.forApi.CustomerBalanceForApi;
import com.aceplus.samparoo.model.forApi.CustomerData;
import com.aceplus.samparoo.model.forApi.CustomerForApi;
import com.aceplus.samparoo.model.forApi.CustomerResponse;
import com.aceplus.samparoo.model.forApi.CustomerVisitRequest;
import com.aceplus.samparoo.model.forApi.CustomerVisitRequestData;
import com.aceplus.samparoo.model.forApi.CustomerVisitResponse;
import com.aceplus.samparoo.model.forApi.DataForSaleManRoute;
import com.aceplus.samparoo.model.forApi.DataForVolumeDiscount;
import com.aceplus.samparoo.model.forApi.DataforMarketing;
import com.aceplus.samparoo.model.forApi.DataforSaleUpload;
import com.aceplus.samparoo.model.forApi.DeliveryApi;
import com.aceplus.samparoo.model.forApi.DeliveryForApi;
import com.aceplus.samparoo.model.forApi.DeliveryItemApi;
import com.aceplus.samparoo.model.forApi.DeliveryItemForApi;
import com.aceplus.samparoo.model.forApi.DeliveryRequest;
import com.aceplus.samparoo.model.forApi.DeliveryRequestData;
import com.aceplus.samparoo.model.forApi.DeliveryResponse;
import com.aceplus.samparoo.model.forApi.DisplayAssessment;
import com.aceplus.samparoo.model.forApi.DisplayAssessmentData;
import com.aceplus.samparoo.model.forApi.DisplayAssessmentRequest;
import com.aceplus.samparoo.model.forApi.District;
import com.aceplus.samparoo.model.forApi.DownloadMarketing;
import com.aceplus.samparoo.model.forApi.ERouteReport;
import com.aceplus.samparoo.model.forApi.GeneralData;
import com.aceplus.samparoo.model.forApi.GeneralResponse;
import com.aceplus.samparoo.model.forApi.GroupCode;
import com.aceplus.samparoo.model.forApi.Invoice;
import com.aceplus.samparoo.model.forApi.InvoiceDetail;
import com.aceplus.samparoo.model.forApi.InvoicePresent;
import com.aceplus.samparoo.model.forApi.InvoiceResponse;
import com.aceplus.samparoo.model.forApi.Location;
import com.aceplus.samparoo.model.forApi.OutletStockAvailability;
import com.aceplus.samparoo.model.forApi.OutletStockAvailabilityItem;
import com.aceplus.samparoo.model.forApi.Outlet_Sizeinstore_Data;
import com.aceplus.samparoo.model.forApi.Outlet_Sizeinstore_request;
import com.aceplus.samparoo.model.forApi.PosmByCustomerApi;
import com.aceplus.samparoo.model.forApi.PosmByCustomerRequest;
import com.aceplus.samparoo.model.forApi.PosmByCustomerRequestData;
import com.aceplus.samparoo.model.forApi.PosmForApi;
import com.aceplus.samparoo.model.forApi.PosmShopTypeResponse;
import com.aceplus.samparoo.model.forApi.PreOrderApi;
import com.aceplus.samparoo.model.forApi.PreOrderDetailApi;
import com.aceplus.samparoo.model.forApi.PreOrderPresentApi;
import com.aceplus.samparoo.model.forApi.PreOrderRequest;
import com.aceplus.samparoo.model.forApi.PreOrderRequestData;
import com.aceplus.samparoo.model.forApi.ProductForApi;
import com.aceplus.samparoo.model.forApi.ProductResponse;
import com.aceplus.samparoo.model.forApi.ProductType;
import com.aceplus.samparoo.model.forApi.PromotionDate;
import com.aceplus.samparoo.model.forApi.PromotionForApi;
import com.aceplus.samparoo.model.forApi.PromotionGift;
import com.aceplus.samparoo.model.forApi.PromotionGiftItem;
import com.aceplus.samparoo.model.forApi.PromotionPrice;
import com.aceplus.samparoo.model.forApi.PromotionResponse;
import com.aceplus.samparoo.model.forApi.SaleManRouteRequest;
import com.aceplus.samparoo.model.forApi.SaleReturnApi;
import com.aceplus.samparoo.model.forApi.SaleReturnItem;
import com.aceplus.samparoo.model.forApi.SaleReturnRequest;
import com.aceplus.samparoo.model.forApi.SaleReturnRequestData;
import com.aceplus.samparoo.model.forApi.SaleVisitRecord;
import com.aceplus.samparoo.model.forApi.ShopTypeForApi;
import com.aceplus.samparoo.model.forApi.SizeInStoreShare;
import com.aceplus.samparoo.model.forApi.SizeInStoreShareItem;
import com.aceplus.samparoo.model.forApi.StandardExternalCheck;
import com.aceplus.samparoo.model.forApi.StateDivision;
import com.aceplus.samparoo.model.forApi.Township;
import com.aceplus.samparoo.model.forApi.UM;
import com.aceplus.samparoo.model.forApi.VolumeDiscount;
import com.aceplus.samparoo.model.forApi.VolumeDiscountFilter;
import com.aceplus.samparoo.model.forApi.VolumeDiscountItem;
import com.aceplus.samparoo.model.forApi.TsaleRequest;
import com.aceplus.samparoo.model.forApi.VolumeDiscountResponse;
import com.aceplus.samparoo.model.forApi.VolumediscountfilterItem;
import com.aceplus.samparoo.retrofit.DownloadService;
import com.aceplus.samparoo.retrofit.RetrofitServiceFactory;
import com.aceplus.samparoo.retrofit.UploadService;
import com.aceplus.samparoo.utils.Constant;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by haker on 2/3/17.
 */
public class SyncActivity extends AppCompatActivity {

    String saleman_Id = "", saleman_No = "", saleman_Pwd = "";

    SQLiteDatabase sqLiteDatabase;

    String services;


    @InjectView(R.id.textViewError)
    TextView textViewError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);

        ButterKnife.inject(this);

        sqLiteDatabase = new Database(this).getDataBase();

        if (LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, "") != null) {
            saleman_Id = LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, "");
        }

        if (LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NO, "") != null) {
            saleman_No = LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NO, "");
        }

        if (LoginActivity.mySharedPreference.getString(Constant.SALEMAN_PWD, "") != null) {
            saleman_Pwd = LoginActivity.mySharedPreference.getString(Constant.SALEMAN_PWD, "");
        }

    }

    @OnClick(R.id.buttonDownload)
    void download() {

        downloadCustomerFromServer(Utils.createParamData(saleman_No, saleman_Pwd, getRouteID(saleman_Id)));
        //downloadProductsFromServer(Utils.createParamData(saleman_No, saleman_Pwd, getRouteID(saleman_Id)));
        //downloadPromotionFromServer(Utils.createParamData(saleman_No, saleman_Pwd, getRouteID(saleman_Id)));
        //downloadVolumeDiscountFromServer(Utils.createParamData(saleman_No, saleman_Pwd, getRouteID(saleman_Id)));
        //downloadGenerarlfromSever(Utils.createParamData(saleman_No, saleman_Pwd, getRouteID(saleman_Id)));
        //downloadVolumeDiscountFromServer(Utils.createParamData(saleman_No, saleman_Pwd, getRouteID(saleman_Id)));
    }

    @OnClick(R.id.buttonUpload)
    void upload() {
        services = "";
        uploadInvoiceToSever();
    }

    @OnClick(R.id.buttonClearData)
    void clearAllData() {
        showConfirmDialog();
    }

    /**
     * Clear all data from database
     */
    private void clearAllTableData() {
        Cursor c = sqLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type ='table'", null);
        List<String> tables = new ArrayList<>();

        while (c.moveToNext()) {
            tables.add(c.getString(0));
        }

        Log.i("Table counts --> ", String.valueOf(tables.size()));
        for (String table : tables) {
            String clearQuery = "DELETE FROM " + table;
            sqLiteDatabase.execSQL(clearQuery);
            Log.i("DELETION SUCCESS --> ", "All data from " + table + " has been successfully deleted");
        }
    }

    /**
     * Confrim dialog before clearing all data.
     */
    private void showConfirmDialog() {
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.dialog_box_sale_quantity, null);

        LinearLayout availableQuantityLayout = (LinearLayout) view.findViewById(R.id.availableQuantityLayout);
        TextView qtyTextView = (TextView) view.findViewById(R.id.dialog_sale_qty_txtView);
        EditText phoneNoEditText = (EditText) view.findViewById(R.id.quantity);
        TextView messageTextView = (TextView) view.findViewById(R.id.message);

        availableQuantityLayout.setVisibility(View.GONE);
        qtyTextView.setVisibility(View.GONE);
        phoneNoEditText.setVisibility(View.GONE);
        messageTextView.setVisibility(View.GONE);

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .setTitle("Are you sure want to clear all data ?")
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
                        clearAllTableData();
                        alertDialog.dismiss();
                        Utils.backToLogin(SyncActivity.this);
                    }
                });
            }
        });
        alertDialog.show();
    }

    private int getRouteID(String saleman_Id) {
        int routeID = 0;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DatabaseContract.RouteScheduleItem.tb + " where " +
                DatabaseContract.RouteScheduleItem.saleManID + " = '" + saleman_Id + "' ", null);
        while (cursor.moveToNext()) {
            routeID = cursor.getInt(cursor.getColumnIndex(DatabaseContract.RouteScheduleItem.routeID));
        }
        Log.i("routeID>>>", routeID + "");
        return routeID;
    }

    private void downloadCustomerFromServer(String paramData) {
        Utils.callDialog("Please wait...", this);

        DownloadService downloadService = RetrofitServiceFactory.createService(DownloadService.class);
        Call<CustomerResponse> call = downloadService.getCustomer(paramData);
        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        //Utils.cancelDialog();
                        textViewError.setText("");

                        //Toast.makeText(SyncActivity.this, response.body().getAceplusStatusMessage(), Toast.LENGTH_SHORT).show();

                        List<CustomerForApi> customerList = new ArrayList<CustomerForApi>();
                        customerList = response.body().getDataForCustomerList().get(0).getCustomerList();
                        Log.i("customerList>>>", customerList.size() + "");

                        sqLiteDatabase.beginTransaction();

                        insertCustomers(customerList);

                        sqLiteDatabase.setTransactionSuccessful();
                        sqLiteDatabase.endTransaction();

                        downloadProductsFromServer(Utils.createParamData(saleman_No, saleman_Pwd, getRouteID(saleman_Id)));
                    } else {
                        Utils.cancelDialog();
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    }

                } else {

                    if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                        onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    } else {
                        Utils.cancelDialog();
                        Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                    }

                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);
            }
        });
    }

    private void insertCustomers(List<CustomerForApi> customerList) {

        sqLiteDatabase.execSQL("delete from CUSTOMER");

        for (CustomerForApi customer : customerList) {

            Cursor cursor = sqLiteDatabase.rawQuery("select * from CUSTOMER where CUSTOMER_ID = '" + customer.getcUSTOMERID() + "'", null);
            //if (cursor.getCount() == 0) {
            Log.i("not", "exist");
            ContentValues cv = new ContentValues();
            cv.put("id", customer.getId());
            cv.put("CUSTOMER_ID", customer.getcUSTOMERID());
            cv.put("CUSTOMER_NAME", customer.getcUSTOMERNAME());
            cv.put("CUSTOMER_TYPE_ID", customer.getcUSTOMERTYPEID());
            cv.put("CUSTOMER_TYPE_NAME", customer.getcUSTOMERTYPENAME());
            cv.put("ADDRESS", customer.getaDDRESS());
            cv.put("PH", customer.getpH());
            cv.put("township_number", customer.getTownshipNumber());
            cv.put("CREDIT_TERM", customer.getcREDITTERM());
            cv.put("CREDIT_LIMIT", customer.getcREDITLIMIT());
            cv.put("CREDIT_AMT", customer.getcREDITAMT());
            cv.put("DUE_AMT", customer.getdUEAMT());
            cv.put("PREPAID_AMT", customer.getpREPAIDAMT());
            cv.put("PAYMENT_TYPE", customer.getpAYMENTTYPE());
            cv.put("IS_IN_ROUTE", customer.getiSINROUTE());

            cv.put("LATITUDE", customer.getlATITUDE());
            cv.put("LONGITUDE", customer.getlONGITUDE());
            cv.put("VISIT_RECORD", customer.getvISITRECORD());
            cv.put("district_id", customer.getDistrict_id());
            cv.put("state_division_id", customer.getState_division_id());
            cv.put("contact_person", customer.getContactPerson());
            cv.put("customer_category_no", customer.getCustomerCategoryNo());
            cv.put("shop_type_id", customer.getShop_type_id());
            sqLiteDatabase.insert("CUSTOMER", null, cv);
            //}
        }
        Cursor cursor = sqLiteDatabase.rawQuery("select * from CUSTOMER", null);
        Log.i("customerCursor>>>", cursor.getCount() + "");
    }

    /*private void insertCustomers(List<Customer> customerList) {
        for (Customer customer : customerList) {

            Cursor cursor = sqLiteDatabase.rawQuery("select * from CUSTOMER where CUSTOMER_ID = '" + customer.getCustomerId() + "'", null);
            //if (cursor.getCount() == 0) {
            Log.i("not", "exist");
            ContentValues cv = new ContentValues();
            cv.put("CUSTOMER_ID", customer.getCustomerId());
            cv.put("CUSTOMER_NAME", customer.getCustomerName());
            cv.put("CUSTOMER_TYPE_ID", customer.getCustomerTypeId());
            cv.put("CUSTOMER_TYPE_NAME", customer.getCustomerTypeName());
            cv.put("ADDRESS", customer.getAddress());
            cv.put("PH", customer.getPhone());
            cv.put("township_number", customer.getTownship());
            cv.put("CREDIT_TERM", customer.getCreditTerms());
            cv.put("CREDIT_LIMIT", customer.getCreditLimit());
            cv.put("CREDIT_AMT", customer.getCreditAmt());
            cv.put("DUE_AMT", customer.getDueAmt());
            cv.put("PREPAID_AMT", customer.getPrepaidAmt());
            cv.put("PAYMENT_TYPE", customer.getPaymentType());
            cv.put("IS_IN_ROUTE", customer.isInRoute());

            cv.put("LATITUDE", customer.getLatitude());
            cv.put("LONGITUDE", customer.getLongitude());
            cv.put("VISIT_RECORD", customer.getVisitRecord());
            cv.put("district_id", customer.getDistrict_id());
            cv.put("state_division_id", customer.getState_division_id());
            cv.put("contact_person", customer.getContact_person());
            cv.put("customer_category_no", customer.getCustomer_category_no());
            sqLiteDatabase.insert("CUSTOMER", null, cv);
            //}
        }
        Cursor cursor = sqLiteDatabase.rawQuery("select * from CUSTOMER", null);
        Log.i("customerCursor>>>", cursor.getCount() + "");
    }*/

    private void downloadProductsFromServer(String paramData) {
        //Utils.callDialog("Please wait...", this);

        DownloadService downloadService = RetrofitServiceFactory.createService(DownloadService.class);
        Call<ProductResponse> call = downloadService.getProduct(paramData);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        //Utils.cancelDialog();

                        //Toast.makeText(SyncActivity.this, response.body().getAceplusStatusMessage(), Toast.LENGTH_SHORT).show();

                        List<ProductForApi> productList = new ArrayList<ProductForApi>();
                        productList = response.body().getDataForProductList().get(0).getProductList();
                        Log.i("productList>>>", productList.size() + "");

                        sqLiteDatabase.beginTransaction();

                        insertProduct(productList);

                        sqLiteDatabase.setTransactionSuccessful();
                        sqLiteDatabase.endTransaction();

                        downloadPromotionFromServer(Utils.createParamData(saleman_No, saleman_Pwd, getRouteID(saleman_Id)));
                    } else {
                        Utils.cancelDialog();
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    }

                } else {

                    if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                        onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    } else {
                        Utils.cancelDialog();
                        Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                    }

                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);
            }
        });
    }

    private void insertProduct(List<ProductForApi> productList) {
        sqLiteDatabase.execSQL("delete from PRODUCT");
        for (ProductForApi product : productList) {
            ContentValues cv = new ContentValues();
            cv.put("ID", product.getId());
            cv.put("PRODUCT_ID", product.getProductId());
            cv.put("CATEGORY_ID", product.getCategoryId());
            cv.put("GROUP_ID", product.getGroupId());
            cv.put("PRODUCT_NAME", product.getProductName());
            cv.put("TOTAL_QTY", product.getTotal_Qty());
            cv.put("REMAINING_QTY", product.getTotal_Qty());
            cv.put("SELLING_PRICE", product.getSellingPrice());
            cv.put("PURCHASE_PRICE", product.getPurchasePrice());
            cv.put("DISCOUNT_TYPE", product.getProductTypeId());
            cv.put("UM", product.getUmId());
            sqLiteDatabase.insert("PRODUCT", null, cv);
        }
    }

    private void downloadPromotionFromServer(String paramData) {
        //Utils.callDialog("Please wait...", this);

        DownloadService downloadService = RetrofitServiceFactory.createService(DownloadService.class);
        Call<PromotionResponse> call = downloadService.getPromotion(paramData);
        call.enqueue(new Callback<PromotionResponse>() {
            @Override
            public void onResponse(Call<PromotionResponse> call, Response<PromotionResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        //Utils.cancelDialog();

                        //Toast.makeText(SyncActivity.this, response.body().getAceplusStatusMessage(), Toast.LENGTH_SHORT).show();

                        sqLiteDatabase.beginTransaction();

                        insertPromotion(response.body().getPromotionForApi());

                        sqLiteDatabase.setTransactionSuccessful();
                        sqLiteDatabase.endTransaction();

                        downloadVolumeDiscountFromServer(Utils.createParamData(saleman_No, saleman_Pwd, getRouteID(saleman_Id)));
                    } else {
                        Utils.cancelDialog();
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    }

                } else {

                    if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                        onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    } else {
                        Utils.cancelDialog();
                        Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                    }

                }
            }

            @Override
            public void onFailure(Call<PromotionResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);
            }
        });
    }

    private void insertPromotion(List<PromotionForApi> promotionForApiList) {
        sqLiteDatabase.execSQL("delete from " + DatabaseContract.PromotionDate.tb);
        sqLiteDatabase.execSQL("delete from " + DatabaseContract.PromotionPrice.tb);
        sqLiteDatabase.execSQL("delete from " + DatabaseContract.PromotionGift.tb);
        sqLiteDatabase.execSQL("delete from " + DatabaseContract.PromotionGiftItem.tb);
        for (PromotionForApi promotionForApi : promotionForApiList) {
            insertPromotionDate(promotionForApi.getPromotionDateList());

            insertPromotionPrice(promotionForApi.getPromotionPriceList());

            insertPromotionGift(promotionForApi.getPromotionGiftList());

            insertPromotionGiftItem(promotionForApi.getPromotionGiftItemList());
        }
    }

    private void insertPromotionDate(List<PromotionDate> promotionDateList) {
        for (PromotionDate promotionDate : promotionDateList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.PromotionDate.id, promotionDate.getId());
            contentValues.put(DatabaseContract.PromotionDate.promotionPlanId, promotionDate.getPromotionPlanId());
            contentValues.put(DatabaseContract.PromotionDate.date, promotionDate.getDate());
            contentValues.put(DatabaseContract.PromotionDate.promotionDate, promotionDate.getPromotionDate());
            contentValues.put(DatabaseContract.PromotionDate.processStatus, promotionDate.getProcessStatus());
            sqLiteDatabase.insert(DatabaseContract.PromotionDate.tb, null, contentValues);
        }
    }

    private void insertPromotionPrice(List<PromotionPrice> promotionPriceList) {
        for (PromotionPrice promotionPrice : promotionPriceList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.PromotionPrice.id, promotionPrice.getId());
            contentValues.put(DatabaseContract.PromotionPrice.promotionPlanId, promotionPrice.getPromotionPlanId());
            contentValues.put(DatabaseContract.PromotionPrice.stockId, promotionPrice.getStockId());
            contentValues.put(DatabaseContract.PromotionPrice.fromQuantity, promotionPrice.getFromQuantity());
            contentValues.put(DatabaseContract.PromotionPrice.toQuantity, promotionPrice.getToQuantity());
            contentValues.put(DatabaseContract.PromotionPrice.promotionPrice, promotionPrice.getPromotionPrice());
            sqLiteDatabase.insert(DatabaseContract.PromotionPrice.tb, null, contentValues);
        }
    }

    private void insertPromotionGift(List<PromotionGift> promotionGiftList) {
        for (PromotionGift promotionGift : promotionGiftList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.PromotionGift.id, promotionGift.getId());
            contentValues.put(DatabaseContract.PromotionGift.promotionPlanId, promotionGift.getPromotionPlanId());
            contentValues.put(DatabaseContract.PromotionGift.stockId, promotionGift.getStockId());
            contentValues.put(DatabaseContract.PromotionGift.fromQuantity, promotionGift.getFromQuantity());
            contentValues.put(DatabaseContract.PromotionGift.toQuantity, promotionGift.getToQuantity());
            sqLiteDatabase.insert(DatabaseContract.PromotionGift.tb, null, contentValues);
        }
    }

    private void insertPromotionGiftItem(List<PromotionGiftItem> promotionGiftItemList) {
        for (PromotionGiftItem promotionGiftItem : promotionGiftItemList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.PromotionGiftItem.id, promotionGiftItem.getId());
            contentValues.put(DatabaseContract.PromotionGiftItem.promotionPlanId, promotionGiftItem.getPromotionPlanId());
            contentValues.put(DatabaseContract.PromotionGiftItem.stockId, promotionGiftItem.getStockId());
            contentValues.put(DatabaseContract.PromotionGiftItem.quantity, promotionGiftItem.getQuantity());
            sqLiteDatabase.insert(DatabaseContract.PromotionGiftItem.tb, null, contentValues);
        }
    }

    /**
     * Retrieving volume discount information from web api and store them in local database.
     *
     * @param paramData request data to api
     */
    private void downloadVolumeDiscountFromServer(String paramData) {
        //Utils.callDialog("Please wait...", this);

        DownloadService downloadService = RetrofitServiceFactory.createService(DownloadService.class);
        Call<VolumeDiscountResponse> call = downloadService.getVolumeDiscount(paramData);
        call.enqueue(new Callback<VolumeDiscountResponse>() {
            @Override
            public void onResponse(Call<VolumeDiscountResponse> call, Response<VolumeDiscountResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        //Utils.cancelDialog();

                        //Toast.makeText(SyncActivity.this, response.body().getAceplusStatusMessage(), Toast.LENGTH_SHORT).show();

                        sqLiteDatabase.beginTransaction();

                        sqLiteDatabase.execSQL("delete from " + DatabaseContract.VolumeDiscount.tb);
                        sqLiteDatabase.execSQL("delete from " + DatabaseContract.VolumeDiscountItem.tb);
                        sqLiteDatabase.execSQL("delete from " + DatabaseContract.VolumeDiscountFilter.tb);
                        sqLiteDatabase.execSQL("delete from " + DatabaseContract.VolumeDiscountFilterItem.tb);

                        for (DataForVolumeDiscount data : response.body().getDataForVolumeDiscountList()) {
                            insertVolumeDiscount(data.getVolumeDiscount());
                            insertVolumeDiscountFilter(data.getVolumeDiscountFilter());
                        }

                        sqLiteDatabase.setTransactionSuccessful();
                        sqLiteDatabase.endTransaction();

                        downloadGenerarlfromSever(Utils.createParamData(saleman_No, saleman_Pwd, getRouteID(saleman_Id)));
                    } else {
                        Utils.cancelDialog();
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    }

                } else {

                    if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                        onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    } else {
                        Utils.cancelDialog();
                        Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                    }

                }
            }

            @Override
            public void onFailure(Call<VolumeDiscountResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);
            }
        });
    }

    /**
     * Insert volumeDiscount to database.
     *
     * @param volumeDiscountList VolumeDiscountList
     */
    private void insertVolumeDiscount(List<VolumeDiscount> volumeDiscountList) {
        if (volumeDiscountList != null) {
            for (VolumeDiscount volumeDiscount : volumeDiscountList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseContract.VolumeDiscount.id, volumeDiscount.getId());
                contentValues.put(DatabaseContract.VolumeDiscount.discountPlanNo, volumeDiscount.getDiscountPlanNo());
                contentValues.put(DatabaseContract.VolumeDiscount.date, volumeDiscount.getDate());
                contentValues.put(DatabaseContract.VolumeDiscount.startDate, volumeDiscount.getStartDate());
                contentValues.put(DatabaseContract.VolumeDiscount.endDate, volumeDiscount.getEndDate());
                contentValues.put(DatabaseContract.VolumeDiscount.exclude, volumeDiscount.getExclude());
                sqLiteDatabase.insert(DatabaseContract.VolumeDiscount.tb, null, contentValues);
                insertVolumeDiscountItem(volumeDiscount.getVolumeDiscountItem());
            }
        }
    }

    /**
     * Insert volumeDiscountItem to database.
     *
     * @param volumeDiscountItemList VolumeDiscountItemList
     */
    private void insertVolumeDiscountItem(List<VolumeDiscountItem> volumeDiscountItemList) {
        if (volumeDiscountItemList != null) {
            for (VolumeDiscountItem volumeDiscountItem : volumeDiscountItemList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseContract.VolumeDiscountItem.id, volumeDiscountItem.getId());
                contentValues.put(DatabaseContract.VolumeDiscountItem.volumeDiscountId, volumeDiscountItem.getVolumeDiscountId());
                contentValues.put(DatabaseContract.VolumeDiscountItem.fromSaleAmt, volumeDiscountItem.getFromSaleAmt());
                contentValues.put(DatabaseContract.VolumeDiscountItem.toSaleAmt, volumeDiscountItem.getToSaleAmt());
                contentValues.put(DatabaseContract.VolumeDiscountItem.discountPercent, volumeDiscountItem.getDiscountPercent());
                contentValues.put(DatabaseContract.VolumeDiscountItem.discountAmount, volumeDiscountItem.getDiscountAmount());
                contentValues.put(DatabaseContract.VolumeDiscountItem.discountPrice, volumeDiscountItem.getDiscountPrice());
                sqLiteDatabase.insert(DatabaseContract.VolumeDiscountItem.tb, null, contentValues);
            }
        }
    }

    /**
     * Insert volumeDiscountFilter to database.
     *
     * @param volumeDiscountFilterList VolumeDiscountFilterList
     */
    private void insertVolumeDiscountFilter(List<VolumeDiscountFilter> volumeDiscountFilterList) {
        if (volumeDiscountFilterList != null) {
            for (VolumeDiscountFilter volumeDiscountFilter : volumeDiscountFilterList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseContract.VolumeDiscountFilter.id, volumeDiscountFilter.getId());
                contentValues.put(DatabaseContract.VolumeDiscountFilter.discountPlanNo, volumeDiscountFilter.getDiscountPlanNo());
                contentValues.put(DatabaseContract.VolumeDiscountFilter.date, volumeDiscountFilter.getDate());
                contentValues.put(DatabaseContract.VolumeDiscountFilter.startDate, volumeDiscountFilter.getStartDate());
                contentValues.put(DatabaseContract.VolumeDiscountFilter.endDate, volumeDiscountFilter.getEndDate());
                contentValues.put(DatabaseContract.VolumeDiscountFilter.exclude, volumeDiscountFilter.getExclude());
                sqLiteDatabase.insert(DatabaseContract.VolumeDiscountFilter.tb, null, contentValues);
                insertVolumeDiscountFilterItem(volumeDiscountFilter.getVolumediscountfilterItem());
            }
        }
    }

    /**
     * Insert volumeDiscountFilterItem to database.
     *
     * @param volumeDiscountFilterItemList VolumeDiscountFilterItemList
     */
    private void insertVolumeDiscountFilterItem(List<VolumediscountfilterItem> volumeDiscountFilterItemList) {
        if (volumeDiscountFilterItemList != null) {
            for (VolumediscountfilterItem volumeDiscountFilterItem : volumeDiscountFilterItemList) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseContract.VolumeDiscountFilterItem.id, volumeDiscountFilterItem.getId());
                contentValues.put(DatabaseContract.VolumeDiscountFilterItem.volumeDiscountId, volumeDiscountFilterItem.getVolumeDiscountId());
                contentValues.put(DatabaseContract.VolumeDiscountFilterItem.categoryId, volumeDiscountFilterItem.getCategoryId());
                contentValues.put(DatabaseContract.VolumeDiscountFilterItem.groupCodeId, volumeDiscountFilterItem.getGroupCodeId());
                contentValues.put(DatabaseContract.VolumeDiscountFilterItem.fromSaleAmount, volumeDiscountFilterItem.getFromSaleAmount());
                contentValues.put(DatabaseContract.VolumeDiscountFilterItem.toSaleAmount, volumeDiscountFilterItem.getToSaleAmount());
                contentValues.put(DatabaseContract.VolumeDiscountFilterItem.discountPercent, volumeDiscountFilterItem.getDiscountPercent());
                contentValues.put(DatabaseContract.VolumeDiscountFilterItem.discountAmount, volumeDiscountFilterItem.getDiscountAmount());
                contentValues.put(DatabaseContract.VolumeDiscountFilterItem.discountPrice, volumeDiscountFilterItem.getDiscountPrice());
                sqLiteDatabase.insert(DatabaseContract.VolumeDiscountFilterItem.tb, null, contentValues);
            }
        }

    }

    /***
     * PLin
     ***/

    private void downloadGenerarlfromSever(String paramData) {

        //Utils.callDialog("Please wait...", this);

        DownloadService downloadService = RetrofitServiceFactory.createService(DownloadService.class);
        Call<GeneralResponse> call = downloadService.getGeneral(paramData);
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        Utils.cancelDialog();

                        Toast.makeText(SyncActivity.this, R.string.download_success, Toast.LENGTH_SHORT).show();

                        sqLiteDatabase.beginTransaction();

                        insertGeneral(response.body().getData());

                        sqLiteDatabase.setTransactionSuccessful();
                        sqLiteDatabase.endTransaction();
                        downloadPosmShopTypeFromServer(Utils.createParamData(saleman_No, saleman_Pwd, getRouteID(saleman_Id)));
                    } else {
                        Utils.cancelDialog();
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    }

                } else {

                    if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                        onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    } else {
                        Utils.cancelDialog();
                        Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                    }

                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);
            }
        });


    }

    private void insertGeneral(List<GeneralData> generalDataList) {

        sqLiteDatabase.execSQL("delete from " + DatabaseContract.ClassOfProduct.tb);
        sqLiteDatabase.execSQL("delete from " + DatabaseContract.District.tb);
        sqLiteDatabase.execSQL("delete from " + DatabaseContract.GroupCode.tb);
        sqLiteDatabase.execSQL("delete from " + DatabaseContract.ProductType.tb);
        sqLiteDatabase.execSQL("delete from " + DatabaseContract.StateDivision.tb);
        sqLiteDatabase.execSQL("delete from " + DatabaseContract.Township.tb);
        sqLiteDatabase.execSQL("delete from " + DatabaseContract.UM.tb);
        sqLiteDatabase.execSQL("delete from " + DatabaseContract.Location.tb);

        for (GeneralData generalData : generalDataList) {

            insertClassOfProduct(generalData.getClass_());
            insertDistrict(generalData.getDistrict());
            insertGroupCode(generalData.getGroupCode());
            insertProductType(generalData.getProductType());
            insertStatDivision(generalData.getStateDivision());
            insertTownship(generalData.getTownship());
            insertUM(generalData.getUM());
            insertLocation(generalData.getLocation());
        }


    }

    private void insertMarkting(List<DataforMarketing> dataforMarketingList) {

        for (DataforMarketing dataforMarketing : dataforMarketingList) {

            insertStandardExternalCheck(dataforMarketing.getStandardExternalCheck());

        }


    /* Log.i("ImageName", dataforMarketingList.get(0).getStandardExternalCheck().get(0).getImageName());*/

    }

    private void insertStandardExternalCheck(List<StandardExternalCheck> standardExternalCheckList) {

        for (StandardExternalCheck standardExternalCheck : standardExternalCheckList) {

            ContentValues contentValues = new ContentValues();

            contentValues.put(DatabaseContract.MARKETING.ID, standardExternalCheck.getId());
            contentValues.put(DatabaseContract.MARKETING.IMAGE_NO, standardExternalCheck.getImageNo());
            contentValues.put(DatabaseContract.MARKETING.IMAGE_NAME, standardExternalCheck.getImageName());
            contentValues.put(DatabaseContract.MARKETING.INVOICE_DATE, standardExternalCheck.getInvoiceDate());
            contentValues.put(DatabaseContract.MARKETING.IMAGE, standardExternalCheck.getImage());

            sqLiteDatabase.insert(DatabaseContract.MARKETING.TABLE, null, contentValues);


        }

    }

    private void downloadMarketingfromServer(final String paramData) {


        DownloadService downloadService = RetrofitServiceFactory.createService(DownloadService.class);
        Call<DownloadMarketing> call = downloadService.getMarketingFromApi(paramData);
        call.enqueue(new Callback<DownloadMarketing>() {
            @Override
            public void onResponse(Call<DownloadMarketing> call, Response<DownloadMarketing> response) {
                if (response.code() == 200) {

                    if (response.body().getAceplusStatusCode() == 200) {

                        sqLiteDatabase.beginTransaction();

                        insertMarkting(response.body().getData());

                        sqLiteDatabase.setTransactionSuccessful();
                        sqLiteDatabase.endTransaction();
                        downloadCustomerVisitFromServer(paramData);
                    } else {
                        Utils.cancelDialog();
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    }

                } else {

                    if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                        onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    } else {
                        Utils.cancelDialog();
                        Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                    }

                }
            }

            @Override
            public void onFailure(Call<DownloadMarketing> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);
            }
        });


    }

    private void insertClassOfProduct(List<ClassOfProduct> classOfProductList) {
        for (ClassOfProduct classOfProduct : classOfProductList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.ClassOfProduct.id, classOfProduct.getId());
            contentValues.put(DatabaseContract.ClassOfProduct.name, classOfProduct.getName());

            sqLiteDatabase.insert(DatabaseContract.ClassOfProduct.tb, null, contentValues);
        }
    }

    private void insertDistrict(List<District> districtList) {
        for (District district : districtList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.District.id, district.getId());
            contentValues.put(DatabaseContract.District.name, district.getName());

            sqLiteDatabase.insert(DatabaseContract.District.tb, null, contentValues);
        }
    }

    private void insertGroupCode(List<GroupCode> groupCodeList) {
        for (GroupCode groupCode : groupCodeList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.GroupCode.id, groupCode.getId());
            contentValues.put(DatabaseContract.GroupCode.name, groupCode.getName());

            sqLiteDatabase.insert(DatabaseContract.GroupCode.tb, null, contentValues);
        }
    }

    private void insertProductType(List<ProductType> productTypeList) {
        for (ProductType productType : productTypeList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.ProductType.id, productType.getId());
            contentValues.put(DatabaseContract.ProductType.name, productType.getDescription());

            sqLiteDatabase.insert(DatabaseContract.ProductType.tb, null, contentValues);
        }
    }

    private void insertStatDivision(List<StateDivision> stateDivisionList) {
        for (StateDivision stateDivision : stateDivisionList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.StateDivision.id, stateDivision.getId());
            contentValues.put(DatabaseContract.StateDivision.name, stateDivision.getName());

            sqLiteDatabase.insert(DatabaseContract.StateDivision.tb, null, contentValues);
        }
    }

    private void insertTownship(List<Township> townshipList) {
        for (Township township : townshipList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.Township.id, township.getTownshipId());
            contentValues.put(DatabaseContract.Township.name, township.getTownshipName());

            sqLiteDatabase.insert(DatabaseContract.Township.tb, null, contentValues);
        }
    }

    private void insertUM(List<UM> umList) {
        for (UM um : umList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.UM.id, um.getId());
            contentValues.put(DatabaseContract.UM.name, um.getName());
            contentValues.put(DatabaseContract.UM.code, um.getCode());

            sqLiteDatabase.insert(DatabaseContract.UM.tb, null, contentValues);
        }
    }

    private void insertLocation(List<Location> locationList) {
        for (Location location : locationList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.Location.id, location.getLocationId());
            contentValues.put(DatabaseContract.Location.no, location.getLocationNo());
            contentValues.put(DatabaseContract.Location.name, location.getLocationName());
            contentValues.put(DatabaseContract.Location.branchId, location.getBranchId());
            contentValues.put(DatabaseContract.Location.branchName, location.getBranchNo());

            sqLiteDatabase.insert(DatabaseContract.Location.tb, null, contentValues);
        }
    }

    private void uploadInvoiceToSever() {

        String paramData = "";
        Utils.callDialog("Please wait...", this);

        DataforSaleUpload dataforSaleUpload = new DataforSaleUpload();
        List<DataforSaleUpload> dataforSaleUploads = new ArrayList<>();

        dataforSaleUpload.setInvoice(getInvoicedData());
        dataforSaleUpload.setInvoicePresent(getInvoicepresentData());

        dataforSaleUploads.add(dataforSaleUpload);

        /*JSONObject jsonObject = new JSONObject();


        try {

            jsonObject.put("site_activation_key", Constant.SITE_ACTIVATION_KEY);
            jsonObject.put("tablet_activation_key", Constant.TABLET_ACTIVATION_KEY);
            jsonObject.put("user_id", saleman_No);
            jsonObject.put("password",saleman_Pwd);
            jsonObject.put("route", getRouteID(saleman_Id));
            jsonObject.put("data",dataforSaleUploads);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        paramData = jsonObject.toString();*/


        TsaleRequest tsaleRequest = new TsaleRequest();
        tsaleRequest.setSiteActivationKey(Constant.SITE_ACTIVATION_KEY);
        tsaleRequest.setTabletActivationKey(Constant.TABLET_ACTIVATION_KEY);
        tsaleRequest.setUserId(saleman_Id);
        tsaleRequest.setPassword("");//it is empty string bcoz json format using gson cannot accept encrypted
        tsaleRequest.setRoute(String.valueOf(getRouteID(saleman_Id)));
        tsaleRequest.setData(dataforSaleUploads);

        paramData = getJsonFromObject(tsaleRequest);

        Log.i("ParamData", paramData);


        UploadService uploadService = RetrofitServiceFactory.createService(UploadService.class);
        Call<InvoiceResponse> call = uploadService.getSaleInvoice(paramData);
        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {

                        if (!services.equals("")) {
                            services += ",";
                        }
                        services += getResources().getString(R.string.sale);

                        uploadCustomertoserver();
                    } else if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                        onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                    }

                } else {
                    Utils.cancelDialog();
                    Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                }
            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);

            }


        });
    }

    private List<Invoice> getInvoicedData() {

        List<Invoice> invoiceList = new ArrayList<>();

        Cursor cursor_invoice = sqLiteDatabase.rawQuery("select * from INVOICE", null);

        while (cursor_invoice.moveToNext()) {

            Invoice invoice = new Invoice();
            String invoice_Id = cursor_invoice.getString(cursor_invoice.getColumnIndex("INVOICE_ID"));
            String customer_Id = cursor_invoice.getString(cursor_invoice.getColumnIndex("CUSTOMER_ID"));
            String sale_date = cursor_invoice.getString(cursor_invoice.getColumnIndex("SALE_DATE"));
            Double totalAmount = cursor_invoice.getDouble(cursor_invoice.getColumnIndex("TOTAL_AMOUNT"));
            int totalQuantity = cursor_invoice.getInt(cursor_invoice.getColumnIndex("TOTAL_QUANTITY"));
            Double totalDiscountAmount = cursor_invoice.getDouble(cursor_invoice.getColumnIndex("TOTAL_DISCOUNT_AMOUNT"));
            Double totalPayAmount = cursor_invoice.getDouble(cursor_invoice.getColumnIndex("PAY_AMOUNT"));
            Double totalRefundAmount = cursor_invoice.getDouble(cursor_invoice.getColumnIndex("REFUND_AMOUNT"));
            String receiptPerson = cursor_invoice.getString(cursor_invoice.getColumnIndex("RECEIPT_PERSON_NAME"));


            invoice.setId(invoice_Id);
            invoice.setCustomerId(customer_Id);
            invoice.setDate(sale_date);
            invoice.setTotalAmt(totalAmount);
            invoice.setTotalQty(totalQuantity);
            invoice.setTotalDiscountAmt(totalDiscountAmount);
            invoice.setTotalPayAmt(totalPayAmount);
            invoice.setTotalRefundAmt(totalRefundAmount);
            invoice.setReceiptPerson(receiptPerson);
            invoice.setSalepersonId(cursor_invoice.getInt(cursor_invoice.getColumnIndex("SALE_PERSON_ID")));
            invoice.setLocationCode(cursor_invoice.getInt(cursor_invoice.getColumnIndex("LOCATION_CODE")));
            //invoice.setDeviceId(cursor_invoice.getString(cursor_invoice.getColumnIndex("DEVICE_ID")));
            invoice.setDeviceId("");
            invoice.setInvoiceTime(cursor_invoice.getString(cursor_invoice.getColumnIndex("INVOICE_TIME")));


            List<InvoiceDetail> invoiceDetailList = new ArrayList<>();

            Cursor cur_invoiceDetail = sqLiteDatabase.rawQuery("select * from INVOICE_PRODUCT", null);
            while (cur_invoiceDetail.moveToNext()) {
                InvoiceDetail invoiceDetail = new InvoiceDetail();
                String tsale_Id = cur_invoiceDetail.getString(cur_invoiceDetail.getColumnIndex("INVOICE_PRODUCT_ID"));
                String product_Id = cur_invoiceDetail.getString(cur_invoiceDetail.getColumnIndex("PRODUCT_ID"));
                int quantity = cur_invoiceDetail.getInt(cur_invoiceDetail.getColumnIndex("SALE_QUANTITY"));
                Double discountAmount = cur_invoiceDetail.getDouble(cur_invoiceDetail.getColumnIndex("DISCOUNT_AMOUNT"));
                Double amount = cur_invoiceDetail.getDouble(cur_invoiceDetail.getColumnIndex("TOTAL_AMOUNT"));
                Double discount_percent = cur_invoiceDetail.getDouble(cur_invoiceDetail.getColumnIndex("DISCOUNT_PERCENT"));


                invoiceDetail.setTsaleId(tsale_Id);
                invoiceDetail.setProductId(product_Id);
                invoiceDetail.setQty(quantity);
                invoiceDetail.setDiscountAmt(discountAmount);
                invoiceDetail.setAmt(amount);
                invoiceDetail.setDiscountPercent(discount_percent);

                invoiceDetailList.add(invoiceDetail);
            }

            invoice.setInvoiceDetail(invoiceDetailList);

            invoiceList.add(invoice);

        }


        return invoiceList;
    }

    private List<InvoicePresent> getInvoicepresentData() {


        List<InvoicePresent> invoicePresentList = new ArrayList<>();

        Cursor cursor_InvoicePresent = sqLiteDatabase.rawQuery("select * from INVOICE_PRESENT", null);
        while (cursor_InvoicePresent.moveToNext()) {
            InvoicePresent invoicePresent = new InvoicePresent();
            String tsale_Id = cursor_InvoicePresent.getString(cursor_InvoicePresent.getColumnIndex("tsale_id"));
            int stock_Id = cursor_InvoicePresent.getInt(cursor_InvoicePresent.getColumnIndex("stock_id"));
            int quantity = cursor_InvoicePresent.getInt(cursor_InvoicePresent.getColumnIndex("quantity"));

            invoicePresent.setTsaleId(tsale_Id);
            invoicePresent.setStockId(stock_Id);
            invoicePresent.setQuantity(quantity);

            //invoicePresent.setPcAddress(cursor_InvoicePresent.getString(cursor_InvoicePresent.getColumnIndex("pc_address")));
            invoicePresent.setPcAddress("");
            invoicePresent.setLocationId(cursor_InvoicePresent.getString(cursor_InvoicePresent.getColumnIndex("location_id")));
            invoicePresent.setPrice(cursor_InvoicePresent.getDouble(cursor_InvoicePresent.getColumnIndex("price")));

            invoicePresentList.add(invoicePresent);
        }


        return invoicePresentList;
    }

    private List<CustomerForApi> getCustomerData() {

        List<CustomerForApi> customerForApiList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("Select * from CUSTOMER where flag = 1", null);

        while (cursor.moveToNext()) {

            CustomerForApi customerForApi = new CustomerForApi();
            String townshipId = cursor.getString(cursor.getColumnIndex("township_number"));
            String contact_person = cursor.getString(cursor.getColumnIndex("contact_person"));
            String customer_Id = cursor.getString(cursor.getColumnIndex("CUSTOMER_ID"));
            String customer_Name = cursor.getString(cursor.getColumnIndex("CUSTOMER_NAME"));
            String customer_Type_Id = "";
            String customer_Type_Name = "";
            String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));
            String Phone = cursor.getString(cursor.getColumnIndex("PH"));
            Double credit_term = 0.0;
            Double credit_limit = 0.0;
            String payment_Type = "";
            String customerCategoryNo = "";
            String cREDITAMT = "";
            String dUEATM = "";
            String prepaidATM = "";
            String isinRoute = "";
            Double Lat = 0.0;
            Double Long = 0.0;
            String visitRecord = "";
            String districtId = cursor.getString(cursor.getColumnIndex("district_id"));
            String statedivisionId = cursor.getString(cursor.getColumnIndex("state_division_id"));

            customerForApi.setTownshipNumber(townshipId);
            customerForApi.setContactPerson(contact_person);
            customerForApi.setcUSTOMERID(customer_Id);
            customerForApi.setcUSTOMERNAME(customer_Name);
            customerForApi.setcUSTOMERTYPEID(customer_Type_Id);
            customerForApi.setcUSTOMERTYPENAME(customer_Type_Name);
            customerForApi.setaDDRESS(address);
            customerForApi.setpH(Phone);
            customerForApi.setcREDITTERM(String.valueOf(credit_term));
            customerForApi.setcREDITLIMIT(String.valueOf(credit_limit));
            customerForApi.setpAYMENTTYPE(payment_Type);
            customerForApi.setCustomerCategoryNo(customerCategoryNo);
            customerForApi.setcREDITAMT(cREDITAMT);
            customerForApi.setdUEAMT(dUEATM);
            customerForApi.setpREPAIDAMT(prepaidATM);
            customerForApi.setiSINROUTE(isinRoute);
            customerForApi.setlATITUDE(String.valueOf(Lat));
            customerForApi.setlONGITUDE(String.valueOf(Long));
            customerForApi.setvISITRECORD(visitRecord);
            customerForApi.setDistrict_id(Integer.parseInt(districtId));
            customerForApi.setState_division_id(Integer.parseInt(statedivisionId));

            customerForApiList.add(customerForApi);


        }

        return customerForApiList;

    }

    private void uploadCustomertoserver() {

        String paramData = "";

        CustomerData customerData = new CustomerData();
        List<CustomerData> customerDatas = new ArrayList<>();


        customerData.setCustomerForApiList(getCustomerData());
        customerDatas.add(customerData);

        //Utils.callDialog("Please wait...", this);

        AddnewCustomerRequest addnewCustomerRequest = new AddnewCustomerRequest();

        addnewCustomerRequest.setSiteActivationKey(Constant.SITE_ACTIVATION_KEY);
        addnewCustomerRequest.setTabletActivationKey(Constant.TABLET_ACTIVATION_KEY);
        addnewCustomerRequest.setUserId(saleman_Id);
        addnewCustomerRequest.setPassword("");//it is empty string bcoz json format using gson cannot accept encrypted
        addnewCustomerRequest.setRoute(String.valueOf(getRouteID(saleman_Id)));
        addnewCustomerRequest.setData(customerDatas);

        paramData = getJsonFromObject(addnewCustomerRequest);
        Log.i("Paramcus", paramData);

        UploadService uploadService = RetrofitServiceFactory.createService(UploadService.class);
        Call<InvoiceResponse> call = uploadService.getCustomer(paramData);
        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {

                        if (!services.equals("")) {
                            services += ",";
                        }
                        services += " " + getResources().getString(R.string.customer_title);

                        uploadPreOrderToServer();
                    } else {
                        if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                            onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                        }
                    }

                } else {
                    Utils.cancelDialog();
                    Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                }
            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);

            }


        });
    }

/*    private String getJsonFromObject(TsaleRequest tsaleRequest) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonString = gson.toJson(tsaleRequest);
        return jsonString;
    }

    private String getJsonFromObject1(AddnewCustomerRequest addnewCustomerRequest) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonString = gson.toJson(addnewCustomerRequest);
        return jsonString;
    }*/

    /**
     * Upload pre order data to server
     */
    private void uploadPreOrderToServer() {

        //Utils.callDialog("Please wait...", this);

        final PreOrderRequest preOrderRequest = getPreOrderRequest();

        String paramData = getJsonFromObject(preOrderRequest);

        Log.i("ParamData", paramData);

        UploadService uploadService = RetrofitServiceFactory.createService(UploadService.class);

        Call<InvoiceResponse> call = uploadService.uploadPreOrderData(paramData);

        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        //Utils.cancelDialog();
                        //Toast.makeText(SyncActivity.this, response.body().getAceplusStatusMessage(), Toast.LENGTH_SHORT).show();

                        sqLiteDatabase.beginTransaction();

                        if (preOrderRequest.getData() != null && preOrderRequest.getData().get(0).getData().size() > 0) {
                            updateDeleteFlag(DatabaseContract.PreOrder.tb, 1, DatabaseContract.PreOrder.invoice_id, preOrderRequest.getData().get(0).getData().get(0).getId());
                            updateDeleteFlag(DatabaseContract.PreOrderDetail.tb, 1, DatabaseContract.PreOrderDetail.sale_order_id, preOrderRequest.getData().get(0).getData().get(0).getId());
                            updateDeleteFlag("PRE_ORDER_PRESENT", 1, "pre_order_id", preOrderRequest.getData().get(0).getData().get(0).getId());
                            /*deleteDataAfterUpload("PRE_ORDER", "INVOICE_ID", preOrderRequest.getData().get(0).getData().get(0).getId());
                            deleteDataAfterUpload("PRE_ORDER_PRODUCT", "SALE_ORDER_ID", preOrderRequest.getData().get(0).getData().get(0).getId());*/
                        }

                        sqLiteDatabase.setTransactionSuccessful();
                        sqLiteDatabase.endTransaction();

                        if (!services.equals("")) {
                            services += ",";
                        }
                        services += " " + getResources().getString(R.string.sale_order);
                        uploadSaleReturnToServer();
                    } else {
                        if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                            onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                        }
                    }


                } else {
                    Utils.cancelDialog();
                    Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                }

            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);
            }
        });
    }

    /**
     * Transform preOrderRequest to json.
     *
     * @param preOrderRequest PreOrderRequest
     * @return preOrderRequest pre order object for api request
     */
/*    private String getJsonFromObject(PreOrderRequest preOrderRequest) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonString = gson.toJson(preOrderRequest);
        return jsonString;
    }*/

    /**
     * Get all related data for pre order from database.
     *
     * @return pre order object for api request
     */
    private PreOrderRequest getPreOrderRequest() {
        List<PreOrder> preOrderList = getPreOrderFromDatabase();

        List<PreOrderApi> preOrderApiList = new ArrayList<>();

        List<PreOrderPresentApi> preOrderPresentApiList = new ArrayList<>();

        for (PreOrder preOrder : preOrderList) {
            PreOrderApi preOrderApi = new PreOrderApi();
            preOrderApi.setId(preOrder.getInvoiceId());
            preOrderApi.setCustomerId(preOrder.getCustomerId());
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

            for (PreOrderPresentApi preOrderPresentApi : getPreOrderPresentFromDatabase(preOrder.getInvoiceId())) {
                preOrderPresentApiList.add(preOrderPresentApi);
            }

            List<PreOrderProduct> preOrderProductList = getPreOrderProductFromDatabase(preOrder.getInvoiceId());

            List<PreOrderDetailApi> preOrderDetailApiList = new ArrayList<>();
            for (PreOrderProduct preOrderProduct : preOrderProductList) {
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

        Cursor cursorPreOrder = sqLiteDatabase.rawQuery("select * from PRE_ORDER WHERE DELETE_FLAG = 0", null);

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

        Cursor cursorPreOrderProduct = sqLiteDatabase.rawQuery("select * from PRE_ORDER_PRODUCT WHERE SALE_ORDER_ID = \'" + saleOrderId + "\' AND DELETE_FLAG = 0;", null);

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
     * Retrieve pre order present from database.
     *
     * @return PreOrderPresentApi object list
     */
    private List<PreOrderPresentApi> getPreOrderPresentFromDatabase(String saleOrderId) {

        List<PreOrderPresentApi> preOrderPresentList = new ArrayList<>();

        Cursor cursorPreOrderPresent = sqLiteDatabase.rawQuery("select * from PRE_ORDER_PRESENT WHERE PRE_ORDER_ID = \'" + saleOrderId + "\' AND WHERE DELETE_FLAG = 0;", null);
        while (cursorPreOrderPresent.moveToNext()) {
            PreOrderPresentApi preOrderPresentApi = new PreOrderPresentApi();
            preOrderPresentApi.setSaleOrderId(cursorPreOrderPresent.getString(cursorPreOrderPresent.getColumnIndex("pre_order_id")));
            preOrderPresentApi.setProductId(cursorPreOrderPresent.getString(cursorPreOrderPresent.getColumnIndex("stock_id")));
            preOrderPresentApi.setQuantity(cursorPreOrderPresent.getInt(cursorPreOrderPresent.getColumnIndex("quantity")));
        }

        return preOrderPresentList;
    }

    /**
     * Upload sale return to server.
     */
    private void uploadSaleReturnToServer() {
        //Utils.callDialog("Please wait...", this);

        final SaleReturnRequest saleReturnRequest = getSaleReturnRequest();

        String paramData = getJsonFromObject(saleReturnRequest);

        Log.i("ParamData", paramData);

        UploadService uploadService = RetrofitServiceFactory.createService(UploadService.class);

        Call<InvoiceResponse> call = uploadService.uploadSaleReturn(paramData);

        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        //Toast.makeText(SyncActivity.this, response.body().getAceplusStatusMessage(), Toast.LENGTH_SHORT).show();

                        sqLiteDatabase.beginTransaction();

                        if (saleReturnRequest.getData() != null && saleReturnRequest.getData().get(0).getData().size() > 0) {
                            updateDeleteFlag("SALE_RETURN", 1, "SALE_RETURN_ID", saleReturnRequest.getData().get(0).getData().get(0).getInvoiceNo());
                            updateDeleteFlag("SALE_RETURN_DETAIL", 1, "SALE_RETURN_ID", saleReturnRequest.getData().get(0).getData().get(0).getInvoiceNo());

                            /*deleteDataAfterUpload("SALE_RETURN", "SALE_RETURN_ID", saleReturnRequest.getData().get(0).getData().get(0).getInvoiceNo());
                            deleteDataAfterUpload("SALE_RETURN_DETAIL", "SALE_RETURN_ID", saleReturnRequest.getData().get(0).getData().get(0).getInvoiceNo());*/
                        }

                        sqLiteDatabase.setTransactionSuccessful();
                        sqLiteDatabase.endTransaction();

                        if (!services.equals("")) {
                            services += ",";
                        }
                        services += " " + getResources().getString(R.string.sale_return);

                        uploadPosmByCustomerToServer();

                    } else {
                        if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                            onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                        }
                    }
                } else {
                    Utils.cancelDialog();
                    Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                }
            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);
            }
        });

    }

    /**
     * Get all related data for pre order from database.
     *
     * @return pre order object for api request
     */
    private SaleReturnRequest getSaleReturnRequest() {
        List<SaleReturn> saleReturnList = getSaleReturnFromDatabase();

        List<SaleReturnApi> saleReturnApiList = new ArrayList<>();

        for (SaleReturn saleReturn : saleReturnList) {
            SaleReturnApi saleReturnApi = new SaleReturnApi();
            saleReturnApi.setInvoiceNo(saleReturn.getSaleReturnId());
            saleReturnApi.setCustomerId(saleReturn.getCustomerId());
            saleReturnApi.setInvoiceDate(saleReturn.getReturnedDate());
            saleReturnApi.setLocationId(saleReturn.getLocationId());
            saleReturnApi.setPcAddress(saleReturn.getPcAddress());
            saleReturnApi.setAmount(saleReturn.getAmt());
            saleReturnApi.setPayAmount(saleReturn.getPayAmt());

            List<SaleReturnDetail> saleReturnDetailList = getSaleReturnDetailFromDatabase(saleReturn.getSaleReturnId());

            List<SaleReturnItem> saleReturnDetailApiList = new ArrayList<>();
            for (SaleReturnDetail saleReturnDetail : saleReturnDetailList) {
                SaleReturnItem saleReturnItem = new SaleReturnItem();
                saleReturnItem.setInvoiceNo(saleReturnDetail.getSaleReturnId());
                saleReturnItem.setStockId(saleReturnDetail.getProductId());
                saleReturnItem.setPrice(saleReturnDetail.getPrice());
                saleReturnItem.setQuantity(saleReturnDetail.getQuantity());
                saleReturnDetailApiList.add(saleReturnItem);
            }

            saleReturnApi.setSaleReturnItemList(saleReturnDetailApiList);
            saleReturnApiList.add(saleReturnApi);
        }

        List<SaleReturnRequestData> SaleReturnRequestDataList = new ArrayList<>();

        SaleReturnRequestData SaleReturnRequestData = new SaleReturnRequestData();
        SaleReturnRequestData.setData(saleReturnApiList);
        SaleReturnRequestDataList.add(SaleReturnRequestData);

        SaleReturnRequestData.setData(saleReturnApiList);

        SaleReturnRequest SaleReturnRequest = new SaleReturnRequest();
        SaleReturnRequest.setSiteActivationKey(Constant.SITE_ACTIVATION_KEY);
        SaleReturnRequest.setTabletActivationKey(Constant.TABLET_ACTIVATION_KEY);
        SaleReturnRequest.setUserId(saleman_Id);
        SaleReturnRequest.setSalemanId(Integer.parseInt(saleman_Id));
        SaleReturnRequest.setPassword("");
        SaleReturnRequest.setRoute(String.valueOf(getRouteID(saleman_Id)));
        SaleReturnRequest.setData(SaleReturnRequestDataList);

        return SaleReturnRequest;
    }

    /**
     * Retrieve SaleReturn from database.
     *
     * @return SaleReturn object list
     */
    private List<SaleReturn> getSaleReturnFromDatabase() {
        List<SaleReturn> saleReturnList = new ArrayList<>();

        Cursor cursorSaleReturn = sqLiteDatabase.rawQuery("select * from SALE_RETURN WHERE DELETE_FLAG = 0", null);

        while (cursorSaleReturn.moveToNext()) {
            SaleReturn saleReturn = new SaleReturn();
            saleReturn.setSaleReturnId(cursorSaleReturn.getString(cursorSaleReturn.getColumnIndex("SALE_RETURN_ID")));
            saleReturn.setCustomerId(cursorSaleReturn.getInt(cursorSaleReturn.getColumnIndex("CUSTOMER_ID")));
            saleReturn.setLocationId(cursorSaleReturn.getInt(cursorSaleReturn.getColumnIndex("LOCATION_ID")));
            saleReturn.setAmt(cursorSaleReturn.getDouble(cursorSaleReturn.getColumnIndex("AMT")));
            saleReturn.setPayAmt(cursorSaleReturn.getDouble(cursorSaleReturn.getColumnIndex("PAY_AMT")));
            saleReturn.setPcAddress(cursorSaleReturn.getString(cursorSaleReturn.getColumnIndex("PC_ADDRESS")));
            saleReturn.setReturnedDate(cursorSaleReturn.getString(cursorSaleReturn.getColumnIndex("RETURNED_DATE")));
            saleReturnList.add(saleReturn);
        }

        return saleReturnList;
    }

    /**
     * Retrieve SaleReturnDetail from database.
     *
     * @return SaleReturnDetail object list
     */
    private List<SaleReturnDetail> getSaleReturnDetailFromDatabase(String saleReturnId) {
        List<SaleReturnDetail> saleReturnDetailList = new ArrayList<>();

        Cursor cursorSaleReturnDetail = sqLiteDatabase.rawQuery("select * from SALE_RETURN_DETAIL WHERE SALE_RETURN_ID = \'" + saleReturnId + "\' AND DELETE_FLAG = 0;", null);

        while (cursorSaleReturnDetail.moveToNext()) {
            SaleReturnDetail saleReturnDetail = new SaleReturnDetail();
            saleReturnDetail.setSaleReturnId(cursorSaleReturnDetail.getString(cursorSaleReturnDetail.getColumnIndex("SALE_RETURN_ID")));
            saleReturnDetail.setProductId(cursorSaleReturnDetail.getString(cursorSaleReturnDetail.getColumnIndex("PRODUCT_ID")));
            saleReturnDetail.setQuantity(cursorSaleReturnDetail.getInt(cursorSaleReturnDetail.getColumnIndex("QUANTITY")));
            //saleReturnDetail.setRemark(cursorSaleReturnProduct.getString(cursorSaleReturnProduct.getColumnIndex("REMARK")));
            saleReturnDetailList.add(saleReturnDetail);
        }

        return saleReturnDetailList;
    }

    /**
     * Transform saleReturnRequest to json.
     *
     * @param saleReturnRequest SaleReturnRequest
     * @return saleReturnRequest sale return object for api request
     */
/*    private String getJsonFromObject(SaleReturnRequest saleReturnRequest) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonString = gson.toJson(saleReturnRequest);
        return jsonString;
    }*/

    /**
     * Download posm and shop type from server.
     *
     * @param param request param
     */
    private void downloadPosmShopTypeFromServer(String param) {
        DownloadService downloadService = RetrofitServiceFactory.createService(DownloadService.class);
        Call<PosmShopTypeResponse> call = downloadService.getPosmAndShopType(param);
        call.enqueue(new Callback<PosmShopTypeResponse>() {
            @Override
            public void onResponse(Call<PosmShopTypeResponse> call, Response<PosmShopTypeResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        sqLiteDatabase.beginTransaction();
                        sqLiteDatabase.execSQL("delete from POSM");
                        List<ShopTypeForApi> ShopTypeForApiList = response.body().getPosmShopTypeForApiList().get(0).getShopTypeForApiList();
                        List<Posm> posmList = new ArrayList<>();
                        for (PosmForApi posmForApi : response.body().getPosmShopTypeForApiList().get(0).getPosmForApiList()) {
                            Posm posm = new Posm();
                            posm.setId(posmForApi.getId());
                            posm.setInvoiceNo(posmForApi.getInvoiceNo());
                            posm.setInvoiceDate(posmForApi.getInvoiceDate());
                            posm.setShopTypeId(posmForApi.getShopTypeId());
                            posm.setStockId(posmForApi.getStockId());
                            posmList.add(posm);
                        }
                        insertPOSM(posmList);
                        sqLiteDatabase.execSQL("delete from SHOP_TYPE");
                        List<ShopType> shopTypeList = new ArrayList<>();
                        for (ShopTypeForApi shopTypeForApi : response.body().getPosmShopTypeForApiList().get(0).getShopTypeForApiList()) {
                            ShopType shopType = new ShopType();
                            shopType.setId(shopTypeForApi.getId());
                            shopType.setShopTypeNo(shopTypeForApi.getShopTypeNo());
                            shopType.setShopTypeName(shopTypeForApi.getShopTypeName());
                            shopTypeList.add(shopType);
                        }

                        insertShopType(shopTypeList);

                        sqLiteDatabase.setTransactionSuccessful();
                        sqLiteDatabase.endTransaction();

                        downloadDeliveryFromApi(Utils.createParamData(saleman_No, saleman_Pwd, getRouteID(saleman_Id)));
                    } else {
                        Utils.cancelDialog();
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    }

                } else {

                    if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                        onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    } else {
                        Utils.cancelDialog();
                        Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                    }

                }
            }

            @Override
            public void onFailure(Call<PosmShopTypeResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);
            }
        });
    }


    /**
     * Insert posm to db.
     *
     * @param posmList posm list
     */
    private void insertPOSM(List<Posm> posmList) {
        for (Posm posm : posmList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.POSM.ID, posm.getId());
            contentValues.put(DatabaseContract.POSM.INVOICE_NO, posm.getInvoiceNo());
            contentValues.put(DatabaseContract.POSM.INVOICE_DATE, posm.getInvoiceDate());
            contentValues.put(DatabaseContract.POSM.SHOP_TYPE_ID, posm.getShopTypeId());
            contentValues.put(DatabaseContract.POSM.STOCK_ID, posm.getStockId());
            sqLiteDatabase.insert(DatabaseContract.POSM.TABLE, null, contentValues);
        }
    }

    /**
     * Insert shop type to db.
     *
     * @param shopTypeList ShopType list
     */
    private void insertShopType(List<ShopType> shopTypeList) {
        for (ShopType shopType : shopTypeList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.SHOP_TYPE.ID, shopType.getId());
            contentValues.put(DatabaseContract.SHOP_TYPE.SHOP_TYPE_NO, shopType.getShopTypeNo());
            contentValues.put(DatabaseContract.SHOP_TYPE.SHOP_TYPE_NAME, shopType.getShopTypeName());
            sqLiteDatabase.insert(DatabaseContract.SHOP_TYPE.TABLE, null, contentValues);
        }
    }

    /**
     * Upload PosmByCustomer to server.
     */
    private void uploadPosmByCustomerToServer() {
        //Utils.callDialog("Please wait...", this);

        final PosmByCustomerRequest posmByCustomerRequest = getPosmByCustomerRequest();

        String paramData = getJsonFromObject(posmByCustomerRequest);

        Log.i("ParamData", paramData);

        UploadService uploadService = RetrofitServiceFactory.createService(UploadService.class);

        Call<InvoiceResponse> call = uploadService.uploadPosm(paramData);

        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        //Utils.cancelDialog();
                        //Toast.makeText(SyncActivity.this, response.body().getAceplusStatusMessage(), Toast.LENGTH_SHORT).show();

                        sqLiteDatabase.beginTransaction();

                        if (posmByCustomerRequest.getData() != null && posmByCustomerRequest.getData().get(0).getPosmByCustomerApiList().size() > 0) {
                            updateDeleteFlag("POSM_BY_CUSTOMER", 1, "INVOICE_NO", posmByCustomerRequest.getData().get(0).getPosmByCustomerApiList().get(0).getInvoiceNo());
                            //deleteDataAfterUpload("POSM_BY_CUSTOMER", "INVOICE_NO", posmByCustomerRequest.getData().get(0).getPosmByCustomerApiList().get(0).getInvoiceNo());
                        }

                        sqLiteDatabase.setTransactionSuccessful();
                        sqLiteDatabase.endTransaction();

                        if (!services.equals("")) {
                            services += ",";
                        }
                        services += " " + getResources().getString(R.string.posm);

                        uploadDeliveryToServer();
                    } else {
                        if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                            onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                        }
                    }
                } else {
                    Utils.cancelDialog();
                    Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                }
            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);
            }
        });
    }

    /**
     * Get data for PosmByCustomerRequest.
     *
     * @return PosmByCustomerRequest to upload PosmByCustomerRequest to server
     */
    private PosmByCustomerRequest getPosmByCustomerRequest() {

        List<PosmByCustomerApi> posmByCustomerApiList = getPosmByCustomerFromDatabase();
        List<PosmByCustomerRequestData> posmByCustomerRequestDataList = new ArrayList<>();

        PosmByCustomerRequestData posmByCustomerRequestData = new PosmByCustomerRequestData();
        posmByCustomerRequestData.setPosmByCustomerApiList(posmByCustomerApiList);

        posmByCustomerRequestDataList.add(posmByCustomerRequestData);

        PosmByCustomerRequest posmByCustomerRequest = new PosmByCustomerRequest();
        posmByCustomerRequest.setData(posmByCustomerRequestDataList);
        posmByCustomerRequest.setSiteActivationKey(Constant.SITE_ACTIVATION_KEY);
        posmByCustomerRequest.setTabletActivationKey(Constant.TABLET_ACTIVATION_KEY);
        posmByCustomerRequest.setUserId(saleman_Id);
        posmByCustomerRequest.setPassword("");

        return posmByCustomerRequest;
    }

    /**
     * Retrieve PosmByCustomer from database.
     *
     * @return PosmByCustomer object list
     */
    private List<PosmByCustomerApi> getPosmByCustomerFromDatabase() {
        List<PosmByCustomerApi> posmByCustomerList = new ArrayList<>();

        Cursor cursorPosmByCustomer = sqLiteDatabase.rawQuery("select * from POSM_BY_CUSTOMER WHERE DELETE_FLAG = 0;", null);

        while (cursorPosmByCustomer.moveToNext()) {
            PosmByCustomerApi posmByCustomerApi = new PosmByCustomerApi();
            posmByCustomerApi.setInvoiceNo(cursorPosmByCustomer.getString(cursorPosmByCustomer.getColumnIndex(DatabaseContract.POSM.INVOICE_NO)));
            posmByCustomerApi.setInvoiceDate(cursorPosmByCustomer.getString(cursorPosmByCustomer.getColumnIndex(DatabaseContract.POSM.INVOICE_DATE)));
            posmByCustomerApi.setCustomerId(cursorPosmByCustomer.getInt(cursorPosmByCustomer.getColumnIndex("CUSTOMER_ID")));
            posmByCustomerApi.setStockId(cursorPosmByCustomer.getInt(cursorPosmByCustomer.getColumnIndex(DatabaseContract.POSM.STOCK_ID)));
            posmByCustomerApi.setShopTypeId(cursorPosmByCustomer.getInt(cursorPosmByCustomer.getColumnIndex(DatabaseContract.POSM.SHOP_TYPE_ID)));
            posmByCustomerApi.setSaleManId(cursorPosmByCustomer.getString(cursorPosmByCustomer.getColumnIndex("SALE_MAN_ID")));
            posmByCustomerApi.setQuantity(cursorPosmByCustomer.getInt(cursorPosmByCustomer.getColumnIndex("QUANTITY")));
            posmByCustomerApi.setPrice(cursorPosmByCustomer.getDouble(cursorPosmByCustomer.getColumnIndex("PRICE")));
            posmByCustomerList.add(posmByCustomerApi);
        }

        return posmByCustomerList;
    }

    /**
     * Transform PosmByCustomerRequest to json.
     *
     * @param posmByCustomerRequest PosmByCustomerRequest
     * @return PosmByCustomerRequest sale return object for api request
     */
 /*   String getJsonFromObject(PosmByCustomerRequest posmByCustomerRequest) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonString = gson.toJson(posmByCustomerRequest);
        return jsonString;
    }*/

    /**
     * Delete data in table after uploading to server.
     *
     * @param tableName   table name
     * @param columnName  column name
     * @param columnValue column value
     */
    private void deleteDataAfterUpload(String tableName, String columnName, String columnValue) {
        String sql = "delete from " + tableName + " WHERE " + columnName + " = \'" + columnValue + "\';";
        sqLiteDatabase.execSQL(sql);
    }

    private void updateDeleteFlag(String tableName, int deleteFlg, String columnName, String columnValue) {
        String query = "UPDATE " + tableName + " SET DELETE_FLAG = " + deleteFlg + " WHERE " + columnName + " = \'" + columnValue + "'";
        sqLiteDatabase.execSQL(query);
    }

    /**
     * Download Delivery From Api
     *
     * @param param parameter to request api
     */
    private void downloadDeliveryFromApi(String param) {
        //Utils.callDialog("Please wait...", this);

        DownloadService downloadService = RetrofitServiceFactory.createService(DownloadService.class);
        Call<DeliveryResponse> call = downloadService.getDeliveryFromApi(param);

        call.enqueue(new Callback<DeliveryResponse>() {
            @Override
            public void onResponse(Call<DeliveryResponse> call, Response<DeliveryResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        Utils.cancelDialog();

                        List<DeliveryForApi> deliveryForApiList = response.body().getDataForDeliveryList().get(0).getDeliveryForApiList();

                        Log.i("DeliveryForApiList >>>", deliveryForApiList.size() + "");

                        sqLiteDatabase.beginTransaction();

                        sqLiteDatabase.execSQL("DELETE FROM " + DatabaseContract.DELIVERY.TABLE);

                        sqLiteDatabase.execSQL("DELETE FROM " + DatabaseContract.DELIVERY_ITEM.TABLE);

                        for (DeliveryForApi deliveryForApi : deliveryForApiList) {
                            insertDelivery(deliveryForApi);
                        }

                        sqLiteDatabase.setTransactionSuccessful();
                        sqLiteDatabase.endTransaction();
                        downloadCreditFromServer(Utils.createParamData(saleman_No, saleman_Pwd, getRouteID(saleman_Id)));
                    } else {
                        Utils.cancelDialog();
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    }

                } else {

                    if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                        onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    } else {
                        Utils.cancelDialog();
                        Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                    }

                }
            }

            @Override
            public void onFailure(Call<DeliveryResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);
            }
        });
    }

    /**
     * Insert delivery to local database.
     *
     * @param delivery delivery
     */
    private void insertDelivery(DeliveryForApi delivery) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.DELIVERY.ID, delivery.getId());
        contentValues.put(DatabaseContract.DELIVERY.INVOICE_NO, delivery.getInvoiceNo());
        contentValues.put(DatabaseContract.DELIVERY.INVOICE_DATE, delivery.getInvoiceDate());
        contentValues.put(DatabaseContract.DELIVERY.CUSTOMER_ID, delivery.getCustomerId());
        contentValues.put(DatabaseContract.DELIVERY.AMOUNT, delivery.getAmount());
        contentValues.put(DatabaseContract.DELIVERY.PAID_AMOUNT, delivery.getPaidAmount());
        contentValues.put(DatabaseContract.DELIVERY.EXP_DATE, delivery.getExpDate());
        contentValues.put(DatabaseContract.DELIVERY.SALEMAN_ID, delivery.getSaleManId());
        sqLiteDatabase.insert(DatabaseContract.DELIVERY.TABLE, null, contentValues);

        insertDeliveryItem(delivery.getDeliveryItemForApiList());
    }

    /**
     * Insert deliver item to local database.
     *
     * @param deliveryItemList delivery item list
     */
    private void insertDeliveryItem(List<DeliveryItemForApi> deliveryItemList) {
        for (DeliveryItemForApi deliveryItem : deliveryItemList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.DELIVERY_ITEM.ID, deliveryItem.getId());
            contentValues.put(DatabaseContract.DELIVERY_ITEM.DELIVERY_ID, deliveryItem.getSaleOrderId());
            contentValues.put(DatabaseContract.DELIVERY_ITEM.STOCK_NO, deliveryItem.getStockNo());
            contentValues.put(DatabaseContract.DELIVERY_ITEM.ORDER_QTY, deliveryItem.getOrderQty());
            contentValues.put(DatabaseContract.DELIVERY_ITEM.RECEIVED_QTY, deliveryItem.getReceiveQty());
            contentValues.put(DatabaseContract.DELIVERY_ITEM.SPRICE, deliveryItem.getSPrice());
            contentValues.put(DatabaseContract.DELIVERY_ITEM.FOC_STATUS, deliveryItem.getFocStatus());
            sqLiteDatabase.insert(DatabaseContract.DELIVERY_ITEM.TABLE, null, contentValues);
        }
    }

    /**
     * upload delivery data to server
     */
    private void uploadDeliveryToServer() {
        final DeliveryRequest deliveryRequest = getDeliveryRequest();

        String paramData = getJsonFromObject(deliveryRequest);

        Log.i("ParamData", paramData);

        UploadService uploadService = RetrofitServiceFactory.createService(UploadService.class);

        Call<InvoiceResponse> call = uploadService.uploadDelivery(paramData);

        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        //Utils.cancelDialog();

                        if (!services.equals("")) {
                            services += ",";
                        }
                        services += " " + getResources().getString(R.string.delivery);

                        uploadCashReceiveToServer();
                       /* sqLiteDatabase.beginTransaction();

                        if(deliveryRequest.getData() != null && deliveryRequest.getData().get(0).getDeliveryApiList().size() > 0) {
                            deleteDataAfterUpload(DatabaseContract.DELIVERY_UPLOAD.TABLE, DatabaseContract.DELIVERY_UPLOAD.INVOICE_NO, deliveryRequest.getData().get(0).getDeliveryApiList().get(0).getInvoiceNo());
                            deleteDataAfterUpload(DatabaseContract.DELIVERY_ITEM_UPLOAD.TABLE, DatabaseContract.DELIVERY_ITEM_UPLOAD.DELIVERY_ID, deliveryRequest.getData().get(0).getDeliveryApiList().get(0).getInvoiceNo());
                        }

                        sqLiteDatabase.setTransactionSuccessful();
                        sqLiteDatabase.endTransaction();*/

                    }
                } else {
                    if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                        onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                    } else {
                        Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                    }
                }

            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);
            }
        });
    }

    /**
     * upload displayassessment data to server by BL
     */

    private void uploadDisplayAssessmenttosever() {


        final DisplayAssessmentRequest displayAssessmentRequest = displayAssessmentRequest();

        String paramData = getJsonFromObject(displayAssessmentRequest);

        Log.i("pAram", paramData);

        UploadService uploadService = RetrofitServiceFactory.createService(UploadService.class);

        Call<InvoiceResponse> call = uploadService.uploadDisplayAssessment(paramData);

        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {

                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        //Utils.cancelDialog();
                        //Toast.makeText(SyncActivity.this, response.body().getAceplusStatusMessage(), Toast.LENGTH_SHORT).show();
                        if (!services.equals("")) {
                            services += ",";
                        }

                        services += " " + getResources().getString(R.string.display_assessment);

                        uploadOutletSizeinstortoserver();

                    }
                } else {
                    if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                        onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                    } else {
                        Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                    }
                }

            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {

                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);

            }
        });


    }

/*    private String getJsonFromObject(DisplayAssessmentRequest displayAssessmentRequest) {

        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonString = gson.toJson(displayAssessmentRequest);
        return jsonString;

    }*/


    private List<DisplayAssessment> getDisplayAssessmentFromDB() {

        List<DisplayAssessment> displayAssessmentList = new ArrayList<>();


        Cursor cursor = sqLiteDatabase.rawQuery("select * from DISPLAY_ASSESSMENT", null);

        while (cursor.moveToNext()) {

            DisplayAssessment displayAssessment = new DisplayAssessment();
            String invoice_No = cursor.getString(cursor.getColumnIndex("INVOICE_NO"));
            String invoice_Date = cursor.getString(cursor.getColumnIndex("INVOICE_DATE"));
            String customer_Id = cursor.getString(cursor.getColumnIndex("CUSTOMER_ID"));
            String saleman_Id = cursor.getString(cursor.getColumnIndex("SALE_MAN_ID"));
            String image = cursor.getString(cursor.getColumnIndex("IMAGE"));

            displayAssessment.setInvoice_No(invoice_No);
            displayAssessment.setInvoice_Date(invoice_Date);
            displayAssessment.setCustomer_Id(customer_Id);
            displayAssessment.setSaleman_Id(saleman_Id);
            displayAssessment.setImage(image);

            displayAssessmentList.add(displayAssessment);

        }


        return displayAssessmentList;
    }

    private DisplayAssessmentRequest displayAssessmentRequest() {

        List<DisplayAssessment> displayAssessmentList = getDisplayAssessmentFromDB();
        List<DisplayAssessmentData> displayAssessmentDataList = new ArrayList<>();

        DisplayAssessmentData displayAssessmentData = new DisplayAssessmentData();
        displayAssessmentData.setDisplayAssessment(displayAssessmentList);

        displayAssessmentDataList.add(displayAssessmentData);

        DisplayAssessmentRequest displayAssessmentRequest = new DisplayAssessmentRequest();
        displayAssessmentRequest.setData(displayAssessmentDataList);
        displayAssessmentRequest.setSiteActivationKey(Constant.SITE_ACTIVATION_KEY);
        displayAssessmentRequest.setTabletActivationKey(Constant.TABLET_ACTIVATION_KEY);
        displayAssessmentRequest.setUserId(saleman_Id);
        displayAssessmentRequest.setPassword("");

        return displayAssessmentRequest;

    }


    /**
     * upload out outletandsizeinstore to server by BL
     */

    private void uploadOutletSizeinstortoserver() {

        final Outlet_Sizeinstore_request outlet_sizeinstore_request = outlet_sizeinstore_request();

        final String paramData = getJsonFromObject(outlet_sizeinstore_request);
        Log.i("PaRam", paramData);

        UploadService uploadService = RetrofitServiceFactory.createService(UploadService.class);

        Call<InvoiceResponse> call = uploadService.uploadoutletsizeinstore(paramData);

        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {

                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {

                        //Toast.makeText(SyncActivity.this, response.body().getAceplusStatusMessage(), Toast.LENGTH_SHORT).show();
                        if (!services.equals("")) {
                            services += ",";
                        }

                        services += " " + getResources().getString(R.string.outlet_stock_availability);

                        uploadCustomerVisitToServer();
                    }
                } else {
                    if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                        onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                    } else {
                        Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                    }
                }

            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {

                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);

            }
        });


    }

    private Outlet_Sizeinstore_request outlet_sizeinstore_request() {

        Outlet_Sizeinstore_request outlet_sizeinstore_request = new Outlet_Sizeinstore_request();

        outlet_sizeinstore_request.setData(setOutLetSizeinStoreData());
        outlet_sizeinstore_request.setPassword("");
        outlet_sizeinstore_request.setSiteActivationKey(Constant.SITE_ACTIVATION_KEY);
        outlet_sizeinstore_request.setTabletActivationKey(Constant.TABLET_ACTIVATION_KEY);
        outlet_sizeinstore_request.setUserId(saleman_Id);


        return outlet_sizeinstore_request;

    }

    private List<Outlet_Sizeinstore_Data> setOutLetSizeinStoreData() {


        List<Outlet_Sizeinstore_Data> outlet_sizeinstore_dataList = new ArrayList<>();

        Outlet_Sizeinstore_Data outlet_sizeinstore_data = new Outlet_Sizeinstore_Data();

        outlet_sizeinstore_data.setOutletStockAvailability(setOutLetData());
        outlet_sizeinstore_data.setSizeInStoreShare(setSizeinStoreData());


        outlet_sizeinstore_dataList.add(outlet_sizeinstore_data);

        return outlet_sizeinstore_dataList;

    }

    private List<OutletStockAvailability> setOutLetData() {

        List<OutletStockAvailability> outletStockAvailabilityList = new ArrayList<>();
        int Customer_Id = 0;

        Cursor cursor1 = sqLiteDatabase.rawQuery("select * from outlet_stock_availability", null);

        while (cursor1.moveToNext()) {

            OutletStockAvailability outletStockAvailability = new OutletStockAvailability();

            String availabilityNo = cursor1.getString(cursor1.getColumnIndex("outlet_stock_availability_id"));
            String cus_Id = cursor1.getString(cursor1.getColumnIndex("customer_id"));
            String date = cursor1.getString(cursor1.getColumnIndex("date"));


            Cursor cursor2 = sqLiteDatabase.rawQuery("select * from CUSTOMER where CUSTOMER_ID='" + cus_Id + "'", null);

            while (cursor2.moveToNext()) {

                Customer_Id = cursor2.getInt(cursor2.getColumnIndex("id"));

            }

            outletStockAvailability.setOutletStockAvailabilityNo(availabilityNo);
            outletStockAvailability.setCustomerId(Customer_Id);
            outletStockAvailability.setDate(date);

            outletStockAvailability.setOutletStockAvailabilityItem(getOutletDetailDatafromDB());

            outletStockAvailabilityList.add(outletStockAvailability);


        }

        cursor1.close();
        return outletStockAvailabilityList;

    }

    private List<SizeInStoreShare> setSizeinStoreData() {
        List<SizeInStoreShare> sizeInStoreShareList = new ArrayList<>();

        int Customer_Id = 0;

        Cursor cursor = sqLiteDatabase.rawQuery("select * from  size_in_store_share", null);

        while (cursor.moveToNext()) {

            SizeInStoreShare sizeInStoreShare = new SizeInStoreShare();

            String sizeInStoreShareNo = cursor.getString(cursor.getColumnIndex("size_in_store_share_id"));
            String cus_Id = cursor.getString(cursor.getColumnIndex("customer_id"));
            String date = cursor.getString(cursor.getColumnIndex("date"));


            Cursor cursor2 = sqLiteDatabase.rawQuery("select * from CUSTOMER where CUSTOMER_ID='" + cus_Id + "'", null);

            while (cursor2.moveToNext()) {

                Customer_Id = cursor2.getInt(cursor2.getColumnIndex("id"));

            }

            sizeInStoreShare.setSizeInStoreShareNo(sizeInStoreShareNo);
            sizeInStoreShare.setCustomerId(Customer_Id);
            sizeInStoreShare.setDate(date);
            sizeInStoreShare.setSizeInStoreShareItem(getSizeinStoreDetailfromDB());


            sizeInStoreShareList.add(sizeInStoreShare);

        }

        return sizeInStoreShareList;

    }

    private List<OutletStockAvailabilityItem> getOutletDetailDatafromDB() {

        int Stock_Id = 0;

        List<OutletStockAvailabilityItem> outletStockAvailabilityItemList = new ArrayList<>();


        Cursor cursor = sqLiteDatabase.rawQuery("select * from outlet_stock_availability_detail", null);

        while (cursor.moveToNext()) {

            OutletStockAvailabilityItem outletStockAvailabilityItem = new OutletStockAvailabilityItem();

            String outletStockAvailabilityNo = cursor.getString(cursor.getColumnIndex("outlet_stock_availability_id"));
            String product_id = cursor.getString(cursor.getColumnIndex("product_id"));
            int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));

            Cursor cursor1 = sqLiteDatabase.rawQuery("select * from PRODUCT where PRODUCT_ID='" + product_id + "'", null);

            while (cursor1.moveToNext()) {

                Stock_Id = cursor1.getInt(cursor1.getColumnIndex("ID"));

            }


            outletStockAvailabilityItem.setOutletStockAvailabilityNo(outletStockAvailabilityNo);
            outletStockAvailabilityItem.setStockId(Stock_Id);
            outletStockAvailabilityItem.setQuantity(quantity);

            outletStockAvailabilityItemList.add(outletStockAvailabilityItem);

        }

        cursor.close();
        return outletStockAvailabilityItemList;
    }

    private List<SizeInStoreShareItem> getSizeinStoreDetailfromDB() {

        int Stock_Id = 0;

        List<SizeInStoreShareItem> sizeInStoreShareItemList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from size_in_store_share_detail", null);

        while (cursor.moveToNext()) {
            SizeInStoreShareItem sizeInStoreShareItem = new SizeInStoreShareItem();

            String sizeInStoreShareNo = cursor.getString(cursor.getColumnIndex("size_in_store_share_id"));
            String productId = cursor.getString(cursor.getColumnIndex("product_id"));
            int sizeInStoreSharePercent = cursor.getInt(cursor.getColumnIndex("size_in_store_share_percent"));


            Cursor cursor1 = sqLiteDatabase.rawQuery("select * from PRODUCT where PRODUCT_ID='" + productId + "'", null);

            while (cursor1.moveToNext()) {

                Stock_Id = cursor1.getInt(cursor1.getColumnIndex("ID"));

            }


            sizeInStoreShareItem.setSizeInStoreShareNo(sizeInStoreShareNo);
            sizeInStoreShareItem.setStockId(Stock_Id);
            sizeInStoreShareItem.setSizeInStoreSharePercent(sizeInStoreSharePercent);

            sizeInStoreShareItemList.add(sizeInStoreShareItem);


        }
        cursor.close();
        return sizeInStoreShareItemList;
    }

/*    private String getJsonFromObject(Outlet_Sizeinstore_request outlet_sizeinstore_request) {

        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonString = gson.toJson(outlet_sizeinstore_request);
        return jsonString;

    }*/


    /**
     * Convert delivery date to json format
     *
     * @param deliveryRequest DeliveryRequest
     * @return json string
     */
/*    private String getJsonFromObject(DeliveryRequest deliveryRequest) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonString = gson.toJson(deliveryRequest);
        return jsonString;
    }*/

    /**
     * Set required delivery data to delivery request
     *
     * @return DeliveryRequest
     */
    private DeliveryRequest getDeliveryRequest() {

        List<DeliveryRequestData> deliveryRequestDataList = new ArrayList<>();

        List<DeliveryApi> deliveryApiList = getDeliveryApiFromDb();

        for (DeliveryApi deliveryApi : deliveryApiList) {
            deliveryApi.setDeliveryItemApi(getDeliveryItemApiFromDb(deliveryApi.getInvoiceNo()));
        }

        DeliveryRequestData deliveryRequestData = new DeliveryRequestData();
        deliveryRequestData.setDeliveryApiList(deliveryApiList);
        deliveryRequestDataList.add(deliveryRequestData);

        DeliveryRequest deliveryRequest = new DeliveryRequest();
        deliveryRequest.setData(deliveryRequestDataList);
        deliveryRequest.setSiteActivationKey(Constant.SITE_ACTIVATION_KEY);
        deliveryRequest.setTabletActivationKey(Constant.TABLET_ACTIVATION_KEY);
        deliveryRequest.setUserId(saleman_Id);
        deliveryRequest.setPassword("");
        return deliveryRequest;

    }

    /**
     * Get delivery from database
     *
     * @return deliveryApiList
     */
    private List<DeliveryApi> getDeliveryApiFromDb() {
        List<DeliveryApi> deliveryApiList = new ArrayList<>();

        Cursor cursorDeliveryApi = sqLiteDatabase.rawQuery("select * from DELIVERY_UPLOAD;", null);
        while (cursorDeliveryApi.moveToNext()) {
            DeliveryApi deliveryApi = new DeliveryApi();
            deliveryApi.setInvoiceNo(cursorDeliveryApi.getString(cursorDeliveryApi.getColumnIndex(DatabaseContract.DELIVERY_UPLOAD.INVOICE_NO)));
            deliveryApi.setInvoiceDate(cursorDeliveryApi.getString(cursorDeliveryApi.getColumnIndex(DatabaseContract.DELIVERY_UPLOAD.INVOICE_DATE)));
            deliveryApi.setSaleId(cursorDeliveryApi.getString(cursorDeliveryApi.getColumnIndex(DatabaseContract.DELIVERY_UPLOAD.SALE_ID)));
            deliveryApi.setRemark(cursorDeliveryApi.getString(cursorDeliveryApi.getColumnIndex(DatabaseContract.DELIVERY_UPLOAD.REMARK)));
            deliveryApi.setCustomerId(cursorDeliveryApi.getInt(cursorDeliveryApi.getColumnIndex(DatabaseContract.DELIVERY_UPLOAD.CUSTOMER_ID)));
            deliveryApi.setLocationId(getLocationCode());
            deliveryApi.setSaleManId(Integer.parseInt(saleman_Id));
            deliveryApiList.add(deliveryApi);
        }
        return deliveryApiList;
    }

    /**
     * Get delivery item from database.
     *
     * @param invoiceNo invoice number to retrieve delivery item
     * @return deliveryItemApiList
     */
    private List<DeliveryItemApi> getDeliveryItemApiFromDb(String invoiceNo) {
        List<DeliveryItemApi> deliveryItemApiList = new ArrayList<>();

        Cursor cursorDeliveryItemApi = sqLiteDatabase.rawQuery("select * from DELIVERY_ITEM_UPLOAD WHERE DELIVERY_ID = \'" + invoiceNo + "\'", null);

        while (cursorDeliveryItemApi.moveToNext()) {
            DeliveryItemApi deliveryItemApi = new DeliveryItemApi();
            deliveryItemApi.setDeliveryId(cursorDeliveryItemApi.getString(cursorDeliveryItemApi.getColumnIndex(DatabaseContract.DELIVERY_ITEM_UPLOAD.DELIVERY_ID)));
            deliveryItemApi.setStockId(cursorDeliveryItemApi.getInt(cursorDeliveryItemApi.getColumnIndex(DatabaseContract.DELIVERY_ITEM_UPLOAD.STOCK_ID)));
            deliveryItemApi.setDeliveryQty(cursorDeliveryItemApi.getInt(cursorDeliveryItemApi.getColumnIndex(DatabaseContract.DELIVERY_ITEM_UPLOAD.QUANTITY)));
            deliveryItemApi.setFoc(cursorDeliveryItemApi.getShort(cursorDeliveryItemApi.getColumnIndex(DatabaseContract.DELIVERY_ITEM_UPLOAD.FOC)));
            deliveryItemApiList.add(deliveryItemApi);
        }

        return deliveryItemApiList;
    }

    /**
     * Download credit from api.
     *
     * @param paramData param data
     */
    private void downloadCreditFromServer(final String paramData) {
        DownloadService downloadService = RetrofitServiceFactory.createService(DownloadService.class);
        Call<CreditResponse> call = downloadService.getCreditFromApi(paramData);
        call.enqueue(new Callback<CreditResponse>() {
            @Override
            public void onResponse(Call<CreditResponse> call, Response<CreditResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {

                        insertCreditToDB(response.body().getDataForCreditList().get(0).getCreditForApiList());
                        insertCustomerBalanceToDB(response.body().getDataForCreditList().get(0).getCustomerBalanceList());
                        downloadMarketingfromServer(paramData);
                    } else {
                        Utils.cancelDialog();
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    }


                } else {

                    if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                        onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    } else {
                        Utils.cancelDialog();
                        Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                    }


                }
            }

            @Override
            public void onFailure(Call<CreditResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);
            }
        });

    }

    /**
     * Insert credit to database.
     *
     * @param creditForApiList data from api
     */
    private void insertCreditToDB(List<CreditForApi> creditForApiList) {
        for (CreditForApi creditForApi : creditForApiList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.CREDIT.ID, creditForApi.getId());
            contentValues.put(DatabaseContract.CREDIT.INVOICE_NO, creditForApi.getInvoiceNo());
            contentValues.put(DatabaseContract.CREDIT.INVOICE_DATE, creditForApi.getInvoiceDate());
            contentValues.put(DatabaseContract.CREDIT.CUSTOMER_ID, creditForApi.getCustomerId());
            contentValues.put(DatabaseContract.CREDIT.AMT, creditForApi.getAmount());
            contentValues.put(DatabaseContract.CREDIT.PAY_AMT, creditForApi.getPayAmount());
            contentValues.put(DatabaseContract.CREDIT.FIRST_PAY_AMT, creditForApi.getFirstPayAmount());
            contentValues.put(DatabaseContract.CREDIT.EXTRA_AMT, creditForApi.getExtraAmount());
            contentValues.put(DatabaseContract.CREDIT.REFUND, creditForApi.getRefund());
            contentValues.put(DatabaseContract.CREDIT.SALE_STATUS, creditForApi.getSaleStatus());
            contentValues.put(DatabaseContract.CREDIT.INVOICE_STATUS, creditForApi.getInvoiceStatus());
            contentValues.put(DatabaseContract.CREDIT.SALE_MAN_ID, creditForApi.getSaleManId());
            sqLiteDatabase.insert(DatabaseContract.CREDIT.TABLE, null, contentValues);
        }
    }

    /**
     * Insert customer balance to database.
     *
     * @param customerBalanceForApiList data from api
     */
    private void insertCustomerBalanceToDB(List<CustomerBalanceForApi> customerBalanceForApiList) {
        for (CustomerBalanceForApi customerBalanceForApi : customerBalanceForApiList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.CUSTOMER_BALANCE.ID, customerBalanceForApi.getId());
            contentValues.put(DatabaseContract.CUSTOMER_BALANCE.CUSTOMER_ID, customerBalanceForApi.getCustomerId());
            contentValues.put(DatabaseContract.CUSTOMER_BALANCE.CURRENCY_ID, customerBalanceForApi.getCurrencyId());
            contentValues.put(DatabaseContract.CUSTOMER_BALANCE.OPENING_BALANCE, customerBalanceForApi.getOpeningBalance());
            contentValues.put(DatabaseContract.CUSTOMER_BALANCE.BALANCE, customerBalanceForApi.getBalance());
            sqLiteDatabase.insert(DatabaseContract.CUSTOMER_BALANCE.TABLE, null, contentValues);
        }
    }

    /**
     * upload cash receive data to server
     */
    private void uploadCashReceiveToServer() {
        CashReceiveRequest cashReceiveRequest = getCashReceiveRequest();
        String paramData = getJsonFromObject(cashReceiveRequest);
        Log.i("ParamData", paramData);

        UploadService uploadService = RetrofitServiceFactory.createService(UploadService.class);

        Call<InvoiceResponse> call = uploadService.uploadCashReceive(paramData);
        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        //Utils.cancelDialog();
                        if (!services.equals("")) {
                            services += ",";
                        }

                        services += " " + getResources().getString(R.string.cash_receive);
                        uploadDisplayAssessmenttosever();

                    } else {
                        if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                            onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                        }
                    }


                } else {
                    Utils.cancelDialog();
                    Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                }
            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);
            }
        });
    }

    /**
     * Get related data from DB for Cash receive.
     *
     * @return cashReceiveRequest
     */
    private CashReceiveRequest getCashReceiveRequest() {
        List<CashReceiveApi> cashReceiveApiList = getCashReceiveFromDB();
        List<CashReceiveRequestData> cashReceiveRequestDataList = new ArrayList<>();

        CashReceiveRequestData cashReceiveRequestData = new CashReceiveRequestData();
        cashReceiveRequestData.setCashReceiveApiList(cashReceiveApiList);
        cashReceiveRequestDataList.add(cashReceiveRequestData);

        CashReceiveRequest cashReceiveRequest = new CashReceiveRequest();
        cashReceiveRequest.setSiteActivationKey(Constant.SITE_ACTIVATION_KEY);
        cashReceiveRequest.setTabletActivationKey(Constant.TABLET_ACTIVATION_KEY);
        cashReceiveRequest.setUserId(saleman_Id);
        cashReceiveRequest.setPassword("");
        cashReceiveRequest.setData(cashReceiveRequestDataList);

        return cashReceiveRequest;
    }

    /**
     * Get cash receive from DB
     *
     * @return CashReceiveApi list
     */
    private List<CashReceiveApi> getCashReceiveFromDB() {
        List<CashReceiveApi> cashReceiveApiList = new ArrayList<>();

        Cursor cursorCashReceiveApi = sqLiteDatabase.rawQuery("select * from " + DatabaseContract.CASH_RECEIVE.TABLE, null);
        while (cursorCashReceiveApi.moveToNext()) {
            CashReceiveApi cashReceiveApi = new CashReceiveApi();
            cashReceiveApi.setReceiveNo(cursorCashReceiveApi.getString(cursorCashReceiveApi.getColumnIndex(DatabaseContract.CASH_RECEIVE.RECEIVE_NO)));
            cashReceiveApi.setReceiveDate(cursorCashReceiveApi.getString(cursorCashReceiveApi.getColumnIndex(DatabaseContract.CASH_RECEIVE.RECEIVE_DATE)));
            cashReceiveApi.setCustomerId(cursorCashReceiveApi.getString(cursorCashReceiveApi.getColumnIndex(DatabaseContract.CASH_RECEIVE.CUSTOMER_ID)));
            cashReceiveApi.setAmount(cursorCashReceiveApi.getString(cursorCashReceiveApi.getColumnIndex(DatabaseContract.CASH_RECEIVE.AMOUNT)));
            cashReceiveApi.setCurrencyId(cursorCashReceiveApi.getString(cursorCashReceiveApi.getColumnIndex(DatabaseContract.CASH_RECEIVE.CURRENCY_ID)));
            cashReceiveApi.setStatus(cursorCashReceiveApi.getString(cursorCashReceiveApi.getColumnIndex(DatabaseContract.CASH_RECEIVE.STATUS)));
            cashReceiveApi.setLocationId(cursorCashReceiveApi.getString(cursorCashReceiveApi.getColumnIndex(DatabaseContract.CASH_RECEIVE.LOCATION_ID)));
            cashReceiveApi.setPaymentType(cursorCashReceiveApi.getString(cursorCashReceiveApi.getColumnIndex(DatabaseContract.CASH_RECEIVE.PAYMENT_TYPE)));
            cashReceiveApi.setCashReceiveType(cursorCashReceiveApi.getString(cursorCashReceiveApi.getColumnIndex(DatabaseContract.CASH_RECEIVE.CASH_RECEIVE_TYPE)));
            cashReceiveApi.setSaleId(cursorCashReceiveApi.getString(cursorCashReceiveApi.getColumnIndex(DatabaseContract.CASH_RECEIVE.SALE_ID)));
            cashReceiveApi.setCashReceiveItem(getCashReceiveItemFromDB(cashReceiveApi.getReceiveNo()));
            cashReceiveApiList.add(cashReceiveApi);
        }

        return cashReceiveApiList;
    }

    /**
     * Get cash receive item from DB
     *
     * @param invoiceNo invoiceNo
     * @return CashReceiveItemApi list
     */
    private List<CashReceiveItemApi> getCashReceiveItemFromDB(String invoiceNo) {
        List<CashReceiveItemApi> cashReceiveItemApiList = new ArrayList<>();

        Cursor cursorCashReceiveItemApi = sqLiteDatabase.rawQuery("select * from " + DatabaseContract.CASH_RECEIVE_ITEM.TABLE + " WHERE " + DatabaseContract.CASH_RECEIVE_ITEM.RECEIVE_NO + " = \'" + invoiceNo + "\'", null);
        while (cursorCashReceiveItemApi.moveToNext()) {
            CashReceiveItemApi cashReceiveItemApi = new CashReceiveItemApi();
            cashReceiveItemApi.setReceiveNo(cursorCashReceiveItemApi.getString(cursorCashReceiveItemApi.getColumnIndex(DatabaseContract.CASH_RECEIVE_ITEM.RECEIVE_NO)));
            cashReceiveItemApi.setSaleId(cursorCashReceiveItemApi.getInt(cursorCashReceiveItemApi.getColumnIndex(DatabaseContract.CASH_RECEIVE_ITEM.SALE_ID)));
            cashReceiveItemApiList.add(cashReceiveItemApi);
        }
        return cashReceiveItemApiList;
    }

    /**
     * Convert CashReceive data to json format
     *
     * @param object CashReceiveRequest
     * @return json string
     */
    private String getJsonFromObject(Object object) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonString = gson.toJson(object);
        return jsonString;
    }

    /**
     * Get related location code
     *
     * @return locationCode
     */
    private int getLocationCode() {
        int locationCode = 0;
        Cursor cursorForLocation = sqLiteDatabase.rawQuery("select * from Location", null);
        while (cursorForLocation.moveToNext()) {
            locationCode = cursorForLocation.getInt(cursorForLocation.getColumnIndex(DatabaseContract.Location.id));
        }

        return locationCode;
    }

    /**
     * Upload custimer visit record.
     */
    private void uploadCustomerVisitToServer() {

        String paramData = "";

        //Utils.callDialog("Please wait...", this);

        CustomerVisitRequest customerVisitRequest = new CustomerVisitRequest();

        customerVisitRequest.setSiteActivationKey(Constant.SITE_ACTIVATION_KEY);
        customerVisitRequest.setTabletActivationKey(Constant.TABLET_ACTIVATION_KEY);
        customerVisitRequest.setUserId(saleman_Id);
        customerVisitRequest.setPassword("");//it is empty string bcoz json format using gson cannot accept encrypted
        customerVisitRequest.setData(getCustomerVisit());

        paramData = getJsonFromObject(customerVisitRequest);
        Log.i("Param_CUS_VISIT", paramData);

        UploadService uploadService = RetrofitServiceFactory.createService(UploadService.class);
        Call<InvoiceResponse> call = uploadService.uploadCustomerVisit(paramData);
        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        //Utils.cancelDialog();
                        if (!services.equals("")) {
                            services += ",";
                        }
                        services += "CUSTOMER VISIT RECORD ";
                        uploadSaleManRoute();
                        /*if (!services.equals("")) {
                            services += " are successfully uploaded";
                            Utils.commonDialog(services, SyncActivity.this);
                        }*/
                    } else {
                        if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                            onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                        }
                    }

                } else {
                    Utils.cancelDialog();
                    Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                }
            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);

            }
        });
    }

    /**
     * Download customer visit from server.
     */
    public void downloadCustomerVisitFromServer(String paramData) {
        DownloadService downloadService = RetrofitServiceFactory.createService(DownloadService.class);
        Call<CustomerVisitResponse> call = downloadService.getCustomerVisitFromApi(paramData);
        call.enqueue(new Callback<CustomerVisitResponse>() {
            @Override
            public void onResponse(Call<CustomerVisitResponse> call, Response<CustomerVisitResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        //Utils.cancelDialog();
                        textViewError.setText("");

                        //Toast.makeText(SyncActivity.this, response.body().getAceplusStatusMessage(), Toast.LENGTH_SHORT).show();

                        List<SaleVisitRecord> saleVisitRecordList = new ArrayList<>();
                        saleVisitRecordList = response.body().getCustomerVisitRequestDataList().get(0).getSaleVisitRecordList();
                        Log.i("saleVisitRecordList>>>", saleVisitRecordList.size() + "");

                        sqLiteDatabase.beginTransaction();

                        for (SaleVisitRecord saleVisitRecord : saleVisitRecordList) {
                            deleteDataAfterUpload(DatabaseContract.SALE_VISIT_RECORD.TABLE_DOWNLOAD, null, null);
                            insertSaleVisitRecord(saleVisitRecord);
                        }

                        sqLiteDatabase.setTransactionSuccessful();
                        sqLiteDatabase.endTransaction();
                    } else {
                        Utils.cancelDialog();
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    }

                } else {

                    if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                        onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                        textViewError.setText(response.body().getAceplusStatusMessage());
                    } else {
                        Utils.cancelDialog();
                        Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                    }

                }
            }

            @Override
            public void onFailure(Call<CustomerVisitResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);
            }
        });
    }

    /**
     * Get customer visit request data for api
     *
     * @return CustomerVisitRequestData list
     */
    List<CustomerVisitRequestData> getCustomerVisit() {
        List<CustomerVisitRequestData> customerVisitRequestDataList = new ArrayList<>();
        CustomerVisitRequestData customerVisitRequestData = new CustomerVisitRequestData();
        customerVisitRequestData.setSaleVisitRecordList(getSaleVisitRecord());
        customerVisitRequestDataList.add(customerVisitRequestData);
        return customerVisitRequestDataList;
    }

    /**
     * Get sale visit record from database
     *
     * @return SaleVisitRecord list
     */
    private List<SaleVisitRecord> getSaleVisitRecord() {
        List<SaleVisitRecord> saleVisitRecordList = new ArrayList<>();

        Cursor cursorSaleVisitRecord = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseContract.SALE_VISIT_RECORD.TABLE_UPLOAD, null);
        while (cursorSaleVisitRecord.moveToNext()) {
            SaleVisitRecord saleVisitRecord = new SaleVisitRecord();

            saleVisitRecord.setId(cursorSaleVisitRecord.getInt(cursorSaleVisitRecord.getColumnIndex(DatabaseContract.SALE_VISIT_RECORD.ID)));
            saleVisitRecord.setCustomerId(cursorSaleVisitRecord.getInt(cursorSaleVisitRecord.getColumnIndex(DatabaseContract.SALE_VISIT_RECORD.CUSTOMER_ID)));
            saleVisitRecord.setLatitude(cursorSaleVisitRecord.getString(cursorSaleVisitRecord.getColumnIndex(DatabaseContract.SALE_VISIT_RECORD.LATITUDE)));
            saleVisitRecord.setLongitude(cursorSaleVisitRecord.getString(cursorSaleVisitRecord.getColumnIndex(DatabaseContract.SALE_VISIT_RECORD.LONGITUDE)));
            saleVisitRecord.setSalemanId(cursorSaleVisitRecord.getInt(cursorSaleVisitRecord.getColumnIndex(DatabaseContract.SALE_VISIT_RECORD.SALEMAN_ID)));
            saleVisitRecord.setSaleFlg(cursorSaleVisitRecord.getShort(cursorSaleVisitRecord.getColumnIndex(DatabaseContract.SALE_VISIT_RECORD.SALE_FLG)));
            saleVisitRecord.setVisitFlg(cursorSaleVisitRecord.getShort(cursorSaleVisitRecord.getColumnIndex(DatabaseContract.SALE_VISIT_RECORD.VISIT_FLG)));
            saleVisitRecord.setRecordDate(cursorSaleVisitRecord.getString(cursorSaleVisitRecord.getColumnIndex(DatabaseContract.SALE_VISIT_RECORD.RECORD_DATE)));
            ;
            saleVisitRecordList.add(saleVisitRecord);
        }
        return saleVisitRecordList;
    }

    /**
     * insert SALE VISIT RECORD to database.
     *
     * @param saleVisitRecord saleVisitRecord
     */
    private void insertSaleVisitRecord(SaleVisitRecord saleVisitRecord) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.SALE_VISIT_RECORD.CUSTOMER_ID, saleVisitRecord.getCustomerId());
        cv.put(DatabaseContract.SALE_VISIT_RECORD.SALEMAN_ID, saleVisitRecord.getSalemanId());
        cv.put(DatabaseContract.SALE_VISIT_RECORD.LATITUDE, saleVisitRecord.getLatitude());
        cv.put(DatabaseContract.SALE_VISIT_RECORD.LONGITUDE, saleVisitRecord.getLongitude());
        cv.put(DatabaseContract.SALE_VISIT_RECORD.VISIT_FLG, saleVisitRecord.getVisitFlg());
        cv.put(DatabaseContract.SALE_VISIT_RECORD.SALE_FLG, saleVisitRecord.getSaleFlg());
        cv.put(DatabaseContract.SALE_VISIT_RECORD.RECORD_DATE, saleVisitRecord.getRecordDate());
        sqLiteDatabase.insert(DatabaseContract.SALE_VISIT_RECORD.TABLE_DOWNLOAD, null, cv);
    }

    /***
     * PL
     ***/

    private void uploadSaleManRoute() {
        String paramData = "";
        SaleManRouteRequest saleManRouteRequest = new SaleManRouteRequest();
        saleManRouteRequest.setSiteActivationKey(Constant.SITE_ACTIVATION_KEY);
        saleManRouteRequest.setTabletActivationKey(Constant.TABLET_ACTIVATION_KEY);
        saleManRouteRequest.setUserId(saleman_Id);
        saleManRouteRequest.setPassword("");//it is empty string bcoz json format using gson cannot accept encrypted

        List<ERouteReport> eRouteReportList = getDatasForSaleManRoute();
        List<DataForSaleManRoute> dataForSaleManRouteList = new ArrayList<>();
        DataForSaleManRoute dataForSaleManRoute = new DataForSaleManRoute();
        dataForSaleManRoute.setERouteReport(eRouteReportList);
        dataForSaleManRouteList.add(dataForSaleManRoute);
        saleManRouteRequest.setData(dataForSaleManRouteList);

        paramData = getJsonFromObject(saleManRouteRequest);
        Log.i("Param_SAL_ROUTE", paramData);

        UploadService uploadService = RetrofitServiceFactory.createService(UploadService.class);
        Call<InvoiceResponse> call = uploadService.uploadSaleManRoute(paramData);
        call.enqueue(new Callback<InvoiceResponse>() {
            @Override
            public void onResponse(Call<InvoiceResponse> call, Response<InvoiceResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        Utils.cancelDialog();
                        if (!services.equals("")) {
                            services += ",";
                        }
                        services += "E ROUTE REPORT ";
                        if (!services.equals("")) {
                            services += " are successfully uploaded";
                            Utils.commonDialog(services, SyncActivity.this);
                        }
                    } else {
                        if (response.body() != null && response.body().getAceplusStatusMessage().length() != 0) {
                            onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                        }
                    }

                } else {
                    Utils.cancelDialog();
                    Utils.commonDialog(getResources().getString(R.string.server_error), SyncActivity.this);
                }
            }

            @Override
            public void onFailure(Call<InvoiceResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(t.getMessage(), SyncActivity.this);
            }
        });
    }

    private ArrayList<ERouteReport> getDatasForSaleManRoute() {
        ArrayList<ERouteReport> eRouteReportArrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DatabaseContract.temp_for_saleman_route.TABLE, null);
        while (cursor.moveToNext()) {
            ERouteReport eRouteReport = new ERouteReport();
            eRouteReport.setCustomerId(cursor.getInt(cursor.getColumnIndex(DatabaseContract.temp_for_saleman_route.CUSTOMER_ID)));
            eRouteReport.setSaleManId(cursor.getInt(cursor.getColumnIndex(DatabaseContract.temp_for_saleman_route.SALEMAN_ID)));
            eRouteReport.setRouteId(cursor.getInt(cursor.getColumnIndex(DatabaseContract.temp_for_saleman_route.ROUTE_ID)));
            eRouteReport.setArrivalTime(cursor.getString(cursor.getColumnIndex(DatabaseContract.temp_for_saleman_route.ARRIVAL_TIME)));
            eRouteReport.setDepartureTime(cursor.getString(cursor.getColumnIndex(DatabaseContract.temp_for_saleman_route.DEPARTURE_TIME)));
            eRouteReport.setLatitude(cursor.getDouble(cursor.getColumnIndex(DatabaseContract.temp_for_saleman_route.LATITUDE)));
            eRouteReport.setLongitude(cursor.getDouble(cursor.getColumnIndex(DatabaseContract.temp_for_saleman_route.LONGITUDE)));

            eRouteReportArrayList.add(eRouteReport);
        }
        return eRouteReportArrayList;
    }

    @OnClick(R.id.cancel_img)
    void back() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Utils.backToHome(this);
    }
}
