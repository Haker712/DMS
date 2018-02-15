package com.aceplus.samparoo.customer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.aceplus.samparoo.LoginActivity;
import com.aceplus.samparoo.PrintInvoiceActivity;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Category;
import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.model.Product;
import com.aceplus.samparoo.model.Promotion;
import com.aceplus.samparoo.model.SoldProduct;
import com.aceplus.samparoo.model.forApi.Invoice;
import com.aceplus.samparoo.model.forApi.InvoiceDetail;
import com.aceplus.samparoo.model.forApi.InvoicePresent;
import com.aceplus.samparoo.myinterface.OnActionClickListener;
import com.aceplus.samparoo.utils.Constant;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by haker on 2/3/17.
 */
public class SaleCancelCheckout2Activity extends AppCompatActivity implements OnActionClickListener {

    public static final String REMAINING_AMOUNT_KEY = "remaining-amount-key";

    public static final String INVOICE = "INVOICE";
    public static final String INVOICE_DETAIL = "INVOICE_DETAIL";
    public static final String INVOICE_PRESENT = "INVOICE_PRESENT";

    public static final String CUSTOMER_INFO_KEY = "customer-info-key";
    public static final String SOLD_PROUDCT_LIST_KEY = "sold-product-list-key";
    public static final String PRESENT_PROUDCT_LIST_KEY = "presnet-product-list-key";
    public static final String ORDERED_INVOICE_KEY = "ordered_invoice_key";

    public static final String SALE_EXCHANGE_INVOICEID_KEY = "sale_exchange_invoiceid_key";
    public static final String DATE_KEY = "date_key";

    private TextView titleTextView;
    ListView soldProductsListView, promotionPlanItemListView, promotionPlanGiftListView;
    TextView invoiceIdTextView;
    TextView saleDateTextView;
    TextView totalAmountTextView, taxTextView, taxLabelTextView;
    TextView advancedPaidAmountTextView;
    TextView discountTextView;
    TextView netAmountTextView;
    EditText payAmountEditText;
    TextView refundTextView;
    EditText receiptPersonEditText, branchEditText, accountEditText;
    private EditText prepaidAmountEditText;
    ImageView backImg, confirmAndPrintImg;
    String paymentMethod = "", bankCardNo = "";
    RadioGroup bankOrCashRadioGroup;
    RadioButton bankRadio, cashRadio;

    SQLiteDatabase database;
    SoldProductListRowAdapter soldProductListRowAdapter;

    private JSONObject orderedInvoice;
    JSONObject salemanInfo;
    Customer customer;
    ArrayList<SoldProduct> soldProductList;
    ArrayList<String> product;

    private Category[] categories;

    ArrayList<Promotion> promotionArrayList = new ArrayList<>();

    ArrayList<Promotion> prev_promotion= new ArrayList<>();
    ArrayList<SoldProduct> prev_product = new ArrayList<>();

    PromotionProductCustomAdapter promotionProductCustomAdapter;

    Double totalVolumeDiscount = 0.0, totalVolumeDiscountPercent = 0.0, totalDiscountAmount = 0.0;
    Integer exclude = 0;
    double totalAmount = 0.0;
    String taxType = "";
    double taxPercent = 0.0, taxAmt = 0.0;
    int locationCode = 0;
    String locationCodeName = "";

    String check, saleReturnInvoiceId;
    boolean adapterFlag = true;
    LinearLayout layoutBranch, layoutBankAcc, taxLayout;
    Invoice invoice = new Invoice();
    String invoiceId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_checkout);
        database = new Database(this).getDataBase();

        /*try {
            orderedInvoice = new JSONObject(getIntent().getStringExtra(SaleActivity.ORDERED_INVOICE_KEY));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        Utils.setOnActionClickListener(this);


        customer = (Customer) getIntent().getSerializableExtra(CUSTOMER_INFO_KEY);
        if (getIntent().getSerializableExtra(SOLD_PROUDCT_LIST_KEY) != null) {
            soldProductList = (ArrayList<SoldProduct>) getIntent().getSerializableExtra(SOLD_PROUDCT_LIST_KEY);
        }

        if (getIntent().getSerializableExtra(PRESENT_PROUDCT_LIST_KEY) != null) {
            promotionArrayList = (ArrayList<Promotion>) getIntent().getSerializableExtra(PRESENT_PROUDCT_LIST_KEY);
        }

        if (getIntent().getSerializableExtra("PREV_PRESENT") != null) {
            prev_promotion = (ArrayList<Promotion>) getIntent().getSerializableExtra("PREV_PRESENT");
        }

        if (getIntent().getSerializableExtra("PREV_PRODUCT") != null) {
            prev_product = (ArrayList<SoldProduct>) getIntent().getSerializableExtra("PREV_PRODUCT");
        }

        if (getIntent().getSerializableExtra(SaleCheckoutActivity.ORDERED_INVOICE_KEY) != null) {

            invoiceId = (String) getIntent().getSerializableExtra(SaleCheckoutActivity.ORDERED_INVOICE_KEY);
        }

        registerIDs();

        titleTextView.setText("SALE CANCEL CHECKOUT");
        findViewById(R.id.advancedPaidAmountLayout).setVisibility(View.GONE);
        findViewById(R.id.totalInfoForPreOrder).setVisibility(View.GONE);
        taxLayout.setVisibility(View.VISIBLE);
        TextView discountHeader = (TextView) findViewById(R.id.tableHeaderDiscount);
        discountHeader.setText("Promotion Price");

        /*for (SoldProduct soldProduct : soldProductList) {

            totalAmount += soldProduct.getTotalAmount();
        }
        totalAmountTextView.setText(Utils.formatAmount(totalAmount));*/

        saleDateTextView.setText(Utils.getCurrentDate(false));

        Cursor cursorForLocation = database.rawQuery("select * from Location", null);
        while (cursorForLocation.moveToNext()) {
            locationCode = cursorForLocation.getInt(cursorForLocation.getColumnIndex(DatabaseContract.Location.id));
            locationCodeName = cursorForLocation.getString(cursorForLocation.getColumnIndex(DatabaseContract.Location.no));
        }

        Intent intent = this.getIntent();

        try {
            if (intent != null) {

                check = intent.getExtras().getString("SaleExchange");
                if (check.equalsIgnoreCase("yes")) {

                    double totalItemDiscountAmount = 0.0;

                    //  titleTextView.setText("SALE EXCHANGE");
                    try {
                        invoiceIdTextView.setText(invoiceId);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        Utils.backToLogin(this);
                    }

                    View layout = findViewById(R.id.SaleExchangeLayout);
                    layout.setVisibility(View.VISIBLE);

                    TextView textView_salereturnAmount = (TextView) findViewById(R.id.salereturnAmount);
                    TextView textView_payAmtfromCustomer = (TextView) findViewById(R.id.payamountfromcustomer);
                    TextView textView_refundtoCustomer = (TextView) findViewById(R.id.refundtocustomer);

                    Double salereturnAmount = getIntent().getDoubleExtra(Constant.KEY_SALE_RETURN_AMOUNT, 0.0);
                    Double saleexchangeAmount = totalAmount - totalItemDiscountAmount - totalVolumeDiscount;

                    textView_salereturnAmount.setText(salereturnAmount + "");

                    if (saleexchangeAmount > salereturnAmount) {

                        Double payAmtfromCustomer = saleexchangeAmount - salereturnAmount;
                        textView_payAmtfromCustomer.setText(payAmtfromCustomer + "");
                        textView_refundtoCustomer.setText("0");

                    } else {

                        double refundAmount = salereturnAmount - saleexchangeAmount;

                        textView_refundtoCustomer.setText(refundAmount + "");
                        textView_refundtoCustomer.setText(refundAmount + "");
                        textView_payAmtfromCustomer.setText("0");

                    }
                } else {
                    try {
                        invoiceIdTextView.setText(invoiceId);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        Utils.backToLogin(this);
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Utils.backToLogin(this);
        }
        soldProductListRowAdapter = new SoldProductListRowAdapter(this);
        soldProductsListView.setAdapter(soldProductListRowAdapter);
        calculateTotlaAmountForProduct();
        /*double a = Double.parseDouble(this.netAmountTextView.getText().toString().replace(",", ""));
        if (Double.parseDouble(this.netAmountTextView.getText().toString().replace(",", ""))
                <= this.remainingAmount) {

            findViewById(R.id.payAmountLayout).setVisibility(View.GONE);
        }*/

        setPromotionProductListView();

        catchEvents();
    }

    private void calculateTotlaAmountForProduct() {
        double soldPrice = 0.0;

        for (SoldProduct soldProduct : soldProductList) {
            if (soldProduct.getPromotionPrice() == 0.0) {
                soldPrice = soldProduct.getProduct().getPrice();
            } else {
                soldPrice = soldProduct.getPromotionPrice();
            }

            double buy_amt = soldPrice * soldProduct.getQuantity();
            totalAmount += buy_amt;
        }
    }

    private Map<String, Double> calculateVolumeDiscount(List<SoldProduct> productList) {
        String volDisFilterId = "";

        Map<String, Double> filterAmountAndPercentage = new HashMap<>();

        double soldPrice = 0.0, totalBuyAmtInclude = 0.0, totalBuyAmtExclude = 0.0, discountPercent = 0.0;
        List<SoldProduct> sameCategoryProducts = new ArrayList<>();

        try {
            Cursor cursor = database.rawQuery("select * from VOLUME_DISCOUNT_FILTER WHERE date('" + Utils.getCurrentDate(true) + "') BETWEEN date(START_DATE) AND date(END_DATE)", null);
            Log.i("VolumeDisFilterCursor", cursor.getCount() + "");
            while (cursor.moveToNext()) {
                volDisFilterId = cursor.getString(cursor.getColumnIndex(DatabaseContract.VolumeDiscountFilter.id));
                exclude = cursor.getInt(cursor.getColumnIndex(DatabaseContract.VolumeDiscountFilter.exclude));

                Log.i("volDisFilterId", volDisFilterId);

                for (SoldProduct soldProduct : productList) {
                    String stockId = soldProduct.getProduct().getStockId() + "";
                    if (checkCategoryForFilterDiscount(stockId, volDisFilterId)) {
                        sameCategoryProducts.add(soldProduct);
                    }
                }

                for (SoldProduct sameCategoryProduct : sameCategoryProducts) {
                    double buy_amt = 0.0;
                    if (sameCategoryProduct.getPromotionPrice() == 0.0) {
                        soldPrice = sameCategoryProduct.getProduct().getPrice();
                    } else {
                        soldPrice = sameCategoryProduct.getPromotionPrice();
                    }

                    buy_amt = soldPrice * sameCategoryProduct.getQuantity();
                    sameCategoryProduct.setTotalAmt(buy_amt);

                    totalBuyAmtInclude += sameCategoryProduct.getTotalAmt();

                    if (sameCategoryProduct.getPromotionPrice() == 0.0) {
                        totalBuyAmtExclude += sameCategoryProduct.getTotalAmt();
                    }
                }

                if (exclude == 0) {
                    discountPercent = getDiscountPercent(volDisFilterId, totalBuyAmtInclude);
                    filterAmountAndPercentage.put("Percentage", discountPercent);
                    double itemTotalDis = totalBuyAmtInclude * (discountPercent / 100);
                    filterAmountAndPercentage.put("Amount", itemTotalDis);

                    if (discountPercent > 0) {
                        for (SoldProduct soldProduct : sameCategoryProducts) {
                            soldProduct.setExclude(exclude);
                            double discountAmount = soldProduct.getTotalAmt() * (discountPercent / 100);
                            soldProduct.setDiscountPercent(discountPercent);
                            soldProduct.setDiscountAmount(discountAmount);
                            soldProduct.setTotalAmt(soldProduct.getTotalAmount());
                        }
                    }
                } else {
                    discountPercent = getDiscountPercent(volDisFilterId, totalBuyAmtExclude);
                    filterAmountAndPercentage.put("Percentage", discountPercent);
                    double itemTotalDis = totalBuyAmtExclude * (discountPercent / 100);
                    filterAmountAndPercentage.put("Amount", itemTotalDis);

                    if (discountPercent > 0) {
                        for (SoldProduct soldProduct : sameCategoryProducts) {
                            soldProduct.setExclude(exclude);
                            double discountAmount = soldProduct.getTotalAmt() * (discountPercent / 100);
                            soldProduct.setDiscountPercent(discountPercent);
                            soldProduct.setDiscountAmount(discountAmount);
                            soldProduct.setTotalAmt(soldProduct.getTotalAmount());
                        }
                    }
                }

            }
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return filterAmountAndPercentage;
        //discountTextView.setText(Utils.formatAmount(totalVolumeDiscount));
    }

    private double getDiscountPercent(String volDisFilterId, double buy_amt) {

        String category = "", group = "";
        boolean isAllAreCategory = false;
        double discount_percent = 0.0, discount_amt = 0.0, discount_price = 0.0;

        Cursor cusorForVolDisFilterItem = database.rawQuery("SELECT * FROM VOLUME_DISCOUNT_FILTER_ITEM WHERE VOLUME_DISCOUNT_ID = '" + volDisFilterId + "' " +
                "and FROM_SALE_AMOUNT <= " + buy_amt + " and TO_SALE_AMOUNT >= " + buy_amt + " ", null);

        if (cusorForVolDisFilterItem.getCount() == 0) {
            exclude = null;
        }

        while (cusorForVolDisFilterItem.moveToNext()) {
            category = cusorForVolDisFilterItem.getString(cusorForVolDisFilterItem.getColumnIndex(DatabaseContract.VolumeDiscountFilterItem.categoryId));
            group = cusorForVolDisFilterItem.getString(cusorForVolDisFilterItem.getColumnIndex(DatabaseContract.VolumeDiscountFilterItem.groupCodeId));
            discount_percent = cusorForVolDisFilterItem.getDouble(cusorForVolDisFilterItem.getColumnIndex(DatabaseContract.VolumeDiscountFilterItem.discountPercent));
        }

        Log.i("category", category + ", group : " + group + ", discount_percent : " + discount_percent);
        return discount_percent;

        //check category and group for product
        /*Cursor cursorForProduct = database.rawQuery("select * from PRODUCT WHERE ID = '" + productId + "'", null);
        while (cursorForProduct.moveToNext()) {
            categoryProduct = String.valueOf(cursorForProduct.getInt(cursorForProduct.getColumnIndex("CATEGORY_ID")));
            groupProduct = cursorForProduct.getString(cursorForProduct.getColumnIndex("GROUP_ID"));
        }

        if (category.equals(categoryProduct)) {
            isAllAreCategory = true;
        } else {
            isAllAreCategory = false;
        }*/


        /*if (isAllAreCategory) {
            return discount_percent;
        }
        else {
            return 0;
        }*/
    }

    String getCategoryForFilterDiscount(String volDisFilterId) {
        Cursor cusorForVolDisFilterItem = database.rawQuery("SELECT * FROM VOLUME_DISCOUNT_FILTER_ITEM WHERE VOLUME_DISCOUNT_ID = '" + volDisFilterId + "' ", null);
        String category = "";
        while (cusorForVolDisFilterItem.moveToNext()) {
            category = cusorForVolDisFilterItem.getString(cusorForVolDisFilterItem.getColumnIndex(DatabaseContract.VolumeDiscountFilterItem.categoryId));
        }

        return category;
    }

    /**
     * Check the category is discount item.
     *
     * @param productId product id
     * @return true : if the category is discount item; otherwise : false;
     */
    private boolean checkCategoryForFilterDiscount(String productId, String volDisFilId) {
        String categoryProduct = "";
        boolean isAllAreCategory = false;

        Cursor cursorForProduct = database.rawQuery("select * from PRODUCT WHERE ID = '" + productId + "'", null);
        while (cursorForProduct.moveToNext()) {
            categoryProduct = String.valueOf(cursorForProduct.getInt(cursorForProduct.getColumnIndex("CATEGORY_ID")));
        }

        if (categoryProduct.equals(getCategoryForFilterDiscount(volDisFilId))) {
            isAllAreCategory = true;
        } else {
            isAllAreCategory = false;
        }
        return isAllAreCategory;
    }

    private void calculateInvoiceDiscount() {
        String volDisId;
        double buy_amt = 0.0, noPromoBuyAmt = 0.0;

        for (SoldProduct soldProduct : soldProductList) {
            buy_amt += soldProduct.getTotalAmt();
        }

        String query = "select * from VOLUME_DISCOUNT WHERE date('" + Utils.getCurrentDate(true) + "') BETWEEN date(START_DATE) AND date(END_DATE)";
        Cursor cursor = database.rawQuery(query, null);
        Log.i("VolumeDiscountCursor", cursor.getCount() + "");
        while (cursor.moveToNext()) {
            volDisId = cursor.getString(cursor.getColumnIndex(DatabaseContract.VolumeDiscount.id));
            exclude = cursor.getInt(cursor.getColumnIndex(DatabaseContract.VolumeDiscount.exclude));

            if (exclude == 0) {
                calculateInvoiceDiscountAmount(totalAmount, volDisId);
            } else {
                for (SoldProduct soldProduct : soldProductList) {
                    if (soldProduct.getPromotionPrice() == 0.0 && soldProduct.getDiscountPercent() == 0.0) {
                        noPromoBuyAmt += soldProduct.getTotalAmt();
                    }
                }

                calculateInvoiceDiscountAmount(noPromoBuyAmt, volDisId);
            }

            Log.i("buy_amt", buy_amt + "");
            Log.i("volDisId", volDisId);
        }
    }

    void calculateInvoiceDiscountAmount(Double buy_amt, String volDisId) {

        Double discountPercentForVolDis;
        Cursor cusorForVolDisItem = database.rawQuery("SELECT * FROM VOLUME_DISCOUNT_ITEM WHERE VOLUME_DISCOUNT_ID = '" + volDisId + "' " +
                "and " + buy_amt + " >= FROM_SALE_AMT and " + buy_amt + "<= TO_SALE_AMT;", null);
        Log.i("cusorForVolDisItem", cusorForVolDisItem.getCount() + "");

        while (cusorForVolDisItem.moveToNext()) {
            discountPercentForVolDis = cusorForVolDisItem.getDouble(cusorForVolDisItem.getColumnIndex(DatabaseContract.VolumeDiscountItem.discountPercent));
            totalVolumeDiscount = buy_amt * (discountPercentForVolDis / 100);
            totalVolumeDiscountPercent = discountPercentForVolDis;
        }

        /*Log.i("totalInvoiceDiscount ----->>>>>>>", totalVolumeDiscount + "");
        double discountAmount = 0.0, discountPercentage = 0.0;
        for (SoldProduct soldProduct : soldProductList) {
            discountAmount += soldProduct.getDiscountAmount();
            discountPercentage = (soldProduct.getDiscountAmount() * 100) / totalAmount;
            //totalVolumeDiscountPercent += discountPercentage;
        }
*/
        //totalVolumeDiscountPercent
    }

    private void registerIDs() {
        this.titleTextView = (TextView) findViewById(R.id.title);
        soldProductsListView = (ListView) findViewById(R.id.soldProductList);
        promotionPlanItemListView = (ListView) findViewById(R.id.promotion_plan_item_listview);
        promotionPlanGiftListView = (ListView) findViewById(R.id.promotion_plan_gift_listview);
        saleDateTextView = (TextView) findViewById(R.id.saleDateTextView);
        invoiceIdTextView = (TextView) findViewById(R.id.invoiceId);
        totalAmountTextView = (TextView) findViewById(R.id.totalAmount);
        taxTextView = (TextView) findViewById(R.id.tax_txtview);
        taxLabelTextView = (TextView) findViewById(R.id.tax_label_salecheckout);

        advancedPaidAmountTextView = (TextView) findViewById(R.id.advancedPaidAmount);

        discountTextView = (TextView) findViewById(R.id.volumeDiscount);
        netAmountTextView = (TextView) findViewById(R.id.netAmount);
        payAmountEditText = (EditText) findViewById(R.id.payAmount);
        refundTextView = (TextView) findViewById(R.id.refund);
        receiptPersonEditText = (EditText) findViewById(R.id.receiptPerson);

        this.prepaidAmountEditText = (EditText) findViewById(R.id.prepaidAmount);

        backImg = (ImageView) findViewById(R.id.back_img);
        confirmAndPrintImg = (ImageView) findViewById(R.id.confirmAndPrint_img);
        bankOrCashRadioGroup = (RadioGroup) findViewById(R.id.activity_sale_checkout_radio_group);
        bankRadio = (RadioButton) findViewById(R.id.activity_sale_checkout_radio_bank);
        cashRadio = (RadioButton) findViewById(R.id.activity_sale_checkout_radio_cash);
        layoutBranch = (LinearLayout) findViewById(R.id.bank_branch_layout);
        layoutBankAcc = (LinearLayout) findViewById(R.id.bank_account_layout);
        accountEditText = (EditText) findViewById(R.id.edit_txt_account_name);
        branchEditText = (EditText) findViewById(R.id.edit_txt_branch_name);
        taxLayout = (LinearLayout) findViewById(R.id.tax_layout);
    }

    private void initCategories() {

        if (categories == null) {

            SQLiteDatabase db = (new Database(this)).getDataBase();

            Cursor cursor = db.rawQuery(
                    "SELECT CATEGORY_ID, CATEGORY_NAME"
                            + " FROM PRODUCT_CATEGORY"
                            + " GROUP BY CATEGORY_ID, CATEGORY_NAME", null);

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

    private void setPromotionProductListView() {
        int itemLength = promotionArrayList.size() * 100;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, itemLength);
        params.setMargins(20, 0, 0, 20);
        promotionPlanItemListView.setLayoutParams(params);

        promotionProductCustomAdapter = new PromotionProductCustomAdapter(this);
        promotionPlanItemListView.setAdapter(promotionProductCustomAdapter);
        promotionProductCustomAdapter.notifyDataSetChanged();
    }

    private void catchEvents() {
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        payAmountEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int arg1, int arg2, int arg3) {

                /*if (charSequence.toString().length() > 0) {

                    String convertedString = charSequence.toString();
                    convertedString = Utils.formatAmount(Double.parseDouble(charSequence.toString().replace(",", "")));
                    if (!payAmountEditText.getText().toString().equals(convertedString)
                            && convertedString.length() > 0) {

                        payAmountEditText.setText(convertedString);
                        payAmountEditText.setSelection(payAmountEditText.getText().length());
                    }
                }*/
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                String tempPayAmount = editable.toString().replace(",", "");
                //Log.i("tempPayAmount", tempPayAmount);
                String tempNetAmount = netAmountTextView.getText().toString().replace(",", "");
                //Log.i("tempNetAmount", tempNetAmount);

                if (tempPayAmount.length() > 0 && tempNetAmount.length() > 0 && !tempPayAmount.equalsIgnoreCase("-")) {

                    if (Double.parseDouble(tempPayAmount) >= Double.parseDouble(tempNetAmount)) {

                        refundTextView.setText(Utils.formatAmount(Double.parseDouble(tempPayAmount) - Double.parseDouble(tempNetAmount)));
                    } else {

                        refundTextView.setText("0");
                    }
                }
            }
        });

        confirmAndPrintImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.askConfirmationDialog("Save", "Do you want to confirm?", "", SaleCancelCheckout2Activity.this);
            }
        });


        bankRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    layoutBranch.setVisibility(View.VISIBLE);
                    layoutBankAcc.setVisibility(View.VISIBLE);
                }
            }
        });

        cashRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    layoutBranch.setVisibility(View.GONE);
                    layoutBankAcc.setVisibility(View.GONE);
                }
            }
        });

    }

    /**
     * Sale or sale exchange
     */
    private void saleOrExchange() {
        if (check.equalsIgnoreCase("yes")) {

        } else {

            if (isSameCustomer(customer.getId())) {
                updateDepartureTimeForSalemanRoute(customer.getId());
                updateSaleVisitRecord(customer.getId());
            }

            Intent intent = new Intent(SaleCancelCheckout2Activity.this, PrintInvoiceActivity.class);
            intent.putExtra(SaleCheckoutActivity.INVOICE, SaleCancelCheckout2Activity.this.invoice);
            intent.putExtra(SaleCheckoutActivity.SOLD_PROUDCT_LIST_KEY
                    , SaleCancelCheckout2Activity.this.soldProductList);
            intent.putExtra(SaleCheckoutActivity.INVOICE_PRESENT
                    , SaleCancelCheckout2Activity.this.promotionArrayList);
            intent.putExtra("PRINT_MODE"
                    , "S");
            startActivity(intent);

            //Utils.backToCustomer(SaleCheckoutActivity.this);
        }
    }

    private void updateDepartureTimeForSalemanRoute(int customerId) {
        database.beginTransaction();
        database.execSQL("update " + DatabaseContract.temp_for_saleman_route.TABLE + " set " + DatabaseContract.temp_for_saleman_route.DEPARTURE_TIME + " = '" + Utils.getCurrentDate(true) + "'" +
                " where " + DatabaseContract.temp_for_saleman_route.CUSTOMER_ID + " = " + customerId + "");
        database.setTransactionSuccessful();
        database.endTransaction();
    }


    /**
     * Get customer payment method.
     *
     * @return CA : cash, B : bank, CR : Credit
     */
    private String getPaymentMethod() {
        int selectedRadio = bankOrCashRadioGroup.getCheckedRadioButtonId();
        String paymentMethod = "";
        if (selectedRadio == R.id.activity_sale_checkout_radio_bank) {
            paymentMethod = "B";

        } else if (selectedRadio == R.id.activity_sale_checkout_radio_cash) {
            paymentMethod = "CA";
        }
        return paymentMethod;
    }

    /**
     * Update SALE VISIT RECORD of related customer id
     *
     * @param customerId customer number
     */
    private void updateSaleVisitRecord(int customerId) {
        ContentValues cv = new ContentValues();
        String where = DatabaseContract.SALE_VISIT_RECORD.CUSTOMER_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(customerId)};
        cv.put(DatabaseContract.SALE_VISIT_RECORD.VISIT_FLG, 1);
        cv.put(DatabaseContract.SALE_VISIT_RECORD.SALE_FLG, 1);
        database.update(DatabaseContract.SALE_VISIT_RECORD.TABLE_UPLOAD, cv, where, whereArgs);
    }

    /**
     * Check it is the same location for customer.
     *
     * @param customerId customer row number
     * @return true: if that location is correct; otherwise false.
     */
    private boolean isSameCustomer(int customerId) {
        Cursor locationCursor = database.rawQuery("SELECT LATITUDE, LONGITUDE FROM CUSTOMER WHERE ID = " + customerId, null);
        String latiString = "", longiString = "";
        Double latitude = 0.0, longitude = 0.0, latiDouble = 0.0, longDouble = 0.0;

        while (locationCursor.moveToNext()) {
            latiString = locationCursor.getString(locationCursor.getColumnIndex("LATITUDE"));
            longiString = locationCursor.getString(locationCursor.getColumnIndex("LONGITUDE"));
        }

        if (latiString != null && longiString != null && !latiString.equals("") && !longiString.equals("") && !latiString.equals("0") && !longiString.equals("0")) {
            latiDouble = Double.parseDouble(latiString.substring(0, 7));
            longDouble = Double.parseDouble(longiString.substring(0, 7));
        }

        GPSTracker gpsTracker = new GPSTracker(SaleCancelCheckout2Activity.this);
        if (gpsTracker.canGetLocation()) {
            String lat = String.valueOf(gpsTracker.getLatitude());
            String lon = String.valueOf(gpsTracker.getLongitude());

            if (!lat.equals(null) && !lon.equals(null) && lat.length() > 6 && lon.length() > 6) {
                latitude = Double.parseDouble(lat.substring(0, 7));
                longitude = Double.parseDouble(lon.substring(0, 7));
            }
        } else {
            gpsTracker.showSettingsAlert();
        }

        boolean flag1 = false, flag2 = false;
        if (latiDouble != null && longDouble != null && latitude != null && longitude != null) {
            Double lati1 = latiDouble - 0.002;
            Double lati2 = latiDouble + 0.002;

            if (latitude >= lati1 && latitude <= lati2) {
                flag1 = true;
            } else if (latitude.equals(latiDouble)) {
                flag1 = true;
            }

            Double longi1 = longDouble - 0.002;
            Double longi2 = longDouble + 0.002;

            if (longitude >= longi1 && longitude <= longi2) {
                flag2 = true;
            } else if (longitude.equals(longDouble)) {
                flag2 = true;
            }

            if (flag1 || flag2) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check for fully paid
     *
     * @return true : fully paid; false : not fully paid
     */
    private boolean isFullyPaid() {
        double pay_amount = 0.0, net_amount = 0.0;

        if (!payAmountEditText.getText().toString().equals("")) {
            pay_amount = Double.parseDouble(payAmountEditText.getText().toString());
        }

        if (!netAmountTextView.getText().toString().equals("")) {
            net_amount = Double.parseDouble(netAmountTextView.getText().toString().replace(",", ""));
        }

        if (pay_amount == 0.0 || pay_amount < net_amount) {
            return false;
        } else {
            return true;
        }
    }

    void getTaxAmount() {
        Cursor cursorTax = database.rawQuery("SELECT TaxType, Tax FROM COMPANYINFORMATION", null);
        while (cursorTax.moveToNext()) {
            taxType = cursorTax.getString(cursorTax.getColumnIndex("TaxType"));
            taxPercent = cursorTax.getDouble(cursorTax.getColumnIndex("Tax"));
        }
    }

    double calculateTax(double amount) {
        double taxAmt = 0.0;
        if (taxPercent != 0.0) {
            taxAmt = (amount * taxPercent) / 100;
        }

        return taxAmt;
    }

    /**
     * Save data to database
     */
    private void saveDatas(String cashOrLoanOrBank) {

        String customerId = String.valueOf(customer.getId());
        String saleDate = Utils.getCurrentDate(true);

        String invoiceId = invoiceIdTextView.getText().toString();

        /*double totalDiscountAmount = 0.0;
        if (discountTextView.getText().toString() != null && !discountTextView.getText().toString().equals("")) {
            totalDiscountAmount = Double.parseDouble(discountTextView.getText().toString().replace(",", ""));
        }

        double totalAmount = 0.0;
        if (netAmountTextView.getText().toString() != null && !netAmountTextView.getText().toString().equals("")) {
            totalAmount = Double.parseDouble(netAmountTextView.getText().toString().replace(",", ""))
                    + totalDiscountAmount;
        }*/

        double payAmount = 0.0;
        if (payAmountEditText.getText().toString() != null && !payAmountEditText.getText().toString().equals("")) {
            payAmount = Double.parseDouble(payAmountEditText.getText().toString().replace(",", ""));
        }

        double refundAmount = 0.0;
        if (refundTextView.getText().toString() != null && !refundTextView.getText().toString().equals("")) {
            refundAmount = Double.parseDouble(refundTextView.getText().toString().replace(",", ""));
        }

        String receiptPersonName = receiptPersonEditText.getText().toString();
        String salePersonId = "";

        try {
            salePersonId = LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, "");
        } catch (NullPointerException e) {
            e.printStackTrace();
            Utils.backToLogin(this);
        }

        String invoiceTime = Utils.getCurrentDate(true);
        Log.i("invoiceTime", invoiceTime);

        String dueDate = null;

        if (cashOrLoanOrBank.equals("CR")) {
            dueDate = saleDate;
        }

        String deviceId = Utils.getDeviceId(this);

        int totolQtyForInvoice = 0;

        ArrayList<InvoiceDetail> invoiceDetailList = new ArrayList<>();

        database.beginTransaction();

        database.execSQL("DELETE FROM INVOICE WHERE INVOICE_ID ='" + invoiceId + "'");
        database.execSQL("DELETE FROM INVOICE_PRODUCT WHERE INVOICE_PRODUCT_ID ='" + invoiceId + "'");
        database.execSQL("DELETE FROM INVOICE_PRESENT WHERE tsale_id ='" + invoiceId + "'");

        for(SoldProduct soldProduct : prev_product){
            database.execSQL("UPDATE PRODUCT SET REMAINING_QTY = REMAINING_QTY + " + soldProduct.getQuantity()
                    + ", SOLD_QTY = SOLD_QTY - " + soldProduct.getQuantity() + " WHERE ID = \'" + soldProduct.getProduct().getId() + "\'");
        }

        for(Promotion promotion : prev_promotion){
            database.execSQL("UPDATE PRODUCT SET PRESENT_QTY = PRESENT_QTY - " + promotion.getPromotionQty() + " WHERE ID = \'" + promotion.getPromotionProductId() + "\'");
            database.execSQL("UPDATE PRODUCT SET REMAINING_QTY = REMAINING_QTY + " + promotion.getPromotionQty() + " WHERE ID = '" + promotion.getPromotionProductId() + "'");

        }

        for (SoldProduct soldProduct : soldProductList) {
            InvoiceDetail invoiceDetail = new InvoiceDetail();
            invoiceDetail.setTsaleId(invoiceId);
            invoiceDetail.setProductId(soldProduct.getProduct().getStockId());
            invoiceDetail.setQty(soldProduct.getQuantity());
            invoiceDetail.setDiscountAmt(soldProduct.getDiscountAmount());
            invoiceDetail.setAmt(soldProduct.getNetAmount(this));
            invoiceDetail.setDiscountPercent(soldProduct.getDiscountPercent());
            invoiceDetail.setS_price(soldProduct.getProduct().getPrice());
            invoiceDetail.setP_price(soldProduct.getProduct().getPurchasePrice());
            invoiceDetail.setPromotionPrice(soldProduct.getPromotionPrice());

            if (soldProduct.getPromotionPlanId() != null && !soldProduct.getPromotionPlanId().equals("")) {
                invoiceDetail.setPromotion_plan_id(Integer.parseInt(soldProduct.getPromotionPlanId()));
            }

            if (soldProduct.getExclude() != null && !soldProduct.getExclude().equals("")) {
                invoiceDetail.setExclude(soldProduct.getExclude());
            }

            invoiceDetailList.add(invoiceDetail);

            //invoiceDetail.setTsaleId(invoiceId);

            ContentValues cvInvoiceProduct = new ContentValues();
            cvInvoiceProduct.put("INVOICE_PRODUCT_ID", invoiceId);
            cvInvoiceProduct.put("PRODUCT_ID", soldProduct.getProduct().getStockId());
            cvInvoiceProduct.put("SALE_QUANTITY", soldProduct.getQuantity());
            cvInvoiceProduct.put("DISCOUNT_AMOUNT", soldProduct.getDiscountAmount() + "");
            cvInvoiceProduct.put("TOTAL_AMOUNT", soldProduct.getNetAmount(this));
            cvInvoiceProduct.put("DISCOUNT_PERCENT", soldProduct.getDiscountPercent());
            cvInvoiceProduct.put("S_PRICE", soldProduct.getProduct().getPrice());
            cvInvoiceProduct.put("P_PRICE", soldProduct.getProduct().getPurchasePrice());

            double promoPrice = soldProduct.getPromotionPrice();
            if (promoPrice == 0.0) {
                promoPrice = soldProduct.getProduct().getPrice();
            }

            cvInvoiceProduct.put("PROMOTION_PRICE", promoPrice);
            cvInvoiceProduct.put("PROMOTION_PLAN_ID", soldProduct.getPromotionPlanId());
            cvInvoiceProduct.put("EXCLUDE", soldProduct.getExclude());

            totolQtyForInvoice += soldProduct.getQuantity();

            database.insert("INVOICE_PRODUCT", null, cvInvoiceProduct);

            database.execSQL("UPDATE PRODUCT SET REMAINING_QTY = REMAINING_QTY - " + soldProduct.getQuantity()
                    + ", SOLD_QTY = SOLD_QTY + " + soldProduct.getQuantity() + " WHERE PRODUCT_ID = \'" + soldProduct.getProduct().getId() + "\'");

            if (soldProduct.getFocQuantity() > 0) {
                Promotion promotion = new Promotion();
                promotion.setPromotionPlanId(null);
                promotion.setPromotionProductId(soldProduct.getProduct().getStockId() + "");
                promotion.setPromotionProductName(soldProduct.getProduct().getName());
                promotion.setPromotionQty(soldProduct.getFocQuantity());
                promotion.setPrice(0.0);
                promotion.setCurrencyId(1);
                promotionArrayList.add(promotion);
            }
        }

        for (Promotion promotion : promotionArrayList) {
            database.execSQL("UPDATE PRODUCT SET PRESENT_QTY = PRESENT_QTY + " + promotion.getPromotionQty() + " WHERE ID = \'" + promotion.getPromotionProductId() + "\'");
            database.execSQL("UPDATE PRODUCT SET REMAINING_QTY = REMAINING_QTY - " + promotion.getPromotionQty() + " WHERE ID = '" + promotion.getPromotionProductId() + "'");
        }


        invoice.setId(invoiceId);
        invoice.setCustomerId(customerId);
        invoice.setDate(saleDate);
        invoice.setTotalAmt(totalAmount);
        invoice.setTotalQty(totolQtyForInvoice);
        invoice.setTotalDiscountAmt(totalDiscountAmount);
        invoice.setTotalPayAmt(payAmount);
        invoice.setTotalRefundAmt(refundAmount);
        invoice.setReceiptPerson(receiptPersonName);
        invoice.setSalepersonId(Integer.parseInt(salePersonId));
        invoice.setLocationCode(locationCode);
        invoice.setDeviceId(deviceId);
        invoice.setInvoiceTime(invoiceTime);
        invoice.setCurrencyId(1);
        invoice.setInvoiceStatus(cashOrLoanOrBank);
        invoice.setDiscountPercent(totalVolumeDiscountPercent);
        invoice.setRate(1);
        invoice.setTaxAmount(taxAmt);
        invoice.setDueDate(dueDate);

        if (branchEditText.getText().toString() != null && !branchEditText.getText().toString().equals("")) {
            invoice.setBankName(branchEditText.getText().toString());
        }

        if (accountEditText.getText().toString() != null && !accountEditText.getText().toString().equals("")) {
            invoice.setBankAccountNo(accountEditText.getText().toString());
        }

        invoice.setInvoiceDetail(invoiceDetailList);

        ContentValues cvInvoice = new ContentValues();
        cvInvoice.put("CUSTOMER_ID", customerId);
        cvInvoice.put("SALE_DATE", saleDate);
        cvInvoice.put("INVOICE_ID", invoiceId);
        cvInvoice.put("TOTAL_AMOUNT", totalAmount);
        cvInvoice.put("TOTAL_DISCOUNT_AMOUNT", totalVolumeDiscount);
        cvInvoice.put("PAY_AMOUNT", payAmount);
        cvInvoice.put("REFUND_AMOUNT", refundAmount);
        cvInvoice.put("RECEIPT_PERSON_NAME", receiptPersonName);
        cvInvoice.put("SALE_PERSON_ID", salePersonId);
        cvInvoice.put("DUE_DATE", dueDate);
        cvInvoice.put("CASH_OR_CREDIT", cashOrLoanOrBank);
        cvInvoice.put("LOCATION_CODE", locationCode);
        cvInvoice.put("DEVICE_ID", deviceId);
        cvInvoice.put("INVOICE_TIME", invoiceTime);
        cvInvoice.put("INVOICE_PRODUCT_ID", invoiceId);
        cvInvoice.put("TOTAL_QUANTITY", totolQtyForInvoice);
        cvInvoice.put("INVOICE_STATUS", cashOrLoanOrBank);
        cvInvoice.put("TOTAL_DISCOUNT_PERCENT", totalVolumeDiscountPercent);
        cvInvoice.put("RATE", 1);
        cvInvoice.put("TAX_AMOUNT", taxAmt);
        cvInvoice.put("BANK_NAME", invoice.getBankName());
        cvInvoice.put("BANK_ACCOUNT_NO", invoice.getBankAccountNo());
        cvInvoice.put("SALE_FLAG", 0);
        database.insert("INVOICE", null, cvInvoice);

        /*database.execSQL("INSERT INTO INVOICE VALUES (\""
                + customerId + "\", \""
                + saleDate + "\", \""
                + invoiceId + "\", \""
                + totalAmount + "\", \""
                + totalVolumeDiscount + "\", \""
                + payAmount + "\", \""
                + refundAmount + "\", \""
                + receiptPersonName + "\", \""
                + salePersonId + "\", \""
                + dueDate + "\", \""
                + cashOrLoanOrBank + "\", \""
                + locationCode + "\", \""
                + deviceId + "\", \""
                + invoiceTime + "\", "
                + null + ", "
                + null + ", "
                + null + ", "
                + null + ", \""
                + invoiceId + "\", "
                + totolQtyForInvoice + ", \""
                + cashOrLoanOrBank + "\", "
                + totalVolumeDiscountPercent + ", "
                + 1 + ", "
                + taxAmt + ", \""
                + invoice.getBankName() + "\", \""
                + invoice.getBankAccountNo() + "\""
                + ",0)");
*/
        for (Promotion promotion : promotionArrayList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("tsale_id", invoiceId);
            contentValues.put("stock_id", promotion.getPromotionProductId());
            contentValues.put("quantity", promotion.getPromotionQty());
            contentValues.put("pc_address", deviceId);
            contentValues.put("location_id", locationCode);
            contentValues.put("price", promotion.getPrice());
            contentValues.put("currency_id", promotion.getCurrencyId());
            contentValues.put("rate", 1);
            database.insert("INVOICE_PRESENT", null, contentValues);
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SaleCancelCheckout2Activity.this
                , SaleActivity.class);
        //intent.putExtra(SaleActivity.REMAINING_AMOUNT_KEY, this.remainingAmount);
        intent.putExtra(SaleActivity.CUSTOMER_INFO_KEY, this.customer);
        intent.putExtra(SaleActivity.SOLD_PROUDCT_LIST_KEY, this.soldProductList);

        intent.putExtra(SaleCheckoutActivity.PRESENT_PROUDCT_LIST_KEY, this.promotionArrayList);
        /*if (this.orderedInvoice != null) {
            intent.putExtra(SaleActivity.ORDERED_INVOICE_KEY, this.orderedInvoice.toString());
        }*/
        intent.putExtra("SaleExchange", check);
        startActivity(intent);
        finish();
    }

    @Override
    public void onActionClick(String type) {

        String cashOrBank = getPaymentMethod();

        if (receiptPersonEditText.getText().toString().equals("") || receiptPersonEditText.getText().toString().equals(null)) {
            receiptPersonEditText.setError("Please enter receipt person");
        } else {

            if (isFullyPaid()) {
                cashOrBank = "CA";
                saveDatas(cashOrBank);
                saleOrExchange();
            } else {
                if (cashOrBank.equals("B")) {
                    if (branchEditText.getText().toString().equals("") || branchEditText.getText().toString().equals(null)) {
                        branchEditText.setError("Please enter bank account");
                    } else if (accountEditText.getText().toString().equals("") || accountEditText.getText().toString().equals(null)) {
                        accountEditText.setError("Please enter bank name");
                    } else {
                        Utils.commonDialog("Insufficient Pay Amount!", SaleCancelCheckout2Activity.this, 1);
                        return;
                    }

                    saveDatas("CA");
                    saleOrExchange();
                } else {
                    saveDatas("CR");
                    saleOrExchange();
                }
            }
        }
    }

    private class SoldProductListRowAdapter extends ArrayAdapter<SoldProduct> {

        final Activity context;

        public SoldProductListRowAdapter(Activity context) {

            super(context, R.layout.list_row_sold_product_checkout, soldProductList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            SoldProduct soldProduct = soldProductList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_sold_product_checkout, null, true);

            final TextView nameTextView = (TextView) view.findViewById(R.id.name);
            final TextView umTextView = (TextView) view.findViewById(R.id.um);
            final TextView qtyTextView = (TextView) view.findViewById(R.id.qty);
            final TextView priceTextView = (TextView) view.findViewById(R.id.price);
            final TextView discountTextView = (TextView) view.findViewById(R.id.discount);
            final TextView totalAmountTextView = (TextView) view.findViewById(R.id.amount);

            nameTextView.setText(soldProduct.getProduct().getName());
            umTextView.setText(soldProduct.getProduct().getUmName());
            qtyTextView.setText(soldProduct.getQuantity() + "");

            if (adapterFlag) {
                Map<String, Double> amountAndPercentage = calculateVolumeDiscount(soldProductList);
                calculateInvoiceDiscount();
                Double itemDiscountAmt = amountAndPercentage.get("Amount");

                double totalItemDisAmt = 0.0;

                if (itemDiscountAmt != null) {
                    totalItemDisAmt = itemDiscountAmt;
                }

                displayFinalAmount(totalItemDisAmt);
                if (check.equalsIgnoreCase("yes")) {
                    Double salereturnAmount = getIntent().getDoubleExtra(Constant.KEY_SALE_RETURN_AMOUNT, 0.0);
                    Double netAmount = 0.0;
                    if (netAmountTextView.getText() != null && !netAmountTextView.getText().toString().equals("")) {
                        netAmount = Double.parseDouble(netAmountTextView.getText().toString().replace(",", ""));
                    }
                    Double payAmt = netAmount - salereturnAmount;
                    payAmountEditText.setText(payAmt + "");
                }
                adapterFlag = false;
            }

            if (soldProduct.getPromotionPrice() == 0.0) {
                priceTextView.setText(Utils.formatAmount(soldProduct.getProduct().getPrice()));
                discountTextView.setText(Utils.formatAmount(soldProduct.getProduct().getPrice()));
            } else {
                priceTextView.setText(Utils.formatAmount(soldProduct.getProduct().getPrice()));
                discountTextView.setText(Utils.formatAmount(soldProduct.getPromotionPrice()));
            }

            totalAmountTextView.setText(Utils.formatAmount(soldProduct.getTotalAmount()));
            return view;
        }
    }

    void displayFinalAmount(Double itemDisAmt) {
        getTaxAmount();
        taxAmt = calculateTax(totalAmount);

        if (itemDisAmt == null) {
            itemDisAmt = 0.0;
        }

        totalAmountTextView.setText(Utils.formatAmount(totalAmount));
        double netAmount = 0.0;
        totalDiscountAmount = totalVolumeDiscount + itemDisAmt;

        totalVolumeDiscountPercent = (totalDiscountAmount * 100) / totalAmount;

        if (taxType.equalsIgnoreCase("E")) {
            taxLabelTextView.setText("Tax (Exclude) : ");
            netAmount = totalAmount - totalDiscountAmount + taxAmt;
        } else {
            taxLabelTextView.setText("Tax (Include) : ");
            netAmount = totalAmount - totalDiscountAmount;
        }
        netAmountTextView.setText(Utils.formatAmount(netAmount));
        discountTextView.setText(Utils.formatAmount(totalDiscountAmount) + " (" + new DecimalFormat("#0.00").format(totalVolumeDiscountPercent) + "%)");
        taxTextView.setText(Utils.formatAmount(taxAmt) + " (" + new DecimalFormat("#0.00").format(taxPercent) + "%)");
    }

    private class PromotionProductCustomAdapter extends ArrayAdapter<Promotion> {

        final Activity context;

        public PromotionProductCustomAdapter(Activity context) {
            super(context, R.layout.list_row_promotion, promotionArrayList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Promotion promotion = promotionArrayList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_promotion, null, true);

            final TextView nameTextView = (TextView) view.findViewById(R.id.productName);
            final TextView qtyTextView = (TextView) view.findViewById(R.id.qty);
            final TextView priceTextView = (TextView) view.findViewById(R.id.price);

            if (!promotion.getPromotionProductName().equals("") || promotion.getPromotionProductName() != null) {
                nameTextView.setText(promotion.getPromotionProductName());
            } else {
                nameTextView.setVisibility(View.GONE);
            }
            if (promotion.getPromotionQty() != 0) {
                qtyTextView.setText(promotion.getPromotionQty() + "");
            } else {
                qtyTextView.setVisibility(View.GONE);
            }
            if (promotion.getPromotionPrice() != null && promotion.getPromotionPrice() != 0.0) {
                priceTextView.setText(promotion.getPromotionPrice() + "");
            } else {
                priceTextView.setVisibility(View.GONE);
            }

            return view;
        }
    }
}