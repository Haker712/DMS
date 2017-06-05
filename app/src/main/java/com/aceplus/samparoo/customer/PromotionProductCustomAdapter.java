package com.aceplus.samparoo.customer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Promotion;

import java.util.ArrayList;

/**
 * Created by YMA on 6/4/17.
 */
class PromotionProductCustomAdapter extends ArrayAdapter<Promotion> {

    final Activity context;
    final ArrayList<Promotion> promotionArrayList;

    public PromotionProductCustomAdapter(Activity context,ArrayList<Promotion> promotionArrayList) {
        super(context, R.layout.list_row_promotion, promotionArrayList);
        this.context = context;
        this.promotionArrayList=promotionArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Promotion promotion = promotionArrayList.get(position);

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.list_row_promotion, null, true);

        final TextView nameTextView = (TextView) view.findViewById(R.id.productName);
        final TextView qtyTextView = (TextView) view.findViewById(R.id.qty);
        final TextView priceTextView = (TextView) view.findViewById(R.id.price);
        priceTextView.setVisibility(View.GONE);

        if (!promotion.getPromotionProductName().equals("") || promotion.getPromotionProductName() != null) {
            nameTextView.setText(promotion.getPromotionProductName());
        } else {
            nameTextView.setVisibility(View.GONE);
        }
        if (promotion.getPromotionQty() != 0) {
            qtyTextView.setText(promotion.getPromotionQty() + "");
        } else {
            qtyTextView.setVisibility(View.GONE);
        }

        return view;
    }
}
