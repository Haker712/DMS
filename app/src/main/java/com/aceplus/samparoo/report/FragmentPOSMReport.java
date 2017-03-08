package com.aceplus.samparoo.report;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import java.util.zip.Inflater;

/**
 * Created by phonelin on 3/8/17.
 */
public class FragmentPOSMReport extends Fragment {

    SQLiteDatabase database;

    ArrayList<JSONObject> POSMReportArrayList;

    ListView POSMListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_posm_report,container,false);

        database = new Database(getContext()).getDataBase();

        POSMListView = (ListView) view.findViewById(R.id.posmReportListView);
        POSMListView.setAdapter(new POSMAdapter(getActivity()));

        return view;
    }

    private class POSMAdapter extends ArrayAdapter<JSONObject>{

        public final Activity context;


        public POSMAdapter(Activity context) {
            super(context, R.layout.list_row_posm_report,POSMReportArrayList );
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            JSONObject posmReportJsonObject = POSMReportArrayList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_posm_report, null, true);

            TextView customerNameTextView= (TextView) view.findViewById(R.id.customerName);
            TextView productNameTextView= (TextView) view.findViewById(R.id.productName);
            TextView shoptypeNameTextView= (TextView) view.findViewById(R.id.shoptype);
            TextView qtyTextView= (TextView) view.findViewById(R.id.qty);


            try {
                customerNameTextView.setText(posmReportJsonObject.getString("CustomerName"));
                productNameTextView.setText(posmReportJsonObject.getString("ProductName"));
                shoptypeNameTextView.setText(posmReportJsonObject.getString("ShopName"));
                qtyTextView.setText(posmReportJsonObject.getString("Quantity"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return view;
        }
    }
}
