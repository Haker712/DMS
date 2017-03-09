package com.aceplus.samparoo.report;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.utils.Database;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by phonelin on 3/9/17.
 */

public class FragmentDeliveryInvoiceReport extends Fragment {

    SQLiteDatabase database;

    ArrayList<JSONObject> DeliveryReportArrayList;

    JSONObject deliveryReportJsonObject;

    ListView deliveryInvoiceReportListview;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_deliveryinvoice_report, container, false);


        database = new Database(getContext()).getDataBase();


        deliveryInvoiceReportListview = (ListView) view.findViewById(R.id.DeliveryReportListview);
        deliveryInvoiceReportListview.setAdapter(new DeliveryAdapter(getActivity()));

        return view;
    }

    private class DeliveryAdapter extends ArrayAdapter<JSONObject> {

        public final Activity context;

        public DeliveryAdapter(Activity context) {
            super(context,R.layout.list_row_deliveryinvoice_report,DeliveryReportArrayList);
            this.context=context;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            deliveryReportJsonObject=DeliveryReportArrayList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_deliveryinvoice_report, null, true);

            TextView invoiceNo= (TextView) view.findViewById(R.id.invoiceNo);
            TextView customerName= (TextView) view.findViewById(R.id.customerName);
            TextView address= (TextView) view.findViewById(R.id.address);


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

}
