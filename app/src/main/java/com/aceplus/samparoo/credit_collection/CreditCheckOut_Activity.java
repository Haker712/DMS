package com.aceplus.samparoo.credit_collection;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.CreditInvoice;
import com.aceplus.samparoo.model.CreditInvoiceItems;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CreditCheckOut_Activity extends Activity {

    private ListView creditListView;
    private TextView totalAmountTxt;
    private TextView totalAdvancePayTxt;
    private TextView remainingPayAmountTxt;
    private TextView customerNameTxt;
    private TextView dateTxt;
    private TextView invnoTxt;
    private TextView invnoTotalAmountTxt;
    private TextView invnoPayAmountTxt;
    private TextView invnoCreditAmountTxt;
    private TextView refundTxt;
    private EditText payAmountEdit;
    private EditText itemPayEdit;
    private EditText receiptEdit;
    private LinearLayout totalLayout;
    private LinearLayout totalPayLayout;
    private LinearLayout itemPayLayout;
    private ImageView cancelImg, saveImg;

    SQLiteDatabase database;
    SimpleDateFormat fmtForInvoiceTodayStr = new SimpleDateFormat("yyMMdd");
    DecimalFormat invoiceFormat = new DecimalFormat("000");

    ArrayList<CreditInvoice> creditInvoiceList = new ArrayList<>();
    ArrayList<CreditInvoice> creditInvoiceSaveList = new ArrayList<>();
    ArrayList<CreditInvoiceItems> creditInvoiceItemsList = new ArrayList<>();

    public static String creditCustomer;
    public static String creditCustomerAddress;
    public static String customerId;
    public static String creditId;
    private String creditInvoiceId;
    String creditDate;
    String invoiceId;
    private static int listviewPosition = 0;
    public static double amount = 0.0;
    public static double paidAmount = 0.0;
    public static double unPaidAmount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_collection);

        creditDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        database = new Database(this).getDataBase();

        registerIDs();
        getCreditInvoiceData();
//        makeInvoiceNo(this);
        catchEvents();
    }

    private void registerIDs() {
        creditListView = (ListView) findViewById(R.id.creditcollection_list);
        totalAmountTxt = (TextView) findViewById(R.id.total_amount_txt);
        totalAdvancePayTxt = (TextView) findViewById(R.id.total_advance_pay_txt);
        remainingPayAmountTxt = (TextView) findViewById(R.id.remaining_pay_amount_txt);

        customerNameTxt = (TextView) findViewById(R.id.customer_name_txt);
        dateTxt = (TextView) findViewById(R.id.date_txt);
        invnoTxt = (TextView) findViewById(R.id.invno_txt);
        invnoTotalAmountTxt = (TextView) findViewById(R.id.invno_total_amount_txt);
        invnoPayAmountTxt = (TextView) findViewById(R.id.invno_pay_amount_txt);
        invnoCreditAmountTxt = (TextView) findViewById(R.id.invno_credit_amount_txt);
        refundTxt = (TextView) findViewById(R.id.refund_txt);
        payAmountEdit = (EditText) findViewById(R.id.payment_amount_edit);
        itemPayEdit = (EditText) findViewById(R.id.item_pay_edit);
        receiptEdit = (EditText) findViewById(R.id.receipt_person_edit);

        totalLayout = (LinearLayout) findViewById(R.id.total_layout);
        totalPayLayout = (LinearLayout) findViewById(R.id.total_pay_layout);
        itemPayLayout = (LinearLayout) findViewById(R.id.item_pay_layout);

        cancelImg = (ImageView) findViewById(R.id.cancel_img);
        saveImg = (ImageView) findViewById(R.id.save_img);
    }

    private void catchEvents() {
        customerNameTxt.setText(creditCustomer);
        dateTxt.setText(creditDate);
        invnoTxt.setText(invoiceId);

        payAmountEdit.setText(null);
        itemPayEdit.setText(null);


        final ArrayAdapter<CreditInvoice> credditArrayAdapter = new CreditCollectAdapter(this);
        creditListView.setAdapter(credditArrayAdapter);
        creditListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                totalPayLayout.setVisibility(View.GONE);
                itemPayLayout.setVisibility(View.VISIBLE);
                listviewPosition = position;
                CreditInvoice creditInvoice = creditInvoiceList.get(position);
                customerNameTxt.setText(creditCustomer);
                dateTxt.setText(creditInvoice.getInvoiceDate());
                invnoTxt.setText(creditInvoice.getInvoiceId());
                invnoTotalAmountTxt.setText(Utils.formatAmount(creditInvoice.getAmount()));
                invnoPayAmountTxt.setText(Utils.formatAmount(creditInvoice.getPaidAmount()));
                invnoCreditAmountTxt.setText(Utils.formatAmount(creditInvoice.getCreditAmount()));
                itemPayEdit.setText(null);
            }
        });
        totalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalPayLayout.setVisibility(View.VISIBLE);
                itemPayLayout.setVisibility(View.GONE);
                credditArrayAdapter.notifyDataSetChanged();
                customerNameTxt.setText(creditCustomer);
                dateTxt.setText(creditDate);
                invnoTxt.setText(invoiceId);
                double totalAmountStr = Double.parseDouble(totalAmountTxt.getText().toString().replace(",", ""));
                double advancePayStr = Double.parseDouble(totalAdvancePayTxt.getText().toString().replace(",", ""));
                double remainingPaystr = Double.parseDouble(remainingPayAmountTxt.getText().toString().replace(",", ""));
                invnoTotalAmountTxt.setText(Utils.formatAmount(totalAmountStr));
                invnoPayAmountTxt.setText(Utils.formatAmount(advancePayStr));
                invnoCreditAmountTxt.setText(Utils.formatAmount(remainingPaystr));
                payAmountEdit.setText(null);
            }
        });

        creditListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                View dialogBoxView = getLayoutInflater().inflate(R.layout.dialog_box_credit_sale_product, null);
                ListView saleProductListView = (ListView) dialogBoxView.findViewById(R.id.credit_sale_product_list);
                creditInvoiceItemsList.clear();
                CreditInvoice creditInvoice = creditInvoiceList.get(position);
                creditInvoiceId = creditInvoice.getInvoiceId();
                getCreditInvoiceItemsData();

                saleProductListView.setAdapter(new SaleProductArrayAdapter(CreditCheckOut_Activity.this));
                new AlertDialog.Builder(CreditCheckOut_Activity.this)
                        .setView(dialogBoxView)
                        .setTitle("Credit Sale Products")
                        .setPositiveButton("OK", null)
                        .show();
                return true;
            }
        });

        itemPayEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int arg1, int arg2, int arg3) {

                if (charSequence.toString().length() > 0) {

                    String convertedString = charSequence.toString();
                    convertedString = Utils.formatAmount(Double.parseDouble(charSequence.toString().replace(",", "")));
                    if (!itemPayEdit.getText().toString().equals(convertedString)
                            && convertedString.length() > 0) {
                        itemPayEdit.setText(convertedString);
                        itemPayEdit.setSelection(itemPayEdit.getText().length());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String tempPayAmount = editable.toString().replace(",", "");
                String tempNetAmount = invnoTotalAmountTxt.getText().toString().replace(",", "");

                if (tempPayAmount.length() > 0 && tempNetAmount.length() > 0) {

                    if (Double.parseDouble(tempPayAmount) >= Double.parseDouble(tempNetAmount)) {
                        refundTxt.setText(Utils.formatAmount(Double.parseDouble(tempPayAmount) - Double.parseDouble(tempNetAmount)));
                        String str = "paid";
                        CreditInvoice creditInvoice = creditInvoiceList.get(listviewPosition);
                        double totalAmt = Double.valueOf(invnoTotalAmountTxt.getText().toString().replace(",", ""));
                        double creditAmt = 0;
                        creditInvoice.setStatus(str);
                        creditInvoice.setPaidAmount(totalAmt);
                        creditInvoice.setCreditAmount(creditAmt);
                        credditArrayAdapter.notifyDataSetChanged();
                        invnoPayAmountTxt.setText(Utils.formatAmount(totalAmt));
                        invnoCreditAmountTxt.setText(Utils.formatAmount(creditAmt));
                    } else {
                        refundTxt.setText(Utils.formatAmount(Double.parseDouble(tempPayAmount) - Double.parseDouble(tempNetAmount)));
                        String str = "unpaid";
                        CreditInvoice creditInvoice = creditInvoiceList.get(listviewPosition);
                        double payAmt = Double.valueOf(invnoPayAmountTxt.getText().toString().replace(",", ""));
                        double tempPay = Double.parseDouble(tempPayAmount);
                        double payTotal = payAmt + tempPay;
                        double creditTotal = Double.parseDouble(tempNetAmount) - Double.parseDouble(tempPayAmount);
                        creditInvoice.setStatus(str);
                        creditInvoice.setPaidAmount(payTotal);
                        creditInvoice.setCreditAmount(creditTotal);
                        credditArrayAdapter.notifyDataSetChanged();
                        invnoPayAmountTxt.setText(Utils.formatAmount(payTotal));
                        invnoCreditAmountTxt.setText(Utils.formatAmount(creditTotal));
                    }
                } else {
                    refundTxt.setText("0");
                    String str = "unpaid";
                    CreditInvoice creditInvoice = creditInvoiceList.get(listviewPosition);
                    double payAmt = Double.valueOf(invnoPayAmountTxt.getText().toString().replace(",", ""));
                    double creditAmt = Double.valueOf(invnoCreditAmountTxt.getText().toString().replace(",", ""));
                    creditInvoice.setStatus(str);
                    creditInvoice.setPaidAmount(payAmt);
                    creditInvoice.setCreditAmount(creditAmt);
                    credditArrayAdapter.notifyDataSetChanged();
                    invnoPayAmountTxt.setText(Utils.formatAmount(payAmt));
                    invnoCreditAmountTxt.setText(Utils.formatAmount(creditAmt));
                }
            }
        });
        payAmountEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSequence, int arg1, int arg2, int arg3) {

                if (charSequence.toString().length() > 0) {

                    String convertedString = charSequence.toString();
                    convertedString = Utils.formatAmount(Double.parseDouble(charSequence.toString().replace(",", "")));
                    if (!payAmountEdit.getText().toString().equals(convertedString)
                            && convertedString.length() > 0) {
                        payAmountEdit.setText(convertedString);
                        payAmountEdit.setSelection(payAmountEdit.getText().length());
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String tempPayAmount = editable.toString().replace(",", "");
                String tempNetAmount = invnoTotalAmountTxt.getText().toString().replace(",", "");
                for (int i = 0; i < creditInvoiceList.size(); i++) {
                    credditArrayAdapter.notifyDataSetChanged();
                    CreditInvoice creditInvoice = creditInvoiceList.get(i);
                    double paidValue = creditInvoice.getPaidAmount();
                    double creditValue = creditInvoice.getCreditAmount();
                    CreditInvoice saveCredit = new CreditInvoice();
                    saveCredit.setPaidAmount(paidValue);
                    saveCredit.setCreditAmount(creditValue);
                    creditInvoiceSaveList.add(saveCredit);
                }
                if (tempPayAmount.length() > 0 && tempNetAmount.length() > 0) {

                    if (Double.parseDouble(tempPayAmount) >= Double.parseDouble(tempNetAmount)) {
                        refundTxt.setText(Utils.formatAmount(Double.parseDouble(tempPayAmount) - Double.parseDouble(tempNetAmount)));
                        String str = "paid";
                        double paidValue = 0.0;
                        double creditAmt = 0.0;
                        double paidValueTotal = 0.0;
                        for (int i = 0; i < creditInvoiceList.size(); i++) {
                            CreditInvoice creditInvoice = creditInvoiceList.get(i);
                            paidValue = creditInvoice.getAmount();
                            paidValueTotal += paidValue;
                            creditInvoice.setPaidAmount(paidValue);
                            creditInvoice.setCreditAmount(creditAmt);
                            creditInvoice.setStatus(str);
                        }
                        credditArrayAdapter.notifyDataSetChanged();
                        invnoPayAmountTxt.setText(Utils.formatAmount(paidValueTotal));
                        invnoCreditAmountTxt.setText(Utils.formatAmount(creditAmt));
                    } else {
                        refundTxt.setText(Utils.formatAmount(Double.parseDouble(tempPayAmount) - Double.parseDouble(tempNetAmount)));
                        String str = "unpaid";
                        double paidValue = 0.0;
                        double unPaidValue = 0.0;
                        double paidValueTotal = 0.0;
                        double unPaidValueTotal = 0.0;
                        for (int i = 0; i < creditInvoiceList.size(); i++) {
                            CreditInvoice savecreditInvoice = creditInvoiceSaveList.get(i);
                            paidValue = savecreditInvoice.getPaidAmount();
                            unPaidValue = savecreditInvoice.getCreditAmount();
                            paidValueTotal += paidValue;
                            unPaidValueTotal += unPaidValue;
                            CreditInvoice creditInvoice = creditInvoiceList.get(i);
                            creditInvoice.setPaidAmount(paidValue);
                            creditInvoice.setCreditAmount(unPaidValue);
                            creditInvoice.setStatus(str);
                        }
                        credditArrayAdapter.notifyDataSetChanged();
                        invnoPayAmountTxt.setText(Utils.formatAmount(paidValueTotal));
                        invnoCreditAmountTxt.setText(Utils.formatAmount(unPaidValueTotal));
                    }
                } else {
                    refundTxt.setText("0");
                    String str = "unpaid";
                    double paidValue = 0.0;
                    double unPaidValue = 0.0;
                    double paidValueTotal = 0.0;
                    double unPaidValueTotal = 0.0;
                    for (int i = 0; i < creditInvoiceList.size(); i++) {
                        CreditInvoice savecreditInvoice = creditInvoiceSaveList.get(i);
                        paidValue = savecreditInvoice.getPaidAmount();
                        unPaidValue = savecreditInvoice.getCreditAmount();
                        paidValueTotal += paidValue;
                        unPaidValueTotal += unPaidValue;
                        CreditInvoice creditInvoice = creditInvoiceList.get(i);
                        creditInvoice.setPaidAmount(paidValue);
                        creditInvoice.setCreditAmount(unPaidValue);
                        creditInvoice.setStatus(str);
                    }
                    credditArrayAdapter.notifyDataSetChanged();
                    invnoPayAmountTxt.setText(Utils.formatAmount(paidValueTotal));
                    invnoCreditAmountTxt.setText(Utils.formatAmount(unPaidValueTotal));
                }
            }
        });
        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreditCheckOut_Activity.this, CreditCollectActivity.class));
                finish();
            }
        });
        saveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (receiptEdit.getText().toString().length() == 0) {

                    new AlertDialog.Builder(CreditCheckOut_Activity.this)
                            .setTitle("Alert")
                            .setMessage("Your must provide 'Receipt Person'.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {

                                    receiptEdit.requestFocus();
                                }
                            })
                            .show();
                    return;
                }
                insertIntoDB();
                Utils.backToCustomerVisit(CreditCheckOut_Activity.this);
            }
        });
    }

    private void getCreditInvoiceData() {
        database.beginTransaction();
        Cursor cursor = database.rawQuery(
                "SELECT CREDIT_ID,INV_ID,INV_DATE,TOTAL_AMT,FLAG,PAID_AMT FROM CREDIT_INVOICE"
                        + " WHERE CREDIT_ID = \"" + creditId + "\""
                , null);
        creditInvoiceList.clear();
        while (cursor.moveToNext()) {
            String invoiceId = cursor.getString(cursor.getColumnIndex("INV_ID"));
            String invoiceDate = cursor.getString(cursor.getColumnIndex("INV_DATE"));
            double Amt = cursor.getDouble(cursor.getColumnIndex("TOTAL_AMT"));
            double paidAmt = cursor.getDouble(cursor.getColumnIndex("PAID_AMT"));
            String flag = cursor.getString(cursor.getColumnIndex("FLAG"));

            CreditInvoice creditInvoice = new CreditInvoice();
            creditInvoice.setInvoiceId(invoiceId);
            creditInvoice.setInvoiceDate(invoiceDate);
            creditInvoice.setAmount(Amt);
            creditInvoice.setPaidAmount(paidAmt);
            creditInvoice.setCreditAmount(Amt - paidAmt);
            creditInvoice.setStatus(flag);
            creditInvoiceList.add(creditInvoice);
        }
        cursor.close();
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    private void getCreditInvoiceItemsData() {
        database.beginTransaction();
        Cursor cursor = database.rawQuery(
                "SELECT INV_ID,PRODUCT_ID,QTY,DISCOUNT FROM CREDIT_ITEMS"
                        + " WHERE INV_ID = \"" + creditInvoiceId + "\""
                , null);
        creditInvoiceItemsList.clear();
        while (cursor.moveToNext()) {
            String productName = cursor.getString(cursor.getColumnIndex("PRODUCT_ID"));
            double quantity = cursor.getDouble(cursor.getColumnIndex("QTY"));
            double discount = cursor.getDouble(cursor.getColumnIndex("DISCOUNT"));

            CreditInvoiceItems creditInvoiceItems = new CreditInvoiceItems();
            creditInvoiceItems.setProductName(productName);
            creditInvoiceItems.setQuantity(quantity);
            creditInvoiceItems.setDiscount(discount);
            creditInvoiceItemsList.add(creditInvoiceItems);
        }
        cursor.close();
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    private void insertIntoDB() {
        double saveAmount = 0.0;
        double savePaidAmount = 0.0;
        double saveUnPaidAmount = 0.0;
        String flag = null;

        database.beginTransaction();
        for (CreditInvoice creditInvoice : creditInvoiceList) {
            saveAmount += creditInvoice.getAmount();
            savePaidAmount += creditInvoice.getPaidAmount();
            saveUnPaidAmount += creditInvoice.getAmount()- creditInvoice.getPaidAmount();
            flag = creditInvoice.getStatus();
            String arg[] = {creditInvoice.getInvoiceId()};
            ContentValues cv = new ContentValues();
            cv.put("FLAG",flag);
            cv.put("PAID_AMT",creditInvoice.getPaidAmount());
            database.update("CREDIT_INVOICE", cv, "INV_ID LIKE ?", arg);
        }
        String arg[] = {creditId};
        ContentValues cvCredit = new ContentValues();
        cvCredit.put("PAID_AMT", savePaidAmount);
        cvCredit.put("CREDIT_AMT", saveUnPaidAmount);
        cvCredit.put("FLAG", flag);
        database.update("CREDIT", cvCredit, "CREDIT_ID LIKE ?", arg);
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public class CreditCollectAdapter extends ArrayAdapter<CreditInvoice> {
        private final Activity context;

        public CreditCollectAdapter(Activity context) {
            super(context, R.layout.credit_collection_list_row, creditInvoiceList);//creditList
            this.context = context;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {

            CreditInvoice creditInv = creditInvoiceList.get(position);
            double tamount = 0.0;
            double tpaidAmount = 0.0;
            double tremainingAmount = 0.0;
            for (int i = 0; i < creditInvoiceList.size(); i++) {
                CreditInvoice creditInvoice = creditInvoiceList.get(i);
                tamount += creditInvoice.getAmount();
                tpaidAmount += creditInvoice.getPaidAmount();
                tremainingAmount += creditInvoice.getCreditAmount();
            }
            totalAmountTxt.setText(Utils.formatAmount(tamount));
            totalAdvancePayTxt.setText(Utils.formatAmount(tpaidAmount));
            remainingPayAmountTxt.setText(Utils.formatAmount(tremainingAmount));

            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.credit_collection_list_row, null, true);

            TextView invId = (TextView) rowView.findViewById(R.id.invoice_id);
            TextView invdate = (TextView) rowView.findViewById(R.id.invoice_date);
            TextView invAmt = (TextView) rowView.findViewById(R.id.invoice_amount);
            TextView statusTxt = (TextView) rowView.findViewById(R.id.status_txt);

            invId.setText(creditInv.getInvoiceId());
            invdate.setText(creditInv.getInvoiceDate());
            invAmt.setText(Utils.formatAmount(creditInv.getAmount()));
            statusTxt.setText(creditInv.getStatus());
            return rowView;
        }
    }

    private class SaleProductArrayAdapter extends ArrayAdapter<CreditInvoiceItems> {

        public final Activity context;

        public SaleProductArrayAdapter(Activity context) {

            super(context, R.layout.list_row_credit_sale_products, creditInvoiceItemsList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            CreditInvoiceItems creditInvoiceItems = creditInvoiceItemsList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_credit_sale_products, null, true);

            TextView productNameTextView = (TextView) view.findViewById(R.id.credit_name);
            TextView quantityTextView = (TextView) view.findViewById(R.id.credit_qty);
            TextView priceTextView = (TextView) view.findViewById(R.id.credit_price);
            TextView discountTextView = (TextView) view.findViewById(R.id.credit_discount);
            TextView totalAmtTextView = (TextView) view.findViewById(R.id.credit_totalAmt);

            productNameTextView.setText(creditInvoiceItems.getProductName());
            quantityTextView.setText(Utils.formatAmount(creditInvoiceItems.getQuantity()));
            priceTextView.setText(Utils.formatAmount(creditInvoiceItems.getPrice()));
            discountTextView.setText(Utils.formatAmount(creditInvoiceItems.getDiscount()));
            totalAmtTextView.setText(Utils.formatAmount(creditInvoiceItems.getTotalAmount()));

            return view;
        }
    }
}
