package com.aceplus.samparoo.customer;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aceplus.samparoo.CustomerVisitActivity;
import com.aceplus.samparoo.model.Deliver;
import com.aceplus.samparoo.model.DeliverItem;
import com.aceplus.samparoo.model.forApi.DeliveryPresentForApi;
import com.aceplus.samparoo.utils.Database;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yma on 2/22/17.
 *
 * DeliveryActivity
 */

public class DeliveryActivity extends AppCompatActivity {

    private TextView titleTextView;

    private ImageView cancelImg;

    SQLiteDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_home);
        this.titleTextView = (TextView) findViewById(R.id.reportTitle);
        cancelImg = (ImageView) findViewById(R.id.cancel_img);

        findViewById(R.id.reports).setVisibility(View.GONE);

        database = new Database(this).getDataBase();

        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DeliveryActivity.this, CustomerVisitActivity.class);
                startActivity(intent);
            }
        });

        this.titleTextView.setText(R.string.delivery);

        FragmentTransaction fragmentTransaction = getFragmentManager()
                .beginTransaction();
        FragmentDeliveryReport deliveryReportFragment = new FragmentDeliveryReport();
        deliveryReportFragment.deliveryReportsArrayList = getCustomerAndDelivery();
        deliveryReportFragment.isDelivery = true;
        fragmentTransaction.replace(R.id.fragment_report,
                deliveryReportFragment).commit();
    }

    /**
     * Get delivery data related to specific customer
     *
     * @return deliver list
     */
    private List<Deliver> getCustomerAndDelivery() {

        Cursor cursor = database.rawQuery("SELECT CUSTOMER.ID AS CID, " +
                "CUSTOMER.CUSTOMER_NAME, DELIVERY.ID AS DID, DELIVERY.INVOICE_NO, DELIVERY.AMOUNT, " +
                "DELIVERY.PAID_AMOUNT, DELIVERY.SALEMAN_ID FROM DELIVERY INNER JOIN CUSTOMER ON " +
                "CUSTOMER.ID = DELIVERY.CUSTOMER_ID;", null);

        List<Deliver> deliverList = new ArrayList<>();

        while (cursor.moveToNext()) {
            Deliver deliver = new Deliver();
            deliver.setDeliverId(cursor.getInt(cursor.getColumnIndex("DID")));
            deliver.setCustomerId(cursor.getString(cursor.getColumnIndex("CID")));
            deliver.setCustomerName(cursor.getString(cursor.getColumnIndex("CUSTOMER_NAME")));
            deliver.setInvoiceNo(cursor.getString(cursor.getColumnIndex("INVOICE_NO")));
            deliver.setAmount(cursor.getDouble(cursor.getColumnIndex("AMOUNT")));
            deliver.setPaidAmount(cursor.getDouble(cursor.getColumnIndex("PAID_AMOUNT")));
            deliver.setSaleManId(cursor.getInt(cursor.getColumnIndex("SALEMAN_ID")));

            Cursor deliveryItemCursor = database.rawQuery("SELECT * FROM DELIVERY_ITEM WHERE DELIVERY_FLG = 0 AND DELIVERY_ID = '" + deliver.getDeliverId() + "' AND RECEIVED_QTY <> ORDER_QTY", null);

            Log.i("deliver_Count -> ", String.valueOf(deliveryItemCursor.getCount()));
            List<DeliverItem> deliverItemList = new ArrayList<>();

            while (deliveryItemCursor.moveToNext()) {
                DeliverItem deliverItem = new DeliverItem();
                deliverItem.setDeliverId(deliveryItemCursor.getInt(deliveryItemCursor.getColumnIndex("DELIVERY_ID")));
                deliverItem.setStockNo(deliveryItemCursor.getString(deliveryItemCursor.getColumnIndex("STOCK_NO")));
                deliverItem.setOrderQty(deliveryItemCursor.getInt(deliveryItemCursor.getColumnIndex("ORDER_QTY")));
                deliverItem.setSPrice(deliveryItemCursor.getDouble(deliveryItemCursor.getColumnIndex("SPRICE")));
                deliverItem.setReceivedQty(deliveryItemCursor.getInt(deliveryItemCursor.getColumnIndex("RECEIVED_QTY")));

                if (deliverItem.getDeliverId() == deliver.getDeliverId()) {
                    deliverItemList.add(deliverItem);
                }
            }

            Cursor deliveryPresentCursor = database.rawQuery("SELECT * FROM DELIVERY_PRESENT WHERE SALE_ORDER_ID = '" + deliver.getDeliverId() + "' AND DELIVERY_FLG = 0", null);

            while(deliveryPresentCursor.moveToNext()) {
                DeliverItem deliverItem = new DeliverItem();
                deliverItem.setDeliverId(deliveryPresentCursor.getInt(deliveryPresentCursor.getColumnIndex("SALE_ORDER_ID")));
                deliverItem.setOrderQty(deliveryPresentCursor.getInt(deliveryPresentCursor.getColumnIndex("QUANTITY")));
                deliverItem.setStockNo(deliveryPresentCursor.getInt(deliveryPresentCursor.getColumnIndex("STOCK_ID")) + "");
                deliverItemList.add(deliverItem);
            }

            deliver.setDeliverItemList(deliverItemList);
            deliverList.add(deliver);
        }

        Log.i("deliver_list_Count -> ", String.valueOf(deliverList.size()));
         return deliverList;

    }

    @Override
    public void onBackPressed() {
        Utils.backToCustomerVisit(this);
    }
}
