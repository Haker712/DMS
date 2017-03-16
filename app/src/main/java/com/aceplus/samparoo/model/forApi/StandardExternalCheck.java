package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by phonelin on 3/15/17.
 */

public class StandardExternalCheck {

    @SerializedName("Id")
    @Expose
    private String id;
    @SerializedName("ImageNo")
    @Expose
    private String imageNo;
    @SerializedName("ImageName")
    @Expose
    private String imageName;
    @SerializedName("InvoiceDate")
    @Expose
    private String invoiceDate;
    @SerializedName("Image")
    @Expose
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageNo() {
        return imageNo;
    }

    public void setImageNo(String imageNo) {
        this.imageNo = imageNo;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
