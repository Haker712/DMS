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
import com.aceplus.samparoo.model.forApi.CreditForApi;
import com.aceplus.samparoo.model.forApi.CustomerBalanceForApi;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CreditCollectActivity extends Activity {

    private ListView customerCreditList;
    private ImageView cancelImg;
    private TextView cusCreditTxt;
    private TextView totalAmtTxt;
    private TextView paidAmtTxt;
    private TextView unpaidAmtTxt;

    SQLiteDatabase database;
    private CustomerCreditAdapter customerCreditAdapter;

    List<CustomerCredit> customerCreditArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_collect);
        database = new Database(this).getDataBase();
        registerIDs();

        customerCreditArrayList = getCreditCollection();

        if(customerCreditArrayList != null && customerCreditArrayList.size() > 0) {
            customerCreditAdapter = new CustomerCreditAdapter(CreditCollectActivity.this);
            customerCreditList.setAdapter(customerCreditAdapter);
        } else {
            Utils.commonDialog("No credit to pay", CreditCollectActivity.this);
        }

        catchEvents();
    }

    private List<CustomerCredit> getCreditCollection() {
        String query = "SELECT * FROM " + DatabaseContract.CUSTOMER_BALANCE.TABLE;
        Cursor customerBalanceCursor = database.rawQuery(query, null);
        List<CustomerCredit> creditList = new ArrayList<>();

        while(customerBalanceCursor.moveToNext()) {
            double paidAmt = 0.0, unpaidAmt = 0.0;
            String customerName = "", customerAddress = "";

            int customerId = customerBalanceCursor.getInt(customerBalanceCursor.getColumnIndex(DatabaseContract.CUSTOMER_BALANCE.CUSTOMER_ID));
            int currencyId = customerBalanceCursor.getInt(customerBalanceCursor.getColumnIndex(DatabaseContract.CUSTOMER_BALANCE.CURRENCY_ID));
            double totalBalance = customerBalanceCursor.getInt(customerBalanceCursor.getColumnIndex(DatabaseContract.CUSTOMER_BALANCE.BALANCE));
            double creditAmount = 0.0;
            String query1 = "SELECT C.PAY_AMT, C.AMT, CUS.CUSTOMER_NAME, CUS.ADDRESS FROM CREDIT AS C, CUSTOMER AS CUS WHERE CUS.ID = " + customerId + " AND c.customer_id = " + customerId;
            Cursor creditCursor = database.rawQuery(query1, null);

            while(creditCursor.moveToNext()) {
                paidAmt += creditCursor.getDouble(creditCursor.getColumnIndex("PAY_AMT"));
                customerName = creditCursor.getString(creditCursor.getColumnIndex("CUSTOMER_NAME"));
                customerAddress = creditCursor.getString(creditCursor.getColumnIndex("ADDRESS"));
                creditAmount += creditCursor.getDouble(creditCursor.getColumnIndex("AMT"));
            }
            creditCursor.close();
            unpaidAmt = creditAmount - paidAmt;

            CustomerCredit customerCredit = new CustomerCredit();
            customerCredit.setCustomerId(customerId);
            customerCredit.setCurrencyId(currencyId);
            customerCredit.setCustomerCreditname(customerName);
            customerCredit.setCustomerAddress(customerAddress);
            customerCredit.setCreditUnpaidAmt(unpaidAmt);
            customerCredit.setCreditPaidAmt(paidAmt);
            customerCredit.setCreditTotalAmt(creditAmount);

            if(creditAmount != 0.0 || paidAmt != 0.0 || unpaidAmt != 0.0) {
                creditList.add(customerCredit);
            }
        }
        customerBalanceCursor.close();

        return creditList;
    }

    /*private void getCreditData() {
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
    }*/

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

        customerCreditList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerCredit customerCredit = customerCreditArrayList.get(position);
                if(customerCredit.getCreditUnpaidAmt() != 0.0) {
                    Intent intent = new Intent(CreditCollectActivity.this, CreditCheckOut_Activity.class);
                    intent.putExtra(CreditCheckOut_Activity.CREDIT_KEY, customerCredit);
                    startActivity(intent);
                } else {
                    Utils.commonDialog("No credit for this customer", CreditCollectActivity.this);
                }
            }
        });
    }

    public class CustomerCreditAdapter extends ArrayAdapter<CustomerCredit> {
        private final Activity context;

        public CustomerCreditAdapter(Activity context) {
            super(context, R.layout.customer_credit_list_row, customerCreditArrayList);
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

            CustomerCredit cusCredit = customerCreditArrayList.get(position);

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
