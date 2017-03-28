package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 3/24/17.
 *
 * CustomerVisitRequestData
 */

class CustomerVisitRequestData {

    @SerializedName("CustomerVisit")
    @Expose
    List<SaleVisitRecord> saleVisitRecordList;

    /**
     * Getter method for saleVisitRecordList
     *
     * @return saleVisitRecordList
     */
    public List<SaleVisitRecord> getSaleVisitRecordList() {
        return saleVisitRecordList;
    }

    /**
     * Setter method for saleVisitRecordList
     *
     * @param saleVisitRecordList saleVisitRecordList
     */
    public void setSaleVisitRecordList(List<SaleVisitRecord> saleVisitRecordList) {
        this.saleVisitRecordList = saleVisitRecordList;
    }
}
