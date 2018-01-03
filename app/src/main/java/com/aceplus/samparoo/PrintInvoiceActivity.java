package com.aceplus.samparoo;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aceplus.samparoo.customer.SaleCheckoutActivity;
import com.aceplus.samparoo.customer.SaleOrderCheckoutActivity;
import com.aceplus.samparoo.model.CreditInvoice;
import com.aceplus.samparoo.model.Deliver;
import com.aceplus.samparoo.model.Product;
import com.aceplus.samparoo.model.Promotion;
import com.aceplus.samparoo.model.SoldProduct;
import com.aceplus.samparoo.model.forApi.Invoice;
import com.aceplus.samparoo.utils.Database;
import com.aceplus.samparoo.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aceplus_mobileteam on 6/16/17.
 */

public class PrintInvoiceActivity extends Activity{

    Invoice invoie;
    List<SoldProduct> invoiceDetailList;
    List<Promotion> invoicePresentList;
    List<CreditInvoice> creditList;
    List<SoldProduct> saleReturnList;

    public static final String INVOICE = "INVOICE";
    public static final String INVOICE_DETAIL = "INVOICE_DETAIL";
    public static final String INVOICE_PRESENT = "INVOICE_PRESENT";
    String taxType = null, branchCode = null, creditFlg = null;
    Double taxPercent = 0.0;
    int pos = 0;

    TextView txtSaleDate, txtInvoiceNo, txtSaleMan, txtBranch, totalAmountTxtView, netAmountTxtView, prepaidAmountTxtView, print_discountAmountTxtView;
    TextView creditCustomerName, creditTownshipName, creditRecievieNo, creditInvoiceNo, creditCollectPerson, creditDate, crediTotalAmount, crediDiscount, crediNetAmount, creditReceiveAmount;;
    TextView deliveryCustomerNameTxt, deliveryTownshipNameTxt, deliveryOrderNoTxt, deliveryOrderPersonTxt, deliveryInvoiceNoTxt, deliveryPersonTxt, deliveryDateTxt;
    ImageView cancelBtn, printBtn;
    String saleman_Id = "", printMode = "";
    SQLiteDatabase database;
    SoldProductListRowAdapter soldProductListRowAdapter;
    PromotionProductCustomAdapter promotionProductCustomAdapter;
    ListView soldProductListView, promotionPlanItemListView;
    RelativeLayout saleLayout;
    LinearLayout creditPrintHeaderLayout, salePrintHeaderLayout1, salePrintHeaderLayout2, deliveryHeaderLayout1;
    private Deliver orderedInvoice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_print);
        database = new Database(this).getDataBase();

        if (getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE) != null) {
            invoie = (Invoice) getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE);
        }

        if (getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE_PRESENT) != null) {
            invoicePresentList = (List<Promotion>) getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE_PRESENT);
        }

        if (getIntent().getSerializableExtra(SaleCheckoutActivity.SOLD_PROUDCT_LIST_KEY) != null) {
            invoiceDetailList = (List<SoldProduct>) getIntent().getSerializableExtra(SaleCheckoutActivity.SOLD_PROUDCT_LIST_KEY);
        }

        if (getIntent().getSerializableExtra("CREDIT") != null) {
            creditList = (List<CreditInvoice>) getIntent().getSerializableExtra("CREDIT");
        }

        if (getIntent().getSerializableExtra("CREDIT_FLG") != null) {
            creditFlg = (String) getIntent().getSerializableExtra("CREDIT_FLG");
        }

        if (getIntent().getSerializableExtra("CUR_POS") != null) {
            pos = (int) getIntent().getSerializableExtra("CUR_POS");
        }

        if (getIntent().getSerializableExtra("PRINT_MODE") != null) {
            printMode = (String) getIntent().getSerializableExtra("PRINT_MODE");
        }

        if (getIntent().getSerializableExtra(SaleOrderCheckoutActivity.ORDERED_INVOICE_KEY) != null) {
            orderedInvoice = (Deliver) getIntent().getSerializableExtra(SaleOrderCheckoutActivity.ORDERED_INVOICE_KEY);
        }

        registerIds();
        getTaxAmount();
        setDataToWidgets();
        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View v1 = getWindow().getDecorView().getRootView();
                v1.setDrawingCacheEnabled(true);
                Bitmap myBitmap = v1.getDrawingCache();

                Log.e(myBitmap+"", "OutPutBitmap");

                if (getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE) != null) {
                    invoie = (Invoice) getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE);
                }

                if (getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE_PRESENT) != null) {
                    invoicePresentList = (List<Promotion>) getIntent().getSerializableExtra(PrintInvoiceActivity.INVOICE_PRESENT);
                }

                if (getIntent().getSerializableExtra(SaleCheckoutActivity.SOLD_PROUDCT_LIST_KEY) != null) {
                    invoiceDetailList = (List<SoldProduct>) getIntent().getSerializableExtra(SaleCheckoutActivity.SOLD_PROUDCT_LIST_KEY);
                }

                if (getIntent().getSerializableExtra("sale_return_list") != null) {
                    saleReturnList = (List<SoldProduct>) getIntent().getSerializableExtra(SaleCheckoutActivity.SOLD_PROUDCT_LIST_KEY);
                }

                if(printMode.equals("C") && creditFlg!= null && !creditFlg.equals("")) {
                    Utils.saveInvoiceImageIntoGallery(creditList.get(pos).getInvoiceNo(), PrintInvoiceActivity.this,myBitmap, "Credit Collect");
                    if (creditList != null && creditList.size() > 0) {
                        Utils.printCredit(PrintInvoiceActivity.this, getCustomerName(creditList.get(pos).getCustomerId()), creditList.get(pos).getInvoiceNo(), getCustomerTownshipName(creditList.get(pos).getCustomerId())
                                , getSaleManName(creditList.get(pos).getSaleManId()), creditList.get(pos));
                    }
                } else if(printMode.equals("S")){
                    Utils.saveInvoiceImageIntoGallery(invoie.getId(), PrintInvoiceActivity.this,myBitmap, "Sale");
                    List<Promotion> tempPresentList = new ArrayList<>();
                    tempPresentList.addAll(invoicePresentList);

                    List<SoldProduct> editProductList = arrangeProductList(invoiceDetailList, tempPresentList);
                    Utils.print(PrintInvoiceActivity.this, getCustomerName(Integer.parseInt(invoie.getCustomerId()))
                            , invoie.getId()
                            , getSaleManName(invoie.getSalepersonId())
                            , getCustomerTownshipName(Integer.parseInt(invoie.getCustomerId()))
                            , invoie, editProductList, tempPresentList, Utils.PRINT_FOR_NORMAL_SALE
                            , Utils.FOR_OTHERS);
                } else if(printMode.equals("D")) {
                    Utils.saveInvoiceImageIntoGallery(invoie.getId(), PrintInvoiceActivity.this,myBitmap, "Deliver");
                    List<SoldProduct> editProductList = arrangeProductList(invoiceDetailList, invoicePresentList);
                    Utils.printDeliver(PrintInvoiceActivity.this, getCustomerName(Integer.parseInt(invoie.getCustomerId()))
                            , orderedInvoice.getInvoiceNo()
                            , getSaleManName(orderedInvoice.getSaleManId())
                            , invoie.getId()
                            , getSaleManName(invoie.getSalepersonId())
                            , getCustomerTownshipName(Integer.parseInt(invoie.getCustomerId()))
                            , invoie, editProductList, invoicePresentList, Utils.PRINT_FOR_NORMAL_SALE
                            , Utils.FOR_OTHERS);
                } /*else if(printMode.equals("SX")) {
                    Utils.saveInvoiceImageIntoGallery(invoie.getId(), PrintInvoiceActivity.this,myBitmap, "Sale");
                    List<Promotion> tempPresentList = new ArrayList<>();
                    tempPresentList.addAll(invoicePresentList);

                    List<SoldProduct> editProductList = arrangeProductList(invoiceDetailList, tempPresentList);
                    Utils.printSaleExchange(PrintInvoiceActivity.this, getCustomerName(Integer.parseInt(invoie.getCustomerId()))
                            , invoie.getId()
                            , getSaleManName(invoie.getSalepersonId())
                            , getCustomerTownshipName(Integer.parseInt(invoie.getCustomerId()))
                            , invoie, editProductList, saleReturnList, tempPresentList, Utils.PRINT_FOR_NORMAL_SALE
                            , Utils.FOR_OTHERS);
                } */else if(printMode.equals("SR")){
                    Utils.saveInvoiceImageIntoGallery(invoie.getId(), PrintInvoiceActivity.this,myBitmap, "Sale");
                    List<Promotion> tempPresentList = new ArrayList<>();

                    if(invoicePresentList != null && invoicePresentList.size() > 0) {
                        tempPresentList.addAll(invoicePresentList);
                    }

                    List<SoldProduct> editProductList = arrangeProductList(invoiceDetailList, tempPresentList);
                    Utils.print(PrintInvoiceActivity.this, getCustomerName(Integer.parseInt(invoie.getCustomerId()))
                            , invoie.getId()
                            , getSaleManName(invoie.getSalepersonId())
                            , getCustomerTownshipName(Integer.parseInt(invoie.getCustomerId()))
                            , invoie, editProductList, tempPresentList, Utils.PRINT_FOR_NORMAL_SALE
                            , Utils.FOR_OTHERS);
                }

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void registerIds() {
        txtSaleDate = (TextView) findViewById(R.id.saleDate);
        txtInvoiceNo = (TextView) findViewById(R.id.invoiceId);
        txtSaleMan = (TextView) findViewById(R.id.saleMan);
        txtBranch = (TextView) findViewById(R.id.branch);
        printBtn = (ImageView) findViewById(R.id.print_img);
        cancelBtn = (ImageView) findViewById(R.id.cancel_img);
        soldProductListView = (ListView) findViewById(R.id.print_soldProductList);
        promotionPlanItemListView = (ListView) findViewById(R.id.promotion_plan_item_listview);
        totalAmountTxtView = (TextView) findViewById(R.id.print_totalAmount);
        netAmountTxtView = (TextView) findViewById(R.id.print_net_amount);
        prepaidAmountTxtView = (TextView) findViewById(R.id.print_prepaidAmount);
        print_discountAmountTxtView = (TextView) findViewById(R.id.print_discountAmount);
        saleLayout = (RelativeLayout) findViewById(R.id.printForSaleLayout);
        creditPrintHeaderLayout = (LinearLayout) findViewById(R.id.crditPrintHeaderLayout1);
        salePrintHeaderLayout1 = (LinearLayout) findViewById(R.id.salePrintHeaderLayout1);
        salePrintHeaderLayout2 = (LinearLayout) findViewById(R.id.salePrintHeaderLayout2);
        deliveryHeaderLayout1 = (LinearLayout) findViewById(R.id.deliverPrintHeaderLayout1);

        if(printMode.equals("C") && creditFlg != null && !creditFlg.equals("")) {
            creditCustomerName = (TextView) findViewById(R.id.credit_customer_name);
            creditTownshipName = (TextView) findViewById(R.id.credit_township_name);
            creditRecievieNo = (TextView) findViewById(R.id.credit_receive_no);
            creditInvoiceNo = (TextView) findViewById(R.id.credit_invoice_no);
            creditCollectPerson = (TextView) findViewById(R.id.credit_sale_man);
            creditDate = (TextView) findViewById(R.id.credit_date);
            crediTotalAmount = (TextView) findViewById(R.id.credit_total_amt);

            crediDiscount = (TextView) findViewById(R.id.credit_discount);
            crediNetAmount = (TextView) findViewById(R.id.credit_net_amount);
            creditReceiveAmount = (TextView) findViewById(R.id.credit_receive_amt);
        }

        if(printMode.equals("D") && orderedInvoice != null) {
            deliveryCustomerNameTxt = (TextView) findViewById(R.id.deliver_customer_name);
            deliveryTownshipNameTxt = (TextView) findViewById(R.id.deliver_township_name);
            deliveryOrderNoTxt = (TextView) findViewById(R.id.deliver_order_invoice_no);
            deliveryOrderPersonTxt = (TextView) findViewById(R.id.deliver_order_person);
            deliveryInvoiceNoTxt = (TextView) findViewById(R.id.deliver_invoice_no);
            deliveryPersonTxt = (TextView) findViewById(R.id.deliver_person);
            deliveryDateTxt = (TextView) findViewById(R.id.deliver_date);
        }
    }

    private void setDataToWidgets() {
        if(printMode.equals("S")) {
            txtSaleDate.setText(invoie.getDate().substring(0,10));
            txtInvoiceNo.setText(invoie.getId());
            txtSaleMan.setText(getSaleManName(invoie.getSalepersonId()));
            txtBranch.setText(branchCode);
            soldProductListView.setAdapter(new SoldProductListRowAdapter(this));
            setPromotionProductListView();
            totalAmountTxtView.setText(Utils.formatAmount(invoie.getTotalAmt()));
            if(taxType.equalsIgnoreCase("E")) {
                if(invoie.getTotalAmt() != null && invoie.getTotalDiscountAmt() != null) {
                    netAmountTxtView.setText(Utils.formatAmount(invoie.getTotalAmt() - invoie.getTotalDiscountAmt() + invoie.getTaxAmount()));
                }
            } else {
                if(invoie.getTotalAmt() != null && invoie.getTotalDiscountAmt() != null) {
                    netAmountTxtView.setText(Utils.formatAmount(invoie.getTotalAmt() - invoie.getTotalDiscountAmt()));
                }
            }
            prepaidAmountTxtView.setText(Utils.formatAmount(invoie.getTotalPayAmt()));
            print_discountAmountTxtView.setText(Utils.formatAmount(invoie.getTotalDiscountAmt()) + " (" + new DecimalFormat("#0.00").format(invoie.getDiscountPercent()) + "%)");
        } else if(printMode.equals("C")){
            saleLayout.setVisibility(View.GONE);
            salePrintHeaderLayout1.setVisibility(View.GONE);
            salePrintHeaderLayout2.setVisibility(View.GONE);
            creditPrintHeaderLayout.setVisibility(View.VISIBLE);
            creditDate.setText(creditList.get(pos).getInvoiceDate().substring(0,10));
            creditInvoiceNo.setText(creditList.get(pos).getInvoiceNo());
            creditCollectPerson.setText(getSaleManName(creditList.get(pos).getSaleManId()));
            creditCustomerName.setText(getCustomerName(creditList.get(pos).getCustomerId()));
            creditTownshipName.setText(getCustomerTownshipName(creditList.get(pos).getCustomerId()));
            creditRecievieNo.setText(creditList.get(pos).getInvoiceNo());

            crediTotalAmount.setTextSize(30);
            crediDiscount.setTextSize(30);
            crediNetAmount.setTextSize(30);
            creditReceiveAmount.setTextSize(30);

            crediTotalAmount.setText(Utils.formatAmount(creditList.get(pos).getAmt()));
            crediNetAmount.setText(Utils.formatAmount(creditList.get(pos).getAmt()));
            creditReceiveAmount.setText(Utils.formatAmount(creditList.get(pos).getPayAmt()));
            crediDiscount.setText("0.0 (0%)");
        } else if(printMode.equals("D")) {
            salePrintHeaderLayout1.setVisibility(View.GONE);
            salePrintHeaderLayout2.setVisibility(View.GONE);
            deliveryHeaderLayout1.setVisibility(View.VISIBLE);

            int cusId = Integer.parseInt(orderedInvoice.getCustomerId());

            deliveryCustomerNameTxt.setText(getCustomerName(cusId));
            deliveryTownshipNameTxt.setText(getCustomerTownshipName(cusId));
            deliveryOrderNoTxt.setText(orderedInvoice.getInvoiceNo());
            deliveryOrderPersonTxt.setText(getSaleManName(orderedInvoice.getSaleManId()));
            deliveryInvoiceNoTxt.setText(invoie.getId());
            deliveryPersonTxt.setText(getSaleManName(invoie.getSalepersonId()));
            deliveryDateTxt.setText(invoie.getDate().substring(0,10));

            soldProductListView.setAdapter(new SoldProductListRowAdapter(this));
            setPromotionProductListView();
            totalAmountTxtView.setText(Utils.formatAmount(invoie.getTotalAmt()));
            if(taxType.equalsIgnoreCase("E")) {
                netAmountTxtView.setText(Utils.formatAmount(invoie.getTotalAmt() - invoie.getTotalDiscountAmt() + invoie.getTaxAmount()));
            } else {
                netAmountTxtView.setText(Utils.formatAmount(invoie.getTotalAmt() - invoie.getTotalDiscountAmt()));
            }
            prepaidAmountTxtView.setText(Utils.formatAmount(invoie.getTotalPayAmt()));
            print_discountAmountTxtView.setText(Utils.formatAmount(invoie.getTotalDiscountAmt()) + " (" + new DecimalFormat("#0.00").format(invoie.getDiscountPercent()) + "%)");
        } else if(printMode.equals("SR")) {
            txtSaleDate.setText(invoie.getDate().substring(0,10));
            txtInvoiceNo.setText(invoie.getId());
            txtSaleMan.setText(getSaleManName(invoie.getSalepersonId()));
            txtBranch.setText(branchCode);
            soldProductListView.setAdapter(new SoldProductListRowAdapter(this));
            setPromotionProductListView();
            if(invoie.getTotalAmt() != null) {
                totalAmountTxtView.setText(Utils.formatAmount(invoie.getTotalAmt()));
            }
            if(taxType.equalsIgnoreCase("E")) {
                if(invoie.getTotalAmt() != null && invoie.getTotalDiscountAmt() != null) {
                    netAmountTxtView.setText(Utils.formatAmount(invoie.getTotalAmt() - invoie.getTotalDiscountAmt() + invoie.getTaxAmount()));
                }
            } else {
                if(invoie.getTotalAmt() != null && invoie.getTotalDiscountAmt() != null) {
                    netAmountTxtView.setText(Utils.formatAmount(invoie.getTotalAmt() - invoie.getTotalDiscountAmt()));
                }
            }

            if(invoie.getTotalPayAmt() != null){
                prepaidAmountTxtView.setText(Utils.formatAmount(invoie.getTotalPayAmt()));
            }

            if(invoie.getTotalDiscountAmt() != null) {
                print_discountAmountTxtView.setText(Utils.formatAmount(invoie.getTotalDiscountAmt()) + " (" + new DecimalFormat("#0.00").format(invoie.getDiscountPercent()) + "%)");
            }
        }

    }

    private String getSaleManName(int id) {
        Cursor cursorSaleMan = database.rawQuery("SELECT USER_NAME FROM SALE_MAN WHERE ID = " + id, null);
        String saleManName = "";
        while(cursorSaleMan.moveToNext()) {
            saleManName = cursorSaleMan.getString(cursorSaleMan.getColumnIndex("USER_NAME"));

        }
        return saleManName;
    }

    private String getCustomerName(int id) {
        Cursor cursorCustomer = database.rawQuery("SELECT CUSTOMER_NAME FROM CUSTOMER WHERE id = " + id + "", null);
        String customerName = "";
        while(cursorCustomer.moveToNext()) {
            customerName = cursorCustomer.getString(cursorCustomer.getColumnIndex("CUSTOMER_NAME"));

        }
        return customerName;
    }

    private String getCustomerTownshipName(int id) {
        Cursor cursorCustomer = database.rawQuery("SELECT TOWNSHIP_NAME FROM TOWNSHIP WHERE TOWNSHIP_ID = (SELECT township_number FROM CUSTOMER where id = "+ id + ")", null);
        String townshipName = "";
        while(cursorCustomer.moveToNext()) {
            townshipName = cursorCustomer.getString(cursorCustomer.getColumnIndex("TOWNSHIP_NAME"));

        }
        return townshipName;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utils.backToCustomer(PrintInvoiceActivity.this);
    }

    private void setPromotionProductListView() {
        if(invoicePresentList != null){
            int itemLength = invoicePresentList.size() * 100;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, itemLength);
            params.setMargins(20, 0, 0, 20);
            promotionPlanItemListView.setLayoutParams(params);

            promotionProductCustomAdapter = new PromotionProductCustomAdapter(this);
            promotionPlanItemListView.setAdapter(promotionProductCustomAdapter);
            promotionProductCustomAdapter.notifyDataSetChanged();
        }

    }

    private class SoldProductListRowAdapter extends ArrayAdapter<SoldProduct> {

        final Activity context;

        public SoldProductListRowAdapter(Activity context) {

            super(context, R.layout.list_row_sold_product_checkout, invoiceDetailList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            SoldProduct soldProduct = invoiceDetailList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_sold_product_checkout, null, true);

            final TextView nameTextView = (TextView) view.findViewById(R.id.name);
            final TextView umTextView = (TextView) view.findViewById(R.id.um);
            final TextView qtyTextView = (TextView) view.findViewById(R.id.qty);
            final TextView priceTextView = (TextView) view.findViewById(R.id.price);
            final TextView discountTextView = (TextView) view.findViewById(R.id.discount);
            final TextView totalAmountTextView = (TextView) view.findViewById(R.id.amount);

            umTextView.setVisibility(View.GONE);

            nameTextView.setText(soldProduct.getProduct().getName());
            umTextView.setText(soldProduct.getProduct().getUm());
            qtyTextView.setText(soldProduct.getQuantity() + "");

            discountTextView.setText(soldProduct.getDiscountAmount() + "");

            if(soldProduct.getPromotionPrice() == 0.0) {
                priceTextView.setText(Utils.formatAmount(soldProduct.getProduct().getPrice()));
            } else {
                priceTextView.setText(Utils.formatAmount(soldProduct.getPromotionPrice()));
            }

            totalAmountTextView.setText(Utils.formatAmount(soldProduct.getTotalAmount()));
            return view;
        }
    }

    private class PromotionProductCustomAdapter extends ArrayAdapter<Promotion> {

        final Activity context;

        public PromotionProductCustomAdapter(Activity context) {
            super(context, R.layout.list_row_promotion, invoicePresentList);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Promotion promotion = invoicePresentList.get(position);

            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.list_row_promotion, null, true);

            final TextView nameTextView = (TextView) view.findViewById(R.id.productName);
            final TextView qtyTextView = (TextView) view.findViewById(R.id.qty);
            final TextView priceTextView = (TextView) view.findViewById(R.id.price);

            if (!promotion.getPromotionProductName().equals("") || promotion.getPromotionProductName() != null) {
                nameTextView.setText(promotion.getPromotionProductName());
            } else {
                nameTextView.setVisibility(View.GONE);
            }
            if (promotion.getPromotionQty() != 0) {
                qtyTextView.setText(promotion.getPromotionQty() + "");
            } else {
                qtyTextView.setVisibility(View.GONE);
            }
            if (promotion.getPromotionPrice()!= null && promotion.getPromotionPrice() != 0.0) {
                priceTextView.setText(promotion.getPromotionPrice() + "");
            } else {
                priceTextView.setVisibility(View.GONE);
            }

            return view;
        }
    }

    void getTaxAmount() {
        Cursor cursorTax = database.rawQuery("SELECT TaxType, Tax, Branch_Code FROM COMPANYINFORMATION", null);
        while(cursorTax.moveToNext()) {
            taxType = cursorTax.getString(cursorTax.getColumnIndex("TaxType"));
            taxPercent = cursorTax.getDouble(cursorTax.getColumnIndex("Tax"));
            branchCode = cursorTax.getString(cursorTax.getColumnIndex("Branch_Code"));
        }
    }

    List<SoldProduct> arrangeProductList(List<SoldProduct> soldProductList, List<Promotion> presentList) {

            List<Integer> positionList = new ArrayList<>();

            List<SoldProduct> newSoldProductList = new ArrayList<>();
            List<Promotion> newPresentList = new ArrayList<>();

            for(int i = 0; i < presentList.size(); i++) {

                int stockId1 = 0;
                if(presentList.get(i).getPromotionProductId() != null && !presentList.get(i).getPromotionProductId().equals("")) {
                    stockId1 = Integer.parseInt(presentList.get(i).getPromotionProductId());
                }

                int indexForNew = 0;
                for (Promotion promotion : newPresentList) {
                    int stockId = 0;
                    if(promotion.getPromotionProductId() != null && !promotion.getPromotionProductId().equals("")) {
                        stockId = Integer.parseInt(promotion.getPromotionProductId());
                    }

                    if(stockId != stockId1 && (indexForNew+1) == newPresentList.size()) {
                        Promotion promotion1 = new Promotion();
                        promotion1.setPromotionQty(0);
                        promotion1.setCurrencyId(presentList.get(i).getCurrencyId());
                        promotion1.setPrice(presentList.get(i).getPrice());
                        promotion1.setPromotionPlanId(presentList.get(i).getPromotionPlanId());
                        promotion1.setPromotionPrice(presentList.get(i).getPromotionPrice());
                        promotion1.setPromotionProductId(presentList.get(i).getPromotionProductId());
                        promotion1.setPromotionProductName(presentList.get(i).getPromotionProductName());

                        newPresentList.add(promotion1);
                    }
                    indexForNew++;
                }

                if(newPresentList.size() == 0) {
                    Promotion promotion1 = new Promotion();
                    promotion1.setPromotionQty(0);
                    promotion1.setCurrencyId(presentList.get(i).getCurrencyId());
                    promotion1.setPrice(presentList.get(i).getPrice());
                    promotion1.setPromotionPlanId(presentList.get(i).getPromotionPlanId());
                    promotion1.setPromotionPrice(presentList.get(i).getPromotionPrice());
                    promotion1.setPromotionProductId(presentList.get(i).getPromotionProductId());
                    promotion1.setPromotionProductName(presentList.get(i).getPromotionProductName());

                    newPresentList.add(promotion1);
                }
            }

            List<Promotion> tempPresentList = new ArrayList<>();
            tempPresentList.addAll(presentList);

            for(Promotion promotion : tempPresentList) {
                int stockId = 0;
                if(promotion.getPromotionProductId() != null && !promotion.getPromotionProductId().equals("")) {
                    stockId = Integer.parseInt(promotion.getPromotionProductId());
                }

                for(int kk = 0; kk < newPresentList.size() ; kk++) {
                    int stockId1 = 0;
                    if(newPresentList.get(kk).getPromotionProductId() != null && !newPresentList.get(kk).getPromotionProductId().equals("")) {
                        stockId1 = Integer.parseInt(newPresentList.get(kk).getPromotionProductId());
                    }

                    if(stockId == stockId1) {
                        int promotionQty = promotion.getPromotionQty();
                        newPresentList.get(kk).setPromotionQty(promotionQty + newPresentList.get(kk).getPromotionQty());
                        int index = presentList.indexOf(promotion);
                        presentList.remove(index);
                    }
                }
            }

            for(int i = 0; i < soldProductList.size(); i++ ) {
                int soldProductStockId = soldProductList.get(i).getProduct().getStockId();

                newSoldProductList.add(soldProductList.get(i));
                if (newPresentList != null && newPresentList.size() > 0) {
                    for(int j = 0; j < newPresentList.size(); j++) {
                        int promoProductId =  Integer.parseInt(newPresentList.get(j).getPromotionProductId());

                        if(soldProductStockId == promoProductId) {
                            SoldProduct promoProduct = new SoldProduct(new Product(), false);
                            promoProduct.getProduct().setStockId(promoProductId);
                            promoProduct.setQuantity(newPresentList.get(j).getPromotionQty());
                            promoProduct.getProduct().setPurchasePrice(0.0);
                            promoProduct.getProduct().setPrice(0.0);
                            promoProduct.getProduct().setName(newPresentList.get(j).getPromotionProductName());
                            promoProduct.setPromotionPrice(0.0);
                            newSoldProductList.add(promoProduct);
                            positionList.add(j);
                            break;
                        }
                    }
                }
            }

            for(int i = positionList.size(); i < 0; i--) {
                int pos = positionList.get(i-1);
                newPresentList.remove(pos);
            }

        return newSoldProductList;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("RESUME", "");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("PAUSE", "");
    }
}
