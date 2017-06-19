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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aceplus.samparoo.customer.SaleCheckoutActivity;
import com.aceplus.samparoo.model.SoldProduct;
import com.aceplus.samparoo.model.forApi.Invoice;
import com.aceplus.samparoo.model.forApi.InvoiceDetail;
import com.aceplus.samparoo.model.forApi.InvoicePresent;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aceplus_mobileteam on 6/16/17.
 */

public class PrintInvoiceActivity extends Activity{

    Invoice invoie;
    List<SoldProduct> invoiceDetailList;
    List<InvoicePresent> invoicePresentList;

    public static final String INVOICE = "INVOICE";
    public static final String INVOICE_DETAIL = "INVOICE_DETAIL";
    public static final String INVOICE_PRESENT = "INVOICE_PRESENT";

    TextView txtSaleDate, txtInvoiceNo, txtSaleMan, txtBranch, totalAmount, prepaidAmount;
    ImageView cancelBtn, printBtn;
    String saleman_Id = "";
    SQLiteDatabase database;
    SoldProductListRowAdapter soldProductListRowAdapter;
    ListView soldProductListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_print);
        database = new Database(this).getDataBase();

        if (getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE) != null) {
            invoie = (Invoice) getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE);
        }

        if (getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE_PRESENT) != null) {
            invoicePresentList = (List<InvoicePresent>) getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE_PRESENT);
        }

        if (getIntent().getSerializableExtra(SaleCheckoutActivity.SOLD_PROUDCT_LIST_KEY) != null) {
            invoiceDetailList = (List<SoldProduct>) getIntent().getSerializableExtra(SaleCheckoutActivity.SOLD_PROUDCT_LIST_KEY);
        }

        registerIds();
        setDataToWidgets();
        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.print(PrintInvoiceActivity.this, getCustomerName(invoie.getCustomerId())
                        , invoie.getId()
                        , getSaleManName(invoie.getSalepersonId()), invoie.getTotalPayAmt(), invoiceDetailList, Utils.PRINT_FOR_NORMAL_SALE
                        , Utils.FOR_OTHERS);

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
        totalAmount = (TextView) findViewById(R.id.print_totalAmount);
        prepaidAmount = (TextView) findViewById(R.id.print_prepaidAmount);
    }

    private void setDataToWidgets() {
        txtSaleDate.setText(invoie.getDate().substring(0,10));
        txtInvoiceNo.setText(invoie.getId());
        txtSaleMan.setText(getSaleManName(invoie.getSalepersonId()));
        soldProductListView.setAdapter(new SoldProductListRowAdapter(this));
        totalAmount.setText(Utils.formatAmount(invoie.getTotalAmt()));
        prepaidAmount.setText(Utils.formatAmount(invoie.getTotalPayAmt()));
    }

    private String getSaleManName(int id) {
        Cursor cursorSaleMan = database.rawQuery("SELECT USER_NAME FROM SALE_MAN WHERE ID = " + id, null);
        String saleManName = "";
        while(cursorSaleMan.moveToNext()) {
            saleManName = cursorSaleMan.getString(cursorSaleMan.getColumnIndex("USER_NAME"));

        }
        return saleManName;
    }

    private String getCustomerName(String id) {
        Cursor cursorCustomer = database.rawQuery("SELECT CUSTOMER_NAME FROM CUSTOMER WHERE id = '" + id + "'", null);
        String customerName = "";
        while(cursorCustomer.moveToNext()) {
            customerName = cursorCustomer.getString(cursorCustomer.getColumnIndex("CUSTOMER_NAME"));

        }
        return customerName;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utils.backToCustomer(PrintInvoiceActivity.this);
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

            priceTextView.setText(Utils.formatAmount(soldProduct.getProduct().getPrice()));
            totalAmountTextView.setText(Utils.formatAmount(soldProduct.getTotalAmount()));
            return view;
        }
    }
}
