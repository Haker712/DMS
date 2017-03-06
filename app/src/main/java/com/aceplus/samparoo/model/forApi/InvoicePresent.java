package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by phonelin on 2/13/17.
 */
public class InvoicePresent {
    @SerializedName("tsale_id")
    @Expose
    private String tsaleId;
    @SerializedName("stock_id")
    @Expose
    private Integer stockId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("pc_address")
    @Expose
    private String pcAddress;
    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("price")
    @Expose
    private Double price;

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getPcAddress() {
        return pcAddress;
    }

    public void setPcAddress(String pcAddress) {
        this.pcAddress = pcAddress;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public String getTsaleId() {
        return tsaleId;
    }

    public void setTsaleId(String tsaleId) {
        this.tsaleId = tsaleId;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


}
