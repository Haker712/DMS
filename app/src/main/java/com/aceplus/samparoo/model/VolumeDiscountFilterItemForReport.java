package com.aceplus.samparoo.model;

/**
 * Created by yma on 5/12/17.
 */

public class VolumeDiscountFilterItemForReport {

    int volumeDiscountId;

    int categoryId;

    String categoryName;

    int groupCodeId;

    String groupCodeName;

    String fromSaleAmount;

    String toSaleAmount;

    String filterDiscountPercent;

    String filterDiscountAmount;

    String filterDiscountPrice;

    public int getVolumeDiscountId() {
        return volumeDiscountId;
    }

    public void setVolumeDiscountId(int volumeDiscountId) {
        this.volumeDiscountId = volumeDiscountId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getGroupCodeId() {
        return groupCodeId;
    }

    public void setGroupCodeId(int groupCodeId) {
        this.groupCodeId = groupCodeId;
    }

    public String getGroupCodeName() {
        return groupCodeName;
    }

    public void setGroupCodeName(String groupCodeName) {
        this.groupCodeName = groupCodeName;
    }

    public String getFromSaleAmount() {
        return fromSaleAmount;
    }

    public void setFromSaleAmount(String fromSaleAmount) {
        this.fromSaleAmount = fromSaleAmount;
    }

    public String getToSaleAmount() {
        return toSaleAmount;
    }

    public void setToSaleAmount(String toSaleAmount) {
        this.toSaleAmount = toSaleAmount;
    }

    public String getFilterDiscountPercent() {
        return filterDiscountPercent;
    }

    public void setFilterDiscountPercent(String filterDiscountPercent) {
        this.filterDiscountPercent = filterDiscountPercent;
    }

    public String getFilterDiscountAmount() {
        return filterDiscountAmount;
    }

    public void setFilterDiscountAmount(String filterDiscountAmount) {
        this.filterDiscountAmount = filterDiscountAmount;
    }

    public String getFilterDiscountPrice() {
        return filterDiscountPrice;
    }

    public void setFilterDiscountPrice(String filterDiscountPrice) {
        this.filterDiscountPrice = filterDiscountPrice;
    }
}
