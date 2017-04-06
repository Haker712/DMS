package com.aceplus.samparoo.model.forApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by phonelin on 4/6/17.
 */

public class CompanyInformation {

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("MainDBName")
    @Expose
    private String mainDBName;
    @SerializedName("HomeCurrencyId")
    @Expose
    private Integer homeCurrencyId;
    @SerializedName("MultiCurrency")
    @Expose
    private Integer multiCurrency;
    @SerializedName("StartDate")
    @Expose
    private String startDate;
    @SerializedName("EndDate")
    @Expose
    private String endDate;
    @SerializedName("AutoGenerate")
    @Expose
    private Integer autoGenerate;
    @SerializedName("CompanyName")
    @Expose
    private String companyName;
    @SerializedName("ShortName")
    @Expose
    private String shortName;
    @SerializedName("ContactPerson")
    @Expose
    private String contactPerson;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Website")
    @Expose
    private String website;
    @SerializedName("SerialNumber")
    @Expose
    private String serialNumber;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("IsSeparator")
    @Expose
    private Integer isSeparator;
    @SerializedName("Amount_Format")
    @Expose
    private Integer amountFormat;
    @SerializedName("Price_Format")
    @Expose
    private Integer priceFormat;
    @SerializedName("Quantity_Format")
    @Expose
    private Integer quantityFormat;
    @SerializedName("Rate_Format")
    @Expose
    private Integer rateFormat;
    @SerializedName("ValuationMethod")
    @Expose
    private String valuationMethod;
    @SerializedName("Font")
    @Expose
    private String font;
    @SerializedName("ReportFont")
    @Expose
    private String reportFont;
    @SerializedName("ReceiptVoucher")
    @Expose
    private String receiptVoucher;
    @SerializedName("prnPort")
    @Expose
    private String prnPort;
    @SerializedName("POSVoucherFooter1")
    @Expose
    private String pOSVoucherFooter1;
    @SerializedName("POSVoucherFooter2")
    @Expose
    private String pOSVoucherFooter2;
    @SerializedName("IsStockAutoGenerate")
    @Expose
    private Integer isStockAutoGenerate;
    @SerializedName("PCCount")
    @Expose
    private String pCCount;
    @SerializedName("ExpiredMonth")
    @Expose
    private String expiredMonth;
    @SerializedName("Paidstatus")
    @Expose
    private String paidstatus;
    @SerializedName("H1")
    @Expose
    private String h1;
    @SerializedName("H2")
    @Expose
    private String h2;
    @SerializedName("H3")
    @Expose
    private String h3;
    @SerializedName("H4")
    @Expose
    private String h4;
    @SerializedName("F1")
    @Expose
    private String f1;
    @SerializedName("F2")
    @Expose
    private String f2;
    @SerializedName("F3")
    @Expose
    private String f3;
    @SerializedName("F4")
    @Expose
    private String f4;
    @SerializedName("Tax")
    @Expose
    private Integer tax;
    @SerializedName("Branch_Code")
    @Expose
    private String branchCode;
    @SerializedName("Branch_Name")
    @Expose
    private String branchName;
    @SerializedName("HBCode")
    @Expose
    private String hBCode;
    @SerializedName("Active")
    @Expose
    private Integer active;
    @SerializedName("TS")
    @Expose
    private String tS;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("CreatedUserID")
    @Expose
    private Integer createdUserID;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("UpdatedUserID")
    @Expose
    private String updatedUserID;
    @SerializedName("CreditSale")
    @Expose
    private Integer creditSale;
    @SerializedName("UseCombo")
    @Expose
    private Integer useCombo;
    @SerializedName("LastDayCloseDate")
    @Expose
    private String lastDayCloseDate;
    @SerializedName("LastUpdateInvoiceDate")
    @Expose
    private String lastUpdateInvoiceDate;
    @SerializedName("CompanyType")
    @Expose
    private String companyType;
    @SerializedName("CompanyCode")
    @Expose
    private String companyCode;
    @SerializedName("StartTime")
    @Expose
    private String startTime;
    @SerializedName("EndTime")
    @Expose
    private String endTime;
    @SerializedName("BranchId")
    @Expose
    private Integer branchId;
    @SerializedName("PrintCopy")
    @Expose
    private Integer printCopy;
    @SerializedName("TaxType")
    @Expose
    private String taxType;
    @SerializedName("BalanceControl")
    @Expose
    private String balanceControl;
    @SerializedName("TransactionAutoGenerate")
    @Expose
    private Integer transactionAutoGenerate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainDBName() {
        return mainDBName;
    }

    public void setMainDBName(String mainDBName) {
        this.mainDBName = mainDBName;
    }

    public Integer getHomeCurrencyId() {
        return homeCurrencyId;
    }

    public void setHomeCurrencyId(Integer homeCurrencyId) {
        this.homeCurrencyId = homeCurrencyId;
    }

    public Integer getMultiCurrency() {
        return multiCurrency;
    }

    public void setMultiCurrency(Integer multiCurrency) {
        this.multiCurrency = multiCurrency;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getAutoGenerate() {
        return autoGenerate;
    }

    public void setAutoGenerate(Integer autoGenerate) {
        this.autoGenerate = autoGenerate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getIsSeparator() {
        return isSeparator;
    }

    public void setIsSeparator(Integer isSeparator) {
        this.isSeparator = isSeparator;
    }

    public Integer getAmountFormat() {
        return amountFormat;
    }

    public void setAmountFormat(Integer amountFormat) {
        this.amountFormat = amountFormat;
    }

    public Integer getPriceFormat() {
        return priceFormat;
    }

    public void setPriceFormat(Integer priceFormat) {
        this.priceFormat = priceFormat;
    }

    public Integer getQuantityFormat() {
        return quantityFormat;
    }

    public void setQuantityFormat(Integer quantityFormat) {
        this.quantityFormat = quantityFormat;
    }

    public Integer getRateFormat() {
        return rateFormat;
    }

    public void setRateFormat(Integer rateFormat) {
        this.rateFormat = rateFormat;
    }

    public String getValuationMethod() {
        return valuationMethod;
    }

    public void setValuationMethod(String valuationMethod) {
        this.valuationMethod = valuationMethod;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getReportFont() {
        return reportFont;
    }

    public void setReportFont(String reportFont) {
        this.reportFont = reportFont;
    }

    public String getReceiptVoucher() {
        return receiptVoucher;
    }

    public void setReceiptVoucher(String receiptVoucher) {
        this.receiptVoucher = receiptVoucher;
    }

    public String getPrnPort() {
        return prnPort;
    }

    public void setPrnPort(String prnPort) {
        this.prnPort = prnPort;
    }

    public String getPOSVoucherFooter1() {
        return pOSVoucherFooter1;
    }

    public void setPOSVoucherFooter1(String pOSVoucherFooter1) {
        this.pOSVoucherFooter1 = pOSVoucherFooter1;
    }

    public String getPOSVoucherFooter2() {
        return pOSVoucherFooter2;
    }

    public void setPOSVoucherFooter2(String pOSVoucherFooter2) {
        this.pOSVoucherFooter2 = pOSVoucherFooter2;
    }

    public Integer getIsStockAutoGenerate() {
        return isStockAutoGenerate;
    }

    public void setIsStockAutoGenerate(Integer isStockAutoGenerate) {
        this.isStockAutoGenerate = isStockAutoGenerate;
    }

    public String getPCCount() {
        return pCCount;
    }

    public void setPCCount(String pCCount) {
        this.pCCount = pCCount;
    }

    public String getExpiredMonth() {
        return expiredMonth;
    }

    public void setExpiredMonth(String expiredMonth) {
        this.expiredMonth = expiredMonth;
    }

    public String getPaidstatus() {
        return paidstatus;
    }

    public void setPaidstatus(String paidstatus) {
        this.paidstatus = paidstatus;
    }

    public String getH1() {
        return h1;
    }

    public void setH1(String h1) {
        this.h1 = h1;
    }

    public String getH2() {
        return h2;
    }

    public void setH2(String h2) {
        this.h2 = h2;
    }

    public String getH3() {
        return h3;
    }

    public void setH3(String h3) {
        this.h3 = h3;
    }

    public String getH4() {
        return h4;
    }

    public void setH4(String h4) {
        this.h4 = h4;
    }

    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public String getF2() {
        return f2;
    }

    public void setF2(String f2) {
        this.f2 = f2;
    }

    public String getF3() {
        return f3;
    }

    public void setF3(String f3) {
        this.f3 = f3;
    }

    public String getF4() {
        return f4;
    }

    public void setF4(String f4) {
        this.f4 = f4;
    }

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) {
        this.tax = tax;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getHBCode() {
        return hBCode;
    }

    public void setHBCode(String hBCode) {
        this.hBCode = hBCode;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
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

    public Integer getCreatedUserID() {
        return createdUserID;
    }

    public void setCreatedUserID(Integer createdUserID) {
        this.createdUserID = createdUserID;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedUserID() {
        return updatedUserID;
    }

    public void setUpdatedUserID(String updatedUserID) {
        this.updatedUserID = updatedUserID;
    }

    public Integer getCreditSale() {
        return creditSale;
    }

    public void setCreditSale(Integer creditSale) {
        this.creditSale = creditSale;
    }

    public Integer getUseCombo() {
        return useCombo;
    }

    public void setUseCombo(Integer useCombo) {
        this.useCombo = useCombo;
    }

    public String getLastDayCloseDate() {
        return lastDayCloseDate;
    }

    public void setLastDayCloseDate(String lastDayCloseDate) {
        this.lastDayCloseDate = lastDayCloseDate;
    }

    public String getLastUpdateInvoiceDate() {
        return lastUpdateInvoiceDate;
    }

    public void setLastUpdateInvoiceDate(String lastUpdateInvoiceDate) {
        this.lastUpdateInvoiceDate = lastUpdateInvoiceDate;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getPrintCopy() {
        return printCopy;
    }

    public void setPrintCopy(Integer printCopy) {
        this.printCopy = printCopy;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getBalanceControl() {
        return balanceControl;
    }

    public void setBalanceControl(String balanceControl) {
        this.balanceControl = balanceControl;
    }

    public Integer getTransactionAutoGenerate() {
        return transactionAutoGenerate;
    }

    public void setTransactionAutoGenerate(Integer transactionAutoGenerate) {
        this.transactionAutoGenerate = transactionAutoGenerate;
    }


}
