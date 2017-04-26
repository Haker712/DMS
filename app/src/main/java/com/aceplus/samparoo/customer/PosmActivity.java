package com.aceplus.samparoo.customer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Category;
import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.model.Product;
import com.aceplus.samparoo.model.SoldProduct;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yma on 2/15/17.
 *
 * PosmActivity
 */

public class PosmActivity extends AppCompatActivity {

    public static final String CUSTOMER_INFO_KEY = "customer-info-key";

    private int[] productIds;

    private AutoCompleteTextView searchProductTextView;

    private Button previousCategoryButton, nextCategoryButton;

    private TextView categoryTextView;

    private ListView productsInGivenCategoryListView;

    private TextView titleTextView;

    private TextView saleDateTextView;

    private ListView soldProductListView;

    private ImageView cancelImg, checkoutImg;

    private ArrayList<SoldProduct> soldProductList = new ArrayList<>();

    private ArrayList<Product> productList = new ArrayList<>();

    private SoldProductListRowAdapter soldProductListRowAdapter;

    private ArrayList<String> products = new ArrayList<String>();

    private Customer customer;

    @InjectView(R.id.tableHeaderOrderedQty)
    TextView textViewTableHeaderOrderedQty;

    @InjectView(R.id.tableHeaderUM)
    TextView txt_UM;

    @InjectView(R.id.tableHeaderDiscount)
    TextView txt_Discount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        setTitle(R.string.posm);
        ButterKnife.inject(this);

        customer = (Customer) getIntent().getSerializableExtra(CUSTOMER_INFO_KEY);

        if(customer!= null) {
            productIds = getProductNamesByStockId(customer.getShopTypeId());
        }

        if(productIds != null) {
            if(productIds.length > 0) {
                for(int i=0; i < productIds.length; i++) {
                    Product product = getProduct(productIds[i]);

                    if(product != null) {
                        productList.add(product);
                    }
                }
            }
        }

        registerUIs();
        initUIforPosm();
        registerEvents();
        setProductListView();
        initAutoCompleteSearch();
        initSoldProductRowAdapter();
    }

    /**
     * Initiate required ui view for POSM.
     */
    private void initUIforPosm() {
        previousCategoryButton.setVisibility(View.GONE);
        nextCategoryButton.setVisibility(View.GONE);
        categoryTextView.setVisibility(View.GONE);

        textViewTableHeaderOrderedQty.setVisibility(View.GONE);
        txt_UM.setVisibility(View.GONE);
        txt_Discount.setVisibility(View.GONE);

        saleDateTextView.setText(Utils.getCurrentDate(false));
        titleTextView.setText(R.string.posm);
    }

    /**
     * Initiate sold product row adapter
     */
    private void initSoldProductRowAdapter() {
        soldProductListRowAdapter = new SoldProductListRowAdapter(this, R.layout.list_row_sold_product_with_custom_discount);

        soldProductListView.setAdapter(soldProductListRowAdapter);
        soldProductListRowAdapter.notifyDataSetChanged();
    }

    /**
     * Initiate AutoComple text search.
     */
    private void initAutoCompleteSearch() {

        searchProductTextView.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, products));
        searchProductTextView.setThreshold(1);
    }

    /**
     * Get product name by stock id from POSM table.
     *
     * @param shopTypeId shop type id
     * @return shop type id array
     */
    private int[] getProductNamesByStockId(int shopTypeId) {
        SQLiteDatabase db = (new Database(this)).getDataBase();
        Cursor cursor = db.rawQuery("SELECT STOCK_ID FROM POSM WHERE SHOP_TYPE_ID = " + shopTypeId, null);
        int[] productIds = new int[cursor.getCount()];
        while (cursor.moveToNext()) {
            productIds[cursor.getPosition()] = cursor.getInt(cursor.getColumnIndex("STOCK_ID"));
        }
        return productIds;
    }

    /**
     * Register UIs to activity
     */
    private void registerUIs() {
        titleTextView = (TextView) findViewById(R.id.title);
        searchProductTextView = (AutoCompleteTextView) findViewById(R.id.searchAutoCompleteTextView);
        previousCategoryButton = (Button) findViewById(R.id.previusCategoryButton);
        categoryTextView = (TextView) findViewById(R.id.categoryTextView);
        nextCategoryButton = (Button) findViewById(R.id.nextCategoryButton);
        productsInGivenCategoryListView = (ListView) findViewById(R.id.productsListView);
        saleDateTextView = (TextView) findViewById(R.id.saleDateTextView);
        soldProductListView = (ListView) findViewById(R.id.soldProductList);

        cancelImg = (ImageView) findViewById(R.id.cancel_img);
        checkoutImg = (ImageView) findViewById(R.id.checkout_img);
    }

    /**
     * Get product by product id from product table.
     *
     * @param productId product id
     * @return Product
     */
    private Product getProduct(int productId) {

        Product product = null;

        SQLiteDatabase db = (new Database(this)).getDataBase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM PRODUCT WHERE ID = " + productId, null);

        while (cursor.moveToNext()) {
            product = new Product(
                    cursor.getString(cursor.getColumnIndex("PRODUCT_ID"))
                    , cursor.getString(cursor.getColumnIndex("PRODUCT_NAME"))
                    , cursor.getDouble(cursor.getColumnIndex("SELLING_PRICE"))
                    , cursor.getDouble(cursor.getColumnIndex("PURCHASE_PRICE"))
                    , cursor.getString(cursor.getColumnIndex("DISCOUNT_TYPE"))
                    , cursor.getInt(cursor.getColumnIndex("REMAINING_QTY")));

            product.setStockId(cursor.getInt(cursor.getColumnIndex("ID")));
        }

        return product;

    }

    /**
     * Register actions.
     */
    private void registerEvents() {

        searchProductTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                sellProduct(parent.getItemAtPosition(position).toString());
                searchProductTextView.setText("");
            }
        });

        productsInGivenCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                sellProduct(parent.getItemAtPosition(position).toString());
            }
        });

        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.backToMarketingActivity(PosmActivity.this);
            }
        });

        checkoutImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soldProductList.size() == 0) {

                    new AlertDialog.Builder(PosmActivity.this)
                            .setTitle("Alert")
                            .setMessage("You must specify at least one product.")
                            .setPositiveButton("OK", null)
                            .show();

                    return;
                }

                for (SoldProduct soldProduct : soldProductList) {

                    if (soldProduct.getQuantity() == 0) {

                        new AlertDialog.Builder(PosmActivity.this)
                                .setTitle("Alert")
                                .setMessage("Quantity must not be zero.")
                                .setPositiveButton("OK", null)
                                .show();

                        return;
                    }
                }

                Intent intent = new Intent(PosmActivity.this
                        , PosmCheckOutActivity.class);
                intent.putExtra(PosmCheckOutActivity.SOLD_PROUDCT_LIST_KEY
                        , PosmActivity.this.soldProductList);
                intent.putExtra(PosmCheckOutActivity.PRESENT_PROUDCT_LIST_KEY
                        , PosmActivity.this.products);
                intent.putExtra(PosmCheckOutActivity.CUSTOMER_INFO_KEY
                        , PosmActivity.this.customer);

                startActivity(intent);
                finish();
            }
        });

        soldProductListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {

                new AlertDialog.Builder(PosmActivity.this)
                        .setTitle("Delete sold product")
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
    }

    /**
     * Add sold product list to adapter
     *
     * @param productName sold product name
     */
     private void sellProduct(String productName) {

         Product tempProduct = null;

        if (productName != null && productName.length() > 0) {

                for (Product product : productList) {

                        if (product.getName().equals(productName)) {

                            tempProduct = product;
                        }
                }
        }

            if (tempProduct != null) {

                soldProductList.add(new SoldProduct(tempProduct, false));
                soldProductListRowAdapter.notifyDataSetChanged();
            }
     }

    /**
     * Set product list.
     */
    private void setProductListView() {

            String[] productNames = null;

            if (productList != null && productList.size() != 0) {

                productNames = new String[productList.size()];

                for (int i = 0; i < productNames.length; i++) {
                    if(productList.get(i).getName() != null) {
                        productNames[i] = productList.get(i).getName();
                        products.add(productList.get(i).getName());
                    }
                }
            }

            if (productNames != null) {

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.custom_simple_list_item_1, android.R.id.text1, productNames);
                productsInGivenCategoryListView.setAdapter(arrayAdapter);
            }
    }

    /**
     * SoldProductListRowAdapter
     */
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
            final Button qtyButton = (Button) view.findViewById(R.id.qty);

            qtyButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View view = layoutInflater.inflate(R.layout.dialog_box_sale_quantity, null);

                    final TextView remainingQtyTextView = (TextView) view.findViewById(R.id.availableQuantity);
                    final EditText quantityEditText = (EditText) view.findViewById(R.id.quantity);
                    final TextView messageTextView = (TextView) view.findViewById(R.id.message);

                    final AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setView(view)
                            .setTitle("Sale Quantity")
                            .setPositiveButton("Confirm", null)
                            .setNegativeButton("Cancel", null)
                            .create();
                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

                        @Override
                        public void onShow(DialogInterface arg0) {


                            remainingQtyTextView.setText(soldProduct.getProduct().getRemainingQty() + "");

                            Button confirmButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                            confirmButton.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {

                                    if (quantityEditText.getText().toString().length() == 0) {

                                        messageTextView.setText("You must specify quantity.");
                                        return;
                                    }

                                    int quantity = Integer.parseInt(quantityEditText.getText().toString());

                                    soldProduct.setQuantity(quantity);
                                    soldProductListRowAdapter.notifyDataSetChanged();

                                    alertDialog.dismiss();
                                }
                            });
                        }
                    });

                    alertDialog.show();
                }
            });

            final TextView priceTextView = (TextView) view.findViewById(R.id.price);
            final TextView discountButtonOrTextView = (TextView) view.findViewById(R.id.discount);
            final TextView totalAmountTextView = (TextView) view.findViewById(R.id.amount);

            nameTextView.setText(soldProduct.getProduct().getName());

            if (this.resource == R.layout.list_row_sold_product_with_custom_discount) {

                TextView orderedQuantityTextView = (TextView) view.findViewById(R.id.orderedQuantity);
                TextView um = (TextView) view.findViewById(R.id.um);
                TextView discount = (TextView) view.findViewById(R.id.promotionPrice);

                um.setVisibility(View.GONE);
                discount.setVisibility(View.GONE);
                orderedQuantityTextView.setVisibility(View.GONE);
                orderedQuantityTextView.setText(soldProduct.getOrderedQuantity() + "");
            }

            qtyButton.setText(soldProduct.getQuantity() + "");
            priceTextView.setText(Utils.formatAmount(soldProduct.getProduct().getPrice()));

            Double totalAmount = soldProduct.getProduct().getPrice() * soldProduct.getQuantity();
            totalAmountTextView.setText(totalAmount.toString());
            return view;
        }

        @Override
        public void notifyDataSetChanged() {

            super.notifyDataSetChanged();

            Double netAmount = 0.0;
            for (SoldProduct soldProduct : soldProductList) {
                netAmount += soldProduct.getNetAmount(PosmActivity.this);
            }

            ((TextView) context.findViewById(R.id.netAmountTextView)).setText(Utils.formatAmount(netAmount));
        }
    }
}
