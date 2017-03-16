package com.aceplus.samparoo.marketing;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.utils.Database;

/**
 * Created by phonelin on 3/16/17.
 */

public class DisplayAssessmentFragment extends Fragment {

    View view;

    SQLiteDatabase sqLiteDatabase;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.display_assessment_fragment,container,false);
        sqLiteDatabase = new Database(getActivity()).getDataBase();

        ImageView imageView= (ImageView) view.findViewById(R.id.outlet_standard_image);


        Cursor cursor=sqLiteDatabase.rawQuery("select * from STANDARD_EXTERNAL_CHECK",null);

        while (cursor.moveToNext()){

            String image=cursor.getString(cursor.getColumnIndex("IMAGE"));

            byte[] decodeValue = Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodeValue,0,decodeValue.length);
            imageView.setImageBitmap(bitmap);


        }

        return view;
    }
}
