package com.aceplus.samparoo.model.forApi;

import com.aceplus.samparoo.model.SaleReturn;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 2/16/17.
 *
 * SaleReturnRequestData
 */

public class SaleReturnRequestData {

    /**
     * TSaleReturn
     */
    @SerializedName("TSaleReturn")
    @Expose
    private List<SaleReturnApi> saleReturnApiList = null;

    /**
     * Getter method for sale return api
     *
     * @return saleReturnApiList
     */
    public List<SaleReturnApi> getData() {
        return saleReturnApiList;
    }

    /**
     * Setter method for sale return api
     * @param saleReturnApiList SaleReturnApi list
     */
    public void setData(List<SaleReturnApi> saleReturnApiList) {
        this.saleReturnApiList = saleReturnApiList;
    }
}
