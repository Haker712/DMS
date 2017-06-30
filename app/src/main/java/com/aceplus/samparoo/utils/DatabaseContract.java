package com.aceplus.samparoo.utils;

/**
 * Created by haker on 2/7/17.
 */

public class DatabaseContract {

    public abstract class RouteSchedule {
        public static final String tb = "routeSchedule";
        public static final String id = "Id";
        public static final String scheduleNo = "ScheduleNo";
        public static final String date = "Date";
        public static final String fromDate = "FromDate";
        public static final String toDate = "ToDate";
        public static final String active = "Active";
        public static final String tS = "TS";
        public static final String createdDate = "CreatedDate";
        public static final String createdUserID = "CreatedUserID";
        public static final String updatedDate = "UpdatedDate";
        public static final String updatedUserID = "UpdatedUserID";
    }

    public abstract class RouteScheduleItem {
        public static final String tb = "routeScheduleItem";
        public static final String id = "Id";
        public static final String routeScheduleId = "RouteScheduleId";
        public static final String saleManID = "SaleManID";
        public static final String routeID = "RouteId";
        public static final String active = "Active";
        public static final String tS = "TS";
        public static final String createdDate = "CreatedDate";
        public static final String createdUserID = "CreatedUserID";
        public static final String updatedDate = "UpdatedDate";
        public static final String updatedUserID = "UpdatedUserID";
    }

    public abstract class RouteAssign {
        public static final String tb = "RouteAssign";
        public static final String id = "Id";
        public static final String assignDate = "AssignDate";
        public static final String customerID = "CustomerID";
        public static final String routeID = "RouteID";
        public static final String active = "Active";
        public static final String tS = "TS";
        public static final String createdDate = "CreatedDate";
        public static final String createdUserID = "CreatedUserID";
        public static final String updatedDate = "UpdatedDate";
        public static final String updatedUserID = "UpdatedUserID";
    }

    public abstract class Route {
        public static final String tb = "Route";
        public static final String id = "Id";
        public static final String routeNo = "RouteNo";
        public static final String routeName = "RouteName";
        public static final String active = "Active";
        public static final String tS = "TS";
        public static final String createdDate = "CreatedDate";
        public static final String createdUserID = "CreatedUserID";
        public static final String updatedDate = "UpdatedDate";
        public static final String updatedUserID = "UpdatedUserID";
    }

    public abstract class PromotionDate {
        public static final String tb = "PROMOTION_DATE";
        public static final String id = "ID";
        public static final String promotionPlanId = "PROMOTION_PLAN_ID";
        public static final String date = "DATE";
        public static final String promotionDate = "PROMOTION_DATE";
        public static final String processStatus = "PROCESS_STATUS";
    }

    public abstract class PromotionPrice {
        public static final String tb = "PROMOTION_PRICE";
        public static final String id = "ID";
        public static final String promotionPlanId = "PROMOTION_PLAN_ID";
        public static final String stockId = "STOCK_ID";
        public static final String fromQuantity = "FROM_QUANTITY";
        public static final String toQuantity = "TO_QUANTITY";
        public static final String promotionPrice = "PROMOTION_PRICE";
    }

    public abstract class PromotionGift {
        public static final String tb = "PROMOTION_GIFT";
        public static final String id = "ID";
        public static final String promotionPlanId = "PROMOTION_PLAN_ID";
        public static final String stockId = "STOCK_ID";
        public static final String fromQuantity = "FROM_QUANTITY";
        public static final String toQuantity = "TO_QUANTITY";
    }

    public abstract class PromotionGiftItem {
        public static final String tb = "PROMOTION_GIFT_ITEM";
        public static final String id = "ID";
        public static final String promotionPlanId = "PROMOTION_PLAN_ID";
        public static final String stockId = "STOCK_ID";
        public static final String quantity = "QUANTITY";
    }

    public abstract class VolumeDiscount {
        public static final String tb = "VOLUME_DISCOUNT";
        public static final String id = "ID";
        public static final String discountPlanNo = "DISCOUNT_PLAN_NO";
        public static final String date = "DATE";
        public static final String startDate = "START_DATE";
        public static final String endDate = "END_DATE";
        public static final String exclude = "EXCLUDE";
    }

    public abstract class VolumeDiscountFilter {
        public static final String tb = "VOLUME_DISCOUNT_FILTER";
        public static final String id = "ID";
        public static final String discountPlanNo = "DISCOUNT_PLAN_NO";
        public static final String date = "DATE";
        public static final String startDate = "START_DATE";
        public static final String endDate = "END_DATE";
        public static final String exclude = "EXCLUDE";
    }

    public abstract class VolumeDiscountFilterItem {
        public static final String tb = "VOLUME_DISCOUNT_FILTER_ITEM";
        public static final String id = "ID";
        public static final String volumeDiscountId = "VOLUME_DISCOUNT_ID";
        public static final String categoryId = "CATEGORY_ID";
        public static final String groupCodeId = "GROUP_CODE_ID";
        public static final String fromSaleAmount = "FROM_SALE_AMOUNT";
        public static final String toSaleAmount = "TO_SALE_AMOUNT";
        public static final String discountPercent = "DISCOUNT_PERCENT";
        public static final String discountAmount = "DISCOUNT_AMOUNT";
        public static final String discountPrice = "DISCOUNT_PRICE";
    }

    public abstract class VolumeDiscountItem {
        public static final String tb = "VOLUME_DISCOUNT_ITEM";
        public static final String id = "ID";
        public static final String volumeDiscountId = "VOLUME_DISCOUNT_ID";
        public static final String fromSaleAmt = "FROM_SALE_AMT";
        public static final String toSaleAmt = "TO_SALE_AMT";
        public static final String discountPercent = "DISCOUNT_PERCENT";
        public static final String discountAmount = "DISCOUNT_AMOUNT";
        public static final String discountPrice = "DISCOUNT_PRICE";
    }


    public abstract class ClassOfProduct {

        public static final String tb = "CLASS";
        public static final String id = "ID";
        public static final String name = "NAME";

    }

    public abstract class District {

        public static final String tb = "DISTRICT";
        public static final String id = "ID";
        public static final String name = "NAME";

    }

    public abstract class GroupCode {

        public static final String tb = "GROUP_CODE";
        public static final String id = "id";
        public static final String group_no = "group_no";
        public static final String name = "name";

    }

    public abstract class ProductType {

        public static final String tb = "PRODUCT_TYPE";
        public static final String id = "ID";
        public static final String name = "DESCRIPTION";

    }

    public abstract class StateDivision {

        public static final String tb = "STATE_DIVISION";
        public static final String id = "ID";
        public static final String name = "NAME";

    }

    public abstract class Township {

        public static final String tb = "TOWNSHIP";
        public static final String id = "TOWNSHIP_ID";
        public static final String name = "TOWNSHIP_NAME";

    }

    public abstract class UM {

        public static final String tb = "UM";
        public static final String id = "ID";
        public static final String name = "NAME";
        public static final String code = "CODE";

    }

    public abstract class Location {

        public static final String tb = "Location";
        public static final String id = "LocationId";
        public static final String no = "LocationNo";
        public static final String name = "LocationName";
        public static final String branchId = "branchId";
        public static final String branchName = "branchName";
    }

    public abstract class PreOrder {
        public static final String tb = "PRE_ORDER";
        public static final String invoice_id = "INVOICE_ID";
        public static final String customer_id = "CUSTOMER_ID";
        public static final String saleperson_id = "SALEPERSON_ID";
        public static final String dev_id = "DEV_ID";
        public static final String preorder_date = "PREORDER_DATE";
        public static final String expected_delivery_date = "EXPECTED_DELIVERY_DATE";
        public static final String advance_payment_amount = "ADVANCE_PAYMENT_AMOUNT";
        public static final String net_amount = "NET_AMOUNT";
        public static final String location_id = "LOCATION_ID";
        public static final String discount = "DISCOUNT";
        public static final String discount_per = "DISCOUNT_PER";
        public static final String volume_discount = "VOLUME_DISCOUNT";
        public static final String volume_discount_per = "VOLUME_DISCOUNT_PER";
    }

    public abstract class PreOrderDetail {
        public static final String tb = "PRE_ORDER_PRODUCT";
        public static final String sale_order_id = "SALE_ORDER_ID";
        public static final String product_id = "PRODUCT_ID";
        public static final String order_qty = "ORDER_QTY";
        public static final String price = "PRICE";
        public static final String total_amt = "TOTAL_AMT";
        public static final String promotion_price = "PROMOTION_PRICE";
        public static final String volume_discount = "VOLUME_DISCOUNT";
        public static final String volume_discount_per = "VOLUME_DISCOUNT_PER";
        public static final String exclude = "EXCLUDE";
    }

    public abstract class POSM {
        public static final String TABLE = "POSM";
        public static final String ID = "ID";
        public static final String INVOICE_NO = "INVOICE_NO";
        public static final String INVOICE_DATE = "INVOICE_DATE";
        public static final String SHOP_TYPE_ID = "SHOP_TYPE_ID";
        public static final String STOCK_ID = "STOCK_ID";
    }

    public abstract class SHOP_TYPE {
        public static final String TABLE = "SHOP_TYPE";
        public static final String ID = "ID";
        public static final String SHOP_TYPE_NO = "SHOP_TYPE_NO";
        public static final String SHOP_TYPE_NAME = "SHOP_TYPE_NAME";
    }

    public abstract class DELIVERY {
        public static final String TABLE = "DELIVERY";
        public static final String ID = "ID";
        public static final String INVOICE_NO = "INVOICE_NO";
        public static final String INVOICE_DATE = "INVOICE_DATE";
        public static final String CUSTOMER_ID = "CUSTOMER_ID";
        public static final String AMOUNT = "AMOUNT";
        public static final String PAID_AMOUNT = "PAID_AMOUNT";
        public static final String EXP_DATE = "EXP_DATE";
        public static final String SALEMAN_ID = "SALEMAN_ID";
    }

    public abstract class DELIVERY_ITEM {
        public static final String TABLE = "DELIVERY_ITEM";
        public static final String ID = "ID";
        public static final String DELIVERY_ID = "DELIVERY_ID";
        public static final String STOCK_NO = "STOCK_NO";
        public static final String ORDER_QTY = "ORDER_QTY";
        public static final String RECEIVED_QTY = "RECEIVED_QTY";
        public static final String SPRICE = "SPRICE";
        public static final String FOC_STATUS = "FOC_STATUS";
    }

    public abstract class DELIVERY_PRESENT {
        public static final String TABLE = "DELIVERY_PRESENT";
        public static final String ID = "ID";
        public static final String STOCK_ID = "STOCK_ID";
        public static final String QUANTITY = "QUANTITY";
        public static final String SALE_ORDER_ID = "SALE_ORDER_ID";
    }

    public abstract class DELIVERY_UPLOAD {
        public static final String TABLE = "DELIVERY_UPLOAD";
        public static final String INVOICE_NO = "INVOICE_NO";
        public static final String INVOICE_DATE = "INVOICE_DATE";
        public static final String CUSTOMER_ID = "CUSTOMER_ID";
        public static final String SALE_ID = "SALE_ID";
        public static final String REMARK = "REMARK";
    }

    public abstract class DELIVERY_ITEM_UPLOAD {
        public static final String TABLE = "DELIVERY_ITEM_UPLOAD";
        public static final String DELIVERY_ID = "DELIVERY_ID";
        public static final String STOCK_ID = "STOCK_ID";
        public static final String QUANTITY = "QUANTITY";
        public static final String FOC = "FOC";
    }

    public abstract class CREDIT {
        public static final String TABLE = "CREDIT";
        public static final String ID = "ID";
        public static final String INVOICE_NO = "INVOICE_NO";
        public static final String INVOICE_DATE = "INVOICE_DATE";
        public static final String CUSTOMER_ID = "CUSTOMER_ID";
        public static final String AMT = "AMT";
        public static final String PAY_AMT = "PAY_AMT";
        public static final String FIRST_PAY_AMT = "FIRST_PAY_AMT";
        public static final String EXTRA_AMT = "EXTRA_AMT";
        public static final String REFUND = "REFUND";
        public static final String SALE_STATUS = "SALE_STATUS";
        public static final String INVOICE_STATUS = "INVOICE_STATUS";
        public static final String SALE_MAN_ID = "SALE_MAN_ID";
    }

    public abstract class CUSTOMER_BALANCE {
        public static final String TABLE = "CUSTOMER_BALANCE";
        public static final String ID = "ID";
        public static final String CUSTOMER_ID = "CUSTOMER_ID";
        public static final String CURRENCY_ID = "CURRENCY_ID";
        public static final String OPENING_BALANCE = "OPENING_BALANCE";
        public static final String BALANCE = "BALANCE";
    }

    public abstract class CASH_RECEIVE {
        public static final String TABLE = "CASH_RECEIVE";
        public static final String ID = "ID";
        public static final String RECEIVE_NO = "RECEIVE_NO";
        public static final String RECEIVE_DATE = "RECEIVE_DATE";
        public static final String CUSTOMER_ID = "CUSTOMER_ID";
        public static final String AMOUNT = "AMOUNT";
        public static final String CURRENCY_ID = "CURRENCY_ID";
        public static final String STATUS = "STATUS";
        public static final String LOCATION_ID = "LOCATION_ID";
        public static final String PAYMENT_TYPE = "PAYMENT_TYPE";
        public static final String CASH_RECEIVE_TYPE = "CASH_RECEIVE_TYPE";
        public static final String SALE_ID = "SALE_ID";
        public static final String SALE_MAN_ID = "SALE_MAN_ID";
    }

    public abstract class CASH_RECEIVE_ITEM {
        public static final String TABLE = "CASH_RECEIVE_ITEM";
        public static final String RECEIVE_NO = "RECEIVE_NO";
        public static final String SALE_ID = "SALE_ID";
    }

    public abstract class MARKETING {

        public static final String TABLE = "STANDARD_EXTERNAL_CHECK";
        public static final String ID = "ID";
        public static final String IMAGE_NO = "IMAGE_NO";
        public static final String IMAGE_NAME = "IMAGE_NAME";
        public static final String INVOICE_DATE = "INVOICE_DATE";
        public static final String IMAGE = "IMAGE";

    }

    public abstract class SMS_RECORD {
        public static final String TABLE = "SMS_RECORD";
        public static final String SEND_DATE = "SEND_DATE";
        public static final String MSG_BODY = "MSG_BODY";
        public static final String PHONE_NO = "PHONE_NO";
    }

    public abstract class SALE_VISIT_RECORD {
        public static final String TABLE_UPLOAD = "SALE_VISIT_RECORD_UPLOAD";
        public static final String TABLE_DOWNLOAD = "SALE_VISIT_RECORD_DOWNLOAD";
        public static final String ID = "ID";
        public static final String SALEMAN_ID = "SALEMAN_ID";
        public static final String CUSTOMER_ID = "CUSTOMER_ID";
        public static final String LATITUDE = "LATITUDE";
        public static final String LONGITUDE = "LONGITUDE";
        public static final String VISIT_FLG = "VISIT_FLG";
        public static final String SALE_FLG = "SALE_FLG";
        public static final String RECORD_DATE = "RECORD_DATE";
    }

    public abstract class CustomerFeedback {

        public static final String tb = "CUSTOMER_FEEDBACK";
        public static final String ID = "ID";
        public static final String INVOICE_NO = "INV_NO";
        public static final String INVOICE_DATE = "INV_DATE";
        public static final String REMARK = "REMARK";
    }

    public abstract class TSaleFeedback {

        public static final String tb = "DID_CUSTOMER_FEEDBACK";
        public static final String ID = "ID";
        public static final String SALEMAN_ID = "SALEMAN_ID";
        public static final String INVOICE_NO = "INV_NO";
        public static final String INVOICE_DATE = "INV_DATE";
        public static final String CUSTOMER_NO = "CUSTOMER_NO";
        public static final String FEEDBACK_NO = "FEEDBACK_NO";
        public static final String FEEDBACK_DATE = "FEEDBACK_DATE";
        public static final String DESCRIPTION = "DESCRIPTION";
        public static final String REMARK = "REMARK";
    }

    public abstract class Product_Category{

        public static final String tb="PRODUCT_CATEGORY";
        public static final String CATEGORY_ID="CATEGORY_ID";
        public static final String CATEGORY_NO="CATEGORY_NO";
        public static final String CATEGORY_NAME="CATEGORY_NAME";

    }

    public abstract class Currency {

        public static final String tb = "currency";
        public static final String ID = "id";
        public static final String CURRENCY = "currency";
        public static final String DESCRIPTION = "description";
        public static final String COUPON_STATUS = "coupon_status";
    }

    public abstract class CompanyInformation {

        public static final String tb = "CompanyInformation";
        public static final String ID = "ID";
        public static final String Description = "Description";
        public static final String MainDBName = "MainDBName";
        public static final String HomeCurrencyId = "HomeCurrencyId";
        public static final String MultiCurrency = "MultiCurrency";
        public static final String StartDate = "StartDate";
        public static final String EndDate = "EndDate";
        public static final String AutoGenerate = "AutoGenerate";
        public static final String CompanyName = "CompanyName";
        public static final String ShortName = "ShortName";
        public static final String ContactPerson = "ContactPerson";
        public static final String Address = "Address";
        public static final String Email = "Email";
        public static final String Website = "Website";
        public static final String SerialNumber = "SerialNumber";
        public static final String PhoneNumber = "PhoneNumber";
        public static final String IsSeparator = "IsSeparator";
        public static final String Amount_Format = "Amount_Format";
        public static final String Price_Format = "Price_Format";
        public static final String Quantity_Format = "Quantity_Format";
        public static final String Rate_Format = "Rate_Format";
        public static final String ValuationMethod = "ValuationMethod";
        public static final String Font = "Font";
        public static final String ReportFont = "ReportFont";
        public static final String ReceiptVoucher = "ReceiptVoucher";
        public static final String prnPort = "prnPort";
        public static final String POSVoucherFooter1 = "POSVoucherFooter1";
        public static final String POSVoucherFooter2 = "POSVoucherFooter2";
        public static final String IsStockAutoGenerate = "IsStockAutoGenerate";
        public static final String PCCount = "PCCount";
        public static final String ExpiredMonth = "ExpiredMonth";
        public static final String Paidstatus = "Paidstatus";
        public static final String H1 = "H1";
        public static final String H2 = "H2";
        public static final String H3 = "H3";
        public static final String H4 = "H4";
        public static final String F1 = "F1";
        public static final String F2 = "F2";
        public static final String F3 = "F3";
        public static final String F4 = "F4";
        public static final String Tax = "Tax";
        public static final String Branch_Code = "Branch_Code";
        public static final String Branch_Name = "Branch_Name";
        public static final String HBCode = "HBCode";
        public static final String CreditSale = "CreditSale";
        public static final String UseCombo = "UseCombo";
        public static final String LastDayCloseDate = "LastDayCloseDate";
        public static final String LastUpdateInvoiceDate = "LastUpdateInvoiceDate";
        public static final String CompanyType = "CompanyType";
        public static final String CompanyCode = "CompanyCode";
        public static final String StartTime = "StartTime";
        public static final String EndTime = "EndTime";
        public static final String BranchId = "BranchId";
        public static final String PrintCopy = "PrintCopy";
        public static final String TaxType = "TaxType";
        public static final String BalanceControl = "BalanceControl";
        public static final String TransactionAutoGenerate = "TransactionAutoGenerate";

    }

    public abstract class temp_for_saleman_route {
        public static final String TABLE = "temp_for_saleman_route";
        public static final String SALEMAN_ID = "saleman_id";
        public static final String CUSTOMER_ID = "customer_id";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String ARRIVAL_TIME = "arrival_time";
        public static final String ADDED_TIME = "added_time";
        public static final String DEPARTURE_TIME = "departure_time";
        public static final String ROUTE_ID = "route_id";
    }

    public abstract class SALE_TARGET {
        public static final String TABLE_FOR_CUS = "sale_target_customer";
        public static final String TABLE_FOR_SALEMAN = "sale_target_saleman";
        public static final String ID = "ID";
        public static final String FROM_DATE = "FROM_DATE";
        public static final String TO_DATE = "TO_DATE";
        public static final String CUSTOMER_ID = "CUSTOMER_ID";
        public static final String SALE_MAN_ID = "SALE_MAN_ID";
        public static final String CATEGORY_ID = "CATEGORY_ID";
        public static final String GROUP_CODE_ID = "GROUP_CODE_ID";
        public static final String STOCK_ID = "STOCK_ID";
        public static final String TARGET_AMOUNT = "TARGET_AMOUNT";
        public static final String DATE = "DATE";
        public static final String DAY = "DAY";
        public static final String DATE_STATUS = "DATE_STATUS";
        public static final String INVOICE_NO = "INVOICE_NO";
    }

    public abstract class SALE_HISTORY {
        public static final String TABLE = "INVOICE";
        public static final String NUM = "NUM";
        public static final String INVOICE_ID = "INVOICE_ID";
        public static final String SALE_DATE = "SALE_DATE";
        public static final String CUSTOMER_ID = "CUSTOMER_ID";
        public static final String TOTAL_AMOUNT = "TOTAL_AMOUNT";
        public static final String PAY_AMOUNT = "PAY_AMOUNT";
        public static final String REFUND_AMOUNT = "REFUND_AMOUNT";
        public static final String SALE_PERSON_ID = "SALE_PERSON_ID";
        public static final String LOCATION_CODE = "LOCATION_CODE";
        public static final String CASH_OR_CREDIT = "CASH_OR_CREDIT";
        public static final String DEVICE_ID = "DEVICE_ID";
    }

    public abstract class SALE_HISTORY_DETAIL {
        public static final String TABLE = "INVOICE_PRODUCT";
        public static final String INVOICE_PRODUCT_ID = "INVOICE_PRODUCT_ID";
        public static final String PRODUCT_ID = "PRODUCT_ID";
        public static final String SALE_QUANTITY = "SALE_QUANTITY";
        public static final String DISCOUNT_AMOUNT = "DISCOUNT_AMOUNT";
    }
}
