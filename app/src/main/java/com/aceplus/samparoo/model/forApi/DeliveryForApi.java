package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 2/22/17.
 *
 * DeliveryForApi
 */

public class DeliveryForApi {

    /**
     * id
     */
    @SerializedName("Id")
    @Expose
    String id;

    /**
     * invoiceNo
     */
    @SerializedName("InvoiceNo")
    @Expose
    String invoiceNo;

    /**
     * invoiceDate
     */
    @SerializedName("InvoiceDate")
    @Expose
    String invoiceDate;

    /**
     * customerId
     */
    @SerializedName("CustomerId")
    @Expose
    String customerId;

    /**
     * amount
     */
    @SerializedName("Amount")
    @Expose
    String amount;

    /**
     * paidAmount
     */
    @SerializedName("PaidAmount")
    @Expose
    String paidAmount;

    /**
     * expDate
     */
    @SerializedName("expDate")
    @Expose
    String expDate;

    /**
     * saleManId
     */
    @SerializedName("SaleManId")
    @Expose
    String saleManId;

    /**
     * deliveryItemForApiList
     */
    @SerializedName("SaleOrderItem")
    @Expose
    List<DeliveryItemForApi> deliveryItemForApiList;

    /**
     * deliveryItemForApiList
     */
    @SerializedName("SaleOrderPresent")
    @Expose
    List<DeliveryPresentForApi> deliveryPresentForApiList;

    /**
     * Getter method for id
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter method for id
     *
     * @param id sale order id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for invoiceNo
     *
     * @return invoiceNo
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * Setter method for invoiceNo
     *
     * @param invoiceNo invoiceNo
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * Getter method for invoiceDate
     *
     * @return invoiceDate
     */
    public String getInvoiceDate() {
        return invoiceDate;
    }

    /**
     * Setter method for invoiceDate
     *
     * @param invoiceDate invoiceDate
     */
    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * Getter method for customerId
     *
     * @return customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Setter method for customerId
     *
     * @param customerId customerId
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter method for amount
     *
     * @return amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Setter method for amount
     *
     * @param amount amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * Getter method for paidAmount
     *
     * @return paidAmount
     */
    public String getPaidAmount() {
        return paidAmount;
    }

    /**
     * Setter method for paidAmount
     *
     * @param paidAmount paidAmount
     */
    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    /**
     * Getter method for expDate
     *
     * @return expDate
     */
    public String getExpDate() {
        return expDate;
    }

    /**
     * Setter method for expDate
     *
     * @param expDate expDate
     */
    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    /**
     * Getter method for saleManId
     *
     * @return saleManId
     */
    public String getSaleManId() {
        return saleManId;
    }

    /**
     * Setter method for saleManId
     *
     * @param saleManId saleManId
     */
    public void setSaleManId(String saleManId) {
        this.saleManId = saleManId;
    }

    /**
     * Getter method for deliveryItemForApiList
     *
     * @return deliveryItemForApiList
     */
    public List<DeliveryItemForApi> getDeliveryItemForApiList() {
        return deliveryItemForApiList;
    }

    /**
     * Setter method for deliveryItemForApiList
     *
     * @param deliveryItemForApiList deliveryItemForApiList
     */
    public void setDeliveryItemForApiList(List<DeliveryItemForApi> deliveryItemForApiList) {
        this.deliveryItemForApiList = deliveryItemForApiList;
    }

    /**
     * Getter method for deliveryPresentForApiList
     *
     * @return deliveryPresentForApiList
     */
    public List<DeliveryPresentForApi> getDeliveryPresentForApiList() {
        return deliveryPresentForApiList;
    }

    /**
     * Setter method for deliveryPresentForApiList
     *
     * @param deliveryPresentForApiList
     */
    public void setDeliveryPresentForApiList(List<DeliveryPresentForApi> deliveryPresentForApiList) {
        this.deliveryPresentForApiList = deliveryPresentForApiList;
    }
}
