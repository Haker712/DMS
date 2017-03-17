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
import android.util.Log;
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
import com.aceplus.samparoo.model.CustomerCredit;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private LinearLayout totalLayout, totalAmountLayout, totalCreditLayout, totalPayAmountLayout;
    private LinearLayout totalPayLayout;
    private LinearLayout itemPayLayout;
    private ImageView cancelImg, saveImg;

    public static final String CREDIT_KEY = "credit-key";
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

    CustomerCredit customerCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_collection);

        creditDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        database = new Database(this).getDataBase();
        customerCredit = (CustomerCredit) getIntent().getSerializableExtra(CREDIT_KEY);
        registerIDs();
        getCreditInvoiceData(customerCredit.getCustomerId());
//        makeInvoiceNo(this);
        setValueToCustomerAdapter();
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

        totalAmountLayout = (LinearLayout) findViewById(R.id.side_total_amt_layout);
        totalCreditLayout = (LinearLayout) findViewById(R.id.side_credit_amt_layout);
        totalPayAmountLayout = (LinearLayout) findViewById(R.id.side_pay_amt_layout);

        cancelImg = (ImageView) findViewById(R.id.cancel_img);
        saveImg = (ImageView) findViewById(R.id.save_img);
    }

    private void setValueToCustomerAdapter() {
        totalAmountLayout.setVisibility(View.GONE);
        totalCreditLayout.setVisibility(View.GONE);
        totalPayAmountLayout.setVisibility(View.GONE);

        customerNameTxt.setText(customerCredit.getCustomerCreditname());
        dateTxt.setText(creditDate);
        invnoTxt.setText(invoiceId);
        invnoTotalAmountTxt.setText(String.valueOf(customerCredit.getCreditTotalAmt()));
        invnoCreditAmountTxt.setText(String.valueOf(customerCredit.getCreditTotalAmt()));
        invnoPayAmountTxt.setText(String.valueOf(customerCredit.getCreditPaidAmt()));

        payAmountEdit.setText(null);
        itemPayEdit.setText(null);
    }

    private void catchEvents() {
        final ArrayAdapter<CreditInvoice> credditArrayAdapter = new CreditCollectAdapter(this);
        creditListView.setAdapter(credditArrayAdapter);
        creditListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                totalPayLayout.setVisibility(View.GONE);
                itemPayLayout.setVisibility(View.VISIBLE);
                listviewPosition = position;
                CreditInvoice creditInvoice = creditInvoiceList.get(position);
                customerNameTxt.setText(customerCredit.getCustomerCreditname());
                dateTxt.setText(creditInvoice.getInvoiceDate());
                invnoTxt.setText(creditInvoice.getInvoiceNo());
                invnoTotalAmountTxt.setText(Utils.formatAmount(creditInvoice.getAmt()));
                invnoPayAmountTxt.setText(Utils.formatAmount(creditInvoice.getPayAmt()));
                invnoCreditAmountTxt.setText(Utils.formatAmount(creditInvoice.getCreditAmt()));
                itemPayEdit.setText("");
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

                double tempPayAmount = 0.0;
                double tempNetAmount = 0.0;
                if(!editable.toString().equals("") && !invnoTotalAmountTxt.getText().toString().equals("")) {
                    tempPayAmount = Double.parseDouble(editable.toString().replace(",", ""));
                    tempNetAmount = Double.parseDouble(invnoTotalAmountTxt.getText().toString().replace(",", ""));
                } else {
                    refundTxt.setText("0");
                }

                if(tempPayAmount > tempNetAmount) {
                    refundTxt.setText(Utils.formatAmount(tempPayAmount - tempNetAmount));
                }

                /*if (tempPayAmount.length() > 0 && tempNetAmount.length() > 0) {

                    if (Double.parseDouble(tempPayAmount) >= Double.parseDouble(tempNetAmount)) {
                        refundTxt.setText(Utils.formatAmount(Double.parseDouble(tempPayAmount) - Double.parseDouble(tempNetAmount)));
                        String str = "paid";
                        CreditInvoice creditInvoice = creditInvoiceList.get(listviewPosition);
                        double totalAmt = Double.valueOf(invnoTotalAmountTxt.getText().toString().replace(",", ""));
                        double creditAmt = 0;
                        //creditInvoice.setStatus(str);
                        creditInvoice.setPayAmt(totalAmt);
                        creditInvoice.setCreditAmt(creditAmt);
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
                        //creditInvoice.setStatus(str);
                        creditInvoice.setPayAmt(payTotal);
                        creditInvoice.setCreditAmt(creditTotal);
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
                    //creditInvoice.setStatus(str);
                    creditInvoice.setPayAmt(payAmt);
                    creditInvoice.setCreditAmt(creditAmt);
                    credditArrayAdapter.notifyDataSetChanged();
                    invnoPayAmountTxt.setText(Utils.formatAmount(payAmt));
                    invnoCreditAmountTxt.setText(Utils.formatAmount(creditAmt));
                }*/
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
                double tempPayAmount = 0.0;
                double tempNetAmount = 0.0;
                if(!editable.toString().equals("") && !invnoTotalAmountTxt.getText().toString().equals("")) {
                    tempPayAmount = Double.parseDouble(editable.toString().replace(",", ""));
                    tempNetAmount = Double.parseDouble(invnoTotalAmountTxt.getText().toString().replace(",", ""));
                } else {
                    refundTxt.setText("0");
                }

                if(tempPayAmount > tempNetAmount) {
                    refundTxt.setText(Utils.formatAmount(tempPayAmount - tempNetAmount));
                }

                /*for (int i = 0; i < creditInvoiceList.size(); i++) {
                    credditArrayAdapter.notifyDataSetChanged();
                    CreditInvoice creditInvoice = creditInvoiceList.get(i);
                    double paidValue = creditInvoice.getPayAmt();
                    double creditValue = creditInvoice.getCreditAmt();
                    CreditInvoice saveCredit = new CreditInvoice();
                    saveCredit.setPayAmt(paidValue);
                    saveCredit.setCreditAmt(creditValue);
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
                            paidValue = creditInvoice.getAmt();
                            paidValueTotal += paidValue;
                            creditInvoice.setPayAmt(paidValue);
                            creditInvoice.setCreditAmt(creditAmt);
                            //creditInvoice.setStatus(str);
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
                            paidValue = savecreditInvoice.getPayAmt();
                            unPaidValue = savecreditInvoice.getCreditAmt();
                            paidValueTotal += paidValue;
                            unPaidValueTotal += unPaidValue;
                            CreditInvoice creditInvoice = creditInvoiceList.get(i);
                            creditInvoice.setPayAmt(paidValue);
                            creditInvoice.setCreditAmt(unPaidValue);
                            //creditInvoice.setStatus(str);
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
                        paidValue = savecreditInvoice.getPayAmt();
                        unPaidValue = savecreditInvoice.getCreditAmt();
                        paidValueTotal += paidValue;
                        unPaidValueTotal += unPaidValue;
                        CreditInvoice creditInvoice = creditInvoiceList.get(i);
                        creditInvoice.setPayAmt(paidValue);
                        creditInvoice.setCreditAmt(unPaidValue);
                        //creditInvoice.setStatus(str);
                    }
                    credditArrayAdapter.notifyDataSetChanged();
                    invnoPayAmountTxt.setText(Utils.formatAmount(paidValueTotal));
                    invnoCreditAmountTxt.setText(Utils.formatAmount(unPaidValueTotal));
                }*/
            }
        });
        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreditCheckOut_Activity.this.onBackPressed();
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

                if(totalPayLayout.getVisibility() == View.VISIBLE) {
                    insertIntoDB(calculatePayAmount(payAmountEdit.getText().toString()));
                } else {
                    insertIntoDB(calculatePayAmount(itemPayEdit.getText().toString()));
                }

                Utils.backToCustomerVisit(CreditCheckOut_Activity.this);
            }
        });
    }

    private void getCreditInvoiceData(int customerId) {

        Cursor cursor = database.rawQuery("SELECT * FROM CREDIT WHERE CUSTOMER_ID = " + customerId, null);
        creditInvoiceList.clear();
        while (cursor.moveToNext()) {
            String invoiceId = cursor.getString(cursor.getColumnIndex("INVOICE_NO"));
            String invoiceDate = cursor.getString(cursor.getColumnIndex("INVOICE_DATE"));
            double Amt = cursor.getDouble(cursor.getColumnIndex("AMT"));
            double paidAmt = cursor.getDouble(cursor.getColumnIndex("PAY_AMT"));
            String flag = cursor.getString(cursor.getColumnIndex("FIRST_PAY_AMT"));

            CreditInvoice creditInvoice = new CreditInvoice();
            creditInvoice.setInvoiceNo(invoiceId);
            creditInvoice.setInvoiceDate(invoiceDate);
            creditInvoice.setCustomerId(customerId);
            creditInvoice.setAmt(Amt);
            creditInvoice.setPayAmt(paidAmt);
            creditInvoice.setCreditAmt(Amt - paidAmt);
            //creditInvoice.setStatus(flag);

            creditInvoiceList.add(creditInvoice);
        }
        cursor.close();
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

    private void insertIntoDB(List<CreditInvoice> invoiceList) {
        Cursor creditCount = database.rawQuery("SELECT * FROM " + DatabaseContract.CASH_RECEIVE.TABLE, null);
        int rowCount = creditCount.getCount();

        database.beginTransaction();
        for (CreditInvoice creditInvoice : invoiceList) {
            //flag = creditInvoice.getStatus();
            ContentValues cashReceiveCv = new ContentValues();
            cashReceiveCv.put(DatabaseContract.CASH_RECEIVE.ID, rowCount + 1);
            cashReceiveCv.put(DatabaseContract.CASH_RECEIVE.RECEIVE_NO, creditInvoice.getInvoiceNo());
            cashReceiveCv.put(DatabaseContract.CASH_RECEIVE.RECEIVE_DATE, creditInvoice.getInvoiceDate());
            cashReceiveCv.put(DatabaseContract.CASH_RECEIVE.CUSTOMER_ID, creditInvoice.getCustomerId());
            cashReceiveCv.put(DatabaseContract.CASH_RECEIVE.AMOUNT, creditInvoice.getPayAmt());
            cashReceiveCv.put(DatabaseContract.CASH_RECEIVE.CURRENCY_ID, customerCredit.getCurrencyId());
            cashReceiveCv.put(DatabaseContract.CASH_RECEIVE.STATUS, "");
            cashReceiveCv.put(DatabaseContract.CASH_RECEIVE.LOCATION_ID, getLocationCode());
            cashReceiveCv.put(DatabaseContract.CASH_RECEIVE.PAYMENT_TYPE, "");
            cashReceiveCv.put(DatabaseContract.CASH_RECEIVE.CASH_RECEIVE_TYPE, "");
            cashReceiveCv.put(DatabaseContract.CASH_RECEIVE.SALE_ID, "");
            //cv.put("FLAG",flag);
            ContentValues receiveItemCv = new ContentValues();
            receiveItemCv.put(DatabaseContract.CASH_RECEIVE.RECEIVE_NO, creditInvoice.getInvoiceNo());

            Cursor creditIdCursor = database.rawQuery("SELECT ID FROM " + DatabaseContract.CREDIT.TABLE + " WHERE INVOICE_NO = \'" + creditInvoice.getInvoiceNo() + "\'", null);
            while(creditIdCursor.moveToNext()) {
                receiveItemCv.put(DatabaseContract.CASH_RECEIVE.SALE_ID, creditIdCursor.getInt(creditIdCursor.getColumnIndex(DatabaseContract.CREDIT.ID)));
            }

            database.insert(DatabaseContract.CASH_RECEIVE.TABLE, null, cashReceiveCv);
            database.insert(DatabaseContract.CASH_RECEIVE_ITEM.TABLE, null, receiveItemCv);
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    private List<CreditInvoice> calculatePayAmount(String payAmt) {

        double payAmount = Double.parseDouble(payAmt.replace(",", ""));
        List<CreditInvoice> remainList = new ArrayList<>();
        List<CreditInvoice> tempCreditList = new ArrayList<>();
        tempCreditList.addAll(creditInvoiceList);

        for(CreditInvoice creditInvoice : creditInvoiceList) {
            double creditAmount = creditInvoice.getAmt() - creditInvoice.getPayAmt();

            if(payAmount != 0.0 && payAmount < creditAmount) {
                creditInvoice.setPayAmt(payAmount);
                payAmount = 0.0;
                remainList.add(creditInvoice);

            } else if(payAmount != 0.0 && payAmount > creditAmount) {
                payAmount -= creditAmount;
                creditInvoice.setPayAmt(creditAmount);
                tempCreditList.remove(creditInvoice);
                remainList.add(creditInvoice);

            } else if(payAmount != 0.0 && payAmount == creditAmount) {
                payAmount -= creditAmount;
                creditInvoice.setPayAmt(creditAmount);
                tempCreditList.remove(creditInvoice);
                remainList.add(creditInvoice);
            }

        }

        Log.i("REMAIN -> ", payAmount + "");
        Log.i("Remain item -> ", remainList.size() + "");
        Log.i("item remove -> ", tempCreditList.size() + "");

        return remainList;
    }

    /**
     * Get Location Code
     *
     * @return location code
     */
    private int getLocationCode() {
        int locationCode = 0;
        String locationCodeName= null;
        Cursor cursorForLocation = database.rawQuery("select * from Location", null);
        while (cursorForLocation.moveToNext()) {
            locationCode = cursorForLocation.getInt(cursorForLocation.getColumnIndex(DatabaseContract.Location.id));
            locationCodeName = cursorForLocation.getString(cursorForLocation.getColumnIndex(DatabaseContract.Location.no));
        }
        return locationCode;
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
                tamount += creditInvoice.getAmt();
                tpaidAmount += creditInvoice.getPayAmt();
                tremainingAmount += creditInvoice.getCreditAmt();
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

            invId.setText(creditInv.getInvoiceNo());
            invdate.setText(creditInv.getInvoiceDate());
            invAmt.setText(Utils.formatAmount(creditInv.getAmt()));
            //statusTxt.setText(creditInv.getStatus());
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CreditCheckOut_Activity.this, CreditCollectActivity.class));
        finish();
    }
}
