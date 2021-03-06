package com.aceplus.samparoo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.aceplus.samparoo.model.forApi.DataForLogin;
import com.aceplus.samparoo.model.forApi.LoginRequest;
import com.aceplus.samparoo.model.forApi.LoginResponse;
import com.aceplus.samparoo.model.Route;
import com.aceplus.samparoo.model.RouteAssign;
import com.aceplus.samparoo.model.RouteSchedule;
import com.aceplus.samparoo.model.RouteScheduleItem;
import com.aceplus.samparoo.model.SaleMan;
import com.aceplus.samparoo.retrofit.DownloadService;
import com.aceplus.samparoo.retrofit.RetrofitServiceFactory;
import com.aceplus.samparoo.utils.Constant;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;
import com.firetrap.permissionhelper.action.OnDenyAction;
import com.firetrap.permissionhelper.action.OnGrantAction;
import com.firetrap.permissionhelper.helper.PermissionHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by haker on 1/26/17.
 */

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.editTextUserID)
    EditText editTextUserID;

    @InjectView(R.id.editTextPassword)
    EditText editTextPassword;

    SQLiteDatabase sqLiteDatabase;

    String saleman_Id = "";
    String saleman_No = "";
    String saleman_Name = "";
    String saleman_Pwd = "";

    final int mode = Activity.MODE_PRIVATE;
    final String MyPREFS = "SamparooPreference";
    public static SharedPreferences mySharedPreference;
    public static SharedPreferences.Editor myEditor;

    private PermissionHelper.PermissionBuilder permissionRequest;
    private static final int REQUEST_STORAGE = 41;
    private OnDenyAction onDenyAction = new OnDenyAction() {
        @Override
        public void call(int i, boolean b) {
            Toast.makeText(LoginActivity.this, "Application Exit Cannot Read Permission for read internal storage is deny.", Toast.LENGTH_SHORT).show();
            finish();
        }
    };
    private OnGrantAction onGrantAction = new OnGrantAction() {
        @Override
        public void call(int i) {
            Toast.makeText(LoginActivity.this, "Read Storage Permission Granted.", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionRequest.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.inject(this);

        sqLiteDatabase = new Database(this).getDataBase();

        mySharedPreference = getSharedPreferences(MyPREFS, mode);
        myEditor = mySharedPreference.edit();

        editTextUserID.setText("ASDF");
        editTextPassword.setText("samparoo");

        if (Utils.isOsMarshmallow()) {
            askPermission();
        }
    }

    void askPermission() {
        permissionRequest = PermissionHelper.with(LoginActivity.this)
                .build(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .onPermissionsGranted(onGrantAction)
                .onPermissionsDenied(onDenyAction)
                .request(REQUEST_STORAGE);
    }

    @OnClick(R.id.buttonLogin)
    void login() {
        if (isValidate()) {
            if (isSaleManExist()) {
                if (isSaleManCorrect()) {
                    myEditor.putString(Constant.SALEMAN_ID, saleman_Id);
                    myEditor.putString(Constant.SALEMAN_NO, saleman_No);
                    myEditor.putString(Constant.SALEMAN_NAME, saleman_Name);
                    myEditor.putString(Constant.SALEMAN_PWD, saleman_Pwd);
                    myEditor.commit();

                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Utils.commonDialog("User ID or Password is incorrect!", this);
                }
            }
            else {
                loginWithApi(Utils.createParamData(editTextUserID.getText().toString(), Utils.encodePassword(editTextPassword.getText().toString()), 0));
            }
        }
    }

    private boolean isValidate() {
        if (editTextUserID.getText().length() == 0) {
            editTextUserID.setError("User ID is required.");
            editTextUserID.requestFocus();
            return false;
        }
        if (editTextPassword.getText().length() == 0) {
            editTextPassword.setError("Password is required.");
            editTextPassword.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isSaleManExist() {
        boolean returnValue = false;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from SALE_MAN", null);
        if (cursor.getCount() > 0) {
            returnValue = true;
        }

        return returnValue;
    }

    private boolean isSaleManCorrect() {
        boolean returnValue = false;
        Cursor cursor = sqLiteDatabase.rawQuery("select * from SALE_MAN where USER_ID = '"+editTextUserID.getText().toString()+"'" +
                " and PASSWORD = '"+Utils.encodePassword(editTextPassword.getText().toString())+"'", null);
        if (cursor.getCount() == 1) {
            returnValue = true;
        }
        while (cursor.moveToNext()) {
            saleman_Id = cursor.getString(cursor.getColumnIndex("ID"));
            saleman_No = cursor.getString(cursor.getColumnIndex("USER_ID"));
            saleman_Name = cursor.getString(cursor.getColumnIndex("USER_NAME"));
            saleman_Pwd= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        }

        return returnValue;
    }

    private String createParamData() {
        String paramData = "";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setSiteActivationKey(Constant.SITE_ACTIVATION_KEY);
        loginRequest.setTabletActivationKey(Constant.TABLET_ACTIVATION_KEY);
        loginRequest.setUserId(editTextUserID.getText().toString());
        //loginRequest.setPassword(Utils.encodePassword(editTextPassword.getText().toString()));
        String encodedPwd = Utils.encodePassword(editTextPassword.getText().toString());
        Log.i("encodedPwd>>>", encodedPwd);
        loginRequest.setPassword(encodedPwd);
        loginRequest.setDate(Utils.getCurrentDate(false));
        List<Object> objectList = new ArrayList<>();
        /*Object object = new Object();
        objectList.add(object);*/
        loginRequest.setData(objectList);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("site_activation_key", Constant.SITE_ACTIVATION_KEY);
            jsonObject.put("tablet_activation_key", Constant.TABLET_ACTIVATION_KEY);
            jsonObject.put("user_id", loginRequest.getUserId());
            jsonObject.put("password", loginRequest.getPassword());
            jsonObject.put("date", loginRequest.getDate());
            jsonObject.put("data", loginRequest.getData());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("param_data>>>", jsonObject.toString());

        paramData = jsonObject.toString();
        return paramData;
    }

    private void loginWithApi(String paramData) {
        Utils.callDialog("Please wait...", this);

        DownloadService downloadService = RetrofitServiceFactory.createService(DownloadService.class);
        Call<LoginResponse> call = downloadService.login(paramData);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getAceplusStatusCode() == 200) {
                        Utils.cancelDialog();
                        if (response.body().getRoute() != 0) {

                            Toast.makeText(LoginActivity.this, response.body().getAceplusStatusMessage(), Toast.LENGTH_SHORT).show();

                            List<DataForLogin> dataForLoginArrayList = new ArrayList<DataForLogin>();
                            dataForLoginArrayList = response.body().getDataForLogin();
                         //   Log.i("dataForLoginArrayList>>>", dataForLoginArrayList.size() + "");

                            sqLiteDatabase.beginTransaction();

                            insertSaleMan(dataForLoginArrayList.get(0).getSaleMan());

                            insertRouteScheduleAndItem(dataForLoginArrayList.get(0).getRouteSchedule());

                            insertRouteAssign(dataForLoginArrayList.get(0).getRouteAssign());

                            insertRoute(dataForLoginArrayList.get(0).getRoute());

                            sqLiteDatabase.setTransactionSuccessful();
                            sqLiteDatabase.endTransaction();

                            myEditor.putString(Constant.SALEMAN_ID, dataForLoginArrayList.get(0).getSaleMan().get(0).getId());
                            myEditor.putString(Constant.SALEMAN_NO, dataForLoginArrayList.get(0).getSaleMan().get(0).getSaleManNo());
                            myEditor.putString(Constant.SALEMAN_NAME, dataForLoginArrayList.get(0).getSaleMan().get(0).getSaleManName());
                            myEditor.putString(Constant.SALEMAN_PWD, dataForLoginArrayList.get(0).getSaleMan().get(0).getPassword());
                            myEditor.commit();

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Utils.commonDialog("You have no route.", LoginActivity.this);
                        }
                    }

                }
                else {
                    onFailure(call, new Throwable(response.body().getAceplusStatusMessage()));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Utils.cancelDialog();
                Utils.commonDialog(getResources().getString(R.string.connection_error), LoginActivity.this);
            }
        });
    }

    private void insertSaleMan(List<SaleMan> saleManList) {
        for (SaleMan saleMan : saleManList) {
            ContentValues cv = new ContentValues();
            cv.put("ID", saleMan.getId());
            cv.put("USER_ID",saleMan.getSaleManNo());
            cv.put("PASSWORD",saleMan.getPassword());
            cv.put("USER_NAME",saleMan.getSaleManName());
            sqLiteDatabase.insert("SALE_MAN",null,cv);
        }
    }

    private void insertRouteScheduleAndItem(List<RouteSchedule> routeScheduleArrayList) {
        for (RouteSchedule routeSchedule : routeScheduleArrayList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.RouteSchedule.id, routeSchedule.getId());
            contentValues.put(DatabaseContract.RouteSchedule.scheduleNo, routeSchedule.getScheduleNo());
            contentValues.put(DatabaseContract.RouteSchedule.date, routeSchedule.getDate());
            contentValues.put(DatabaseContract.RouteSchedule.fromDate, routeSchedule.getFromDate());
            contentValues.put(DatabaseContract.RouteSchedule.fromDate, routeSchedule.getToDate());
            contentValues.put(DatabaseContract.RouteSchedule.active, routeSchedule.getActive());
            contentValues.put(DatabaseContract.RouteSchedule.tS, routeSchedule.getTS());

            sqLiteDatabase.insert(DatabaseContract.RouteSchedule.tb, null, contentValues);

            for(RouteScheduleItem routeScheduleItem : routeSchedule.getRouteScheduleItem()) {
                ContentValues contentValuesItem = new ContentValues();
                contentValuesItem.put(DatabaseContract.RouteScheduleItem.id, routeScheduleItem.getId());
                contentValuesItem.put(DatabaseContract.RouteScheduleItem.routeScheduleId, routeScheduleItem.getRouteScheduleId());
                contentValuesItem.put(DatabaseContract.RouteScheduleItem.saleManID, routeScheduleItem.getSaleManId());
                contentValuesItem.put(DatabaseContract.RouteScheduleItem.routeID, routeScheduleItem.getRouteId());

                sqLiteDatabase.insert(DatabaseContract.RouteScheduleItem.tb, null, contentValuesItem);
            }
        }
    }

    private void insertRouteAssign(List<RouteAssign> routeAssignList) {
        for (RouteAssign routeAssign : routeAssignList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.RouteAssign.id, routeAssign.getId());
            contentValues.put(DatabaseContract.RouteAssign.assignDate, routeAssign.getAssignDate());
            contentValues.put(DatabaseContract.RouteAssign.customerID, routeAssign.getCustomerId());
            contentValues.put(DatabaseContract.RouteAssign.routeID, routeAssign.getRouteId());
            contentValues.put(DatabaseContract.RouteAssign.active, routeAssign.getActive());
            contentValues.put(DatabaseContract.RouteAssign.tS, routeAssign.getTS());

            sqLiteDatabase.insert(DatabaseContract.RouteAssign.tb, null, contentValues);
        }
    }

    private void insertRoute(List<Route> routeList) {
        for (Route route : routeList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.Route.id, route.getId());
            contentValues.put(DatabaseContract.Route.routeNo, route.getRouteNo());
            contentValues.put(DatabaseContract.Route.routeName, route.getRouteName());
            contentValues.put(DatabaseContract.Route.active, route.getActive());
            contentValues.put(DatabaseContract.Route.tS, route.getTS());

            sqLiteDatabase.insert(DatabaseContract.Route.tb, null, contentValues);
        }
    }

    @OnClick(R.id.imageView)
    void backupDB() {
        /*Calendar now = Calendar.getInstance();
        String today = now.get(Calendar.DATE) + "." + (now.get(Calendar.MONTH) + 1)
                + "." + now.get(Calendar.YEAR);*/
        String today = Utils.getCurrentDate(true);

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                Toast.makeText(getApplicationContext(), "Backup database is starting...",
                        Toast.LENGTH_SHORT).show();
                String currentDBPath = "/data/com.aceplus.samparoo/databases/aceplus-dms.sqlite";

                String backupDBPath = "Samparoo_DB_Backup_" + today + ".db";
                File currentDB = new File(data, currentDBPath);

                String folderPath = "mnt/sdcard/Samparoo_DB_Backup";
                File f = new File(folderPath);
                f.mkdir();
                File backupDB = new File(f, backupDBPath);
                FileChannel source = new FileInputStream(currentDB).getChannel();
                FileChannel destination = new FileOutputStream(backupDB).getChannel();
                destination.transferFrom(source, 0, source.size());
                source.close();
                destination.close();
                Toast.makeText(getApplicationContext(), "Backup database Successful!",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Please set Permission for Storage in Setting!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Cannot Backup!", Toast.LENGTH_SHORT).show();
        }

    }
}
