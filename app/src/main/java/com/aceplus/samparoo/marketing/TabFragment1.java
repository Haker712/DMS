package com.aceplus.samparoo.marketing;

/**
 * Created by ACEPLU049 on 2/24/2016.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.aceplus.samparoo.R;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TabFragment1 extends Fragment {

    AppCompatActivity activity;
    View view;

    public static String customerName;

    private String[] FilePathStrings, org_FilePathStrings;
    private String[] FileNameStrings, org_FileNameStrings;
    private File[] listFile, org_listFile;

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;

    private Uri fileUri; // file url to store image/video
    String imgBase64Str;
    ArrayList<String> imageList = new ArrayList<>();
    SQLiteDatabase database;

    private ImageView cancelImg, cameraImg,saveImg;
    TextView message;

    GridView grid;
    public GridViewAdapter adapter;
    File file, org_File;

    public static String rootFolderName = "DMSV2";
    public static String category = "/Outlet External Check";
    String category_for_small = "(Thumbnails)/Outlet External Check";

    String outlet_external_check_id;
    int count = 0;
    Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.outlet_external_check_fragment, container, false);
        activity = (AppCompatActivity) getActivity();
        /*Toolbar toolbar = (Toolbar) view.findViewById(R.id.app_bar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("");*/

        /*TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        titleTextView.setText("Outlet External Check");*/

        database = new Database(activity).getDataBase();

        cursor = database.rawQuery("select * from outlet_external_check", null);
        count = cursor.getCount();
        /*try {
            outlet_external_check_id = "OEC/" + MainFragmentActivity.userInfo.getString("userId") + "/" + MainFragmentActivity.customerId + "/" + (count + 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        cursor = database.rawQuery("select * from customer where CUSTOMER_ID='"+MainFragmentActivity.customerId+"'", null);
        Log.e("Customer ID is>>", MainFragmentActivity.customerId);
        while(cursor.moveToNext()) {
            customerName = cursor.getString(cursor.getColumnIndex("CUSTOMER_NAME"));
        }
        Log.e("Customer Name is>>>", customerName);

        registerIDs();

        // Checking camera availability
        if (!isDeviceSupportCamera()) {
            Toast.makeText(getContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            activity.finish();
        }

        // Check for SD Card
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getContext(), "Error! No SDCARD Found!", Toast.LENGTH_LONG)
                    .show();
        } else {
            // Locate the image folder in your SD Card
            file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "/" + rootFolderName + "/"+customerName + category_for_small);
            file.mkdirs();

            org_File = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "/" + rootFolderName + "/"+customerName + category);
            org_File.mkdirs();
        }

        if (file.isDirectory()) {
            listFile = file.listFiles();
            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];
            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();
            }

            if(FilePathStrings.length!=0){
                message.setVisibility(View.INVISIBLE);
            }
        }

        if (org_File.isDirectory()) {
            org_listFile = org_File.listFiles();
            // Create a String array for FilePathStrings
            org_FilePathStrings = new String[org_listFile.length];
            // Create a String array for FileNameStrings
            org_FileNameStrings = new String[org_listFile.length];

            for (int i = 0; i < org_listFile.length; i++) {
                // Get the path of the image file
                org_FilePathStrings[i] = org_listFile[i].getAbsolutePath();
                // Get the name image file
                org_FileNameStrings[i] = org_listFile[i].getName();
            }
        }

        setAdapters();

        catchEvents();

        return view;
    }

    private void registerIDs() {
        message = (TextView) view.findViewById(R.id.message);
        cancelImg = (ImageView) view.findViewById(R.id.cancel_img);
        cameraImg = (ImageView) view.findViewById(R.id.camera_img);
        saveImg = (ImageView) view.findViewById(R.id.save_img);
        grid = (GridView) view.findViewById(R.id.gridview);
    }

    private void setAdapters() {
        adapter = new GridViewAdapter(activity, FilePathStrings, FileNameStrings, org_FilePathStrings, org_FileNameStrings);
        grid.setAdapter(adapter);
        grid.invalidateViews();
        adapter.notifyDataSetChanged();
    }

    private void catchEvents() {
        cameraImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                try {
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                // start the image capture Intent
                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
            }
        });

        saveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(activity)
                        .setTitle("Alert")
                        .setMessage("Are you sure want to save images?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.e("sizeofimagelist", imageList.size() + "");
                                String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

                                database.beginTransaction();
                                database.execSQL("INSERT INTO outlet_external_check VALUES (\""
                                        + outlet_external_check_id + "\", \""
                                        + MainFragmentActivity.customerId + "\", \'"
                                        + date + "\'"
                                        + ")");

                                database.execSQL("INSERT INTO outlet_external_check_detail VALUES (\""
                                        + outlet_external_check_id + "\", \'"
                                        + imageList.toString() + "\'"
                                        + ")");

                                database.setTransactionSuccessful();
                                database.endTransaction();

                                Toast.makeText(getContext(), "Saving images successully finished.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.backToMarketingActivity(activity);
            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent i = new Intent(activity, ViewImage.class);
                // Pass String arrays FilePathStrings
                i.putExtra("filepath", org_FilePathStrings);
                // Pass String arrays FileNameStrings
                i.putExtra("filename", org_FileNameStrings);
                // Pass click position
                i.putExtra("position", position);
                startActivity(i);
            }

        });

        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(activity)
                        .setTitle("Delete image")
                        .setMessage("Are you sure you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                //String root = Environment.getExternalStorageDirectory().toString();
                                //File mediaStorageDir = new File(root + "/MyanmarPadauk/"+customer.getCustomerName()+"/Outlet External Check");
                                File deleteFile = new File(FilePathStrings[position]);
                                Log.e("File Path String>>>>", FilePathStrings[position].toString());
                                Log.e("Delete file name>>>>", deleteFile.toString());
                                FilePathStrings[position] = null;
                                FileNameStrings[position] = null;
                                deleteFile.delete();

                                File org_deleteFile = new File(org_FilePathStrings[position]);
                                org_FilePathStrings[position] = null;
                                org_FileNameStrings[position] = null;
                                org_deleteFile.delete();

                                imageList.remove(position);


                                file = new File(Environment.getExternalStorageDirectory()
                                        + File.separator + "/" + rootFolderName + "/"+customerName + category_for_small);

                                org_File = new File(Environment.getExternalStorageDirectory()
                                        + File.separator + "/" + rootFolderName + "/"+customerName + category);
                                if (file.isDirectory()) {
                                    listFile = file.listFiles();
                                    // Create a String array for FilePathStrings
                                    FilePathStrings = new String[listFile.length];
                                    // Create a String array for FileNameStrings
                                    FileNameStrings = new String[listFile.length];

                                    for (int i = 0; i < listFile.length; i++) {
                                        // Get the path of the image file
                                        FilePathStrings[i] = listFile[i].getAbsolutePath();
                                        // Get the name image file
                                        FileNameStrings[i] = listFile[i].getName();
                                    }

                                    if (FilePathStrings.length != 0) {
                                        message.setVisibility(View.INVISIBLE);
                                    }
                                }

                                if (org_File.isDirectory()) {
                                    org_listFile = org_File.listFiles();
                                    // Create a String array for FilePathStrings
                                    org_FilePathStrings = new String[org_listFile.length];
                                    // Create a String array for FileNameStrings
                                    org_FileNameStrings = new String[org_listFile.length];

                                    for (int i = 0; i < org_listFile.length; i++) {
                                        // Get the path of the image file
                                        org_FilePathStrings[i] = org_listFile[i].getAbsolutePath();
                                        // Get the name image file
                                        org_FileNameStrings[i] = org_listFile[i].getName();
                                    }
                                }

                                /*adapter = new GridViewAdapter(activity, FilePathStrings, FileNameStrings, org_FilePathStrings, org_FileNameStrings);
                                grid.invalidateViews();
                                grid.setAdapter(adapter);
                                adapter.notifyDataSetChanged();*/
                                setAdapters();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }

    /**
     * Checking device has camera hardware or not
     * */
    private boolean isDeviceSupportCamera() {
        if (getContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == -1) {
                // successfully captured the image
                // display it in image view

                //File dir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                Log.e("FileUri>>>>", fileUri.toString());
                Bitmap b= BitmapFactory.decodeFile(fileUri.getPath());
                Log.e("Original>>>>", b.toString());
                //Bitmap out = Bitmap.createScaledBitmap(b, 320, 480, false);
                Bitmap out = Bitmap.createScaledBitmap(b, (int)(b.getWidth()/6), (int)(b.getHeight()/6), false);

                String root = Environment.getExternalStorageDirectory().toString();
                File mediaStorageDir = new File(root + "/" + rootFolderName + "/"+customerName + category_for_small);

                // Create a media file name
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());

                File file = new File(mediaStorageDir.getPath() + File.separator
                        + "IMG_" + timeStamp + ".jpg");
                FileOutputStream fOut;
                try {
                    fOut = new FileOutputStream(file);
                    out.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    b.recycle();
                    out.recycle();
                } catch (Exception e) {}

                final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath());

                ByteArrayOutputStream bao1 = new ByteArrayOutputStream();
                Log.e(bitmap.toString() + "", "BitMapppppp");

                //for saving bitmap to database
                bitmap.compress(Bitmap.CompressFormat.JPEG, 30, bao1);
                Log.e("here1", "here1");
                byte [] ba1 = bao1.toByteArray();

                imgBase64Str= Base64.encodeToString(ba1, Base64.NO_WRAP);
                Log.e("ImageBase64String", imgBase64Str.toString() + "");

                //add image to string array
                imageList.add(imgBase64Str.toString());
                Log.e("sizeofimagelist", imageList.size() + "");


                refreshAdapter();

            } else if (resultCode == 0) {
                // user cancelled Image capture
                Toast.makeText(getContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void refreshAdapter() {
        super.onResume();
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(activity, "Error! No SDCARD Found!", Toast.LENGTH_LONG)
                    .show();
        } else {
            // Locate the image folder in your SD Card
            file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "/" + rootFolderName + "/"+customerName + category_for_small);
            file.mkdirs();

            org_File = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "/" + rootFolderName + "/"+customerName + category);
            org_File.mkdirs();
        }

        if (file.isDirectory()) {
            listFile = file.listFiles();
            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];
            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();
            }

            if(FilePathStrings.length!=0){
                message.setVisibility(View.INVISIBLE);
            }
        }

        if (org_File.isDirectory()) {
            org_listFile = org_File.listFiles();
            // Create a String array for FilePathStrings
            org_FilePathStrings = new String[org_listFile.length];
            // Create a String array for FileNameStrings
            org_FileNameStrings = new String[org_listFile.length];

            for (int i = 0; i < org_listFile.length; i++) {
                // Get the path of the image file
                org_FilePathStrings[i] = org_listFile[i].getAbsolutePath();
                // Get the name image file
                org_FileNameStrings[i] = org_listFile[i].getName();
            }
        }

        setAdapters();
    }

    /*
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) throws IOException {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /*
     * returning image / video
     */
    private static File getOutputMediaFile(int type) throws IOException {

        // External sdcard location
        String root = Environment.getExternalStorageDirectory().toString();
        File mediaStorageDir = new File(root + "/" + rootFolderName + "/"+customerName + category);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("Oops! Failed create "," directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        File f;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_outlet_external_check, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            Toast.makeText(getContext(), "Save!!!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }*/
}
