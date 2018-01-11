package com.aceplus.samparoo.customer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aceplus.samparoo.CustomerVisitActivity;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Product;
import com.aceplus.samparoo.model.Promotion;
import com.aceplus.samparoo.model.SaleCancelItem;
import com.aceplus.samparoo.model.SoldProduct;
import com.aceplus.samparoo.model.forApi.Invoice;
import com.aceplus.samparoo.model.forApi.InvoiceDetail;
import com.aceplus.samparoo.model.forApi.InvoicePresent;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaleCancelActivity extends AppCompatActivity {

    ImageView cancelBtn;
    RecyclerView saleCancelItemList;
    ArrayList<SaleCancelItem> saleCancelItems;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_cancel);

        database = new Database(this).getDataBase();
        cancelBtn = (ImageView) findViewById(R.id.sale_cancel_img);
        saleCancelItemList = (RecyclerView) findViewById(R.id.sale_cancel_item_list);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        saleCancelItemList.setLayoutManager(layoutManager);

        saleCancelItems = getSaleInvoiceForToday();
        saleCancelItemList.setAdapter(new SaleCancelItemAdapter(saleCancelItems));
        registerEvents();
    }

    void registerEvents() {
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaleCancelActivity.this.onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {

        Intent toSaleCancel = new Intent(SaleCancelActivity.this, CustomerVisitActivity.class);
        startActivity(toSaleCancel);
        finish();
    }

    class SaleCancelItemAdapter extends RecyclerView.Adapter<SaleCancelItemAdapter.SaleCancelItemViewHolder> {
        ArrayList<SaleCancelItem> saleCancelItemList;

        public SaleCancelItemAdapter(ArrayList<SaleCancelItem> saleCancelItemList) {
            this.saleCancelItemList = saleCancelItemList;
        }

        @Override
        public SaleCancelItemAdapter.SaleCancelItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sale_cancel_activity, parent, false);
            return new SaleCancelItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SaleCancelItemAdapter.SaleCancelItemViewHolder holder, final int position) {
            final SaleCancelItem saleCancelItem = saleCancelItemList.get(position);
            holder.sale_cancel_activity_row_txt_customerName.setText(saleCancelItem.getCustomerName());
            holder.sale_cancel_activity_row_txt_saleInvoice.setText(saleCancelItem.getSaleInvoiceNo());
            holder.sale_cancel_activity_row_txt_date.setText(saleCancelItem.getDate());
            holder.sale_cancel_activity_row_txt_totalQty.setText(saleCancelItem.getTotalSaleQty() + "");
            holder.sale_cancel_activity_row_txt_totalAmt.setText(saleCancelItem.getTotalSaleAmt() + "");

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SaleCancelActivity.this, SaleCancelCheckoutActivity.class);
                    ArrayList<SoldProduct> detailList = getInvoiceDetailList(saleCancelItem.getSaleInvoiceNo());
                    intent.putExtra(SaleActivity.SOLD_PROUDCT_LIST_KEY, detailList);
                    intent.putExtra(SaleActivity.ORDERED_INVOICE_KEY, saleCancelItem.getSaleInvoiceNo());
                    intent.putExtra("SaleExchange", "no");
                    intent.putExtra(SaleCheckoutActivity.PRESENT_PROUDCT_LIST_KEY, getPresentByInvoiceId(detailList, saleCancelItem.getSaleInvoiceNo()));
                    startActivity(intent);
                    finish();
                }
            });
        }

        @Override
        public int getItemCount() {
            return saleCancelItemList.size();
        }

        public class SaleCancelItemViewHolder extends RecyclerView.ViewHolder {
            public TextView sale_cancel_activity_row_txt_customerName, sale_cancel_activity_row_txt_saleInvoice, sale_cancel_activity_row_txt_date, sale_cancel_activity_row_txt_totalQty, sale_cancel_activity_row_txt_totalAmt;

            public SaleCancelItemViewHolder(View itemView) {
                super(itemView);
                sale_cancel_activity_row_txt_customerName = (TextView) itemView.findViewById(R.id.row_sale_cancel_customer_name);
                sale_cancel_activity_row_txt_saleInvoice = (TextView) itemView.findViewById(R.id.row_sale_cancel_sale_invoice);
                sale_cancel_activity_row_txt_date = (TextView) itemView.findViewById(R.id.row_sale_cancel_date);
                sale_cancel_activity_row_txt_totalQty = (TextView) itemView.findViewById(R.id.row_sale_cancel_total_qty);
                sale_cancel_activity_row_txt_totalAmt = (TextView) itemView.findViewById(R.id.row_sale_cancel_total_sale_amt);
            }
        }
    }

    ArrayList<SaleCancelItem> getSaleInvoiceForToday() {
        ArrayList<SaleCancelItem> saleCancelItemList = new ArrayList<>();
        Cursor invoiceListForToday = database.rawQuery("SELECT * FROM INVOICE WHERE date(SALE_DATE) = date('now')", null);

        while (invoiceListForToday.moveToNext()) {
            SaleCancelItem saleCancelItem = new SaleCancelItem();
            saleCancelItem.setSaleInvoiceNo(invoiceListForToday.getString(invoiceListForToday.getColumnIndex("INVOICE_ID")));
            saleCancelItem.setCustomerName(getCustomerName(invoiceListForToday.getInt(invoiceListForToday.getColumnIndex("CUSTOMER_ID"))));
            saleCancelItem.setDate(invoiceListForToday.getString(invoiceListForToday.getColumnIndex("SALE_DATE")));
            saleCancelItem.setTotalSaleQty(invoiceListForToday.getInt(invoiceListForToday.getColumnIndex("TOTAL_QUANTITY")));
            saleCancelItem.setTotalSaleAmt(invoiceListForToday.getDouble(invoiceListForToday.getColumnIndex("TOTAL_AMOUNT")));
            saleCancelItemList.add(saleCancelItem);
        }
        invoiceListForToday.close();
        return saleCancelItemList;
    }

    private String getCustomerName(int id) {
        Cursor cursorCustomer = database.rawQuery("SELECT CUSTOMER_NAME FROM CUSTOMER WHERE id = " + id + "", null);
        String customerName = "";
        while (cursorCustomer.moveToNext()) {
            customerName = cursorCustomer.getString(cursorCustomer.getColumnIndex("CUSTOMER_NAME"));

        }
        cursorCustomer.close();
        return customerName;
    }

    ArrayList<SoldProduct> getInvoiceDetailList(String invoiceId) {
        ArrayList<SoldProduct> invoiceDetailList = new ArrayList<>();

        Cursor cursorInvoiceProducts = database.rawQuery("SELECT IP.*, P.REMAINING_QTY FROM INVOICE_PRODUCT AS IP LEFT JOIN PRODUCT AS P ON P.ID = IP.PRODUCT_ID WHERE IP.INVOICE_PRODUCT_ID = '" + invoiceId + "'", null);

        while (cursorInvoiceProducts.moveToNext()) {

            Product product = new Product();
            product.setStockId(cursorInvoiceProducts.getInt(cursorInvoiceProducts.getColumnIndex("PRODUCT_ID")));
            product.setId(product.getStockId()+"");
            product.setPrice(cursorInvoiceProducts.getDouble(cursorInvoiceProducts.getColumnIndex("S_PRICE")));
            product.setPurchasePrice(cursorInvoiceProducts.getDouble(cursorInvoiceProducts.getColumnIndex("P_PRICE")));
            String[] names = getProductNameAndUm(product.getStockId());
            product.setName(names[0]);
            product.setUm(names[1]);
            product.setRemainingQty(cursorInvoiceProducts.getInt(cursorInvoiceProducts.getColumnIndex("REMAINING_QTY")));

            SoldProduct soldProduct = new SoldProduct(product, false);
            soldProduct.setQuantity(cursorInvoiceProducts.getInt(cursorInvoiceProducts.getColumnIndex("SALE_QUANTITY")));
            product.setRemainingQty(product.getRemainingQty() + soldProduct.getQuantity());
            soldProduct.setDiscountAmount(cursorInvoiceProducts.getDouble(cursorInvoiceProducts.getColumnIndex("DISCOUNT_AMOUNT")));
            soldProduct.setDiscountPercent(cursorInvoiceProducts.getDouble(cursorInvoiceProducts.getColumnIndex("DISCOUNT_PERCENT")));
            soldProduct.setPromotionPrice(cursorInvoiceProducts.getDouble(cursorInvoiceProducts.getColumnIndex("PROMOTION_PRICE")));
            soldProduct.setPromotionPlanId(cursorInvoiceProducts.getString(cursorInvoiceProducts.getColumnIndex("PROMOTION_PLAN_ID")));
            soldProduct.setExclude(cursorInvoiceProducts.getInt(cursorInvoiceProducts.getColumnIndex("EXCLUDE")));
            invoiceDetailList.add(soldProduct);
        }
        cursorInvoiceProducts.close();
        return invoiceDetailList;
    }

    /**
     * Get promotion list that is related to product list.
     * @param detailList product list
     * @param invoiceId invoice no
     * @return promotion list that is related to product list
     */
    ArrayList<Promotion> getPresentByInvoiceId(ArrayList<SoldProduct> detailList, String invoiceId) {

        List<Map<String, Integer>> stockIdList = new ArrayList<>();
        for (SoldProduct soldProduct : detailList) {
            if (soldProduct.getPromotionPlanId() != null) {
                stockIdList.add(checkPromotionToBuyProduct(soldProduct.getPromotionPlanId()));
            }
        }

        ArrayList<Promotion> promotionArrayList = new ArrayList<>();
        Cursor cursorPromotion = database.rawQuery("SELECT * FROM INVOICE_PRESENT WHERE tsale_id = '" + invoiceId + "'", null);
        while (cursorPromotion.moveToNext()) {
            Promotion promotion = new Promotion();
            promotion.setPromotionProductId(cursorPromotion.getString(cursorPromotion.getColumnIndex("stock_id")));
            promotion.setPromotionQty(cursorPromotion.getInt(cursorPromotion.getColumnIndex("quantity")));

            for (Map<String, Integer> map : stockIdList) {
                Integer stockId = map.get("STOCK_ID");
                Integer quantity = map.get("QUANTITY");
                Integer planId = map.get("PLAN_ID");

                if (stockId.equals(Integer.parseInt(promotion.getPromotionProductId())) && quantity.equals(promotion.getPromotionQty()) && promotion.getPromotionPlanId() == null) {
                    promotion.setPromotionPlanId(planId + "");
                    stockIdList.remove(map);
                    break;
                }
            }

            String[] name = getProductNameAndUm(Integer.parseInt(promotion.getPromotionProductId()));
            promotion.setPromotionProductName(name[0]);
            promotion.setPrice(0.0);
            promotion.setCurrencyId(1);
            promotionArrayList.add(promotion);
        }
        cursorPromotion.close();
        return promotionArrayList;
    }

    /**
     * Check promotion product by its planId.
     * @param promotionPlanId String
     * @return map that contains promotion product id, quantity and plan id.
     */
    Map<String, Integer> checkPromotionToBuyProduct(String promotionPlanId) {
        Map<String, Integer> stockIdList = new HashMap<>();

        Cursor giftItemCursor = database.rawQuery("select * from " + DatabaseContract.PromotionGiftItem.tb + " where " + DatabaseContract.PromotionGiftItem.promotionPlanId + " = '" + promotionPlanId + "'", null);
        if (giftItemCursor.moveToNext()) {
            stockIdList.put("STOCK_ID", giftItemCursor.getInt(giftItemCursor.getColumnIndex(DatabaseContract.PromotionGiftItem.stockId)));
            stockIdList.put("QUANTITY", giftItemCursor.getInt(giftItemCursor.getColumnIndex(DatabaseContract.PromotionGiftItem.quantity)));
            stockIdList.put("PLAN_ID", Integer.parseInt(promotionPlanId));
        }
        return stockIdList;
    }

    /**
     * Get proudct name and um name of
     * @param stockId
     * @return
     */
    String[] getProductNameAndUm(int stockId) {
        Cursor cursorProductName = database.rawQuery("SELECT PRODUCT_NAME, UM FROM PRODUCT WHERE ID = " + stockId, null);
        String[] products = new String[2];
        while (cursorProductName.moveToNext()) {
            products[0] = cursorProductName.getString(cursorProductName.getColumnIndex("PRODUCT_NAME"));
            products[1] = cursorProductName.getString(cursorProductName.getColumnIndex("UM"));
        }
        cursorProductName.close();
        return products;
    }
}
