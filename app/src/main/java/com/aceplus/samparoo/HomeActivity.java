package com.aceplus.samparoo;

import android.content.Intent;
import android.net.Uri;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aceplus.samparoo.customer.CustomerActivity;
import com.aceplus.samparoo.model.Promotion;
import com.aceplus.samparoo.myinterface.OnActionClickListener;
import com.aceplus.samparoo.utils.Constant;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;
import com.aceplus.samparoo.report.ReportHomeActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by haker on 1/24/17.
 */
public class HomeActivity extends AppCompatActivity implements OnActionClickListener {

    @InjectView(R.id.buttonSync)
    Button buttonSync;

    @InjectView(R.id.buttonRoute)
    Button buttonRoute;

    @InjectView(R.id.buttonCustomerVisit)
    Button buttonCustomerVisit;

    @InjectView(R.id.buttonPromotion)
    Button buttonPromotion;

    @InjectView(R.id.buttonMarketing)
    Button buttonMarketing;

    @InjectView(R.id.buttonReport)
    Button buttonReport;

    @InjectView(R.id.username)
    TextView textViewUserName;

    @InjectView(R.id.date)
    TextView textViewDate;

    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.inject(this);

        Utils.setOnActionClickListener(this);

        sqLiteDatabase = new Database(this).getDataBase();

        //toolbarTitle.setText(R.string.home_label);
        //Toast.makeText(this, LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, ""), Toast.LENGTH_SHORT).show();

        if (LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NAME, "") != null) {
            textViewUserName.setText(LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NAME, ""));
        }

        textViewDate.setText(Utils.getCurrentDate(false));
    }

    @OnClick(R.id.cancel_img)
    void back() {
        onBackPressed();
    }

    @OnClick(R.id.buttonSync)
    void sync() {
        Intent intent = new Intent(this, SyncActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.buttonRoute)
    void route() {
        if (isNoCustomer() > 0) {
            Intent intent = new Intent(this, RouteActivity.class);
            startActivity(intent);
            finish();
        }else {Utils.commonDialog("NO CUSTOMER",this);}
    }

    @OnClick(R.id.buttonCustomerVisit)
    void customerVisit() {
        Intent intent = new Intent(this, CustomerVisitActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.buttonPromotion)
    void promotion() {
        Intent intent = new Intent(this, PromotionActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.buttonMarketing)
    void marketing() {

        //Utils.commonDialog("Under construction", HomeActivity.this);
        Intent intent = new Intent(this, MarketingActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.buttonReport)
    void report() {
        Intent intent = new Intent(this, ReportHomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Utils.askConfirmationDialog("Logout", "Do you want to logout?", "", this);
    }

    private int isNoCustomer() {
        sqLiteDatabase = new Database(this).getDataBase();
        Cursor cursorCustomerCount = sqLiteDatabase.rawQuery("SELECT * FROM CUSTOMER", null);
        int count = cursorCustomerCount.getCount();
        return count;
    }

    @Override
    public void onActionClick(String type) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
