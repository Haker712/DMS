package com.aceplus.samparoo.marketing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.system.StructPollfd;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aceplus.samparoo.LoginActivity;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.CompetitorActivity;
import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.model.SoldProduct;
import com.aceplus.samparoo.utils.Constant;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.DatabaseContract;
import com.aceplus.samparoo.utils.Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phonelin on 4/3/17.
 */

public class TabFragment6 extends Fragment {


    View view;

    SQLiteDatabase database;

    ImageView addimg;


    Customer customer;
    int Cus_id;

    CompetitorActivityAdapter competitorActivityAdapter;


    List<CompetitorActivity> competioractivityList;
    ListView CompetitorActivityListview;

    AppCompatActivity activity;

    ImageView Cancelimg;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab_fragment_6, container, false);

        database = new Database(getActivity()).getDataBase();

        customer = MainFragmentActivity.customer;

        String customerId = customer.getCustomerId();

        Cursor cursor = database.rawQuery("select * from CUSTOMER where CUSTOMER_ID='" + customerId + "'", null);
        while (cursor.moveToNext()) {

            Cus_id = cursor.getInt(cursor.getColumnIndex("id"));

        }

        view.findViewById(R.id.save_img).setVisibility(View.GONE);

        RegisterById();
        AddActivityOnClick();

        getCompetitorActivityData();

        CompetitorActivityListview.setAdapter(new CompetitorActivityAdapter(getActivity()));


        Cancelimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.backToMarketingActivity(activity);
            }
        });


        return view;
    }

    private void RegisterById() {

        addimg = (ImageView) view.findViewById(R.id.add_img);
        CompetitorActivityListview = (ListView) view.findViewById(R.id.competitoractivityList);
        Cancelimg= (ImageView) view.findViewById(R.id.cancel_img);


    }

    public void AddActivityOnClick() {


        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = layoutInflater.inflate(R.layout.dialogbox_competitor_activity, null);

                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                        .setView(view)
                        .setTitle("Competitor Activity")
                        .setPositiveButton("Confirm", null)
                        .setNegativeButton("Cancel", null)
                        .create();
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button confirmButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        confirmButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                final TextView txtCompetitorName = (TextView) view.findViewById(R.id.competitor_name);
                                final TextView txtActivity = (TextView) view.findViewById(R.id.activity);

                                String competitor_name = String.valueOf(txtCompetitorName.getText());
                                String activity = String.valueOf(txtActivity.getText());

                                String competitoractivity_id=(Utils.getInvoiceNo(getActivity(), LoginActivity.mySharedPreference.getString(Constant.SALEMAN_NO, ""),  String.valueOf(getLocationCode()) + "", Utils.FOR_COMPETITORACTIVITY));

                                ContentValues contentValues = new ContentValues();

                                contentValues.put("ID",competitoractivity_id);
                                contentValues.put("CUSTOMER_ID", Cus_id);
                                contentValues.put("COMPETITOR_NAME", competitor_name);
                                contentValues.put("ACTIVITY", activity);

                                database.beginTransaction();

                                database.insert("COMPETITOR_ACTIVITY", null, contentValues);


                                database.setTransactionSuccessful();
                                database.endTransaction();

                                alertDialog.dismiss();

                                getCompetitorActivityData();
                                competitorActivityAdapter = new CompetitorActivityAdapter(getActivity());


                                CompetitorActivityListview.setAdapter(competitorActivityAdapter);
                                competitorActivityAdapter.notifyDataSetChanged();


                            }
                        });
                    }


                });
                alertDialog.show();


            }
        });

    }


    public void getCompetitorActivityData() {

        competioractivityList = new ArrayList<>();


        Cursor cur = database.rawQuery("select * from COMPETITOR_ACTIVITY where CUSTOMER_ID=" + Cus_id + "", null);

        while (cur.moveToNext()) {

            CompetitorActivity competitoractivity = new CompetitorActivity();

            String competitor_name = cur.getString(cur.getColumnIndex("COMPETITOR_NAME"));
            String activity = cur.getString(cur.getColumnIndex("ACTIVITY"));

            competitoractivity.setCompetitor_Name(competitor_name);
            competitoractivity.setActivity(activity);

            competioractivityList.add(competitoractivity);

        }


    }

    private class CompetitorActivityAdapter extends ArrayAdapter<CompetitorActivity> {


        public final Activity context;


        public CompetitorActivityAdapter(Activity context) {
            super(context, R.layout.list_row_competitoractivity, competioractivityList);
            this.context = context;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


            final CompetitorActivity competitoractivity = competioractivityList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_competitoractivity, null, true);

            TextView txtCompetitor = (TextView) view.findViewById(R.id.competitorName);
            TextView txtActivityName = (TextView) view.findViewById(R.id.ActivityName);

            txtCompetitor.setText(competitoractivity.getCompetitor_Name());
            txtActivityName.setText(competitoractivity.getActivity());

            return view;
        }
    }

    private int getLocationCode() {
        int locationCode = 0;
        String locationCodeName = "";
        Cursor cursorForLocation = database.rawQuery("select * from Location", null);
        while (cursorForLocation.moveToNext()) {
            locationCode = cursorForLocation.getInt(cursorForLocation.getColumnIndex(DatabaseContract.Location.id));
            locationCodeName = cursorForLocation.getString(cursorForLocation.getColumnIndex(DatabaseContract.Location.no));
        }

        return locationCode;
    }
}
