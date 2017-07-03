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
import com.aceplus.samparoo.model.forApi.DisplayAssessment;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by aceplus_mobileteam on 6/23/17.
 */

public class DisplayProgramFragment extends Fragment {

    Spinner fromCustomerSpinner, toCustomerSpinner;
    EditText fromDateEditText, toDateEditText;
    Button search, clear;
    SQLiteDatabase database;
    List<String> customerNameArr;
    List<Integer> customerIdArr;
    Date fromDate, toDate;
    List<String> customerNoList;
    ListView dpListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_program_report, container, false);
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

    void registerIDs(View view) {
        fromCustomerSpinner = (Spinner) view.findViewById(R.id.fragment_dp_spinner_from_customer);
        toCustomerSpinner = (Spinner) view.findViewById(R.id.fragment_dp_spinner_to_customer);
        fromDateEditText = (EditText) view.findViewById(R.id.edit_text_dp_sale_report_from_date);
        toDateEditText = (EditText) view.findViewById(R.id.edit_text_dp_sale_report_to_date);
        search = (Button) view.findViewById(R.id.btn_dp_sale_report_search);
        clear = (Button) view.findViewById(R.id.btn_dp_sale_report_clear);
        dpListView = (ListView) view.findViewById(R.id.fragment_dp_lv);
    }

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

    void setupAdapter() {

        int fromPosition = fromCustomerSpinner.getSelectedItemPosition();
        int toPosition = toCustomerSpinner.getSelectedItemPosition();

        List<DisplayAssessment> dpList = new ArrayList<>();

        if (fromPosition > toPosition) {
            fromPosition = toCustomerSpinner.getSelectedItemPosition();
            toPosition = fromCustomerSpinner.getSelectedItemPosition();
        }

        if (fromPosition == toPosition) {
            DisplayAssessment displayAssessment = new DisplayAssessment();
            displayAssessment.setCustomerName(customerNameArr.get(fromPosition));
            displayAssessment.setCustomerNo(customerNoList.get(fromPosition));
            displayAssessment.setCustomerId(customerIdArr.get(fromPosition));
            dpList.add(displayAssessment);
        } else {
            for (int start = fromPosition; start <= toPosition; start++) {
                DisplayAssessment displayAssessment = new DisplayAssessment();
                displayAssessment.setCustomerName(customerNameArr.get(start));
                displayAssessment.setCustomerNo(customerNoList.get(start));
                displayAssessment.setCustomerId(customerIdArr.get(start));


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
                        displayAssessment.setInvoiceDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    }
                } else {
                    displayAssessment.setInvoiceDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                }

                dpList.add(displayAssessment);
            }
        }

        List<DisplayAssessment> dpList1 = getDisplayProgramListFromDB();

        for (DisplayAssessment dp : dpList) {
            for (DisplayAssessment dp1 : dpList1) {
                if (dp.getCustomerId() != null && dp1.getCustomerId() != null && dp.getCustomerId().equals(dp1.getCustomerId())) {
                    dp.setInvoiceNo(dp1.getInvoiceNo());

                    if(dp1.getInvoiceDate() != null || !dp1.getInvoiceDate().equals("")) {
                        dp.setInvoiceDate(dp1.getInvoiceDate().substring(0,10));
                    }

                    dp.setSaleManId(dp1.getSaleManId());
                    dp.setImage(dp1.getImage());
                    dp.setImageName(dp1.getImageName());
                    dp.setDateAndTime(dp1.getDateAndTime());
                    dp.setRemark(dp1.getRemark());
                    dp.setImage(dp1.getImage());
                }
            }
        }

        ArrayAdapter<DisplayAssessment> dpReportArrayAdapter = new DisplayProgramFragment.DisplayProgramReportArrayAdapter(getActivity(), dpList);
        dpListView.setAdapter(dpReportArrayAdapter);
    }

    void setupSpinner() {

        if (customerNameArr != null) {
            ArrayAdapter<String> customerAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, customerNameArr);
            customerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            fromCustomerSpinner.setAdapter(customerAdapter);
            toCustomerSpinner.setAdapter(customerAdapter);
        }
    }

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

    List<DisplayAssessment> getDisplayProgramListFromDB() {

        String query = "SELECT OV.* FROM OUTLET_VISIBILITY AS OV ";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<DisplayAssessment> dpList = new ArrayList<>();

        int fromCusNo = 0;
        int toCusNo = 0;

        fromCusNo = customerIdArr.get(fromCustomerSpinner.getSelectedItemPosition());
        toCusNo = customerIdArr.get(toCustomerSpinner.getSelectedItemPosition());

        if (fromCustomerSpinner.getSelectedItemPosition() > toCustomerSpinner.getSelectedItemPosition()) {
            fromCusNo = customerIdArr.get(toCustomerSpinner.getSelectedItemPosition());;
            toCusNo = customerIdArr.get(fromCustomerSpinner.getSelectedItemPosition());;
        }

        String customerCondition = "WHERE CUSTOMER_ID BETWEEN '" + fromCusNo + "' AND '" + toCusNo + "'";
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

        Cursor dpCursor = database.rawQuery(query, null);

        while (dpCursor.moveToNext()) {
            DisplayAssessment dp = new DisplayAssessment();
            dp.setInvoiceNo(dpCursor.getString(dpCursor.getColumnIndex("INVOICE_NO")));
            dp.setInvoiceDate(dpCursor.getString(dpCursor.getColumnIndex("INVOICE_DATE")));
            dp.setCustomerId(dpCursor.getInt(dpCursor.getColumnIndex("CUSTOMER_ID")));
            dp.setSaleManId(dpCursor.getInt(dpCursor.getColumnIndex("SALE_MAN_ID")));
            dp.setImage(dpCursor.getString(dpCursor.getColumnIndex("IMAGE")));
            dp.setImageName(dpCursor.getString(dpCursor.getColumnIndex("IMAGE_NAME")));
            dp.setDateAndTime(dpCursor.getString(dpCursor.getColumnIndex("DATE_AND_TIME")));
            dp.setRemark(dpCursor.getString(dpCursor.getColumnIndex("REMARK")));
            dp.setImage(dpCursor.getString(dpCursor.getColumnIndex("IMAGE_NO")));
            dpList.add(dp);
        }

        return dpList;
    }

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

    private class DisplayProgramReportArrayAdapter extends ArrayAdapter<DisplayAssessment> {

        public final Activity context;
        List<DisplayAssessment> dpList;

        public DisplayProgramReportArrayAdapter(Activity context, List<DisplayAssessment> dpList) {

            super(context, R.layout.list_row_display_program_report, dpList);
            this.context = context;
            this.dpList = dpList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_display_program_report, null, true);

            TextView dateTextView = (TextView) view.findViewById(R.id.adapter_dp_date);
            TextView customerNameTextView = (TextView) view.findViewById(R.id.adapter_dp_customerName);
            TextView statusTextView = (TextView) view.findViewById(R.id.adapter_dp_status);
            TextView imageNameTextView = (TextView) view.findViewById(R.id.adapter_dp_image_name);

            dateTextView.setText(dpList.get(position).getInvoiceDate());

            customerNameTextView.setText(dpList.get(position).getCustomerName());

            if (dpList.get(position).getImage() != null && !dpList.get(position).getImage().equals("")) {
                statusTextView.setText("Done");
            } else {
                statusTextView.setText("Not Yet Done");
            }

            imageNameTextView.setText(dpList.get(position).getImageName());

            return view;
        }
    }
}
