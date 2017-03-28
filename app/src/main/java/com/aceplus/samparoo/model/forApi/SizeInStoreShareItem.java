package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by phonelin on 3/21/17.
 */

public class SizeInStoreShareItem {
    @SerializedName("SizeInStoreShareNo")
    @Expose
    private String sizeInStoreShareNo;
    @SerializedName("StockId")
    @Expose
    private Integer stockId;
    @SerializedName("SizeInStoreSharePercent")
    @Expose
    private Integer sizeInStoreSharePercent;

    public String getSizeInStoreShareNo() {
        return sizeInStoreShareNo;
    }

    public void setSizeInStoreShareNo(String sizeInStoreShareNo) {
        this.sizeInStoreShareNo = sizeInStoreShareNo;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Integer getSizeInStoreSharePercent() {
        return sizeInStoreSharePercent;
    }

    public void setSizeInStoreSharePercent(Integer sizeInStoreSharePercent) {
        this.sizeInStoreSharePercent = sizeInStoreSharePercent;
    }


}
