package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 3/15/17.
 *
 * Holder for credit data
 */

public class DataForCredit {

    @SerializedName("Sale")
    @Expose
    List<CreditForApi> creditForApiList;

    @SerializedName("CustomerBalance")
    @Expose
    List<CustomerBalanceForApi> customerBalanceList;

    public List<CreditForApi> getCreditForApiList() {
        return creditForApiList;
    }

    public void setCreditForApiList(List<CreditForApi> creditForApiList) {
        this.creditForApiList = creditForApiList;
    }

    public List<CustomerBalanceForApi> getCustomerBalanceList() {
        return customerBalanceList;
    }

    public void setCustomerBalanceList(List<CustomerBalanceForApi> customerBalanceList) {
        this.customerBalanceList = customerBalanceList;
    }
}
