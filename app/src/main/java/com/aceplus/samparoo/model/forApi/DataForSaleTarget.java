package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 4/4/17.
 *
 * DataForSaleTarget
 */

public class DataForSaleTarget {

    /**
     * saleTargetForCustomerList
     */
    @SerializedName("SaleTargetForCustomer")
    @Expose
    List<SaleTargetForCustomer> saleTargetForCustomerList;

    /**
     * saleTargetForSaleManList
     */
    @SerializedName("SaleTargetForSaleMan")
    @Expose
    List<SaleTargetForSaleMan> saleTargetForSaleManList;

    /**
     * Getter method for saleTargetForCustomerList
     *
     * @return saleTargetForCustomerList
     */
    public List<SaleTargetForCustomer> getSaleTargetForCustomerList() {
        return saleTargetForCustomerList;
    }

    /**
     * Setter method for saleTargetForCustomerList
     *
     * @param saleTargetForCustomerList
     */
    public void setSaleTargetForCustomerList(List<SaleTargetForCustomer> saleTargetForCustomerList) {
        this.saleTargetForCustomerList = saleTargetForCustomerList;
    }

    /**
     * Getter method for saleTargetForSaleManList
     *
     * @return saleTargetForSaleManList
     */
    public List<SaleTargetForSaleMan> getSaleTargetForSaleManList() {
        return saleTargetForSaleManList;
    }

    /**
     * Setter method for saleTargetForSaleManList
     *
     * @param saleTargetForSaleManList saleTargetForSaleManList
     */
    public void setSaleTargetForSaleManList(List<SaleTargetForSaleMan> saleTargetForSaleManList) {
        this.saleTargetForSaleManList = saleTargetForSaleManList;
    }
}
