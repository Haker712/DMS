package com.aceplus.samparoo.model.forApi;

import com.aceplus.samparoo.model.Category;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by phonelin on 2/13/17.
 */

public class GeneralData {


    @SerializedName("District")
    @Expose
    private List<District> district = null;
    @SerializedName("Township")
    @Expose
    private List<Township> township = null;
    @SerializedName("Category")
    @Expose
    private List<Category> category = null;
    @SerializedName("GroupCode")
    @Expose
    private List<GroupCode> groupCode = null;
    @SerializedName("Class")
    @Expose
    private List<ClassOfProduct> _clases = null;
    @SerializedName("UM")
    @Expose
    private List<UM> uM = null;
    @SerializedName("ProductType")
    @Expose
    private List<ProductType> productType = null;
    @SerializedName("StateDivision")
    @Expose
    private List<StateDivision> stateDivision = null;
    @SerializedName("Location")
    @Expose
    private List<Location> location = null;
    @SerializedName("CustomerFeedback")
    @Expose
    private List<CustomerFeedback> customerFeedbacks = null;

    public List<District> getDistrict() {
        return district;
    }

    public void setDistrict(List<District> district) {
        this.district = district;
    }

    public List<Township> getTownship() {
        return township;
    }

    public void setTownship(List<Township> township) {
        this.township = township;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<GroupCode> getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(List<GroupCode> groupCode) {
        this.groupCode = groupCode;
    }

    public List<ClassOfProduct> getClass_() {
        return _clases;
    }

    public void setClass_(List<ClassOfProduct> _clases) {
        this._clases = _clases;
    }

    public List<UM> getUM() {
        return uM;
    }

    public void setUM(List<UM> uM) {
        this.uM = uM;
    }

    public List<ProductType> getProductType() {
        return productType;
    }

    public void setProductType(List<ProductType> productType) {
        this.productType = productType;
    }

    public List<StateDivision> getStateDivision() {
        return stateDivision;
    }

    public void setStateDivision(List<StateDivision> stateDivision) {
        this.stateDivision = stateDivision;
    }

    public List<Location> getLocation() {
        return location;
    }

    public void setLocation(List<Location> location) {
        this.location = location;
    }

    public List<CustomerFeedback> getCustomerFeedbacks() {
        return customerFeedbacks;
    }

    public void setCustomerFeedbacks(List<CustomerFeedback> customerFeedbacks) {
        this.customerFeedbacks = customerFeedbacks;
    }
}
