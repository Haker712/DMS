package com.aceplus.samparoo.report;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;


import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Saleinvoicedetail;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressLint("NewApi")
public class FragmentSaleOrderHistoryReport extends Fragment {

    ListView saleInvoiceReportsListView;

    ArrayList<JSONObject> saleOrderHistoryReportsArrayList = new ArrayList<>();

    ArrayList<JSONObject> customerReportsArrayList = new ArrayList<>();

    int saleHistory = 0;

    JSONObject saleOrderHistoryReportJsonObject;

    SQLiteDatabase database;

    String invoice_Id;

    String quantity;
    Double dicount_amount;
    Double total_amount;
    Spinner customerSpinner;
    Saleinvoicedetail saleinvoicedetail;
    List<Saleinvoicedetail> saleinvoicedetailList = new ArrayList<Saleinvoicedetail>();

    String ALL_CUSTOMER = "All";

    Calendar myCalendar = Calendar.getInstance();

    EditText fromDateEditTxt, toDateEditTxt;

    SimpleDateFormat sdf;

    Date fromDate, toDate;

    Button searchBtn, clearBtn;

    TextView sale_order_history_report_total, sale_order_history_report_discount, sale_order_history_report_net, sale_report_discount_advance_amt_label, sale_order_report_advance_amt;

    TableRow table_row_advance_amt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sale_invoice_report, container, false);

        database = new Database(getActivity()).getDataBase();

        sdf = new SimpleDateFormat("yyyy/MM/dd");

        saleInvoiceReportsListView = (ListView) view.findViewById(R.id.saleInvoceReports);
        customerSpinner = (Spinner) view.findViewById(R.id.customer_spinner_fragment_invoice_report);
        fromDateEditTxt = (EditText) view.findViewById(R.id.edit_text_sale_report_from_date);
        toDateEditTxt = (EditText) view.findViewById(R.id.edit_text_sale_report_to_date);
        searchBtn = (Button) view.findViewById(R.id.btn_sale_report_search);
        clearBtn = (Button) view.findViewById(R.id.btn_sale_report_clear);
        sale_order_history_report_total = (TextView) view.findViewById(R.id.sale_report_total_amt);
        sale_order_history_report_discount = (TextView) view.findViewById(R.id.sale_report_discount);
        sale_order_history_report_net = (TextView) view.findViewById(R.id.sale_report_net_amt);
        sale_report_discount_advance_amt_label = (TextView) view.findViewById(R.id.sale_order_report_advanced_amount_label);
        table_row_advance_amt = (TableRow) view.findViewById(R.id.table_row_advance_amt);
        sale_order_report_advance_amt = (TextView) view.findViewById(R.id.sale_order_report_advanced_amt);

        table_row_advance_amt.setVisibility(View.VISIBLE);
        sale_report_discount_advance_amt_label.setVisibility(View.VISIBLE);
        List<String> customerNameArr = new ArrayList<>();
        if (customerSpinner != null) {
            try {
                JSONObject allCustomer = new JSONObject();
                allCustomer.put("customerId", ALL_CUSTOMER);
                allCustomer.put("customerName", "ALL");
                customerReportsArrayList.add(0,allCustomer);

                for (JSONObject customerName : customerReportsArrayList) {
                    customerNameArr.add(customerName.getString("customerName"));
                }
                saleOrderHistoryReportsArrayList = getSaleInvoiceReports(ALL_CUSTOMER);
                double[] amountArr = Utils.calculateReportAmounts(saleOrderHistoryReportsArrayList, false);
                sale_order_history_report_total.setText(Utils.formatAmount(amountArr[0]));
                sale_order_history_report_discount.setText(Utils.formatAmount(amountArr[1]));
                sale_order_history_report_net.setText(Utils.formatAmount(amountArr[2]));
                sale_order_report_advance_amt.setText(Utils.formatAmount(amountArr[3]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayAdapter<String> customerAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, customerNameArr);
            customerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            customerSpinner.setAdapter(customerAdapter);
        }

        fromDateEditTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDob(1);
            }
        });

        toDateEditTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDob(2);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedCustomerPosition = customerSpinner.getSelectedItemPosition();
                setDataToSaleReportAdapter(selectedCustomerPosition);
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDateEditTxt.setText("");
                fromDateEditTxt.setError(null);
                toDateEditTxt.setText("");
                toDateEditTxt.setError(null);
            }
        });

        /*customerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setDataToSaleReportAdapter(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        ArrayAdapter<JSONObject> saleInvoiceReportsArrayAdapter = new SaleInvoiceReportsArrayAdapter(getActivity());
        saleInvoiceReportsListView.setAdapter(saleInvoiceReportsArrayAdapter);

        saleInvoiceReportsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                saleOrderHistoryReportJsonObject = saleOrderHistoryReportsArrayList.get(position);

                try {
                    invoice_Id = saleOrderHistoryReportJsonObject.getString("invoiceId");
                    Log.i("invoice_Id", invoice_Id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                saleinvoicedetailList.clear();

                Cursor cursor_Invoice_Id = database.rawQuery("SELECT * FROM PRE_ORDER_PRODUCT WHERE SALE_ORDER_ID='" + invoice_Id + "'", null);
                while (cursor_Invoice_Id.moveToNext()) {

                    saleinvoicedetail = new Saleinvoicedetail();

                    String produc_Id = cursor_Invoice_Id.getString(cursor_Invoice_Id.getColumnIndex("PRODUCT_ID"));
                    Log.i("product_Id", produc_Id + "aaa");
                    quantity = cursor_Invoice_Id.getString(cursor_Invoice_Id.getColumnIndex("ORDER_QTY"));
                    //String discountAmt = cursor_Invoice_Id.getString(cursor_Invoice_Id.getColumnIndex("DISCOUNT_AMOUNT"));
                    String totalAmt = cursor_Invoice_Id.getString(cursor_Invoice_Id.getColumnIndex("TOTAL_AMT"));

                    /*if(discountAmt != null && !discountAmt.equals("")) {
                        dicount_amount = Double.valueOf(cursor_Invoice_Id.getString(cursor_Invoice_Id.getColumnIndex("DISCOUNT_AMOUNT")));
                    } else {
                        dicount_amount = 0.0;
                    }*/


                    if(totalAmt != null && !totalAmt.equals("")) {
                        total_amount = Double.valueOf(totalAmt);
                    } else {
                        total_amount = 0.0;
                    }

                    Cursor cursor_product_Id = database.rawQuery("SELECT * FROM PRODUCT WHERE ID =" + produc_Id, null);
                    Log.i("cur_count", cursor_product_Id.getCount() + "");

                    String product_name = "";
                    while (cursor_product_Id.moveToNext()) {
                        product_name = cursor_product_Id.getString(cursor_product_Id.getColumnIndex("PRODUCT_NAME"));
                        Log.i("product_name", cursor_product_Id.getString(cursor_product_Id.getColumnIndex("PRODUCT_NAME")) + " aaa");
                    }
                    saleinvoicedetail.setProductName(product_name);
                    saleinvoicedetail.setQuantity(quantity);
                    saleinvoicedetail.setDiscountAmount(dicount_amount);
                    saleinvoicedetail.setTotalAmount(total_amount);
                    saleinvoicedetailList.add(saleinvoicedetail);
                }

                View dialogBoxView = getActivity().getLayoutInflater().inflate(R.layout.dialog_box_sale_invoice_report, null);
                saleInvoiceReportsListView = (ListView) dialogBoxView.findViewById(R.id.saleinvoicedialog);
                TextView discountTxtViewForDialongSaleReport = (TextView) dialogBoxView.findViewById(R.id.discount);
                discountTxtViewForDialongSaleReport.setVisibility(View.GONE);

                saleInvoiceReportsListView.setAdapter(new SaleInvoiceArrayAdapter(getActivity()));
                new AlertDialog.Builder(getActivity())
                        .setView(dialogBoxView)
                        .setTitle("Sale Order Products")
                        .setPositiveButton("OK", null)
                        .show();

            }
        });
        return view;
    }

    private void setDataToSaleReportAdapter(int position) {
        try {
            fromDateEditTxt.setError(null);
            toDateEditTxt.setError(null);
            saleOrderHistoryReportsArrayList.clear();
            saleOrderHistoryReportsArrayList.addAll(getSaleInvoiceReports(customerReportsArrayList.get(position).getString("customerId")));
            ArrayAdapter<JSONObject> saleInvoiceReportsArrayAdapter = new SaleInvoiceReportsArrayAdapter(getActivity());
            saleInvoiceReportsListView.setAdapter(saleInvoiceReportsArrayAdapter);
            saleInvoiceReportsArrayAdapter.notifyDataSetChanged();
            double[] amountArr = Utils.calculateReportAmounts(saleOrderHistoryReportsArrayList, false);
            sale_order_history_report_total.setText(Utils.formatAmount(amountArr[0]));
            sale_order_history_report_discount.setText(Utils.formatAmount(amountArr[1]));
            sale_order_history_report_net.setText(Utils.formatAmount(amountArr[2]));
            sale_order_report_advance_amt.setText(Utils.formatAmount(amountArr[3]));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            saleOrderHistoryReportsArrayList.clear();
            saleOrderHistoryReportsArrayList.addAll(getSaleInvoiceReports(customerReportsArrayList.get(customerSpinner.getSelectedItemPosition()).getString("customerId")));
            ArrayAdapter<JSONObject> saleInvoiceReportsArrayAdapter = new SaleInvoiceReportsArrayAdapter(getActivity());
            saleInvoiceReportsListView.setAdapter(saleInvoiceReportsArrayAdapter);
            saleInvoiceReportsArrayAdapter.notifyDataSetChanged();
            double[] amountArr = Utils.calculateReportAmounts(saleOrderHistoryReportsArrayList, false);
            sale_order_history_report_total.setText(Utils.formatAmount(amountArr[0]));
            sale_order_history_report_discount.setText(Utils.formatAmount(amountArr[1]));
            sale_order_history_report_net.setText(Utils.formatAmount(amountArr[2]));
            sale_order_report_advance_amt.setText(Utils.formatAmount(amountArr[3]));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class SaleInvoiceReportsArrayAdapter extends ArrayAdapter<JSONObject> {

        public final Activity context;

        public SaleInvoiceReportsArrayAdapter(Activity context) {

            super(context, R.layout.list_row_sale_invoice_report, saleOrderHistoryReportsArrayList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            saleOrderHistoryReportJsonObject = saleOrderHistoryReportsArrayList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_sale_invoice_report, null, true);

            TextView invoiceIdTextView = (TextView) view.findViewById(R.id.invoiceId);
            TextView customerNameTextView = (TextView) view.findViewById(R.id.customerName);
            TextView addressTextView = (TextView) view.findViewById(R.id.address);
            TextView totalAmountTextView = (TextView) view.findViewById(R.id.totalAmount);
            TextView discountTextView = (TextView) view.findViewById(R.id.discount);
            TextView advanceAmtTextView = (TextView) view.findViewById(R.id.advance_amt);
            TextView netAmountTextView = (TextView) view.findViewById(R.id.netAmount);
            advanceAmtTextView.setVisibility(View.VISIBLE);
            try {
                invoiceIdTextView.setText(saleOrderHistoryReportJsonObject.getString("invoiceId"));
                customerNameTextView.setText(saleOrderHistoryReportJsonObject.getString("customerName"));
                addressTextView.setText(saleOrderHistoryReportJsonObject.getString("address"));
                totalAmountTextView.setText(Utils.formatAmount(saleOrderHistoryReportJsonObject.getDouble("totalAmount")));
                discountTextView.setText(Utils.formatAmount(saleOrderHistoryReportJsonObject.getDouble("discount")));
                advanceAmtTextView.setText(Utils.formatAmount(saleOrderHistoryReportJsonObject.getDouble("advancedPaymentAmount")));
                netAmountTextView.setText(Utils.formatAmount(saleOrderHistoryReportJsonObject.getDouble("netAmount")));
            } catch (JSONException e) {

                e.printStackTrace();
            }
            return view;
        }
    }

    private class SaleInvoiceArrayAdapter extends ArrayAdapter<Saleinvoicedetail> {

        public final Activity context;

        public SaleInvoiceArrayAdapter(Activity context) {

            super(context, R.layout.list_row_sale_invoice_detail, saleinvoicedetailList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Saleinvoicedetail saleinvoicedetail1 = saleinvoicedetailList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_sale_invoice_detail, null, true);

            TextView productNameTextView = (TextView) view.findViewById(R.id.sidproductName);
            TextView quantityTextView = (TextView) view.findViewById(R.id.sidquantity);
            TextView discountTextview = (TextView) view.findViewById(R.id.siddiscount);
            TextView totalAmountTextView = (TextView) view.findViewById(R.id.sidamount);

            discountTextview.setVisibility(View.GONE);
            productNameTextView.setText(saleinvoicedetail1.getProductName());
            quantityTextView.setText(saleinvoicedetail1.getQuantity());
            //discountTextview.setText(saleinvoicedetail1.getDiscountAmount() + "");
            totalAmountTextView.setText(saleinvoicedetail1.getTotalAmount() + "");

            return view;
        }
    }

    private ArrayList<JSONObject> getSaleInvoiceReports(String customerId) {

        ArrayList<JSONObject> saleInvoiceReportsArrayList = new ArrayList<JSONObject>();

        String query = "SELECT * FROM PRE_ORDER where SALE_FLAG = " + saleHistory + " ";
        String customerCondition = "and CUSTOMER_ID = '" + customerId + "' ";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if(!customerId.equals(ALL_CUSTOMER)) {
            query += customerCondition;
        }

        if(!fromDateEditTxt.getText().toString().equals("") || !toDateEditTxt.getText().toString().equals("")) {

            if(fromDateEditTxt.getText().toString().equals("")) {
                fromDateEditTxt.setError("Please enter START DATE");
            } else if(toDateEditTxt.getText().toString().equals("")) {
                toDateEditTxt.setError("Please enter END DATE");
            } else {
                String dateCondtion = "and date(PREORDER_DATE) between date('" + sdf.format(fromDate) + "') and date('" + sdf.format(toDate) + "')";
                query += dateCondtion;
            }
        }

        Cursor cursor = database.rawQuery(query, null);

        while (cursor.moveToNext()) {

            JSONObject saleInvoiceReportJsonObject = new JSONObject();
            try {

                saleInvoiceReportJsonObject.put("invoiceId", cursor.getString(cursor.getColumnIndex("INVOICE_ID")));

                Cursor cursorForCustomer = database.rawQuery(
                        "SELECT CUSTOMER_NAME, ADDRESS FROM CUSTOMER"
                                + " WHERE id = \"" + cursor.getString(cursor.getColumnIndex("CUSTOMER_ID")) + "\""
                        , null);
                if (cursorForCustomer.moveToNext()) {

                    saleInvoiceReportJsonObject.put("customerName"
                            , cursorForCustomer.getString(cursorForCustomer.getColumnIndex("CUSTOMER_NAME")));
                    saleInvoiceReportJsonObject.put("address"
                            , cursorForCustomer.getString(cursorForCustomer.getColumnIndex("ADDRESS")));

                    double totalAmount = cursor.getDouble(cursor.getColumnIndex("NET_AMOUNT"));
                    double advancedPaymentAmount = cursor.getDouble(cursor.getColumnIndex("ADVANCE_PAYMENT_AMOUNT"));
                    double discount = cursor.getDouble(cursor.getColumnIndex("DISCOUNT"));
                    Log.i("advancedPaymentAmount :", advancedPaymentAmount + "");
                    saleInvoiceReportJsonObject.put("totalAmount", totalAmount);
                    saleInvoiceReportJsonObject.put("discount", discount);
                    saleInvoiceReportJsonObject.put("advancedPaymentAmount", advancedPaymentAmount);
                    saleInvoiceReportJsonObject.put("netAmount", totalAmount - advancedPaymentAmount - discount);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            saleInvoiceReportsArrayList.add(saleInvoiceReportJsonObject);
        }

        return saleInvoiceReportsArrayList;
    }

    void chooseDob(final int choice) {

        DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                if(choice == 1) {
                    fromDateEditTxt.setText(sdf.format(myCalendar.getTime()));
                    fromDate = myCalendar.getTime();
                } else if(choice == 2) {
                    toDateEditTxt.setText(sdf.format(myCalendar.getTime()));
                    toDate = myCalendar.getTime();
                }
            }
        };

        DatePickerDialog dateDialog = new DatePickerDialog(this.getContext(),
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        dateDialog.show();
    }
}
