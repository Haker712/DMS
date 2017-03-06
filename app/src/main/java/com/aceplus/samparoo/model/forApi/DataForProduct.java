package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by haker on 2/9/17.
 */
public class DataForProduct {
    @SerializedName("Product")
    @Expose
    private List<ProductForApi> productList = null;

    public List<ProductForApi> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductForApi> productList) {
        this.productList = productList;
    }
}
