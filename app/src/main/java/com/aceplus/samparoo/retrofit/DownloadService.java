package com.aceplus.samparoo.retrofit;

import com.aceplus.samparoo.model.forApi.CompanyInformationResponse;
import com.aceplus.samparoo.model.forApi.CreditResponse;
import com.aceplus.samparoo.model.forApi.CustomerResponse;
import com.aceplus.samparoo.model.forApi.CustomerVisitResponse;
import com.aceplus.samparoo.model.forApi.DeliveryResponse;
import com.aceplus.samparoo.model.forApi.DownloadMarketing;
import com.aceplus.samparoo.model.forApi.GeneralResponse;
/*import com.aceplus.samparoo.model.forApi.IncentiveResponse;*/
import com.aceplus.samparoo.model.forApi.LoginResponse;
import com.aceplus.samparoo.model.forApi.PosmShopTypeResponse;
import com.aceplus.samparoo.model.forApi.ProductResponse;
import com.aceplus.samparoo.model.forApi.PromotionResponse;
import com.aceplus.samparoo.model.forApi.SaleHistoryResponse;
import com.aceplus.samparoo.model.forApi.SaleTargetResponse;
import com.aceplus.samparoo.model.forApi.VolumeDiscountResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by haker on 2/3/17.
 */

public interface DownloadService {
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("customer")
    Call<CustomerResponse> getCustomer(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("saleitem")
    Call<ProductResponse> getProduct(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("promotion")
    Call<PromotionResponse> getPromotion(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("volumediscount")
    Call<VolumeDiscountResponse> getVolumeDiscount(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("general")
    Call<GeneralResponse> getGeneral(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("posm_shopType")
    Call<PosmShopTypeResponse> getPosmAndShopType(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("delivery")
    Call<DeliveryResponse> getDeliveryFromApi(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("standardExternalCheck")
    Call<DownloadMarketing> getMarketingFromApi(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("creditCollection")
    Call<CreditResponse> getCreditFromApi(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("customerVisit")
    Call<CustomerVisitResponse> getCustomerVisitFromApi(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("saleTarget")
    Call<SaleTargetResponse> getSaleTargetFromApi(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("companyInfo")
    Call<CompanyInformationResponse> getCompanyInformationFromApi(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("saleHistory")
    Call<SaleHistoryResponse> getSaleHistoryFromApi(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("reissue")
    Call<ProductResponse> getReissueProductFromApi(@Field("param_data") String paramData);

    /*@FormUrlEncoded
    @POST("incentive")
    Call<IncentiveResponse> getIncentiveFromApi(@Field("param_data") String paramData);*/
}
