package com.aceplus.samparoo.customer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aceplus.samparoo.R;

/**
 * Created by haker on 2/15/17.
 */
public class SalePrintActivity extends AppCompatActivity {

    public static final String CUSTOMER_INFO_KEY = "customer-info-key";
    public static final String SOLD_PRODUCT_LIST_KEY = "sold-product-list";
    public static final String INVOICE_ID_KEY = "invoice-id-key";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_print);
    }
}
