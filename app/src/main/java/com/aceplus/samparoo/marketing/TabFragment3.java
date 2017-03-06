package com.aceplus.samparoo.marketing;

/**
 * Created by ACEPLU049 on 2/24/2016.
 */

import android.app.AlertDialog;
import android.content.Context;
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
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.aceplus.samparoo.R;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TabFragment3 extends Fragment {

    AppCompatActivity activity;
    View view;

    // Declare variables
    private String[] FilePathStrings, org_FilePathStrings;
    private String[] FileNameStrings, org_FileNameStrings;
    private File[] listFile, org_listFile;
    GridView grid;
    public GridViewAdapter adapter;
    File file, org_File;

    TextView message;

    private ImageView cancelImg,addImg,cameraImg,saveImg;

    // Activity request codes
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;

    private Uri fileUri; // file url to store image/video

    RelativeLayout rlayout;
    TextView txtNotes;
    Spinner comp_nameSpinner;
    ArrayList<String> comp_nameList = new ArrayList<>();
    ArrayList<String> comp_idList = new ArrayList<>();

    SQLiteDatabase database;

    private static String selected_comp_name="Select Competitor";
    String note;

    ArrayList<String> imageList = new ArrayList<>();
    String imgBase64Str;

    public static String category = "/Competitors' Activities/";
    String category_for_small = "(Thumbnails)/Competitors' Activities/";

    String competitor_activities_id;
    int count = 0;
    Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab_fragment_3, container, false);

        /*Toolbar toolbar = (Toolbar) view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Competitors' Activities");
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        activity = (AppCompatActivity) getActivity();

        database = new Database(activity).getDataBase();

        cursor = database.rawQuery("select * from competitor_activities", null);
        count = cursor.getCount();
        /*try {
            competitor_activities_id = "CA/" + MainFragmentActivity.userInfo.getString("userId") + "/" + MainFragmentActivity.customerId + "/" + (count + 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        //first initialize comp_nameList
        comp_nameList.add(0, "Select Competitor");
        comp_idList.add(0, null);

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

            if(selected_comp_name.equals("Select Competitor")) {
                // Locate the image folder in your SD Card
                file = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "/"+ TabFragment1.rootFolderName +"/"+TabFragment1.customerName + category_for_small + "Select Competitor");
                file.mkdirs();

                org_File = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "/"+ TabFragment1.rootFolderName +"/"+TabFragment1.customerName + category + "Select Competitor");
                org_File.mkdirs();
            }
            else{
                // Locate the image folder in your SD Card
                file = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "/"+ TabFragment1.rootFolderName +"/"+TabFragment1.customerName + category_for_small + selected_comp_name);
                file.mkdirs();

                org_File = new File(Environment.getExternalStorageDirectory()
                        + File.separator + "/"+ TabFragment1.rootFolderName +"/"+TabFragment1.customerName + category + selected_comp_name);
                org_File.mkdirs();
            }
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

            if(FilePathStrings.length != 0){
                message.setVisibility(View.GONE);
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

        Cursor cursor = database.rawQuery("SELECT * FROM COMPETITOR where customer_id='"+MainFragmentActivity.customerId+"'", null);
        int count = cursor.getCount();
        Log.e("Count>>>>>>Count>>>>>>>", count + "");

        rlayout = (RelativeLayout) view.findViewById(R.id.rlayout);
        if(count==0){
            rlayout.setVisibility(View.GONE);
            cameraImg.setVisibility(View.GONE);
        }
        else{
            rlayout.setVisibility(View.VISIBLE);
            message.setVisibility(View.GONE);
            cameraImg.setVisibility(View.VISIBLE);
        }

        while(cursor.moveToNext()){
            String comp_id = cursor.getString(cursor.getColumnIndex("competitor_id"));
            String comp_name = cursor.getString(cursor.getColumnIndex("competitor_name"));
            Log.e("comp_name>>>>>", comp_name);
            comp_nameList.add(comp_name.toString());
            comp_idList.add(comp_id.toString());
        }

        setAdapters();

        catchEvents();

        return view;
    }

    private void registerIDs() {
        message = (TextView) view.findViewById(R.id.message);
        cancelImg = (ImageView) view.findViewById(R.id.cancel_img);
        addImg = (ImageView) view.findViewById(R.id.add_img);
        cameraImg = (ImageView) view.findViewById(R.id.camera_img);
        saveImg = (ImageView) view.findViewById(R.id.save_img);
        grid = (GridView) view.findViewById(R.id.gridview);
        txtNotes = (TextView) view.findViewById(R.id.txt_notes);
        comp_nameSpinner = (Spinner) view.findViewById(R.id.comp_name_spinner);
    }

    private void setAdapters() {
        adapter = new GridViewAdapter(activity, FilePathStrings, FileNameStrings, org_FilePathStrings, org_FileNameStrings);
        grid.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        ArrayAdapter<String> aa = new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item,comp_nameList);
        //aa.setDropDownViewResource(R.layout.spinner_layout);
        aa.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        comp_nameSpinner.setAdapter(aa);
        aa.notifyDataSetChanged();
    }

    private void catchEvents() {
        cameraImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected_comp_name.equals("Select Competitor")){
                    new AlertDialog.Builder(activity)
                            .setTitle("Competitor's Name is required")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setMessage("You need to select competitor_name.")
                            .setPositiveButton("OK", null)
                            .show();
                }
                else {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                    // start the image capture Intent
                    startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                }
            }
        });

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAdd();
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
                                database.execSQL("INSERT INTO competitor_activities VALUES (\""
                                        + competitor_activities_id + "\", \""
                                        + MainFragmentActivity.customerId + "\", \'"
                                        + date + "\'"
                                        + ")");

                                for(int i = 0; i < comp_idList.size(); i++) {
                                    database.execSQL("INSERT INTO competitor_activities_detail VALUES (\""
                                            + competitor_activities_id + "\", \'"
                                            + comp_idList.get(i) + "\""
                                            + ")");

                                    database.execSQL("update competitor set imageList = '"+imageList.toString()+"' where competitor_id='"+comp_idList.get(i + 1)+"'");
                                }

                                database.setTransactionSuccessful();
                                database.endTransaction();

                                Toast.makeText(getContext(), "Saving Competitor's Details successfully finished.", Toast.LENGTH_SHORT).show();
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


                                file = new File(Environment.getExternalStorageDirectory()
                                        + File.separator + "/"+ TabFragment1.rootFolderName +"/"+TabFragment1.customerName + category_for_small + selected_comp_name);
                                file.mkdirs();

                                org_File = new File(Environment.getExternalStorageDirectory()
                                        + File.separator + "/"+ TabFragment1.rootFolderName +"/"+TabFragment1.customerName + category + selected_comp_name);
                                org_File.mkdirs();

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

                                adapter = new GridViewAdapter(activity, FilePathStrings, FileNameStrings, org_FilePathStrings, org_FileNameStrings);
                                grid.invalidateViews();
                                grid.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });

        //for competitor acitvity note
        comp_nameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                message.setVisibility(View.GONE);
                selected_comp_name = comp_nameList.get(position);
                if (selected_comp_name.equals("Select Competitor")) {
                    txtNotes.setVisibility(View.GONE);
                    cameraImg.setVisibility(View.GONE);
                    refreshAdapterForNotCompetitor();
                } else {
                    txtNotes.setVisibility(View.VISIBLE);
                    cameraImg.setVisibility(View.VISIBLE);
                    Cursor cursor = database.rawQuery("SELECT note FROM COMPETITOR WHERE competitor_id='" + comp_idList.get(position) + "'", null);
                    while (cursor.moveToNext()) {
                        note = cursor.getString(cursor.getColumnIndex("note"));
                    }
                    txtNotes.setText(note);
                    refreshAdapter();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                //previewCapturedImage();

                Log.e("FileUri>>>>", fileUri.toString());
                Bitmap b= BitmapFactory.decodeFile(fileUri.getPath());
                Log.e("Original>>>>", b.toString());
                //Bitmap out = Bitmap.createScaledBitmap(b, 320, 480, false);
                Bitmap out = Bitmap.createScaledBitmap(b, (int)(b.getWidth()/6), (int)(b.getHeight()/6), false);

                String root = Environment.getExternalStorageDirectory().toString();
                File mediaStorageDir = new File(root + "/" + TabFragment1.rootFolderName + "/" + TabFragment1.customerName + category_for_small + selected_comp_name);

                // Create a media file name
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());

                File file = new File(mediaStorageDir.getPath() + File.separator
                        + "IMG_" + timeStamp + ".jpg");
                //int ct = (int) dir.length();
                //File file = new File(dir, ct + " resize.png");
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
                    + File.separator + "/"+ TabFragment1.rootFolderName +"/"+TabFragment1.customerName + category_for_small + selected_comp_name);
            file.mkdirs();

            org_File = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "/"+ TabFragment1.rootFolderName +"/"+TabFragment1.customerName + category + selected_comp_name);
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

        adapter = new GridViewAdapter(activity, FilePathStrings, FileNameStrings, org_FilePathStrings, org_FileNameStrings);
        grid.invalidateViews();
        grid.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void refreshAdapterForNotCompetitor() {
        super.onResume();
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getContext(), "Error! No SDCARD Found!", Toast.LENGTH_LONG)
                    .show();
        } else {
            // Locate the image folder in your SD Card
            file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "/"+ TabFragment1.rootFolderName +"/"+TabFragment1.customerName + category_for_small + "Select Competitor");
            file.mkdirs();

            org_File = new File(Environment.getExternalStorageDirectory()
                    + File.separator + "/"+ TabFragment1.rootFolderName +"/"+TabFragment1.customerName + category + "Select Competitor");
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
                message.setVisibility(View.GONE);
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

        adapter = new GridViewAdapter(activity, FilePathStrings, FileNameStrings, org_FilePathStrings, org_FileNameStrings);
        grid.invalidateViews();
        grid.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /*
     * Display image from a path to ImageView
     */
    private void previewCapturedImage() {
        try {
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
            //imgPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /*
     * Creating file uri to store image/video
     */
    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /*
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
		/*File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);*/

        String root = Environment.getExternalStorageDirectory().toString();
        File mediaStorageDir = new File(root + "/" + TabFragment1.rootFolderName + "/" + TabFragment1.customerName + category +selected_comp_name);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("Oops! Failed create ", " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    public void userAdd(){
        //Toast.makeText(getApplicationContext(),"Clicked!!!", Toast.LENGTH_LONG).show();

        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view1 = layoutInflater.inflate(R.layout.dialog_box_competitor, null);

        final EditText competitor_nameEditText = (EditText) view1.findViewById(R.id.competitor_name);
        final EditText noteEditText = (EditText) view1.findViewById(R.id.note);
        final TextView messageTextView = (TextView) view1.findViewById(R.id.message);

        /*competitor_nameEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                competitor_nameEditText.requestLayout();
                Competitor_Activity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
                return false;
            }
        });*/

        final AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setView(view1)
                .setTitle("Competitor's Activities")
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

                        competitor_nameEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

                        if (competitor_nameEditText.getText().toString().length() == 0) {

                            messageTextView.setText("You must specify competitor's name.");
                            return;
                        }

                        if (noteEditText.getText().toString().length() == 0) {

                            messageTextView.setText("You must specify note.");
                            return;
                        }

                        database = new Database(activity).getDataBase();

                        String competitor_name = competitor_nameEditText.getText().toString();
                        String note = noteEditText.getText().toString();

                        cursor = database.rawQuery("SELECT * FROM COMPETITOR", null);
                        int competitor_count = cursor.getCount();
                        String competitor_id = "CPT" + competitor_count;

                        database.beginTransaction();
                        database.execSQL("INSERT INTO competitor VALUES (\""
                                + MainFragmentActivity.customerId + "\", \""
                                + competitor_id + "\", \""
                                + competitor_name + "\", \""
                                + note + "\", \'"
                                + null + "\'"
                                + ")");

                        database.setTransactionSuccessful();
                        database.endTransaction();

                        cursor = database.rawQuery("SELECT * FROM COMPETITOR", null);
                        int count = cursor.getCount();
                        Log.e("Count>>>>>>Count>>>>>>>", count + "");
                        if(count>0){
                            Toast.makeText(getContext(),"Add Competitor's Details Successfully", Toast.LENGTH_LONG).show();
                            rlayout.setVisibility(View.VISIBLE);
                            message.setVisibility(View.GONE);
                            cameraImg.setVisibility(View.VISIBLE);
                        }

                        comp_nameList.add(competitor_name);
                        comp_idList.add(competitor_id);

                        //comp_nameSpinner = (Spinner) view.findViewById(R.id.comp_name_spinner);

                        ArrayAdapter<String> aa = new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item,comp_nameList);
                        //aa.setDropDownViewResource(R.layout.spinner_layout);
                        aa.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                        comp_nameSpinner.setAdapter(aa);
                        aa.notifyDataSetChanged();

                        alertDialog.dismiss();
                    }
                });

            }
        });
        alertDialog.show();
    }
}