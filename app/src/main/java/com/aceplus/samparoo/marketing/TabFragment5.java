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
import android.widget.TextView;

import com.aceplus.samparoo.LoginActivity;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Category;
import com.aceplus.samparoo.model.Product;
import com.aceplus.samparoo.model.SoldProduct;
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
public class TabFragment5 extends Fragment {

    AppCompatActivity activity;
    View view;

    private AutoCompleteTextView searchProductTextView;
    private Button previousCategoryButton, nextCategoryButton;
    private TextView categoryTextView;
    private ListView productsInGivenCategoryListView;

    private TextView saleDateTextView;

    private ListView soldProductListView;

    private Category[] categories;
    private int currentCategoryIndex;

    private ArrayList<SoldProduct> soldProductList = new ArrayList<SoldProduct>();
    private SoldProductListRowAdapter soldProductListRowAdapter;

    SQLiteDatabase database;

    private ImageView cancelImg, saveImg;

    String size_in_store_share_id;
    int count = 0;
    Cursor cursor;

    int locationCode = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab_fragment_5, container, false);

       /* Toolbar toolbar = (Toolbar) view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Size in Store Share");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        activity = (AppCompatActivity) getActivity();

        database = new Database(activity).getDataBase();

        cursor = database.rawQuery("select * from size_in_store_share", null);
        count = cursor.getCount();
        /*try {
            size_in_store_share_id = "SIS/" + MainFragmentActivity.userInfo.getString("userId") + "/" + MainFragmentActivity.customerId + "/" + (count + 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        size_in_store_share_id = Utils.getInvoiceNo(getActivity(), LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NO, ""), locationCode + "", Utils.FOR_SIZE_IN_STORE_SHARE);


        // Hide keyboard on startup.
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        registerIDs();

        initCategories();

        if (categories.length > 0) {

            categoryTextView.setText(categories[0].getName());
            currentCategoryIndex = 0;

            setProductListView(categories[0].getName());

            ArrayList<String> products = new ArrayList<String>();
            for (Category category : categories) {

                for (Product product : category.getProducts()) {

                    products.add(product.getName());
                }
            }
            searchProductTextView.setAdapter(new ArrayAdapter<String>(
                    activity, android.R.layout.simple_list_item_1, products));
            searchProductTextView.setThreshold(1);
        } else {

            categoryTextView.setText("No product");
        }

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

        previousCategoryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (categories.length > 0) {

                    if (currentCategoryIndex == 0) {

                        currentCategoryIndex = categories.length - 1;
                    } else {

                        currentCategoryIndex--;
                    }

                    categoryTextView.setText(categories[currentCategoryIndex].getName());
                    setProductListView(categories[currentCategoryIndex].getName());
                }
            }
        });

        nextCategoryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (categories.length > 0) {

                    if (currentCategoryIndex == categories.length - 1) {

                        currentCategoryIndex = 0;
                    } else {

                        currentCategoryIndex++;
                    }

                    categoryTextView.setText(categories[currentCategoryIndex].getName());
                    setProductListView(categories[currentCategoryIndex].getName());
                }
            }
        });

        productsInGivenCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                sellProduct(categoryTextView.getText().toString(), parent.getItemAtPosition(position).toString());
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
            }
        });

    }

    private void insertintoDB() {

        // String customerName = TabFragment1.customerName;
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

        database.beginTransaction();
        database.execSQL("INSERT INTO size_in_store_share VALUES (\""
                + size_in_store_share_id + "\", \""
                + MainFragmentActivity.customerId + "\", \'"
                + saleDate + "\'"
                + ")");

        for (int i = 0; i < soldProductList.size(); i++) {
            database.execSQL("INSERT INTO size_in_store_share_detail VALUES (\""
                    + size_in_store_share_id + "\", \""
                    + soldProductList.get(i).getProduct().getName() + "\", \""
                    + soldProductList.get(i).getSize_in_store_share() + "\""
                    + ")");
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    private void initCategories() {

        if (categories == null) {

            SQLiteDatabase db = (new Database(activity)).getDataBase();

            Cursor cursor = db.rawQuery(
                    "SELECT CATEGORY_ID, CATEGORY_NAME"
                            + " FROM PRODUCT_CATEGORY"
//                            + " GROUP BY CATEGORY_ID, CATEGORY_NAME", null);
                    , null);

            categories = new Category[cursor.getCount()];
            while (cursor.moveToNext()) {

                categories[cursor.getPosition()] = new Category(cursor.getString(cursor.getColumnIndex("CATEGORY_ID")), cursor.getString(cursor.getColumnIndex("CATEGORY_NAME")));
                categories[cursor.getPosition()].setProducts(getProducts(categories[cursor.getPosition()].getId()));
            }
        }
    }

    private Product[] getProducts(String categoryId) {

        Product[] products;

        SQLiteDatabase db = (new Database(activity)).getDataBase();

        Cursor cursor = db.rawQuery(
                "SELECT PRODUCT_ID, PRODUCT_NAME, SELLING_PRICE"
                        + ", PURCHASE_PRICE, DISCOUNT_TYPE, REMAINING_QTY"
                        + " FROM PRODUCT WHERE CATEGORY_ID = '" + categoryId + "'", null);

        products = new Product[cursor.getCount()];
        while (cursor.moveToNext()) {

            Product tempProduct = new Product(
                    cursor.getString(cursor.getColumnIndex("PRODUCT_ID"))
                    , cursor.getString(cursor.getColumnIndex("PRODUCT_NAME"))
                    , cursor.getDouble(cursor.getColumnIndex("SELLING_PRICE"))
                    , cursor.getDouble(cursor.getColumnIndex("PURCHASE_PRICE"))
                    , cursor.getString(cursor.getColumnIndex("DISCOUNT_TYPE"))
                    , cursor.getInt(cursor.getColumnIndex("REMAINING_QTY")));

            products[cursor.getPosition()] = tempProduct;
        }

        return products;
    }

    private void setProductListView(String categoryName) {

        if (categories.length > 0) {

            Category tempCategory = null;

            for (Category category : categories) {

                if (category.getName().equals(categoryName)) {

                    tempCategory = category;
                    break;
                }
            }

            String[] productNames = null;
            if (tempCategory != null) {

                productNames = new String[tempCategory.getProducts().length];
                for (int i = 0; i < productNames.length; i++) {

                    productNames[i] = tempCategory.getProducts()[i].getName();
                }
            }

            if (productNames != null) {

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity, R.layout.custom_simple_list_item_1, android.R.id.text1, productNames);
                productsInGivenCategoryListView.setAdapter(arrayAdapter);
            }
        }
    }

    private void sellProduct(String categoryName, String productName) {

        if (categories != null) {

            Product tempProduct = null;

            if (categoryName != null && categoryName.length() > 0) {

                for (Category category : categories) {

                    if (category.getName().equals(categoryName)) {

                        for (Product product : category.getProducts()) {

                            if (product.getName().equals(productName)) {

                                tempProduct = product;
                            }
                        }
                    }
                }
            } else if (productName != null && productName.length() > 0) {

                for (Category category : categories) {

                    for (Product product : category.getProducts()) {

                        if (product.getName().equals(productName)) {

                            tempProduct = product;
                        }
                    }
                }
            }

            if (tempProduct != null) {

                soldProductList.add(new SoldProduct(tempProduct, false));
                soldProductListRowAdapter.notifyDataSetChanged();
            }
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
