package com.aceplus.samparoo.model;

/**
 * Created by aceplus_mobileteam on 5/11/17.
 */

public class VolumeDiscountItemForReport {

    int volumeDiscountId;

    String fromSaleAmt;

    String toSaleAmt;

    String itemDiscountPercent;

    String itemDiscountAmount;

    String itemDiscountPrice;

    public int getVolumeDiscountId() {
        return volumeDiscountId;
    }

    public void setVolumeDiscountId(int volumeDiscountId) {
        this.volumeDiscountId = volumeDiscountId;
    }

    public String getFromSaleAmt() {
        return fromSaleAmt;
    }

    public void setFromSaleAmt(String fromSaleAmt) {
        this.fromSaleAmt = fromSaleAmt;
    }

    public String getToSaleAmt() {
        return toSaleAmt;
    }

    public void setToSaleAmt(String toSaleAmt) {
        this.toSaleAmt = toSaleAmt;
    }

    public String getItemDiscountPercent() {
        return itemDiscountPercent;
    }

    public void setItemDiscountPercent(String itemDiscountPercent) {
        this.itemDiscountPercent = itemDiscountPercent;
    }

    public String getItemDiscountAmount() {
        return itemDiscountAmount;
    }

    public void setItemDiscountAmount(String itemDiscountAmount) {
        this.itemDiscountAmount = itemDiscountAmount;
    }

    public String getItemDiscountPrice() {
        return itemDiscountPrice;
    }

    public void setItemDiscountPrice(String itemDiscountPrice) {
        this.itemDiscountPrice = itemDiscountPrice;
    }
}
