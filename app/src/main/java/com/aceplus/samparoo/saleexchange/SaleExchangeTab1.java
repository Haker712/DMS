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

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.SaleReturn;
import com.aceplus.samparoo.model.SaleReturnDetailreport;
import com.aceplus.samparoo.report.FragmentSaleReturnReport;
import com.aceplus.samparoo.utils.Database;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by phonelin on 2/23/17.
 */

public class SaleExchangeTab1 extends Fragment {

    ListView salereturnlistview;
    ArrayList<JSONObject> saleReturnReportsArrayList;
    String sale_return_id;

    SQLiteDatabase database;

    String qty, remark, product_name;

    SaleReturnDetailreport saleReturnDetailreport = new SaleReturnDetailreport();
    List<SaleReturnDetailreport> saleReturnDetailreports = new ArrayList<SaleReturnDetailreport>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        database = new Database(getActivity()).getDataBase();

        saleReturnReportsArrayList=getSaleReturnReports();


        View view = inflater.inflate(R.layout.fragment_sale_return_report, container, false);
        salereturnlistview = (ListView) view.findViewById(R.id.saleReturnReports);
        salereturnlistview.setAdapter(new SaleExchangeTab1.SaleReturnReportAdapter(getActivity()));
        salereturnlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                JSONObject saleReturnReportJsonObject = saleReturnReportsArrayList.get(position);
                try {
                    sale_return_id=saleReturnReportJsonObject.getString("saleReturnId");
                    Log.i("invoice_Id", sale_return_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                saleReturnDetailreports.clear();

                Cursor cursor_sale_return_id= database.rawQuery("select * from SALE_RETURN_DETAIL WHERE SALE_RETURN_ID='"+sale_return_id+"'",null);


                while (cursor_sale_return_id.moveToNext()){


                    qty= cursor_sale_return_id.getString(cursor_sale_return_id.getColumnIndex("QUANTITY"));
                    remark=cursor_sale_return_id.getString(cursor_sale_return_id.getColumnIndex("REMARK"));

                    String product_id=cursor_sale_return_id.getString(cursor_sale_return_id.getColumnIndex("PRODUCT_ID"));

                    Cursor cursor_product_id=database.rawQuery("select * from PRODUCT WHERE PRODUCT_ID='"+product_id+"'",null);

                    while (cursor_product_id.moveToNext()){



                        product_name=cursor_product_id.getString(cursor_product_id.getColumnIndex("PRODUCT_NAME"));

                    }

                    saleReturnDetailreport.setProductName(product_name);
                    saleReturnDetailreport.setQuantity(qty);
                    saleReturnDetailreport.setRemark(remark);

                }

                saleReturnDetailreports.add(saleReturnDetailreport);




                View dialogBoxView = getActivity().getLayoutInflater().inflate(R.layout.dialog_box_sale_return_detail, null);
                ListView salereturnDetailListview = (ListView) dialogBoxView.findViewById(R.id.salereturnreportdetail);

                salereturnDetailListview.setAdapter(new SaleExchangeTab1.SaleReturnDetailAdapter(getActivity()));
                new AlertDialog.Builder(getActivity())
                        .setView(dialogBoxView)
                        .setTitle("SALERETURN DETAIL")
                        .setPositiveButton("OK", null)
                        .show();



            }
        });




        return view;
    }

    private class SaleReturnReportAdapter extends ArrayAdapter<JSONObject> {

        public final Activity context;

        public SaleReturnReportAdapter(Activity context) {

            super(context, R.layout.list_row_sale_return_report, saleReturnReportsArrayList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            JSONObject saleReturnReportJsonObject = saleReturnReportsArrayList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_sale_return_report, null, true);

            TextView customerNameTextView = (TextView) view.findViewById(R.id.customerName);
            TextView addressTextView= (TextView) view.findViewById(R.id.customerAddress);
            TextView dateTextView= (TextView) view.findViewById(R.id.date);

            try {

                customerNameTextView.setText(saleReturnReportJsonObject.getString("customerName"));
                addressTextView.setText(saleReturnReportJsonObject.getString("address"));
                dateTextView.setText(saleReturnReportJsonObject.getString("returnedDate"));




            } catch (JSONException e) {

                e.printStackTrace();
            }
            return view;
        }
    }

    private class SaleReturnDetailAdapter extends ArrayAdapter<SaleReturnDetailreport> {

        public final Activity context;

        public SaleReturnDetailAdapter(Activity context) {

            super(context, R.layout.list_row_sale_return_detail, saleReturnDetailreports);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            SaleReturnDetailreport saleReturnDetailreport = saleReturnDetailreports.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_sale_return_detail, null, true);

            TextView productNameTextView = (TextView) view.findViewById(R.id.salereturnproductName);
            TextView quantityTextView = (TextView) view.findViewById(R.id.salereturnQty);
            TextView remarkTextView= (TextView) view.findViewById(R.id.remark);

            productNameTextView.setText(saleReturnDetailreport.getProductName());
            quantityTextView.setText(saleReturnDetailreport.getQuantity());
            remarkTextView.setText(saleReturnDetailreport.getRemark());


            return view;
        }
    }


    private ArrayList<JSONObject> getSaleReturnReports() {

        ArrayList<JSONObject> saleReturnReportsArrayList = new ArrayList<JSONObject>();

        Cursor cursor = database.rawQuery("SELECT S.SALE_RETURN_ID, S.RETURNED_DATE, S.PC_ADDRESS, CUSTOMER.CUSTOMER_NAME, CUSTOMER.ADDRESS FROM SALE_RETURN S INNER JOIN CUSTOMER ON CUSTOMER.ID = S.CUSTOMER_ID WHERE SALE_RETURN_ID LIKE 'SX%'", null);
        while (cursor.moveToNext()) {

            JSONObject saleReturnReportJsonObject = new JSONObject();
            try {
                /*saleReturnReportJsonObject.put("customerAddress", cursor.getString(cursor.getColumnIndex("ADDRESS")));
                saleReturnReportJsonObject.put("customerName"
                        , cursor.getString(cursor.getColumnIndex("CUSTOMER_NAME")));
                saleReturnReportJsonObject.put("date", cursor.getString(cursor.getColumnIndex("DATE")));
                JSONArray productListJSONArray = new JSONArray(cursor.getString(cursor.getColumnIndex("RETURN_PRODUCT")));
                saleReturnReportJsonObject.put("returnProductList", productListJSONArray);
                //saleReturnReportJsonObject.put("returnProductList", cursor.getString(cursor.getColumnIndex("RETURN_PRODUCT")));*/

                saleReturnReportJsonObject.put("saleReturnId", cursor.getString(cursor.getColumnIndex("SALE_RETURN_ID")));
                saleReturnReportJsonObject.put("customerName", cursor.getString(cursor.getColumnIndex("CUSTOMER_NAME")));
                saleReturnReportJsonObject.put("returnedDate", cursor.getString(cursor.getColumnIndex("RETURNED_DATE")));
                saleReturnReportJsonObject.put("address", cursor.getString(cursor.getColumnIndex("ADDRESS")));

            } catch (JSONException e) {

                e.printStackTrace();
            }

            saleReturnReportsArrayList.add(saleReturnReportJsonObject);
        }

        return saleReturnReportsArrayList;
    }

}
