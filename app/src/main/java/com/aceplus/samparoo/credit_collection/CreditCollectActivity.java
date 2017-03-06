package com.aceplus.samparoo.credit_collection;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.CustomerCredit;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import java.util.ArrayList;

public class CreditCollectActivity extends Activity {

    private ListView customerCreditList;
    private ImageView cancelImg;
    private TextView cusCreditTxt;
    private TextView totalAmtTxt;
    private TextView paidAmtTxt;
    private TextView unpaidAmtTxt;

    SQLiteDatabase database;
    private CustomerCreditAdapter customerCreditAdapter;

    ArrayList<CustomerCredit> customerCreitArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_collect);
        database = new Database(this).getDataBase();
        registerIDs();
        getCreditData();
        catchEvents();
    }

    private void getCreditData() {
        database.beginTransaction();
        customerCreitArrayList.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM CREDIT", null);
        while (cursor.moveToNext()) {
            String customerName = null;
            String customerAddress = null;
            double amount = cursor.getDouble(cursor.getColumnIndex("TOTAL_AMT"));
            double paidAmount = cursor.getDouble(cursor.getColumnIndex("PAID_AMT"));
            double remainingAmount = cursor.getDouble(cursor.getColumnIndex("CREDIT_AMT"));
            String customerId = cursor.getString(cursor.getColumnIndex("CUSTOMER_ID"));
            String creditId = cursor.getString(cursor.getColumnIndex("CREDIT_ID"));

            String[] selCol1 = {"CUSTOMER_ID", "CUSTOMER_NAME", "ADDRESS"};
            String[] arg = {customerId};
            Cursor cur1;
            cur1 = database.query("CUSTOMER", selCol1, "CUSTOMER_ID LIKE ?", arg, null, null, null, null);
            while (cur1.moveToNext()) {
                customerName = cur1.getString(cur1.getColumnIndex("CUSTOMER_NAME"));
                customerAddress = cur1.getString(cur1.getColumnIndex("ADDRESS"));
            }
            CustomerCredit customerCredit = new CustomerCredit();
            customerCredit.setCreditId(creditId);
            customerCredit.setCustomerId(customerId);
            customerCredit.setCustomerCreditname(customerName);
            customerCredit.setCustomerAddress(customerAddress);
            customerCredit.setCreditTotalAmt(amount);
            customerCredit.setCreditPaidAmt(paidAmount);
            customerCredit.setCreditUnpaidAmt(remainingAmount);

            customerCreitArrayList.add(customerCredit);
        }
        cursor.close();
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    private void registerIDs() {
        customerCreditList = (ListView) findViewById(R.id.customer_credit_list);
        cancelImg = (ImageView) findViewById(R.id.cancel_img);
    }

    private void catchEvents() {
        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreditCollectActivity.this.onBackPressed();
            }
        });

        customerCreditAdapter = new CustomerCreditAdapter(CreditCollectActivity.this);
        customerCreditList.setAdapter(customerCreditAdapter);
        customerCreditList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerCredit customerCredit = customerCreitArrayList.get(position);
                if (customerCredit.getCreditUnpaidAmt() != 0) {
                    CreditCheckOut_Activity.creditId = customerCredit.getCreditId();
                    CreditCheckOut_Activity.customerId = customerCredit.getCustomerId();
                    CreditCheckOut_Activity.creditCustomer = customerCredit.getCustomerCreditname();
                    CreditCheckOut_Activity.creditCustomerAddress = customerCredit.getCustomerAddress();
                    CreditCheckOut_Activity.amount = customerCredit.getCreditTotalAmt();
                    CreditCheckOut_Activity.paidAmount = customerCredit.getCreditPaidAmt();
                    CreditCheckOut_Activity.unPaidAmount = customerCredit.getCreditUnpaidAmt();
                    startActivity(new Intent(CreditCollectActivity.this, CreditCheckOut_Activity.class));
                    finish();
                } else {
                    Toast.makeText(CreditCollectActivity.this, "You do not have pay Credit.Thank you!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public class CustomerCreditAdapter extends ArrayAdapter<CustomerCredit> {
        private final Activity context;

        public CustomerCreditAdapter(Activity context) {
            super(context, R.layout.customer_credit_list_row, customerCreitArrayList);
            this.context = context;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.customer_credit_list_row, null, true);

            cusCreditTxt = (TextView) rowView.findViewById(R.id.credit_customer_name);
            totalAmtTxt = (TextView) rowView.findViewById(R.id.credit_totalamt);
            paidAmtTxt = (TextView) rowView.findViewById(R.id.credit_paidamt);
            unpaidAmtTxt = (TextView) rowView.findViewById(R.id.credit_unpaidamt);

            CustomerCredit cusCredit = customerCreitArrayList.get(position);

            cusCreditTxt.setText(cusCredit.getCustomerCreditname());
            totalAmtTxt.setText(cusCredit.getCreditTotalAmt() + "");
            paidAmtTxt.setText(cusCredit.getCreditPaidAmt() + "");
            unpaidAmtTxt.setText(cusCredit.getCreditUnpaidAmt() + "");

            if(cusCredit.getCreditUnpaidAmt()==0){
                Resources res1 = getResources();
                cusCreditTxt.setTextColor(res1.getColor(R.color.accentColor));
                totalAmtTxt.setTextColor(res1.getColor(R.color.accentColor));
                paidAmtTxt.setTextColor(res1.getColor(R.color.accentColor));
                unpaidAmtTxt.setTextColor(res1.getColor(R.color.accentColor));
            }
            return rowView;
        }
    }

    @Override
    public void onBackPressed() {
        Utils.backToCustomerVisit(this);
    }
}
