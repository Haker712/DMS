package com.aceplus.samparoo.model;

/**
 * Created by yma on 2/21/17.
 *
 * PosmByCustomer
 */

public class PosmByCustomer {

    /**
     * invoiceNo
     */
    private String invoiceNo;

    /**
     * invoiceDate
     */
    private String invoiceDate;

    /**
     * customerId
     */
    private int customerId;

    /**
     * stockId
     */
    private int stockId;

    /**
     * shopTypeId
     */
    private int shopTypeId;

    /**
     * saleManId
     */
    private String saleManId;

    /**
     * quantity
     */
    private int quantity;

    /**
     * price
     */
    private double price;

    /**
     * Getter method for invoiceNo.
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
     * Getter method for invoiceDate.
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
     * Getter method for customerId.
     *
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Setter method for customerId
     *
     * @param customerId customer id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter method for stockId.
     *
     * @return stockId
     */
    public int getStockId() {
        return stockId;
    }

    /**
     * Setter method for stockId
     *
     * @param stockId stock id number
     */
    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    /**
     * Getter method for shopTypeId.
     *
     * @return shopTypeId
     */
    public int getShopTypeId() {
        return shopTypeId;
    }

    /**
     * Setter method for shopTypeId
     *
     * @param shopTypeId shop type id number
     */
    public void setShopTypeId(int shopTypeId) {
        this.shopTypeId = shopTypeId;
    }

    /**
     * Getter method for saleManId.
     *
     * @return saleManId
     */
    public String getSaleManId() {
        return saleManId;
    }

    /**
     * Setter method for saleManId
     *
     * @param saleManId sale man id number
     */
    public void setSaleManId(String saleManId) {
        this.saleManId = saleManId;
    }

    /**
     * Getter method for quantity.
     *
     * @return quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Setter method for quantity
     *
     * @param quantity quantity of product
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Getter method for price.
     *
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter method for price
     *
     * @param price product price
     */
    public void setPrice(double price) {
        this.price = price;
    }
}
