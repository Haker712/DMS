package com.aceplus.samparoo.marketing;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aceplus.samparoo.LoginActivity;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Category;
import com.aceplus.samparoo.model.Product;
import com.aceplus.samparoo.model.SoldProduct;
import com.aceplus.samparoo.myinterface.OnActionClickListener;
import com.aceplus.samparoo.utils.Constant;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by i'm lovin' her on 3/31/16.
 */
public class TabFragment5 extends Fragment implements OnActionClickListener {

    AppCompatActivity activity;
    View view;

    private AutoCompleteTextView searchProductTextView;
    private Button previousCategoryButton, nextCategoryButton;
    private TextView categoryTextView;
    private ListView productsInGivenCategoryListView;

    private EditText remarkEditText;
    private TextView saleDateTextView;

    private ListView soldProductListView;

    private Category[] categories;
    private int currentCategoryIndex;

    private ArrayList<SoldProduct> soldProductList = new ArrayList<SoldProduct>();
    private ArrayList<String> productsForSearch = new ArrayList<String>();

    private SoldProductListRowAdapter soldProductListRowAdapter;

    Spinner statusSpinner;

    SQLiteDatabase database;

    private ImageView cancelImg, saveImg;

    String size_in_store_share_id = "", status = "";
    int count = 0, salemanId = 0;
    Cursor cursor;
    String [] statusArr;

    int locationCode = 0, cus_id = 0;

    Product[] products;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab_fragment_5, container, false);

        activity = (AppCompatActivity) getActivity();

        Utils.setOnActionClickListener(this);

        database = new Database(activity).getDataBase();

        cursor = database.rawQuery("select * from size_in_store_share", null);
        count = cursor.getCount();
        /*try {
            size_in_store_share_id = "SIS/" + MainFragmentActivity.userInfo.getString("userId") + "/" + MainFragmentActivity.customerId + "/" + (count + 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        try {
            size_in_store_share_id = Utils.getInvoiceNo(getActivity(), LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NO, ""), locationCode + "", Utils.FOR_SIZE_IN_STORE_SHARE);
            String saleManString = LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, "");
            salemanId = Integer.parseInt(saleManString);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Utils.backToLogin(this.getActivity());
        }

        // Hide keyboard on startup.
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        registerIDs();

       // initCategories();
        products = getProducts("");

        Log.i("products length", products.length + "");

        for (int i = 0; i < products.length; i++) {
            productsForSearch.add(products[i].getName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_simple_list_item_1, android.R.id.text1, productsForSearch);
        productsInGivenCategoryListView.setAdapter(arrayAdapter);

        statusArr = new String[3];
        statusArr[0] = "Quantity";
        statusArr[1] = "Amount";
        statusArr[2] = "Percentage";

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_simple_list_item_1, android.R.id.text1, statusArr);
        statusSpinner.setAdapter(statusAdapter);

        searchProductTextView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, productsForSearch));
        searchProductTextView.setThreshold(1);

        setAdapters();

        catchEvents();

        Cursor cursorForLocation = database.rawQuery("select * from Location", null);
        while (cursorForLocation.moveToNext()) {
            locationCode = cursorForLocation.getInt(cursorForLocation.getColumnIndex(DatabaseContract.Location.id));

        }

        return view;
    }

    private void registerIDs() {
        searchProductTextView = (AutoCompleteTextView) view.findViewById(R.id.searchAutoCompleteTextView);
        previousCategoryButton = (Button) view.findViewById(R.id.previusCategoryButton);
        categoryTextView = (TextView) view.findViewById(R.id.categoryTextView);
        nextCategoryButton = (Button) view.findViewById(R.id.nextCategoryButton);
        productsInGivenCategoryListView = (ListView) view.findViewById(R.id.productsListView);
        saleDateTextView = (TextView) view.findViewById(R.id.saleDateTextView);
        soldProductListView = (ListView) view.findViewById(R.id.soldProductList);
        statusSpinner = (Spinner) view.findViewById(R.id.spinner_sns_comparison_status);
        remarkEditText = (EditText) view.findViewById(R.id.edit_size_in_stock_remark);

        cancelImg = (ImageView) view.findViewById(R.id.cancel_img);
        saveImg = (ImageView) view.findViewById(R.id.save_img);
    }

    private void setAdapters() {
        soldProductListRowAdapter = new SoldProductListRowAdapter(activity, R.layout.list_row_sold_product_outlet_check);
        soldProductListView.setAdapter(soldProductListRowAdapter);
        soldProductListRowAdapter.notifyDataSetChanged();
    }

    private void catchEvents() {
        saleDateTextView.setText(Utils.getCurrentDate(false));

        productsInGivenCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Product tempProduct = null;

                for (Product product : products) {

                    if (product.getName().equals(parent.getItemAtPosition(position).toString())) {

                        tempProduct = product;
                    }
                }

                if (tempProduct != null) {

                    soldProductList.add(new SoldProduct(tempProduct, false));
                    soldProductListRowAdapter.notifyDataSetChanged();
                }

            }
        });

        soldProductListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {

                new AlertDialog.Builder(activity)
                        .setTitle("Delete product")
                        .setMessage("Are you sure you want to delete "
                                + soldProductList.get(position).getProduct().getName() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                soldProductList.remove(position);
                                soldProductListRowAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });

        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.backToMarketingActivity(activity);
            }
        });

        saveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.askConfirmationDialog("Save", "Do you want to save?", MainFragmentActivity.SNS, activity);
            }
        });

    }

    private void insertintoDB() {

        String saleDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

        JSONArray saleProducts = new JSONArray();
        for (SoldProduct soldProduct : soldProductList) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("productName", soldProduct.getProduct().getName());
                jsonObject.put("sizeinstoreShare", soldProduct.getSize_in_store_share());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            saleProducts.put(jsonObject);
        }
        String saleProduct = saleProducts.toString();
        System.out.println("saleProduct is>>>>>>" + saleProduct.toString());

        Cursor cursor = database.rawQuery("select * from CUSTOMER where CUSTOMER_ID='" + MainFragmentActivity.customerId + "'", null);
        while (cursor.moveToNext()) {
            cus_id = cursor.getInt(cursor.getColumnIndex("id"));
        }

        database.beginTransaction();

        if(statusSpinner.getSelectedItemPosition() == 0) {
            status = "Q";
        } else if(statusSpinner.getSelectedItemPosition() == 1) {
            status = "A";
        } else if(statusSpinner.getSelectedItemPosition() == 2) {
            status = "P";
        }

        Cursor rowCountCursor = database.rawQuery("SELECT size_in_store_share_id AS COUNT FROM size_in_store_share", null);
        int rowCount = 0;
        while(rowCountCursor.moveToNext()) {
            rowCount = rowCountCursor.getInt(rowCountCursor.getColumnIndex("COUNT")) + 1;
        }

        if(rowCount == 0) {
            rowCount = 1;
        }

        for (int i = 0; i < soldProductList.size(); i++) {
            database.execSQL("INSERT INTO size_in_store_share VALUES ("
                    + rowCount + ", \""
                    + cus_id + "\", \""
                    + saleDate + "\", \""
                    + soldProductList.get(i).getProduct().getStockId() + "\", \""
                    + soldProductList.get(i).getSize_in_store_share() + "\", \""
                    + status + "\", \""
                    + remarkEditText.getText().toString() + "\", \""
                    + salemanId + "\", "
                    + 0
                    + ")");
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    private Product[] getProducts(String categoryId) {

        Product[] products;

        SQLiteDatabase db = (new Database(activity)).getDataBase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM PRODUCT", null);

        products = new Product[cursor.getCount()];
        while (cursor.moveToNext()) {

            Product tempProduct = new Product(
                    cursor.getString(cursor.getColumnIndex("PRODUCT_ID"))
                    , cursor.getString(cursor.getColumnIndex("PRODUCT_NAME"))
                    , cursor.getDouble(cursor.getColumnIndex("SELLING_PRICE"))
                    , cursor.getDouble(cursor.getColumnIndex("PURCHASE_PRICE"))
                    , cursor.getString(cursor.getColumnIndex("DISCOUNT_TYPE"))
                    , cursor.getInt(cursor.getColumnIndex("REMAINING_QTY")));
            tempProduct.setStockId(cursor.getInt(cursor.getColumnIndex("ID")));
            products[cursor.getPosition()] = tempProduct;
        }

        return products;
    }

    @Override
    public void onActionClick(String type) {

        if(type.equals(MainFragmentActivity.SNS)) {
            if (soldProductList.size() == 0) {

                new AlertDialog.Builder(activity)
                        .setTitle("Alert")
                        .setMessage("You must specify at least one product.")
                        .setPositiveButton("OK", null)
                        .show();

                return;
            }


        for (SoldProduct soldProduct : soldProductList) {

            if (soldProduct.getSize_in_store_share() == 0) {

                new AlertDialog.Builder(activity)
                        .setTitle("Alert")
                        .setMessage("Size in Store Share must not be zero.")
                        .setPositiveButton("OK", null)
                        .show();

                return;
            }
        }

            insertintoDB();
            remarkEditText.setText(null);
            soldProductList.clear();
            soldProductListRowAdapter.notifyDataSetChanged();
        }
    }


    private class SoldProductListRowAdapter extends ArrayAdapter<SoldProduct> {

        public final Activity context;
        public final int resource;

        public SoldProductListRowAdapter(Activity context, int resource) {

            super(context, resource, soldProductList);
            this.context = context;
            this.resource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final SoldProduct soldProduct = soldProductList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(this.resource, null, true);

            final TextView nameTextView = (TextView) view.findViewById(R.id.name);
            final Button sizeInStoreShare = (Button) view.findViewById(R.id.qty);

            nameTextView.setText(soldProduct.getProduct().getName());

            sizeInStoreShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View view = layoutInflater.inflate(R.layout.dialog_box_sale_quantity, null);

                    final TextView qtytxtview= (TextView) view.findViewById(R.id.dialog_sale_qty_txtView);
                    qtytxtview.setText(statusArr[statusSpinner.getSelectedItemPosition()]);

                    final TextView sizeinstoreshare = (TextView) view.findViewById(R.id.availableQuantity);
                    sizeinstoreshare.setText("Size in Store Share");
                    final EditText quantityEditText = (EditText) view.findViewById(R.id.quantity);
                    final TextView messageTextView = (TextView) view.findViewById(R.id.message);

                    final AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setView(view)
                            .setTitle("Size in Store Share")
                            .setPositiveButton("Confirm", null)
                            .setNegativeButton("Cancel", null)
                            .create();
                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialog) {
                            view.findViewById(R.id.availableQuantityLayout).setVisibility(View.GONE);
                            Button confirmButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                            confirmButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (quantityEditText.getText().toString().length() == 0) {

                                        messageTextView.setText("You must specify.");
                                        return;
                                    }
                                    int size_in_store_share = Integer.parseInt(quantityEditText.getText().toString());
                                    soldProduct.setSize_in_store_share(size_in_store_share);
                                    soldProductListRowAdapter.notifyDataSetChanged();

                                    alertDialog.dismiss();
                                }
                            });
                        }
                    });
                    alertDialog.show();
                }
            });
            sizeInStoreShare.setText(soldProduct.getSize_in_store_share() + "");

            return view;
        }

        @Override
        public void notifyDataSetChanged() {

            super.notifyDataSetChanged();
        }
    }
}
