package com.aceplus.samparoo.customer;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aceplus.samparoo.CustomerVisitActivity;
import com.aceplus.samparoo.model.Deliver;
import com.aceplus.samparoo.model.DeliverItem;
import com.aceplus.samparoo.utils.Database;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aceplus.samparoo.R;

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

        Cursor cursor = database.rawQuery("SELECT CUSTOMER.CUSTOMER_ID, " +
                "CUSTOMER.CUSTOMER_NAME, DELIVERY.ID, DELIVERY.INVOICE_NO, DELIVERY.AMOUNT, " +
                "DELIVERY.PAID_AMOUNT FROM DELIVERY INNER JOIN CUSTOMER ON " +
                "CUSTOMER.ID = DELIVERY.CUSTOMER_ID;", null);

        List<Deliver> deliverList = new ArrayList<>();

        while (cursor.moveToNext()) {
            Deliver deliver = new Deliver();
            deliver.setDeliverId(cursor.getInt(cursor.getColumnIndex("ID")));
            deliver.setCustomerId(cursor.getString(cursor.getColumnIndex("CUSTOMER_ID")));
            deliver.setCustomerName(cursor.getString(cursor.getColumnIndex("CUSTOMER_NAME")));
            deliver.setInvoiceNo(cursor.getString(cursor.getColumnIndex("INVOICE_NO")));
            deliver.setAmount(cursor.getDouble(cursor.getColumnIndex("AMOUNT")));
            deliver.setPaidAmount(cursor.getDouble(cursor.getColumnIndex("PAID_AMOUNT")));


            cursor = database.rawQuery("SELECT D.DELIVERY_ID, D.STOCK_NO, D.ORDER_QTY, D.SPRICE, " +
                    "PRODUCT.PRODUCT_NAME FROM DELIVERY_ITEM D " +
                    "INNER JOIN PRODUCT ON PRODUCT.PRODUCT_ID = D.STOCK_NO;", null);

            List<DeliverItem> deliverItemList = new ArrayList<>();

            while (cursor.moveToNext()) {
                DeliverItem deliverItem = new DeliverItem();
                deliverItem.setDeliverId(cursor.getInt(cursor.getColumnIndex("DELIVERY_ID")));
                deliverItem.setStockNo(cursor.getString(cursor.getColumnIndex("STOCK_NO")));
                deliverItem.setOrderQty(cursor.getInt(cursor.getColumnIndex("ORDER_QTY")));
                deliverItem.setSPrice(cursor.getDouble(cursor.getColumnIndex("SPRICE")));
                deliverItem.setSPrice(cursor.getDouble(cursor.getColumnIndex("PRODUCT_NAME")));

                if (deliverItem.getDeliverId() == deliver.getDeliverId()) {
                    deliverItemList.add(deliverItem);
                }
            }

            deliver.setDeliverItemList(deliverItemList);
            deliverList.add(deliver);
        }

        return deliverList;

    }
}
