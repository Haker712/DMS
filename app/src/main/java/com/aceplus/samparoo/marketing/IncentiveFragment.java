package com.aceplus.samparoo.marketing;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.BaseKeyListener;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aceplus.samparoo.LoginActivity;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.model.IncentiveForUI;
import com.aceplus.samparoo.myinterface.OnActionClickListener;
import com.aceplus.samparoo.utils.Constant;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yma on 6/30/17.
 */
public class IncentiveFragment extends Fragment implements OnActionClickListener {

    SQLiteDatabase sqLiteDatabase;
    List<Integer> customerIdArr;
    List<String> customerNoList, customerNameArr;
    Spinner fromCustomerSpinner, toCustomerSpinner;
    ListView incentiveListView;
    ImageView cancelImageView, confirmImageView;
    Activity activity;
    String lastPaidQuantity = "";
    int lastPaidPosition = 0;
    List<IncentiveForUI> incentiveForUIList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incentive, container, false);
        sqLiteDatabase = new Database(getActivity()).getDataBase();
        activity = getActivity();
        Utils.setOnActionClickListener(this);
        registerIDs(view);
        setDataToSpinner();
        catchEvents();
        return view;
    }

    /**
     * Register id of the widgets from current layout.
     *
     * @param view current layout
     */
    void registerIDs(View view) {
        incentiveListView = (ListView) view.findViewById(R.id.fragment_incentive_lv);
        fromCustomerSpinner = (Spinner) view.findViewById(R.id.fragment_incentive_spinner_from_customer);
        toCustomerSpinner = (Spinner) view.findViewById(R.id.fragment_incentive_spinner_to_customer);
        cancelImageView = (ImageView) view.findViewById(R.id.fragment_incentive_cancel_img);
        confirmImageView = (ImageView) view.findViewById(R.id.fragment_incentive_save_img);
    }

    /**
     * Set up data to spinner.
     */
    void setDataToSpinner() {
        getCustomersFromDb();

        if (customerNameArr != null) {
            ArrayAdapter<String> customerAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, customerNameArr);
            customerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            fromCustomerSpinner.setAdapter(customerAdapter);
            toCustomerSpinner.setAdapter(customerAdapter);
        }
    }

    /**
     * Get Customer data from database.
     */
    void getCustomersFromDb() {
        customerNoList = new ArrayList<>();
        customerNameArr = new ArrayList<>();
        customerIdArr = new ArrayList<>();
        Cursor cursorCustomer = sqLiteDatabase.rawQuery("SELECT CUSTOMER_ID, CUSTOMER_NAME, id FROM CUSTOMER ORDER BY id ASC", null);
        while (cursorCustomer.moveToNext()) {

            String customerId = cursorCustomer.getString(cursorCustomer.getColumnIndex("CUSTOMER_ID"));
            String customerName = cursorCustomer.getString(cursorCustomer.getColumnIndex("CUSTOMER_NAME"));
            int id = cursorCustomer.getInt(cursorCustomer.getColumnIndex("id"));

            customerNoList.add(customerId);
            customerNameArr.add(customerName);
            customerIdArr.add(id);
        }
    }

    /**
     * event listener for current view
     */
    void catchEvents() {

        fromCustomerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Integer fromId = customerIdArr.get(fromCustomerSpinner.getSelectedItemPosition());
                Integer toId = customerIdArr.get(toCustomerSpinner.getSelectedItemPosition());

                if(fromId > toId){
                    fromId = customerIdArr.get(toCustomerSpinner.getSelectedItemPosition());
                    toId = customerIdArr.get(fromCustomerSpinner.getSelectedItemPosition());
                }

                incentiveForUIList = getIncentiveFromDb(fromId, toId);
                IncentiveListItemAdapter dpReportArrayAdapter = new IncentiveListItemAdapter(getActivity(), R.layout.list_row_incentive_item, incentiveForUIList);
                incentiveListView.setAdapter(dpReportArrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toCustomerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Integer fromId = customerIdArr.get(fromCustomerSpinner.getSelectedItemPosition());
                Integer toId = customerIdArr.get(toCustomerSpinner.getSelectedItemPosition());

                if(fromId > toId){
                    fromId = customerIdArr.get(toCustomerSpinner.getSelectedItemPosition());
                    toId = customerIdArr.get(fromCustomerSpinner.getSelectedItemPosition());
                }

                incentiveForUIList = getIncentiveFromDb(fromId, toId);
                IncentiveListItemAdapter dpReportArrayAdapter = new IncentiveListItemAdapter(getActivity(), R.layout.list_row_incentive_item, incentiveForUIList);
                incentiveListView.setAdapter(dpReportArrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        confirmImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.askConfirmationDialog("Save", "Do you want to save?", getResources().getString(R.string.incentive), activity);
            }
        });

        cancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.backToMarketingActivity(activity);
            }
        });
    }

    /**
     * Get Incentive Data from database.
     *
     * @return IncentiveForUI object list
     */
    List<IncentiveForUI> getIncentiveFromDb(Integer fromCustomerId, Integer toCustomerId) {

        List<IncentiveForUI> incentiveList = new ArrayList<>();

        Cursor incentiveItemCursor = sqLiteDatabase.rawQuery("SELECT A.*, P.PRODUCT_NAME, N.INVOICE_NO, N.INVOICE_DATE, N.CUSTOMER_ID, " +
                "(SELECT CUSTOMER_NAME FROM CUSTOMER WHERE CUSTOMER.id = N.CUSTOMER_ID) AS CUSTOMER_NAME, " +
                "(SELECT CUSTOMER_ID FROM CUSTOMER WHERE CUSTOMER.id = N.CUSTOMER_ID) AS CUSTOMER_NO "
                + "FROM INCENTIVE AS N, INCENTIVE_ITEM AS A "
                + "LEFT JOIN PRODUCT AS P ON P.ID = A.STOCK_ID "
                + "WHERE N.CUSTOMER_ID BETWEEN " + fromCustomerId + " AND "+ toCustomerId +" AND A.INCENTIVE_ID = N.ID", null);

        while (incentiveItemCursor.moveToNext()) {
            IncentiveForUI incentiveForUi = new IncentiveForUI();
            Integer customerId = incentiveItemCursor.getInt(incentiveItemCursor.getColumnIndex("CUSTOMER_ID"));
            String customerNo = incentiveItemCursor.getString(incentiveItemCursor.getColumnIndex("CUSTOMER_NO"));
            String customerName = incentiveItemCursor.getString(incentiveItemCursor.getColumnIndex("CUSTOMER_NAME"));
            Integer stockId = incentiveItemCursor.getInt(incentiveItemCursor.getColumnIndex("STOCK_ID"));
            Integer quantity = incentiveItemCursor.getInt(incentiveItemCursor.getColumnIndex("QUANTITY"));
            String productName = incentiveItemCursor.getString(incentiveItemCursor.getColumnIndex("PRODUCT_NAME"));
            String invoiceNo = incentiveItemCursor.getString(incentiveItemCursor.getColumnIndex("INVOICE_NO"));
            String invoiceDate = incentiveItemCursor.getString(incentiveItemCursor.getColumnIndex("INVOICE_DATE"));

            incentiveForUi.setStockId(stockId);
            incentiveForUi.setIncentiveQuantity(quantity);
            incentiveForUi.setIncentiveItemName(productName);
            incentiveForUi.setCustomerId(customerId);
            incentiveForUi.setCustomerNo(customerNo);
            incentiveForUi.setCustomerName(customerName);
            incentiveForUi.setInvoiceNo(invoiceNo);
            incentiveForUi.setInvoiceDate(invoiceDate);
            incentiveList.add(incentiveForUi);
        }
        return incentiveList;
    }

    /**
     * Get values of every edit text from list view.
     *
     * @return string list
     */
    List<String> getAllPaidQuantity() {
        View v;
        ArrayList<String> paidQuantityList = new ArrayList<>();
        EditText et;
        for (int i = 0; i < incentiveListView.getCount(); i++) {
            v = incentiveListView.getAdapter().getView(i, null, incentiveListView);
            et = (EditText) v.findViewById(i);
            paidQuantityList.add(et.getText().toString());
        }

        return paidQuantityList;
    }

    @Override
    public void onActionClick(String type) {
        if(type.equals(getResources().getString(R.string.incentive))) {
            List<String> qtyList = getAllPaidQuantity();
            qtyList.set(lastPaidPosition,lastPaidQuantity);

            Integer saleman_Id = null;
            try {
                if (LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, "") != null) {
                    saleman_Id = Integer.parseInt(LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, null));
                }

            } catch (NullPointerException e) {
                Utils.backToLogin(activity);
            }

            for(int i = 0; i < incentiveForUIList.size(); i ++) {

                if(qtyList.get(i) != null) {
                    incentiveForUIList.get(i).setPaidQuantity(Integer.parseInt(qtyList.get(i)));

                    if(saleman_Id != null) {
                        incentiveForUIList.get(i).setSaleManId(saleman_Id);
                    }
                }
            }

            long successFlg = insertIncentivePaidToDatabase(incentiveForUIList);
            if(successFlg != -1) {
                Toast.makeText(activity, "Insertion Success", Toast.LENGTH_SHORT).show();
                fromCustomerSpinner.setSelection(0);
                toCustomerSpinner.setSelection(0);
            } else {
                Toast.makeText(activity, "Insertion Fail", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * IncentiveListItemAdapter
     */
    private class IncentiveListItemAdapter extends BaseAdapter {

        Activity activity;
        int resource;
        LayoutInflater layoutInflater;
        ArrayList<ListItem> tempQtyList = new ArrayList<>();
        ViewHolder holder;
        List<IncentiveForUI> itemList;

        public IncentiveListItemAdapter(Activity activity, int resource, List<IncentiveForUI> itemList) {
            this.activity = activity;
            this.resource = resource;
            this.itemList = itemList;

            for(IncentiveForUI incentiveForUI : itemList) {
                ListItem listItem = new ListItem();

                if(incentiveForUI.getPaidQuantity() == null) {
                    listItem.paidQty = 0;
                } else {
                    listItem.paidQty = incentiveForUI.getPaidQuantity();
                }
                tempQtyList.add(listItem);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return tempQtyList.size();
        }

        @Override
        public Object getItem(int position) {
            return tempQtyList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if(convertView == null) {
                holder = new ViewHolder();
                layoutInflater = activity.getLayoutInflater();
                convertView = layoutInflater.inflate(this.resource, null, true);

                holder.customerNoTextView = (TextView) convertView.findViewById(R.id.row_incentive_item_customer_no);
                holder.customerNameTextView = (TextView) convertView.findViewById(R.id.row_incentive_item_customer_name);
                holder.stockNameTextView = (TextView) convertView.findViewById(R.id.row_incentive_item_stock_name);
                holder.qtyTextView = (TextView) convertView.findViewById(R.id.row_incentive_item_qty);
                holder.paidEditText = (EditText) convertView.findViewById(R.id.row_incentive_item_paid);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.customerNoTextView.setText(itemList.get(position).getCustomerNo());
            holder.customerNameTextView.setText(itemList.get(position).getCustomerName());
            holder.stockNameTextView.setText(itemList.get(position).getIncentiveItemName());
            holder.qtyTextView.setText(itemList.get(position).getIncentiveQuantity() + "");

            holder.paidEditText.setText(tempQtyList.get(position).paidQty + "");

            holder.paidEditText.setId(position);

            holder.paidEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    int position = v.getId();
                    EditText paidQtyEditText = (EditText) v;
                    if(!hasFocus) {
                        try {
                            tempQtyList.get(position).paidQty = Integer.parseInt(paidQtyEditText.getText().toString());
                        } catch(NumberFormatException e) {
                            Toast.makeText(activity, "Invalid Paid Quantity", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            holder.paidEditText.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        int position = v.getId();
                        EditText paidQtyEditText = (EditText) v;
                        try {
                            tempQtyList.get(position).paidQty = Integer.parseInt(paidQtyEditText.getText().toString());
                        } catch(NumberFormatException e) {
                            Toast.makeText(activity, "Invalid Paid Quantity", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                    return false;
                }
            });

            if(holder.paidEditText.getId() == position) {
                holder.paidEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        lastPaidPosition = position;
                        lastPaidQuantity = s.toString();
                    }
                });
            }

            return convertView;
        }

        class ViewHolder {
            TextView customerNoTextView;
            TextView customerNameTextView;
            TextView stockNameTextView;
            TextView qtyTextView;
            EditText paidEditText;
        }

        /**
         * to hold paid quantity from edit text
         */
        class ListItem {
            int paidQty;
        }
    }

    /**
     * Insert incentive paid data to database.
     *
     * @param incentiveForUIList IncentiveForUI list
     * @return long return -1: error while inserting to table; otherwise success
     */
    long insertIncentivePaidToDatabase(List<IncentiveForUI> incentiveForUIList) {
        long insertSuccess = 0;
        sqLiteDatabase.beginTransaction();
        for(IncentiveForUI incentiveForUI : incentiveForUIList) {
            ContentValues cvForIncentPaid = new ContentValues();
            cvForIncentPaid.put("INVOICE_NO", incentiveForUI.getInvoiceNo());
            cvForIncentPaid.put("INVOICE_DATE", incentiveForUI.getInvoiceDate());
            cvForIncentPaid.put("CUSTOMER_ID", incentiveForUI.getCustomerId());
            cvForIncentPaid.put("STOCK_ID", incentiveForUI.getStockId());
            cvForIncentPaid.put("QUANTITY", incentiveForUI.getIncentiveQuantity());
            cvForIncentPaid.put("PAID_QUANTITY", incentiveForUI.getPaidQuantity());
            cvForIncentPaid.put("SALE_MAN_ID", incentiveForUI.getSaleManId());
            cvForIncentPaid.put("DELETE_FLAG", 0);
            insertSuccess = sqLiteDatabase.insert("INCENTIVE_PAID", null, cvForIncentPaid);
        }
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return insertSuccess;
    }
}
