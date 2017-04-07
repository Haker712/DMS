package com.aceplus.samparoo.report;

import android.app.ActionBar;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.SaleTarget;
import com.aceplus.samparoo.model.forApi.SaleTargetForCustomer;
import com.aceplus.samparoo.model.forApi.SaleTargetForSaleMan;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;

/**
 * Created by yma on 4/3/17.
 *
 * FragmentSaleComparisonReport
 */
public class FragmentSaleComparisonReport extends Fragment {

    View view;
    SQLiteDatabase database;

    private GraphicalView mChartView;
    private LinearLayout layout;
    private TextView saleTargetTxt, saleTxt;

    private int[] COLORS = new int[]{R.color.colorPrimaryDark, R.color.colorPrimary};
    ArrayList<Double> VALUE = new ArrayList<>();
    ArrayList<SaleTargetForCustomer> saleTargetArrayList = new ArrayList<>();
    ArrayList<SaleTarget> actualTargetArrayList = new ArrayList<>();
    private static String[] NAME_LIST = new String[]{"Actual Sale", "Remaining Sale"};
    private CategorySeries mSeries = new CategorySeries("");
    private DefaultRenderer mRenderer = new DefaultRenderer();
    List<String> customerNameArr, groupNameArr, categoryNameArr, groupIdArr, categoryIdArr, customerIdArr;
    Spinner spinnerCustomer, spinnerGroup, spinnerCategory;

    double sale = 0;
    double remainingSale = 0;
    private double allSaleTargetValue = 0;
    private double allActualSaleValue = 0;
    String categoryId, groupId, customerId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sale_comparison_report, container, false);
        database = new Database(getActivity()).getDataBase();

        registerIDS();

        getTargetSaleDB();

        getCustomerListFromDB();
        getGroupCodeListFromDB();
        getCategoryListFromDB();

        setUpSpinner();

        if(categoryIdArr != null && categoryIdArr.size() != 0) {
            categoryId = categoryIdArr.get(spinnerCategory.getSelectedItemPosition());
        }

        if(groupIdArr != null && groupIdArr.size() != 0) {
            groupId = groupIdArr.get(spinnerGroup.getSelectedItemPosition());
        }

        if(customerIdArr != null && customerIdArr.size() != 0) {
            customerId = customerIdArr.get(spinnerCustomer.getSelectedItemPosition());
        }
        catchEvents();
        return view;
    }

    private void registerIDS() {
        saleTargetTxt = (TextView) view.findViewById(R.id.sale_target_txt);
        saleTxt = (TextView) view.findViewById(R.id.sale_txt);
        spinnerCustomer = (Spinner) view.findViewById(R.id.spinner_customer);
        spinnerGroup = (Spinner) view.findViewById(R.id.spinner_group);
        spinnerCategory = (Spinner) view.findViewById(R.id.spinner_category);
    }

    private void updateChartData() {
        if(customerIdArr != null && customerIdArr.size() != 0) {
            customerId = customerIdArr.get(spinnerCustomer.getSelectedItemPosition());
        }

        if(categoryIdArr != null && categoryIdArr.size() != 0) {
            categoryId = categoryIdArr.get(spinnerCategory.getSelectedItemPosition());
        }

        if(groupIdArr != null && groupIdArr.size() != 0) {
            groupId = groupIdArr.get(spinnerGroup.getSelectedItemPosition());
        }

        getActualSaleDB(customerId, categoryId, groupId);
        initialize();
    }

    private void catchEvents() {
        spinnerCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateChartData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateChartData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateChartData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private void initialize() {
        allSaleTargetValue = 0;
        allActualSaleValue = 0;

        for (int j = 0; j < saleTargetArrayList.size(); j++) {
            allSaleTargetValue += Integer.parseInt(saleTargetArrayList.get(j).getTargetAmount());
        }
        for (int j = 0; j < actualTargetArrayList.size(); j++) {
            allActualSaleValue += actualTargetArrayList.get(j).getTotalAmount();
        }

        saleTargetTxt.setText(String.valueOf(allSaleTargetValue));
        saleTxt.setText(String.valueOf(allActualSaleValue));

        if(allSaleTargetValue !=0) {
            sale = allActualSaleValue / (allSaleTargetValue / 100);
        }

        if (sale < 100) {
            remainingSale = 100 - sale;
            VALUE.clear();
            sale = Double.parseDouble(String.format("%.2f", sale));
            remainingSale = Double.parseDouble(String.format("%.2f", remainingSale));
            VALUE.add(sale);
            VALUE.add(remainingSale);

        } else {
            sale = 100;
            remainingSale = 0;
            VALUE.clear();
            VALUE.add(sale);
            VALUE.add(remainingSale);
        }

        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.WHITE);
        mRenderer.setLabelsTextSize(20);
        mRenderer.setLabelsColor(Color.BLACK);
        mRenderer.setLegendTextSize(20);
        mRenderer.setMargins(new int[]{100, 0});
        mRenderer.setStartAngle(90);

            if(mSeries.getItemCount() != 0){
                mSeries.clear();
                for(SimpleSeriesRenderer simpleSeriesRenderer : mRenderer.getSeriesRenderers()) {
                    mRenderer.removeSeriesRenderer(simpleSeriesRenderer);
                }
            }

            for (int i = 0; i < VALUE.size(); i++) {
                mSeries.add(NAME_LIST[i] + " " + VALUE.get(i) + "%", VALUE.get(i));
                SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
                Resources res = this.getResources();
                renderer.setColor(res.getColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]));
                mRenderer.addSeriesRenderer(renderer);
            }

        if (mChartView != null) {
            mChartView.repaint();
        }
    }

    private void getActualSaleDB(String customerId, String categoryId, String groupId) {

        String query = "SELECT IP.TOTAL_AMOUNT, P.PRODUCT_ID, IP.SALE_QUANTITY FROM INVOICE_PRODUCT AS IP, PRODUCT AS P, INVOICE AS INV WHERE P.PRODUCT_ID = IP.PRODUCT_ID";
        String customerCondtion = " AND INV.CUSTOMER_ID = '" + customerId + "'";
        String groupCondtion = " AND P.GROUP_ID = '" + groupId + "'";
        String categoryCondition = " AND P.CATEGORY_ID = '" + categoryId + "'";

        if(!customerId.equals("-1")) {
            query += customerCondtion;
        }

        if(!categoryId.equals("-1")) {
            query += categoryCondition;
        }

        if(!groupId.equals("-1")) {
            query += groupCondtion;
        }

        actualTargetArrayList.clear();
        Cursor cursor = database.rawQuery(query, null);

        while (cursor.moveToNext()) {
            String productId = cursor.getString(cursor.getColumnIndex("PRODUCT_ID"));
            int saleQty = Integer.parseInt(cursor.getString(cursor.getColumnIndex("SALE_QUANTITY")));
            double totalSaleAmount = Double.parseDouble(cursor.getString(cursor.getColumnIndex("TOTAL_AMOUNT")));
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

    private void getTargetSaleDB() {
        saleTargetArrayList.clear();
        Cursor cursor = database.rawQuery("SELECT * FROM sale_target_customer", null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.ID));
            String fromDate = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.FROM_DATE));
            String toDate = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.TO_DATE));
            String customerId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.CUSTOMER_ID));
            String saleManId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.SALE_MAN_ID));
            String categoryId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.CATEGORY_ID));
            String groupCodeId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.GROUP_CODE_ID));
            String stockId = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.STOCK_ID));
            String targetAmt = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.TARGET_AMOUNT));
            String date = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.DATE));
            String invoiceNo = cursor.getString(cursor.getColumnIndex(DatabaseContract.SALE_TARGET.INVOICE_NO));

            SaleTargetForCustomer saleTarget = new SaleTargetForCustomer();
            saleTarget.setId(id);
            saleTarget.setFromDate(fromDate);
            saleTarget.setToDate(toDate);
            saleTarget.setCustomerId(customerId);
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
     * Get customer name from database
     *
     * @return customer name list
     */
    void getCustomerListFromDB() {
        customerIdArr = new ArrayList<>();
        customerIdArr.add("-1");
        customerNameArr = new ArrayList<>();
        customerNameArr.add("All Customer");

        Cursor cursorCustomer = database.rawQuery("SELECT CUSTOMER_ID, CUSTOMER_NAME FROM CUSTOMER", null);
        while (cursorCustomer.moveToNext()) {
            customerIdArr.add(cursorCustomer.getString(cursorCustomer.getColumnIndex("CUSTOMER_ID")));
            customerNameArr.add(cursorCustomer.getString(cursorCustomer.getColumnIndex("CUSTOMER_NAME")));
        }
    }

    /**
     * Get group code name from database
     *
     * @return group code list
     */
    void getGroupCodeListFromDB() {
        groupIdArr = new ArrayList<>();
        groupIdArr.add("-1");
        groupNameArr = new ArrayList<>();
        groupNameArr.add("All Group");

        Cursor cursorGroup = database.rawQuery("SELECT id, name FROM GROUP_CODE", null);
        while (cursorGroup.moveToNext()) {
            groupIdArr.add(cursorGroup.getString(cursorGroup.getColumnIndex("id")));
            groupNameArr.add(cursorGroup.getString(cursorGroup.getColumnIndex("name")));
        }
    }

    /**
     * Get group code name from database
     *
     * @return group code list
     */
    void getCategoryListFromDB() {
        categoryIdArr = new ArrayList<>();
        categoryIdArr.add("-1");
        categoryNameArr = new ArrayList<>();
        categoryNameArr.add("All Category");

        Cursor cursorCategory = database.rawQuery("SELECT CATEGORY_ID,CATEGORY_NAME FROM PRODUCT_CATEGORY", null);
        while (cursorCategory.moveToNext()) {
            categoryIdArr.add(cursorCategory.getString(cursorCategory.getColumnIndex("CATEGORY_ID")));
            categoryNameArr.add(cursorCategory.getString(cursorCategory.getColumnIndex("CATEGORY_NAME")));
        }
    }

    /**
     * Initialize all spinner.
     */
    private void setUpSpinner() {

        if(customerNameArr != null) {
            ArrayAdapter<String> customerAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, customerNameArr);
            customerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCustomer.setAdapter(customerAdapter);
        }

        if(groupNameArr != null) {
            ArrayAdapter<String> groupAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, groupNameArr);
            groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerGroup.setAdapter(groupAdapter);
        }

        if(categoryNameArr != null) {
            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, categoryNameArr);
            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(categoryAdapter);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mChartView == null) {
            layout = (LinearLayout) view.findViewById(R.id.chart);
            mChartView = ChartFactory.getPieChartView(getActivity(), mSeries, mRenderer);
            mRenderer.setClickEnabled(false);
            mRenderer.setSelectableBuffer(10);
            layout.addView(mChartView, new ActionBar.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT));
        } else {
            mChartView.repaint();
        }
    }
}
