package com.aceplus.samparoo.report;

/**
 * Created by aceplus_mobileteam on 7/7/17.
 */

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
import com.aceplus.samparoo.model.IncentiveForUI;
import com.aceplus.samparoo.model.forApi.DisplayAssessment;
import com.aceplus.samparoo.model.forApi.IncentivePaidUploadData;
import com.aceplus.samparoo.utils.Database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentIncentiveProgramReport extends Fragment{

    Spinner fromCustomerSpinner, toCustomerSpinner;
    EditText fromDateEditText, toDateEditText;
    Button search, clear;
    SQLiteDatabase database;
    List<String> customerNameArr;
    List<Integer> customerIdArr;
    Date fromDate, toDate;
    List<String> customerNoList;
    ListView ipListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incentive_program, container, false);
        database = new Database(getActivity()).getDataBase();
        registerIDs(view);
        customerNameArr = new ArrayList<>();
        customerNoList = new ArrayList<>();
        customerIdArr = new ArrayList<>();
        getCustomersFromDb();
        setupSpinner();
        catchEvents();
        return view;
    }

    void registerIDs(View view) {
        fromCustomerSpinner = (Spinner) view.findViewById(R.id.fragment_incentive_report_spinner_from_customer);
        toCustomerSpinner = (Spinner) view.findViewById(R.id.fragment_incentive_report_spinner_to_customer);
        fromDateEditText = (EditText) view.findViewById(R.id.edit_text_incentive_report_from_date);
        toDateEditText = (EditText) view.findViewById(R.id.edit_text_incentive_report_to_date);
        search = (Button) view.findViewById(R.id.btn_fragment_incentive_report_search);
        clear = (Button) view.findViewById(R.id.btn_fragment_incentive_report_clear);
        ipListView = (ListView) view.findViewById(R.id.fragment_incentive_report_lv);
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
     * Search display program.
     */
    void setupAdapter() {

        int fromPosition = fromCustomerSpinner.getSelectedItemPosition();
        int toPosition = toCustomerSpinner.getSelectedItemPosition();

        List<IncentiveForUI> ipList = new ArrayList<>();

        if (fromPosition > toPosition) {
            fromPosition = toCustomerSpinner.getSelectedItemPosition();
            toPosition = fromCustomerSpinner.getSelectedItemPosition();
        }

        if (fromPosition == toPosition) {
            IncentiveForUI incentiveForUI = new IncentiveForUI();
            incentiveForUI.setCustomerName(customerNameArr.get(fromPosition));
            incentiveForUI.setCustomerNo(customerNoList.get(fromPosition));
            incentiveForUI.setCustomerId(customerIdArr.get(fromPosition));
            ipList.add(incentiveForUI);
        } else {
            for (int start = fromPosition; start <= toPosition; start++) {
                IncentiveForUI incentiveForUI = new IncentiveForUI();
                incentiveForUI.setCustomerName(customerNameArr.get(start));
                incentiveForUI.setCustomerNo(customerNoList.get(start));
                incentiveForUI.setCustomerId(customerIdArr.get(start));


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
                        incentiveForUI.setInvoiceDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    }
                } else {
                    incentiveForUI.setInvoiceDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                }

                ipList.add(incentiveForUI);
            }
        }

        List<IncentiveForUI> ipList1 = getDisplayProgramListFromDB();

        for (IncentiveForUI ip : ipList) {
            for (IncentiveForUI ip1 : ipList1) {
                if (ip.getCustomerId() != null && ip1.getCustomerId() != null && ip.getCustomerId().equals(ip1.getCustomerId())) {
                    ip.setInvoiceNo(ip1.getInvoiceNo());

                    if(ip1.getInvoiceDate() != null || !ip1.getInvoiceDate().equals("")) {
                        ip.setInvoiceDate(ip1.getInvoiceDate().substring(0,10));
                    }

                    ip.setStockId(ip1.getStockId());
                    ip.setIncentiveQuantity(ip1.getIncentiveQuantity());
                    ip.setPaidQuantity(ip1.getPaidQuantity());
                    ip.setSaleManId(ip1.getSaleManId());
                    ip.setIncentiveProgramName(ip1.getIncentiveProgramName());
                }
            }
        }

        ArrayAdapter<IncentiveForUI> dpReportArrayAdapter = new IncentiveProgramReportArrayAdapter(getActivity(), ipList);
        ipListView.setAdapter(dpReportArrayAdapter);
    }

    /**
     * Get incentive paid from database
     *
     * @return incentive paid result list
     */
    List<IncentiveForUI> getDisplayProgramListFromDB() {

        String query = "SELECT IP.*, I.INCENTIVE_PROGRAM_NAME FROM INCENTIVE_PAID AS IP LEFT JOIN INCENTIVE AS I ON I.INVOICE_NO = IP.INVOICE_NO ";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<IncentiveForUI> ipList = new ArrayList<>();

        int fromCusNo = 0;
        int toCusNo = 0;

        fromCusNo = customerIdArr.get(fromCustomerSpinner.getSelectedItemPosition());
        toCusNo = customerIdArr.get(toCustomerSpinner.getSelectedItemPosition());

        if (fromCustomerSpinner.getSelectedItemPosition() > toCustomerSpinner.getSelectedItemPosition()) {
            fromCusNo = customerIdArr.get(toCustomerSpinner.getSelectedItemPosition());;
            toCusNo = customerIdArr.get(fromCustomerSpinner.getSelectedItemPosition());;
        }

        String customerCondition = "WHERE IP.CUSTOMER_ID BETWEEN '" + fromCusNo + "' AND '" + toCusNo + "'";
        query += customerCondition;

        if (!fromDateEditText.getText().toString().equals("") || !toDateEditText.getText().toString().equals("")) {

            if (fromDateEditText.getText().toString().equals("")) {
                fromDateEditText.setError("Please enter START DATE");
            } else if (toDateEditText.getText().toString().equals("")) {
                toDateEditText.setError("Please enter END DATE");
            } else {
                if (!fromDateEditText.getText().toString().equals("") && !toDateEditText.getText().toString().equals("")) {
                    String dateCondtion = " and date(IP.INVOICE_DATE) between date('" + sdf.format(fromDate) + "') and date('" + sdf.format(toDate) + "')";
                    query += dateCondtion;
                }

            }
        }

        Cursor incentiveCursor = database.rawQuery(query, null);
        while(incentiveCursor.moveToNext()) {
            IncentiveForUI incentivePaidUploadData = new IncentiveForUI();
            incentivePaidUploadData.setInvoiceNo(incentiveCursor.getString(incentiveCursor.getColumnIndex("INVOICE_NO")));
            incentivePaidUploadData.setInvoiceDate(incentiveCursor.getString(incentiveCursor.getColumnIndex("INVOICE_DATE")));
            incentivePaidUploadData.setCustomerId(incentiveCursor.getInt(incentiveCursor.getColumnIndex("CUSTOMER_ID")));
            incentivePaidUploadData.setStockId(incentiveCursor.getInt(incentiveCursor.getColumnIndex("STOCK_ID")));
            incentivePaidUploadData.setIncentiveQuantity(incentiveCursor.getInt(incentiveCursor.getColumnIndex("QUANTITY")));
            incentivePaidUploadData.setPaidQuantity(incentiveCursor.getInt(incentiveCursor.getColumnIndex("PAID_QUANTITY")));
            incentivePaidUploadData.setSaleManId(incentiveCursor.getInt(incentiveCursor.getColumnIndex("SALE_MAN_ID")));
            incentivePaidUploadData.setIncentiveProgramName(incentiveCursor.getString(incentiveCursor.getColumnIndex("INCENTIVE_PROGRAM_NAME")));
            ipList.add(incentivePaidUploadData);
        }

        return ipList;
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
    private class IncentiveProgramReportArrayAdapter extends ArrayAdapter<IncentiveForUI> {

        public final Activity context;
        List<IncentiveForUI> ipList;

        public IncentiveProgramReportArrayAdapter(Activity context, List<IncentiveForUI> ipList) {

            super(context, R.layout.list_row_display_program_report, ipList);
            this.context = context;
            this.ipList = ipList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_display_program_report, null, true);

            TextView dateTextView = (TextView) view.findViewById(R.id.adapter_dp_date);
            TextView customerNameTextView = (TextView) view.findViewById(R.id.adapter_dp_customerName);
            TextView statusTextView = (TextView) view.findViewById(R.id.adapter_dp_status);
            TextView imageNameTextView = (TextView) view.findViewById(R.id.adapter_dp_image_name);

            dateTextView.setText(ipList.get(position).getInvoiceDate());

            customerNameTextView.setText(ipList.get(position).getCustomerName());

            if(ipList.get(position).getIncentiveQuantity() != null && ipList.get(position).getPaidQuantity() != null) {
                if (ipList.get(position).getIncentiveQuantity().equals(ipList.get(position).getPaidQuantity())) {
                    statusTextView.setText("Done");
                } else {
                    statusTextView.setText("Not Yet Done");
                }
            } else {
                statusTextView.setText("Not Yet Done");
            }

            imageNameTextView.setText(ipList.get(position).getIncentiveProgramName());

            return view;
        }
    }
}
