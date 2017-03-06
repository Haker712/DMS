package com.aceplus.samparoo.marketing;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aceplus.samparoo.R;

public class GridViewAdapter extends BaseAdapter {

	// Declare variables
	private Activity activity;
	private String[] filepath, org_filepath;
	private String[] filename, org_filename;

	private static LayoutInflater inflater = null;

	public GridViewAdapter(Activity a, String[] fpath, String[] fname) {
		activity = a;
		filepath = fpath;
		filename = fname;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public GridViewAdapter(Activity a, String[] fpath, String[] fname, String[] org_fpath, String[] org_fname) {
		activity = a;
		filepath = fpath;
		filename = fname;
		org_filepath = org_fpath;
		org_filename = org_fname;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public int getCount() {
		return filepath.length;

	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.gridview_item, null);
		// Locate the TextView in gridview_item.xml
		TextView text = (TextView) vi.findViewById(R.id.text);
		// Locate the ImageView in gridview_item.xml
		ImageView image = (ImageView) vi.findViewById(R.id.image);

		// Set file name to the TextView followed by the position
		text.setText(filename[position]);

		// Decode the filepath with BitmapFactory followed by the position
		Bitmap bmp = BitmapFactory.decodeFile(filepath[position]);
		
		//Bitmap.createScaledBitmap(bmp,(int)(bmp.getWidth()*0.8), (int)(bmp.getHeight()*0.8), true);
		
		image.setImageBitmap(Bitmap.createScaledBitmap(bmp, 260, 200, false));//latest 19.10.2015

		///image.setImageBitmap(Bitmap.createScaledBitmap(bmp,(int)(bmp.getWidth()*0.8), (int)(bmp.getHeight()*0.8), true));

		// Set the decoded bitmap into ImageView
		//image.setImageBitmap(bmp);
		
		
		//LayoutParams params = new LayoutParams(200, 150);
		//image.setLayoutParams(params);
		//image.setScaleType(ImageView.ScaleType.FIT_CENTER);
		
		notifyDataSetChanged();
		
		return vi;
	}
}