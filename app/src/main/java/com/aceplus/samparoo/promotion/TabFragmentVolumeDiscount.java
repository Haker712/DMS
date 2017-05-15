package com.aceplus.samparoo.promotion;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.VolumeDiscountForReport;
import com.aceplus.samparoo.model.VolumeDiscountItemForReport;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yma on 5/11/17.
 */

public class TabFragmentVolumeDiscount extends Fragment {

    View view;
    Activity activity;
    SQLiteDatabase sqLiteDatabase;

    @InjectView(R.id.cancel_img)
    ImageView cancelImage;

    @InjectView(R.id.listViewVolumeDiscount)
    ListView listViewVolumeDiscount;

    VolumeDiscountCustomAdapter volumeDiscountCustomAdapter;
    ArrayList<VolumeDiscountForReport> volumeDiscountForReportArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_fragment_volume_discount, container, false);
        activity = (AppCompatActivity) getActivity();

        ButterKnife.inject(this, view);

        sqLiteDatabase = new Database(activity).getDataBase();

        volumeDiscountForReportArrayList = getVolumeDiscountFromDB();

        setPromotionPriceListView();

        cancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.backToHome(activity);
            }
        });

        return view;
    }

    private void setPromotionPriceListView() {
        volumeDiscountCustomAdapter = new TabFragmentVolumeDiscount.VolumeDiscountCustomAdapter(activity);
        listViewVolumeDiscount.setAdapter(volumeDiscountCustomAdapter);
        volumeDiscountCustomAdapter.notifyDataSetChanged();
    }

    private ArrayList<VolumeDiscountForReport> getVolumeDiscountFromDB() {
        Cursor cursorVd = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseContract.VolumeDiscount.tb, null);

        while(cursorVd.moveToNext()) {
            VolumeDiscountForReport volumeDiscountForReport = new VolumeDiscountForReport();
            volumeDiscountForReport.setVolumeDiscountId(cursorVd.getInt(cursorVd.getColumnIndex(DatabaseContract.VolumeDiscount.id)));
            volumeDiscountForReport.setVolumeDiscountPlanNo(cursorVd.getString(cursorVd.getColumnIndex(DatabaseContract.VolumeDiscount.discountPlanNo)));
            volumeDiscountForReport.setFromDate(cursorVd.getString(cursorVd.getColumnIndex(DatabaseContract.VolumeDiscount.startDate)).substring(0,10));
            volumeDiscountForReport.setToDate(cursorVd.getString(cursorVd.getColumnIndex(DatabaseContract.VolumeDiscount.endDate)).substring(0,10));
            volumeDiscountForReport.setVolumeDiscountExclude(cursorVd.getString(cursorVd.getColumnIndex(DatabaseContract.VolumeDiscount.exclude)));

            List<VolumeDiscountItemForReport> volumeDiscountItemForReportList = new ArrayList<>();
            Cursor cursorVdItem = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseContract.VolumeDiscountItem.tb + " WHERE VOLUME_DISCOUNT_ID = '" + volumeDiscountForReport.getVolumeDiscountId() +"'", null);
            while(cursorVdItem.moveToNext()) {
                VolumeDiscountItemForReport volumeDiscountItemForReport = new VolumeDiscountItemForReport();
                volumeDiscountItemForReport.setVolumeDiscountId(cursorVdItem.getInt(cursorVdItem.getColumnIndex(DatabaseContract.VolumeDiscountItem.volumeDiscountId)));
                volumeDiscountItemForReport.setFromSaleAmt(cursorVdItem.getString(cursorVdItem.getColumnIndex(DatabaseContract.VolumeDiscountItem.fromSaleAmt)));
                volumeDiscountItemForReport.setToSaleAmt(cursorVdItem.getString(cursorVdItem.getColumnIndex(DatabaseContract.VolumeDiscountItem.toSaleAmt)));
                volumeDiscountItemForReport.setItemDiscountPercent(cursorVdItem.getString(cursorVdItem.getColumnIndex(DatabaseContract.VolumeDiscountItem.discountPercent)));
                volumeDiscountItemForReport.setItemDiscountAmount(cursorVdItem.getString(cursorVdItem.getColumnIndex(DatabaseContract.VolumeDiscountItem.discountAmount)));
                volumeDiscountItemForReport.setItemDiscountPrice(cursorVdItem.getString(cursorVdItem.getColumnIndex(DatabaseContract.VolumeDiscountItem.discountPrice)));
                volumeDiscountItemForReportList.add(volumeDiscountItemForReport);
            }
            volumeDiscountForReport.setVolumeDiscountItemForReport(volumeDiscountItemForReportList);
            volumeDiscountForReportArrayList.add(volumeDiscountForReport);
        }
        return volumeDiscountForReportArrayList;
    }

    private class VolumeDiscountCustomAdapter extends ArrayAdapter<VolumeDiscountForReport> {

        public final Activity context;

        public VolumeDiscountCustomAdapter(Activity context) {

            super(context, R.layout.list_row_promotion_price, volumeDiscountForReportArrayList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            VolumeDiscountForReport volumeDiscountForReport = volumeDiscountForReportArrayList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_volume_discount, null, true);

            TextView txt_from_date = (TextView) view.findViewById(R.id.row_vd_from_date);
            TextView txt_to_date = (TextView) view.findViewById(R.id.row_vd_to_date);
            TextView txt_discount_from = (TextView) view.findViewById(R.id.row_vd_item_discount_from);
            TextView txt_discount_to = (TextView) view.findViewById(R.id.row_vd_item_discount_to);
            TextView txt_discount = (TextView) view.findViewById(R.id.row_vd_item_discount_percent);
            TextView txt_discount_amount = (TextView) view.findViewById(R.id.row_vd_item_discount_amount);
            TextView txt_discount_price = (TextView) view.findViewById(R.id.row_vd_item_discount_price);
            TextView txt_exclude = (TextView) view.findViewById(R.id.row_vd_volume_discount_exclude);

            txt_from_date.setText(volumeDiscountForReport.getFromDate());
            txt_to_date.setText(volumeDiscountForReport.getToDate());
            if(volumeDiscountForReport.getVolumeDiscountItemForReport() != null) {
                txt_discount_from.setText(volumeDiscountForReport.getVolumeDiscountItemForReport().get(0).getFromSaleAmt());
                txt_discount_to.setText(volumeDiscountForReport.getVolumeDiscountItemForReport().get(0).getToSaleAmt());
                txt_discount.setText(volumeDiscountForReport.getVolumeDiscountItemForReport().get(0).getItemDiscountPercent());
                txt_discount_amount.setText(volumeDiscountForReport.getVolumeDiscountItemForReport().get(0).getItemDiscountAmount());
                txt_discount_price.setText(volumeDiscountForReport.getVolumeDiscountItemForReport().get(0).getItemDiscountPrice());
            }

            if(volumeDiscountForReport.getVolumeDiscountExclude() != null) {
                if(volumeDiscountForReport.getVolumeDiscountExclude().equals("0")) {
                    txt_exclude.setText("Yes");
                } else {
                    txt_exclude.setText("No");
                }
            }

            return view;
        }
    }
}
