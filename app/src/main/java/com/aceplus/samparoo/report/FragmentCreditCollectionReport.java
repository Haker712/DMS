package com.aceplus.samparoo.report;

import android.annotation.TargetApi;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.CreditReport;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import java.util.ArrayList;

/**
 * Created by ESeries on 11/2/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentCreditCollectionReport extends Fragment {

    ListView saleInvoiceReportsListView;
    SQLiteDatabase database;
    //    ArrayList<JSONObject> creditCollectionArrayList;
    ArrayList<CreditReport> creditReportList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_credit_collection_report, container, false);
        database = new Database(getActivity()).getDataBase();
        getCreditReportData();
        saleInvoiceReportsListView = (ListView) view.findViewById(R.id.creditCollectionReports);
        final ArrayAdapter<CreditReport> creditReportArrayAdapter = new SaleInvoiceReportsArrayAdapter(getActivity());
        saleInvoiceReportsListView.setAdapter(creditReportArrayAdapter);
        return view;
    }

    private void getCreditReportData() {
        database.beginTransaction();
        Cursor cursor = database.rawQuery("SELECT * FROM CREDIT WHERE FLAG='paid'", null);
        creditReportList.clear();
        while (cursor.moveToNext()) {
            String invoiceId = cursor.getString(cursor.getColumnIndex("CREDIT_ID"));
            String customerId = cursor.getString(cursor.getColumnIndex("CUSTOMER_ID"));
            double amount = cursor.getDouble(cursor.getColumnIndex("TOTAL_AMT"));
            double paidAmount = cursor.getDouble(cursor.getColumnIndex("PAID_AMT"));
            double creditAmount = cursor.getDouble(cursor.getColumnIndex("CREDIT_AMT"));

            String customerName = null;
            String customerAddress = null;
            String[] selCol1 = {"CUSTOMER_ID", "CUSTOMER_NAME", "ADDRESS"};
            String[] arg = {customerId};
            Cursor cur1;
            cur1 = database.query("CUSTOMER", selCol1, "CUSTOMER_ID LIKE ?", arg, null, null, null, null);
            while (cur1.moveToNext()) {
                customerName = cur1.getString(cur1.getColumnIndex("CUSTOMER_NAME"));
                customerAddress = cur1.getString(cur1.getColumnIndex("ADDRESS"));
            }

            CreditReport creditReport = new CreditReport();
            creditReport.setCcInvoiceId(invoiceId);
            creditReport.setCutomerName(customerName);
            creditReport.setCustomerAddress(customerAddress);
            creditReport.setTotalAmount(amount);
            creditReport.setPaidAmount(paidAmount);
            creditReport.setCreditAmount(creditAmount);

            creditReportList.add(creditReport);
        }
        cursor.close();
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    private class SaleInvoiceReportsArrayAdapter extends ArrayAdapter<CreditReport> {

        public final Activity context;

        public SaleInvoiceReportsArrayAdapter(Activity context) {

            super(context, R.layout.list_row_sale_invoice_report, creditReportList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CreditReport creditReport = creditReportList.get(position);
            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_sale_invoice_report, null, true);

            TextView invoiceIdTextView = (TextView) view.findViewById(R.id.invoiceId);
            TextView customerNameTextView = (TextView) view.findViewById(R.id.customerName);
            TextView addressTextView = (TextView) view.findViewById(R.id.address);
            TextView totalAmountTextView = (TextView) view.findViewById(R.id.totalAmount);
            TextView discountTextView = (TextView) view.findViewById(R.id.discount);
            TextView netAmountTextView = (TextView) view.findViewById(R.id.netAmount);

            invoiceIdTextView.setText(creditReport.getCcInvoiceId());
            customerNameTextView.setText(creditReport.getCutomerName());
            addressTextView.setText(creditReport.getCustomerAddress());
            totalAmountTextView.setText(Utils.formatAmount(creditReport.getTotalAmount()));
            discountTextView.setText(Utils.formatAmount(creditReport.getPaidAmount()));
            netAmountTextView.setText(Utils.formatAmount(creditReport.getCreditAmount()));
            return view;
        }
    }
}
