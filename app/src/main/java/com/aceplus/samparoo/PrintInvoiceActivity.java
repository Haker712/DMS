package com.aceplus.samparoo;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aceplus.samparoo.model.forApi.Invoice;
import com.aceplus.samparoo.model.forApi.InvoiceDetail;
import com.aceplus.samparoo.model.forApi.InvoicePresent;
import com.aceplus.samparoo.print.Device;
import com.aceplus.samparoo.print.PrintActivity;
import com.aceplus.samparoo.print.PrintSettingActivity;
import com.aceplus.samparoo.print.PrinterClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aceplus_mobileteam on 6/16/17.
 */

public class PrintInvoiceActivity extends Activity{

    Invoice invoie;
    List<InvoiceDetail> invoiceDetailList;
    List<InvoicePresent> invoicePresentList;
    Handler mhandler=null;
    Handler handler = null;
    private Thread tv_update;

    public static boolean checkState = true;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;

    public static final String INVOICE = "INVOICE";
    public static final String INVOICE_DETAIL = "INVOICE_DETAIL";
    public static final String INVOICE_PRESENT = "INVOICE_PRESENT";

    TextView txtPrinterStatus, txtSaleDate, txtInvoiceNo, txtSaleMan, txtBranch;
    ImageView cancelBtn, printBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_print);

        if (getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE) != null) {
            invoie = (Invoice) getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE);
        }

        if (getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE) != null) {
            invoiceDetailList = (List<InvoiceDetail>) getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE);
        }

        if (getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE_PRESENT) != null) {
            invoicePresentList = (List<InvoicePresent>) getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE_PRESENT);
        }
        registerIds();

        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(PrintInvoiceActivity.this,PrintSettingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                //autorunHandler();
            }
        });

    }

    private void registerIds() {
        txtPrinterStatus = (TextView) findViewById(R.id.printer_status);
        txtSaleDate = (TextView) findViewById(R.id.saleDate);
        txtInvoiceNo = (TextView) findViewById(R.id.invoiceId);
        txtSaleMan = (TextView) findViewById(R.id.saleMan);
        txtBranch = (TextView) findViewById(R.id.branch);
        printBtn = (ImageView) findViewById(R.id.print_img);
    }

    private void autorunHandler()
    {
        mhandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MESSAGE_STATE_CHANGE:// è“�ç‰™è¿žæŽ¥çŠ¶
                        switch (msg.arg1) {
                            case PrinterClass.STATE_CONNECTED:// å·²ç»�è¿žæŽ¥
                                break;
                            case PrinterClass.STATE_CONNECTING:// æ­£åœ¨è¿žæŽ¥
                                break;
                            case PrinterClass.STATE_LISTEN:
                            case PrinterClass.STATE_NONE:
                                break;
                            case PrinterClass.SUCCESS_CONNECT:
                                break;
                            case PrinterClass.FAILED_CONNECT:

                                break;
                            case PrinterClass.LOSE_CONNECT:
                                Log.i("PRINTER STATUS ---->>> ", "LOSE_CONNECT");
                        }
                        break;
                    case MESSAGE_READ:
                        break;
                    case MESSAGE_WRITE:
                        break;
                }
            }
        };

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        break;
                    case 1:// æ‰«æ��å®Œæ¯•
                        //PrintActivity.pl.stopScan();
                        Device d=(Device)msg.obj;
                        if(d!=null)
                        {
                            if(PrintSettingActivity.deviceList==null)
                            {
                                PrintSettingActivity.deviceList=new ArrayList<Device>();
                            }

                            if(!checkData(PrintSettingActivity.deviceList,d))
                            {
                                PrintSettingActivity.deviceList.add(d);
                            }
                        }
                        break;
                    case 2:// å�œæ­¢æ‰«æ��
                        break;
                }
            }
        };

        //Printer Status Update
        tv_update = new Thread() {
            public void run() {
                while (true) {
                    if(checkState)
                    {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        txtPrinterStatus.post(new Runnable() {
                            @Override
                            public void run() {
                                if (PrintActivity.pl != null) {
                                    if (PrintActivity.pl.getState() == PrinterClass.STATE_CONNECTED) {
                                        txtPrinterStatus.setText(getResources().getString(
                                                        R.string.str_connected));
                                    } else if (PrintActivity.pl.getState() == PrinterClass.STATE_CONNECTING) {
                                        txtPrinterStatus.setText(getResources().getString(
                                                        R.string.str_connecting));
                                    } else if(PrintActivity.pl.getState() == PrinterClass.LOSE_CONNECT
                                            ||PrintActivity.pl.getState() == PrinterClass.FAILED_CONNECT){
                                        checkState=false;
                                        txtPrinterStatus.setText(getResources().getString(
                                                        R.string.str_disconnected));
                                        Intent intent=new Intent();
                                        intent.setClass(PrintInvoiceActivity.this,PrintSettingActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                        startActivity(intent);
                                    }else{
                                        txtPrinterStatus.setText(getResources().getString(
                                                        R.string.str_disconnected));
                                    }
                                }
                            }
                        });
                    }
                }
            }
        };

    }

    private boolean checkData(List<Device> list,Device d)
    {
        for (Device device : list) {
            if(device.deviceAddress.equals(d.deviceAddress))
            {
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onRestart() {
        checkState = true;
        super.onRestart();
    }
}
