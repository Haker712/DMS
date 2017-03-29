package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yma on 3/23/17.
 */

public class SaleVisitRecord {

    /**
     * id
     */
    int id;

    /**
     * salemanId
     */
    @SerializedName("SaleManId")
    @Expose
    int salemanId;

    /**
     * customerId
     */
    @SerializedName("CustomerId")
    @Expose
    int customerId;

    /**
     * latitude
     */
    @SerializedName("Latitude")
    @Expose
    String latitude;

    /**
     * longitude
     */
    @SerializedName("Longitude")
    @Expose
    String longitude;

    /**
     * visitFlg
     */
    @SerializedName("VisitStatus")
    @Expose
    short visitFlg;

    /**
     * saleFlg
     */
    @SerializedName("SaleStatus")
    @Expose
    short saleFlg;

    /**
     * recorded date
     */
    @SerializedName("Date")
    @Expose
    String recordDate;

    /**
     * Getter method for id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Setter method for id
     *
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter method for salemanId
     *
     * @return salemanId
     */
    public int getSalemanId() {
        return salemanId;
    }

    /**
     * Setter method for salemanId
     *
     * @param salemanId salemanId
     */
    public void setSalemanId(int salemanId) {
        this.salemanId = salemanId;
    }

    /**
     * Getter method for customerId
     *
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Setter method for customerId
     *
     * @param customerId customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter method for latitude
     *
     * @return latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Setter method for latitude
     *
     * @param latitude latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Getter method for longitude
     *
     * @return longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Setter method for longitude
     *
     * @param longitude longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Getter method for visitFlg
     *
     * @return visitFlg
     */
    public short getVisitFlg() {
        return visitFlg;
    }

    /**
     * Setter method for visitFlg
     *
     * @param visitFlg visit flag
     */
    public void setVisitFlg(short visitFlg) {
        this.visitFlg = visitFlg;
    }

    /**
     * Getter method for saleFlg
     *
     * @return saleFlg
     */
    public short getSaleFlg() {
        return saleFlg;
    }

    /**
     * Setter method for sale flag
     *
     * @param saleFlg sale flag
     */
    public void setSaleFlg(short saleFlg) {
        this.saleFlg = saleFlg;
    }

    /**
     * Getter method for recordDate
     *
     * @return recordDate
     */
    public String getRecordDate() {
        return recordDate;
    }

    /**
     * Setter method for recordDate
     *
     * @param recordDate recordDate
     */
    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
}
