package com.aceplus.samparoo.marketing;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aceplus.samparoo.LoginActivity;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.SyncActivity;
import com.aceplus.samparoo.customer.AddNewCustomerActivity;
import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.model.forApi.DisplayAssessment;
import com.aceplus.samparoo.myinterface.OnActionClickListener;
import com.aceplus.samparoo.utils.Constant;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.aceplus.samparoo.utils.DatabaseContract.MARKETING.IMAGE;

/**
 * Created by phonelin on 3/16/17.
 */

public class DisplayAssessmentFragment extends Fragment implements OnActionClickListener {

    View view;

    SQLiteDatabase sqLiteDatabase;

    ImageView cancelImg, cameraImg, saveImg;

    Customer customer;

    public static final String CUSTOMER_INFO_KEY = "customer-info-key";

    int locationCode = 0;

    int Cus_id;

    Activity activity;

    EditText takenImageName, remark;

    TextView customerName, imageNoTxtView;

    String encodedImage = "";

    Bitmap picture;

    ImageView takenImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Utils.setOnActionClickListener(this);
        view = inflater.inflate(R.layout.display_assessment_fragment, container, false);
        sqLiteDatabase = new Database(getActivity()).getDataBase();
        customer = MainFragmentActivity.customer;
        activity = getActivity();

        //LinearLayout image1Layout1 = (LinearLayout) view.findViewById(R.id.display_assessment_image_layout1);
        LinearLayout image1Layout2 = (LinearLayout) view.findViewById(R.id.display_assessment_image_layout2);
        takenImageName = (EditText) view.findViewById(R.id.edit_display_assess_taken_photo_name);
        remark = (EditText) view.findViewById(R.id.edit_display_assess_remark);
        customerName = (TextView) view.findViewById(R.id.txt_display_assess_customer_name);
        imageNoTxtView = (TextView) view.findViewById(R.id.txt_display_assess_image_no);
        takenImageView = (ImageView) view.findViewById(R.id.outlet_image);

        WindowManager windowManager = (WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;

        //tableLayout.setLayoutParams(new TableLayout.LayoutParams(400, TableLayout.LayoutParams.WRAP_CONTENT));

        //image1Layout1.setLayoutParams(new LinearLayout.LayoutParams(width/2, height/3));
        image1Layout2.setLayoutParams(new LinearLayout.LayoutParams(width, height/2));

        //ImageView imageView1 = (ImageView) view.findViewById(R.id.display_standard_image);
        cancelImg = (ImageView) view.findViewById(R.id.cancel_img);
        cameraImg = (ImageView) view.findViewById(R.id.camera_img);
        saveImg = (ImageView) view.findViewById(R.id.save_img);
        //saveImg.setVisibility(View.GONE);

        saveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.askConfirmationDialog("Save", "Do you want to save?", MainFragmentActivity.DAF, activity);
            }
        });

        customerName.setText(customer.getCustomerName());

        /*Cursor cursor = sqLiteDatabase.rawQuery("select * from STANDARD_EXTERNAL_CHECK", null);

        while (cursor.moveToNext()) {

            String image = cursor.getString(cursor.getColumnIndex("IMAGE"));

            byte[] decodeValue = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodeValue, 0, decodeValue.length);
            imageView1.setImageBitmap(bitmap);
            imageView1.setAdjustViewBounds(true);

        }*/

        cameraImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });


       /* Cursor cursor1=sqLiteDatabase.rawQuery("select * from OUTLET_VISIBILITY",null);

        while (cursor1.moveToNext()){

            String encoded_Image=cursor1.getString(cursor1.getColumnIndex("IMAGE"));

            byte[] decodeValue = Base64.decode(encoded_Image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodeValue, 0, decodeValue.length);
            takenImageView.setImageBitmap(bitmap);

        }
*/
        Cursor cursorForLocation = sqLiteDatabase.rawQuery("select * from Location", null);
        while (cursorForLocation.moveToNext()) {
            locationCode = cursorForLocation.getInt(cursorForLocation.getColumnIndex(DatabaseContract.Location.id));

        }

        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.backToMarketingActivity(activity);
            }
        });


        return view;
    }

    private static final int CAMERA_REQUEST = 1888;

    private void takePicture() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            picture = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();

            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
            takenImageView = (ImageView) view.findViewById(R.id.outlet_image);
            takenImageView.setImageBitmap(picture);
            takenImageView.setAdjustViewBounds(true);

            int imageCount = 0;
            Cursor cursor = sqLiteDatabase.rawQuery("select COUNT(*) AS COUNT from OUTLET_VISIBILITY", null);
            while (cursor.moveToNext()) {
                imageCount = cursor.getInt(cursor.getColumnIndex("COUNT"));
            }

            imageNoTxtView.setText(customer.getCustomerId() + new SimpleDateFormat("yyMMdd").format(new Date()) + imageCount);
        }
    }

    void insertOutletVisibility(DisplayAssessment displayAssessment) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("IMAGE", displayAssessment.getImage());
        contentValues.put("IMAGE_NO", displayAssessment.getImageNo());
        contentValues.put("SALE_MAN_ID",displayAssessment.getSaleManId());
        contentValues.put("CUSTOMER_ID",displayAssessment.getCustomerId());
        contentValues.put("INVOICE_DATE",displayAssessment.getInvoiceDate());
        contentValues.put("INVOICE_NO",displayAssessment.getInvoiceNo());
        contentValues.put("IMAGE_NAME", displayAssessment.getImageName());
        contentValues.put("DATE_AND_TIME",displayAssessment.getDateAndTime());
        contentValues.put("REMARK", displayAssessment.getRemark());
        contentValues.put("CUSTOMER_NO",displayAssessment.getCustomerNo());

        sqLiteDatabase.beginTransaction();

        sqLiteDatabase.insert("OUTLET_VISIBILITY", null, contentValues);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
    }

    boolean checkUserInput() {
        boolean flag = true;

        if(takenImageName.getText().toString().length() == 0) {
            flag = false;
            takenImageName.setError("Please insert image name");
        }

        return flag;
    }

    @Override
    public void onActionClick(String type) {
        if(type.equals(MainFragmentActivity.DAF)) {
            if (checkUserInput()) {
                String saleman_Id = "";


                String invoice_Id = "";

                try {
                    saleman_Id = LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, "");
                    invoice_Id = Utils.getInvoiceNo(getActivity(), LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NO, ""), locationCode + "", Utils.FOR_DISPLAY_ASSESSMENT);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Utils.backToLogin(activity);
                }

                String customerId = customer.getCustomerId();
                String customerName = "";
                Cursor cursor = sqLiteDatabase.rawQuery("select * from CUSTOMER where CUSTOMER_ID='" + customerId + "'", null);
                while (cursor.moveToNext()) {

                    Cus_id = cursor.getInt(cursor.getColumnIndex("id"));
                    customerName = cursor.getString(cursor.getColumnIndex("CUSTOMER_NAME"));
                }

                String saleDate = Utils.getCurrentDate(true);

                DisplayAssessment displayAssessment = new DisplayAssessment();
                displayAssessment.setImage(encodedImage);

                if (!saleman_Id.equals(null)) {
                    displayAssessment.setSaleManId(Integer.parseInt(saleman_Id));
                }

                displayAssessment.setCustomerId(Cus_id);
                displayAssessment.setInvoiceDate(saleDate);
                displayAssessment.setInvoiceNo(invoice_Id);
                displayAssessment.setImageNo(imageNoTxtView.getText().toString());
                displayAssessment.setImageName(takenImageName.getText().toString());
                displayAssessment.setDateAndTime(saleDate);
                displayAssessment.setCustomerNo(customerId);
                displayAssessment.setCustomerName(customerName);
                displayAssessment.setRemark(remark.getText().toString());

                insertOutletVisibility(displayAssessment);

                imageNoTxtView.setText(null);
                takenImageName.setText(null);
                remark.setText(null);
                takenImageView.setImageBitmap(null);

                Toast.makeText(activity, "Image has been saved.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
