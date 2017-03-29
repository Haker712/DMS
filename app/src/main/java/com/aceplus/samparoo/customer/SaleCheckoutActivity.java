package com.aceplus.samparoo.customer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aceplus.samparoo.LoginActivity;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Category;
import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.model.Product;
import com.aceplus.samparoo.model.Promotion;
import com.aceplus.samparoo.model.SoldProduct;
import com.aceplus.samparoo.utils.Constant;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by haker on 2/3/17.
 */
public class SaleCheckoutActivity extends AppCompatActivity {

    public static final String REMAINING_AMOUNT_KEY = "remaining-amount-key";

    public static final String USER_INFO_KEY = "user-info-key";
    public static final String CUSTOMER_INFO_KEY = "customer-info-key";
    public static final String SOLD_PROUDCT_LIST_KEY = "sold-product-list-key";
    public static final String PRESENT_PROUDCT_LIST_KEY = "presnet-product-list-key";
    public static final String ORDERED_INVOICE_KEY = "ordered_invoice_key";

    public static final String SALE_EXCHANGE_INVOICEID_KEY = "sale_exchange_invoiceid_key";
    public static final String DATE_KEY = "date_key";

    private TextView titleTextView;
    ListView soldProductsListView, promotionPlanItemListView, promotionPlanGiftListView;
    TextView invoiceIdTextView;
    TextView saleDateTextView;
    TextView totalAmountTextView;
    TextView advancedPaidAmountTextView;
    TextView discountTextView;
    TextView netAmountTextView;
    EditText payAmountEditText;
    TextView refundTextView;
    EditText receiptPersonEditText;
    private EditText prepaidAmountEditText;
    ImageView backImg, confirmAndPrintImg;

    SQLiteDatabase database;
    SoldProductListRowAdapter soldProductListRowAdapter;

    private JSONObject orderedInvoice;
    JSONObject salemanInfo;
    Customer customer;
    ArrayList<SoldProduct> soldProductList;
    ArrayList<String> product;

    private Category[] categories;

    private boolean isPreOrder;
    private boolean isDelivery;
    private double remainingAmount;

    ArrayList<Promotion> promotionArrayList = new ArrayList<>();
    PromotionProductCustomAdapter promotionProductCustomAdapter;

    Double totalVolumeDiscount = 0.0;
    int exclude = 0;
    double totalAmount = 0.0;

    int locationCode = 0;
    String locationCodeName = "";

    String check;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_checkout);

        database = new Database(this).getDataBase();

        /*try {
            orderedInvoice = new JSONObject(getIntent().getStringExtra(SaleActivity.ORDERED_INVOICE_KEY));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        customer = (Customer) getIntent().getSerializableExtra(CUSTOMER_INFO_KEY);
        soldProductList = (ArrayList<SoldProduct>) getIntent().getSerializableExtra(SOLD_PROUDCT_LIST_KEY);

        registerIDs();

        findViewById(R.id.advancedPaidAmountLayout).setVisibility(View.GONE);
        findViewById(R.id.totalInfoForPreOrder).setVisibility(View.GONE);

        findViewById(R.id.tableHeaderDiscount).setVisibility(View.GONE);


        for (SoldProduct soldProduct : soldProductList) {

            totalAmount += soldProduct.getTotalAmount();
        }
        totalAmountTextView.setText(Utils.formatAmount(totalAmount));

        saleDateTextView.setText(Utils.getCurrentDate(false));

        Cursor cursorForLocation = database.rawQuery("select * from Location", null);
        while (cursorForLocation.moveToNext()) {
            locationCode = cursorForLocation.getInt(cursorForLocation.getColumnIndex(DatabaseContract.Location.id));
            locationCodeName = cursorForLocation.getString(cursorForLocation.getColumnIndex(DatabaseContract.Location.no));
        }

        Intent intent = this.getIntent();

        if (intent != null) {

            check = intent.getExtras().getString("SaleExchange");
            if (check.equalsIgnoreCase("yes")) {

                double totalItemDiscountAmount = 0.0;

                titleTextView.setText("SALE EXCHANGE");
                invoiceIdTextView.setText(Utils.getInvoiceNo(this, LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NO, ""), locationCode + "", Utils.FOR_SALE_EXCHANGE));
                View layout=findViewById(R.id.SaleExchangeLayout);
                layout.setVisibility(View.VISIBLE);

                TextView textView_salereturnAmount= (TextView) findViewById(R.id.salereturnAmount);
                TextView textView_payAmtfromCustomer= (TextView) findViewById(R.id.payamountfromcustomer);
                TextView textView_refundtoCustomer= (TextView) findViewById(R.id.refundtocustomer);

                Double salereturnAmount=getIntent().getDoubleExtra(Constant.KEY_SALE_RETURN_AMOUNT,0.0);
                Double saleexchangeAmount= Double.valueOf(Utils.formatAmount(totalAmount - totalItemDiscountAmount - totalVolumeDiscount));

                textView_salereturnAmount.setText(salereturnAmount+"");

                if (saleexchangeAmount>salereturnAmount){

                    Double payAmtfromCustomer=saleexchangeAmount-salereturnAmount;
                    textView_payAmtfromCustomer.setText(payAmtfromCustomer+"");

                }else {

                    double refundAmount=salereturnAmount-saleexchangeAmount;

                    textView_refundtoCustomer.setText(refundAmount+"");

                }

//                textView_salereturnAmount.setText((int) getIntent().getDoubleExtra(Constant.KEY_SALE_RETURN_AMOUNT,0.0));





            } else {

                invoiceIdTextView.setText(Utils.getInvoiceNo(this, LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NO, ""), locationCode + "", Utils.FOR_OTHERS));
            }
        }
        soldProductListRowAdapter = new SoldProductListRowAdapter(this);
        soldProductsListView.setAdapter(soldProductListRowAdapter);

        double totalAmountForVolumeDiscount = 0.0;
        double totalItemDiscountAmount = 0.0;
        for (SoldProduct soldProduct : soldProductList) {

//			totalAmount += soldProduct.getTotalAmount();
            if (soldProduct.getProduct().getDiscountType().equalsIgnoreCase("v")) {

                totalAmountForVolumeDiscount += soldProduct.getTotalAmount();
            }
            totalItemDiscountAmount += soldProduct.getDiscountAmount(this) + soldProduct.getExtraDiscountAmount();
        }
        totalAmountTextView.setText(Utils.formatAmount(totalAmount));

        /*Cursor cursor = database.rawQuery(
                "SELECT DISCOUNT_PERCENT, DISCOUNT_AMOUNT"
                        + " FROM VOLUME_DISCOUNT WHERE"
                        + " FROM_AMOUNT <= " + totalAmountForVolumeDiscount
                        + " AND TO_AMOUNT >= " + totalAmountForVolumeDiscount, null);
        if (cursor.getCount() == 1) {
            cursor.moveToNext();
            if (cursor.getDouble(cursor.getColumnIndex("DISCOUNT_AMOUNT")) != 0) {

                totalVolumeDiscount = cursor.getDouble(cursor.getColumnIndex("DISCOUNT_AMOUNT"));
            } else {

                totalVolumeDiscount = totalAmountForVolumeDiscount
                        * cursor.getDouble(cursor.getColumnIndex("DISCOUNT_PERCENT")) / 100;
            }
        }*/

        //discountTextView.setText(Utils.formatAmount(totalItemDiscountAmount + totalVolumeDiscount));

        calculateVolumeDiscount();
        calculateInvoiceDiscount();

        netAmountTextView.setText(Utils.formatAmount(totalAmount - totalItemDiscountAmount - totalVolumeDiscount));

        double a = Double.parseDouble(this.netAmountTextView.getText().toString().replace(",", ""));
        if (Double.parseDouble(this.netAmountTextView.getText().toString().replace(",", ""))
                <= this.remainingAmount) {

            findViewById(R.id.payAmountLayout).setVisibility(View.GONE);
        }

        //initCategories();

        showPromotionData();

        catchEvents();
    }

    private void calculateVolumeDiscount() {
        String volDisFilterId = "";
        String category = "", group = "";
        double discount_percent = 0.0, discount_amt = 0.0, discount_price = 0.0;

        String categoryProduct = "", groupProduct = "";

        boolean isAllAreCategory = false;

        double buy_amt = totalAmount;
        try {
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
                Log.i("cusorForVolDisFilterItem", cusorForVolDisFilterItem.getCount() + "");
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
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        discountTextView.setText(Utils.formatAmount(totalVolumeDiscount));
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

        discountTextView.setText(Utils.formatAmount(totalVolumeDiscount));
    }

    private void registerIDs() {
        this.titleTextView = (TextView) findViewById(R.id.title);
        soldProductsListView = (ListView) findViewById(R.id.soldProductList);
        promotionPlanItemListView = (ListView) findViewById(R.id.promotion_plan_item_listview);
        promotionPlanGiftListView = (ListView) findViewById(R.id.promotion_plan_gift_listview);
        saleDateTextView = (TextView) findViewById(R.id.saleDateTextView);
        invoiceIdTextView = (TextView) findViewById(R.id.invoiceId);
        totalAmountTextView = (TextView) findViewById(R.id.totalAmount);

        advancedPaidAmountTextView = (TextView) findViewById(R.id.advancedPaidAmount);

        discountTextView = (TextView) findViewById(R.id.volumeDiscount);
        netAmountTextView = (TextView) findViewById(R.id.netAmount);
        payAmountEditText = (EditText) findViewById(R.id.payAmount);
        refundTextView = (TextView) findViewById(R.id.refund);
        receiptPersonEditText = (EditText) findViewById(R.id.receiptPerson);

        this.prepaidAmountEditText = (EditText) findViewById(R.id.prepaidAmount);

        backImg = (ImageView) findViewById(R.id.back_img);
        confirmAndPrintImg = (ImageView) findViewById(R.id.confirmAndPrint_img);

    }

    private void initCategories() {

        if (categories == null) {

            SQLiteDatabase db = (new Database(this)).getDataBase();

            Cursor cursor = db.rawQuery(
                    "SELECT CATEGORY_ID, CATEGORY_NAME"
                            + " FROM PRODUCT_CATEGORY"
                            + " GROUP BY CATEGORY_ID, CATEGORY_NAME", null);

            categories = new Category[cursor.getCount()];
            while (cursor.moveToNext()) {
                categories[cursor.getPosition()] = new Category(cursor.getString(cursor.getColumnIndex("CATEGORY_ID")), cursor.getString(cursor.getColumnIndex("CATEGORY_NAME")));
                categories[cursor.getPosition()].setProducts(getProducts(categories[cursor.getPosition()].getId()));
            }
        }
    }

    private Product[] getProducts(String categoryId) {

        Product[] products;

        SQLiteDatabase db = (new Database(this)).getDataBase();

        Cursor cursor = db.rawQuery(
                "SELECT PRODUCT_ID, PRODUCT_NAME, SELLING_PRICE"
                        + ", PURCHASE_PRICE, DISCOUNT_TYPE, REMAINING_QTY"
                        + " FROM PRODUCT WHERE CATEGORY_ID = '" + categoryId + "'", null);

        products = new Product[cursor.getCount()];
        while (cursor.moveToNext()) {

            Product tempProduct = new Product(
                    cursor.getString(cursor.getColumnIndex("PRODUCT_ID"))
                    , cursor.getString(cursor.getColumnIndex("PRODUCT_NAME"))
                    , cursor.getDouble(cursor.getColumnIndex("SELLING_PRICE"))
                    , cursor.getDouble(cursor.getColumnIndex("PURCHASE_PRICE"))
                    , cursor.getString(cursor.getColumnIndex("DISCOUNT_TYPE"))
                    , cursor.getInt(cursor.getColumnIndex("REMAINING_QTY")));

            products[cursor.getPosition()] = tempProduct;
        }

        return products;
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

    private void catchEvents() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        payAmountEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int arg1, int arg2, int arg3) {

                /*if (charSequence.toString().length() > 0) {

                    String convertedString = charSequence.toString();
                    convertedString = Utils.formatAmount(Double.parseDouble(charSequence.toString().replace(",", "")));
                    if (!payAmountEditText.getText().toString().equals(convertedString)
                            && convertedString.length() > 0) {

                        payAmountEditText.setText(convertedString);
                        payAmountEditText.setSelection(payAmountEditText.getText().length());
                    }
                }*/
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String tempPayAmount = editable.toString().replace(",", "");
                //Log.i("tempPayAmount", tempPayAmount);
                String tempNetAmount = netAmountTextView.getText().toString().replace(",", "");
                //Log.i("tempNetAmount", tempNetAmount);

                if (tempPayAmount.length() > 0 && tempNetAmount.length() > 0) {

                    if (Double.parseDouble(tempPayAmount) >= Double.parseDouble(tempNetAmount)) {

                        refundTextView.setText(Utils.formatAmount(Double.parseDouble(tempPayAmount) - Double.parseDouble(tempNetAmount)));
                    } else {

                        refundTextView.setText("0");
                    }
                }
            }
        });

        confirmAndPrintImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFullyPaid()) {
                    saveDatas();
                    if (check.equalsIgnoreCase("yes")) {
                        Intent intent = new Intent(SaleCheckoutActivity.this, SaleExchangeInfoActivity.class);
                        intent.putExtra(CUSTOMER_INFO_KEY, customer);
                        intent.putExtra(SaleActivity.SALE_RETURN_INVOICEID_KEY, getIntent().getStringExtra(SaleActivity.SALE_RETURN_INVOICEID_KEY));
                        intent.putExtra(SALE_EXCHANGE_INVOICEID_KEY, invoiceIdTextView.getText().toString());
                        intent.putExtra(DATE_KEY, saleDateTextView.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                    else {

                        if(isSameCustomer(customer.getId())) {

                            updateSaleVisitRecord(customer.getId());
                        }

                        Utils.backToCustomer(SaleCheckoutActivity.this);
                    }
                } else {
                    Utils.commonDialog("Insufficient Pay Amount!", SaleCheckoutActivity.this);
                }
                /*Intent intent = new Intent(SaleCheckoutActivity.this, SalePrintActivity.class);
                intent.putExtra(SalePrintActivity.CUSTOMER_INFO_KEY, customer);
                intent.putExtra(SalePrintActivity.SOLD_PRODUCT_LIST_KEY, soldProductList);
                intent.putExtra(SalePrintActivity.INVOICE_ID_KEY, invoiceIdTextView.getText().toString());
                startActivity(intent);
                finish();*/
            }
        });
    }

    /**
     * Update SALE VISIT RECORD of related customer id
     *
     * @param customerId customer number
     */
    private void updateSaleVisitRecord(int customerId) {
        ContentValues cv = new ContentValues();
        String where = DatabaseContract.SALE_VISIT_RECORD.CUSTOMER_ID + "=?";
        String[] whereArgs = new String[] {String.valueOf(customerId)};
        cv.put(DatabaseContract.SALE_VISIT_RECORD.SALE_FLG, 1);
        database.update(DatabaseContract.SALE_VISIT_RECORD.TABLE_UPLOAD, cv, where, whereArgs);
    }

    /**
     * Check it is the same location for customer.
     *
     * @param customerId customer row number
     *
     * @return true: if that location is correct; otherwise false.
     */
    private boolean isSameCustomer(int customerId) {
        Cursor locationCursor = database.rawQuery("SELECT LATITUDE, LONGITUDE FROM CUSTOMER WHERE ID = " + customerId, null);
        String latiString = "", longiString = "";
        Double latitude = 0.0, longitude = 0.0, latiDouble = 0.0, longDouble = 0.0;

        while(locationCursor.moveToNext()) {
            latiString = locationCursor.getString(locationCursor.getColumnIndex("LATITUDE"));
            longiString = locationCursor.getString(locationCursor.getColumnIndex("LONGITUDE"));
        }

        if(latiString != null && longiString != null) {
            latiDouble = Double.parseDouble(latiString.substring(0, 6));
            longDouble = Double.parseDouble(longiString.substring(0, 6));
        }

        GPSTracker gpsTracker = new GPSTracker(SaleCheckoutActivity.this);
        if (gpsTracker.canGetLocation()) {
            String lat = String.valueOf(gpsTracker.getLatitude());
            String lon = String.valueOf(gpsTracker.getLongitude());

            if(!lat.equals(null) && !lon.equals(null) && lat.length() > 5 && lon.length() > 5){
                latitude = Double.parseDouble(lat.substring(0,6));
                longitude = Double.parseDouble(lon.substring(0,6));
            }
        } else {
            gpsTracker.showSettingsAlert();
        }

        if(latiDouble != null && longDouble !=null && latitude != null && longitude != null) {

            if(latitude >= (latiDouble - 0.001) && latitude <= (latiDouble + 0.001) && longitude >= (longDouble - 0.001) && longitude <= (longDouble + 0.001)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Check for fully paid
     *
     * @return true : fully paid; false : not fully paid
     */
    private boolean isFullyPaid() {
        double pay_amount = 0.0, net_amount = 0.0;

        if(!payAmountEditText.getText().toString().equals("")) {
            pay_amount = Double.parseDouble(payAmountEditText.getText().toString());
        }

        if(!netAmountTextView.getText().toString().equals("")) {
            net_amount = Double.parseDouble(netAmountTextView.getText().toString().replace(",", ""));
        }

        if(pay_amount == 0.0 || pay_amount < net_amount) {
            return false;
        } else {
            return true;
        }
    }

    private void saveDatas() {
        String customerId = customer.getCustomerId();
        String saleDate = Utils.getCurrentDate(true);

        String invoiceId = invoiceIdTextView.getText().toString();

        double totalDiscountAmount = 0.0;
        if(discountTextView.getText().toString() != null && !discountTextView.getText().toString().equals("")) {
            totalDiscountAmount = Double.parseDouble(discountTextView.getText().toString().replace(",", ""));
        }

        double totalAmount = 0.0;
        if(netAmountTextView.getText().toString() != null && !netAmountTextView.getText().toString().equals("")) {
            totalAmount = Double.parseDouble(netAmountTextView.getText().toString().replace(",", ""))
                    + totalDiscountAmount;
        }

        double payAmount = 0.0;
        if(payAmountEditText.getText().toString() != null && !payAmountEditText.getText().toString().equals("")) {
            payAmount = Double.parseDouble(payAmountEditText.getText().toString().replace(",", ""));
        }

        double refundAmount = 0.0;
        if(refundTextView.getText().toString() != null && !refundTextView.getText().toString().equals("")) {
            refundAmount = Double.parseDouble(refundTextView.getText().toString().replace(",", ""));
        }

        String receiptPersonName = receiptPersonEditText.getText().toString();

        String salePersonId = LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, "");

        String invoiceTime = Utils.getCurrentDate(true);
        Log.i("invoiceTime", invoiceTime);

        String dueDate = saleDate;
        String cashOrCredit = "C";


        String deviceId = Utils.getDeviceId(this);

        int totolQtyForInvoice = 0;

        database.beginTransaction();
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

            database.execSQL("UPDATE PRODUCT SET REMAINING_QTY = REMAINING_QTY - " + soldProduct.getQuantity()
                    + ", SOLD_QTY = SOLD_QTY + " + soldProduct.getQuantity() + " WHERE PRODUCT_ID = \'" + soldProduct.getProduct().getId() + "\'");

            for (Promotion promotion : promotionArrayList) {
                database.execSQL("UPDATE PRODUCT SET PRESENT_QTY = PRESENT_QTY + " + promotion.getPromotionQty() + " WHERE PRODUCT_ID = \'" + soldProduct.getProduct().getId() + "\'");
            }
        }
        database.execSQL("INSERT INTO INVOICE VALUES (\""
                + customerId + "\", \""
                + saleDate + "\", \""
                + invoiceId + "\", \""
                + totalAmount + "\", \""
                + totalDiscountAmount + "\", \""
                + payAmount + "\", \""
                + refundAmount + "\", \""
                + receiptPersonName + "\", \""
                + salePersonId + "\", \""
                + dueDate + "\", \""
                + cashOrCredit + "\", \""
                + locationCode + "\", \""
                + deviceId + "\", \""
                + invoiceTime + "\", "
                + null + ", "
                + null + ", "
                + null + ", "
                + null + ", \""
                + invoiceId + "\", "
                + totolQtyForInvoice
                + ")");

        for (Promotion promotion : promotionArrayList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("tsale_id", invoiceId);
            contentValues.put("stock_id", promotion.getPromotionProductId());
            contentValues.put("quantity", promotion.getPromotionQty());
            contentValues.put("pc_address", deviceId);
            contentValues.put("location_id", locationCode);
            contentValues.put("price", promotion.getPromotionPrice());
            database.insert("INVOICE_PRESENT", null, contentValues);
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SaleCheckoutActivity.this
                , SaleActivity.class);
        intent.putExtra(SaleActivity.REMAINING_AMOUNT_KEY, this.remainingAmount);
        intent.putExtra(SaleActivity.CUSTOMER_INFO_KEY, this.customer);
        intent.putExtra(SaleActivity.SOLD_PROUDCT_LIST_KEY, this.soldProductList);
        /*if (this.orderedInvoice != null) {
            intent.putExtra(SaleActivity.ORDERED_INVOICE_KEY, this.orderedInvoice.toString());
        }*/
        intent.putExtra("SaleExchange", "no");
        startActivity(intent);
    }

    private class SoldProductListRowAdapter extends ArrayAdapter<SoldProduct> {

        final Activity context;

        public SoldProductListRowAdapter(Activity context) {

            super(context, R.layout.list_row_sold_product_checkout, soldProductList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final SoldProduct soldProduct = soldProductList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_sold_product_checkout, null, true);

            final TextView nameTextView = (TextView) view.findViewById(R.id.name);
            final TextView umTextView = (TextView) view.findViewById(R.id.um);
            final TextView qtyTextView = (TextView) view.findViewById(R.id.qty);
            final TextView priceTextView = (TextView) view.findViewById(R.id.price);
            final TextView discountTextView = (TextView) view.findViewById(R.id.discount);
            discountTextView.setVisibility(View.GONE);
            final TextView totalAmountTextView = (TextView) view.findViewById(R.id.amount);

            nameTextView.setText(soldProduct.getProduct().getName());
            umTextView.setText(soldProduct.getProduct().getUm());
            qtyTextView.setText(soldProduct.getQuantity() + "");
            if (soldProduct.getPromotionPrice() == 0.0) {
                priceTextView.setText(Utils.formatAmount(soldProduct.getProduct().getPrice()));
            }
            else {
                priceTextView.setText(Utils.formatAmount(soldProduct.getPromotionPrice()));
            }

            double discountPercent = soldProduct.getDiscount(context) + soldProduct.getExtraDiscount();

            discountTextView.setText(discountPercent + "%");

            Double totalAmount = 0.0;
            if (soldProduct.getPromotionPrice() == 0.0) {
                totalAmount = soldProduct.getProduct().getPrice() * soldProduct.getQuantity();
            }
            else {
                totalAmount = soldProduct.getPromotionPrice() * soldProduct.getQuantity();
            }
            Double discount = totalAmount * discountPercent / 100;
            totalAmountTextView.setText(Utils.formatAmount(totalAmount - discount));

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
            if (promotion.getPromotionPrice() != 0.0) {
                priceTextView.setText(promotion.getPromotionPrice() + "");
            } else {
                priceTextView.setVisibility(View.GONE);
            }

            return view;
        }
    }
}
