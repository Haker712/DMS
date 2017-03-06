package com.aceplus.samparoo.route;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.aceplus.samparoo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by haker on 2/6/17.
 */
public class ECalenderFragment extends Fragment {

    View view;

    @InjectView(R.id.textViewMonth)
    TextView textViewMonth;
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
    @InjectView(R.id.hideRow)
    TableRow hideRow;

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

        currentDate = new SimpleDateFormat(dateFormat).format(new Date());
        /*Date date = new SimpleDateFormat(dateFormat).get2DigitYearStart();
        Log.i("date>>>", date.toString());*/

        String str[] = currentDate.split("-");
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

                twentyEight();

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

        textViewMonth.setText(monthName);
        textViewYear.setText(year);

        setColorByDate();

        return view;
    }

    private void thirtyOne() {
        //first row
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
    }

    private void thirty() {
        //first row
        day1.setText(Integer.parseInt(day) + "");

        if (Integer.parseInt(day1.getText().toString()) < 30) {
            day2.setText((Integer.parseInt(day1.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day1.getText().toString());
            day2.setText(diff + "");
        }

        if (Integer.parseInt(day2.getText().toString()) < 30) {
            day3.setText((Integer.parseInt(day2.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day2.getText().toString());
            day3.setText(diff + "");
        }

        if (Integer.parseInt(day3.getText().toString()) < 30) {
            day4.setText((Integer.parseInt(day3.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day3.getText().toString());
            day4.setText(diff + "");
        }

        if (Integer.parseInt(day4.getText().toString()) < 30) {
            day5.setText((Integer.parseInt(day4.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day4.getText().toString());
            day5.setText(diff + "");
        }

        if (Integer.parseInt(day5.getText().toString()) < 30) {
            day6.setText((Integer.parseInt(day5.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day5.getText().toString());
            day6.setText(diff + "");
        }

        if (Integer.parseInt(day6.getText().toString()) < 30) {
            day7.setText((Integer.parseInt(day6.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day6.getText().toString());
            day7.setText(diff + "");
        }

        //second row
        if (Integer.parseInt(day7.getText().toString()) < 30) {
            day8.setText((Integer.parseInt(day7.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day7.getText().toString());
            day8.setText(diff + "");
        }

        if (Integer.parseInt(day8.getText().toString()) < 30) {
            day9.setText((Integer.parseInt(day8.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day8.getText().toString());
            day9.setText(diff + "");
        }

        if (Integer.parseInt(day9.getText().toString()) < 30) {
            day10.setText((Integer.parseInt(day9.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day9.getText().toString());
            day10.setText(diff + "");
        }

        if (Integer.parseInt(day10.getText().toString()) < 30) {
            day11.setText((Integer.parseInt(day10.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day10.getText().toString());
            day11.setText(diff + "");
        }

        if (Integer.parseInt(day11.getText().toString()) < 30) {
            day12.setText((Integer.parseInt(day11.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day11.getText().toString());
            day12.setText(diff + "");
        }

        if (Integer.parseInt(day12.getText().toString()) < 30) {
            day13.setText((Integer.parseInt(day12.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day12.getText().toString());
            day13.setText(diff + "");
        }

        if (Integer.parseInt(day13.getText().toString()) < 30) {
            day14.setText((Integer.parseInt(day13.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day13.getText().toString());
            day14.setText(diff + "");
        }

        //third row
        if (Integer.parseInt(day14.getText().toString()) < 30) {
            day15.setText((Integer.parseInt(day14.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day14.getText().toString());
            day15.setText(diff + "");
        }

        if (Integer.parseInt(day15.getText().toString()) < 30) {
            day16.setText((Integer.parseInt(day15.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day15.getText().toString());
            day16.setText(diff + "");
        }

        if (Integer.parseInt(day16.getText().toString()) < 30) {
            day17.setText((Integer.parseInt(day16.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day16.getText().toString());
            day17.setText(diff + "");
        }

        if (Integer.parseInt(day17.getText().toString()) < 30) {
            day18.setText((Integer.parseInt(day17.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day17.getText().toString());
            day18.setText(diff + "");
        }

        if (Integer.parseInt(day18.getText().toString()) < 30) {
            day19.setText((Integer.parseInt(day18.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day18.getText().toString());
            day19.setText(diff + "");
        }

        if (Integer.parseInt(day19.getText().toString()) < 30) {
            day20.setText((Integer.parseInt(day19.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day19.getText().toString());
            day20.setText(diff + "");
        }

        if (Integer.parseInt(day20.getText().toString()) < 30) {
            day21.setText((Integer.parseInt(day20.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day20.getText().toString());
            day21.setText(diff + "");
        }

        //forth row
        if (Integer.parseInt(day21.getText().toString()) < 30) {
            day22.setText((Integer.parseInt(day21.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day21.getText().toString());
            day22.setText(diff + "");
        }

        if (Integer.parseInt(day22.getText().toString()) < 30) {
            day23.setText((Integer.parseInt(day22.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day22.getText().toString());
            day23.setText(diff + "");
        }

        if (Integer.parseInt(day23.getText().toString()) < 30) {
            day24.setText((Integer.parseInt(day23.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day23.getText().toString());
            day24.setText(diff + "");
        }

        if (Integer.parseInt(day24.getText().toString()) < 30) {
            day25.setText((Integer.parseInt(day24.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day24.getText().toString());
            day25.setText(diff + "");
        }

        if (Integer.parseInt(day25.getText().toString()) < 30) {
            day26.setText((Integer.parseInt(day25.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day25.getText().toString());
            day26.setText(diff + "");
        }

        if (Integer.parseInt(day26.getText().toString()) < 30) {
            day27.setText((Integer.parseInt(day26.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day26.getText().toString());
            day27.setText(diff + "");
        }

        if (Integer.parseInt(day27.getText().toString()) < 30) {
            day28.setText((Integer.parseInt(day27.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day27.getText().toString());
            day28.setText(diff + "");
        }

        //fifth row
        if (Integer.parseInt(day28.getText().toString()) < 30) {
            day29.setText((Integer.parseInt(day28.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day28.getText().toString());
            day29.setText(diff + "");
        }

        if (Integer.parseInt(day29.getText().toString()) < 30) {
            day30.setText((Integer.parseInt(day29.getText().toString()) + 1) + "");
        }
        else {
            diff = 31 - Integer.parseInt(day29.getText().toString());
            day30.setText(diff + "");
        }
    }

    private void twentyEight() {
        //first row
        day1.setText(Integer.parseInt(day) + "");

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
            day31.setText(diff + "");
        }
    }

    private void setColorByDate() {
        for (int i = 0; i < dateStr.length; i++) {
            if (dateStr[i].equals(day1.getText().toString())) {
                day1.setBackgroundColor(Color.RED);
            }
            if (dateStr[i].equals(day2.getText().toString())) {
                day2.setBackgroundColor(Color.RED);
            }
        }
    }
}
