package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aceplus_mobileteam on 6/29/17.
 */

public class IncentiveItemForApi {

    @SerializedName("Id")
    @Expose
    private Integer id;

    @SerializedName("DisplayProgramId")
    @Expose
    private Integer displayProgramId;

    @SerializedName("IncentiveId")
    @Expose
    private Integer incentiveId;

    @SerializedName("StockId")
    @Expose
    private Integer stockId;

    @SerializedName("Quantity")
    @Expose
    private Integer quantity;

    @SerializedName("Price")
    @Expose
    private Double price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDisplayProgramId() {
        return displayProgramId;
    }

    public void setDisplayProgramId(Integer displayProgramId) {
        this.displayProgramId = displayProgramId;
    }

    public Integer getIncentiveId() {
        return incentiveId;
    }

    public void setIncentiveId(Integer incentiveId) {
        this.incentiveId = incentiveId;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
