package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 3/17/17.
 *
 * CashReceiveApi
 */

public class CashReceiveApi {

    /**
     * receiveNo
     */
    @SerializedName("ReceiveNo")
    @Expose
    String receiveNo;

    /**
     * receiveDate
     */
    @SerializedName("ReceiveDate")
    @Expose
    String receiveDate;

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
     * currencyId
     */
    @SerializedName("CurrencyId")
    @Expose
    String currencyId;

    /**
     * status
     */
    @SerializedName("Status")
    @Expose
    String status;

    /**
     * locationId
     */
    @SerializedName("LocationId")
    @Expose
    String locationId;

    /**
     * paymentType
     */
    @SerializedName("PaymentType")
    @Expose
    String paymentType;

    /**
     * cashReceiveType
     */
    @SerializedName("CashReceiveType")
    @Expose
    String cashReceiveType;

    /**
     * saleId
     */
    @SerializedName("SaleId")
    @Expose
    String saleId;

    /**
     * saleManId
     */
    @SerializedName("SaleManId")
    @Expose
    String saleManId;

    /**
     * cashReceiveItem
     */
    @SerializedName("TCashReceiveItem")
    @Expose
    List<CashReceiveItemApi> cashReceiveItem;

    /**
     * Getter method for receiveNo
     *
     * @return receiveNo
     */
    public String getReceiveNo() {
        return receiveNo;
    }

    /**
     * Setter method for receiveNo
     *
     * @param receiveNo receiveNo
     */
    public void setReceiveNo(String receiveNo) {
        this.receiveNo = receiveNo;
    }

    /**
     * Getter method for receiveDate
     *
     * @return receiveDate
     */
    public String getReceiveDate() {
        return receiveDate;
    }

    /**
     * Setter method for receiveDate
     *
     * @param receiveDate receiveDate
     */
    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
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
     * Getter method for currencyId
     *
     * @return currencyId
     */
    public String getCurrencyId() {
        return currencyId;
    }

    /**
     * Setter method for currencyId
     *
     * @param currencyId currencyId
     */
    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    /**
     * Getter method for status
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Setter method for status
     *
     * @param status status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter method for locationId
     *
     * @return locationId
     */
    public String getLocationId() {
        return locationId;
    }

    /**
     * Setter method for locationId
     *
     * @param locationId locationId
     */
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    /**
     * Getter method for paymentType
     *
     * @return paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * Setter method for paymentType
     *
     * @param paymentType paymentType
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * Getter method for cashReceiveType
     *
     * @return cashReceiveType
     */
    public String getCashReceiveType() {
        return cashReceiveType;
    }

    /**
     * Setter method for cashReceiveType
     *
     * @param cashReceiveType cashReceiveType
     */
    public void setCashReceiveType(String cashReceiveType) {
        this.cashReceiveType = cashReceiveType;
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
     * @param saleId saleId
     */
    public void setSaleId(String saleId) {
        this.saleId = saleId;
    }

    /**
     * Getter method for cashReceiveItem
     *
     * @return cashReceiveItem
     */
    public List<CashReceiveItemApi> getCashReceiveItem() {
        return cashReceiveItem;
    }

    /**
     * Setter method for cashReceiveItem
     *
     * @param cashReceiveItem cashReceiveItem
     */
    public void setCashReceiveItem(List<CashReceiveItemApi> cashReceiveItem) {
        this.cashReceiveItem = cashReceiveItem;
    }

    public String getSaleManId() {
        return saleManId;
    }

    public void setSaleManId(String saleManId) {
        this.saleManId = saleManId;
    }
}
