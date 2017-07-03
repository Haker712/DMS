package com.aceplus.samparoo.marketing;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.forApi.IncentiveForApi;
import com.aceplus.samparoo.utils.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aceplus_mobileteam on 6/30/17.
 */

public class IncentiveFragment extends Fragment {

    SQLiteDatabase sqLiteDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_assessment_fragment, container, false);
        sqLiteDatabase = new Database(getActivity()).getDataBase();

        return view;
    }

    List<IncentiveForApi> getIncentiveFromDb() {
        List<IncentiveForApi> incentiveList = new ArrayList<>();
        Cursor incentiveCursor = sqLiteDatabase.rawQuery("SELECT * FROM INCENTIVE", null);

        while(incentiveCursor.moveToNext()) {
           // IncentiveForApi inc
        }
        return null;
    }
}
