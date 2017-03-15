package com.aceplus.samparoo.saleexchange;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Saleinvoicedetail;
import com.aceplus.samparoo.report.FragmentSaleInvoiceReport;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phonelin on 2/23/17.
 */
public class SaleExchangeTab2 extends Fragment {

    ListView saleInvoiceReportsListView;

    ArrayList<JSONObject> saleInvoiceReportsArrayList = new ArrayList<>();

    JSONObject saleInvoiceReportJsonObject;

    SQLiteDatabase database;

    String invoice_Id;

    String quantity, product_name;
    Double dicount_amount;
    Double total_amount;

    Saleinvoicedetail saleinvoicedetail;
    List<Saleinvoicedetail> saleinvoicedetailList = new ArrayList<Saleinvoicedetail>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_sale_invoice_report, container, false);

        database = new Database(getActivity()).getDataBase();
        saleInvoiceReportsArrayList = getSaleInvoiceReports();

        saleInvoiceReportsListView = (ListView) view.findViewById(R.id.saleInvoceReports);
        ArrayAdapter<JSONObject> saleInvoiceReportsArrayAdapter = new SaleExchangeTab2.SaleInvoiceReportsArrayAdapter(getActivity());
        saleInvoiceReportsListView.setAdapter(saleInvoiceReportsArrayAdapter);
        saleInvoiceReportsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                saleInvoiceReportJsonObject = saleInvoiceReportsArrayList.get(position);

                try {
                    invoice_Id = saleInvoiceReportJsonObject.getString("invoiceId");
                    Log.i("invoice_Id", invoice_Id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                saleinvoicedetailList.clear();

                Cursor cursor_Invoice_Id = database.rawQuery("SELECT * FROM INVOICE_PRODUCT WHERE INVOICE_PRODUCT_ID='" + invoice_Id + "'", null);
                while (cursor_Invoice_Id.moveToNext()) {

                    saleinvoicedetail = new Saleinvoicedetail();

                    String produc_Id = cursor_Invoice_Id.getString(cursor_Invoice_Id.getColumnIndex("PRODUCT_ID"));
                    Log.i("product_Id", produc_Id + "aaa");
                    quantity = cursor_Invoice_Id.getString(cursor_Invoice_Id.getColumnIndex("SALE_QUANTITY"));
                    dicount_amount = Double.valueOf(cursor_Invoice_Id.getString(cursor_Invoice_Id.getColumnIndex("DISCOUNT_AMOUNT")));
                    total_amount = Double.valueOf(cursor_Invoice_Id.getString(cursor_Invoice_Id.getColumnIndex("TOTAL_AMOUNT")));

                    Cursor cursor_product_Id = database.rawQuery("SELECT * FROM PRODUCT WHERE PRODUCT_ID='" + produc_Id + "'", null);
                    Log.i("cur_count", cursor_product_Id.getCount() + "");
                    while (cursor_product_Id.moveToNext()) {
                        product_name = cursor_product_Id.getString(cursor_product_Id.getColumnIndex("PRODUCT_NAME"));
                        Log.i("product_name", cursor_product_Id.getString(cursor_product_Id.getColumnIndex("PRODUCT_NAME")) + " aaa");

                        saleinvoicedetail.setProductName(product_name);
                        saleinvoicedetail.setQuantity(quantity);
                        saleinvoicedetail.setDiscountAmount(dicount_amount);
                        saleinvoicedetail.setTotalAmount(total_amount);

                    }
                    saleinvoicedetailList.add(saleinvoicedetail);

//


                }


                View dialogBoxView = getActivity().getLayoutInflater().inflate(R.layout.dialog_box_sale_invoice_report, null);
                saleInvoiceReportsListView = (ListView) dialogBoxView.findViewById(R.id.saleinvoicedialog);

                saleInvoiceReportsListView.setAdapter(new SaleExchangeTab2.SaleInvoiceArrayAdapter(getActivity()));
                new AlertDialog.Builder(getActivity())
                        .setView(dialogBoxView)
                        .setTitle("Pre-Order Products")
                        .setPositiveButton("OK", null)
                        .show();

            }
        });
        return view;
    }


    private class SaleInvoiceReportsArrayAdapter extends ArrayAdapter<JSONObject> {

        public final Activity context;

        public SaleInvoiceReportsArrayAdapter(Activity context) {

            super(context, R.layout.list_row_sale_invoice_report, saleInvoiceReportsArrayList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            saleInvoiceReportJsonObject = saleInvoiceReportsArrayList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_sale_invoice_report, null, true);

            TextView invoiceIdTextView = (TextView) view.findViewById(R.id.invoiceId);
            TextView customerNameTextView = (TextView) view.findViewById(R.id.customerName);
            TextView addressTextView = (TextView) view.findViewById(R.id.address);
            TextView totalAmountTextView = (TextView) view.findViewById(R.id.totalAmount);
            TextView discountTextView = (TextView) view.findViewById(R.id.discount);
            TextView netAmountTextView = (TextView) view.findViewById(R.id.netAmount);

            try {
                invoiceIdTextView.setText(saleInvoiceReportJsonObject.getString("invoiceId"));
                customerNameTextView.setText(saleInvoiceReportJsonObject.getString("customerName"));
                addressTextView.setText(saleInvoiceReportJsonObject.getString("address"));
                totalAmountTextView.setText(Utils.formatAmount(saleInvoiceReportJsonObject.getDouble("totalAmount")));
                discountTextView.setText(Utils.formatAmount(saleInvoiceReportJsonObject.getDouble("discount")));
                netAmountTextView.setText(Utils.formatAmount(saleInvoiceReportJsonObject.getDouble("netAmount")));
            } catch (JSONException e) {

                e.printStackTrace();
            }
            return view;
        }
    }

    private class SaleInvoiceArrayAdapter extends ArrayAdapter<Saleinvoicedetail> {

        public final Activity context;

        public SaleInvoiceArrayAdapter(Activity context) {

            super(context, R.layout.list_row_sale_invoice_detail, saleinvoicedetailList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Saleinvoicedetail saleinvoicedetail1 = saleinvoicedetailList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_sale_invoice_detail, null, true);

            TextView productNameTextView = (TextView) view.findViewById(R.id.sidproductName);
            TextView quantityTextView = (TextView) view.findViewById(R.id.sidquantity);
            TextView discountTextview = (TextView) view.findViewById(R.id.siddiscount);
            TextView totalAmountTextView = (TextView) view.findViewById(R.id.sidamount);


            productNameTextView.setText(saleinvoicedetail1.getProductName());
            quantityTextView.setText(saleinvoicedetail1.getQuantity());
            discountTextview.setText(saleinvoicedetail1.getDiscountAmount() + "");
            totalAmountTextView.setText(saleinvoicedetail1.getTotalAmount() + "");

            return view;
        }
    }

    private ArrayList<JSONObject> getSaleInvoiceReports() {

        ArrayList<JSONObject> saleInvoiceReportsArrayList = new ArrayList<JSONObject>();

        Cursor cursor = database.rawQuery("SELECT * FROM INVOICE", null);
        while (cursor.moveToNext()) {

            JSONObject saleInvoiceReportJsonObject = new JSONObject();
            try {

                saleInvoiceReportJsonObject.put("invoiceId", cursor.getString(cursor.getColumnIndex("INVOICE_ID")));

                Cursor cursorForCustomer = database.rawQuery(
                        "SELECT CUSTOMER_NAME, ADDRESS FROM CUSTOMER"
                                + " WHERE CUSTOMER_ID = \"" + cursor.getString(cursor.getColumnIndex("CUSTOMER_ID")) + "\""
                        , null);
                if (cursorForCustomer.moveToNext()) {

                    saleInvoiceReportJsonObject.put("customerName"
                            , cursorForCustomer.getString(cursorForCustomer.getColumnIndex("CUSTOMER_NAME")));
                    saleInvoiceReportJsonObject.put("address"
                            , cursorForCustomer.getString(cursorForCustomer.getColumnIndex("ADDRESS")));

                    double totalAmount = cursor.getDouble(cursor.getColumnIndex("TOTAL_AMOUNT"));
                    double discount = cursor.getDouble(cursor.getColumnIndex("TOTAL_DISCOUNT_AMOUNT"));
                    saleInvoiceReportJsonObject.put("totalAmount", totalAmount);
                    saleInvoiceReportJsonObject.put("discount", discount);
                    saleInvoiceReportJsonObject.put("netAmount", totalAmount - discount);
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }

            saleInvoiceReportsArrayList.add(saleInvoiceReportJsonObject);
        }

        return saleInvoiceReportsArrayList;
    }

}
