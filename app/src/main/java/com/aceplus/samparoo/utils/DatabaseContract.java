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


    public abstract class ClassOfProduct{

        public static final String tb="CLASS";
        public static final String id="ID";
        public static final String name="NAME";

    }

    public abstract class District{

        public static final String tb="DISTRICT";
        public static final String id="ID";
        public static final String name="NAME";

    }

    public abstract class GroupCode{

        public static final String tb="GROUP_CODE";
        public static final String id="id";
        public static final String name="name";

    }

    public abstract class ProductType{

        public static final String tb="PRODUCT_TYPE";
        public static final String id="ID";
        public static final String name="DESCRIPTION";

    }
    public abstract class StateDivision{

        public static final String tb="STATE_DIVISION";
        public static final String id="ID";
        public static final String name="NAME";

    }
    public abstract class Township{

        public static final String tb="TOWNSHIP";
        public static final String id="TOWNSHIP_ID";
        public static final String name="TOWNSHIP_NAME";

    }
    public abstract class UM{

        public static final String tb="UM";
        public static final String id="ID";
        public static final String name="NAME";
        public static final String code="CODE";

    }

    public abstract class Location{

        public static final String tb="Location";
        public static final String id="LocationId";
        public static final String no="LocationNo";
        public static final String name="LocationName";
        public static final String branchId="branchId";
        public static final String branchName="branchName";
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
        public static final String TABLE="POSM";
        public static final String ID = "ID";
        public static final String INVOICE_NO="INVOICE_NO";
        public static final String INVOICE_DATE="INVOICE_DATE";
        public static final String SHOP_TYPE_ID="SHOP_TYPE_ID";
        public static final String STOCK_ID="STOCK_ID";
    }

    public abstract class SHOP_TYPE {
        public static final String TABLE="SHOP_TYPE";
        public static final String ID = "ID";
        public static final String SHOP_TYPE_NO="SHOP_TYPE_NO";
        public static final String SHOP_TYPE_NAME="SHOP_TYPE_NAME";
    }

    public abstract class DELIVERY {
        public static final String TABLE="DELIVERY";
        public static final String ID = "ID";
        public static final String INVOICE_NO="INVOICE_NO";
        public static final String INVOICE_DATE="INVOICE_DATE";
        public static final String CUSTOMER_ID="CUSTOMER_ID";
        public static final String AMOUNT="AMOUNT";
        public static final String PAID_AMOUNT="PAID_AMOUNT";
        public static final String EXP_DATE="EXP_DATE";
        public static final String SALEMAN_ID="SALEMAN_ID";
    }

    public abstract class DELIVERY_ITEM {
        public static final String TABLE="DELIVERY_ITEM";
        public static final String ID = "ID";
        public static final String DELIVERY_ID="DELIVERY_ID";
        public static final String STOCK_NO="STOCK_NO";
        public static final String ORDER_QTY="ORDER_QTY";
        public static final String RECEIVED_QTY="RECEIVED_QTY";
        public static final String SPRICE="SPRICE";
        public static final String FOC_STATUS="FOC_STATUS";
    }

    public abstract class DELIVERY_UPLOAD {
        public static final String TABLE = "DELIVERY_UPLOAD";
        public static final String INVOICE_NO="INVOICE_NO";
        public static final String INVOICE_DATE="INVOICE_DATE";
        public static final String CUSTOMER_ID="CUSTOMER_ID";
        public static final String SALE_ID="SALE_ID";
        public static final String REMARK="REMARK";
    }

    public abstract class DELIVERY_ITEM_UPLOAD {
        public static final String TABLE = "DELIVERY_ITEM_UPLOAD";
        public static final String DELIVERY_ID="DELIVERY_ID";
        public static final String STOCK_ID="STOCK_ID";
        public static final String DELIVERY_QTY="DELIVERY_QTY";
    }

    public abstract class MARKETING{

        public static final String TABLE="STANDARD_EXTERNAL_CHECK";
        public static final String ID="ID";
        public static final String IMAGE_NO="IMAGE_NO";
        public static final String IMAGE_NAME="IMAGE_NAME";
        public static final String INVOICE_DATE="INVOICE_DATE";
        public static final String IMAGE="IMAGE";

    }



}
