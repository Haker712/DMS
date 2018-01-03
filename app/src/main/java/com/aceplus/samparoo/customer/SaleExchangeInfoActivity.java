package com.aceplus.samparoo.customer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aceplus.samparoo.PrintInvoiceActivity;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.model.Product;
import com.aceplus.samparoo.model.Promotion;
import com.aceplus.samparoo.model.SaleReturn;
import com.aceplus.samparoo.model.SoldProduct;
import com.aceplus.samparoo.model.forApi.Invoice;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by haker on 3/17/17.
 */

public class SaleExchangeInfoActivity extends AppCompatActivity {

    @InjectView(R.id.textViewDate)
    TextView textViewDate;

    @InjectView(R.id.textViewCustomerName)
    TextView textViewSaleReturnDiscountAmt;

    @InjectView((R.id.textViewSaleReturnDiscountAmt))
    TextView textViewCustomerName;

    @InjectView(R.id.textViewSaleReturnAmt)
    TextView textViewSaleReturnAmt;

    @InjectView(R.id.textViewSaleExchangeAmt)
    TextView textViewSaleExchangeAmt;

    @InjectView(R.id.textViewPayAmt)
    TextView textViewPayAmt;

    @InjectView(R.id.textViewRefund)
    TextView textViewRefundAmt;

    Customer customer;
    String date = "";
    double saleReturnAmt = 0.0, saleExchangeAmt = 0.0, discountAmt = 0.0;
    String saleReturnInvoiceId = "", saleExchangeInvoiceId = "";
    double pay_amt = 0.0, refund_amt = 0.0;

    SQLiteDatabase sqLiteDatabase;

    @InjectView(R.id.sx_info_sale_exchange_list)
    ListView exchangeListView;

    @InjectView(R.id.sx_info_sale_list)
    ListView saleListView;

    @InjectView(R.id.print_img)
    ImageView printImg;

    Invoice invoice;

    ArrayList<SoldProduct> saleReturnProductList, saleExchangeProductList;
    Invoice invoie;
    List<SoldProduct> invoiceDetailList;
    List<Promotion> invoicePresentList;

    List<SoldProduct> saleReturnList;
    List<SoldProduct> saleList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sale_exchange_info_layout);

        ButterKnife.inject(this);

        sqLiteDatabase = new Database(this).getDataBase();

        if (getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE) != null) {
            invoie = (Invoice) getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE);
        }

        if (getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE_PRESENT) != null) {
            invoicePresentList = (List<Promotion>) getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE_PRESENT);
        }

        if (getIntent().getSerializableExtra(SaleCheckoutActivity.SOLD_PROUDCT_LIST_KEY) != null) {
            invoiceDetailList = (List<SoldProduct>) getIntent().getSerializableExtra(SaleCheckoutActivity.SOLD_PROUDCT_LIST_KEY);
        }

        customer = (Customer) getIntent().getSerializableExtra(SaleCheckoutActivity.CUSTOMER_INFO_KEY);
        date = getIntent().getStringExtra(SaleCheckoutActivity.DATE_KEY);
        saleReturnInvoiceId = getIntent().getStringExtra(SaleActivity.SALE_RETURN_INVOICEID_KEY);
        saleExchangeInvoiceId = getIntent().getStringExtra(SaleCheckoutActivity.SALE_EXCHANGE_INVOICEID_KEY);

        invoice = new Invoice();
        invoice.setId(saleExchangeInvoiceId);

        getDatasForTextView();

        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int height = point.y;

        RelativeLayout.LayoutParams exListLayout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height/4);
        exListLayout.addRule(RelativeLayout.BELOW, R.id.sale_return_headerLayout);
        exchangeListView.setLayoutParams(exListLayout);

        RelativeLayout.LayoutParams exListLayout1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height/4);
        exListLayout1.addRule(RelativeLayout.BELOW, R.id.sale_exchange_headerLayout);
        saleListView.setLayoutParams(exListLayout1);

        textViewDate.setText(date);
        textViewCustomerName.setText(customer.getCustomerName());
        textViewSaleReturnAmt.setText(Utils.formatAmount(saleReturnAmt) + " MMK");
        textViewSaleReturnDiscountAmt.setText(Utils.formatAmount(discountAmt) + " MMK");
        textViewSaleExchangeAmt.setText(Utils.formatAmount(saleExchangeAmt) + " MMK");

        if (saleReturnAmt > saleExchangeAmt) {
            refund_amt = (saleReturnAmt - discountAmt) - saleExchangeAmt;
        }
        else {
            pay_amt = saleExchangeAmt - (saleReturnAmt - discountAmt);
        }

        textViewPayAmt.setText(Utils.formatAmount(pay_amt) + " MMK");
        textViewRefundAmt.setText(Utils.formatAmount(refund_amt) + " MMK");
    }

    private void getDatasForTextView() {
        Cursor cursorForSaleReturnAmt = sqLiteDatabase.rawQuery("select * from SALE_RETURN where SALE_RETURN_ID = '" + saleReturnInvoiceId + "'", null);
        while (cursorForSaleReturnAmt.moveToNext()) {
            saleReturnAmt = cursorForSaleReturnAmt.getDouble(cursorForSaleReturnAmt.getColumnIndex("AMT"));
            discountAmt = cursorForSaleReturnAmt.getDouble(cursorForSaleReturnAmt.getColumnIndex("DISCOUNT"));
            pay_amt = cursorForSaleReturnAmt.getDouble(cursorForSaleReturnAmt.getColumnIndex("PAY_AMT"));
        }

        Cursor cursorForSaleExchangeAmt = sqLiteDatabase.rawQuery("select * from INVOICE where INVOICE_ID = '" + saleExchangeInvoiceId + "'", null);
        while (cursorForSaleExchangeAmt.moveToNext()) {
            saleExchangeAmt = cursorForSaleExchangeAmt.getDouble(cursorForSaleExchangeAmt.getColumnIndex("TOTAL_AMOUNT"));
        }

        setProductList();
        printImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View v1 = getWindow().getDecorView().getRootView();
                v1.setDrawingCacheEnabled(true);
                Bitmap myBitmap = v1.getDrawingCache();

                Utils.saveInvoiceImageIntoGallery(invoie.getId(), SaleExchangeInfoActivity.this,myBitmap, "Sale");
                List<Promotion> tempPresentList = new ArrayList<>();
                tempPresentList.addAll(invoicePresentList);

                List<SoldProduct> editProductList = arrangeProductList(saleList, tempPresentList);
                Utils.printSaleExchange(SaleExchangeInfoActivity.this
                        , invoie.getId()
                        , saleReturnInvoiceId
                        , getSaleManName(invoie.getSalepersonId())
                        , invoie, editProductList, saleReturnList, discountAmt);
            }
        });
    }

    private void setProductList(){
        List<Product> saleReturnProductList = getDataForSaleReturn();
        List<Product> saleProductList = getDataForSaleExchange();

        if(saleReturnProductList.size() > 0) {
            exchangeListView.setAdapter(new SaleExchangeInfoRowAdapter(this, R.layout.row_sale_exchange_info, saleReturnProductList));
        }

        if(saleProductList.size() > 0){
            saleListView.setAdapter(new SaleExchangeInfoRowAdapter(this, R.layout.row_sale_exchange_info, saleProductList));
        }

        saleReturnList = new ArrayList<>();
        for(Product product: saleReturnProductList) {
            SoldProduct soldProduct = new SoldProduct(product, false);
            saleReturnList.add(soldProduct);
        }

        saleList = new ArrayList<>();
        for(Product product: saleProductList) {
            SoldProduct soldProduct = new SoldProduct(product, false);
            saleList.add(soldProduct);
        }
    }

    /**
     * Get product list for current sale exchange
     * @return product list
     */
    private List<Product> getDataForSaleReturn() {
        List<Product> productList = new ArrayList<>();
        saleReturnProductList = new ArrayList<>();
        Cursor cursorForSaleReturnItem = sqLiteDatabase.rawQuery("select P.PRODUCT_NAME, SR.PRICE, SR.QUANTITY from SALE_RETURN_DETAIL AS SR LEFT JOIN PRODUCT AS P ON P.PRODUCT_ID = SR.PRODUCT_ID where SALE_RETURN_ID = '" + saleReturnInvoiceId + "'", null);
        while (cursorForSaleReturnItem.moveToNext()) {
            Product product = new Product();
            product.setName(cursorForSaleReturnItem.getString(cursorForSaleReturnItem.getColumnIndex("PRODUCT_NAME")));
            product.setPrice(cursorForSaleReturnItem.getDouble(cursorForSaleReturnItem.getColumnIndex("PRICE")));
            product.setSoldQty(cursorForSaleReturnItem.getInt(cursorForSaleReturnItem.getColumnIndex("QUANTITY")));
            productList.add(product);

            SoldProduct soldProduct = new SoldProduct(product, false);
            saleReturnProductList.add(soldProduct);
        }
        return productList;
    }

    private String getSaleManName(int id) {
        Cursor cursorSaleMan = sqLiteDatabase.rawQuery("SELECT USER_NAME FROM SALE_MAN WHERE ID = " + id, null);
        String saleManName = "";
        while(cursorSaleMan.moveToNext()) {
            saleManName = cursorSaleMan.getString(cursorSaleMan.getColumnIndex("USER_NAME"));

        }
        return saleManName;
    }

    private String getCustomerName(int id) {
        Cursor cursorCustomer = sqLiteDatabase.rawQuery("SELECT CUSTOMER_NAME FROM CUSTOMER WHERE id = " + id + "", null);
        String customerName = "";
        while(cursorCustomer.moveToNext()) {
            customerName = cursorCustomer.getString(cursorCustomer.getColumnIndex("CUSTOMER_NAME"));

        }
        return customerName;
    }

    private String getCustomerTownshipName(int id) {
        Cursor cursorCustomer = sqLiteDatabase.rawQuery("SELECT TOWNSHIP_NAME FROM TOWNSHIP WHERE TOWNSHIP_ID = (SELECT township_number FROM CUSTOMER where id = "+ id + ")", null);
        String townshipName = "";
        while(cursorCustomer.moveToNext()) {
            townshipName = cursorCustomer.getString(cursorCustomer.getColumnIndex("TOWNSHIP_NAME"));

        }
        return townshipName;
    }

    /**
     * Get product list for current sale exchange
     * @return product list
     */
    private List<Product> getDataForSaleExchange() {
        List<Product> productList = new ArrayList<>();
        saleExchangeProductList = new ArrayList<>();
        Cursor cursorForSaleReturnItem = sqLiteDatabase.rawQuery("select P.PRODUCT_NAME, P.SELLING_PRICE, IP.SALE_QUANTITY from INVOICE_PRODUCT AS IP LEFT JOIN PRODUCT AS P ON P.ID = IP.PRODUCT_ID where IP.INVOICE_PRODUCT_ID = '" + saleExchangeInvoiceId + "'", null);
        while (cursorForSaleReturnItem.moveToNext()) {
            Product product = new Product();
            product.setName(cursorForSaleReturnItem.getString(cursorForSaleReturnItem.getColumnIndex("PRODUCT_NAME")));
            product.setPrice(cursorForSaleReturnItem.getDouble(cursorForSaleReturnItem.getColumnIndex("SELLING_PRICE")));
            product.setSoldQty(cursorForSaleReturnItem.getInt(cursorForSaleReturnItem.getColumnIndex("SALE_QUANTITY")));
            productList.add(product);

            SoldProduct soldProduct = new SoldProduct(product, false);
            saleExchangeProductList.add(soldProduct);
        }
        return productList;
    }

    @OnClick(R.id.confirmAndPrint_img)
    void confirm() {
        Utils.backToCustomer(this);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Please click Confirm Button!")
                .setPositiveButton("Ok", null)
                .show();
    }

    List<SoldProduct> arrangeProductList(List<SoldProduct> soldProductList, List<Promotion> presentList) {

        List<Integer> positionList = new ArrayList<>();

        List<SoldProduct> newSoldProductList = new ArrayList<>();
        List<Promotion> newPresentList = new ArrayList<>();

        for(int i = 0; i < presentList.size(); i++) {

            int stockId1 = 0;
            if(presentList.get(i).getPromotionProductId() != null && !presentList.get(i).getPromotionProductId().equals("")) {
                stockId1 = Integer.parseInt(presentList.get(i).getPromotionProductId());
            }

            int indexForNew = 0;
            for (Promotion promotion : newPresentList) {
                int stockId = 0;
                if(promotion.getPromotionProductId() != null && !promotion.getPromotionProductId().equals("")) {
                    stockId = Integer.parseInt(promotion.getPromotionProductId());
                }

                if(stockId != stockId1 && (indexForNew+1) == newPresentList.size()) {
                    Promotion promotion1 = new Promotion();
                    promotion1.setPromotionQty(0);
                    promotion1.setCurrencyId(presentList.get(i).getCurrencyId());
                    promotion1.setPrice(presentList.get(i).getPrice());
                    promotion1.setPromotionPlanId(presentList.get(i).getPromotionPlanId());
                    promotion1.setPromotionPrice(presentList.get(i).getPromotionPrice());
                    promotion1.setPromotionProductId(presentList.get(i).getPromotionProductId());
                    promotion1.setPromotionProductName(presentList.get(i).getPromotionProductName());

                    newPresentList.add(promotion1);
                }
                indexForNew++;
            }

            if(newPresentList.size() == 0) {
                Promotion promotion1 = new Promotion();
                promotion1.setPromotionQty(0);
                promotion1.setCurrencyId(presentList.get(i).getCurrencyId());
                promotion1.setPrice(presentList.get(i).getPrice());
                promotion1.setPromotionPlanId(presentList.get(i).getPromotionPlanId());
                promotion1.setPromotionPrice(presentList.get(i).getPromotionPrice());
                promotion1.setPromotionProductId(presentList.get(i).getPromotionProductId());
                promotion1.setPromotionProductName(presentList.get(i).getPromotionProductName());

                newPresentList.add(promotion1);
            }
        }

        List<Promotion> tempPresentList = new ArrayList<>();
        tempPresentList.addAll(presentList);

        for(Promotion promotion : tempPresentList) {
            int stockId = 0;
            if(promotion.getPromotionProductId() != null && !promotion.getPromotionProductId().equals("")) {
                stockId = Integer.parseInt(promotion.getPromotionProductId());
            }

            for(int kk = 0; kk < newPresentList.size() ; kk++) {
                int stockId1 = 0;
                if(newPresentList.get(kk).getPromotionProductId() != null && !newPresentList.get(kk).getPromotionProductId().equals("")) {
                    stockId1 = Integer.parseInt(newPresentList.get(kk).getPromotionProductId());
                }

                if(stockId == stockId1) {
                    int promotionQty = promotion.getPromotionQty();
                    newPresentList.get(kk).setPromotionQty(promotionQty + newPresentList.get(kk).getPromotionQty());
                    int index = presentList.indexOf(promotion);
                    presentList.remove(index);
                }
            }
        }

        for(int i = 0; i < soldProductList.size(); i++ ) {
            int soldProductStockId = soldProductList.get(i).getProduct().getStockId();

            newSoldProductList.add(soldProductList.get(i));
            if (newPresentList != null && newPresentList.size() > 0) {
                for(int j = 0; j < newPresentList.size(); j++) {
                    int promoProductId =  Integer.parseInt(newPresentList.get(j).getPromotionProductId());

                    if(soldProductStockId == promoProductId) {
                        SoldProduct promoProduct = new SoldProduct(new Product(), false);
                        promoProduct.getProduct().setStockId(promoProductId);
                        promoProduct.setQuantity(newPresentList.get(j).getPromotionQty());
                        promoProduct.getProduct().setPurchasePrice(0.0);
                        promoProduct.getProduct().setPrice(0.0);
                        promoProduct.getProduct().setName(newPresentList.get(j).getPromotionProductName());
                        promoProduct.setPromotionPrice(0.0);
                        newSoldProductList.add(promoProduct);
                        positionList.add(j);
                        break;
                    }
                }
            }
        }

        for(int i = positionList.size(); i < 0; i--) {
            int pos = positionList.get(i-1);
            newPresentList.remove(pos);
        }

        return newSoldProductList;
    }

    class SaleExchangeInfoRowAdapter extends ArrayAdapter<Product> {

        Activity context;
        List<Product> productList;

        public SaleExchangeInfoRowAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Product> objects) {
            super(context, resource, objects);
            this.context = (Activity) context;
            this.productList = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.row_sale_exchange_info, null, true);

            TextView productName = (TextView) view.findViewById(R.id.sx_info_row_name);
            TextView productQty = (TextView) view.findViewById(R.id.sx_info_row_qty);
            TextView productPrice = (TextView) view.findViewById(R.id.sx_info_row_price);

            Product product = productList.get(position);
            productName.setText(product.getName() + "");
            productQty.setText(product.getSoldQty()+ "");
            productPrice.setText(Utils.formatAmount(product.getPrice()));

            return view;
        }
    }
}
