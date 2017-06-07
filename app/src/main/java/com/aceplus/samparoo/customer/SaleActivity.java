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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Category;
import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.model.Product;
import com.aceplus.samparoo.model.Promotion;
import com.aceplus.samparoo.model.SoldProduct;
import com.aceplus.samparoo.utils.Constant;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haker on 2/3/17.
 */
public class SaleActivity extends AppCompatActivity {

    // For pre order
    public static final String IS_PRE_ORDER = "is-pre-order";
    // For delivery
    public static final String IS_DELIVERY = "is-delivery";
    public static final String REMAINING_AMOUNT_KEY = "remaining-amount-key";

    public static final String USER_INFO_KEY = "user-info-key";
    public static final String CUSTOMER_INFO_KEY = "customer-info-key";
    public static final String SOLD_PROUDCT_LIST_KEY = "sold-product-list-key";
    public static final String ORDERED_INVOICE_KEY = "ordered_invoice_key";

    public static final String SALE_RETURN_INVOICEID_KEY = "sale_return_invoiceid_key";


    private boolean isPreOrder;

    private boolean isDelivery;
    private double remainingAmount;

    private JSONObject salemanInfo;
    private Customer customer;
    private ArrayList<SoldProduct> soldProductList = new ArrayList<>();

    //for present product
    private ArrayList<String> productsForSearch = new ArrayList<String>();

    Product[] products;

    private JSONObject orderedInvoice;

    private TextView titleTextView;

    private AutoCompleteTextView searchProductTextView;
    private Button previousCategoryButton, nextCategoryButton;
    private TextView categoryTextView;
    private ListView productsInGivenCategoryListView;

    private TextView saleDateTextView;

    private ListView soldProductListView;

    private ImageView cancelImg, checkoutImg;

    private Category[] categories;
    private int currentCategoryIndex;

    private SoldProductListRowAdapter soldProductListRowAdapter;

    SQLiteDatabase sqLiteDatabase;

    String check;

    ListView promotionPlanGiftListView;
    ArrayList<Promotion> promotionArrayList = new ArrayList<>();
    PromotionProductCustomAdapter promotionProductCustomAdapter;

    double promotionPrice = 0.0;
    double totalPromotionPrice = 0.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        sqLiteDatabase = new Database(this).getDataBase();

        titleTextView = (TextView) findViewById(R.id.title);

        Intent intent = this.getIntent();

        if (intent != null) {

            check = intent.getExtras().getString("SaleExchange");
            if (check.equalsIgnoreCase("yes")) {

                //titleTextView.setText("SALE EXCHANGE");
            }

        }

        customer = (Customer) getIntent().getSerializableExtra(CUSTOMER_INFO_KEY);
        if (getIntent().getSerializableExtra(SOLD_PROUDCT_LIST_KEY) != null) {

            soldProductList = (ArrayList<SoldProduct>) getIntent().getSerializableExtra(SOLD_PROUDCT_LIST_KEY);
        }

        if (getIntent().getSerializableExtra(SaleCheckoutActivity.PRESENT_PROUDCT_LIST_KEY) != null) {

            promotionArrayList = (ArrayList <Promotion>) getIntent().getSerializableExtra(SaleCheckoutActivity.PRESENT_PROUDCT_LIST_KEY);
        }

        //Toast.makeText(this, customer.getCustomerName(), Toast.LENGTH_SHORT).show();
        // Hide keyboard on startup.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        searchProductTextView = (AutoCompleteTextView) findViewById(R.id.searchAutoCompleteTextView);
        previousCategoryButton = (Button) findViewById(R.id.previusCategoryButton);
        categoryTextView = (TextView) findViewById(R.id.categoryTextView);
        nextCategoryButton = (Button) findViewById(R.id.nextCategoryButton);
        productsInGivenCategoryListView = (ListView) findViewById(R.id.productsListView);
        saleDateTextView = (TextView) findViewById(R.id.saleDateTextView);
        soldProductListView = (ListView) findViewById(R.id.soldProductList);
        promotionPlanGiftListView = (ListView) findViewById(R.id.promotion_plan_gift_listview);

        cancelImg = (ImageView) findViewById(R.id.cancel_img);
        checkoutImg = (ImageView) findViewById(R.id.checkout_img);

        findViewById(R.id.searchAndSelectProductsLayout).setVisibility(
                this.isDelivery ? View.GONE : View.VISIBLE);
        findViewById(R.id.tableHeaderOrderedQty).setVisibility(
                this.isDelivery ? View.VISIBLE : View.GONE);
        findViewById(R.id.tableHeaderDiscount).setVisibility(
                this.isPreOrder ? View.GONE : View.VISIBLE);

        searchProductTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                sellProduct(null, parent.getItemAtPosition(position).toString());
                searchProductTextView.setText("");
            }
        });

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

                //sellProduct(categoryTextView.getText().toString(), parent.getItemAtPosition(position).toString());

                Product tempProduct = null;

                for (Product product : products) {

                    if (product.getName().equals(parent.getItemAtPosition(position).toString())) {

                        tempProduct = product;
                    }
                }

                if (tempProduct != null) {
                    boolean sameProduct = false;
                    for (SoldProduct tempSoldProduct : soldProductList) {
                        if (tempSoldProduct.getProduct().equals(tempProduct)) {
                            sameProduct = true;
                            break;
                        }
                    }

                    if (!sameProduct) {
                        soldProductList.add(new SoldProduct(tempProduct, false));
                        soldProductListRowAdapter.notifyDataSetChanged();
                    } else {
                        Utils.commonDialog("Already have this product", SaleActivity.this);
                    }
                }
            }
        });

        saleDateTextView.setText(Utils.getCurrentDate(false));

        soldProductListRowAdapter = new SoldProductListRowAdapter(this, R.layout.list_row_sold_product_with_custom_discount);

        soldProductListView.setAdapter(soldProductListRowAdapter);
        soldProductListRowAdapter.notifyDataSetChanged();
        soldProductListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {

                new AlertDialog.Builder(SaleActivity.this)
                        .setTitle("Delete sold product")
                        .setMessage("Are you sure you want to delete "
                                + soldProductList.get(position).getProduct().getName() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                soldProductList.remove(position);
                                soldProductListRowAdapter.notifyDataSetChanged();
                                if(soldProductList.size() != 0) {
                                    calculatePromotinPriceAndGift(soldProductList.get(soldProductList.size()-1));
                                } else {
                                    promotionArrayList.clear();
                                    updatePromotionProductList();
                                }
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });

        updatePromotionProductList();

        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaleActivity.this.onBackPressed();
            }
        });

        checkoutImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soldProductList.size() == 0) {

                    new AlertDialog.Builder(SaleActivity.this)
                            .setTitle("Alert")
                            .setMessage("You must specify at least one product.")
                            .setPositiveButton("OK", null)
                            .show();

                    return;
                }

                for (SoldProduct soldProduct : soldProductList) {

                    if (soldProduct.getQuantity() == 0) {

                        new AlertDialog.Builder(SaleActivity.this)
                                .setTitle("Alert")
                                .setMessage("Quantity must not be zero.")
                                .setPositiveButton("OK", null)
                                .show();

                        return;
                    }
                }

                Intent intent = new Intent(SaleActivity.this
                        , SaleCheckoutActivity.class);
                intent.putExtra(SaleCheckoutActivity.REMAINING_AMOUNT_KEY
                        , SaleActivity.this.remainingAmount);/*
                intent.putExtra(SaleCheckoutActivity.USER_INFO_KEY
                        , SaleActivity.this.salemanInfo.toString());*/
                intent.putExtra(SaleCheckoutActivity.CUSTOMER_INFO_KEY
                        , SaleActivity.this.customer);
                intent.putExtra(SaleCheckoutActivity.SOLD_PROUDCT_LIST_KEY
                        , SaleActivity.this.soldProductList);
                intent.putExtra(SaleCheckoutActivity.PRESENT_PROUDCT_LIST_KEY
                        , SaleActivity.this.promotionArrayList);
                if (SaleActivity.this.orderedInvoice != null) {


                    intent.putExtra(SaleCheckoutActivity.ORDERED_INVOICE_KEY
                            , SaleActivity.this.orderedInvoice.toString());
                }

                if (check.equalsIgnoreCase("yes")) {

                    intent.putExtra("SaleExchange", "yes");
                    intent.putExtra(SALE_RETURN_INVOICEID_KEY, getIntent().getStringExtra(SALE_RETURN_INVOICEID_KEY));
                    intent.putExtra(Constant.KEY_SALE_RETURN_AMOUNT, getIntent().getDoubleExtra(Constant.KEY_SALE_RETURN_AMOUNT, 0.0));


                } else {

                    intent.putExtra("SaleExchange", "no");

                }
                startActivity(intent);
                finish();
            }
        });

        //initCategories();
        products = getProducts("");

        //productsForSearch.clear();
        Log.i("products length", products.length + "");

        if (products.length == 0) {
            Utils.commonDialog("No issued product", this);
            return;
        }

        for (int i = 0; i < products.length; i++) {
            productsForSearch.add(products[i].getName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.custom_simple_list_item_1, android.R.id.text1, productsForSearch);
        productsInGivenCategoryListView.setAdapter(arrayAdapter);

        searchProductTextView.setAdapter(new ArrayAdapter<String>(SaleActivity.this, android.R.layout.simple_list_item_1, productsForSearch));
        searchProductTextView.setThreshold(1);
    }

    private Product[] getProducts(String categoryId) {

        SQLiteDatabase db = (new Database(this)).getDataBase();

        /*Cursor cursor = db.rawQuery(
                "SELECT * FROM PRODUCT WHERE CATEGORY_ID = '" + categoryId + "'", null);*/

        Cursor cursor = db.rawQuery(
                "SELECT * FROM PRODUCT WHERE TOTAL_QTY > 0", null);

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
            tempProduct.setUm(cursor.getString(cursor.getColumnIndex("UM")));

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

        /*if (categories != null) {*/

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
            if (!soldProductList.contains(tempProduct)) {
                soldProductList.add(new SoldProduct(tempProduct, false));
                soldProductListRowAdapter.notifyDataSetChanged();
            } else {
                Utils.callDialog("Already have this product", SaleActivity.this);
            }
        }

        //}
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
        public View getView(final int position, View convertView, ViewGroup parent) {

            final SoldProduct soldProduct = soldProductList.get(position);
            LayoutInflater layoutInflater = context.getLayoutInflater();
            final View view = layoutInflater.inflate(this.resource, null, true);

            final TextView nameTextView = (TextView) view.findViewById(R.id.name);
            final TextView umTextView = (TextView) view.findViewById(R.id.um);
            final Button qtyButton = (Button) view.findViewById(R.id.qty);
            final TextView priceTextView = (TextView) view.findViewById(R.id.price);
            final TextView totalAmountTextView = (TextView) view.findViewById(R.id.amount);

            qtyButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View dialogView = layoutInflater.inflate(R.layout.dialog_box_sale_quantity, null);

                    final TextView remainingQtyTextView = (TextView) dialogView.findViewById(R.id.availableQuantity);
                    final EditText quantityEditText = (EditText) dialogView.findViewById(R.id.quantity);
                    final TextView messageTextView = (TextView) dialogView.findViewById(R.id.message);

                    final AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setView(dialogView)
                            .setTitle("Sale Quantity")
                            .setPositiveButton("Confirm", null)
                            .setNegativeButton("Cancel", null)
                            .create();
                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

                        @Override
                        public void onShow(DialogInterface arg0) {

                            if (SaleActivity.this.isPreOrder) {

                                dialogView.findViewById(R.id.availableQuantityLayout).setVisibility(View.GONE);
                            } else {

                                remainingQtyTextView.setText(soldProduct.getProduct().getRemainingQty() + "");
                            }

                            Button confirmButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                            confirmButton.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View arg0) {

                                    if (quantityEditText.getText().toString().length() == 0) {

                                        messageTextView.setText("You must specify quantity.");
                                        return;
                                    }

                                    int quantity = Integer.parseInt(quantityEditText.getText().toString());

                                    if (SaleActivity.this.isDelivery
                                            && quantity > soldProduct.getOrderedQuantity()) {

                                        messageTextView.setText("Quantity must be no more than ordered quantity.");
                                        quantityEditText.selectAll();
                                        return;
                                    }

                                    soldProduct.setQuantity(quantity);
                                    soldProductListRowAdapter.notifyDataSetChanged();

                                    //promotionArrayList.clear();
                                    promotionPrice = calculatePromotinPriceAndGift(soldProduct);
                                    totalPromotionPrice += promotionPrice;

                                    soldProduct.setPromotionPrice(promotionPrice);


                                    alertDialog.dismiss();


                                }
                            });
                        }
                    });

                    alertDialog.show();
                }
            });

            TextView discountButtonOrTextView = (TextView) view.findViewById(R.id.promotionPrice);
            discountButtonOrTextView.setText(Utils.formatAmount(soldProduct.getPromotionPrice()));

            nameTextView.setText(soldProduct.getProduct().getName());

            String um = "";
            Log.i("um_id", soldProduct.getProduct().getUm());
            Cursor cursor = sqLiteDatabase.rawQuery("select * from UM where ID=" + soldProduct.getProduct().getUm() + "", null);
            while (cursor.moveToNext()) {
                um = cursor.getString(cursor.getColumnIndex(DatabaseContract.UM.code));
            }
            Log.i("um", um);
            umTextView.setText(um);

            if (this.resource == R.layout.list_row_sold_product_with_custom_discount) {

                TextView orderedQuantityTextView = (TextView) view.findViewById(R.id.orderedQuantity);
                orderedQuantityTextView.setVisibility(
                        SaleActivity.this.isDelivery ? View.VISIBLE : View.GONE);
                if (SaleActivity.this.isDelivery) {

                    orderedQuantityTextView.setText(soldProduct.getOrderedQuantity() + "");
                }
            }

            qtyButton.setText(soldProduct.getQuantity() + "");
            priceTextView.setText(Utils.formatAmount(soldProduct.getProduct().getPrice()));

            Double totalAmount = 0.0;
            if (promotionPrice == 0.0) {
                totalAmount = (soldProduct.getProduct().getPrice() * soldProduct.getQuantity());
                Log.i("totalAmount1", totalAmount + "");
            } else {
                totalAmount = promotionPrice * soldProduct.getQuantity();
                Log.i("totalAmount2", totalAmount + "");
            }

            totalAmountTextView.setText(Utils.formatAmount(totalAmount));
            soldProduct.setTotalAmt(totalAmount);
            double netAmount = 0.0;
            for (SoldProduct soldProduct1 : soldProductList) {
                //netAmount += soldProduct.getNetAmount(SaleActivity.this);
                Log.i("soldProduct1.getTotalAmount", soldProduct1.getTotalAmount() + "");
                netAmount += soldProduct1.getTotalAmount();
            }
            Log.i("netAmount", netAmount + "");

            ((TextView) context.findViewById(R.id.netAmountTextView)).setText(Utils.formatAmount(netAmount));

            return view;
        }

        @Override
        public void notifyDataSetChanged() {

            super.notifyDataSetChanged();

            Double netAmount = 0.0;
            for (SoldProduct soldProduct : soldProductList) {
                //netAmount += soldProduct.getNetAmount(SaleActivity.this);
                netAmount += soldProduct.getTotalAmount();
            }

            ((TextView) context.findViewById(R.id.netAmountTextView)).setText(Utils.formatAmount(netAmount));
        }
    }

    private double calculatePromotinPriceAndGift(SoldProduct soldProduct) {
        double promotion_price = 0.0;
        String promotionProductId = "";
        String promotionProductName = "";
        int promotionProductQty = 0;

        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DatabaseContract.PromotionDate.tb + " WHERE DATE(PROMOTION_DATE) = DATE('" + Utils.getCurrentDate(true) + "')", null);
        Log.i("cursor", cursor.getCount() + "");

        int buy_qty = soldProduct.getProduct().getSoldQty();
        String stock_id_old = soldProduct.getProduct().getId();

        String promotionPlanId = "";

        String stock_id_new = "";

        while (cursor.moveToNext()) {

            promotionPlanId = cursor.getString(cursor.getColumnIndex(DatabaseContract.PromotionDate.promotionPlanId));
            Log.i("promotionPlanId", promotionPlanId);
            Cursor cursorForStockId = sqLiteDatabase.rawQuery("select * from PRODUCT where PRODUCT_ID = '" + stock_id_old + "'", null);
            while (cursorForStockId.moveToNext()) {
                stock_id_new = cursorForStockId.getString(cursorForStockId.getColumnIndex("ID"));
                Log.i("stock_id_new", stock_id_new + "");
            }
            Cursor cursorForPromotionPrice = sqLiteDatabase.rawQuery("select * from " + DatabaseContract.PromotionPrice.tb + " where " + DatabaseContract.PromotionPrice.promotionPlanId + " = '" + promotionPlanId + "'" +
                    " and " + DatabaseContract.PromotionPrice.fromQuantity + " <= " + buy_qty + " and " + DatabaseContract.PromotionPrice.toQuantity + " >= " + buy_qty + " and " + DatabaseContract.PromotionPrice.stockId + " = '" + stock_id_new + "'", null);
            Log.i("PriceCount", cursorForPromotionPrice.getCount() + "");
            while (cursorForPromotionPrice.moveToNext()) {
                promotion_price = cursorForPromotionPrice.getDouble(cursorForPromotionPrice.getColumnIndex(DatabaseContract.PromotionPrice.promotionPrice));
            }
            Log.i("promotionPrice", promotion_price + "");

            if (promotion_price == 0.0) {
                Cursor cursorForPromotionGift = sqLiteDatabase.rawQuery("select * from " + DatabaseContract.PromotionGift.tb + " where " + DatabaseContract.PromotionGift.promotionPlanId + " = '" + promotionPlanId + "'", null);

                List<String> productToBuy = new ArrayList<>();
                Log.i("GiftCount", cursorForPromotionGift.getCount() + "");
                while (cursorForPromotionGift.moveToNext()) {
                    String promotionProductToBuy = cursorForPromotionGift.getString(cursorForPromotionGift.getColumnIndex(DatabaseContract.PromotionGift.stockId));
                    Log.i("promotionPlanIdForGift", promotionPlanId + "");
                    productToBuy.add(promotionProductToBuy);
                }

                int count = checkPromotionToBuyProduct(promotionPlanId);

                if (count == productToBuy.size()) {
                    boolean flag = false;
                    for (Promotion promotion : promotionArrayList) {
                        if (promotion.getPromotionPlanId().equals(promotionPlanId)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        addPromotionProduct(promotionPlanId);
                    }
                } else {
                    boolean flag = false;
                    for (Promotion promotion : promotionArrayList) {
                        if (promotion.getPromotionPlanId().equals(promotionPlanId)) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        removePromotionProduct(promotionPlanId);
                    }
                }

                updatePromotionProductList();
            }
        }
        return promotion_price;
    }

    int checkPromotionToBuyProduct(String promotionPlanId) {

        int count = 0;
        for(SoldProduct soldProduct : soldProductList) {
            Cursor cursorForPromotionGift = sqLiteDatabase.rawQuery("select * from " + DatabaseContract.PromotionGift.tb + " where " + DatabaseContract.PromotionGift.promotionPlanId + " = '" + promotionPlanId + "'" +
                    " and " + DatabaseContract.PromotionGift.fromQuantity + " <= " + soldProduct.getProduct().getSoldQty() + " and " + DatabaseContract.PromotionGift.toQuantity + " >= " + soldProduct.getProduct().getSoldQty() + " and " + DatabaseContract.PromotionGift.stockId + " = '" + soldProduct.getProduct().getStockId() + "'", null);

            while (cursorForPromotionGift.moveToNext()) {
                count++;
            }
        }


        return count;
    }

    void addPromotionProduct(String promotionPlanId) {
        String promotionProductId = "";
        int promotionProductQty = 0;
        String promotionProductName = "";
        Cursor cursorForPromotionGiftItem = sqLiteDatabase.rawQuery("select * from " + DatabaseContract.PromotionGiftItem.tb + " where " + DatabaseContract.PromotionGiftItem.promotionPlanId + " = '" + promotionPlanId + "'", null);
        while (cursorForPromotionGiftItem.moveToNext()) {
            promotionProductId = cursorForPromotionGiftItem.getString(cursorForPromotionGiftItem.getColumnIndex(DatabaseContract.PromotionGiftItem.stockId));
            Log.i("promotionProductId", promotionProductId + "");
            Cursor cursorForProductName = sqLiteDatabase.rawQuery("select * from PRODUCT WHERE ID = '" + promotionProductId + "'", null);
            while (cursorForProductName.moveToNext()) {
                promotionProductName = cursorForProductName.getString(cursorForProductName.getColumnIndex("PRODUCT_NAME"));
                Log.i("promotionProductName", promotionProductName + ">>not null");
            }

            promotionProductQty = cursorForPromotionGiftItem.getInt(cursorForPromotionGiftItem.getColumnIndex(DatabaseContract.PromotionGiftItem.quantity));

            Promotion promotion = new Promotion();
            promotion.setPromotionPlanId(promotionPlanId);
            promotion.setPromotionProductId(promotionProductId);
            promotion.setPromotionProductName(promotionProductName);
            promotion.setPromotionQty(promotionProductQty);

            promotionArrayList.add(promotion);
        }
    }

    void removePromotionProduct(String promotionPlanId) {
        String promotionProductId = "";
        int promotionProductQty = 0;
        String promotionProductName = "";
        Cursor cursorForPromotionGiftItem = sqLiteDatabase.rawQuery("select * from " + DatabaseContract.PromotionGiftItem.tb + " where " + DatabaseContract.PromotionGiftItem.promotionPlanId + " = '" + promotionPlanId + "'", null);
        while (cursorForPromotionGiftItem.moveToNext()) {
            promotionProductId = cursorForPromotionGiftItem.getString(cursorForPromotionGiftItem.getColumnIndex(DatabaseContract.PromotionGiftItem.stockId));
            Log.i("promotionProductId", promotionProductId + "");
            Cursor cursorForProductName = sqLiteDatabase.rawQuery("select * from PRODUCT WHERE ID = '" + promotionProductId + "'", null);
            while (cursorForProductName.moveToNext()) {
                promotionProductName = cursorForProductName.getString(cursorForProductName.getColumnIndex("PRODUCT_NAME"));
                Log.i("promotionProductName", promotionProductName + ">>not null");
            }

            promotionProductQty = cursorForPromotionGiftItem.getInt(cursorForPromotionGiftItem.getColumnIndex(DatabaseContract.PromotionGiftItem.quantity));

            Promotion promotion = new Promotion();
            promotion.setPromotionPlanId(promotionPlanId);
            promotion.setPromotionProductId(promotionProductId);
            promotion.setPromotionProductName(promotionProductName);
            promotion.setPromotionQty(promotionProductQty);

            for(int i = 0; i < promotionArrayList.size(); i++) {
                if(promotion.getPromotionPlanId().equals(promotionArrayList.get(i).getPromotionPlanId()) &&
                        promotion.getPromotionProductId().equals(promotionArrayList.get(i).getPromotionProductId()) &&
                        promotion.getPromotionProductName().equals(promotionArrayList.get(i).getPromotionProductName()) &&
                        promotion.getPromotionQty() == promotionArrayList.get(i).getPromotionQty()) {
                promotionArrayList.remove(i);
                }
            }
            promotionArrayList.remove(promotion);
        }
    }

    private void updatePromotionProductList() {
        ArrayList<Promotion> promotions = new ArrayList<>();
        promotions.addAll(promotionArrayList);

        int itemLength = promotions.size() * 100;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, itemLength);
        params.setMargins(20, 0, 0, 20);
        promotionPlanGiftListView.setLayoutParams(params);

        promotionProductCustomAdapter = new PromotionProductCustomAdapter(this, promotions);
        promotionPlanGiftListView.setAdapter(promotionProductCustomAdapter);
        promotionProductCustomAdapter.notifyDataSetChanged();
    }

    private void updateDepartureTimeForSalemanRoute(int customerId) {
        sqLiteDatabase.beginTransaction();
        sqLiteDatabase.execSQL("update " + DatabaseContract.temp_for_saleman_route.TABLE + " set " + DatabaseContract.temp_for_saleman_route.DEPARTURE_TIME + " = '"+Utils.getCurrentDate(true)+"'" +
                " where " + DatabaseContract.temp_for_saleman_route.CUSTOMER_ID + " = "+customerId+"");
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    @Override
    public void onBackPressed() {
        if(isSameCustomer(customer.getId())) {
            updateDepartureTimeForSalemanRoute(customer.getId());
        }
        Utils.backToCustomer(this);
    }

    /**
     * Check it is the same location for customer.
     *
     * @param customerId customer row number
     * @return true: if that location is correct; otherwise false.
     */
    private boolean isSameCustomer(int customerId) {
        Cursor locationCursor = sqLiteDatabase.rawQuery("SELECT LATITUDE, LONGITUDE FROM CUSTOMER WHERE ID = " + customerId, null);
        String latiString = "", longiString = "";
        Double latitude = 0.0, longitude = 0.0, latiDouble = 0.0, longDouble = 0.0;

        while(locationCursor.moveToNext()) {
            latiString = locationCursor.getString(locationCursor.getColumnIndex("LATITUDE"));
            longiString = locationCursor.getString(locationCursor.getColumnIndex("LONGITUDE"));
        }

        if(latiString != null && longiString != null && !latiString.equals("") && !longiString.equals("") && !latiString.equals("0") && !longiString.equals("0")) {
            latiDouble = Double.parseDouble(latiString.substring(0, 7));
            longDouble = Double.parseDouble(longiString.substring(0, 7));
        }

        GPSTracker gpsTracker = new GPSTracker(SaleActivity.this);
        if (gpsTracker.canGetLocation()) {
            String lat = String.valueOf(gpsTracker.getLatitude());
            String lon = String.valueOf(gpsTracker.getLongitude());

            if(!lat.equals(null) && !lon.equals(null) && lat.length() > 6 && lon.length() > 6){
                latitude = Double.parseDouble(lat.substring(0,7));
                longitude = Double.parseDouble(lon.substring(0,7));
            }
        } else {
            gpsTracker.showSettingsAlert();
        }

        boolean flag1 = false, flag2 = false;
        if(latiDouble != null && longDouble !=null && latitude != null && longitude != null) {

            if(latitude.equals(latiDouble - 0.001)) {
                flag1 = true;
            } else if (latitude.equals(latiDouble + 0.001)) {
                flag1 = true;
            } else if(latitude.equals(latiDouble)) {
                flag1 = true;
            }

            if(longitude.equals(longDouble - 0.001)) {
                flag2 = true;
            } else if (longitude.equals(longDouble + 0.001)) {
                flag2 = true;
            } else if(longitude.equals(longDouble)) {
                flag1 = true;
            }

            if(flag1 || flag2) {
                return true;
            }
        }

        return false;
    }
}
