package com.aceplus.samparoo.marketing;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aceplus.samparoo.LoginActivity;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.SyncActivity;
import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.utils.Constant;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import java.io.ByteArrayOutputStream;

import static com.aceplus.samparoo.utils.DatabaseContract.MARKETING.IMAGE;

/**
 * Created by phonelin on 3/16/17.
 */

public class DisplayAssessmentFragment extends Fragment {

    View view;

    SQLiteDatabase sqLiteDatabase;

    ImageView cancelImg, cameraImg, saveImg;

    Customer customer;

    public static final String CUSTOMER_INFO_KEY = "customer-info-key";

    int locationCode = 0;

    int Cus_id;

    Activity activity;







    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.display_assessment_fragment, container, false);
        sqLiteDatabase = new Database(getActivity()).getDataBase();

        activity = getActivity();

        ImageView imageView1 = (ImageView) view.findViewById(R.id.display_standard_image);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.outlet_image);
        cancelImg = (ImageView) view.findViewById(R.id.cancel_img);
        cameraImg = (ImageView) view.findViewById(R.id.camera_img);
        saveImg = (ImageView) view.findViewById(R.id.save_img);
        saveImg.setVisibility(View.GONE);


        Cursor cursor = sqLiteDatabase.rawQuery("select * from STANDARD_EXTERNAL_CHECK", null);

        while (cursor.moveToNext()) {

            String image = cursor.getString(cursor.getColumnIndex("IMAGE"));

            byte[] decodeValue = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodeValue, 0, decodeValue.length);
            imageView1.setImageBitmap(bitmap);


        }

        cameraImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });


        Cursor cursor1=sqLiteDatabase.rawQuery("select * from DISPLAY_ASSESSMENT",null);

        while (cursor1.moveToNext()){

        String encoded_Image=cursor1.getString(cursor1.getColumnIndex("IMAGE"));

            byte[] decodeValue = Base64.decode(encoded_Image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodeValue, 0, decodeValue.length);
            imageView2.setImageBitmap(bitmap);

        }

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
            Bitmap picture = (Bitmap) data.getExtras().get("data");

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
            byte[] b = baos.toByteArray();

            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

            String saleman_Id = "";

            String invoice_Id = "";

            try {
                saleman_Id = LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, "");
                invoice_Id = Utils.getInvoiceNo(getActivity(), LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NO, ""), locationCode + "", Utils.FOR_DISPLAY_ASSESSMENT);
            } catch (NullPointerException e) {
                e.printStackTrace();
                Utils.backToLogin(this.getActivity());
            }

            customer=MainFragmentActivity.customer;


            String customerId = customer.getCustomerId();

            Cursor cursor=sqLiteDatabase.rawQuery("select * from CUSTOMER where CUSTOMER_ID='"+customerId+"'",null);
            while (cursor.moveToNext()){

                Cus_id=cursor.getInt(cursor.getColumnIndex("id"));

            }


            String saleDate = Utils.getCurrentDate(true);


            ContentValues contentValues = new ContentValues();

            contentValues.put("IMAGE", encodedImage);
            contentValues.put("SALE_MAN_ID",saleman_Id);
            contentValues.put("CUSTOMER_ID",Cus_id);
            contentValues.put("INVOICE_DATE",saleDate);
            contentValues.put("INVOICE_NO",invoice_Id);

            sqLiteDatabase.beginTransaction();

            sqLiteDatabase.insert("DISPLAY_ASSESSMENT", null, contentValues);



            sqLiteDatabase.setTransactionSuccessful();
            sqLiteDatabase.endTransaction();


            ImageView imageView = (ImageView) view.findViewById(R.id.outlet_image);
            imageView.setImageBitmap(picture);
        }
    }


}
