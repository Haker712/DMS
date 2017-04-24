package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 4/11/17.
 *
 * DataForSaleHistory
 */

public class DataForSaleHistory {

    /**
     * invoice list
     */
    @SerializedName("invoice")
    @Expose
    private List<SaleHistory> saleHistoryList;

    /**
     * Getter method for sale history list
     *
     * @return saleHistoryList
     */
    public List<SaleHistory> getSaleHistoryList() {
        return saleHistoryList;
    }

    /**
     * Setter method for sale history list
     *
     * @param saleHistoryList
     */
    public void setSaleHistoryList(List<SaleHistory> saleHistoryList) {
        this.saleHistoryList = saleHistoryList;
    }
}
