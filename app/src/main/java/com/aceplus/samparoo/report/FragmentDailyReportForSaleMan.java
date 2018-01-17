package com.aceplus.samparoo.report;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aceplus.samparoo.LoginActivity;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.SaleManDailyReport;
import com.aceplus.samparoo.utils.Constant;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 1/11/2018.
 */

public class FragmentDailyReportForSaleMan extends Fragment {

    TextView textView_saleman_name, textView_route, textView_date, textView_startTime, textView_endTime, textView_totalSale, textView_totalExRe, textView_cashRecipt, textView_netCash, textView_totalCust, textView_saleCount, textView_orderCount, textView_saleReturn, textView_cashReciptCount, textView_notVisitedCount;

    SQLiteDatabase database;

    int totalCustomer = 0;
    int saleCount = 0;
    int orderCount = 0;
    int saleReturnCount = 0;
    int cashReceiptCount = 0;
    int notVisitedCount = 0;
    int totalNotVisitedCount = 0;
    double totalSaleAmt, totalReturnAmt, totalPayAmt, netAmt;

    SaleManDailyReport saleManDailyReport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        totalCustomer = 0;
        saleCount = 0;
        orderCount = 0;
        saleReturnCount = 0;
        cashReceiptCount = 0;
        notVisitedCount = 0;
        totalNotVisitedCount = 0;

        database = new Database(getActivity()).getDataBase();
        View view = inflater.inflate(R.layout.fragment_daily_report_for_sale_man, container, false);
        textView_saleman_name = (TextView) view.findViewById(R.id.fragment_daily_report_sale_man);
        textView_route = (TextView) view.findViewById(R.id.fragment_daily_report_route);
        textView_date = (TextView) view.findViewById(R.id.fragment_daily_report_date);
        textView_startTime = (TextView) view.findViewById(R.id.fragment_daily_report_start_time);
        textView_endTime = (TextView) view.findViewById(R.id.fragment_daily_report_end_time);
        textView_totalSale = (TextView) view.findViewById(R.id.fragment_daily_report_total_sale);
        textView_totalExRe = (TextView) view.findViewById(R.id.fragment_daily_report_total_exchange_return);
        textView_cashRecipt = (TextView) view.findViewById(R.id.fragment_daily_report_total_cash_receipt);
        textView_netCash = (TextView) view.findViewById(R.id.fragment_daily_report_net_cash);
        textView_totalCust = (TextView) view.findViewById(R.id.fragment_daily_report_total_customer);
        textView_saleCount = (TextView) view.findViewById(R.id.fragment_daily_report_total_sale_count);
        textView_orderCount = (TextView) view.findViewById(R.id.fragment_daily_report_total_order_count);
        textView_saleReturn = (TextView) view.findViewById(R.id.fragment_daily_report_sale_return);
        textView_cashReciptCount = (TextView) view.findViewById(R.id.fragment_daily_report_total_cash_receipt_count);
        textView_notVisitedCount = (TextView) view.findViewById(R.id.fragment_daily_report_not_visited_count);

        ImageView printImg = (ImageView) getActivity().findViewById(R.id.print_img);

        printImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saleManDailyReport = new SaleManDailyReport();
                saleManDailyReport.setSaleMan(textView_saleman_name.getText().toString());
                saleManDailyReport.setRouteName(textView_route.getText().toString());
                saleManDailyReport.setDate(textView_date.getText().toString());
                saleManDailyReport.setStartTime("8:30");
                saleManDailyReport.setEndTime("5:30");
                saleManDailyReport.setSaleAmt(totalSaleAmt);
                saleManDailyReport.setReturnAmt(totalReturnAmt);
                saleManDailyReport.setCashReceive(totalPayAmt);
                saleManDailyReport.setNetAmt(netAmt);
                saleManDailyReport .setCustomerCount(totalCustomer);
                saleManDailyReport.setSaleCount(saleCount);
                saleManDailyReport.setOrderCount(orderCount);
                saleManDailyReport.setReturnCount(saleReturnCount);
                saleManDailyReport.setCashReceiveCount(cashReceiptCount);
                saleManDailyReport.setNotVisitCount(totalNotVisitedCount);
                Utils.printDailyReportForSaleMan(getActivity(), saleManDailyReport);

                Toast.makeText(getContext(), "Print img Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        if (LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NAME, "") != null) {
            textView_saleman_name.setText(LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NAME, ""));
        }

        Integer saleManId = null;
        if (LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, "") != null) {
            saleManId = Integer.parseInt(LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, ""));
        }

        textView_route.setText(getRouteName(saleManId));

        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(today);

        textView_date.setText(currentDate);
        // need to ask strat time and end time
        //textView_startTime.setText("");
        //textView_endTime.setText("");
        totalSaleAmt = 0;
        totalSaleAmt  = getTodayTotalSaleAmount();
        textView_totalSale.setText(Utils.formatAmount(totalSaleAmt)+ "");

        totalReturnAmt = 0;
        totalReturnAmt = getTodayTotalSaleReturn();
        textView_totalExRe.setText(Utils.formatAmount(totalReturnAmt) + "");

        totalPayAmt = 0;
        totalPayAmt = getTodayPayAmt();
        textView_cashRecipt.setText(Utils.formatAmount(totalPayAmt) + "");

        netAmt = 0;
        netAmt = totalPayAmt + totalSaleAmt - totalReturnAmt;
        textView_netCash.setText(Utils.formatAmount(netAmt) + "");

        totalCustomer = getCustomerCount();
        totalNotVisitedCount = getNotVisitedCount();

        textView_totalCust.setText(totalCustomer + "");
        textView_saleCount.setText(saleCount + "");
        textView_orderCount.setText(orderCount + "");
        textView_saleReturn.setText(saleReturnCount + "");
        textView_cashReciptCount.setText(cashReceiptCount + "");
        textView_notVisitedCount.setText(totalNotVisitedCount + "");

        return view;
    }

    int getNotVisitedCount() {

        List<Integer> customerIdList = new ArrayList<>();
        Cursor idCursor = database.rawQuery("SELECT id from CUSTOMER", null);

        while (idCursor.moveToNext()) {
            Integer id = idCursor.getInt(idCursor.getColumnIndex("id"));
            customerIdList.add(id);
        }
        idCursor.close();

        Cursor visitedCountCursor = database.rawQuery("SELECT CUSTOMER_ID FROM SALE_VISIT_RECORD_UPLOAD WHERE VISIT_FLG = 1", null);
        while (visitedCountCursor.moveToNext()) {
            Integer visitId = visitedCountCursor.getInt(visitedCountCursor.getColumnIndex("CUSTOMER_ID"));

            if (visitId != null) {
                for (int i = 0; i < customerIdList.size(); i++) {
                    if (visitId.equals(customerIdList.get(i))) {
                        customerIdList.remove(i);
                        break;
                    }
                }

            }
        }
        visitedCountCursor.close();

        return customerIdList.size();
    }

    int getCustomerCount() {
        Cursor customerCountCursor = database.rawQuery("SELECT * FROM CUSTOMER", null);
        int count = 0;
        if (customerCountCursor.moveToNext()) {
            count = customerCountCursor.getCount();
        }
        customerCountCursor.close();
        return count;
    }

    String getRouteName(Integer saleManId) {
        String routeName = "";

        Integer routeId = 0;
        Cursor routeIdCursor = database.rawQuery("select RouteId from routeScheduleItem where SaleManId = '" + saleManId + "'", null);
        while (routeIdCursor.moveToNext()) {
            routeId = routeIdCursor.getInt(routeIdCursor.getColumnIndex("RouteId"));
        }
        routeIdCursor.close();

        Cursor routeNameCursor = database.rawQuery("select RouteName from Route where Id = " + routeId, null);
        while (routeNameCursor.moveToNext()) {
            routeName = routeNameCursor.getString(routeNameCursor.getColumnIndex("RouteName"));
        }
        routeNameCursor.close();
        return routeName;
    }

    double getTodayTotalSaleAmount() {
        double totalAmt = 0.0;

        Cursor todayTotalSaleCursor = database.rawQuery("SELECT TOTAL_AMOUNT FROM INVOICE WHERE date(SALE_DATE) = date('now')", null);
        saleCount = todayTotalSaleCursor.getCount();
        while (todayTotalSaleCursor.moveToNext()) {
            double amt = todayTotalSaleCursor.getDouble(todayTotalSaleCursor.getColumnIndex("TOTAL_AMOUNT"));
            totalAmt += amt;
        }
        todayTotalSaleCursor.close();
        return totalAmt;
    }

    double getTodayTotalSaleReturn() {
        double returnAmt = 0.0;
        Cursor todayTotalSaleReturnCursor = database.rawQuery("SELECT AMT FROM SALE_RETURN WHERE date(RETURNED_DATE) = date('now')", null);
        saleReturnCount = todayTotalSaleReturnCursor.getCount();
        while (todayTotalSaleReturnCursor.moveToNext()) {
            double amt = todayTotalSaleReturnCursor.getDouble(todayTotalSaleReturnCursor.getColumnIndex("AMT"));
            returnAmt += amt;
        }
        todayTotalSaleReturnCursor.close();
        return returnAmt;
    }

    double getTodayPayAmt() {
        double totalPayAmt = 0.0;

        Cursor cursorSaleReturnPayAmt = database.rawQuery("SELECT PAY_AMT FROM SALE_RETURN WHERE date(RETURNED_DATE) = date('now') AND PAY_AMT > 0", null);
        cashReceiptCount = 0;

        while (cursorSaleReturnPayAmt.moveToNext()) {
            double amt = cursorSaleReturnPayAmt.getDouble(cursorSaleReturnPayAmt.getColumnIndex("PAY_AMT"));
            totalPayAmt += amt;
            if (amt > 0.0) {
                cashReceiptCount++;
            }
        }
        cursorSaleReturnPayAmt.close();
        //, , I., PR.ADVANCE_PAYMENT_AMOUNT FROM SALE_RETURN AS SR, DELIVERY AS D,  AS I, PRE_ORDER AS PR WHERE date(D.INVOICE_DATE) = date('now') AND date(PR.) = date('now')", null);

        Cursor cursorDeliveryPayAmt = database.rawQuery("SELECT PAID_AMOUNT FROM DELIVERY WHERE date(INVOICE_DATE)  = date('now') AND PAID_AMOUNT > 0", null);

        while (cursorDeliveryPayAmt.moveToNext()) {
            double amt = cursorDeliveryPayAmt.getDouble(cursorDeliveryPayAmt.getColumnIndex("PAID_AMOUNT"));
            totalPayAmt += amt;

            if (amt > 0.0) {
                cashReceiptCount++;
            }
        }
        cursorDeliveryPayAmt.close();

        Cursor cursorInvoicePayAmt = database.rawQuery("SELECT PAY_AMOUNT FROM INVOICE WHERE date(SALE_DATE)  = date('now') AND PAY_AMOUNT > 0", null);

        while (cursorInvoicePayAmt.moveToNext()) {
            double amt = cursorInvoicePayAmt.getDouble(cursorInvoicePayAmt.getColumnIndex("PAY_AMOUNT"));
            totalPayAmt += amt;

            if (amt > 0.0) {
                cashReceiptCount++;
            }
        }
        cursorDeliveryPayAmt.close();

        Cursor cursorPreOrderPayAmt = database.rawQuery("SELECT ADVANCE_PAYMENT_AMOUNT FROM PRE_ORDER WHERE date(PREORDER_DATE) = date('now') AND ADVANCE_PAYMENT_AMOUNT > 0", null);

        orderCount = cursorPreOrderPayAmt.getCount();
        while (cursorPreOrderPayAmt.moveToNext()) {
            double amt = cursorPreOrderPayAmt.getDouble(cursorPreOrderPayAmt.getColumnIndex("ADVANCE_PAYMENT_AMOUNT"));
            totalPayAmt += amt;

            if (amt > 0.0) {
                cashReceiptCount++;
            }
        }
        cursorPreOrderPayAmt.close();

        return totalPayAmt;
    }


}
