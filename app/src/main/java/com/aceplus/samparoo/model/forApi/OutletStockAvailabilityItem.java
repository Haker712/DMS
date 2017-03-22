package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by phonelin on 3/21/17.
 */

public class OutletStockAvailabilityItem {

    @SerializedName("OutletStockAvailabilityNo")
    @Expose
    private String outletStockAvailabilityNo;
    @SerializedName("StockId")
    @Expose
    private Integer stockId;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;

    public String getOutletStockAvailabilityNo() {
        return outletStockAvailabilityNo;
    }

    public void setOutletStockAvailabilityNo(String outletStockAvailabilityNo) {
        this.outletStockAvailabilityNo = outletStockAvailabilityNo;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


}
