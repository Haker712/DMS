package com.aceplus.samparoo.route;

import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.Route_Township;
import com.aceplus.samparoo.model.Routedata;
import com.aceplus.samparoo.utils.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haker on 2/6/17.
 */
public class ERouteListFragment extends Fragment {

    View view;

    SQLiteDatabase database;

    Routedata routedata;
    ArrayList<Routedata> routedataArrayList;

    ListView routeListview;

    Spinner townshipSpinner;

    Route_Township route_township;
    ArrayList<Route_Township> route_townships=new ArrayList<>();
    List<String> townshiplist=new ArrayList<>();
    String township_Id,township_Name,Seleceted_TownshipId;
    String shop_type="";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_e_route_listview, container, false);

        database = new Database(getContext()).getDataBase();
        routedataArrayList = new ArrayList<>();

        townshipSpinner= (Spinner) view.findViewById(R.id.townshipspinner);

        routeListview= (ListView) view.findViewById(R.id.routeListView);


        Cursor cur_Township=database.rawQuery("select * from TOWNSHIP",null);

        while (cur_Township.moveToNext()){
            route_township=new Route_Township();

            township_Id=cur_Township.getString(cur_Township.getColumnIndex("TOWNSHIP_ID"));
            township_Name=cur_Township.getString(cur_Township.getColumnIndex("TOWNSHIP_NAME"));

            route_township.setTownship_Id(township_Id);
            route_township.setTownship_Name(township_Name);
            route_townships.add(route_township);

        }

        for (int i = 0; i < route_townships.size(); i++) {

            townshiplist.add(route_townships.get(i).getTownship_Name());

        }

        ArrayAdapter<String> TownshipAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, townshiplist);
        TownshipAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        townshipSpinner.setAdapter(TownshipAdapter);


        townshipSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                Seleceted_TownshipId=route_townships.get(position).getTownship_Id();

                Cursor cur_CustomerId = database.rawQuery("select * from RouteAssign", null);

                while (cur_CustomerId.moveToNext()) {


                    String cus_Id = cur_CustomerId.getString(cur_CustomerId.getColumnIndex("CustomerId"));
                    Log.i("Customer_Id>>>",cus_Id);

                    Cursor cur_Data = database.rawQuery("select * from CUSTOMER where id='" + cus_Id + "'and township_number='"+Seleceted_TownshipId+"'", null);

                    while (cur_Data.moveToNext()) {

                        routedata=new Routedata();

                        String customer_Name = cur_Data.getString(cur_Data.getColumnIndex("CUSTOMER_NAME"));
                        String ph_No = cur_Data.getString(cur_Data.getColumnIndex("PH"));
                        String address = cur_Data.getString(cur_Data.getColumnIndex("ADDRESS"));
                        String shoptype_id = cur_Data.getString(cur_Data.getColumnIndex("shop_type_id"));


                        Log.i("phone--",ph_No);
                        Log.i("address",address);
                        Log.i("ShopType___Id",shoptype_id);



                        Cursor cur_shopetype = database.rawQuery("select * from SHOP_TYPE where ID='" + shoptype_id + "'", null);


                        while (cur_shopetype.moveToNext()) {



                            shop_type = cur_shopetype.getString(cur_shopetype.getColumnIndex("SHOP_TYPE_NAME"));






                        }
                        try {
                            routedata.setCustomerName(customer_Name);
                            routedata.setPhNo(ph_No);
                            routedata.setAddress(address);
                            routedata.setShopType(shop_type);

                            routedataArrayList.add(routedata);
                        }
                        catch (NullPointerException a){
                            a.printStackTrace();
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        Log.i("ArraySize",routedataArrayList.size()+"");
                        RouteAdapter routeAdapter = new RouteAdapter(getActivity());
                        routeListview.setAdapter(routeAdapter);




                    }

                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });






       // getDataforRoute();





        return view;
    }

    public void getDataforRoute() {

        routedata = new Routedata();


        routedataArrayList.removeAll(routedataArrayList);

        Cursor cur_CustomerId = database.rawQuery("select * from RouteAssign", null);

        while (cur_CustomerId.moveToNext()) {

            String cus_Id = cur_CustomerId.getString(cur_CustomerId.getColumnIndex("CustomerId"));
            Log.i("Customer_Id>>>",cus_Id);

            Cursor cur_Data = database.rawQuery("select * from CUSTOMER where id='" + cus_Id + "'and township_number='"+Seleceted_TownshipId+"'", null);

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
            Log.i("CusN",routedata.getCustomerName()+"ggg");
            textView_shoptpye.setText(routedata.getShopType());
            textView_address.setText(routedata.getAddress());
            textView_Phone.setText(routedata.getPhNo());

            return view;
        }
    }

}
