package com.aceplus.samparoo.promotion;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.PromotionPriceForReport;
import com.aceplus.samparoo.model.forApi.PromotionPrice;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by haker on 2/23/17.
 */
public class TabFragmentPromotionPrice extends Fragment {

    View view;
    Activity activity;

    @InjectView(R.id.listViewPromotionPrice)
    ListView listViewPromotionPrice;

    ArrayList<PromotionPriceForReport> promotionPriceForReportArrayList = new ArrayList<>();

    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    @InjectView(R.id.cancel_img)
    ImageView cancelImage;

    PromotionPriceCustomAdapter promotionPriceCustomAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_fragment_promotion_price, container, false);
        activity = (AppCompatActivity) getActivity();

        ButterKnife.inject(this, view);

        sqLiteDatabase = new Database(activity).getDataBase();

        promotionPriceForReportArrayList = getPromotionPriceFromDB();

        setPromotionPriceListView();

        cancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.backToHome(activity);
            }
        });

        return view;
    }

    private ArrayList<PromotionPriceForReport> getPromotionPriceFromDB() {
        /*cursor = sqLiteDatabase.rawQuery("select * from " + DatabaseContract.PromotionDate.tb + "", null);
        while (cursor.moveToNext()) {
            String promotionPlanId = "";

            promotionPlanId = cursor.getString(cursor.getColumnIndex(DatabaseContract.PromotionDate.promotionPlanId));
            Log.i("promotionPlanId", promotionPlanId);

            Cursor cursorForPromotionPrice = sqLiteDatabase.rawQuery("select * from " + DatabaseContract.PromotionPrice.tb + " where " + DatabaseContract.PromotionPrice.promotionPlanId + "= '"+promotionPlanId+"'" , null);
            while ()
        }*/

        cursor = sqLiteDatabase.rawQuery("select * from " + DatabaseContract.PromotionPrice.tb + "", null);
        while (cursor.moveToNext()) {
            PromotionPriceForReport promotionPriceForReport = new PromotionPriceForReport();
            promotionPriceForReport.setProduct_id(cursor.getString(cursor.getColumnIndex(DatabaseContract.PromotionPrice.stockId)));
            promotionPriceForReport.setFrom_quantity(cursor.getString(cursor.getColumnIndex(DatabaseContract.PromotionPrice.fromQuantity)));
            promotionPriceForReport.setTo_quantity(cursor.getString(cursor.getColumnIndex(DatabaseContract.PromotionPrice.toQuantity)));
            promotionPriceForReport.setPromotion_price(cursor.getString(cursor.getColumnIndex(DatabaseContract.PromotionPrice.promotionPrice)));

            Cursor cursorForProductName = sqLiteDatabase.rawQuery("select * from PRODUCT WHERE ID = '" + promotionPriceForReport.getProduct_id() + "'", null);
            while (cursorForProductName.moveToNext()) {
                promotionPriceForReport.setProduct_name(cursorForProductName.getString(cursorForProductName.getColumnIndex("PRODUCT_NAME")));
            }

            promotionPriceForReportArrayList.add(promotionPriceForReport);
        }
        return promotionPriceForReportArrayList;
    }

    private void setPromotionPriceListView() {
        promotionPriceCustomAdapter = new PromotionPriceCustomAdapter(activity);
        listViewPromotionPrice.setAdapter(promotionPriceCustomAdapter);
        promotionPriceCustomAdapter.notifyDataSetChanged();
    }

    private class PromotionPriceCustomAdapter extends ArrayAdapter<PromotionPriceForReport> {

        public final Activity context;

        public PromotionPriceCustomAdapter(Activity context) {

            super(context, R.layout.list_row_promotion_price, promotionPriceForReportArrayList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            PromotionPriceForReport promotionPriceForReport = promotionPriceForReportArrayList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_promotion_price, null, true);

            TextView dateTextView = (TextView) view.findViewById(R.id.date);
            TextView productNameTextView = (TextView) view.findViewById(R.id.product_name);
            TextView fromQtyTextView = (TextView) view.findViewById(R.id.fromQty);
            TextView toQtyTextView = (TextView) view.findViewById(R.id.toQty);
            TextView promotionPriceTextView = (TextView) view.findViewById(R.id.promotion_price);

            productNameTextView.setText(promotionPriceForReport.getProduct_name());
            fromQtyTextView.setText(promotionPriceForReport.getFrom_quantity());
            toQtyTextView.setText(promotionPriceForReport.getTo_quantity());
            promotionPriceTextView.setText(Utils.formatAmount(Double.parseDouble(promotionPriceForReport.getPromotion_price())));

            return view;
        }
    }

}
