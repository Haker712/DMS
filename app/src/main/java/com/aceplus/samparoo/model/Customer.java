package com.aceplus.samparoo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Customer implements Serializable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("CUSTOMER_ID")
    @Expose
    private String customerId;
    @SerializedName("CUSTOMER_NAME")
    @Expose
    private String customerName;
    @SerializedName("CUSTOMER_TYPE_ID")
    @Expose
    private String customerTypeId;
    @SerializedName("CUSTOMER_TYPE_NAME")
    @Expose
    private String customerTypeName;
    @SerializedName("ADDRESS")
    @Expose
    private String address;
    @SerializedName("PH")
    @Expose
    private String phone;
    @SerializedName("township_number")
    @Expose
    private String township;
    @SerializedName("CREDIT_TERM")
    @Expose
    private String creditTerms;
    @SerializedName("CREDIT_LIMIT")
    @Expose
    private double creditLimit;
    @SerializedName("CREDIT_AMT")
    @Expose
    private double creditAmt;
    @SerializedName("DUE_AMT")
    @Expose
    private double dueAmt;
    @SerializedName("PREPAID_AMT")
    @Expose
    private double prepaidAmt;
    @SerializedName("PAYMENT_TYPE")
    @Expose
    private String paymentType;
    @SerializedName("IS_IN_ROUTE")
    @Expose
    private boolean isInRoute;
    @SerializedName("LATITUDE")
    @Expose
    private double latitude;
    @SerializedName("LONGITUDE")
    @Expose
    private double longitude;
    @SerializedName("VISIT_RECORD")
    @Expose
    private int visitRecord;

    @SerializedName("district_id")
    @Expose
    private int district_id;
    @SerializedName("state_division_id")
    @Expose
    private int state_division_id;
    @SerializedName("contact_person")
    @Expose
    private String contact_person;
    @SerializedName("customer_category_no")
    @Expose
    private String customer_category_no;
    @SerializedName("shop_type_id")
    @Expose
    private int shopTypeId;

    public Customer(){}

    public Customer(String customerId, String customerName, String customerTypeId, String customerTypeName, String address
            , String phone, String township, String creditTerms, double creditLimit, double creditAmt, double dueAmt
            , double prepaidAmt, String paymentType, String isInRoute, double latitude, double longitude, int visitRecord) {

        this.customerId = customerId;
        this.customerName = customerName;
        this.customerTypeId = customerTypeId;
        this.customerTypeName = customerTypeName;
        this.address = address;
        this.phone = phone;
        this.township = township;
        this.creditTerms = creditTerms;
        this.creditLimit = creditLimit;
        this.creditAmt = creditAmt;
        this.dueAmt = dueAmt;
        this.prepaidAmt = prepaidAmt;
        this.paymentType = paymentType;

        if(isInRoute != null && !isInRoute.equals("")) {
            this.isInRoute = isInRoute.equalsIgnoreCase("true");
        }

        this.latitude = latitude;
        this.longitude = longitude;
        this.visitRecord = visitRecord;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTypeId() {
        return customerTypeId;
    }

    public void setCustomerTypeId(String customerTypeId) {
        this.customerTypeId = customerTypeId;
    }

    public String getCustomerTypeName() {
        return customerTypeName;
    }

    public void setCustomerTypeName(String customerTypeName) {
        this.customerTypeName = customerTypeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTownship() {
        return township;
    }

    public void setTownship(String township) {
        this.township = township;
    }

    public String getCreditTerms() {
        return creditTerms;
    }

    public void setCreditTerms(String creditTerms) {
        this.creditTerms = creditTerms;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public double getCreditAmt() {
        return creditAmt;
    }

    public void setCreditAmt(double creditAmt) {
        this.creditAmt = creditAmt;
    }

    public double getDueAmt() {
        return dueAmt;
    }

    public void setDueAmt(double dueAmt) {
        this.dueAmt = dueAmt;
    }

    public double getPrepaidAmt() {
        return prepaidAmt;
    }

    public void setPrepaidAmt(double prepaidAmt) {
        this.prepaidAmt = prepaidAmt;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public boolean isInRoute() {
        return isInRoute;
    }

    public void setInRoute(boolean inRoute) {
        isInRoute = inRoute;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getVisitRecord() {
        return visitRecord;
    }

    public void setVisitRecord(int visitRecord) {
        this.visitRecord = visitRecord;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public int getState_division_id() {
        return state_division_id;
    }

    public void setState_division_id(int state_division_id) {
        this.state_division_id = state_division_id;
    }

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getCustomer_category_no() {
        return customer_category_no;
    }

    public void setCustomer_category_no(String customer_category_no) {
        this.customer_category_no = customer_category_no;
    }

    public int getShopTypeId() {
        return shopTypeId;
    }

    public void setShopTypeId(int shopTypeId) {
        this.shopTypeId = shopTypeId;
    }
}
