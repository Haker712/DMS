package com.aceplus.samparoo.customer;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aceplus.samparoo.LoginActivity;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.model.Posm;
import com.aceplus.samparoo.model.PosmByCustomer;
import com.aceplus.samparoo.model.SoldProduct;
import com.aceplus.samparoo.utils.Constant;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by yma on 2/21/17.
 *
 * PosmCheckOutActivity
 */

public class PosmCheckOutActivity extends AppCompatActivity{

    public static final String CUSTOMER_INFO_KEY = "customer-info-key";
    public static final String SOLD_PROUDCT_LIST_KEY = "sold-product-list-key";
    public static final String PRESENT_PROUDCT_LIST_KEY = "presnet-product-list-key";

    SQLiteDatabase database;

    private ArrayList<SoldProduct> soldProductList = new ArrayList<SoldProduct>();

    //for present product
    private ArrayList<String> products = new ArrayList<String>();

    private ImageView img_back, img_confirmAndPrint;

    private TextView txt_invoiceId, txt_totalAmount, saleDateTextView;

    private TextView txt_tableHeaderDiscount, txt_tableHeaderUM;

    private LinearLayout advancedPaidAmountLayout, totalInfoForGeneralSale, totalInfoForPreOrder;

    private ListView lv_soldProductList;

    private Customer customer;

    int customerId;

    int locationCode = 0;
    String locationCodeName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_checkout);

        soldProductList = (ArrayList<SoldProduct>) getIntent().getSerializableExtra(this.SOLD_PROUDCT_LIST_KEY);

        products = (ArrayList<String>) getIntent().getSerializableExtra(this.PRESENT_PROUDCT_LIST_KEY);

        customer = (Customer) getIntent().getSerializableExtra(CUSTOMER_INFO_KEY);

        database = new Database(this).getDataBase();

        customerId = getCustomerId();

        Cursor cursorForLocation = database.rawQuery("select * from Location", null);
        while (cursorForLocation.moveToNext()) {
            locationCode = cursorForLocation.getInt(cursorForLocation.getColumnIndex(DatabaseContract.Location.id));
            locationCodeName = cursorForLocation.getString(cursorForLocation.getColumnIndex(DatabaseContract.Location.no));
        }

        registerIDs();
        hideUnnecessaryView();
        setSoldProductToListView();
        setSoldProductInformation();
        catchEvents();
    }

    /**
     * Register all view ids that are related to this activity
     */
    private void registerIDs() {

        TextView textViewTitle = (TextView) findViewById(R.id.title);
        textViewTitle.setText(R.string.posm_checkout);

        img_back = (ImageView) findViewById(R.id.back_img);
        img_confirmAndPrint = (ImageView) findViewById(R.id.confirmAndPrint_img);
        txt_tableHeaderUM = (TextView) findViewById(R.id.tableHeaderUM);
        txt_tableHeaderDiscount = (TextView) findViewById(R.id.tableHeaderDiscount);

        saleDateTextView = (TextView) findViewById(R.id.saleDateTextView);
        txt_invoiceId = (TextView) findViewById(R.id.invoiceId);
        txt_totalAmount = (TextView) findViewById(R.id.totalAmount);

        advancedPaidAmountLayout = (LinearLayout) findViewById(R.id.advancedPaidAmountLayout);
        totalInfoForGeneralSale =(LinearLayout) findViewById(R.id.totalInfoForGeneralSale);
        totalInfoForPreOrder = (LinearLayout) findViewById(R.id.totalInfoForPreOrder) ;
        lv_soldProductList = (ListView) findViewById(R.id.soldProductList);
    }

    /**
     * Hide views that are not related.
     */
    private void hideUnnecessaryView() {
        txt_tableHeaderUM.setVisibility(View.GONE);
        txt_tableHeaderDiscount.setVisibility(View.GONE);
        advancedPaidAmountLayout.setVisibility(View.GONE);
        totalInfoForGeneralSale.setVisibility(View.GONE);
        totalInfoForPreOrder.setVisibility(View.GONE);
    }

    /**
     * Set data to list view.
     */
    private void setSoldProductToListView() {
        lv_soldProductList.setAdapter(new SoldProductCustomAdapter(this));
    }

    private void setSoldProductInformation() {
        saleDateTextView.setText(Utils.getCurrentDate(false));
        txt_invoiceId.setText(Utils.getInvoiceNoForPOSM(this, LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NO, ""), locationCode + ""));

        double totalAmount = 0.0;
        for (SoldProduct soldProduct : soldProductList) {

            totalAmount += soldProduct.getTotalAmount();
        }

        txt_totalAmount.setText(Utils.formatAmount(totalAmount));
    }

    /**
     * Action to events
     */
    private void catchEvents() {

        // back image (cross btn) event listener
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PosmCheckOutActivity.this.onBackPressed();
            }
        });

        // confirm image (nike btn) event listener
        img_confirmAndPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertPosmByCustomer();
                Intent intent = new Intent(PosmCheckOutActivity.this
                        , PosmActivity.class);
                intent.putExtra(PosmActivity.CUSTOMER_INFO_KEY, customer);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Insert PosmByCustomer to database.
     */
    void insertPosmByCustomer() {

        for(SoldProduct soldProduct : soldProductList) {
            PosmByCustomer posmByCustomer = new PosmByCustomer();

            if(PosmCheckOutActivity.this.txt_invoiceId.getText() != null) {
                posmByCustomer.setInvoiceNo(PosmCheckOutActivity.this.txt_invoiceId.getText().toString());
            }

            if(PosmCheckOutActivity.this.customer != null) {
                posmByCustomer.setCustomerId(customerId);
            }

            posmByCustomer.setStockId(soldProduct.getProduct().getStockId());

            posmByCustomer.setShopTypeId(getShopTypeIdByStockId(soldProduct.getProduct().getStockId()));

            posmByCustomer.setInvoiceDate((new SimpleDateFormat("yyyy/MM/dd").format(new Date())));

            posmByCustomer.setSaleManId(LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, ""));

            posmByCustomer.setQuantity(soldProduct.getQuantity());

            posmByCustomer.setPrice(soldProduct.getProduct().getPrice());

            database.beginTransaction();

            database.execSQL("INSERT INTO POSM_BY_CUSTOMER VALUES (\""
                + posmByCustomer.getInvoiceNo() + "\", \""
                + posmByCustomer.getInvoiceDate() + "\", \""
                + posmByCustomer.getCustomerId() + "\", \""
                + posmByCustomer.getStockId() + "\", \""
                + posmByCustomer.getShopTypeId() + "\", \""
                + posmByCustomer.getSaleManId() + "\", \""
                + posmByCustomer.getQuantity() + "\", \""
                + posmByCustomer.getPrice() + "\", "
                + 0
                + ")");

            database.setTransactionSuccessful();
            database.endTransaction();
        }
    }

    /**
     * Get customer id from customer table.
     *
     * @return id of customer
     */
    private int getCustomerId(){
        Cursor cursor = database.rawQuery("SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = \'" + customer.getCustomerId() + "\';", null);
        int id = 0;
        while(cursor.moveToNext()) {
            id = cursor.getInt(cursor.getColumnIndex("id"));
        }
        return id;
    }

    /**
     * Get shop type id by stock id from POSM table.
     *
     * @param stockId
     * @return
     */
    private int getShopTypeIdByStockId(int stockId) {
        SQLiteDatabase db = (new Database(this)).getDataBase();
        Cursor cursor = db.rawQuery("SELECT SHOP_TYPE_ID FROM POSM WHERE STOCK_ID = " + stockId, null);
        int productId = 0;
        while (cursor.moveToNext()) {
            productId = cursor.getInt(cursor.getColumnIndex("SHOP_TYPE_ID"));
        }
        return productId;
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

            txt_um.setVisibility(View.GONE);
            txt_discount.setVisibility(View.GONE);

            txt_name.setText(String.valueOf(soldProduct.getProduct().getName()));
            txt_qty.setText(String.valueOf(soldProduct.getQuantity()));
            txt_price.setText(String.valueOf(soldProduct.getProduct().getPrice()));

            double discountPercent = soldProduct.getDiscount(context) + soldProduct.getExtraDiscount();
            Double totalAmount = soldProduct.getProduct().getPrice() * soldProduct.getQuantity();
            Double discount = totalAmount * discountPercent / 100;
            txt_amount.setText(Utils.formatAmount(totalAmount - discount));

            return view;
        }
    }
}
