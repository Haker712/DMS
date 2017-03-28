package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yma on 3/17/17.
 *
 * CashReceiveItemApi
 */

public class CashReceiveItemApi {

    /**
     * receiveNo
     */
    @SerializedName("ReceiveNo")
    @Expose
    String receiveNo;

    /**
     * saleId
     */
    @SerializedName("SaleId")
    @Expose
    int saleId;

    /**
     * Getter method for receiveNo
     *
     * @return receiveNo
     */
    public String getReceiveNo() {
        return receiveNo;
    }

    /**
     * Getter method for receiveNo
     *
     * @param receiveNo receive number
     */
    public void setReceiveNo(String receiveNo) {
        this.receiveNo = receiveNo;
    }

    /**
     * Getter method for saleId
     *
     * @return saleId
     */
    public int getSaleId() {
        return saleId;
    }

    /**
     * Setter method for sale id
     *
     * @param saleId sale id
     */
    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }
}
