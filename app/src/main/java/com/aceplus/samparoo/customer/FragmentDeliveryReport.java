package com.aceplus.samparoo.customer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.utils.Database;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Deliver;
import com.aceplus.samparoo.model.DeliverItem;
import com.aceplus.samparoo.model.Product;
import com.aceplus.samparoo.model.SoldProduct;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yma on 2/22/17.
 *
 * FragmentDeliveryReport
 */

public class FragmentDeliveryReport extends Fragment {

    ListView deliveryReportsListView;
    public List<Deliver> deliveryReportsArrayList;
    public boolean isDelivery;

    public static final String IS_DELIVERY = "is-delivery";
    public static final String CUSTOMER_INFO_KEY = "customer-info-key";
    public static final String SOLD_PROUDCT_LIST_KEY = "sold-product-list-key";
    public static final String ORDERED_INVOICE_KEY = "ordered_invoice_key";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_delivery_report, container, false);

        deliveryReportsListView = (ListView) view.findViewById(R.id.deliveryReports);
        deliveryReportsListView.setAdapter(new DeliveryAdapter(getActivity()));
        deliveryReportsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (FragmentDeliveryReport.this.isDelivery) {
                    List<DeliverItem> deliveryItemList = FragmentDeliveryReport.this.deliveryReportsArrayList.get(position).getDeliverItemList();

                    ArrayList<SoldProduct> soldProductList = FragmentDeliveryReport.this.getProductList(deliveryItemList);
                    ArrayList<SoldProduct> copySoldProductList = new ArrayList<>(soldProductList);

                    for (SoldProduct soldProduct : soldProductList) {

                        if (soldProduct.getOrderedQuantity() == 0) {

                            copySoldProductList.remove(soldProduct);
                        }
                    }
                    soldProductList = copySoldProductList;

                    Customer customer = getCustomerFromDB(deliveryReportsArrayList.get(position).getCustomerId());

                    if (soldProductList.size() == 0) {

                        new AlertDialog.Builder(getActivity())
                                .setTitle("Delivery")
                                .setMessage("No products to deliver for this invoice.")
                                .setPositiveButton("OK", null)
                                .show();

                        return;
                    } else if (customer != null && soldProductList.size() != 0) {

                        Intent intent = new Intent(getActivity(), SaleOrderActivity.class);
                        intent.putExtra(SaleOrderActivity.IS_DELIVERY, true);
                        intent.putExtra(SaleOrderActivity.CUSTOMER_INFO_KEY, customer);
                        intent.putExtra(SaleOrderActivity.SOLD_PROUDCT_LIST_KEY, soldProductList);
                        intent.putExtra(SaleOrderActivity.ORDERED_INVOICE_KEY, deliveryReportsArrayList.get(position));
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
            }
        });
        return view;
    }

    private void getDeliveryUpload() {

    }

    private ArrayList<SoldProduct> getProductList(List<DeliverItem> deliverItemList) {

        ArrayList<SoldProduct> soldProductList = new ArrayList<SoldProduct>();

        SQLiteDatabase database = new Database(getActivity()).getDataBase();
        Cursor cursor;
        for (int i = 0; i < deliverItemList.size(); i++) {

                DeliverItem deliverItem = deliverItemList.get(i);

                cursor = database.rawQuery(
                        "SELECT * FROM PRODUCT"
                                + " WHERE ID = " + deliverItem.getStockNo(), null);
                if (cursor.moveToNext()) {

                    SoldProduct soldProduct = new SoldProduct(new Product(
                            cursor.getString(cursor.getColumnIndex("PRODUCT_ID"))
                            , cursor.getString(cursor.getColumnIndex("PRODUCT_NAME"))
                            , cursor.getDouble(cursor.getColumnIndex("SELLING_PRICE"))
                            , cursor.getDouble(cursor.getColumnIndex("PURCHASE_PRICE"))
                            , cursor.getString(cursor.getColumnIndex("DISCOUNT_TYPE"))
                            , cursor.getInt(cursor.getColumnIndex("REMAINING_QTY"))), false);
                    soldProduct.setOrderedQuantity(deliverItem.getOrderQty());
                    soldProduct.setQuantity(soldProduct.getOrderedQuantity());
                    soldProduct.getProduct().setStockId(Integer.parseInt(deliverItem.getStockNo()));

                    if(deliverItem.getSPrice() == 0.0) {
                        soldProduct.getProduct().setPrice(0.0);
                    }

                    soldProductList.add(soldProduct);
                }

        }
        return soldProductList;
    }

    private Customer getCustomerFromDB(String customerId) {
        Customer customer = null;
        SQLiteDatabase database = new Database(getActivity()).getDataBase();
        Cursor cursor = database.rawQuery("SELECT * FROM CUSTOMER WHERE ID = \'" + customerId + "\';", null);
        while (cursor.moveToNext()) {
            customer = new Customer(
                    cursor.getString(cursor.getColumnIndex("CUSTOMER_ID"))
                    , cursor.getString(cursor.getColumnIndex("CUSTOMER_NAME"))
                    , cursor.getString(cursor.getColumnIndex("CUSTOMER_TYPE_ID"))
                    , cursor.getString(cursor.getColumnIndex("CUSTOMER_TYPE_NAME"))
                    , cursor.getString(cursor.getColumnIndex("ADDRESS"))
                    , cursor.getString(cursor.getColumnIndex("PH"))
                    , cursor.getString(cursor.getColumnIndex("TOWNSHIP"))
                    , cursor.getString(cursor.getColumnIndex("CREDIT_TERM"))
                    , cursor.getDouble(cursor.getColumnIndex("CREDIT_LIMIT"))
                    , cursor.getDouble(cursor.getColumnIndex("CREDIT_AMT"))
                    , cursor.getDouble(cursor.getColumnIndex("DUE_AMT"))
                    , cursor.getDouble(cursor.getColumnIndex("PREPAID_AMT"))
                    , cursor.getString(cursor.getColumnIndex("PAYMENT_TYPE"))/*
                    , cursor.getString(cursor.getColumnIndex("IS_IN_ROUTE"))*/
                    , "false"
                    , cursor.getDouble(cursor.getColumnIndex("LATITUDE"))
                    , cursor.getDouble(cursor.getColumnIndex("LONGITUDE"))
                    , cursor.getInt(cursor.getColumnIndex("VISIT_RECORD")));
            customer.setShopTypeId(cursor.getInt(cursor.getColumnIndex("shop_type_id")));
            customer.setId(cursor.getInt(cursor.getColumnIndex("id")));
        }

        return customer;
    }

    private class DeliveryAdapter extends ArrayAdapter<Deliver> {

        Activity context;

        private class ViewHolder {
            TextView txtName;
            TextView txtTotalAmount;
        }

        public DeliveryAdapter(Activity context) {
            super(context, R.layout.list_row_delivery_report, deliveryReportsArrayList);
            this.context = context;
        }

        @Nullable
        @Override
        public Deliver getItem(int position) {
            return deliveryReportsArrayList.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Deliver deliver = getItem(position);
            ViewHolder viewHolder;

            if(convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.list_row_delivery_report, parent, false);
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.customerName);
                viewHolder.txtTotalAmount = (TextView) convertView.findViewById(R.id.totalAmount);

                convertView.setTag(viewHolder);
            } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

            viewHolder.txtName.setText(deliver.getCustomerName());
            viewHolder.txtTotalAmount.setText(Utils.formatAmount(deliver.getAmount()));

            return convertView;
        }
    }
}
