package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DELL on 8/8/2017.
 */

public class PreOrderHistoryForApi {

    /**
     * Id
     */
    @SerializedName("Id")
    @Expose
    private String id;

    /**
     * InvoiceNo
     */
    @SerializedName("InvoiceNo")
    @Expose
    private String invoiceNo;

    /**
     * InvoiceDate
     */
    @SerializedName("InvoiceDate")
    @Expose
    private String invoiceDate;

    /**
     * CustomerId
     */
    @SerializedName("CustomerId")
    @Expose
    private String customerId;

    /**
     * SaleManId
     */
    @SerializedName("SaleManId")
    @Expose
    private String saleManId;

    /**
     * LocationId
     */
    @SerializedName("LocationId")
    @Expose
    private String locationId;

    /**
     * Amount
     */
    @SerializedName("Amount")
    @Expose
    private String amount;

    /**
     * PaidAmount
     */
    @SerializedName("PaidAmount")
    @Expose
    private String paidAmount;

    /**
     * DeliveryDate
     */
    @SerializedName("DeliveryDate")
    @Expose
    private String deliveryDate;

    /**
     * Discount
     */
    @SerializedName("Discount")
    @Expose
    private String discount;

    /**
     * DiscountPer
     */
    @SerializedName("DiscountPer")
    @Expose
    private String discountPer;

    /**
     * VolumeDiscount
     */
    @SerializedName("VolumeDiscount")
    @Expose
    private String volumeDiscount;

    /**
     * VolumeDiscountPer
     */
    @SerializedName("VolumeDiscountPer")
    @Expose
    private String volumeDiscountPer;

    /**
     * TaxAmount
     */
    @SerializedName("TaxAmount")
    @Expose
    private String taxAmount;

    /**
     * CardCodeId
     */
    @SerializedName("CardCodeId")
    @Expose
    private String cardCodeId;

    /**
     * CardNo
     */
    @SerializedName("CardNo")
    @Expose
    private String cardNo;

    /**
     * Remark
     */
    @SerializedName("Remark")
    @Expose
    private String remark;

    /**
     * SaleOrderItem
     */
    @SerializedName("SaleOrderItem")
    @Expose
    private List<PreOrderDetailHistoryForApi> itemHistoryList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(String saleManId) {
        this.saleManId = saleManId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(String discountPer) {
        this.discountPer = discountPer;
    }

    public String getVolumeDiscount() {
        return volumeDiscount;
    }

    public void setVolumeDiscount(String volumeDiscount) {
        this.volumeDiscount = volumeDiscount;
    }

    public String getVolumeDiscountPer() {
        return volumeDiscountPer;
    }

    public void setVolumeDiscountPer(String volumeDiscountPer) {
        this.volumeDiscountPer = volumeDiscountPer;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getCardCodeId() {
        return cardCodeId;
    }

    public void setCardCodeId(String cardCodeId) {
        this.cardCodeId = cardCodeId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<PreOrderDetailHistoryForApi> getItemHistoryList() {
        return itemHistoryList;
    }

    public void setItemHistoryList(List<PreOrderDetailHistoryForApi> itemHistoryList) {
        this.itemHistoryList = itemHistoryList;
    }
}
