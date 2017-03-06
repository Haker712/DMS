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
import com.aceplus.samparoo.model.PromotionGiftForReport;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by haker on 2/23/17.
 */
public class TabFragmentPromotionGift extends Fragment {

    View view;

    Activity activity;

    ArrayList<PromotionGiftForReport> promotionGoftForReportArrayList = new ArrayList<>();

    TextView txt_title;

    SQLiteDatabase sqLiteDatabase;

    @InjectView(R.id.cancel_img)
    ImageView cancelImage;

    Cursor cursor;

    PromotionGiftCustomAdapter promotionGiftCustomAdapter;

    @InjectView(R.id.listViewPromotionGift)
    ListView listViewPromotionGift;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_fragment_promotion_gift, container, false);
        activity = (AppCompatActivity) getActivity();
        ButterKnife.inject(this, view);

        txt_title = (TextView) view.findViewById(R.id.promotion_gift_title);

        sqLiteDatabase = new Database(activity).getDataBase();

        promotionGoftForReportArrayList = getPromotionGiftFromDB();

        setPromotionGiftListView();

        cancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.backToHome(activity);
            }
        });

        return view;
    }

    /**
     * Get promotion gifts from database
     *
     * @return PromotionGiftForReport list
     */
    private ArrayList<PromotionGiftForReport> getPromotionGiftFromDB() {
        cursor = sqLiteDatabase.rawQuery("select * from " + DatabaseContract.PromotionGift.tb, null);
        while (cursor.moveToNext()) {
            PromotionGiftForReport promotionGiftForReport = new PromotionGiftForReport();
            promotionGiftForReport.setPromotionPlanId(cursor.getString(cursor.getColumnIndex(DatabaseContract.PromotionGift.promotionPlanId)));
            promotionGiftForReport.setStockId(cursor.getString(cursor.getColumnIndex(DatabaseContract.PromotionGift.stockId)));
            promotionGiftForReport.setFromQuantity(cursor.getInt(cursor.getColumnIndex(DatabaseContract.PromotionGift.fromQuantity)));
            promotionGiftForReport.setToQuantity(cursor.getInt(cursor.getColumnIndex(DatabaseContract.PromotionGift.toQuantity)));

            Cursor cursor = sqLiteDatabase.rawQuery("select PRODUCT_NAME from PRODUCT WHERE ID = '" + promotionGiftForReport.getStockId() + "'", null);
            while (cursor.moveToNext()) {
                promotionGiftForReport.setProductName(cursor.getString(cursor.getColumnIndex("PRODUCT_NAME")));
            }

            cursor = sqLiteDatabase.rawQuery("SELECT P.PRODUCT_NAME, G.QUANTITY FROM PROMOTION_GIFT_ITEM G, PRODUCT P WHERE G.STOCK_ID = P.ID", null);
            while (cursor.moveToNext()) {
                promotionGiftForReport.setPromotionGiftName(cursor.getString(cursor.getColumnIndex("PRODUCT_NAME")));
                promotionGiftForReport.setPromotionGiftQuantity(cursor.getInt(cursor.getColumnIndex("QUANTITY")));
            }

            promotionGoftForReportArrayList.add(promotionGiftForReport);
        }
        return promotionGoftForReportArrayList;
    }

    /**
     *
     * Set promotion gift list
     */
    private void setPromotionGiftListView() {
        promotionGiftCustomAdapter = new PromotionGiftCustomAdapter(activity);
        listViewPromotionGift.setAdapter(promotionGiftCustomAdapter);
        promotionGiftCustomAdapter.notifyDataSetChanged();
    }

    /**
     * PromotionGiftAdapter
     */
    private class PromotionGiftCustomAdapter extends ArrayAdapter<PromotionGiftForReport> {

        public final Activity context;

        public PromotionGiftCustomAdapter(Activity context) {

            super(context, R.layout.list_row_promotion_price, promotionGoftForReportArrayList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            PromotionGiftForReport promotionPriceForReport = promotionGoftForReportArrayList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_promotion_gift, null, true);

            TextView dateTextView = (TextView) view.findViewById(R.id.date);
            TextView productNameTextView = (TextView) view.findViewById(R.id.txt_product_name);
            TextView fromQtyTextView = (TextView) view.findViewById(R.id.txt_fromQty);
            TextView toQtyTextView = (TextView) view.findViewById(R.id.txt_toQty);
            TextView promotionGiftextView = (TextView) view.findViewById(R.id.txt_promotion_gift);
            TextView promotionGifQtytextView = (TextView) view.findViewById(R.id.txt_promotion_gift_quantity);

            productNameTextView.setText(promotionPriceForReport.getProductName());
            fromQtyTextView.setText(promotionPriceForReport.getFromQuantity() + "");
            toQtyTextView.setText(promotionPriceForReport.getToQuantity() + "");
            promotionGiftextView.setText(promotionPriceForReport.getPromotionGiftName());
            promotionGifQtytextView.setText(promotionPriceForReport.getPromotionGiftQuantity() + "");

            return view;
        }
    }
}
