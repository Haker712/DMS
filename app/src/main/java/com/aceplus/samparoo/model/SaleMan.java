package com.aceplus.samparoo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by haker on 2/3/17.
 */

public class SaleMan {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("SaleManNo")
    @Expose
    private String saleManNo;
    @SerializedName("SaleManName")
    @Expose
    private String saleManName;
    @SerializedName("PostId")
    @Expose
    private String postId;
    @SerializedName("Active")
    @Expose
    private String active;
    @SerializedName("TS")
    @Expose
    private String tS;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("CreatedUserID")
    @Expose
    private String createdUserID;
    @SerializedName("UpdatedDate")
    @Expose
    private Object updatedDate;
    @SerializedName("UpdatedUserID")
    @Expose
    private Object updatedUserID;
    @SerializedName("Password")
    @Expose
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaleManNo() {
        return saleManNo;
    }

    public void setSaleManNo(String saleManNo) {
        this.saleManNo = saleManNo;
    }

    public String getSaleManName() {
        return saleManName;
    }

    public void setSaleManName(String saleManName) {
        this.saleManName = saleManName;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getTS() {
        return tS;
    }

    public void setTS(String tS) {
        this.tS = tS;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedUserID() {
        return createdUserID;
    }

    public void setCreatedUserID(String createdUserID) {
        this.createdUserID = createdUserID;
    }

    public Object getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Object updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Object getUpdatedUserID() {
        return updatedUserID;
    }

    public void setUpdatedUserID(Object updatedUserID) {
        this.updatedUserID = updatedUserID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
