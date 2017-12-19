package com.aceplus.samparoo.report;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.IncentiveForUI;
import com.aceplus.samparoo.model.SaleVisitForUI;
import com.aceplus.samparoo.utils.Database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 10/10/2017.
 */

public class FragmentSaleVisitReport extends Fragment {
    Button searchBtn;
    Spinner fromCustomerSpinner, toCustomerSpinner;
    SQLiteDatabase database;
    List<String> customerNameArr;
    List<Integer> customerIdArr;
    List<String> customerNoList;
    List<String> customerAddressList;
    ListView saleVisitListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale_visit_report, container, false);
        database = new Database(getActivity()).getDataBase();
        registerIDs(view);
        customerNameArr = new ArrayList<>();
        customerNoList = new ArrayList<>();
        customerIdArr = new ArrayList<>();
        customerAddressList = new ArrayList<>();

        getCustomersFromDb();
        setupSpinner();
        catchEvents();
        return view;
    }

    void registerIDs(View view) {
        fromCustomerSpinner = (Spinner) view.findViewById(R.id.fragment_sale_visit_spinner_from_customer);
        toCustomerSpinner = (Spinner) view.findViewById(R.id.fragment_sale_visit_spinner_to_customer);
        searchBtn = (Button) view.findViewById(R.id.btn_sale_visit_search);
        saleVisitListView = (ListView) view.findViewById(R.id.saleVisitList);
    }

    /**
     * Get customers from database.
     */
    void getCustomersFromDb() {
        Cursor cursorCustomer = database.rawQuery("SELECT CUSTOMER_ID, CUSTOMER_NAME, id, ADDRESS FROM CUSTOMER", null);
        while (cursorCustomer.moveToNext()) {

            String customerId = cursorCustomer.getString(cursorCustomer.getColumnIndex("CUSTOMER_ID"));
            String customerName = cursorCustomer.getString(cursorCustomer.getColumnIndex("CUSTOMER_NAME"));
            int id = cursorCustomer.getInt(cursorCustomer.getColumnIndex("id"));
            String address = cursorCustomer.getString(cursorCustomer.getColumnIndex("ADDRESS"));

            customerNoList.add(customerId);
            customerNameArr.add(customerName);
            customerIdArr.add(id);
            customerAddressList.add(address);
        }
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

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupAdapter();
            }
        });
    }

    /**
     * Search display program.
     */
    void setupAdapter() {
        List<SaleVisitForUI> saleVisitList = new ArrayList<>();
        saleVisitList = getSaleVisitFromDB();
        for (SaleVisitForUI ip : saleVisitList) {
            int index = -1;
            for (int i = 0; i < customerIdArr.size(); i++) {
                if (ip.getCustomerId().equals(customerIdArr.get(i))) {
                    index = i;
                }
            }

            ip.setCustomerName(customerNameArr.get(index));
            ip.setCustomerNo(customerNoList.get(index));
            ip.setAddress(customerAddressList.get(index));
        }

        ArrayAdapter<SaleVisitForUI> dpReportArrayAdapter = new SaleVisitReportArrayAdapter(getActivity(), saleVisitList);
        saleVisitListView.setAdapter(dpReportArrayAdapter);
    }

    /**
     * Get incentive paid from database
     *
     * @return incentive paid result list
     */
    private List<SaleVisitForUI> getSaleVisitFromDB() {

        Map<Integer, SaleVisitForUI> visitedCustomerMap = new HashMap<>();

        String invoiceQuery = "SELECT CUSTOMER_ID FROM INVOICE AS IVC ";
        String preOrderQuery = "SELECT CUSTOMER_ID FROM PRE_ORDER AS PO ";
        String deliveryQuery = "SELECT CUSTOMER_ID FROM DELIVERY_UPLOAD AS D ";
        String returnQuery = "SELECT CUSTOMER_ID FROM SALE_RETURN AS SR ";
        String unsellQuery = "SELECT CUSTOMER_NO FROM DID_CUSTOMER_FEEDBACK AS DCF ";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<SaleVisitForUI> ipList = new ArrayList<>();

        int fromCusNo = 0;
        int toCusNo = 0;

        fromCusNo = customerIdArr.get(fromCustomerSpinner.getSelectedItemPosition());
        toCusNo = customerIdArr.get(toCustomerSpinner.getSelectedItemPosition());

        if (fromCustomerSpinner.getSelectedItemPosition() > toCustomerSpinner.getSelectedItemPosition()) {
            fromCusNo = customerIdArr.get(toCustomerSpinner.getSelectedItemPosition());
            toCusNo = customerIdArr.get(fromCustomerSpinner.getSelectedItemPosition());
        }

        String invoiceCustomerCondition = "WHERE IVC.CUSTOMER_ID BETWEEN '" + fromCusNo + "' AND '" + toCusNo + "' and date(IVC.SALE_DATE) = date('" + sdf.format(new Date()) + "') ";
        String preOrderCustomerCondition = "WHERE PO.CUSTOMER_ID BETWEEN '" + fromCusNo + "' AND '" + toCusNo + "' and date(PO.PREORDER_DATE) = date('" + sdf.format(new Date()) + "') ";
        String deliveryCustomerCondition = "WHERE D.CUSTOMER_ID BETWEEN '" + fromCusNo + "' AND '" + toCusNo + "' and date(D.INVOICE_DATE) = date('" + sdf.format(new Date()) + "') ";
        String returnCustomerCondition = "WHERE SR.CUSTOMER_ID BETWEEN '" + fromCusNo + "' AND '" + toCusNo + "' and date(SR.RETURNED_DATE) = date('" + sdf.format(new Date()) + "') ";
        String unsellCustomerCondition = "WHERE DCF.CUSTOMER_NO BETWEEN '" + fromCusNo + "' AND '" + toCusNo + "' and date(DCF.INV_DATE) = date('" + sdf.format(new Date()) + "') ";

        invoiceQuery += invoiceCustomerCondition;
        preOrderQuery += preOrderCustomerCondition;
        deliveryQuery += deliveryCustomerCondition;
        returnQuery += returnCustomerCondition;
        unsellQuery += unsellCustomerCondition;
/*
        String dateCondtion = " and date(IP.INVOICE_DATE) between date('" + sdf.format(new Date()) + "') ";
        query += dateCondtion;
*/

        Cursor invoiceCursor = database.rawQuery(invoiceQuery, null);
        while(invoiceCursor.moveToNext()) {
            Integer cusIdFromDb = invoiceCursor.getInt(invoiceCursor.getColumnIndex("CUSTOMER_ID"));
            for(int i=0; i < customerIdArr.size(); i++) {
                if(!visitedCustomerMap.containsKey(customerIdArr.get(i))) {
                    if(customerIdArr.get(i).equals(cusIdFromDb)) {
                        SaleVisitForUI saleVisitForUI = new SaleVisitForUI();
                        saleVisitForUI.setCustomerId(customerIdArr.get(i));
                        saleVisitForUI.setInvoiceDate(sdf.format(new Date()));
                        saleVisitForUI.setStatus("Visited");
                        saleVisitForUI.setTask("Sale");
                        visitedCustomerMap.put(cusIdFromDb, saleVisitForUI);
                    }
                } else {
                    String saleStatus = "";
                    if(!visitedCustomerMap.get(customerIdArr.get(i)).getTask().equals("Sale") && customerIdArr.get(i).equals(cusIdFromDb)) {
                        saleStatus = "Sale, " + visitedCustomerMap.get(customerIdArr.get(i)).getTask();
                        visitedCustomerMap.get(customerIdArr.get(i)).setTask(saleStatus);
                    }
                }
            }
        }
        invoiceCursor.close();

        Cursor preOrderCursor = database.rawQuery(preOrderQuery, null);
        while(preOrderCursor.moveToNext()) {
            Integer cusIdFromDb = preOrderCursor.getInt(preOrderCursor.getColumnIndex("CUSTOMER_ID"));
            for(int i=0; i < customerIdArr.size(); i++) {
                if(!visitedCustomerMap.containsKey(customerIdArr.get(i))) {
                    if(customerIdArr.get(i).equals(cusIdFromDb)) {
                        SaleVisitForUI saleVisitForUI = new SaleVisitForUI();
                        saleVisitForUI.setCustomerId(customerIdArr.get(i));
                        saleVisitForUI.setInvoiceDate(sdf.format(new Date()));
                        saleVisitForUI.setStatus("Visited");
                        saleVisitForUI.setTask("Order");
                        visitedCustomerMap.put(cusIdFromDb, saleVisitForUI);
                    }
                } else {
                    String saleStatus = "";
                    if(!visitedCustomerMap.get(customerIdArr.get(i)).getTask().equals("Order") && customerIdArr.get(i).equals(cusIdFromDb)) {
                        saleStatus = visitedCustomerMap.get(customerIdArr.get(i)).getTask() + ", Order";
                        visitedCustomerMap.get(customerIdArr.get(i)).setTask(saleStatus);
                    }
                }
            }
        }
        preOrderCursor.close();

        Cursor deliveryCursor = database.rawQuery(deliveryQuery, null);
        while(deliveryCursor.moveToNext()) {
            Integer cusIdFromDb = deliveryCursor.getInt(deliveryCursor.getColumnIndex("CUSTOMER_ID"));
            for(int i=0; i < customerIdArr.size(); i++) {
                if(!visitedCustomerMap.containsKey(customerIdArr.get(i))) {
                    if(customerIdArr.get(i).equals(cusIdFromDb)) {
                        SaleVisitForUI saleVisitForUI = new SaleVisitForUI();
                        saleVisitForUI.setCustomerId(customerIdArr.get(i));
                        saleVisitForUI.setInvoiceDate(sdf.format(new Date()));
                        saleVisitForUI.setStatus("Visited");
                        saleVisitForUI.setTask("Delivery");
                        visitedCustomerMap.put(cusIdFromDb, saleVisitForUI);
                    }
                } else {
                    String saleStatus = "";
                    if(!visitedCustomerMap.get(customerIdArr.get(i)).getTask().equals("Delivery") && customerIdArr.get(i).equals(cusIdFromDb)) {
                        saleStatus = visitedCustomerMap.get(customerIdArr.get(i)).getTask() + ", Delivery";
                        visitedCustomerMap.get(customerIdArr.get(i)).setTask(saleStatus);
                    }
                }
            }
        }
        deliveryCursor.close();

        Cursor returnCursor = database.rawQuery(returnQuery, null);
        while(returnCursor.moveToNext()) {
            Integer cusIdFromDb = returnCursor.getInt(returnCursor.getColumnIndex("CUSTOMER_ID"));
            for(int i=0; i < customerIdArr.size(); i++) {
                if(!visitedCustomerMap.containsKey(customerIdArr.get(i))) {
                    if(customerIdArr.get(i).equals(cusIdFromDb)) {
                        SaleVisitForUI saleVisitForUI = new SaleVisitForUI();
                        saleVisitForUI.setCustomerId(customerIdArr.get(i));
                        saleVisitForUI.setInvoiceDate(sdf.format(new Date()));
                        saleVisitForUI.setStatus("Visited");
                        saleVisitForUI.setTask("Return");
                        visitedCustomerMap.put(cusIdFromDb, saleVisitForUI);
                    }
                } else {
                    String saleStatus = "";
                    if(!visitedCustomerMap.get(customerIdArr.get(i)).getTask().equals("Return") && customerIdArr.get(i).equals(cusIdFromDb)) {
                        saleStatus = visitedCustomerMap.get(customerIdArr.get(i)).getTask() + ", Return";
                        visitedCustomerMap.get(customerIdArr.get(i)).setTask(saleStatus);
                    }
                }
            }
        }
        returnCursor.close();

        Cursor unseCursor = database.rawQuery(unsellQuery, null);
        while(unseCursor.moveToNext()) {
            Integer cusIdFromDb = unseCursor.getInt(unseCursor.getColumnIndex("CUSTOMER_NO"));
            for(int i=0; i < customerIdArr.size(); i++) {
                if(!visitedCustomerMap.containsKey(customerIdArr.get(i))) {
                    if(customerIdArr.get(i).equals(cusIdFromDb)) {
                        SaleVisitForUI saleVisitForUI = new SaleVisitForUI();
                        saleVisitForUI.setCustomerId(customerIdArr.get(i));
                        saleVisitForUI.setInvoiceDate(sdf.format(new Date()));
                        saleVisitForUI.setStatus("Visited");
                        saleVisitForUI.setTask("Unsell");
                        visitedCustomerMap.put(cusIdFromDb, saleVisitForUI);
                    }
                } else {
                    String saleStatus = "";
                    if(!visitedCustomerMap.get(customerIdArr.get(i)).getTask().equals("Unsell") && customerIdArr.get(i).equals(cusIdFromDb)) {
                        saleStatus = visitedCustomerMap.get(customerIdArr.get(i)).getTask() + ", Unsell";
                        visitedCustomerMap.get(customerIdArr.get(i)).setTask(saleStatus);
                    }
                }
            }
        }
        unseCursor.close();

        for (SaleVisitForUI value : visitedCustomerMap.values()){
            ipList.add(value);
        }

        return ipList;
    }

    /**
     * DisplayProgramReportArrayAdapter
     */
    private class SaleVisitReportArrayAdapter extends ArrayAdapter<SaleVisitForUI> {

        public final Activity context;
        List<SaleVisitForUI> ipList;

        public SaleVisitReportArrayAdapter(Activity context, List<SaleVisitForUI> ipList) {

            super(context, R.layout.list_row_sale_visit_report, ipList);
            this.context = context;
            this.ipList = ipList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_sale_visit_report, null, true);

            TextView addressTextView = (TextView) view.findViewById(R.id.adapter_sale_visit_address);
            TextView customerNameTextView = (TextView) view.findViewById(R.id.adapter_sale_visit_customerName);
            TextView statusTextView = (TextView) view.findViewById(R.id.adapter_sale_visit_status);
            TextView taskTextView = (TextView) view.findViewById(R.id.adapter_sale_visit_task);

            addressTextView.setText(ipList.get(position).getAddress());
            customerNameTextView.setText(ipList.get(position).getCustomerName());
            statusTextView.setText(ipList.get(position).getStatus());
            statusTextView.setText(ipList.get(position).getStatus());
            taskTextView.setText(ipList.get(position).getTask());

            return view;
        }
    }
}
