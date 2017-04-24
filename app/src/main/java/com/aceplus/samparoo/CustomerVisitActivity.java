package com.aceplus.samparoo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aceplus.samparoo.credit_collection.CreditCollectActivity;
import com.aceplus.samparoo.customer.AddNewCustomerActivity;
import com.aceplus.samparoo.customer.CustomerActivity;
import com.aceplus.samparoo.customer.DeliveryActivity;
import com.aceplus.samparoo.utils.Utils;

import butterknife.ButterKnife;
import butterknife.InjectViews;
import butterknife.OnClick;

/**
 * Created by haker on 2/3/17.
 */
public class CustomerVisitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_visit);

        ButterKnife.inject(this);
    }

    @OnClick(R.id.buttonCustomer)
    void customer() {
        Intent intent = new Intent(this, CustomerActivity.class);
        intent.putExtra("SaleExchange","no");
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.buttonAddNewCustomer)
    void addNewCustomer() {
        Intent intent = new Intent(this, AddNewCustomerActivity.class);
        startActivity(intent);
        finish();


    }

    @OnClick(R.id.buttonCreditCollections)
    void creditCollections() {
        //Utils.commonDialog("This feature is not yet.", this);
        Intent intent = new Intent(this, CreditCollectActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.buttonSaleExchange)
    void saleExchange() {
        Intent intent=new Intent(this,CustomerActivity.class);
        intent.putExtra("SaleExchange","yes");
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.buttonDelivery)
    void deliver() {

        Intent intent=new Intent(this,DeliveryActivity.class);
        startActivity(intent);
        finish();
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
