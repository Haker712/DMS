package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phonelin on 3/21/17.
 */

public class OutletStockAvailability {

    @SerializedName("OutletStockAvailabilityNo")
    @Expose
    private String outletStockAvailabilityNo;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("OutletStockAvailabilityItem")
    @Expose
    private List<OutletStockAvailabilityItem> outletStockAvailabilityItem = null;

    public String getOutletStockAvailabilityNo() {
        return outletStockAvailabilityNo;
    }

    public void setOutletStockAvailabilityNo(String outletStockAvailabilityNo) {
        this.outletStockAvailabilityNo = outletStockAvailabilityNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<OutletStockAvailabilityItem> getOutletStockAvailabilityItem() {
        return outletStockAvailabilityItem;
    }

    public void setOutletStockAvailabilityItem(List<OutletStockAvailabilityItem> outletStockAvailabilityItem) {
        this.outletStockAvailabilityItem = outletStockAvailabilityItem;
    }



}
