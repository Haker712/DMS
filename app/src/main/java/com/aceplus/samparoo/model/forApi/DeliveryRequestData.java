package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yma on 3/8/17.
 *
 * DeliveryRequestData
 */

public class DeliveryRequestData {

    /**
     * Delivery
     */
    @SerializedName("Delivery")
    @Expose
    private List<DeliveryApi> deliveryApiList = null;

    /**
     * Getter method for delivery list
     *
     * @return deliveryApiList
     */
    public List<DeliveryApi> getDeliveryApiList() {
        return deliveryApiList;
    }

    /**
     * Setter method for delivery list
     *
     * @param deliveryApiList deliveryApiList
     */
    public void setDeliveryApiList(List<DeliveryApi> deliveryApiList) {
        this.deliveryApiList = deliveryApiList;
    }
}
