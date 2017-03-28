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
 * Created by haker on 2/6/17.
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

    List<SaleVisitRecord> saleVisitRecordList;

    String dateFormat = "yyyy-MM-dd";
    String currentDate = "";
    String day = "", month = "", year = "";
    String monthName = "";
    int i = 0;
    int startDay = 0;
    int diff = 0;

    String today = "THU";

    String[] dateStr = {"8", "10", "15", "20", "23", "29"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_e_calender, container, false);

        ButterKnife.inject(this, view);
        database = new Database(this.getContext()).getDataBase();

        currentDate = new SimpleDateFormat(dateFormat).format(new Date());
        /*Date date = new SimpleDateFormat(dateFormat).get2DigitYearStart();
        Log.i("date>>>", date.toString());*/

        List<ECalendar> eCalendarList = getLastThirtDays();

        Log.i("Size of eCalendar :", eCalendarList.size() + "");
        setUpCustomerSpinner();

        int dayIndex = 0;
        for(int i=0; i < 7; i++) {
            if(eCalendarList.get(0).getDay() == i+1) {
                dayIndex = i;
                break;
            }
        }

        if(dayIndex != 6) {
            hideRow1.setVisibility(View.GONE);
        }

        Button[] btnArr = new Button[42];
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

        for(int i = dayIndex; i < 30 + dayIndex; i++) {
            btnArr[i].setText(eCalendarList.get(i-dayIndex).getDateNum() + "");
            ECalendar eCalendar = eCalendarList.get(i-dayIndex);
            for(int index = 0; index < saleVisitRecordList.size(); index++){
                String saleVisitDate = saleVisitRecordList.get(index).getRecordDate().substring(0,10);
                if(eCalendar.getDefaultFormat().equals(saleVisitDate)) {
                    setColorByDate(btnArr[i]);
                }
            }
        }

        return view;
    }

    /**
     * @return
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

    private void arraneDayHeader() {
        /*int current_day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        switch (current_day) {
            case Calendar.SUNDAY:
                break;

            case Calendar.MONDAY:
                sun.setText("MON");
                mon.setText("TUE");
                tue.setText("WED");
                wed.setText("THU");
                thu.setText("FRI");
                fri.setText("SAT");
                sat.setText("SUN");
                break;

            case Calendar.TUESDAY:
                sun.setText("TUE");
                mon.setText("WED");
                tue.setText("THU");
                wed.setText("FRI");
                thu.setText("SAT");
                fri.setText("SUN");
                sat.setText("MON");
                break;

            case Calendar.WEDNESDAY :
                sun.setText("WED");
                mon.setText("THU");
                tue.setText("FRI");
                wed.setText("SAT");
                thu.setText("SUN");
                fri.setText("MON");
                sat.setText("TUE");
                break;

            case Calendar.THURSDAY:
                sun.setText("THU");
                mon.setText("FRI");
                tue.setText("SAT");
                wed.setText("SUN");
                thu.setText("MON");
                fri.setText("TUE");
                sat.setText("WED");
                break;

            case Calendar.FRIDAY:
                sun.setText("FRI");
                mon.setText("SAT");
                tue.setText("SUN");
                wed.setText("MON");
                thu.setText("TUE");
                fri.setText("WED");
                sat.setText("THU");
                break;

            case Calendar.SATURDAY:
                sun.setText("SAT");
                mon.setText("SUN");
                tue.setText("MON");
                wed.setText("TUE");
                thu.setText("WED");
                fri.setText("THU");
                sat.setText("FRI");
                break;
        }*/
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
        saleVisitRecordList = getSaleVisitRecord(customerList.get(0).getId());
    }

    @OnItemSelected(R.id.ecalendar_spinner_customer)
    void chooseCustomer() {
        int selectedItemPosition = customerSpinner.getSelectedItemPosition();
        Log.i("SELECTED CUSTOMER -->", customerList.get(selectedItemPosition).getCustomerName());
        saleVisitRecordList = getSaleVisitRecord(customerList.get(selectedItemPosition).getId());
        //setColorByDate(customerList.get(selectedItemPosition).getId());
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

    private void setColorByDate(Button btn) {

        //List<SaleVisitRecord> saleVisitRecordList = getSaleVisitRecord();
        btn.setBackgroundColor(Color.GREEN);
        //for (SaleVisitRecord saleVisitRecord : saleVisitRecordList) {
            /*if(saleVisitRecord.getRecordDate())*/
        //}
    }

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

    private void previousCode() {
        /*String str[] = currentDate.split("-");
        year = str[0];
        month = str[1];
        day = str[2];

        int tempMonth = 0;
        int tempYear = 0;

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Log.i("month>>>", Integer.parseInt(month) + "");
        if (Integer.parseInt(month) < 10) {
            tempMonth = Integer.parseInt(month) - 1;
            if (tempMonth != 0 || tempMonth > 0) {
                month = "0" + tempMonth;
                tempYear = Integer.parseInt(year);
            }
            else if (tempMonth == 0) {
                month = "12";
                tempYear = Integer.parseInt(year) - 1;
            }
        }
        else {
            month = Integer.parseInt(month) - 1 + "";
            tempYear = Integer.parseInt(year);
        }
        Log.i("month>>>", month + "");
        year = tempYear + "";
        String parseStr = year + "-" + month + "-" + day;
        try {
            date = formatter.parse(parseStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("date>>>", date.toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int current_day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (current_day) {
            case Calendar.SUNDAY:
                break;

            case Calendar.MONDAY:
                sun.setText("MON");
                mon.setText("TUE");
                tue.setText("WED");
                wed.setText("THU");
                thu.setText("FRI");
                fri.setText("SAT");
                sat.setText("SUN");
                break;

            case Calendar.TUESDAY:
                sun.setText("TUE");
                mon.setText("WED");
                tue.setText("THU");
                wed.setText("FRI");
                thu.setText("SAT");
                fri.setText("SUN");
                sat.setText("MON");
                break;

            case Calendar.WEDNESDAY :
                sun.setText("WED");
                mon.setText("THU");
                tue.setText("FRI");
                wed.setText("SAT");
                thu.setText("SUN");
                fri.setText("MON");
                sat.setText("TUE");
                break;

            case Calendar.THURSDAY:
                sun.setText("THU");
                mon.setText("FRI");
                tue.setText("SAT");
                wed.setText("SUN");
                thu.setText("MON");
                fri.setText("TUE");
                sat.setText("WED");
                break;

            case Calendar.FRIDAY:
                sun.setText("FRI");
                mon.setText("SAT");
                tue.setText("SUN");
                wed.setText("MON");
                thu.setText("TUE");
                fri.setText("WED");
                sat.setText("THU");
                break;

            case Calendar.SATURDAY:
                sun.setText("SAT");
                mon.setText("SUN");
                tue.setText("MON");
                wed.setText("TUE");
                thu.setText("WED");
                fri.setText("THU");
                sat.setText("FRI");
                break;
        }

        switch (month) {
            case "01": monthName = "January";

                thirtyOne();

                break;
            case "02": monthName = "February";

                //twentyEight();

                break;
            case "03": monthName = "March";
                break;
            case "04": monthName = "April";
                break;
            case "05": monthName = "May";
                break;
            case "06": monthName = "June";
                break;
            case "07": monthName = "July";
                break;
            case "08": monthName = "August";
                break;
            case "09": monthName = "September";
                break;
            case "10": monthName = "October";
                break;
            case "11": monthName = "November";

                thirtyOne();

                break;
            case "12": monthName = "December";

                thirtyOne();

                break;
        }

        //thirty();

        setUpCustomerSpinner();

        textViewYear.setText(year);*/

    }

    private void twentyEight() {
        //first row
        /*day1.setText(Integer.parseInt(day) + "");

        if (Integer.parseInt(day1.getText().toString()) < 28) {
            day2.setText((Integer.parseInt(day1.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day1.getText().toString());
            day2.setText(diff + "");
        }

        if (Integer.parseInt(day2.getText().toString()) < 28) {
            day3.setText((Integer.parseInt(day2.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day2.getText().toString());
            day3.setText(diff + "");
        }

        if (Integer.parseInt(day3.getText().toString()) < 30) {
            day4.setText((Integer.parseInt(day3.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day3.getText().toString());
            day4.setText(diff + "");
        }

        if (Integer.parseInt(day4.getText().toString()) < 28) {
            day5.setText((Integer.parseInt(day4.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day4.getText().toString());
            day5.setText(diff + "");
        }

        if (Integer.parseInt(day5.getText().toString()) < 28) {
            day6.setText((Integer.parseInt(day5.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day5.getText().toString());
            day6.setText(diff + "");
        }

        if (Integer.parseInt(day6.getText().toString()) < 28) {
            day7.setText((Integer.parseInt(day6.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day6.getText().toString());
            day7.setText(diff + "");
        }

        //second row
        if (Integer.parseInt(day7.getText().toString()) < 28) {
            day8.setText((Integer.parseInt(day7.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day7.getText().toString());
            day8.setText(diff + "");
        }

        if (Integer.parseInt(day8.getText().toString()) < 28) {
            day9.setText((Integer.parseInt(day8.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day8.getText().toString());
            day9.setText(diff + "");
        }

        if (Integer.parseInt(day9.getText().toString()) < 28) {
            day10.setText((Integer.parseInt(day9.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day9.getText().toString());
            day10.setText(diff + "");
        }

        if (Integer.parseInt(day10.getText().toString()) < 28) {
            day11.setText((Integer.parseInt(day10.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day10.getText().toString());
            day11.setText(diff + "");
        }

        if (Integer.parseInt(day11.getText().toString()) < 28) {
            day12.setText((Integer.parseInt(day11.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day11.getText().toString());
            day12.setText(diff + "");
        }

        if (Integer.parseInt(day12.getText().toString()) < 28) {
            day13.setText((Integer.parseInt(day12.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day12.getText().toString());
            day13.setText(diff + "");
        }

        if (Integer.parseInt(day13.getText().toString()) < 28) {
            day14.setText((Integer.parseInt(day13.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day13.getText().toString());
            day14.setText(diff + "");
        }

        //third row
        if (Integer.parseInt(day14.getText().toString()) < 28) {
            day15.setText((Integer.parseInt(day14.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day14.getText().toString());
            day15.setText(diff + "");
        }

        if (Integer.parseInt(day15.getText().toString()) < 28) {
            day16.setText((Integer.parseInt(day15.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day15.getText().toString());
            day16.setText(diff + "");
        }

        if (Integer.parseInt(day16.getText().toString()) < 28) {
            day17.setText((Integer.parseInt(day16.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day16.getText().toString());
            day17.setText(diff + "");
        }

        if (Integer.parseInt(day17.getText().toString()) < 28) {
            day18.setText((Integer.parseInt(day17.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day17.getText().toString());
            day18.setText(diff + "");
        }

        if (Integer.parseInt(day18.getText().toString()) < 28) {
            day19.setText((Integer.parseInt(day18.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day18.getText().toString());
            day19.setText(diff + "");
        }

        if (Integer.parseInt(day19.getText().toString()) < 28) {
            day20.setText((Integer.parseInt(day19.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day19.getText().toString());
            day20.setText(diff + "");
        }

        if (Integer.parseInt(day20.getText().toString()) < 28) {
            day21.setText((Integer.parseInt(day20.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day20.getText().toString());
            day21.setText(diff + "");
        }

        //forth row
        if (Integer.parseInt(day21.getText().toString()) < 28) {
            day22.setText((Integer.parseInt(day21.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day21.getText().toString());
            day22.setText(diff + "");
        }

        if (Integer.parseInt(day22.getText().toString()) < 28) {
            day23.setText((Integer.parseInt(day22.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day22.getText().toString());
            day23.setText(diff + "");
        }

        if (Integer.parseInt(day23.getText().toString()) < 28) {
            day24.setText((Integer.parseInt(day23.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day23.getText().toString());
            day24.setText(diff + "");
        }

        if (Integer.parseInt(day24.getText().toString()) < 28) {
            day25.setText((Integer.parseInt(day24.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day24.getText().toString());
            day25.setText(diff + "");
        }

        if (Integer.parseInt(day25.getText().toString()) < 28) {
            day26.setText((Integer.parseInt(day25.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day25.getText().toString());
            day26.setText(diff + "");
        }

        if (Integer.parseInt(day26.getText().toString()) < 28) {
            day27.setText((Integer.parseInt(day26.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day26.getText().toString());
            day27.setText(diff + "");
        }

        if (Integer.parseInt(day27.getText().toString()) < 28) {
            day28.setText((Integer.parseInt(day27.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day27.getText().toString());
            day28.setText(diff + "");
        }

        //fifth row
        if (Integer.parseInt(day28.getText().toString()) < 28) {
            day29.setText((Integer.parseInt(day28.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day28.getText().toString());
            day29.setText(diff + "");
        }

        if (Integer.parseInt(day29.getText().toString()) < 28) {
            day30.setText((Integer.parseInt(day29.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day29.getText().toString());
            day30.setText(diff + "");
        }

        if (Integer.parseInt(day30.getText().toString()) < 28) {
            day31.setText((Integer.parseInt(day30.getText().toString()) + 1) + "");
        }
        else {
            diff = 29 - Integer.parseInt(day30.getText().toString());
            day31.setText(diff + "");}*/
    }

    private void thirtyOne() {
        //first row
        /*
        day1.setText(Integer.parseInt(day) + "");

        if (Integer.parseInt(day1.getText().toString()) < 31) {
            day2.setText((Integer.parseInt(day1.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day1.getText().toString());
            day2.setText(diff + "");
        }

        if (Integer.parseInt(day2.getText().toString()) < 31) {
            day3.setText((Integer.parseInt(day2.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day2.getText().toString());
            day3.setText(diff + "");
        }

        if (Integer.parseInt(day3.getText().toString()) < 31) {
            day4.setText((Integer.parseInt(day3.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day3.getText().toString());
            day4.setText(diff + "");
        }

        if (Integer.parseInt(day4.getText().toString()) < 31) {
            day5.setText((Integer.parseInt(day4.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day4.getText().toString());
            day5.setText(diff + "");
        }

        if (Integer.parseInt(day5.getText().toString()) < 31) {
            day6.setText((Integer.parseInt(day5.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day5.getText().toString());
            day6.setText(diff + "");
        }

        if (Integer.parseInt(day6.getText().toString()) < 31) {
            day7.setText((Integer.parseInt(day6.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day6.getText().toString());
            day7.setText(diff + "");
        }

        //second row
        if (Integer.parseInt(day7.getText().toString()) < 31) {
            day8.setText((Integer.parseInt(day7.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day7.getText().toString());
            day8.setText(diff + "");
        }

        if (Integer.parseInt(day8.getText().toString()) < 31) {
            day9.setText((Integer.parseInt(day8.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day8.getText().toString());
            day9.setText(diff + "");
        }

        if (Integer.parseInt(day9.getText().toString()) < 31) {
            day10.setText((Integer.parseInt(day9.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day9.getText().toString());
            day10.setText(diff + "");
        }

        if (Integer.parseInt(day10.getText().toString()) < 31) {
            day11.setText((Integer.parseInt(day10.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day10.getText().toString());
            day11.setText(diff + "");
        }

        if (Integer.parseInt(day11.getText().toString()) < 31) {
            day12.setText((Integer.parseInt(day11.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day11.getText().toString());
            day12.setText(diff + "");
        }

        if (Integer.parseInt(day12.getText().toString()) < 31) {
            day13.setText((Integer.parseInt(day12.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day12.getText().toString());
            day13.setText(diff + "");
        }

        if (Integer.parseInt(day13.getText().toString()) < 31) {
            day14.setText((Integer.parseInt(day13.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day13.getText().toString());
            day14.setText(diff + "");
        }

        //third row
        if (Integer.parseInt(day14.getText().toString()) < 31) {
            day15.setText((Integer.parseInt(day14.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day14.getText().toString());
            day15.setText(diff + "");
        }

        if (Integer.parseInt(day15.getText().toString()) < 31) {
            day16.setText((Integer.parseInt(day15.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day15.getText().toString());
            day16.setText(diff + "");
        }

        if (Integer.parseInt(day16.getText().toString()) < 31) {
            day17.setText((Integer.parseInt(day16.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day16.getText().toString());
            day17.setText(diff + "");
        }

        if (Integer.parseInt(day17.getText().toString()) < 31) {
            day18.setText((Integer.parseInt(day17.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day17.getText().toString());
            day18.setText(diff + "");
        }

        if (Integer.parseInt(day18.getText().toString()) < 31) {
            day19.setText((Integer.parseInt(day18.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day18.getText().toString());
            day19.setText(diff + "");
        }

        if (Integer.parseInt(day19.getText().toString()) < 31) {
            day20.setText((Integer.parseInt(day19.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day19.getText().toString());
            day20.setText(diff + "");
        }

        if (Integer.parseInt(day20.getText().toString()) < 31) {
            day21.setText((Integer.parseInt(day20.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day20.getText().toString());
            day21.setText(diff + "");
        }

        //forth row
        if (Integer.parseInt(day21.getText().toString()) < 31) {
            day22.setText((Integer.parseInt(day21.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day21.getText().toString());
            day22.setText(diff + "");
        }

        if (Integer.parseInt(day22.getText().toString()) < 31) {
            day23.setText((Integer.parseInt(day22.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day22.getText().toString());
            day23.setText(diff + "");
        }

        if (Integer.parseInt(day23.getText().toString()) < 31) {
            day24.setText((Integer.parseInt(day23.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day23.getText().toString());
            day24.setText(diff + "");
        }

        if (Integer.parseInt(day24.getText().toString()) < 31) {
            day25.setText((Integer.parseInt(day24.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day24.getText().toString());
            day25.setText(diff + "");
        }

        if (Integer.parseInt(day25.getText().toString()) < 31) {
            day26.setText((Integer.parseInt(day25.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day25.getText().toString());
            day26.setText(diff + "");
        }

        if (Integer.parseInt(day26.getText().toString()) < 31) {
            day27.setText((Integer.parseInt(day26.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day26.getText().toString());
            day27.setText(diff + "");
        }

        if (Integer.parseInt(day27.getText().toString()) < 31) {
            day28.setText((Integer.parseInt(day27.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day27.getText().toString());
            day28.setText(diff + "");
        }

        //fifth row
        if (Integer.parseInt(day28.getText().toString()) < 31) {
            day29.setText((Integer.parseInt(day28.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day28.getText().toString());
            day29.setText(diff + "");
        }

        if (Integer.parseInt(day29.getText().toString()) < 31) {
            day30.setText((Integer.parseInt(day29.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day29.getText().toString());
            day30.setText(diff + "");
        }

        if (Integer.parseInt(day30.getText().toString()) < 31) {
            day31.setText((Integer.parseInt(day30.getText().toString()) + 1) + "");
        }
        else {
            diff = 32 - Integer.parseInt(day30.getText().toString());
            day31.setText(diff + "");
        }
    }*/
    }
}
