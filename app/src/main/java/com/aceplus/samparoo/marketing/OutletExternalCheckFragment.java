package com.aceplus.samparoo.marketing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by phonelin on 3/15/17.
 */

public class OutletExternalCheckFragment extends Fragment {

    View view;

    SQLiteDatabase sqLiteDatabase;

    ImageView cancelImg,cameraImg,saveImg;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.outlet_external_check_fragment, container, false);
        sqLiteDatabase = new Database(getActivity()).getDataBase();

        ImageView imageView= (ImageView) view.findViewById(R.id.outlet_standard_image);
        cancelImg = (ImageView) view.findViewById(R.id.cancel_img);
        cameraImg = (ImageView) view.findViewById(R.id.camera_img);
        saveImg = (ImageView) view.findViewById(R.id.save_img);


        Cursor cursor=sqLiteDatabase.rawQuery("select * from STANDARD_EXTERNAL_CHECK",null);

        while (cursor.moveToNext()){

            String image=cursor.getString(cursor.getColumnIndex("IMAGE"));
            Log.i("StringImage",image);

            byte[] decodeValue = Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodeValue,0,decodeValue.length);
            imageView.setImageBitmap(bitmap);


        }

        cameraImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });




        return view;
    }

    private static final int CAMERA_REQUEST = 1888;

    private void takePicture(){
        Intent cameraIntent = new  Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap picture = (Bitmap) data.getExtras().get("data");
            ImageView imageView= (ImageView) view.findViewById(R.id.outlet_image);
            imageView.setImageBitmap(picture);
        }
    }



}
