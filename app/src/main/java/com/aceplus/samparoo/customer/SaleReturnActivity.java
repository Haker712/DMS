package com.aceplus.samparoo.customer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.aceplus.samparoo.LoginActivity;
import com.aceplus.samparoo.PrintInvoiceActivity;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Category;
import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.model.Product;
import com.aceplus.samparoo.model.SaleReturn;
import com.aceplus.samparoo.model.SaleReturnDetail;
import com.aceplus.samparoo.model.SoldProduct;
import com.aceplus.samparoo.model.forApi.Invoice;
import com.aceplus.samparoo.model.forApi.InvoiceResponse;
import com.aceplus.samparoo.model.forApi.SaleReturnApi;
import com.aceplus.samparoo.model.forApi.SaleReturnItem;
import com.aceplus.samparoo.model.forApi.SaleReturnRequest;
import com.aceplus.samparoo.model.forApi.SaleReturnRequestData;
import com.aceplus.samparoo.myinterface.OnActionClickListener;
import com.aceplus.samparoo.retrofit.RetrofitServiceFactory;
import com.aceplus.samparoo.retrofit.UploadService;
import com.aceplus.samparoo.utils.Constant;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by i'm lovin' her on 10/21/15.
 */
public class SaleReturnActivity extends AppCompatActivity implements OnActionClickListener {

    public static final String USER_INFO_KEY = "user-info-key";
    public static final String CUSTOMER_INFO_KEY = "customer-info-key";
    //JSONObject userInfo;
    Customer customer;

    private AutoCompleteTextView searchProductTextView;
    private ListView productsInGivenCategoryListView;

    private TextView saleDateTextView, txtCustomerName, netAmountTextView;

    private ListView soldProductListView;

    private Category[] categories;
    private int currentCategoryIndex, customerId;

    private ArrayList<SoldProduct> soldProductList = new ArrayList<SoldProduct>();
    private SoldProductListRowAdapter soldProductListRowAdapter;

    SQLiteDatabase database;

    private ImageView cancelImg, comfirmImg;

    EditText returnCashAmtEditText, returnDiscountEditText;

    String sale_return_id, saleman_Id;
    int count = 0;
    Cursor cursor;

    String check;

    Product[] products;
    private ArrayList<String> productsForSearch = new ArrayList<String>();

    TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sale_return_layout);

        // Hide keyboard on startup.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        customer = (Customer) getIntent().getSerializableExtra(CUSTOMER_INFO_KEY);

        database = new Database(this).getDataBase();

        Utils.setOnActionClickListener(this);

        registerIDs();

        cursor = database.rawQuery("SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = \'" + customer.getCustomerId() + "\';", null);
        while (cursor.moveToNext()) {
            customerId = cursor.getInt(cursor.getColumnIndex("id"));
        }

        cursor = database.rawQuery("select * from sale_return", null);
        count = cursor.getCount();

        try {
            saleman_Id = LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, "");


            Intent intent = this.getIntent();

            if (intent != null) {

                check = intent.getExtras().getString("SaleExchange");

                if (check.equalsIgnoreCase("yes")) {

                    // titleTextView.setText(R.string.sale_return);
//                sale_return_id = Utils.getInvoiceNo(this, LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NO, ""), "YGN", Utils.FOR_SALE_RETURN_EXCHANGE);
                    titleTextView.setText(R.string.sale_exchange);
                    sale_return_id = Utils.getInvoiceNo(this, LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NO, ""), String.valueOf(getLocationCode()), Utils.FOR_SALE_RETURN_EXCHANGE);

                } else {
                    sale_return_id = Utils.getInvoiceNo(this, LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NO, ""), String.valueOf(getLocationCode()), Utils.FOR_SALE_RETURN);

                }

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Utils.backToLogin(this);
        }


        products = getProducts("");

        //productsForSearch.clear();
        Log.i("products length", products.length + "");

        for (int i = 0; i < products.length; i++) {
            productsForSearch.add(products[i].getName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.custom_simple_list_item_1, android.R.id.text1, productsForSearch);
        productsInGivenCategoryListView.setAdapter(arrayAdapter);

        searchProductTextView.setAdapter(new ArrayAdapter<String>(SaleReturnActivity.this, android.R.layout.simple_list_item_1, productsForSearch));
        searchProductTextView.setThreshold(1);

        searchProductTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                /*sellProduct(null, parent.getItemAtPosition(position).toString());
                searchProductTextView.setText("");*/

                Product tempProduct = null;

                for (Product product : products) {

                    if (product.getName().equals(parent.getItemAtPosition(position).toString())) {

                        tempProduct = product;
                    }
                }

                if (tempProduct != null) {
                    boolean sameProduct = false;
                    for (SoldProduct tempSoldProduct : soldProductList) {
                        if (tempSoldProduct.getProduct().getStockId() == tempProduct.getStockId()) {
                            sameProduct = true;
                            break;
                        }
                    }

                    if (!sameProduct) {
                        soldProductList.add(new SoldProduct(tempProduct, false));
                        soldProductListRowAdapter.notifyDataSetChanged();
                    } else {
                        Utils.commonDialog("Already have this product", SaleReturnActivity.this, 2);
                    }
                }
                searchProductTextView.setText("");
            }
        });

        productsInGivenCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                //sellProduct(categoryTextView.getText().toString(), parent.getItemAtPosition(position).toString());

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

        setAdapters();

        catchEvents();
    }

    private void registerIDs() {
        this.titleTextView = (TextView) findViewById(R.id.title);
        searchProductTextView = (AutoCompleteTextView) findViewById(R.id.searchAutoCompleteTextView);
        productsInGivenCategoryListView = (ListView) findViewById(R.id.productsListView);
        saleDateTextView = (TextView) findViewById(R.id.saleDateTextView);
        soldProductListView = (ListView) findViewById(R.id.soldProductList);

        txtCustomerName = (TextView) findViewById(R.id.txtCustomerName);
        cancelImg = (ImageView) findViewById(R.id.cancel_img);
        comfirmImg = (ImageView) findViewById(R.id.confirm_img);
        netAmountTextView = (TextView) findViewById(R.id.netAmountTextView);
        returnCashAmtEditText = (EditText) findViewById(R.id.returnCashAmtEditText);
        returnDiscountEditText = (EditText) findViewById(R.id.saleReturnDiscountEditText);
    }

    private void setAdapters() {
        soldProductListRowAdapter = new SoldProductListRowAdapter(this, R.layout.listrow_sale_return);
        soldProductListView.setAdapter(soldProductListRowAdapter);
        soldProductListRowAdapter.notifyDataSetChanged();
    }

    private void catchEvents() {
        txtCustomerName.setText(customer.getCustomerName());

        saleDateTextView.setText(Utils.getCurrentDate(false));

        soldProductListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {

                new AlertDialog.Builder(SaleReturnActivity.this)
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


        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaleReturnActivity.this.onBackPressed();
            }
        });

        comfirmImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.askConfirmationDialog("Save", "Do you want to confirm?", "", SaleReturnActivity.this);

            }
        });
    }

    private void insertintoDB() {
        String customerName = customer.getCustomerName();
        String customerAddress = customer.getAddress();
        String saleDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

        JSONArray saleProducts = new JSONArray();
        for (SoldProduct soldProduct : SaleReturnActivity.this.soldProductList) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("productName", soldProduct.getProduct().getName());
                jsonObject.put("qty", soldProduct.getQuantity());
                jsonObject.put("remark", soldProduct.getRemark());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            saleProducts.put(jsonObject);
        }
        String saleProduct = saleProducts.toString();
        System.out.println("saleProduct is>>>>>>" + saleProduct.toString());

        SaleReturn saleReturn = new SaleReturn();
        saleReturn.setSaleReturnId(sale_return_id);
        saleReturn.setCustomerId(customerId);

        // get location id from db table
        Cursor cursor = database.rawQuery("SELECT LocationId" + " FROM Location", null);
        while (cursor.moveToNext()) {
            saleReturn.setLocationId(cursor.getInt(cursor.getColumnIndex("LocationId")));
        }
        String netAmtTxt = netAmountTextView.getText().toString().replace(",", "");
        Double netAmt = Double.parseDouble(netAmtTxt);
        saleReturn.setAmt(netAmt);

        Double payAmt = 0.0, discountAmt = 0.0;

        if(returnCashAmtEditText.getText().toString() != null && !returnCashAmtEditText.getText().toString().equals("")) {
            payAmt = Double.parseDouble(returnCashAmtEditText.getText().toString());
        }

        saleReturn.setPayAmt(Double.parseDouble(returnCashAmtEditText.getText().toString()));
        saleReturn.setPcAddress(Utils.getDeviceId(SaleReturnActivity.this));
        saleReturn.setReturnedDate(saleDate);

        if(payAmt > netAmt || payAmt.equals(netAmt)) {
            saleReturn.setInvoiceStatus("CA");
        } else {
            saleReturn.setInvoiceStatus("CR");
        }

        if(returnDiscountEditText.getText().toString() != null && !returnDiscountEditText.getText().toString().equals("")) {
            discountAmt = Double.parseDouble(returnDiscountEditText.getText().toString());
        }
        saleReturn.setDiscountAmount(discountAmt);

        database.beginTransaction();

        insertSaleReturn(saleReturn);

        for (int i = 0; i < soldProductList.size(); i++) {
            SaleReturnDetail saleReturnDetail = new SaleReturnDetail();
            saleReturnDetail.setSaleReturnId(sale_return_id);
            saleReturnDetail.setProductId(soldProductList.get(i).getProduct().getId());
            saleReturnDetail.setPrice(soldProductList.get(i).getProduct().getPrice());
            saleReturnDetail.setQuantity(soldProductList.get(i).getQuantity());
            saleReturnDetail.setRemark(soldProductList.get(i).getRemark());
            insertSaleReturnDetail(saleReturnDetail);

            if (check.equalsIgnoreCase("yes")) {
                database.execSQL("UPDATE PRODUCT SET REMAINING_QTY = REMAINING_QTY + " + soldProductList.get(i).getQuantity()
                        + ", EXCHANGE_QTY = EXCHANGE_QTY + " + soldProductList.get(i).getQuantity() + " WHERE PRODUCT_ID = \'" + soldProductList.get(i).getProduct().getId() + "\'");
            } else {
                database.execSQL("UPDATE PRODUCT SET REMAINING_QTY = REMAINING_QTY + " + soldProductList.get(i).getQuantity()
                        + ", RETURN_QTY = RETURN_QTY + " + soldProductList.get(i).getQuantity() + " WHERE PRODUCT_ID = \'" + soldProductList.get(i).getProduct().getId() + "\'");
            }
        }

        database.setTransactionSuccessful();
        database.endTransaction();

        if (check.equalsIgnoreCase("yes")) {

            Double netAmount = 0.0;

            for (SoldProduct soldProduct : soldProductList) {
                netAmount += soldProduct.getNetAmount(SaleReturnActivity.this);

                //Log.e("Tot Amt>>>", soldProduct.getTotalAmount() + "");
            }

            Intent intent = new Intent(SaleReturnActivity.this, SaleActivity.class);
            intent.putExtra("SaleExchange", "yes");
            intent.putExtra(SaleActivity.CUSTOMER_INFO_KEY, customer);
            intent.putExtra(SaleActivity.SALE_RETURN_INVOICEID_KEY, sale_return_id);
            intent.putExtra(Constant.KEY_SALE_RETURN_AMOUNT, saleReturn.getDiscountAmount() + saleReturn.getPayAmt());
            startActivity(intent);
        } else {

            /*Intent intent = new Intent(SaleReturnActivity.this, CustomerActivity.class);
            intent.putExtra("SaleExchange", "no");
            startActivity(intent);
            finish();*/

            Invoice invoice = new Invoice();
            invoice.setId(sale_return_id);
            invoice.setDate(saleReturn.getReturnedDate());
            invoice.setCustomerId(saleReturn.getCustomerId() + "");
            invoice.setLocationCode(saleReturn.getLocationId());
            invoice.setTotalAmt(saleReturn.getAmt());
            invoice.setTotalPayAmt(saleReturn.getPayAmt());
            invoice.setTotalDiscountAmt(saleReturn.getDiscountAmount());
            invoice.setDeviceId(saleReturn.getPcAddress());
            invoice.setInvoiceStatus(saleReturn.getInvoiceStatus());
            invoice.setSalepersonId(Integer.parseInt(saleman_Id));

            Intent intent = new Intent(SaleReturnActivity.this, PrintInvoiceActivity.class);
            intent.putExtra(SaleCheckoutActivity.INVOICE, invoice);
            intent.putExtra(SaleCheckoutActivity.SOLD_PROUDCT_LIST_KEY
                    , SaleReturnActivity.this.soldProductList);
            intent.putExtra("PRINT_MODE"
                    , "SR");
            startActivity(intent);
        }


    }

    /**
     * Insert sale return to database.
     *
     * @param saleReturn sale return
     */
    void insertSaleReturn(SaleReturn saleReturn) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("SALE_RETURN_ID", saleReturn.getSaleReturnId());
        contentValues.put("CUSTOMER_ID", saleReturn.getCustomerId());
        contentValues.put("LOCATION_ID", saleReturn.getLocationId());
        contentValues.put("AMT", saleReturn.getAmt());
        contentValues.put("PAY_AMT", saleReturn.getPayAmt());
        contentValues.put("PC_ADDRESS", saleReturn.getPcAddress());
        contentValues.put("RETURNED_DATE", saleReturn.getReturnedDate());
        contentValues.put("DELETE_FLAG", 0);
        contentValues.put("INVOICE_STATUS", saleReturn.getInvoiceStatus());
        contentValues.put("SALE_MAN_ID", saleman_Id);
        contentValues.put("DISCOUNT", saleReturn.getDiscountAmount());

        database.insert("SALE_RETURN", null, contentValues);

/*        database.execSQL("INSERT INTO SALE_RETURN VALUES (\""
                + saleReturn.getSaleReturnId() + "\", \""
                + saleReturn.getCustomerId() + "\", \""
                + saleReturn.getLocationId() + "\", \""
                + saleReturn.getAmt() + "\", \""
                + saleReturn.getPayAmt() + "\", \""
                + saleReturn.getPcAddress() + "\", \""
                + saleReturn.getReturnedDate() + "\""
                + ", 0)");*/
    }

    /**
     * Insert sale return detail to db.
     *
     * @param saleReturnDetail sale return detail
     */
    private void insertSaleReturnDetail(SaleReturnDetail saleReturnDetail) {
        database.execSQL("INSERT INTO SALE_RETURN_DETAIL VALUES (\""
                + saleReturnDetail.getSaleReturnId() + "\", \""
                + saleReturnDetail.getProductId() + "\", \""
                + saleReturnDetail.getPrice() + "\", \""
                + saleReturnDetail.getQuantity() + "\", \""
                + saleReturnDetail.getRemark() + "\""
                + ", 0)");
    }

    private void initCategories() {

        if (categories == null) {

            SQLiteDatabase db = (new Database(this)).getDataBase();

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

        SQLiteDatabase db = (new Database(this)).getDataBase();

        /*Cursor cursor = db.rawQuery(
                "SELECT PRODUCT_ID, PRODUCT_NAME, SELLING_PRICE"
                        + ", PURCHASE_PRICE, DISCOUNT_TYPE, REMAINING_QTY"
                        + " FROM PRODUCT WHERE CATEGORY_ID = '" + categoryId + "'", null);*/

        Cursor cursor = db.rawQuery("SELECT * FROM PRODUCT", null);

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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.custom_simple_list_item_1, android.R.id.text1, productNames);
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

    /**
     * Get location code from database.
     *
     * @return location code
     */
    private int getLocationCode() {
        int locationCode = 0;
        String locationCodeName = "";
        Cursor cursorForLocation = database.rawQuery("select * from Location", null);
        while (cursorForLocation.moveToNext()) {
            locationCode = cursorForLocation.getInt(cursorForLocation.getColumnIndex(DatabaseContract.Location.id));
            locationCodeName = cursorForLocation.getString(cursorForLocation.getColumnIndex(DatabaseContract.Location.no));
        }

        return locationCode;
    }

    @Override
    public void onBackPressed() {
        if (check.equalsIgnoreCase("yes")) {
            Intent intent = new Intent(SaleReturnActivity.this, CustomerActivity.class);
            intent.putExtra("SaleExchange", "yes");
            startActivity(intent);
            finish();
        } else {
            Utils.backToCustomer(this);
        }
    }

    @Override
    public void onActionClick(String type) {
        if (soldProductList.size() == 0) {

            new AlertDialog.Builder(SaleReturnActivity.this)
                    .setTitle("Alert")
                    .setMessage("You must specify at least one product.")
                    .setPositiveButton("OK", null)
                    .show();

            return;
        }

        for (SoldProduct soldProduct : soldProductList) {

            if (soldProduct.getQuantity() == 0) {

                new AlertDialog.Builder(SaleReturnActivity.this)
                        .setTitle("Alert")
                        .setMessage("Quantity must not be zero.")
                        .setPositiveButton("OK", null)
                        .show();

                return;
            }
        }

        if (returnCashAmtEditText.getText().length() == 0 || returnCashAmtEditText.getText().toString().equals("")) {
            new AlertDialog.Builder(SaleReturnActivity.this)
                    .setTitle("Alert")
                    .setIcon(R.drawable.fail)
                    .setMessage("Return Cash Amount must be required.")
                    .setPositiveButton("OK", null)
                    .show();

            return;
        } else if (!Utils.isNumeric(returnCashAmtEditText.getText().toString())) {
            new AlertDialog.Builder(SaleReturnActivity.this)
                    .setTitle("Alert").setIcon(R.drawable.fail)
                    .setMessage("Please enter valid amount.")
                    .setPositiveButton("OK", null)
                    .show();

            return;
        }

        insertintoDB();
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
            final Button qty = (Button) view.findViewById(R.id.qty);
            final TextView price = (TextView) view.findViewById(R.id.price);
            final TextView remark = (TextView) view.findViewById(R.id.remark);

            nameTextView.setText(soldProduct.getProduct().getName());
            price.setText(soldProduct.getProduct().getPrice().toString());

            qty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View view = layoutInflater.inflate(R.layout.dialog_box_sale_quantity, null);

                    final TextView sizeinstoreshare = (TextView) view.findViewById(R.id.availableQuantity);
                    sizeinstoreshare.setText("Quantity");
                    final EditText quantityEditText = (EditText) view.findViewById(R.id.quantity);
                    final TextView messageTextView = (TextView) view.findViewById(R.id.message);

                    final AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setView(view)
                            .setTitle("Quantity")
                            .setPositiveButton("Confirm", null)
                            .setNegativeButton("Cancel", null)
                            .create();
                    alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
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
                                    int qty = Integer.parseInt(quantityEditText.getText().toString());
                                    soldProduct.setQuantity(qty);
                                    soldProductListRowAdapter.notifyDataSetChanged();

                                    alertDialog.dismiss();
                                }
                            });
                        }
                    });
                    alertDialog.show();
                }
            });
            qty.setText(soldProduct.getQuantity() + "");


            remark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View view = layoutInflater.inflate(R.layout.dialog_box_remark_sale_return, null);

                    final EditText remarkEditText = (EditText) view.findViewById(R.id.remark);
                    remarkEditText.setText(soldProduct.getRemark());
                    final TextView messageTextView = (TextView) view.findViewById(R.id.message);

                    final AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setView(view)
                            .setTitle("Remark")
                            .setPositiveButton("Confirm", null)
                            .setNegativeButton("Cancel", null)
                            .create();
                    alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialog) {
                            Button confirmButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                            confirmButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (remarkEditText.getText().toString().length() == 0) {

                                        messageTextView.setText("You must specify.");
                                        return;
                                    }
                                    String remark = remarkEditText.getText().toString();
                                    soldProduct.setRemark(remark);
                                    soldProductListRowAdapter.notifyDataSetChanged();

                                    alertDialog.dismiss();
                                }
                            });
                        }
                    });
                    alertDialog.show();
                }
            });
            remark.setText(soldProduct.getRemark());
            if (remark.getText().length() == 0) {
                remark.setText("Click here");
            }

            return view;
        }

        @Override
        public void notifyDataSetChanged() {

            super.notifyDataSetChanged();

            Double netAmount = 0.0;
            for (SoldProduct soldProduct : soldProductList) {
                netAmount += soldProduct.getNetAmount(SaleReturnActivity.this);

                //Log.e("Tot Amt>>>", soldProduct.getTotalAmount() + "");
            }

            netAmountTextView.setText(Utils.formatAmount(netAmount));
        }
    }
}
