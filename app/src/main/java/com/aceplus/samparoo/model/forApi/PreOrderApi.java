package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 2/15/17.
 *
 * PreOrderApi - retrieve data from api
 */

public class PreOrderApi {

    /**
     * id
     */
    @SerializedName("id")
    @Expose
    private String id;

    /**
     * customer id
     */
    @SerializedName("customer_id")
    @Expose
    private String customerId;

    /**
     * sale man id
     */
    @SerializedName("saleman_id")
    @Expose
    private String saleManId;

    /**
     * device id
     */
    @SerializedName("device_id")
    @Expose
    private String deviceId;

    /**
     * sale order date
     */
    @SerializedName("sale_order_date")
    @Expose
    private String saleOrderDate;

    /**
     * expected delivered date
     */
    @SerializedName("expected_delivered_date")
    @Expose
    private String expectedDeliveredDate;

    /**
     * advanced payment amount
     */
    @SerializedName("advanced_payment_amt")
    @Expose
    private double advancedPaymentAmt;

    /**
     * net amount
     */
    @SerializedName("net_amt")
    @Expose
    private double netAmt;

    @SerializedName("location_id")
    @Expose
    private Integer locationId;
    @SerializedName("discount")
    @Expose
    private Double discount;
    @SerializedName("discount_per")
    @Expose
    private Double discountPer;
    @SerializedName("volume_discount")
    @Expose
    private Double volumeDiscount;
    @SerializedName("volume_discount_per")
    @Expose
    private Double volumeDiscountPer;

    /**
     * pre order detail
     */
    @SerializedName("preorder_detail")
    @Expose
    private List<PreOrderDetailApi> preOrderDetailList;

    /**
     * Getter method of id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method of id
     *
     * @param id id number
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method of customerId
     *
     * @return customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Setter method of customerId
     *
     * @param customerId customer id
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter method of saleManId
     *
     * @return saleManId
     */
    public String getSaleManId() {
        return saleManId;
    }

    /**
     * Setter method of saleManId
     *
     * @param saleManId sale man id
     */
    public void setSaleManId(String saleManId) {
        this.saleManId = saleManId;
    }

    /**
     * Getter method of deviceId
     *
     * @return deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * Setter method of deviceId
     *
     * @param deviceId device id
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * Getter method of saleOrderDate
     *
     * @return saleOrderDate
     */
    public String getSaleOrderDate() {
        return saleOrderDate;
    }

    /**
     * Setter method of saleOrderDate
     *
     * @param saleOrderDate sale order date
     */
    public void setSaleOrderDate(String saleOrderDate) {
        this.saleOrderDate = saleOrderDate;
    }

    /**
     * Getter method of expectedDeliveredDate
     *
     * @return expectedDeliveredDate
     */
    public String getExpectedDeliveredDate() {
        return expectedDeliveredDate;
    }

    /**
     * Setter method of expectedDeliveredDate
     *
     * @param expectedDeliveredDate expected delivered date
     */
    public void setExpectedDeliveredDate(String expectedDeliveredDate) {
        this.expectedDeliveredDate = expectedDeliveredDate;
    }

    /**
     * Getter method of advancedPaymentAmt
     *
     * @return advancedPaymentAmt
     */
    public double getAdvancedPaymentAmt() {
        return advancedPaymentAmt;
    }

    /**
     * Setter method of advancedPaymentAmt
     *
     * @param advancedPaymentAmt advanced payment amount
     */
    public void setAdvancedPaymentAmt(double advancedPaymentAmt) {
        this.advancedPaymentAmt = advancedPaymentAmt;
    }

    /**
     * Getter method of netAmt
     *
     * @return netAmt
     */
    public double getNetAmt() {
        return netAmt;
    }

    /**
     * Setter method of netAmt
     *
     * @param netAmt net amount
     */
    public void setNetAmt(double netAmt) {
        this.netAmt = netAmt;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(Double discountPer) {
        this.discountPer = discountPer;
    }

    public Double getVolumeDiscount() {
        return volumeDiscount;
    }

    public void setVolumeDiscount(Double volumeDiscount) {
        this.volumeDiscount = volumeDiscount;
    }

    public Double getVolumeDiscountPer() {
        return volumeDiscountPer;
    }

    public void setVolumeDiscountPer(Double volumeDiscountPer) {
        this.volumeDiscountPer = volumeDiscountPer;
    }

    /**
     * Getter method of preOrderDetailList
     *
     * @return preOrderDetailList
     */
    public List<PreOrderDetailApi> getPreOrderDetailList() {
        return preOrderDetailList;
    }

    /**
     * Setter method of pre order detail
     *
     * @param preOrderDetailList pre order detail list
     */
    public void setPreOrderDetailList(List<PreOrderDetailApi> preOrderDetailList) {
        this.preOrderDetailList = preOrderDetailList;
    }
}
