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

import com.aceplus.samparoo.LoginActivity;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Category;
import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.model.Deliver;
import com.aceplus.samparoo.model.Product;
import com.aceplus.samparoo.model.Promotion;
import com.aceplus.samparoo.model.SoldProduct;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by htetaungkyaw on 2/5/17.
 */
public class SaleOrderActivity extends AppCompatActivity {

    @InjectView(R.id.title)
    TextView textViewTitle;

    @InjectView(R.id.tableHeaderQty)
    TextView textViewTableHeaderQty;

    @InjectView(R.id.tableHeaderUM)
    TextView txt_UM;

    @InjectView(R.id.tableHeaderDiscount)
    TextView txt_Discount;

    @InjectView(R.id.searchAndSelectProductsLayout)
    LinearLayout searchProductLayout;


    ArrayList<Promotion> promotionArrayList = new ArrayList<>();
    PromotionProductCustomAdapter promotionProductCustomAdapter;

    // For pre order
    public static final String IS_PRE_ORDER = "is-pre-order";
    // For delivery
    public static final String IS_DELIVERY = "is-delivery";
    public static final String REMAINING_AMOUNT_KEY = "remaining-amount-key";

    public static final String USER_INFO_KEY = "user-info-key";
    public static final String CUSTOMER_INFO_KEY = "customer-info-key";
    public static final String SOLD_PROUDCT_LIST_KEY = "sold-product-list-key";
    public static final String ORDERED_INVOICE_KEY = "ordered_invoice_key";

    private boolean isPreOrder;

    private boolean isDelivery;
    private double remainingAmount;

    private JSONObject salemanInfo;
    private Customer customer;
    private ArrayList<SoldProduct> soldProductList = new ArrayList<SoldProduct>();

    //for present product
    private ArrayList<String> productsForSearch = new ArrayList<String>();
    List<String> productToBuyForPromotion = new ArrayList<>();
    Product[] products;

    private Deliver orderedInvoice;

    private TextView titleTextView;

    private AutoCompleteTextView searchProductTextView;
    private Button previousCategoryButton, nextCategoryButton;
    private TextView categoryTextView;
    private ListView productsInGivenCategoryListView;

    private TextView saleDateTextView;

    private ListView soldProductListView, promotionPlanGiftListView;

    private ImageView cancelImg, checkoutImg;

    private Category[] categories;
    private int currentCategoryIndex;

    double totalPromotionPrice = 0.0;

    SQLiteDatabase sqLiteDatabase;

    private SoldProductListRowAdapter soldProductListRowAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);

        ButterKnife.inject(this);

        isPreOrder = getIntent().getBooleanExtra(SaleOrderActivity.IS_PRE_ORDER, false);
        isDelivery = getIntent().getBooleanExtra(SaleOrderActivity.IS_DELIVERY, false);
        customer = (Customer) getIntent().getSerializableExtra(CUSTOMER_INFO_KEY);

        sqLiteDatabase = new Database(this).getDataBase();

        if(isPreOrder){
            textViewTitle.setText(R.string.sale_order);
        } else if(isDelivery) {
            textViewTitle.setText(R.string.delivery);
        }

        if (getIntent().getSerializableExtra(SOLD_PROUDCT_LIST_KEY) != null) {

            soldProductList = (ArrayList<SoldProduct>) getIntent().getSerializableExtra(SOLD_PROUDCT_LIST_KEY);
        }

        if (getIntent().getSerializableExtra(SaleOrderActivity.ORDERED_INVOICE_KEY) != null) {

            orderedInvoice = (Deliver) getIntent().getSerializableExtra(SaleOrderActivity.ORDERED_INVOICE_KEY);
        }

        if (getIntent().getSerializableExtra(SaleOrderCheckoutActivity.PRESENT_PROUDCT_LIST_KEY) != null) {

            promotionArrayList = (ArrayList<Promotion>) getIntent().getSerializableExtra(SaleOrderCheckoutActivity.PRESENT_PROUDCT_LIST_KEY);
        }

        hideUnnecessaryViews();

        // Hide keyboard on startup.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        titleTextView = (TextView) findViewById(R.id.title);
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

        searchProductTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                sellProduct(null, parent.getItemAtPosition(position).toString());
                searchProductTextView.setText("");
            }
        });

        setPromotionProductListView();

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

                    soldProductList.add(new SoldProduct(tempProduct, false));
                    soldProductListRowAdapter.notifyDataSetChanged();
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

                new AlertDialog.Builder(SaleOrderActivity.this)
                        .setTitle("Delete sold product")
                        .setMessage("Are you sure you want to delete "
                                + soldProductList.get(position).getProduct().getName() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                if(promotionArrayList.size() != 0) {
                                    promotionArrayList.remove(position);
                                    promotionProductCustomAdapter.notifyDataSetChanged();
                                }

                                soldProductList.remove(position);
                                productToBuyForPromotion.remove(position);
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
                SaleOrderActivity.this.onBackPressed();
            }
        });

        checkoutImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soldProductList.size() == 0) {

                    new AlertDialog.Builder(SaleOrderActivity.this)
                            .setTitle("Alert")
                            .setMessage("You must specify at least one product.")
                            .setPositiveButton("OK", null)
                            .show();

                    return;
                }

                for (SoldProduct soldProduct : soldProductList) {

                    if (soldProduct.getQuantity() == 0) {

                        new AlertDialog.Builder(SaleOrderActivity.this)
                                .setTitle("Alert")
                                .setMessage("Quantity must not be zero.")
                                .setPositiveButton("OK", null)
                                .show();

                        return;
                    }
                }

                Intent intent = new Intent(SaleOrderActivity.this
                        , SaleOrderCheckoutActivity.class);
                intent.putExtra(SaleOrderCheckoutActivity.REMAINING_AMOUNT_KEY
                        , SaleOrderActivity.this.remainingAmount);
                intent.putExtra(SaleOrderCheckoutActivity.IS_PRE_ORDER
                        , SaleOrderActivity.this.isPreOrder);
                intent.putExtra(SaleOrderCheckoutActivity.IS_DELIVERY
                        , SaleOrderActivity.this.isDelivery);
                intent.putExtra(SaleOrderCheckoutActivity.CUSTOMER_INFO_KEY
                        , SaleOrderActivity.this.customer);
                intent.putExtra(SaleOrderCheckoutActivity.SOLD_PROUDCT_LIST_KEY
                        , SaleOrderActivity.this.soldProductList);
                intent.putExtra(SaleOrderCheckoutActivity.PRESENT_PROUDCT_LIST_KEY
                        , SaleOrderActivity.this.promotionArrayList);
                if (SaleOrderActivity.this.orderedInvoice != null) {
                    intent.putExtra(SaleOrderCheckoutActivity.ORDERED_INVOICE_KEY
                            , SaleOrderActivity.this.orderedInvoice);
                }
                startActivity(intent);
                finish();
            }
        });

        products = getProducts("");

        //productsForSearch.clear();
        Log.i("products length", products.length + "");

        for (int i = 0; i < products.length; i++) {
            productsForSearch.add(products[i].getName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.custom_simple_list_item_1, android.R.id.text1, productsForSearch);
        productsInGivenCategoryListView.setAdapter(arrayAdapter);

        searchProductTextView.setAdapter(new ArrayAdapter<String>(SaleOrderActivity.this, android.R.layout.simple_list_item_1, productsForSearch));
        searchProductTextView.setThreshold(1);

        /*initCategories();

        if (categories.length > 0) {

            categoryTextView.setText(categories[0].getName());
            currentCategoryIndex = 0;

            setProductListView(categories[0].getName());

            for (Category category : categories) {

                for (Product product : category.getProducts()) {

                    products.add(product.getName());
                }
            }
            searchProductTextView.setAdapter(new ArrayAdapter<String>(
                    this, android.R.layout.simple_list_item_1, products));
            searchProductTextView.setThreshold(1);
        } else {

            categoryTextView.setText("No product");
        }*/
    }

    private void hideUnnecessaryViews() {

        textViewTableHeaderQty.setVisibility(View.GONE);
        txt_UM.setVisibility(View.GONE);
        //txt_Discount.setVisibility(View.GONE);

        if(isDelivery) {
            searchProductLayout.setVisibility(View.GONE);
            textViewTableHeaderQty.setVisibility(View.VISIBLE);
        }
    }

    private void initCategories() {

        if (categories == null) {

            SQLiteDatabase db = (new Database(this)).getDataBase();

            Cursor cursor = db.rawQuery(
                    "SELECT CATEGORY_ID, CATEGORY_NAME"
                            + " FROM PRODUCT_CATEGORY"
//                            + " GROUP BY CATEGORY_ID, CATEGORY_NAME", null);
                    , null);

            Log.e("category size>>>", cursor.getCount() + "");

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
            productToBuyForPromotion.add(String.valueOf(soldProductList.get(position).getProduct().getStockId()));
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

                            if (SaleOrderActivity.this.isPreOrder) {

                                view.findViewById(R.id.availableQuantityLayout).setVisibility(View.GONE);
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

                                    if (SaleOrderActivity.this.isDelivery
                                            && quantity > soldProduct.getOrderedQuantity()) {

                                        messageTextView.setText("Quantity must be no more than ordered quantity.");
                                        quantityEditText.selectAll();
                                        return;
                                    }

                                    soldProduct.setQuantity(quantity);
                                    soldProductListRowAdapter.notifyDataSetChanged();

                                    //promotionArrayList.clear();
                                    double promotionPrice = calculatePromotinPriceAndGift(soldProduct);
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

            final TextView priceTextView = (TextView) view.findViewById(R.id.price);
            final TextView totalAmountTextView = (TextView) view.findViewById(R.id.amount);
            TextView discountButtonOrTextView = (TextView) view.findViewById(R.id.promotionPrice);
            discountButtonOrTextView.setText(Utils.formatAmount(soldProduct.getPromotionPrice()));

            nameTextView.setText(soldProduct.getProduct().getName());

            if (this.resource == R.layout.list_row_sold_product_with_custom_discount) {

                TextView orderedQuantityTextView = (TextView) view.findViewById(R.id.orderedQuantity);
                TextView um = (TextView) view.findViewById(R.id.um);
                //TextView discount = (TextView) view.findViewById(R.id.discount);

                orderedQuantityTextView.setVisibility(
                        SaleOrderActivity.this.isDelivery ? View.VISIBLE : View.GONE);

                um.setVisibility(View.GONE);
                //discount.setVisibility(View.GONE);

                if (SaleOrderActivity.this.isDelivery) {

                    orderedQuantityTextView.setText(soldProduct.getOrderedQuantity() + "");
                }
            }

            qtyButton.setText(soldProduct.getQuantity() + "");
            priceTextView.setText(Utils.formatAmount(soldProduct.getProduct().getPrice()));

            Double totalAmount = 0.0;
            if (soldProduct.getPromotionPrice() == 0.0) {
                totalAmount = (soldProduct.getProduct().getPrice() * soldProduct.getQuantity());
                Log.i("totalAmount1", totalAmount + "");
            }
            else {
                totalAmount = soldProduct.getPromotionPrice() * soldProduct.getQuantity();
                Log.i("totalAmount2", totalAmount + "");
            }
            soldProduct.setTotalAmt(totalAmount);
            totalAmountTextView.setText(totalAmount.toString());

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
                netAmount += soldProduct.getNetAmount(SaleOrderActivity.this);
            }

            ((TextView) context.findViewById(R.id.netAmountTextView)).setText(Utils.formatAmount(netAmount));
        }
    }

    private double calculatePromotinPriceAndGift(SoldProduct soldProduct) {
        double promotion_price = 0.0;
        String promotionProductId = "";
        String promotionProductName = "";
        int promotionProductQty = 0;

        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + DatabaseContract.PromotionDate.tb + "", null);
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
                Cursor cursorForPromotionGift = sqLiteDatabase.rawQuery("select * from " + DatabaseContract.PromotionGift.tb + " where " + DatabaseContract.PromotionGift.promotionPlanId + " = '" + promotionPlanId + "'" +
                        " and " + DatabaseContract.PromotionGift.fromQuantity + " <= " + buy_qty + " and " + DatabaseContract.PromotionGift.toQuantity + " >= " + buy_qty, null);

                List<String> productToBuy = new ArrayList<>();
                Log.i("GiftCount", cursorForPromotionGift.getCount() + "");
                while (cursorForPromotionGift.moveToNext()) {
                    String promotionProductToBuy = cursorForPromotionGift.getString(cursorForPromotionGift.getColumnIndex(DatabaseContract.PromotionGift.stockId));
                    Log.i("promotionPlanIdForGift", promotionPlanId + "");
                    productToBuy.add(promotionProductToBuy);
                }

                if(productToBuy.size() > 1) {
                    int productCount = 0;
                    for(String productId : productToBuy) {
                        for(SoldProduct soldProduct1 : soldProductList) {
                            if(productId.equals(String.valueOf(soldProduct1.getProduct().getStockId()))) {
                                productCount++;
                            }
                        }
                    }

                    if(productToBuy.size() == productCount) {
                        addToPromotionList(promotionProductId, promotionProductName, promotionProductQty, promotionPlanId);
                    }
                }

            }
        }

        setPromotionProductListView();

        return promotion_price;
    }

    void addToPromotionList(String promotionProductId, String promotionProductName, int promotionProductQty, String promotionPlanId) {
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

            if (!promotionProductId.equals("")) {

                if(promotionArrayList.size() == 0) {
                    Promotion promotion = new Promotion();
                    promotion.setPromotionProductId(promotionProductId);
                    promotion.setPromotionProductName(promotionProductName);
                    promotion.setPromotionQty(promotionProductQty);

                    promotionArrayList.add(promotion);
                } else {
                    for(Promotion item : promotionArrayList) {

                        if(item.getPromotionProductId().equals(promotionProductId)){
                            item.setPromotionQty(item.getPromotionQty() + promotionProductQty);
                        } else {
                            Promotion promotion = new Promotion();
                            promotion.setPromotionProductId(promotionProductId);
                            promotion.setPromotionProductName(promotionProductName);
                            promotion.setPromotionQty(promotionProductQty);

                            promotionArrayList.add(promotion);
                        }

                    }
                }

            }

        }
    }

    private void setPromotionProductListView() {
        int itemLength = promotionArrayList.size() * 100;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, itemLength);
        params.setMargins(20, 0, 0, 20);
        promotionPlanGiftListView.setLayoutParams(params);

        promotionProductCustomAdapter = new PromotionProductCustomAdapter(this);
        promotionPlanGiftListView.setAdapter(promotionProductCustomAdapter);
        promotionProductCustomAdapter.notifyDataSetChanged();
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
            priceTextView.setVisibility(View.GONE);

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

            return view;
        }
    }

    @Override
    public void onBackPressed() {
        if(isDelivery) {
            Utils.backToCustomerVisit(this);
        } else {
            Utils.backToCustomer(this);
        }
    }
}
