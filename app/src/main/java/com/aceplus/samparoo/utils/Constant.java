package com.aceplus.samparoo.utils;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by haker on 2/3/17.
 */
public class Constant {

    //public static String BASE_URL = "http://192.168.11.57:9292/api/v1/";
    public static String REAL_TIME_URL= "http://acedms-001-site1.atempurl.com/api/v1/upload.svc/";
    public static String REAL_TIME_AP_URL= "http://128.199.226.122:8080/api/v1/";
    //public static String BASE_URL = "";
    public static String BASE_URL = "http://192.168.0.81:9000/api/v1/";//test ip for bi2

    public static String KEY_CHANGE_URL = "change_url";

    public static String SITE_ACTIVATION_KEY = "1234567";

    public static String TABLET_ACTIVATION_KEY = "1234567";

    public static String SALEMAN_ID = "saleman_id";

    public static String SALEMAN_NO = "saleman_no";

    public static String SALEMAN_NAME = "saleman_name";

    public static String SALEMAN_PWD = "saleman_pwd";

    public static String TABLET_KEY = "tablet_key";

    public static String MAX_KEY = "max_key";

    public static String ADDNEWCUSTOMERCOUNT="addnewcustomerCount";

    public static String KEY_MAX_ZOOM = "12";

    public static String KEY_SALE_RETURN_AMOUNT="sale_return_amount";

    public String BASE_URL_TEST = "";

    public static String START_TIME = "START_TIME";

    public static String END_TIME = "END_TIME";

    public static void changeUrl(String ip) {
        BASE_URL = "";
        BASE_URL = ip;
    }

    public static void getUrlFromDb(SQLiteDatabase sqLiteDatabase) {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from url", null);
        while (cursor.moveToNext()) {
            BASE_URL = "http://" + cursor.getString(cursor.getColumnIndex("current_url")) + "/api/v1/";
        }
    }

    public void getUrlFromDbTest(SQLiteDatabase sqLiteDatabase) {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from url", null);
        while (cursor.moveToNext()) {
            BASE_URL_TEST = "http://" + cursor.getString(cursor.getColumnIndex("current_url")) + "/api/v1/";
        }
    }
}
