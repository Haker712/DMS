package com.aceplus.samparoo.report;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.PromotionProduct;
import com.aceplus.samparoo.utils.Database;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ESeries on 10/22/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FragmentPromotionReport extends Fragment {

    ListView promotionListView;
    ArrayList<JSONObject> promotionReportsArrayList;
    List<PromotionProduct> promotionsDetailList = new ArrayList<PromotionProduct>();
    String invoiceId = "";
    String productId = "";
    String productName = "";
    String qty = "";

    SQLiteDatabase database;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotion_report, container, false);

        context = getActivity();
        database = new Database(context).getDataBase();

        promotionListView = (ListView) view.findViewById(R.id.saleInvoceReports);
        ArrayAdapter<JSONObject> promotionPriceReportsArrayAdapter = new PromotionReportsArrayAdapter(getActivity());
        promotionListView.setAdapter(promotionPriceReportsArrayAdapter);

        promotionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View dialogBoxView = getActivity().getLayoutInflater().inflate(R.layout.dialog_box_pre_order_products, null);
                ListView PromotionProductsListView = (ListView) dialogBoxView.findViewById(R.id.preOrderProducts);
                TextView productNameTextView = (TextView) dialogBoxView.findViewById(R.id.productNameTextView);
                productNameTextView.setText("Promotion Product Name");
                TextView remarkTextView = (TextView) dialogBoxView.findViewById(R.id.total);
                remarkTextView.setVisibility(View.GONE);

                try {

                    promotionsDetailList.clear();

                    invoiceId = promotionReportsArrayList.get(position).getString("invoiceId");
                    Cursor cursor = database.rawQuery("SELECT * FROM INVOICE_PRODUCT WHERE INVOICE_PRODUCT_ID='"+invoiceId+"'", null);
                    while (cursor.moveToNext()) {
                        productId = cursor.getString(cursor.getColumnIndex("PRODUCT_ID"));

                        cursor = database.rawQuery("SELECT * FROM PRODUCT WHERE PRODUCT_ID='" + productId + "'", null);
                        while (cursor.moveToNext()) {
                            productName = cursor.getString(cursor.getColumnIndex("PRODUCT_NAME"));
                        }

                        cursor = database.rawQuery("SELECT * FROM PROMOTION_INVOICE WHERE ID='"+invoiceId+"'", null);
                        while (cursor.moveToNext()) {
                            qty = cursor.getString(cursor.getColumnIndex("PROMOTION_QTY"));
                        }

                        PromotionProduct promotionProduct = new PromotionProduct(productId, productName, qty);
                        promotionsDetailList.add(promotionProduct);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }

                PromotionProductsListView.setAdapter(new PromotionProductsArrayAdapter(getActivity()));
                new AlertDialog.Builder(getActivity())
                        .setView(dialogBoxView)
                        .setTitle("Promotion Products")
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

        return view;
    }

    private class PromotionReportsArrayAdapter extends ArrayAdapter<JSONObject> {

        public final Activity context;

        public PromotionReportsArrayAdapter(Activity context) {

            super(context, R.layout.list_row_promotion_report, promotionReportsArrayList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            JSONObject promotionReportJsonObject = promotionReportsArrayList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_promotion_report, null, true);

            TextView invoiceIdTextView = (TextView) view.findViewById(R.id.invoiceId);
            TextView customerNameTextView = (TextView) view.findViewById(R.id.customerName);
            TextView addressTextView = (TextView) view.findViewById(R.id.address);
            TextView totalAmountTextView = (TextView) view.findViewById(R.id.totalAmount);
            totalAmountTextView.setVisibility(View.GONE);

            try {

                invoiceIdTextView.setText(promotionReportJsonObject.getString("invoiceId"));
                customerNameTextView.setText(promotionReportJsonObject.getString("customerName"));
                addressTextView.setText(promotionReportJsonObject.getString("customerAddress"));
                //totalAmountTextView.setText(Utils.formatAmount(promotionReportJsonObject.getDouble("totalAmount")));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            return view;
        }
    }

    private class PromotionProductsArrayAdapter extends ArrayAdapter<PromotionProduct> {

        public final Activity context;

        public PromotionProductsArrayAdapter(Activity context) {

            super(context, R.layout.list_row_pre_order_product, promotionsDetailList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            PromotionProduct promotionProduct = promotionsDetailList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_pre_order_product, null, true);

            TextView productNameTextView = (TextView) view.findViewById(R.id.productName);
            TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
            TextView remarkTextView = (TextView) view.findViewById(R.id.totalAmount);
            remarkTextView.setVisibility(View.GONE);

            productNameTextView.setText(promotionProduct.getPromotion_product_name());
            quantityTextView.setText(promotionProduct.getPromotion_qty());
            return view;
        }
    }

    /*ListView promotionListView;

    ArrayList<JSONObject> promotionReportsArrayList;
    List<JSONObject> promotionsList = new ArrayList<JSONObject>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_promotion_report, container, false);

        promotionListView = (ListView) view.findViewById(R.id.saleInvoceReports);
        ArrayAdapter<JSONObject> promotionPriceReportsArrayAdapter = new SaleInvoiceReportsArrayAdapter(getActivity());
        promotionListView.setAdapter(promotionPriceReportsArrayAdapter);
        promotionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View dialogBoxView = getActivity().getLayoutInflater().inflate(R.layout.dialog_box_pre_order_products, null);
                ListView PromotionProductsListView = (ListView) dialogBoxView.findViewById(R.id.preOrderProducts);
                TextView remarkTextView = (TextView) dialogBoxView.findViewById(R.id.total);
                remarkTextView.setVisibility(View.GONE);

                try {

                    promotionsList.clear();
                    JSONArray itemProductsJSONArray = FragmentPromotionReport.this.promotionReportsArrayList.get(position).getJSONArray("itemProductList");
                    JSONArray presentProductsJSONArray = FragmentPromotionReport.this.promotionReportsArrayList.get(position).getJSONArray("presentProductList");
                    for (int i = 0; i < itemProductsJSONArray.length(); i++) {

                        promotionsList.add(itemProductsJSONArray.getJSONObject(i));
                    }
                    for (int i = 0; i < presentProductsJSONArray.length(); i++) {

                        promotionsList.add(presentProductsJSONArray.getJSONObject(i));
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }

                PromotionProductsListView.setAdapter(new PromotionProductsArrayAdapter(getActivity()));
                new AlertDialog.Builder(getActivity())
                        .setView(dialogBoxView)
                        .setTitle("Promotion Products")
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
        return view;
    }

    private class SaleInvoiceReportsArrayAdapter extends ArrayAdapter<JSONObject> {

        public final Activity context;

        public SaleInvoiceReportsArrayAdapter(Activity context) {

            super(context, R.layout.list_row_promotion_report, promotionReportsArrayList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            JSONObject saleInvoiceReportJsonObject = promotionReportsArrayList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_promotion_report, null, true);

            TextView invoiceIdTextView = (TextView) view.findViewById(R.id.invoiceId);
            TextView customerNameTextView = (TextView) view.findViewById(R.id.customerName);
            TextView addressTextView = (TextView) view.findViewById(R.id.address);
            TextView totalAmountTextView = (TextView) view.findViewById(R.id.totalAmount);

            try {

                invoiceIdTextView.setText(saleInvoiceReportJsonObject.getString("invoiceId"));
                customerNameTextView.setText(saleInvoiceReportJsonObject.getString("customerName"));
                addressTextView.setText(saleInvoiceReportJsonObject.getString("address"));
                totalAmountTextView.setText(Utils.formatAmount(saleInvoiceReportJsonObject.getDouble("totalAmount")));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            return view;
        }
    }

    private class PromotionProductsArrayAdapter extends ArrayAdapter<JSONObject> {

        public final Activity context;

        public PromotionProductsArrayAdapter(Activity context) {

            super(context, R.layout.list_row_pre_order_product, promotionsList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            JSONObject promotionReportJsonObject = promotionsList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_pre_order_product, null, true);

            TextView productNameTextView = (TextView) view.findViewById(R.id.productName);
            TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
            TextView remarkTextView = (TextView) view.findViewById(R.id.totalAmount);
            remarkTextView.setVisibility(View.GONE);
            try {

                productNameTextView.setText(promotionReportJsonObject.getString("productName"));
                quantityTextView.setText(promotionReportJsonObject.getString("saleQty"));
            } catch (JSONException e) {

                e.printStackTrace();
            }
            return view;
        }
    }*/
}
