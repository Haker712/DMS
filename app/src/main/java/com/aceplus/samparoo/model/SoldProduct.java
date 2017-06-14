package com.aceplus.samparoo.model;

import android.app.Activity;

import java.io.Serializable;
import java.util.ArrayList;

public class SoldProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    private Product product;
    private int quantity;
    private double discount;

    private boolean isForPackage;
    private double extraDiscount;
    private double discountPercent;
    private double discountAmount;
    private ArrayList<String> serialList;

    private int orderedQuantity;
    private int size_in_store_share;
    private String remark;

    double promotionPrice;

    double totalAmt;

    boolean focStatus;

    String promotionPlanId;

    Integer exclude;

    public SoldProduct(){}

    public SoldProduct(Product product, Boolean isForPackage) {

        this.product = product;
        quantity = 0;
        discount = 0;

        this.isForPackage = isForPackage;
        extraDiscount = 0;

        orderedQuantity = 0;
    }

    public Product getProduct() {

        return product;
    }

    public int getQuantity() {

        return quantity;
    }

    public boolean setQuantity(int quantity) {

        if (quantity > 0) {

            product.setSoldQty(-this.quantity);
        }

        product.setSoldQty(quantity);
//		if (!product.setSoldQty(quantity)) {
//
//			return false;
//		}

        this.quantity = quantity;

        return true;
    }

    public int getSize_in_store_share() {
        return size_in_store_share;
    }

    public void setSize_in_store_share(int size_in_store_share) {
        this.size_in_store_share = size_in_store_share;
    }

    public void setDiscount(double discount) {

        if (discount >= 0 && discount <= 100) {

            this.discount = discount;
        }
    }

    public double getDiscount(Activity context) {

        if (discount != 0) {

            return discount;
        }

//        SQLiteDatabase db = new Database(context).getDataBase();
//
//        String sql = null;
//
//        if (product.getDiscountType().equalsIgnoreCase("i")) {
//
//            sql = "SELECT DISCOUNT_PERCENT FROM ITEM_DISCOUNT WHERE STOCK_NO = '" + product.getId() + "' AND START_DISCOUNT_QTY <= " + quantity + " AND END_DISCOUNT_QTY >= " + quantity;
//        }
//
//        if (sql != null) {
//
//            Cursor cursor = db.rawQuery(sql, null);
//            System.out.println(cursor.getCount());
//            if (cursor.getCount() == 1) {
//
//                cursor.moveToNext();
//                return cursor.getDouble(cursor.getColumnIndex("DISCOUNT_PERCENT"));
//            }
//        }

        return 0;
    }

    public boolean isForPackage() {

        return isForPackage;
    }

    public void setForPackage(boolean isForPackage) {

        this.isForPackage = isForPackage;
    }

    public void setSerialList(ArrayList<String> serialList) {

        this.serialList = serialList;
    }

    public ArrayList<String> getSerialList() {

        if (serialList == null) {

            serialList = new ArrayList<String>();
        }

        return serialList;
    }

    public void setExtraDiscount(double extraDiscount) {

        if (extraDiscount >= 0) {

            this.extraDiscount = extraDiscount;
        }
    }

    public double getExtraDiscount() {

        return extraDiscount;
    }

    public double getTotalAmount() {
        if(getPromotionPrice() == 0) {
            return product.getPrice() * quantity;
        }
        else {
            return getPromotionPrice() * quantity;
        }

    }

    public double getDiscountAmount(Activity context) {

        return getTotalAmount() * getDiscount(context) / 100;
    }

    public double getExtraDiscountAmount() {

        return getTotalAmount() * extraDiscount / 100;
    }

    public double getNetAmount(Activity context) {

        return getTotalAmount() - getDiscountAmount(context) - getExtraDiscountAmount();
    }

    public void setOrderedQuantity(int orderedQuantity) {

        this.orderedQuantity = orderedQuantity;
    }

    public int getOrderedQuantity() {

        return this.orderedQuantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public double getDiscount() {
        return discount;
    }

    public boolean isFocStatus() {
        return focStatus;
    }

    public void setFocStatus(boolean focStatus) {
        this.focStatus = focStatus;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getPromotionPlanId() {
        return promotionPlanId;
    }

    public void setPromotionPlanId(String promotionPlanId) {
        this.promotionPlanId = promotionPlanId;
    }

    public Integer getExclude() {
        return exclude;
    }

    public void setExclude(Integer exclude) {
        this.exclude = exclude;
    }
}
