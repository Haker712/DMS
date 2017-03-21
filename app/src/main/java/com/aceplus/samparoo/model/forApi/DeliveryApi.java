package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 3/8/17.
 *
 * DeliveryApi
 */

public class DeliveryApi {

    @SerializedName("InvoiceNo")
    @Expose
    String invoiceNo;

    @SerializedName("InvoiceDate")
    @Expose
    String invoiceDate;

    @SerializedName("SaleInvoiceNo")
    @Expose
    String saleId;

    @SerializedName("CustomerId")
    @Expose
    int customerId;

    @SerializedName("SaleManId")
    @Expose
    int saleManId;

    @SerializedName("LocationId")
    @Expose
    int locationId;

    @SerializedName("Remark")
    @Expose
    String remark;

    @SerializedName("DeliveryItem")
    @Expose
    List<DeliveryItemApi> deliveryItemApi;

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
     * @param invoiceNo invoice number
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
     * @param invoiceDate invoice date
     */
    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    /**
     * Getter method for saleId
     *
     * @return saleId
     */
    public String getSaleId() {
        return saleId;
    }

    /**
     * Setter method for saleId
     *
     * @param saleId sale id
     */
    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    /**
     * Getter method for remark
     *
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Setter method for remark
     *
     * @param remark remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * Getter method for deliveryItemApi
     *
     * @return deliveryItemApi
     */
    public List<DeliveryItemApi> getDeliveryItemApi() {
        return deliveryItemApi;
    }

    /**
     * Setter method for deliveryItemApi
     *
     * @param deliveryItemApi list of delivery item
     */
    public void setDeliveryItemApi(List<DeliveryItemApi> deliveryItemApi) {
        this.deliveryItemApi = deliveryItemApi;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(int saleManId) {
        this.saleManId = saleManId;
    }
}
