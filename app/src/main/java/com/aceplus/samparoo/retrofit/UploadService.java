package com.aceplus.samparoo.retrofit;

import com.aceplus.samparoo.model.forApi.InvoiceResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by haker on 2/14/17.
 */

public interface UploadService {

    @FormUrlEncoded
    @POST("upload/tsale")
    Call<InvoiceResponse> getSaleInvoice(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("upload/customer")
    Call<InvoiceResponse> getCustomer(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("upload/preorder")
    Call<InvoiceResponse> uploadPreOrderData(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("upload/tsalereturn")
    Call<InvoiceResponse> uploadSaleReturn(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("upload/posmByCustomer")
    Call<InvoiceResponse> uploadPosm(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("upload/delivery")
    Call<InvoiceResponse> uploadDelivery(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("upload/tCashReceive")
    Call<InvoiceResponse> uploadCashReceive(@Field("param_data") String paramData);

    @FormUrlEncoded
    @POST("upload/displayAssessment ")
    Call<InvoiceResponse> uploadDisplayAssessment(@Field("param_data") String paramData);
}
