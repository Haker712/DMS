package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phonelin on 2/16/17.
 */

public class CustomerData {

    public List<CustomerForApi> getCustomerForApiList() {
        return customerForApiList;
    }

    public void setCustomerForApiList(List<CustomerForApi> customerForApiList) {
        this.customerForApiList = customerForApiList;
    }

    @SerializedName("Customer")
    @Expose
    private List<CustomerForApi> customerForApiList = null;

}
