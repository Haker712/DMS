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
import com.aceplus.samparoo.model.VolumeDiscountFilterForReport;
import com.aceplus.samparoo.model.VolumeDiscountFilterItemForReport;
import com.aceplus.samparoo.model.VolumeDiscountForReport;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yma on 5/11/17.
 */

public class TabFragmentVolumeDiscountFilter extends Fragment {

    View view;
    Activity activity;
    SQLiteDatabase sqLiteDatabase;

    @InjectView(R.id.listViewVolumeDiscount)
    ListView listViewVolumeDiscount;

    @InjectView(R.id.cancel_img)
    ImageView cancelImage;

    VolumeDiscountFilterCustomAdapter volumeDiscountCustomAdapter;
    ArrayList<VolumeDiscountFilterForReport> volumeDiscountFilterForReportArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_fragment_volume_discount_filter, container, false);
        activity = (AppCompatActivity) getActivity();

        ButterKnife.inject(this, view);

        sqLiteDatabase = new Database(activity).getDataBase();

        volumeDiscountFilterForReportArrayList = getVolumeDiscountFromDB();

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
        volumeDiscountCustomAdapter = new TabFragmentVolumeDiscountFilter.VolumeDiscountFilterCustomAdapter(activity);
        listViewVolumeDiscount.setAdapter(volumeDiscountCustomAdapter);
        volumeDiscountCustomAdapter.notifyDataSetChanged();
    }

    private ArrayList<VolumeDiscountFilterForReport> getVolumeDiscountFromDB() {

        Cursor cursorVdFilter = sqLiteDatabase.rawQuery("SELECT * FROM " + DatabaseContract.VolumeDiscountFilter.tb, null);
        VolumeDiscountFilterForReport volumeDiscountFilterForReport = new VolumeDiscountFilterForReport();

        while (cursorVdFilter.moveToNext()) {
            volumeDiscountFilterForReport.setVolumeDiscountId(cursorVdFilter.getInt(cursorVdFilter.getColumnIndex(DatabaseContract.VolumeDiscountFilter.id)));
            volumeDiscountFilterForReport.setDiscountPlanNo(cursorVdFilter.getString(cursorVdFilter.getColumnIndex(DatabaseContract.VolumeDiscountFilter.discountPlanNo)));
            volumeDiscountFilterForReport.setFromDate(cursorVdFilter.getString(cursorVdFilter.getColumnIndex(DatabaseContract.VolumeDiscountFilter.startDate)));
            volumeDiscountFilterForReport.setToDate(cursorVdFilter.getString(cursorVdFilter.getColumnIndex(DatabaseContract.VolumeDiscountFilter.endDate)));
            volumeDiscountFilterForReport.setFilterExclude(cursorVdFilter.getString(cursorVdFilter.getColumnIndex(DatabaseContract.VolumeDiscountFilter.exclude)));

            Cursor cursorVdFilterItem = sqLiteDatabase.rawQuery("SELECT VDF.*, (SELECT CATEGORY_NAME FROM PRODUCT_CATEGORY WHERE CATEGORY_ID = VDF.CATEGORY_ID) AS CNAME," +
                    " (SELECT GROUP_NAME FROM PRODUCT_GROUP WHERE GROUP_ID = VDF.GROUP_CODE_ID) AS GNAME FROM " + DatabaseContract.VolumeDiscountFilterItem.tb + " AS VDF WHERE " +
                    DatabaseContract.VolumeDiscountFilterItem.volumeDiscountId + " ='" + volumeDiscountFilterForReport.getVolumeDiscountId() + "'", null);

            List<VolumeDiscountFilterItemForReport> volumeDiscountFilterItemForReportList = new ArrayList<>();
            while (cursorVdFilterItem.moveToNext()) {
                VolumeDiscountFilterItemForReport volumeDiscountFilterItemForReport= new VolumeDiscountFilterItemForReport();
                volumeDiscountFilterItemForReport.setVolumeDiscountId(cursorVdFilterItem.getInt(cursorVdFilterItem.getColumnIndex(DatabaseContract.VolumeDiscountFilterItem.volumeDiscountId)));
                volumeDiscountFilterItemForReport.setCategoryId(cursorVdFilterItem.getInt(cursorVdFilterItem.getColumnIndex(DatabaseContract.VolumeDiscountFilterItem.categoryId)));
                volumeDiscountFilterItemForReport.setGroupCodeId(cursorVdFilterItem.getInt(cursorVdFilterItem.getColumnIndex(DatabaseContract.VolumeDiscountFilterItem.groupCodeId)));
                volumeDiscountFilterItemForReport.setFromSaleAmount(cursorVdFilterItem.getString(cursorVdFilterItem.getColumnIndex(DatabaseContract.VolumeDiscountFilterItem.fromSaleAmount)));
                volumeDiscountFilterItemForReport.setToSaleAmount(cursorVdFilterItem.getString(cursorVdFilterItem.getColumnIndex(DatabaseContract.VolumeDiscountFilterItem.toSaleAmount)));
                volumeDiscountFilterItemForReport.setFilterDiscountPercent(cursorVdFilterItem.getString(cursorVdFilterItem.getColumnIndex(DatabaseContract.VolumeDiscountFilterItem.discountPercent)));
                volumeDiscountFilterItemForReport.setFilterDiscountAmount(cursorVdFilterItem.getString(cursorVdFilterItem.getColumnIndex(DatabaseContract.VolumeDiscountFilterItem.discountAmount)));
                volumeDiscountFilterItemForReport.setFilterDiscountPrice(cursorVdFilterItem.getString(cursorVdFilterItem.getColumnIndex(DatabaseContract.VolumeDiscountFilterItem.discountPrice)));
                volumeDiscountFilterItemForReport.setCategoryName(cursorVdFilterItem.getString(cursorVdFilterItem.getColumnIndex("CNAME")));
                volumeDiscountFilterItemForReport.setGroupCodeName(cursorVdFilterItem.getString(cursorVdFilterItem.getColumnIndex("GNAME")));
                volumeDiscountFilterItemForReportList.add(volumeDiscountFilterItemForReport);
            }
            volumeDiscountFilterForReport.setVolumeDiscountFilterItemForReportList(volumeDiscountFilterItemForReportList);
            volumeDiscountFilterForReportArrayList.add(volumeDiscountFilterForReport);
        }

        return volumeDiscountFilterForReportArrayList;
    }

    private class VolumeDiscountFilterCustomAdapter extends ArrayAdapter<VolumeDiscountFilterForReport> {

        public final Activity context;

        public VolumeDiscountFilterCustomAdapter(Activity context) {

            super(context, R.layout.list_row_volume_discount_filter, volumeDiscountFilterForReportArrayList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            VolumeDiscountFilterForReport volumeDiscountFilterForReport = volumeDiscountFilterForReportArrayList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_volume_discount_filter, null, true);

            TextView txt_filter_discount_from_date = (TextView) view.findViewById(R.id.row_vd_filter_from_date);
            TextView txt_filter_discount_to_date = (TextView) view.findViewById(R.id.row_vd_filter_to_date);
            TextView txt_filter_discount_from = (TextView) view.findViewById(R.id.row_vd_filter_discount_from);
            TextView txt_filter_discount_to = (TextView) view.findViewById(R.id.row_vd_filter_discount_to);
            TextView txt_filter_discount = (TextView) view.findViewById(R.id.row_vd_filter_discount_percent);
            TextView txt_filter_amount = (TextView) view.findViewById(R.id.row_vd_filter_discount_amount);
            TextView txt_filter_price = (TextView) view.findViewById(R.id.row_vd_filter_discount_price);
            TextView txt_filter_category = (TextView) view.findViewById(R.id.row_vd_filter_category);
            TextView txt_filter_group = (TextView) view.findViewById(R.id.row_vd_filter_group);
            TextView txt_filter_exclude = (TextView) view.findViewById(R.id.row_vd_filter_exclude);

            txt_filter_discount_from_date.setText(volumeDiscountFilterForReport.getFromDate().substring(0,10));
            txt_filter_discount_to_date.setText(volumeDiscountFilterForReport.getToDate().substring(0,10));
            txt_filter_discount_from.setText(volumeDiscountFilterForReport.getVolumeDiscountFilterItemForReportList().get(0).getFromSaleAmount());
            txt_filter_discount_to.setText(volumeDiscountFilterForReport.getVolumeDiscountFilterItemForReportList().get(0).getToSaleAmount());
            txt_filter_discount.setText(volumeDiscountFilterForReport.getVolumeDiscountFilterItemForReportList().get(0).getFilterDiscountPercent());
            txt_filter_amount.setText(volumeDiscountFilterForReport.getVolumeDiscountFilterItemForReportList().get(0).getFilterDiscountAmount());
            txt_filter_price.setText(volumeDiscountFilterForReport.getVolumeDiscountFilterItemForReportList().get(0).getFilterDiscountPrice());
            txt_filter_category.setText(volumeDiscountFilterForReport.getVolumeDiscountFilterItemForReportList().get(0).getCategoryName());
            txt_filter_group.setText(volumeDiscountFilterForReport.getVolumeDiscountFilterItemForReportList().get(0).getGroupCodeName());

            if(volumeDiscountFilterForReport.getFilterExclude() != null) {
                if(volumeDiscountFilterForReport.getFilterExclude().equals("0")) {
                    txt_filter_exclude.setText("Yes");
                } else {
                    txt_filter_exclude.setText("No");
                }
            }

            return view;
        }
    }
}
