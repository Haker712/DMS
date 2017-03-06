package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 2/21/17.
 *
 * PosmByCustomerRequestData
 */

public class PosmByCustomerRequestData {

    /**
     * posmByCustomerApiList
     */
    @SerializedName("POSMByCustomer")
    @Expose
    private List<PosmByCustomerApi> posmByCustomerApiList = null;

    /**
     * Getter method for PosmByCustomerApiList
     *
     * @return posmByCustomerApiList
     */
    public List<PosmByCustomerApi> getPosmByCustomerApiList() {
        return posmByCustomerApiList;
    }

    /**
     * Setter method for PosmByCustomerApiList
     *
     * @param posmByCustomerApiList PosmByCustomerApi list
     */
    public void setPosmByCustomerApiList(List<PosmByCustomerApi> posmByCustomerApiList) {
        this.posmByCustomerApiList = posmByCustomerApiList;
    }
}
