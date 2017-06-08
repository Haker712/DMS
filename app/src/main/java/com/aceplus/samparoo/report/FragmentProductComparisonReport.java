package com.aceplus.samparoo.report;

import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.SaleTarget;
import com.aceplus.samparoo.model.forApi.SaleTargetForCustomer;
import com.aceplus.samparoo.model.forApi.SaleTargetForSaleMan;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ValueFormatter;

import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yma on 4/3/17.
 *
 * FragmentProductComparisonReport
 */
public class FragmentProductComparisonReport extends Fragment  {

    View view;
    SQLiteDatabase database;
    ArrayList<SaleTargetForSaleMan> saleTargetArrayList = new ArrayList<>();
    ArrayList<SaleTarget> actualTargetArrayList = new ArrayList<>();
    BarChart chart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_product_report, container, false);
        database = new Database(getActivity()).getDataBase();
        getTargetSaleDB();
        chart = (HorizontalBarChart) view.findViewById(R.id.chart);
        initialize();

        getActualSaleDB();
        return view;
    }

    /**
     * Initialize bar chart for sale target.
     */
    private void initialize() {
        ArrayList<String> xAxisList = getXAxisValues();

        if(xAxisList.size() == 0) {
            xAxisList.add("");
        }

        ArrayList<BarDataSet> barDataArrayList = getDataSet();
        BarData data = null;
            data = new BarData(xAxisList, barDataArrayList);
            data.setValueFormatter(new MyValueFormatter());

            chart.canScrollHorizontally(10);
            chart.setData(data);
            chart.setDescription("Sale Target");
            chart.animateXY(2000, 2000);
            chart.getXAxis().setLabelsToSkip(0);
            chart.invalidate();

    }

    /**
     * Get actual sale for today
     */
    private void getActualSaleDB() {

        String query = "SELECT IP.TOTAL_AMOUNT, P.PRODUCT_ID, IP.SALE_QUANTITY FROM INVOICE_PRODUCT AS IP, PRODUCT AS P, INVOICE AS INV WHERE P.PRODUCT_ID = IP.PRODUCT_ID";

        actualTargetArrayList.clear();
        Cursor cursor = database.rawQuery(query, null);

        while (cursor.moveToNext()) {
            String productId = "";

            if(cursor.getString(cursor.getColumnIndex("PRODUCT_ID")) != null) {
                productId = cursor.getString(cursor.getColumnIndex("PRODUCT_ID"));
            }

            double saleQty = 0.0;
            if(cursor.getString(cursor.getColumnIndex("SALE_QUANTITY")) != null) {
                saleQty = Double.parseDouble(cursor.getString(cursor.getColumnIndex("SALE_QUANTITY")));
            }

            double totalSaleAmount = 0.0;
            if(cursor.getString(cursor.getColumnIndex("TOTAL_AMOUNT")) != null) {
                totalSaleAmount = Double.parseDouble(cursor.getString(cursor.getColumnIndex("TOTAL_AMOUNT")));
            }
            double sellingPrice = totalSaleAmount / saleQty;

            SaleTarget saleTarget = new SaleTarget();
            saleTarget.setProductId(productId);
            saleTarget.setQuantity(saleQty);
            saleTarget.setSellingPrice(sellingPrice);
            saleTarget.setTotalAmount(totalSaleAmount);
            actualTargetArrayList.add(saleTarget);
        }
        cursor.close();
    }

    /**
     * Get target sale for current month
     */
    private void getTargetSaleDB() {
        Cursor cursor = database.rawQuery("SELECT * FROM sale_target_saleman", null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.ID));
            String fromDate = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.FROM_DATE));
            String toDate = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.TO_DATE));
            String saleManId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.SALE_MAN_ID));
            String categoryId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.CATEGORY_ID));
            String groupCodeId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.GROUP_CODE_ID));
            String stockId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.STOCK_ID));
            String targetAmt = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.TARGET_AMOUNT));
            String date = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.DATE));
            String invoiceNo = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.INVOICE_NO));

            SaleTargetForSaleMan saleTarget = new SaleTargetForSaleMan();
            saleTarget.setId(id);
            saleTarget.setFromDate(fromDate);
            saleTarget.setToDate(toDate);
            saleTarget.setSaleManId(saleManId);
            saleTarget.setCategoryId(categoryId);
            saleTarget.setGroupCodeId(groupCodeId);
            saleTarget.setStockId(stockId);
            saleTarget.setTargetAmount(targetAmt);
            saleTarget.setDate(date);
            saleTarget.setInvoiceNo(invoiceNo);

            saleTargetArrayList.add(saleTarget);
        }

        cursor.close();
    }

    /**
     * Set comparing data set for bar chart
     *
     * @return bar data set array list
     */
    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        for (int i = 0; i < saleTargetArrayList.size(); i++) {
            BarEntry v1e12 = new BarEntry(Float.valueOf(saleTargetArrayList.get(i).getTargetAmount()), i); // Dec
            valueSet1.add(v1e12);
        }

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        for (int i = 0; i < actualTargetArrayList.size(); i++) {
            BarEntry v1e12 = new BarEntry(Float.valueOf(String.valueOf(actualTargetArrayList.get(i).getTotalAmount())), i); // Dec
            valueSet2.add(v1e12);
        }

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Target Sale");
        Resources res1 = this.getResources();
        barDataSet1.setColor(res1.getColor(R.color.colorPrimary));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Actual Sale");
        Resources res2 = this.getResources();
        barDataSet2.setColor(res2.getColor(R.color.colorPrimaryDark));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    /**
     * Product title for comparing bar chart data
     *
     * @return title list
     */
    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (SaleTargetForSaleMan saleTarget : saleTargetArrayList) {
            if(saleTarget.getStockId() != null) {
                xAxis.add(getProductNameFromDb(Integer.parseInt(saleTarget.getStockId())));
            } else {
                xAxis.add("");
            }
        }
        return xAxis;
    }

    /**
     * Get product name related to sale target product
     *
     * @param stockId product id
     * @return product name
     */
    String getProductNameFromDb(int stockId) {
        String name = "";
        Cursor c = database.rawQuery("SELECT PRODUCT_NAME FROM PRODUCT WHERE ID = " + stockId, null);
        while (c.moveToNext()) {
            name = c.getString(c.getColumnIndex("PRODUCT_NAME"));
        }

        return name;
    }

    /**
     * value formatter for bar chart
     */
    public class MyValueFormatter implements ValueFormatter{
        @Override
        public String getFormattedValue(float value) {
            return Math.round(value)+"";
        }
    }
}
