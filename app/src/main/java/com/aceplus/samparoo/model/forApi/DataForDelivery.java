package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 2/22/17.
 *
 * DataForDelivery
 */

public class DataForDelivery {

    /**
     * deliveryForApiList
     */
    @SerializedName("SaleOrder")
    @Expose
    List<DeliveryForApi> deliveryForApiList;

    /**
     * Getter method for deliveryForApiList
     *
     * @return deliveryForApiList
     */
    public List<DeliveryForApi> getDeliveryForApiList() {
        return deliveryForApiList;
    }

    /**
     * Setter method for deliveryForApiList
     * @param deliveryForApiList sale order data list
     */
    public void setDeliveryForApiList(List<DeliveryForApi> deliveryForApiList) {
        this.deliveryForApiList = deliveryForApiList;
    }
}
