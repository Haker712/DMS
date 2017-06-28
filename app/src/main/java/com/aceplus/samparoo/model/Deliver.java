package com.aceplus.samparoo.model;

import com.aceplus.samparoo.model.forApi.DeliveryPresentForApi;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yma on 2/22/17.
 *
 * Deliver
 */

public class Deliver implements Serializable{

    /**
     * deliverId
     */
    private int deliverId;

    /**
     * customerId
     */
    private String customerId;

    /**
     * customerName
     */
    private String customerName;

    /**
     * invoiceNo
     */
    private String invoiceNo;

    /**
     * amount
     */
    private double amount;

    /**
     * paidAmount
     */
    private double paidAmount;

    /**
     * deliverItemList
     */
    private List<DeliverItem> deliverItemList;

    /**
     * deliveryPresentForApiList
     */
    private List<DeliveryPresentForApi> deliveryPresentForApiList;

    /**
     * Getter method for deliverId
     *
     * @return deliverId
     */
    public int getDeliverId() {
        return deliverId;
    }

    /**
     * Setter method for deliverId
     *
     * @param deliverId deliver id
     */
    public void setDeliverId(int deliverId) {
        this.deliverId = deliverId;
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
     * Getter method for customerName
     *
     * @return customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Setter method for customerName
     *
     * @param customerName customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
     * Getter method for amount
     *
     * @return amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Setter method for amount
     *
     * @param amount amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Getter method for paidAmount
     *
     * @return paidAmount
     */
    public double getPaidAmount() {
        return paidAmount;
    }

    /**
     * Setter method for paidAmount
     *
     * @param paidAmount paidAmount
     */
    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    /**
     * Getter method for deliverItemList
     *
     * @return deliverItemList
     */
    public List<DeliverItem> getDeliverItemList() {
        return deliverItemList;
    }

    /**
     * Setter method for deliverItemList
     *
     * @param deliverItemList deliver item list
     */
    public void setDeliverItemList(List<DeliverItem> deliverItemList) {
        this.deliverItemList = deliverItemList;
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
