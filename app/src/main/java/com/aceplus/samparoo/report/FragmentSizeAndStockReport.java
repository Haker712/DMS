package com.aceplus.samparoo.report;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.forApi.SizeInStoreShare;
import com.aceplus.samparoo.model.forApi.SizeInStoreShare;
import com.aceplus.samparoo.utils.Database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by aceplus_mobileteam on 6/23/17.
 */

public class FragmentSizeAndStockReport extends Fragment {

    Spinner fromCustomerSpinner, toCustomerSpinner;
    EditText fromDateEditText, toDateEditText;
    Button search, clear;
    SQLiteDatabase database;
    List<String> customerNameArr;
    List<Integer> customerIdArr;
    Date fromDate, toDate;
    List<String> customerNoList;
    ListView ssListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_size_and_stock_report, container, false);
        database = new Database(getActivity()).getDataBase();
        /*database.beginTransaction();
        database.execSQL("DELETE FROM OUTLET_VISIBILITY");
        database.setTransactionSuccessful();
        database.endTransaction();*/

        registerIDs(view);
        customerNameArr = new ArrayList<>();
        customerNoList = new ArrayList<>();
        customerIdArr = new ArrayList<>();

        getCustomersFromDb();
        setupSpinner();
        catchEvents();
        return view;
    }

    /**
     * Register id of every widget in current layout.
     *
     * @param view current view
     */
    void registerIDs(View view) {
        fromCustomerSpinner = (Spinner) view.findViewById(R.id.fragment_ss_spinner_from_customer);
        toCustomerSpinner = (Spinner) view.findViewById(R.id.fragment_ss_spinner_to_customer);
        fromDateEditText = (EditText) view.findViewById(R.id.edit_text_ss_sale_report_from_date);
        toDateEditText = (EditText) view.findViewById(R.id.edit_text_ss_sale_report_to_date);
        search = (Button) view.findViewById(R.id.btn_ss_sale_report_search);
        clear = (Button) view.findViewById(R.id.btn_ss_sale_report_clear);
        ssListView = (ListView) view.findViewById(R.id.fragment_ss_lv);
    }

    /**
     * Get customers from database.
     */
    void getCustomersFromDb() {
        Cursor cursorCustomer = database.rawQuery("SELECT CUSTOMER_ID, CUSTOMER_NAME, id FROM CUSTOMER", null);
        while (cursorCustomer.moveToNext()) {

            String customerId = cursorCustomer.getString(cursorCustomer.getColumnIndex("CUSTOMER_ID"));
            String customerName = cursorCustomer.getString(cursorCustomer.getColumnIndex("CUSTOMER_NAME"));
            int id = cursorCustomer.getInt(cursorCustomer.getColumnIndex("id"));

            customerNoList.add(customerId);
            customerNameArr.add(customerName);
            customerIdArr.add(id);
        }
    }

    /**
     * Search display program.
     */
    void setupAdapter() {

        int fromPosition = fromCustomerSpinner.getSelectedItemPosition();
        int toPosition = toCustomerSpinner.getSelectedItemPosition();

        List<SizeInStoreShare> ssList = new ArrayList<>();

        if (fromPosition > toPosition) {
            fromPosition = toCustomerSpinner.getSelectedItemPosition();
            toPosition = fromCustomerSpinner.getSelectedItemPosition();
        }

        if (fromPosition == toPosition) {
            SizeInStoreShare SizeInStoreShare = new SizeInStoreShare();
            SizeInStoreShare.setCustomerName(customerNameArr.get(fromPosition));
            SizeInStoreShare.setCustomerNo(customerNoList.get(fromPosition));
            SizeInStoreShare.setCustomerId(customerIdArr.get(fromPosition));
            ssList.add(SizeInStoreShare);
        } else {
            for (int start = fromPosition; start <= toPosition; start++) {
                SizeInStoreShare SizeInStoreShare = new SizeInStoreShare();
                SizeInStoreShare.setCustomerName(customerNameArr.get(start));
                SizeInStoreShare.setCustomerNo(customerNoList.get(start));
                SizeInStoreShare.setCustomerId(customerIdArr.get(start));


                if(fromDate != null && toDate !=null) {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date1 = null, date2 = null, today = null;
                    try {
                        date1 = sdf.parse(sdf.format(fromDate));
                        date2 = sdf.parse(sdf.format(toDate));
                        today = sdf.parse(sdf.format(new Date()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    int from = date1.compareTo(today);
                    int to = date2.compareTo(today);

                    if(from >= 0 && to <= 0) {
                        SizeInStoreShare.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    }
                } else {
                    SizeInStoreShare.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                }

                ssList.add(SizeInStoreShare);
            }
        }

        List<SizeInStoreShare> ssList1 = getDisplayProgramListFromDB();

        for (SizeInStoreShare ss : ssList) {
            for (SizeInStoreShare ss1 : ssList1) {
                if (ss.getCustomerId() != null && ss1.getCustomerId() != null && ss.getCustomerId().equals(ss1.getCustomerId())) {
                    ss.setSizeInStoreShareNo(ss1.getSizeInStoreShareNo());

                    if(ss1.getDate() != null || !ss1.getDate().equals("")) {
                        ss.setDate(ss1.getDate().substring(0,10));
                    }

                    ss.setSalemanId(ss1.getSalemanId());
                    ss.setStockId(ss1.getStockId());
                    ss.setQuantity(ss1.getQuantity());
                    ss.setStatus(ss1.getStatus());
                    ss.setRemark(ss1.getRemark());
                }
            }
        }

        ArrayAdapter<SizeInStoreShare> ssReportArrayAdapter = new SizeAndStockReportArrayAdapter(getActivity(), ssList);
        ssListView.setAdapter(ssReportArrayAdapter);
    }

    /**
     * Set customer data to spinner
     */
    void setupSpinner() {

        if (customerNameArr != null) {
            ArrayAdapter<String> customerAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, customerNameArr);
            customerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            fromCustomerSpinner.setAdapter(customerAdapter);
            toCustomerSpinner.setAdapter(customerAdapter);
        }
    }

    /**
     * Events listeners for view in current layout
     */
    void catchEvents() {

        fromDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDob(1);
            }
        });

        toDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDob(2);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!fromDateEditText.getText().toString().equals("") || !toDateEditText.getText().toString().equals("")) {

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date1 = null, date2 = null;
                    try {
                        date1 = sdf.parse(sdf.format(fromDate));
                        date2 = sdf.parse(sdf.format(toDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (fromDateEditText.getText().toString().equals("")) {
                        fromDateEditText.setError("Please enter START DATE");
                        return;
                    } else if (toDateEditText.getText().toString().equals("")) {
                        toDateEditText.setError("Please enter END DATE");
                        return;
                    } else if(date1.compareTo(date2) > 0) {
                        toDateEditText.setError("END DATE is earlier than START DATE");
                        return;
                    }

                }

                setupAdapter();
                fromDateEditText.setError(null);
                toDateEditText.setError(null);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDateEditText.setError(null);
                toDateEditText.setError(null);
                fromDateEditText.setText("");
                toDateEditText.setText("");
            }
        });
    }

    /**
     * Get display program from database
     *
     * @return SizeInStoreShare result list
     */
    List<SizeInStoreShare> getDisplayProgramListFromDB() {

        String query = "SELECT * FROM size_in_store_share ";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<SizeInStoreShare> ssList = new ArrayList<>();

        int fromCusNo = 0;
        int toCusNo = 0;

        fromCusNo = customerIdArr.get(fromCustomerSpinner.getSelectedItemPosition());
        toCusNo = customerIdArr.get(toCustomerSpinner.getSelectedItemPosition());

        if (fromCustomerSpinner.getSelectedItemPosition() > toCustomerSpinner.getSelectedItemPosition()) {
            fromCusNo = customerIdArr.get(toCustomerSpinner.getSelectedItemPosition());;
            toCusNo = customerIdArr.get(fromCustomerSpinner.getSelectedItemPosition());;
        }

        String customerCondition = "WHERE customer_id BETWEEN " + fromCusNo + " AND " + toCusNo + "";
        query += customerCondition;

        if (!fromDateEditText.getText().toString().equals("") || !toDateEditText.getText().toString().equals("")) {

            if (fromDateEditText.getText().toString().equals("")) {
                fromDateEditText.setError("Please enter START DATE");
            } else if (toDateEditText.getText().toString().equals("")) {
                toDateEditText.setError("Please enter END DATE");
            } else {
                if (!fromDateEditText.getText().toString().equals("") && !toDateEditText.getText().toString().equals("")) {
                    String dateCondtion = " and date(INVOICE_DATE) between date('" + sdf.format(fromDate) + "') and date('" + sdf.format(toDate) + "')";
                    query += dateCondtion;
                }

            }
        }

        Cursor ssCursor = database.rawQuery(query, null);

        while (ssCursor.moveToNext()) {
            SizeInStoreShare ss = new SizeInStoreShare();
            ss.setSizeInStoreShareNo(ssCursor.getString(ssCursor.getColumnIndex("size_in_store_share_id")));
            ss.setDate(ssCursor.getString(ssCursor.getColumnIndex("INVOICE_DATE")));
            ss.setCustomerId(ssCursor.getInt(ssCursor.getColumnIndex("customer_id")));
            ss.setSalemanId(ssCursor.getInt(ssCursor.getColumnIndex("saleman_id")));
            ss.setStockId(ssCursor.getInt(ssCursor.getColumnIndex("stock_id")));
            ss.setQuantity(ssCursor.getInt(ssCursor.getColumnIndex("quantity")));
            ss.setStatus(ssCursor.getString(ssCursor.getColumnIndex("status")));
            ss.setRemark(ssCursor.getString(ssCursor.getColumnIndex("remark")));
            ssList.add(ss);
        }

        return ssList;
    }

    /**
     * set date to date chooser dialog.
     *
     * @param choice 1: start date; 2: end date
     */
    void chooseDob(final int choice) {

        final Calendar myCalendar = Calendar.getInstance();
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                if (choice == 1) {
                    fromDateEditText.setText(sdf.format(myCalendar.getTime()));
                    fromDate = myCalendar.getTime();
                } else if (choice == 2) {
                    toDateEditText.setText(sdf.format(myCalendar.getTime()));
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

    /**
     * DisplayProgramReportArrayAdapter
     */
    private class SizeAndStockReportArrayAdapter extends ArrayAdapter<SizeInStoreShare> {

        public final Activity context;
        List<SizeInStoreShare> ssList;

        public SizeAndStockReportArrayAdapter(Activity context, List<SizeInStoreShare> ssList) {

            super(context, R.layout.list_row_display_program_report, ssList);
            this.context = context;
            this.ssList = ssList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_display_program_report, null, true);

            TextView dateTextView = (TextView) view.findViewById(R.id.adapter_dp_date);
            TextView customerNameTextView = (TextView) view.findViewById(R.id.adapter_dp_customerName);
            TextView statusTextView = (TextView) view.findViewById(R.id.adapter_dp_status);
            TextView imageNameTextView = (TextView) view.findViewById(R.id.adapter_dp_image_name);

            dateTextView.setText(ssList.get(position).getDate());

            customerNameTextView.setText(ssList.get(position).getCustomerName());

            if (ssList.get(position).getStatus() != null && !ssList.get(position).getStatus().equals("")) {
                statusTextView.setText("Done");

                if(ssList.get(position).getStatus().equals("A")) {
                    imageNameTextView.setText("Amount");
                } else if(ssList.get(position).getStatus().equals("P")) {
                    imageNameTextView.setText("Percent");
                } else if(ssList.get(position).getStatus().equals("Q")) {
                    imageNameTextView.setText("Quantity");
                }
            } else {
                statusTextView.setText("Not Yet Done");
            }

            return view;
        }
    }
}
