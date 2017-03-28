package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phonelin on 3/21/17.
 */

public class SizeInStoreShare {

    @SerializedName("SizeInStoreShareNo")
    @Expose
    private String sizeInStoreShareNo;
    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("CustomerId")
    @Expose
    private Integer customerId;
    @SerializedName("SizeInStoreShareItem")
    @Expose
    private List<SizeInStoreShareItem> sizeInStoreShareItem = null;

    public String getSizeInStoreShareNo() {
        return sizeInStoreShareNo;
    }

    public void setSizeInStoreShareNo(String sizeInStoreShareNo) {
        this.sizeInStoreShareNo = sizeInStoreShareNo;
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

    public List<SizeInStoreShareItem> getSizeInStoreShareItem() {
        return sizeInStoreShareItem;
    }

    public void setSizeInStoreShareItem(List<SizeInStoreShareItem> sizeInStoreShareItem) {
        this.sizeInStoreShareItem = sizeInStoreShareItem;
    }


}
