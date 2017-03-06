package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by haker on 2/9/17.
 */

public class CustomerForApi {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("township_number")
    @Expose
    private String townshipNumber;
    @SerializedName("contact_person")
    @Expose
    private String contactPerson;
    @SerializedName("CUSTOMER_ID")
    @Expose
    private String cUSTOMERID;
    @SerializedName("CUSTOMER_NAME")
    @Expose
    private String cUSTOMERNAME;
    @SerializedName("CUSTOMER_TYPE_ID")
    @Expose
    private String cUSTOMERTYPEID;
    @SerializedName("CUSTOMER_TYPE_NAME")
    @Expose
    private String cUSTOMERTYPENAME;
    @SerializedName("ADDRESS")
    @Expose
    private String aDDRESS;
    @SerializedName("PH")
    @Expose
    private String pH;
    @SerializedName("CREDIT_TERM")
    @Expose
    private String cREDITTERM;
    @SerializedName("CREDIT_LIMIT")
    @Expose
    private String cREDITLIMIT;
    @SerializedName("PAYMENT_TYPE")
    @Expose
    private String pAYMENTTYPE;
    @SerializedName("customer_category_no")
    @Expose
    private String customerCategoryNo;
    @SerializedName("CREDIT_AMT")
    @Expose
    private String cREDITAMT;
    @SerializedName("DUE_AMT")
    @Expose
    private String dUEAMT;
    @SerializedName("PREPAID_AMT")
    @Expose
    private String pREPAIDAMT;
    @SerializedName("IS_IN_ROUTE")
    @Expose
    private String iSINROUTE;
    @SerializedName("LATITUDE")
    @Expose
    private String lATITUDE;
    @SerializedName("LONGITUDE")
    @Expose
    private String lONGITUDE;
    @SerializedName("VISIT_RECORD")
    @Expose
    private String vISITRECORD;

    @SerializedName("district_id")
    @Expose
    private int district_id;
    @SerializedName("state_division_id")
    @Expose
    private int state_division_id;

    @SerializedName("shop_type_id")
    @Expose
    private int shop_type_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTownshipNumber() {
        return townshipNumber;
    }

    public void setTownshipNumber(String townshipNumber) {
        this.townshipNumber = townshipNumber;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getcUSTOMERID() {
        return cUSTOMERID;
    }

    public void setcUSTOMERID(String cUSTOMERID) {
        this.cUSTOMERID = cUSTOMERID;
    }

    public String getcUSTOMERNAME() {
        return cUSTOMERNAME;
    }

    public void setcUSTOMERNAME(String cUSTOMERNAME) {
        this.cUSTOMERNAME = cUSTOMERNAME;
    }

    public String getcUSTOMERTYPEID() {
        return cUSTOMERTYPEID;
    }

    public void setcUSTOMERTYPEID(String cUSTOMERTYPEID) {
        this.cUSTOMERTYPEID = cUSTOMERTYPEID;
    }

    public String getcUSTOMERTYPENAME() {
        return cUSTOMERTYPENAME;
    }

    public void setcUSTOMERTYPENAME(String cUSTOMERTYPENAME) {
        this.cUSTOMERTYPENAME = cUSTOMERTYPENAME;
    }

    public String getaDDRESS() {
        return aDDRESS;
    }

    public void setaDDRESS(String aDDRESS) {
        this.aDDRESS = aDDRESS;
    }

    public String getpH() {
        return pH;
    }

    public void setpH(String pH) {
        this.pH = pH;
    }

    public String getcREDITTERM() {
        return cREDITTERM;
    }

    public void setcREDITTERM(String cREDITTERM) {
        this.cREDITTERM = cREDITTERM;
    }

    public String getcREDITLIMIT() {
        return cREDITLIMIT;
    }

    public void setcREDITLIMIT(String cREDITLIMIT) {
        this.cREDITLIMIT = cREDITLIMIT;
    }

    public String getpAYMENTTYPE() {
        return pAYMENTTYPE;
    }

    public void setpAYMENTTYPE(String pAYMENTTYPE) {
        this.pAYMENTTYPE = pAYMENTTYPE;
    }

    public String getCustomerCategoryNo() {
        return customerCategoryNo;
    }

    public void setCustomerCategoryNo(String customerCategoryNo) {
        this.customerCategoryNo = customerCategoryNo;
    }

    public String getcREDITAMT() {
        return cREDITAMT;
    }

    public void setcREDITAMT(String cREDITAMT) {
        this.cREDITAMT = cREDITAMT;
    }

    public String getdUEAMT() {
        return dUEAMT;
    }

    public void setdUEAMT(String dUEAMT) {
        this.dUEAMT = dUEAMT;
    }

    public String getpREPAIDAMT() {
        return pREPAIDAMT;
    }

    public void setpREPAIDAMT(String pREPAIDAMT) {
        this.pREPAIDAMT = pREPAIDAMT;
    }

    public String getiSINROUTE() {
        return iSINROUTE;
    }

    public void setiSINROUTE(String iSINROUTE) {
        this.iSINROUTE = iSINROUTE;
    }

    public String getlATITUDE() {
        return lATITUDE;
    }

    public void setlATITUDE(String lATITUDE) {
        this.lATITUDE = lATITUDE;
    }

    public String getlONGITUDE() {
        return lONGITUDE;
    }

    public void setlONGITUDE(String lONGITUDE) {
        this.lONGITUDE = lONGITUDE;
    }

    public String getvISITRECORD() {
        return vISITRECORD;
    }

    public void setvISITRECORD(String vISITRECORD) {
        this.vISITRECORD = vISITRECORD;
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

    public int getShop_type_id() {
        return shop_type_id;
    }

    public void setShop_type_id(int shop_type_id) {
        this.shop_type_id = shop_type_id;
    }
}
