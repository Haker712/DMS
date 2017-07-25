package com.aceplus.samparoo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aceplus.samparoo.customer.SaleCheckoutActivity;
import com.aceplus.samparoo.model.CreditInvoice;
import com.aceplus.samparoo.model.Promotion;
import com.aceplus.samparoo.model.SoldProduct;
import com.aceplus.samparoo.model.forApi.Invoice;
import com.aceplus.samparoo.model.forApi.InvoiceDetail;
import com.aceplus.samparoo.model.forApi.InvoicePresent;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aceplus_mobileteam on 6/16/17.
 */

public class PrintInvoiceActivity extends Activity{

    Invoice invoie;
    List<SoldProduct> invoiceDetailList;
    List<Promotion> invoicePresentList;
    List<CreditInvoice> creditList;

    public static final String INVOICE = "INVOICE";
    public static final String INVOICE_DETAIL = "INVOICE_DETAIL";
    public static final String INVOICE_PRESENT = "INVOICE_PRESENT";
    String taxType = null, branchCode = null, creditFlg = null;
    Double taxPercent = 0.0;
    int pos = 0;

    TextView txtSaleDate, txtInvoiceNo, txtSaleMan, txtBranch, totalAmountTxtView, netAmountTxtView, prepaidAmountTxtView, print_discountAmountTxtView;
    TextView creditCustomerName, creditTownshipName, creditRecievieNo, creditInvoiceNo, creditCollectPerson, creditDate, crediTotalAmount, crediDiscount, crediNetAmount, creditReceiveAmount;;
    ImageView cancelBtn, printBtn;
    String saleman_Id = "";
    SQLiteDatabase database;
    SoldProductListRowAdapter soldProductListRowAdapter;
    PromotionProductCustomAdapter promotionProductCustomAdapter;
    ListView soldProductListView, promotionPlanItemListView;
    RelativeLayout saleLayout;
    LinearLayout creditPrintHeaderLayout, salePrintHeaderLayout1, salePrintHeaderLayout2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_print);
        database = new Database(this).getDataBase();

        if (getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE) != null) {
            invoie = (Invoice) getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE);
        }

        if (getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE_PRESENT) != null) {
            invoicePresentList = (List<Promotion>) getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE_PRESENT);
        }

        if (getIntent().getSerializableExtra(SaleCheckoutActivity.SOLD_PROUDCT_LIST_KEY) != null) {
            invoiceDetailList = (List<SoldProduct>) getIntent().getSerializableExtra(SaleCheckoutActivity.SOLD_PROUDCT_LIST_KEY);
        }

        if (getIntent().getSerializableExtra("CREDIT") != null) {
            creditList = (List<CreditInvoice>) getIntent().getSerializableExtra("CREDIT");
        }

        if (getIntent().getSerializableExtra("CREDIT_FLG") != null) {
            creditFlg = (String) getIntent().getSerializableExtra("CREDIT_FLG");
        }

        if (getIntent().getSerializableExtra("CUR_POS") != null) {
            pos = (int) getIntent().getSerializableExtra("CUR_POS");
        }

        registerIds();
        getTaxAmount();
        setDataToWidgets();
        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(creditFlg!= null && !creditFlg.equals("")) {

                    if (creditList != null && creditList.size() > 0) {
                        Utils.printCredit(PrintInvoiceActivity.this, getCustomerName(creditList.get(pos).getCustomerId()), getCustomerTownshipName(creditList.get(pos).getCustomerId())
                                , creditList.get(pos).getInvoiceNo(), getSaleManName(creditList.get(pos).getSaleManId()), creditList.get(pos));
                    }
                } else {
                    Utils.print(PrintInvoiceActivity.this, getCustomerName(Integer.parseInt(invoie.getCustomerId()))
                            , invoie.getId()
                            , getSaleManName(invoie.getSalepersonId()), invoie, invoiceDetailList, invoicePresentList, Utils.PRINT_FOR_NORMAL_SALE
                            , Utils.FOR_OTHERS);
                }

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void registerIds() {
        txtSaleDate = (TextView) findViewById(R.id.saleDate);
        txtInvoiceNo = (TextView) findViewById(R.id.invoiceId);
        txtSaleMan = (TextView) findViewById(R.id.saleMan);
        txtBranch = (TextView) findViewById(R.id.branch);
        printBtn = (ImageView) findViewById(R.id.print_img);
        cancelBtn = (ImageView) findViewById(R.id.cancel_img);
        soldProductListView = (ListView) findViewById(R.id.print_soldProductList);
        promotionPlanItemListView = (ListView) findViewById(R.id.promotion_plan_item_listview);
        totalAmountTxtView = (TextView) findViewById(R.id.print_totalAmount);
        netAmountTxtView = (TextView) findViewById(R.id.print_net_amount);
        prepaidAmountTxtView = (TextView) findViewById(R.id.print_prepaidAmount);
        print_discountAmountTxtView = (TextView) findViewById(R.id.print_discountAmount);
        saleLayout = (RelativeLayout) findViewById(R.id.printForSaleLayout);
        creditPrintHeaderLayout = (LinearLayout) findViewById(R.id.crditPrintHeaderLayout1);
        salePrintHeaderLayout1 = (LinearLayout) findViewById(R.id.salePrintHeaderLayout1);
        salePrintHeaderLayout2 = (LinearLayout) findViewById(R.id.salePrintHeaderLayout2);

        if(creditFlg != null && !creditFlg.equals("")) {
            creditCustomerName = (TextView) findViewById(R.id.credit_customer_name);
            creditTownshipName = (TextView) findViewById(R.id.credit_township_name);
            creditRecievieNo = (TextView) findViewById(R.id.credit_receive_no);
            creditInvoiceNo = (TextView) findViewById(R.id.credit_invoice_no);
            creditCollectPerson = (TextView) findViewById(R.id.credit_sale_man);
            creditDate = (TextView) findViewById(R.id.credit_date);
            crediTotalAmount = (TextView) findViewById(R.id.credit_total_amt);
            crediDiscount = (TextView) findViewById(R.id.credit_discount);
            crediNetAmount = (TextView) findViewById(R.id.credit_net_amount);
            creditReceiveAmount = (TextView) findViewById(R.id.credit_receive_amt);


        }
    }

    private void setDataToWidgets() {
        if(creditFlg== null || creditFlg.equals("")) {
            txtSaleDate.setText(invoie.getDate().substring(0,10));
            txtInvoiceNo.setText(invoie.getId());
            txtSaleMan.setText(getSaleManName(invoie.getSalepersonId()));
            txtBranch.setText(branchCode);
            soldProductListView.setAdapter(new SoldProductListRowAdapter(this));
            setPromotionProductListView();
            totalAmountTxtView.setText(Utils.formatAmount(invoie.getTotalAmt()));
            if(taxType.equalsIgnoreCase("E")) {
                netAmountTxtView.setText(Utils.formatAmount(invoie.getTotalAmt() - invoie.getTotalDiscountAmt() + invoie.getTaxAmount()));
            } else {
                netAmountTxtView.setText(Utils.formatAmount(invoie.getTotalAmt() - invoie.getTotalDiscountAmt()));
            }
            prepaidAmountTxtView.setText(Utils.formatAmount(invoie.getTotalPayAmt()));
            print_discountAmountTxtView.setText(Utils.formatAmount(invoie.getTotalDiscountAmt()) + " (" + new DecimalFormat("#0.00").format(invoie.getDiscountPercent()) + "%)");
        } else {
            saleLayout.setVisibility(View.GONE);
            salePrintHeaderLayout1.setVisibility(View.GONE);
            salePrintHeaderLayout2.setVisibility(View.GONE);
            creditPrintHeaderLayout.setVisibility(View.VISIBLE);
            creditDate.setText(creditList.get(pos).getInvoiceDate().substring(0,10));
            creditInvoiceNo.setText(creditList.get(pos).getInvoiceNo());
            creditCollectPerson.setText(getSaleManName(creditList.get(pos).getSaleManId()));
            creditCustomerName.setText(getCustomerName(creditList.get(pos).getCustomerId()));
            creditTownshipName.setText(getCustomerTownshipName(creditList.get(pos).getCustomerId()));
            creditRecievieNo.setText(creditList.get(pos).getInvoiceNo());

            crediTotalAmount.setTextSize(30);
            crediDiscount.setTextSize(30);
            crediNetAmount.setTextSize(30);
            creditReceiveAmount.setTextSize(30);

            crediTotalAmount.setText(Utils.formatAmount(creditList.get(pos).getAmt()));
            crediNetAmount.setText(Utils.formatAmount(creditList.get(pos).getAmt()));
            creditReceiveAmount.setText(Utils.formatAmount(creditList.get(pos).getPayAmt()));
            crediDiscount.setText("0.0 (0%)");
        }

    }

    private String getSaleManName(int id) {
        Cursor cursorSaleMan = database.rawQuery("SELECT USER_NAME FROM SALE_MAN WHERE ID = " + id, null);
        String saleManName = "";
        while(cursorSaleMan.moveToNext()) {
            saleManName = cursorSaleMan.getString(cursorSaleMan.getColumnIndex("USER_NAME"));

        }
        return saleManName;
    }

    private String getCustomerName(int id) {
        Cursor cursorCustomer = database.rawQuery("SELECT CUSTOMER_NAME FROM CUSTOMER WHERE id = " + id + "", null);
        String customerName = "";
        while(cursorCustomer.moveToNext()) {
            customerName = cursorCustomer.getString(cursorCustomer.getColumnIndex("CUSTOMER_NAME"));

        }
        return customerName;
    }

    private String getCustomerTownshipName(int id) {
        Cursor cursorCustomer = database.rawQuery("SELECT TOWNSHIP_NAME FROM TOWNSHIP WHERE TOWNSHIP_ID = (SELECT township_number FROM CUSTOMER where id = "+ id + ")", null);
        String townshipName = "";
        while(cursorCustomer.moveToNext()) {
            townshipName = cursorCustomer.getString(cursorCustomer.getColumnIndex("TOWNSHIP_NAME"));

        }
        return townshipName;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utils.backToCustomer(PrintInvoiceActivity.this);
    }

    private void setPromotionProductListView() {
        int itemLength = invoicePresentList.size() * 100;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, itemLength);
        params.setMargins(20, 0, 0, 20);
        promotionPlanItemListView.setLayoutParams(params);

        promotionProductCustomAdapter = new PromotionProductCustomAdapter(this);
        promotionPlanItemListView.setAdapter(promotionProductCustomAdapter);
        promotionProductCustomAdapter.notifyDataSetChanged();
    }

    private class SoldProductListRowAdapter extends ArrayAdapter<SoldProduct> {

        final Activity context;

        public SoldProductListRowAdapter(Activity context) {

            super(context, R.layout.list_row_sold_product_checkout, invoiceDetailList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            SoldProduct soldProduct = invoiceDetailList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_sold_product_checkout, null, true);

            final TextView nameTextView = (TextView) view.findViewById(R.id.name);
            final TextView umTextView = (TextView) view.findViewById(R.id.um);
            final TextView qtyTextView = (TextView) view.findViewById(R.id.qty);
            final TextView priceTextView = (TextView) view.findViewById(R.id.price);
            final TextView discountTextView = (TextView) view.findViewById(R.id.discount);
            final TextView totalAmountTextView = (TextView) view.findViewById(R.id.amount);

            umTextView.setVisibility(View.GONE);

            nameTextView.setText(soldProduct.getProduct().getName());
            umTextView.setText(soldProduct.getProduct().getUm());
            qtyTextView.setText(soldProduct.getQuantity() + "");

            discountTextView.setText(soldProduct.getDiscountAmount() + "");

            if(soldProduct.getPromotionPrice() == 0.0) {
                priceTextView.setText(Utils.formatAmount(soldProduct.getProduct().getPrice()));
            } else {
                priceTextView.setText(Utils.formatAmount(soldProduct.getPromotionPrice()));
            }

            totalAmountTextView.setText(Utils.formatAmount(soldProduct.getTotalAmount()));
            return view;
        }
    }

    private class PromotionProductCustomAdapter extends ArrayAdapter<Promotion> {

        final Activity context;

        public PromotionProductCustomAdapter(Activity context) {
            super(context, R.layout.list_row_promotion, invoicePresentList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Promotion promotion = invoicePresentList.get(position);

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
            if (promotion.getPromotionPrice()!= null && promotion.getPromotionPrice() != 0.0) {
                priceTextView.setText(promotion.getPromotionPrice() + "");
            } else {
                priceTextView.setVisibility(View.GONE);
            }

            return view;
        }
    }

    void getTaxAmount() {
        Cursor cursorTax = database.rawQuery("SELECT TaxType, Tax, Branch_Code FROM COMPANYINFORMATION", null);
        while(cursorTax.moveToNext()) {
            taxType = cursorTax.getString(cursorTax.getColumnIndex("TaxType"));
            taxPercent = cursorTax.getDouble(cursorTax.getColumnIndex("Tax"));
            branchCode = cursorTax.getString(cursorTax.getColumnIndex("Branch_Code"));
        }
    }
}
