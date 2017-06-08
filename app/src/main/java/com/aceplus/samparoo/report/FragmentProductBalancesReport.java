package com.aceplus.samparoo.report;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;


import com.aceplus.samparoo.R;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class FragmentProductBalancesReport extends Fragment {

    ListView saleInvoiceReportsListView;

    ArrayList<JSONObject> productBalanceReportsArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_balance_report, container, false);

        saleInvoiceReportsListView = (ListView) view.findViewById(R.id.productBalanceReports);
        ArrayAdapter<JSONObject> productBalanceReportsArrayAdapter = new ProductBalanceReportsArrayAdapter(getActivity());
        saleInvoiceReportsListView.setAdapter(productBalanceReportsArrayAdapter);
        return view;
    }

    private class ProductBalanceReportsArrayAdapter extends ArrayAdapter<JSONObject> {

        public final Activity context;

        public ProductBalanceReportsArrayAdapter(Activity context) {

            super(context, R.layout.list_row_product_balance_report, productBalanceReportsArrayList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            JSONObject saleInvoiceReportJsonObject = productBalanceReportsArrayList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_product_balance_report, null, true);

            TextView productNameTextView = (TextView) view.findViewById(R.id.productName);
            TextView totalQuantityTextView = (TextView) view.findViewById(R.id.totalQuantity);
            TextView soldQuantityTextView = (TextView) view.findViewById(R.id.soldQuantity);
            TextView remainingQuantityTextView = (TextView) view.findViewById(R.id.remainingQuantity);
            TextView exchangeQuantityTextView= (TextView) view.findViewById(R.id.exchangeQuantity);
            TextView returnQuantityTextView= (TextView) view.findViewById(R.id.returnQuantity);
            TextView deliveryQuantityTextView= (TextView) view.findViewById(R.id.deliveryQuantity);
            TextView presentQuantityTextView= (TextView) view.findViewById(R.id.presentQuantity);


            try {
                int remaingingQuantity = (int) saleInvoiceReportJsonObject.getDouble("remainingQuantity");
                int soldQuantity = (int) saleInvoiceReportJsonObject.getDouble("soldQuantity");

                int returnQuantity = (int) saleInvoiceReportJsonObject.getDouble("returnQuantity");
                int deliveryQuantity = (int) saleInvoiceReportJsonObject.getDouble("deliveryQuantity");
                int presentQuantity = (int) saleInvoiceReportJsonObject.getDouble("presentQuantity");
                int exchangeQuantity = (int) saleInvoiceReportJsonObject.getDouble("exchangeQuantity");
                int totalQuantity = (int) saleInvoiceReportJsonObject.getDouble("totalQuantity");

                if (totalQuantity == 0 && soldQuantity == 0 && remaingingQuantity == 0 && returnQuantity == 0 && deliveryQuantity == 0 && presentQuantity == 0 && exchangeQuantity == 0) {
                    Resources res1 = getActivity().getResources();
                    remainingQuantityTextView.setTextColor(res1.getColor(R.color.accentColor));
                    productNameTextView.setTextColor(res1.getColor(R.color.accentColor));
                    totalQuantityTextView.setTextColor(res1.getColor(R.color.accentColor));
                    soldQuantityTextView.setTextColor(res1.getColor(R.color.accentColor));
                    exchangeQuantityTextView.setTextColor(res1.getColor(R.color.accentColor));
                    returnQuantityTextView.setTextColor(res1.getColor(R.color.accentColor));
                    deliveryQuantityTextView.setTextColor(res1.getColor(R.color.accentColor));
                    presentQuantityTextView.setTextColor(res1.getColor(R.color.accentColor));


                }
                productNameTextView.setText(saleInvoiceReportJsonObject.getString("productName"));
                totalQuantityTextView.setText(
                        Utils.formatAmount(saleInvoiceReportJsonObject.getDouble("totalQuantity")));
                soldQuantityTextView.setText(
                        Utils.formatAmount(saleInvoiceReportJsonObject.getDouble("soldQuantity")));
                remainingQuantityTextView.setText(
                        Utils.formatAmount(saleInvoiceReportJsonObject.getDouble("remainingQuantity")));
                exchangeQuantityTextView.setText(
                        Utils.formatAmount(saleInvoiceReportJsonObject.getDouble("exchangeQuantity")));
                returnQuantityTextView.setText(
                        Utils.formatAmount(saleInvoiceReportJsonObject.getDouble("returnQuantity")));
                deliveryQuantityTextView.setText(
                        Utils.formatAmount(saleInvoiceReportJsonObject.getDouble("deliveryQuantity")));
                presentQuantityTextView.setText(
                        Utils.formatAmount(saleInvoiceReportJsonObject.getDouble("presentQuantity")));
            } catch (JSONException e) {

                e.printStackTrace();
            }
            return view;
        }
    }
}
