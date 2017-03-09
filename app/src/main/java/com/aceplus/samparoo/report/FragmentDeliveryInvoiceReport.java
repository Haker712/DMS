package com.aceplus.samparoo.report;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
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

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.DeliveryInvoiceDetail;
import com.aceplus.samparoo.model.Saleinvoicedetail;
import com.aceplus.samparoo.utils.Database;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phonelin on 3/9/17.
 */

public class FragmentDeliveryInvoiceReport extends Fragment {

    SQLiteDatabase database;

    ArrayList<JSONObject> DeliveryReportArrayList;

    JSONObject deliveryReportJsonObject;

    ListView deliveryInvoiceReportListview;
    ListView deliveryInvoiceDetailListview;

    String invoice_Id;
    String product_Name, qty;

    List<DeliveryInvoiceDetail> deliveryInvoiceDetailList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_deliveryinvoice_report, container, false);


        database = new Database(getContext()).getDataBase();


        deliveryInvoiceReportListview = (ListView) view.findViewById(R.id.DeliveryReportListview);
        deliveryInvoiceReportListview.setAdapter(new DeliveryAdapter(getActivity()));
        deliveryInvoiceReportListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                deliveryReportJsonObject = DeliveryReportArrayList.get(position);
                deliveryInvoiceDetailList.clear();

                try {
                    invoice_Id = deliveryReportJsonObject.getString("InvoiceNo");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Cursor cursor = database.rawQuery("select * from DELIVERY_ITEM_UPLOAD where DELIVERY_ID='" + invoice_Id + "'", null);

                while (cursor.moveToNext()) {

                    DeliveryInvoiceDetail deliveryInvoiceDetail = new DeliveryInvoiceDetail();


                    qty = cursor.getString(cursor.getColumnIndex("DELIVERY_QTY"));
                    String pro_Id = cursor.getString(cursor.getColumnIndex("STOCK_ID"));

                    Cursor cursor1 = database.rawQuery("select * from PRODUCT where ID='" + pro_Id + "'", null);

                    while (cursor1.moveToNext()) {

                        product_Name = cursor1.getString(cursor1.getColumnIndex("PRODUCT_NAME"));
                        Log.i("Product_Name",product_Name);

                    }

                    Log.i("QTY",qty);
                    deliveryInvoiceDetail.setQty(qty);
                    deliveryInvoiceDetail.setProduct_Name(product_Name);
                    deliveryInvoiceDetailList.add(deliveryInvoiceDetail);


                }

                View dialogBoxView = getActivity().getLayoutInflater().inflate(R.layout.dialog_box_deliveryinvoice_report, null);
                deliveryInvoiceDetailListview = (ListView) dialogBoxView.findViewById(R.id.deliveryInvoiceDetailListview);


                deliveryInvoiceDetailListview.setAdapter(new DeliveryDetailAdapter(getActivity()));
                new AlertDialog.Builder(getActivity())
                        .setView(dialogBoxView)
                        .setTitle("Delivery Report")
                        .setPositiveButton("OK", null)
                        .show();

            }
        });

        return view;
    }

    private class DeliveryAdapter extends ArrayAdapter<JSONObject> {

        public final Activity context;

        public DeliveryAdapter(Activity context) {
            super(context, R.layout.list_row_deliveryinvoice_report, DeliveryReportArrayList);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            deliveryReportJsonObject = DeliveryReportArrayList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_deliveryinvoice_report, null, true);

            TextView invoiceNo = (TextView) view.findViewById(R.id.invoiceNo);
            TextView customerName = (TextView) view.findViewById(R.id.customerName);
            TextView address = (TextView) view.findViewById(R.id.address);


            try {
                invoiceNo.setText(deliveryReportJsonObject.getString("InvoiceNo"));
                customerName.setText(deliveryReportJsonObject.getString("CustomerName"));
                address.setText(deliveryReportJsonObject.getString("Address"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return view;
        }
    }

    private class DeliveryDetailAdapter extends ArrayAdapter<DeliveryInvoiceDetail> {


        public final Activity context;

        public DeliveryDetailAdapter(@NonNull Activity context) {
            super(context, R.layout.list_row_deliveryinvoicedetail_report,deliveryInvoiceDetailList);
            this.context=context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            DeliveryInvoiceDetail deliveryInvoiceDetail = deliveryInvoiceDetailList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_deliveryinvoicedetail_report, null, true);


            TextView productName= (TextView) view.findViewById(R.id.productNameTextView);
            TextView qty= (TextView) view.findViewById(R.id.qtyTextView);


            productName.setText(deliveryInvoiceDetail.getProduct_Name());
            qty.setText(deliveryInvoiceDetail.getQty());

            return view;
        }
    }

}
