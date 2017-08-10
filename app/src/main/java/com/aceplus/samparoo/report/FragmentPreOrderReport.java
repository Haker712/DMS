package com.aceplus.samparoo.report;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Preorder_Product;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class FragmentPreOrderReport extends Fragment {

    ListView preOrderReportsListView;

    ArrayList<JSONObject> preOrderReportsArrayList;

    SQLiteDatabase database;

    String invoice_Id;

    ArrayList<Preorder_Product> preorderProductArrayList=new ArrayList<>();
    Preorder_Product preorderProduct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pre_order_report, container, false);


        database = new Database(getContext()).getDataBase();

        preOrderReportsListView = (ListView) view.findViewById(R.id.preOrderReports);
        preOrderReportsListView.setAdapter(new PreOrderReportsArrayAdapter(getActivity()));
        preOrderReportsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                View dialogBoxView = getActivity().getLayoutInflater().inflate(R.layout.dialog_box_pre_order_products, null);
                ListView preOrderProductsListView = (ListView) dialogBoxView.findViewById(R.id.preOrderProducts);

                JSONObject saleInvoiceReportJsonObject = preOrderReportsArrayList.get(position);

                try {
                    invoice_Id = saleInvoiceReportJsonObject.getString("invoice_Id");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Cursor cursor = database.rawQuery("select * from PRE_ORDER_PRODUCT where SALE_ORDER_ID='" + invoice_Id + "'", null);

                while (cursor.moveToNext()) {
                    String orderQty = cursor.getString(cursor.getColumnIndex("ORDER_QTY"));
                    Log.i("OrderQty",orderQty);
                    String total_amount = cursor.getString(cursor.getColumnIndex("TOTAL_AMT"));

                    preorderProduct=new Preorder_Product();
                    preorderProduct.setOrderQty(orderQty);
                    preorderProduct.setTotalAmount(total_amount);
                    String product_Id = cursor.getString(cursor.getColumnIndex("PRODUCT_ID"));

                    Cursor cursor1 = database.rawQuery("select * from PRODUCT where ID='" + product_Id + "'", null);

                    while (cursor1.moveToNext()) {
                        String productName = cursor1.getString(cursor1.getColumnIndex("PRODUCT_NAME"));
                        preorderProduct.setProductName(productName);
                    }
                    preorderProductArrayList.add(preorderProduct);
                }
                preOrderProductsListView.setAdapter(new PreOrderProductsArrayAdapter(getActivity()));
                new AlertDialog.Builder(getActivity())
                        .setView(dialogBoxView)
                        .setTitle("Pre-Order Products")
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

        return view;
    }

    private class PreOrderReportsArrayAdapter extends ArrayAdapter<JSONObject> {

        public final Activity context;

        public PreOrderReportsArrayAdapter(Activity context) {

            super(context, R.layout.list_row_pre_order_report, preOrderReportsArrayList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            JSONObject saleInvoiceReportJsonObject = preOrderReportsArrayList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_pre_order_report, null, true);

            TextView customerNameTextView = (TextView) view.findViewById(R.id.customerName);
            TextView prepaidAmountTextView = (TextView) view.findViewById(R.id.prepaidAmount);
            TextView totalAmountTextView = (TextView) view.findViewById(R.id.totalAmount);

            try {

                customerNameTextView.setText(saleInvoiceReportJsonObject.getString("customerName"));
                prepaidAmountTextView.setText(Utils.formatAmount(saleInvoiceReportJsonObject.getDouble("prepaidAmount")));
                totalAmountTextView.setText(Utils.formatAmount(saleInvoiceReportJsonObject.getDouble("totalAmount")));
            } catch (JSONException e) {

                e.printStackTrace();
            }
            return view;
        }
    }

    private class PreOrderProductsArrayAdapter extends ArrayAdapter<Preorder_Product> {

        public final Activity context;

        public PreOrderProductsArrayAdapter(Activity context) {

            super(context, R.layout.list_row_pre_order_product, preorderProductArrayList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            preorderProduct=preorderProductArrayList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_pre_order_product, null, true);


            TextView productNameTextView = (TextView) view.findViewById(R.id.productName);
            TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
            TextView totalAmountTextView = (TextView) view.findViewById(R.id.totalAmount);

          productNameTextView.setText(preorderProduct.getProductName());
            quantityTextView.setText(preorderProduct.getOrderQty());
            totalAmountTextView.setText(preorderProduct.getTotalAmount());



            return view;
        }
    }
}
