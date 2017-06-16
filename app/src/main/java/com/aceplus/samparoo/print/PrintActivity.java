package com.aceplus.samparoo.print;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.aceplus.samparoo.PrintInvoiceActivity;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.customer.CustomerActivity;

public class PrintActivity extends Activity {
	public static PrinterClass pl=null;
	Button printBtn,exitBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_print_item);//pp activity_print_items
		//setListAdapter(new SimpleAdapter(this,getData("simple-list-item-2"),android.R.layout.simple_list_item_2,new String[]{"title", "description"},new int[]{android.R.id.text1, android.R.id.text2}));

		printBtn = (Button) findViewById(R.id.print_btn);//pp code
		exitBtn = (Button) findViewById(R.id.exit_btn);
		printBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 Intent intent = new Intent();
			        intent.setClass(PrintActivity.this,
			    			PrintTextActivity.class);
			        startActivity(intent);
			        finish();
			}
		});
		
		exitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(PrintActivity.this, CustomerActivity.class));
				finish();
			}
		});
		
		if(PrintActivity.pl.getState() != PrinterClass.STATE_CONNECTED)
		{
			PrintInvoiceActivity.checkState=false;
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		startActivity(new Intent(PrintActivity.this, CustomerActivity.class));
		finish();
		super.onBackPressed();
	}
}
