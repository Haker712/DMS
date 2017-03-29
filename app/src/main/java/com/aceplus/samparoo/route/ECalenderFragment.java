package com.aceplus.samparoo.route;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.model.ECalendar;
import com.aceplus.samparoo.model.forApi.SaleVisitRecord;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemSelected;

/**
 * Created by yma on 3/28/17.
 *
 * ECalenderFragment
 */
public class ECalenderFragment extends Fragment {

    View view;

    SQLiteDatabase database;

    List<Customer> customerList;

    @InjectView(R.id.ecalendar_spinner_customer)
    AppCompatSpinner customerSpinner;
    @InjectView(R.id.textViewYear)
    TextView textViewYear;
    @InjectView(R.id.mon)
    TextView mon;
    @InjectView(R.id.tue)
    TextView tue;
    @InjectView(R.id.wed)
    TextView wed;
    @InjectView(R.id.thu)
    TextView thu;
    @InjectView(R.id.fri)
    TextView fri;
    @InjectView(R.id.sat)
    TextView sat;
    @InjectView(R.id.sun)
    TextView sun;
    @InjectView(R.id.day1)
    Button day1;
    @InjectView(R.id.day2)
    Button day2;
    @InjectView(R.id.day3)
    Button day3;
    @InjectView(R.id.day4)
    Button day4;
    @InjectView(R.id.day5)
    Button day5;
    @InjectView(R.id.day6)
    Button day6;
    @InjectView(R.id.day7)
    Button day7;
    @InjectView(R.id.day8)
    Button day8;
    @InjectView(R.id.day9)
    Button day9;
    @InjectView(R.id.day10)
    Button day10;
    @InjectView(R.id.day11)
    Button day11;
    @InjectView(R.id.day12)
    Button day12;
    @InjectView(R.id.day13)
    Button day13;
    @InjectView(R.id.day14)
    Button day14;
    @InjectView(R.id.day15)
    Button day15;
    @InjectView(R.id.day16)
    Button day16;
    @InjectView(R.id.day17)
    Button day17;
    @InjectView(R.id.day18)
    Button day18;
    @InjectView(R.id.day19)
    Button day19;
    @InjectView(R.id.day20)
    Button day20;
    @InjectView(R.id.day21)
    Button day21;
    @InjectView(R.id.day22)
    Button day22;
    @InjectView(R.id.day23)
    Button day23;
    @InjectView(R.id.day24)
    Button day24;
    @InjectView(R.id.day25)
    Button day25;
    @InjectView(R.id.day26)
    Button day26;
    @InjectView(R.id.day27)
    Button day27;
    @InjectView(R.id.day28)
    Button day28;
    @InjectView(R.id.day29)
    Button day29;
    @InjectView(R.id.day30)
    Button day30;
    @InjectView(R.id.day31)
    Button day31;
    @InjectView(R.id.day32)
    Button day32;
    @InjectView(R.id.day33)
    Button day33;
    @InjectView(R.id.day34)
    Button day34;
    @InjectView(R.id.day35)
    Button day35;
    @InjectView(R.id.day36)
    Button day36;
    @InjectView(R.id.day37)
    Button day37;
    @InjectView(R.id.day38)
    Button day38;
    @InjectView(R.id.day39)
    Button day39;
    @InjectView(R.id.day40)
    Button day40;
    @InjectView(R.id.day41)
    Button day41;
    @InjectView(R.id.day42)
    Button day42;

    @InjectView(R.id.hideRow)
    TableRow hideRow;
    @InjectView(R.id.hideRow1)
    TableRow hideRow1;

    List<ECalendar> eCalendarList;

    String dateFormat = "yyyy-MM-dd";
    String currentDate = "";
    Button[] btnArr;
    int dayIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_e_calender, container, false);

        ButterKnife.inject(this, view);
        database = new Database(this.getContext()).getDataBase();

        currentDate = new SimpleDateFormat(dateFormat).format(new Date());

        eCalendarList = getLastThirtDays();

        Log.i("Size of eCalendar :", eCalendarList.size() + "");

        String dateDuration = eCalendarList.get(0).getDefaultFormat() + " ~ " + eCalendarList.get(eCalendarList.size()-1).getDefaultFormat();
        textViewYear.setText(dateDuration);

        dayIndex = 0;
        for(int i=0; i < 7; i++) {
            if(eCalendarList.get(0).getDay() == i+1) {
                dayIndex = i;
                break;
            }
        }

        if(dayIndex != 6) {
            hideRow1.setVisibility(View.GONE);
        }

        btnArr = new Button[42];
        btnArr[0] = day1;
        btnArr[1] = day2;
        btnArr[2] = day3;
        btnArr[3] = day4;
        btnArr[4] = day5;
        btnArr[5] = day6;
        btnArr[6] = day7;
        btnArr[7] = day8;
        btnArr[8] = day9;
        btnArr[9] = day10;
        btnArr[10] = day11;
        btnArr[11] = day12;
        btnArr[12] = day13;
        btnArr[13] = day14;
        btnArr[14] = day15;
        btnArr[15] = day16;
        btnArr[16] = day17;
        btnArr[17] = day18;
        btnArr[18] = day19;
        btnArr[19] = day20;
        btnArr[20] = day21;
        btnArr[21] = day22;
        btnArr[22] = day23;
        btnArr[23] = day24;
        btnArr[24] = day25;
        btnArr[25] = day26;
        btnArr[26] = day27;
        btnArr[27] = day28;
        btnArr[28] = day29;
        btnArr[29] = day30;
        btnArr[30] = day31;
        btnArr[31] = day32;
        btnArr[32] = day33;
        btnArr[33] = day34;
        btnArr[34] = day35;
        btnArr[35] = day36;
        btnArr[36] = day37;
        btnArr[37] = day38;
        btnArr[38] = day39;
        btnArr[39] = day40;
        btnArr[40] = day41;
        btnArr[41] = day42;

        setUpCustomerSpinner();

        return view;
    }

    /**
     * Get 30 days from current date
     *
     * @return ECalendar object list
     */
    private List<ECalendar> getLastThirtDays() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<ECalendar> ecalendarList = new ArrayList<>();

        for (int i = 30; i > 0; i--) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, 0 - i);

            ECalendar ecalendar = new ECalendar();
            ecalendar.setDefaultFormat(sdf.format(cal.getTime()));
            ecalendar.setDate(cal.getTime());
            //ecalendar.setDay(cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US)); // display as Wed , sun, etc
            ecalendar.setDay(cal.get(Calendar.DAY_OF_WEEK));
            ecalendar.setDateNum(cal.get(Calendar.DAY_OF_MONTH));
            ecalendar.setMonth(cal.get(Calendar.MONTH) + 1);
            ecalendar.setYear(cal.get(Calendar.YEAR));
            ecalendarList.add(ecalendar);
        }
        return ecalendarList;
    }

    /**
     * Set customer spinner.
     */
    private void setUpCustomerSpinner() {
        customerList = getCustomerList();
        String[] customerNameArr = new String[customerList.size()];

        for (int i = 0; i < customerList.size(); i++) {
            customerNameArr[i] = customerList.get(i).getCustomerName();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, customerNameArr);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerSpinner.setAdapter(arrayAdapter);
        customerSpinner.setSelection(0);
    }

    @OnItemSelected(R.id.ecalendar_spinner_customer)
    void chooseCustomer() {
        int selectedItemPosition = customerSpinner.getSelectedItemPosition();
        Log.i("SELECTED CUSTOMER -->", customerList.get(selectedItemPosition).getCustomerName());
        setColorSaleVisitRecordByDate(customerList.get(selectedItemPosition).getId());
    }

    /**
     * Set color depending on sale visit record
     * 0 : not visited or sale, 1 : already sale or visited
     *
     * @param customerId customer id
     */
    private void setColorSaleVisitRecordByDate(int customerId) {
        List<SaleVisitRecord> saleVisitRecordList = getSaleVisitRecord(customerId);

        for(int i = dayIndex; i < 30 + dayIndex; i++) {
            btnArr[i].setText(eCalendarList.get(i-dayIndex).getDateNum() + "");
            ECalendar eCalendar = eCalendarList.get(i-dayIndex);
            btnArr[i].setBackgroundColor(Color.WHITE);

            for(int index = 0; index < saleVisitRecordList.size(); index++){
                String saleVisitDate = saleVisitRecordList.get(index).getRecordDate().substring(0,10);
                Short saleStatus = saleVisitRecordList.get(index).getSaleFlg();
                Short visitStatus = saleVisitRecordList.get(index).getVisitFlg();

                if(eCalendar.getDefaultFormat().equals(saleVisitDate)) {
                    if(saleStatus == 1 && visitStatus == 1) {
                        btnArr[i].setBackgroundColor(Color.RED);
                    }

                    if(visitStatus == 1 && saleStatus == 0) {
                        btnArr[i].setBackgroundColor(Color.YELLOW);
                    }

                    if(saleStatus == 0 && visitStatus == 0) {
                        btnArr[i].setBackgroundColor(Color.GRAY);
                    }
                }


            }

        }
    }

    /**
     * Get all customer from database
     *
     * @return customer list
     */
    private List<Customer> getCustomerList() {
        Cursor customerCursor = database.rawQuery("SELECT * FROM CUSTOMER", null);
        List<Customer> customerList = new ArrayList<>();

        while (customerCursor.moveToNext()) {
            Customer customer = new Customer();
            customer.setId(customerCursor.getInt(customerCursor.getColumnIndex("id")));
            customer.setCustomerName(customerCursor.getString(customerCursor.getColumnIndex("CUSTOMER_NAME")));
            customerList.add(customer);
        }

        customerCursor.close();
        return customerList;
    }

    /**
     * Get sale visit record for specific customer
     *
     * @param customerId customer id
     * @return sale visit record list
     */
    private List<SaleVisitRecord> getSaleVisitRecord(int customerId) {
        List<SaleVisitRecord> saleVisitRecordList = new ArrayList<>();

        Cursor cursorSaleVisitRecord = database.rawQuery("SELECT * FROM " + DatabaseContract.SALE_VISIT_RECORD.TABLE_DOWNLOAD + " WHERE CUSTOMER_ID = " + customerId,null);
        while(cursorSaleVisitRecord.moveToNext()) {
            SaleVisitRecord saleVisitRecord = new SaleVisitRecord();

            saleVisitRecord.setId(cursorSaleVisitRecord.getInt(cursorSaleVisitRecord.getColumnIndex(DatabaseContract.SALE_VISIT_RECORD.ID)));
            saleVisitRecord.setCustomerId(cursorSaleVisitRecord.getInt(cursorSaleVisitRecord.getColumnIndex(DatabaseContract.SALE_VISIT_RECORD.CUSTOMER_ID)));
            saleVisitRecord.setLatitude(cursorSaleVisitRecord.getString(cursorSaleVisitRecord.getColumnIndex(DatabaseContract.SALE_VISIT_RECORD.LATITUDE)));
            saleVisitRecord.setLongitude(cursorSaleVisitRecord.getString(cursorSaleVisitRecord.getColumnIndex(DatabaseContract.SALE_VISIT_RECORD.LONGITUDE)));
            saleVisitRecord.setSalemanId(cursorSaleVisitRecord.getInt(cursorSaleVisitRecord.getColumnIndex(DatabaseContract.SALE_VISIT_RECORD.SALEMAN_ID)));
            saleVisitRecord.setSaleFlg(cursorSaleVisitRecord.getShort(cursorSaleVisitRecord.getColumnIndex(DatabaseContract.SALE_VISIT_RECORD.SALE_FLG)));
            saleVisitRecord.setVisitFlg(cursorSaleVisitRecord.getShort(cursorSaleVisitRecord.getColumnIndex(DatabaseContract.SALE_VISIT_RECORD.VISIT_FLG)));
            saleVisitRecord.setRecordDate(cursorSaleVisitRecord.getString(cursorSaleVisitRecord.getColumnIndex(DatabaseContract.SALE_VISIT_RECORD.RECORD_DATE)));;
            saleVisitRecordList.add(saleVisitRecord);
        }
        return saleVisitRecordList;
    }
}
