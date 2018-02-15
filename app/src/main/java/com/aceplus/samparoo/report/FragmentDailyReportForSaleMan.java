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

    TextView textView_saleman_name, textView_route, textView_date, textView_startTime, textView_endTime, textView_totalSale, textView_totalRe, textView_totalEx, textView_cashRecipt, textView_netCash, textView_totalCust, textView_saleCount, textView_orderCount, textView_saleReturn, textView_cashReciptCount, textView_notVisitedCount, textView_totalOrder, textView_saleExchangeCount, textView_saleReturnCount;

    SQLiteDatabase database;

    int totalCustomer = 0;
    int saleCount = 0;
    int orderCount = 0;
    int saleReturnCount = 0;
    int saleExchangeCount = 0;
    int cashReceiptCount = 0;
    int notVisitedCount = 0;
    int totalNotVisitedCount = 0;
    double totalSaleAmt, totalPayAmt, netAmt, totalCashReceive, totalOrderAmt;
    Double[] amtArr;
    String startTime, endTime;

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
        textView_totalOrder = (TextView) view.findViewById(R.id.fragment_daily_report_total_order_sale);
        textView_totalRe = (TextView) view.findViewById(R.id.fragment_daily_report_total_return);
        textView_totalEx = (TextView) view.findViewById(R.id.fragment_daily_report_total_exchange);
        textView_cashRecipt = (TextView) view.findViewById(R.id.fragment_daily_report_total_cash_receipt);
        textView_netCash = (TextView) view.findViewById(R.id.fragment_daily_report_net_cash);
        textView_totalCust = (TextView) view.findViewById(R.id.fragment_daily_report_total_customer);
        textView_saleCount = (TextView) view.findViewById(R.id.fragment_daily_report_total_sale_count);
        textView_orderCount = (TextView) view.findViewById(R.id.fragment_daily_report_total_order_count);
        textView_saleExchangeCount = (TextView) view.findViewById(R.id.fragment_daily_report_sale_exchange);
        textView_saleReturnCount = (TextView) view.findViewById(R.id.fragment_daily_report_sale_return);
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
                saleManDailyReport.setStartTime(startTime);
                saleManDailyReport.setEndTime(endTime);
                saleManDailyReport.setSaleAmt(totalSaleAmt);
                saleManDailyReport.setOrderAmt(totalOrderAmt);
                saleManDailyReport.setExchangeAmt(amtArr[1]);
                saleManDailyReport.setReturnAmt(amtArr[0]);
                saleManDailyReport.setCashReceive(totalPayAmt);
                saleManDailyReport.setNetAmt(netAmt);
                saleManDailyReport.setCustomerCount(totalCustomer);
                saleManDailyReport.setSaleCount(saleCount);
                saleManDailyReport.setOrderCount(orderCount);
                saleManDailyReport.setExchangeCount(saleExchangeCount);
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

        if (LoginActivity.mySharedPreference.getString(Constant.START_TIME, "") != null) {
            startTime = LoginActivity.mySharedPreference.getString(Constant.START_TIME, "");
        }

        textView_route.setText(getRouteName(saleManId));

        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(today);

        textView_date.setText(currentDate);

        sdf = new SimpleDateFormat("h:mm a");
        endTime = sdf.format(today);
        // need to ask strat time and end time
        textView_startTime.setText(startTime);
        textView_endTime.setText(endTime);
        totalSaleAmt = 0;
        totalSaleAmt = getTodayPayAmt();
        textView_totalSale.setText(Utils.formatAmount(totalSaleAmt));

        totalOrderAmt = 0;
        totalOrderAmt = getTodaySaleOrderAmount();
        textView_totalOrder.setText(Utils.formatAmount(totalOrderAmt));

        amtArr = getTodayTotalSaleReturn();
        textView_totalRe.setText("(" + Utils.formatAmount(amtArr[0]) + ")");
        textView_totalEx.setText("(" + Utils.formatAmount(amtArr[1]) + ")");

        totalCashReceive = 0;
        totalCashReceive = getTotalCreditReceive();
        textView_cashRecipt.setText(Utils.formatAmount(totalCashReceive));

        netAmt = 0;
        netAmt = totalSaleAmt + totalOrderAmt + totalCashReceive - amtArr[0] - amtArr[1];
        textView_netCash.setText(Utils.formatAmount(netAmt));

        totalCustomer = getCustomerCount();
        totalNotVisitedCount = getNotVisitedCount();

        textView_totalCust.setText(totalCustomer + "");
        textView_saleCount.setText(saleCount + "");
        textView_orderCount.setText(orderCount + "");
        textView_saleExchangeCount.setText(saleExchangeCount + "");
        textView_saleReturnCount.setText(saleReturnCount + "");
        textView_cashReciptCount.setText(cashReceiptCount + "");
        textView_notVisitedCount.setText(totalNotVisitedCount + "");

        /*textView_totalSale = (Te
        textView_totalOrder = (T
        textView_totalRe = (Text
        textView_totalEx = (Text
        textView_cashRecipt = (T
        textView_netCash = (Text
        textView_totalCust = (Te
        textView_saleCount = (Te
        textView_orderCount = (T
        textView_saleExchangeCount
             textView_saleReturnCount
        textView_cashReciptCount
                textView_notVisitedCount*/

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

        Cursor todayTotalSaleCursor = database.rawQuery("SELECT PAY_AMOUNT FROM INVOICE WHERE date(SALE_DATE) = date('now')", null);
        saleCount = todayTotalSaleCursor.getCount();
        while (todayTotalSaleCursor.moveToNext()) {
            double amt = todayTotalSaleCursor.getDouble(todayTotalSaleCursor.getColumnIndex("PAY_AMOUNT"));
            if (amt > 0.0) {
                totalAmt += amt;
            }
        }
        todayTotalSaleCursor.close();
        return totalAmt;
    }

    double getTodaySaleOrderAmount() {
        Cursor cursorPreOrderPayAmt = database.rawQuery("SELECT ADVANCE_PAYMENT_AMOUNT FROM PRE_ORDER WHERE date(PREORDER_DATE) = date('now') AND ADVANCE_PAYMENT_AMOUNT > 0", null);
        double amt = 0;
        orderCount = cursorPreOrderPayAmt.getCount();
        while (cursorPreOrderPayAmt.moveToNext()) {
            amt = cursorPreOrderPayAmt.getDouble(cursorPreOrderPayAmt.getColumnIndex("ADVANCE_PAYMENT_AMOUNT"));
            totalPayAmt += amt;
        }
        cursorPreOrderPayAmt.close();
        return amt;
    }

    Double[] getTodayTotalSaleReturn() {
        Double[] amtArr = {0.0,0.0};
        Cursor todayTotalSaleReturnCursor = database.rawQuery("SELECT PAY_AMT FROM SALE_RETURN WHERE date(RETURNED_DATE) = date('now') AND SALE_RETURN_ID LIKE 'SR%'", null);
        saleReturnCount = todayTotalSaleReturnCursor.getCount();
        while (todayTotalSaleReturnCursor.moveToNext()) {
            double amt = todayTotalSaleReturnCursor.getDouble(todayTotalSaleReturnCursor.getColumnIndex("AMT"));
            amtArr[0] += amt;
        }
        todayTotalSaleReturnCursor.close();

        Cursor cursorInvoicePayAmt = database.rawQuery("SELECT PAY_AMOUNT FROM INVOICE WHERE date(SALE_DATE) = date('now') AND INVOICE_ID LIKE 'SX%'", null);
        saleExchangeCount = cursorInvoicePayAmt.getCount();
        while (cursorInvoicePayAmt.moveToNext()) {
            double amt = cursorInvoicePayAmt.getDouble(cursorInvoicePayAmt.getColumnIndex("PAY_AMOUNT"));

            if (amt < 0.0) {
                amt = Math.abs(amt);
                amtArr[1] += amt;
            }
        }
        cursorInvoicePayAmt.close();

        return amtArr;
    }

    double getTodayPayAmt() {
        double totalPayAmt = 0.0;

        Cursor cursorDeliveryPayAmt = database.rawQuery("SELECT PAID_AMOUNT FROM DELIVERY WHERE date(INVOICE_DATE)  = date('now') AND PAID_AMOUNT > 0", null);

        while (cursorDeliveryPayAmt.moveToNext()) {
            double amt = cursorDeliveryPayAmt.getDouble(cursorDeliveryPayAmt.getColumnIndex("PAID_AMOUNT"));
            totalPayAmt += amt;
        }
        cursorDeliveryPayAmt.close();

        Cursor cursorInvoicePayAmt = database.rawQuery("SELECT PAY_AMOUNT FROM INVOICE WHERE date(SALE_DATE)  = date('now') AND PAY_AMOUNT > 0", null);

        while (cursorInvoicePayAmt.moveToNext()) {
            double amt = cursorInvoicePayAmt.getDouble(cursorInvoicePayAmt.getColumnIndex("PAY_AMOUNT"));

            if (amt > 0.0) {
                totalPayAmt += amt;
            }
        }
        cursorInvoicePayAmt.close();

        return totalPayAmt;
    }

    double getTotalCreditReceive(){
        double totalCredit = 0.0;
        Cursor cursorCredit = database.rawQuery("SELECT " + DatabaseContract.CREDIT.PAY_AMT + " FROM CREDIT WHERE date(INVOICE_DATE) = date('now')", null);
        cashReceiptCount = cursorCredit.getCount();
        while(cursorCredit.moveToNext()){
            double amt = cursorCredit.getDouble(cursorCredit.getColumnIndex(DatabaseContract.CREDIT.TABLE));
            totalCredit += amt;
        }
        cursorCredit.close();
        return totalCredit;
    }

}
