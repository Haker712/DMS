package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by haker on 2/9/17.
 */

public class ProductForApi {
    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("Product_id")
    @Expose
    private String productId;
    @SerializedName("Product_name")
    @Expose
    private String productName;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("group_id")
    @Expose
    private String groupId;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("um_id")
    @Expose
    private String umId;
    @SerializedName("product_type_id")
    @Expose
    private String productTypeId;
    @SerializedName("purchase_price")
    @Expose
    private String purchasePrice;
    @SerializedName("selling_price")
    @Expose
    private String sellingPrice;
    @SerializedName("total_qty")
    @Expose
    private int total_Qty;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getUmId() {
        return umId;
    }

    public void setUmId(String umId) {
        this.umId = umId;
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getTotal_Qty() {
        return total_Qty;
    }

    public void setTotal_Qty(int total_Qty) {
        this.total_Qty = total_Qty;
    }
}
