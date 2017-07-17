package com.aceplus.samparoo.marketing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
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
    IncentiveListItemAdapter incentiveListItemAdapter;

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

                if (fromId > toId) {
                    fromId = customerIdArr.get(toCustomerSpinner.getSelectedItemPosition());
                    toId = customerIdArr.get(fromCustomerSpinner.getSelectedItemPosition());
                }

                incentiveForUIList = getIncentiveFromDb(fromId, toId);
                incentiveListItemAdapter = new IncentiveListItemAdapter(getActivity(), R.layout.list_row_incentive_item, incentiveForUIList);
                incentiveListItemAdapter.notifyDataSetChanged();
                incentiveListView.setAdapter(incentiveListItemAdapter);
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

                if (fromId > toId) {
                    fromId = customerIdArr.get(toCustomerSpinner.getSelectedItemPosition());
                    toId = customerIdArr.get(fromCustomerSpinner.getSelectedItemPosition());
                }

                incentiveForUIList = getIncentiveFromDb(fromId, toId);
                incentiveListItemAdapter = new IncentiveListItemAdapter(getActivity(), R.layout.list_row_incentive_item, incentiveForUIList);
                incentiveListItemAdapter.notifyDataSetChanged();
                incentiveListView.setAdapter(incentiveListItemAdapter);
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
                + "WHERE N.CUSTOMER_ID BETWEEN " + fromCustomerId + " AND " + toCustomerId + " AND A.INCENTIVE_ID = N.ID", null);

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

            Cursor cursorIncentivePaid = sqLiteDatabase.rawQuery("SELECT PAID_QUANTITY FROM INCENTIVE_PAID WHERE INVOICE_NO = '" + incentiveForUi.getInvoiceNo() +"'", null);
            while(cursorIncentivePaid.moveToNext()) {
                int paidQuantity = cursorIncentivePaid.getInt(cursorIncentivePaid.getColumnIndex("PAID_QUANTITY"));
                incentiveForUi.setPaidQuantity(paidQuantity);
            }
            incentiveList.add(incentiveForUi);
        }
        incentiveList = checkAlreadyPaidIncentive(incentiveList);
        return incentiveList;
    }

    /**
     * Check incentive is already paid.
     *
     * @param incentiveList IncentiveForUI list
     * @return no paid incentive list
     */
    List<IncentiveForUI> checkAlreadyPaidIncentive(List<IncentiveForUI> incentiveList) {
        List<IncentiveForUI> incentiveForUIList = new ArrayList<>();
        incentiveForUIList.addAll(incentiveList);

        for(int i = 0; i < incentiveForUIList.size(); i++) {
            Cursor incentiveCursor = sqLiteDatabase.rawQuery("SELECT * FROM INCENTIVE_PAID WHERE PAID_QUANTITY = QUANTITY", null);
            while(incentiveCursor.moveToNext()) {
                String invoiceNo = incentiveCursor.getString(incentiveCursor.getColumnIndex("INVOICE_NO"));
                Integer stockId = incentiveCursor.getInt(incentiveCursor.getColumnIndex("STOCK_ID"));
                Integer customerId = incentiveCursor.getInt(incentiveCursor.getColumnIndex("CUSTOMER_ID"));
                if(incentiveForUIList != null && incentiveForUIList.size() > 0) {
                    if (invoiceNo.equals(incentiveForUIList.get(i).getInvoiceNo()) && incentiveForUIList.get(i).getStockId().equals(stockId) && incentiveForUIList.get(i).getCustomerId().equals(customerId)) {
                        incentiveForUIList.remove(i);
                    }
                }
            }
        }

        return incentiveForUIList;
    }

    @Override
    public void onActionClick(String type) {
        if (type.equals(getResources().getString(R.string.incentive))) {
            /*List<String> qtyList = getAllPaidQuantity();
            qtyList.set(lastPaidPosition, lastPaidQuantity);*/

            Integer saleman_Id = null;
            try {
                if (LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, "") != null) {
                    saleman_Id = Integer.parseInt(LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, null));
                }

            } catch (NullPointerException e) {
                Utils.backToLogin(activity);
            }

            for (int i = 0; i < incentiveForUIList.size(); i++) {

                if (saleman_Id != null) {
                    incentiveForUIList.get(i).setSaleManId(saleman_Id);
                }
            }

            insertOrUpdateIncentivePaid(incentiveForUIList);
        }
    }

    /**
     * IncentiveListItemAdapter
     */
    private class IncentiveListItemAdapter extends BaseAdapter {

        Activity activity;
        int resource;
        LayoutInflater layoutInflater;
        ViewHolder holder;
        List<IncentiveForUI> itemList;
        String[] tempItemQty;

        public IncentiveListItemAdapter(Activity activity, int resource, List<IncentiveForUI> itemList) {
            this.activity = activity;
            this.resource = resource;
            this.itemList = itemList;
            tempItemQty = new String[itemList.size()];
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null) {
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
            holder.paidEditText.setText(itemList.get(position).getPaidQuantity() + "");
            holder.paidEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View dialogView = layoutInflater.inflate(R.layout.dialog_box_sale_quantity, null);

                    final TextView remainingQtyTextView = (TextView) dialogView.findViewById(R.id.availableQuantity);
                    TextView availableQtyTextView = (TextView) dialogView.findViewById(R.id.txt_dialog_qty);
                    availableQtyTextView.setText("Incentive Quantity: ");
                    final EditText quantityEditText = (EditText) dialogView.findViewById(R.id.quantity);
                    final TextView messageTextView = (TextView) dialogView.findViewById(R.id.message);

                    final AlertDialog alertDialog = new AlertDialog.Builder(activity)
                            .setView(dialogView)
                            .setTitle("Sale Quantity")
                            .setPositiveButton("Confirm", null)
                            .setNegativeButton("Cancel", null)
                            .create();

                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialog) {
                            remainingQtyTextView.setText(itemList.get(position).getIncentiveQuantity() + "");
                            Button confirmButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                            confirmButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (quantityEditText.getText().toString().length() == 0) {

                                        messageTextView.setText("You must specify quantity.");
                                        return;
                                    }

                                    int quantity = Integer.parseInt(quantityEditText.getText().toString());
                                    if (quantity > itemList.get(position).getIncentiveQuantity()) {
                                        messageTextView.setText("More than Given Quantity");
                                        quantityEditText.selectAll();
                                        return;
                                    }

                                    itemList.get(position).setPaidQuantity(quantity);
                                    holder.paidEditText.setText(quantity + "");
                                    alertDialog.dismiss();
                                    incentiveListItemAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    });

                    alertDialog.show();
                }
            });

            if (itemList.get(position).getPaidQuantity() != null) {
                holder.paidEditText.setText(itemList.get(position).getPaidQuantity() + "");
            } else {
                holder.paidEditText.setText("0");
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
    }

    /**
     * Insert incentive paid data to database.
     *
     * @param incentiveForUI IncentiveForUI
     * @return long return -1: error while inserting to table; otherwise success
     */
    long insertIncentivePaidToDatabase(IncentiveForUI incentiveForUI) {
        long insertSuccess = 0;
        sqLiteDatabase.beginTransaction();
        ContentValues cvForIncentPaid = new ContentValues();
        cvForIncentPaid.put("INVOICE_NO", incentiveForUI.getInvoiceNo());
        cvForIncentPaid.put("INVOICE_DATE", incentiveForUI.getInvoiceDate());
        cvForIncentPaid.put("CUSTOMER_ID", incentiveForUI.getCustomerId());
        cvForIncentPaid.put("STOCK_ID", incentiveForUI.getStockId());
        cvForIncentPaid.put("QUANTITY", incentiveForUI.getIncentiveQuantity());
        cvForIncentPaid.put("PAID_QUANTITY", "PAID_QUANTITY + " + incentiveForUI.getPaidQuantity());
        cvForIncentPaid.put("SALE_MAN_ID", incentiveForUI.getSaleManId());
        cvForIncentPaid.put("DELETE_FLAG", 0);
        insertSuccess = sqLiteDatabase.insert("INCENTIVE_PAID", null, cvForIncentPaid);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return insertSuccess;
    }

    /**
     * Insertion or updating data to incentive paid table
     *
     * @param incentiveForUIList IncentiveForUI list
     */
    void insertOrUpdateIncentivePaid(List<IncentiveForUI> incentiveForUIList) {

        for (IncentiveForUI incentiveForUI : incentiveForUIList) {
            Cursor cursorIncentive = sqLiteDatabase.rawQuery("SELECT COUNT(*) AS COUNT FROM INCENTIVE_PAID WHERE DELETE_FLAG = 0 " +
                    "AND INVOICE_NO = '" + incentiveForUI.getInvoiceNo() + "' AND CUSTOMER_ID = " + incentiveForUI.getCustomerId() + " AND " +
                    "STOCK_ID = " + incentiveForUI.getStockId(), null);
            int count = 0;
            while (cursorIncentive.moveToNext()) {
                count = cursorIncentive.getInt(cursorIncentive.getColumnIndex("COUNT"));
                if (count > 0) {
                    int updatedRowCount = updateIncentivePaid(incentiveForUI);
                    if (updatedRowCount > 0) {
                        Toast.makeText(activity, "Update Success", Toast.LENGTH_SHORT).show();
                        fromCustomerSpinner.setSelection(0);
                        toCustomerSpinner.setSelection(0);
                    } else {
                        Toast.makeText(activity, "Update Fail", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    long insertSuccess = insertIncentivePaidToDatabase(incentiveForUI);
                    if (insertSuccess != -1) {
                        Toast.makeText(activity, "Insertion Success", Toast.LENGTH_SHORT).show();
                        fromCustomerSpinner.setSelection(0);
                        toCustomerSpinner.setSelection(0);
                        //resetAllDefaultValue();
                    } else {
                        Toast.makeText(activity, "Insertion Fail", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    /**
     * Update incentive to database
     *
     * @param incentiveForUI IncentiveForUI
     * @return updated row count
     */
    int updateIncentivePaid(IncentiveForUI incentiveForUI) {
        int updateSuccess = 0;
        String whereClause = "DELETE_FLAG = 0 AND INVOICE_NO = '" + incentiveForUI.getInvoiceNo() + "' " +
                "AND CUSTOMER_ID = " + incentiveForUI.getCustomerId() + " AND STOCK_ID = " + incentiveForUI.getStockId() ;
        sqLiteDatabase.beginTransaction();
        ContentValues cvForIncentPaid = new ContentValues();
        cvForIncentPaid.put("INVOICE_NO", incentiveForUI.getInvoiceNo());
        cvForIncentPaid.put("INVOICE_DATE", incentiveForUI.getInvoiceDate());
        cvForIncentPaid.put("CUSTOMER_ID", incentiveForUI.getCustomerId());
        cvForIncentPaid.put("STOCK_ID", incentiveForUI.getStockId());
        cvForIncentPaid.put("QUANTITY", incentiveForUI.getIncentiveQuantity());
        cvForIncentPaid.put("PAID_QUANTITY", "PAID_QUANTITY + " + incentiveForUI.getPaidQuantity());
        cvForIncentPaid.put("SALE_MAN_ID", incentiveForUI.getSaleManId());
        cvForIncentPaid.put("DELETE_FLAG", 0);
        updateSuccess = sqLiteDatabase.update("INCENTIVE_PAID", cvForIncentPaid, whereClause, null);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return updateSuccess;
    }

    public interface onViewHolderClickListener {
        void onClickListener(View view, int position);
    }
}
