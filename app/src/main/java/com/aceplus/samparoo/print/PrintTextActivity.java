package com.aceplus.samparoo.print;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.aceplus.samparoo.LoginActivity;
import com.aceplus.samparoo.PrintInvoiceActivity;
import com.aceplus.samparoo.model.forApi.Invoice;
import com.aceplus.samparoo.model.forApi.InvoiceDetail;
import com.aceplus.samparoo.model.forApi.InvoicePresent;
import com.aceplus.samparoo.utils.Constant;
import com.aceplus.samparoo.utils.Database;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;


public class PrintTextActivity extends Activity {

	DecimalFormat commaSepFormat = new DecimalFormat("###,##0");
	
	String invoiceIDs;
	String customerID, cusName, cusPhone, cusAddress;
	SimpleDateFormat fmtForDueStr = new SimpleDateFormat("yyyy/MM/dd");
	
	String productName, qty;

	Invoice invoice;
	List<InvoiceDetail> invoiceDetailList;
	List<InvoicePresent> invoicePresentList;
	SQLiteDatabase database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StringBuilder str = new StringBuilder();
		double total = 0;
		database = new Database(this).getDataBase();

		if (getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE) != null) {
			invoice = (Invoice) getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE);
		}

		if (getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE) != null) {
			invoiceDetailList = (List<InvoiceDetail>) getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE);
		}

		if (getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE_PRESENT) != null) {
			invoicePresentList = (List<InvoicePresent>) getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE_PRESENT);
		}
		
		String format_saleMan = "%1$-12s %2$-17s %3$-6s %4$-10s\n";
		String format_cusName = "%1$-14s %2$-25s %3$-3s %4$-3s\n";
		String format_cusPhone = "%1$-6s %2$-25s %3$-3s %4$-3s\n";
		String format_cusAdd = "%1$-8s %2$-32s %3$-2s %4$-3s\n\n";
		String format_title = "%1$-23s|%2$-7s|%3$-5s|%4$-10s\n\n";
        String format_content = "%1$-23s|%2$7s|%3$5s|%4$10s\n\n";
        String format_total = "%1$-9s %2$7s %3$16s %4$13s\n\n";
        String format_thankyou = "%1$-17s%2$11s%3$7s %4$10s\n\n";
             
        Calendar todayCal = Calendar.getInstance();
        String todayDate = fmtForDueStr.format(todayCal.getTime());

		getCustomerName(invoice.getCustomerId());

        str.append(String.format(format_saleMan, "Sale Men ID:", LoginActivity.mySharedPreference.getString(Constant.SALEMAN_ID, ""),"Date:",todayDate));
                
        str.append(String.format(format_cusName, "Customer Name:", cusName,"",""));
        
        str.append(String.format(format_cusPhone, "Phone:", cusPhone,"",""));
        
        str.append(String.format(format_cusAdd, "Address:", cusAddress,"",""));
        
		str.append(String.format(format_title, "Description", "  Q'ty", "Dis%", "  Amount"));
		Log.e("TEXT", str.toString());
		

		Log.e("Printed : ", str.toString());		
		PrintActivity.pl.printText(str.toString());
		startActivity(new Intent(PrintTextActivity.this, PrintActivity.class));
		finish();		
	}

	private void getCustomerName(String id) {
		Cursor customerCursor = database.rawQuery("SELECT CUSTOMER_NAME, PH FROM CUSTOMER WHERE CUSTOMER_ID = \'" + id + "\'", null);

		while(customerCursor.moveToNext()) {
			cusName = customerCursor.getString(customerCursor.getColumnIndex("CUSTOMER_NAME"));
			cusPhone = customerCursor.getString(customerCursor.getColumnIndex("PH"));
			cusAddress = customerCursor.getString(customerCursor.getColumnIndex("ADDRESS"));
		}
	}
}
