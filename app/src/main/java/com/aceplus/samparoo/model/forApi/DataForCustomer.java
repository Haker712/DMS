package com.aceplus.samparoo.model.forApi;

import com.aceplus.samparoo.model.Customer;
import com.aceplus.samparoo.model.forApi.CustomerForApi;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by haker on 2/9/17.
 */
public class DataForCustomer {
    @SerializedName("Customer")
    @Expose
    private List<CustomerForApi> customerList = null;

    public List<CustomerForApi> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<CustomerForApi> customerList) {
        this.customerList = customerList;
    }
}
