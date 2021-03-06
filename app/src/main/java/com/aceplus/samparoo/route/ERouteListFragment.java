package com.aceplus.samparoo.route;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Routedata;
import com.aceplus.samparoo.report.FragmentPromotionReport;
import com.aceplus.samparoo.utils.Database;

import org.json.JSONObject;

import java.util.ArrayList;

import static android.R.attr.resource;

/**
 * Created by haker on 2/6/17.
 */
public class ERouteListFragment extends Fragment {

    View view;

    SQLiteDatabase database;

    Routedata routedata;
    ArrayList<Routedata> routedataArrayList;

    ListView routeListview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_e_route_listview, container, false);

        database = new Database(getContext()).getDataBase();
        routedataArrayList = new ArrayList<>();



        getDataforRoute();

        routeListview= (ListView) view.findViewById(R.id.routeListView);
        ArrayAdapter<Routedata> routeAdapter = new ERouteListFragment.RouteAdapter(getActivity());
        routeListview.setAdapter(routeAdapter);



        return view;
    }

    public void getDataforRoute() {

        routedata = new Routedata();


        routedataArrayList.removeAll(routedataArrayList);

        Cursor cur_CustomerId = database.rawQuery("select * from RouteAssign", null);

        while (cur_CustomerId.moveToNext()) {

            String cus_Id = cur_CustomerId.getString(cur_CustomerId.getColumnIndex("CustomerId"));
            Log.i("Customer_Id>>>",cus_Id);

            Cursor cur_Data = database.rawQuery("select * from CUSTOMER where id='" + cus_Id + "'", null);

            while (cur_Data.moveToNext()) {

                String customer_Name = cur_Data.getString(cur_Data.getColumnIndex("CUSTOMER_NAME"));
                String ph_No = cur_Data.getString(cur_Data.getColumnIndex("PH"));
                String address = cur_Data.getString(cur_Data.getColumnIndex("ADDRESS"));
                String shoptype_id = cur_Data.getString(cur_Data.getColumnIndex("shop_type_id"));


                Log.i("phone--",ph_No);
                Log.i("address",address);
                Log.i("ShopType___Id",shoptype_id);



                Cursor cur_shopetype = database.rawQuery("select * from SHOP_TYPE where ID='" + shoptype_id + "'", null);


                while (cur_shopetype.moveToNext()) {


                    String shop_type = cur_shopetype.getString(cur_shopetype.getColumnIndex("SHOP_TYPE_NAME"));

                    routedata.setCustomerName(customer_Name);
                    routedata.setPhNo(ph_No);
                    routedata.setAddress(address);
                    routedata.setShopType(shop_type);

                    routedataArrayList.add(routedata);


                }

            }

        }
    }

    public class RouteAdapter extends ArrayAdapter<Routedata> {

        public final Activity context;

        public RouteAdapter(Activity context) {
            super(context, R.layout.list_row_route,routedataArrayList);
            this.context = context;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_route, null, true);

            TextView textView_customer_Name= (TextView) view.findViewById(R.id.customer_Name);
            TextView textView_shoptpye= (TextView) view.findViewById(R.id.shop_Type);
            TextView textView_address= (TextView) view.findViewById(R.id.address);
            TextView textView_Phone= (TextView) view.findViewById(R.id.phone);


            textView_customer_Name.setText(routedata.getCustomerName());
            textView_shoptpye.setText(routedata.getShopType());
            textView_address.setText(routedata.getAddress());
            textView_Phone.setText(routedata.getPhNo());

            return view;
        }
    }

}
