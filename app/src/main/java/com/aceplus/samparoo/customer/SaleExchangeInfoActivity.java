package com.aceplus.samparoo.customer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by haker on 3/17/17.
 */

public class SaleExchangeInfoActivity extends AppCompatActivity {

    @InjectView(R.id.textViewDate)
    TextView textViewDate;

    @InjectView(R.id.textViewCustomerName)
    TextView textViewCustomerName;

    @InjectView(R.id.textViewSaleReturnAmt)
    TextView textViewSaleReturnAmt;

    @InjectView(R.id.textViewSaleExchangeAmt)
    TextView textViewSaleExchangeAmt;

    @InjectView(R.id.textViewPayAmt)
    TextView textViewPayAmt;

    @InjectView(R.id.textViewRefund)
    TextView textViewRefundAmt;

    Customer customer;
    String date = "";
    double saleReturnAmt = 0.0, saleExchangeAmt = 0.0;
    String saleReturnInvoiceId = "", saleExchangeInvoiceId = "";
    double pay_amt = 0.0, refund_amt = 0.0;

    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sale_exchange_info_layout);

        ButterKnife.inject(this);

        sqLiteDatabase = new Database(this).getDataBase();

        customer = (Customer) getIntent().getSerializableExtra(SaleCheckoutActivity.CUSTOMER_INFO_KEY);
        date = getIntent().getStringExtra(SaleCheckoutActivity.DATE_KEY);
        saleReturnInvoiceId = getIntent().getStringExtra(SaleActivity.SALE_RETURN_INVOICEID_KEY);
        saleExchangeInvoiceId = getIntent().getStringExtra(SaleCheckoutActivity.SALE_EXCHANGE_INVOICEID_KEY);

        getDatasForTextView();

        textViewDate.setText(date);
        textViewCustomerName.setText(customer.getCustomerName());
        textViewSaleReturnAmt.setText(Utils.formatAmount(saleReturnAmt) + " MMK");
        textViewSaleExchangeAmt.setText(Utils.formatAmount(saleExchangeAmt) + " MMK");

        if (saleReturnAmt > saleExchangeAmt) {
            refund_amt = saleReturnAmt - saleExchangeAmt;
        }
        else {
            pay_amt = saleExchangeAmt - saleReturnAmt;
        }

        textViewPayAmt.setText(Utils.formatAmount(pay_amt) + " MMK");
        textViewRefundAmt.setText(Utils.formatAmount(refund_amt) + " MMK");
    }

    private void getDatasForTextView() {
        Cursor cursorForSaleReturnAmt = sqLiteDatabase.rawQuery("select * from SALE_RETURN where SALE_RETURN_ID = '" + saleReturnInvoiceId + "'", null);
        while (cursorForSaleReturnAmt.moveToNext()) {
            saleReturnAmt = cursorForSaleReturnAmt.getDouble(cursorForSaleReturnAmt.getColumnIndex("AMT"));
        }

        Cursor cursorForSaleExchangeAmt = sqLiteDatabase.rawQuery("select * from INVOICE where INVOICE_ID = '" + saleExchangeInvoiceId + "'", null);
        while (cursorForSaleExchangeAmt.moveToNext()) {
            saleExchangeAmt = cursorForSaleExchangeAmt.getDouble(cursorForSaleExchangeAmt.getColumnIndex("TOTAL_AMOUNT"));
        }
    }

    @OnClick(R.id.confirmAndPrint_img)
    void confirm() {
        Utils.backToCustomer(this);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Please click Confirm Button!")
                .setPositiveButton("Ok", null)
                .show();
    }
}
