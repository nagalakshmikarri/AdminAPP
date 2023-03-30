package com.example.amplifieradmin.data.api

import com.amplifier.amplifier.data.network.NetworkResponse
import com.example.amplifieradmin.ErrorResponse
import com.example.amplifieradmin.data.model.*
import retrofit2.http.*

interface ApiService {
    @POST("login")
    @FormUrlEncoded
    suspend fun getLoginUser(
        @Header("Authorization") authorization: String,
        @Field("username") username: String,
        @Field("password") password: String?,
    ):NetworkResponse<LoginResp, ErrorResponse>

    @POST("admin_users")
    suspend fun getAdminUser(
        @Header("Authorization") authorization: String,
        ):NetworkResponse<AdminUsersResp,ErrorResponse>

    @POST("subadmin_info")
    @FormUrlEncoded
    suspend fun getSubAdminInfo(
        @Header("Authorization") authorization: String,
        @Field("subadmin_id") subadmin_id:Int
    ):NetworkResponse<SubAdminInfoResp,ErrorResponse>

    @POST("subadmin_ads_pending")
    @FormUrlEncoded
    suspend fun getSubadminAdsPending(
        @Header("Authorization") authorization: String,
        @Field("admin_id") admin_id: String,
        @Field("id") id: String?,
    ):NetworkResponse<AdsPendingResp, ErrorResponse>

    @POST("admin_ads_accept")
    @FormUrlEncoded
    suspend fun getAdminAdsAccept(
        @Header("Authorization") authorization: String,
        @Field("admin_id") admin_id: String,
        @Field("id") id: String?,
        ):NetworkResponse<AcceptResp,ErrorResponse>

    @POST("admin_ads_reject")
    @FormUrlEncoded
    suspend fun getAdminAdsReject(
        @Header("Authorization") authorization: String,
        @Field("admin_id") admin_id: String,
        @Field("id") id: String?,
    ):NetworkResponse<RejectResp,ErrorResponse>

    @POST("subadmin_ads_accept")
    @FormUrlEncoded
    suspend fun getSubAdminAdsAccept(
        @Header("Authorization") authorization: String,
        @Field("subadmin_id") subadmin_id:Int
    ):NetworkResponse<AcceptAdsResp,ErrorResponse>

    @POST("subadmin_ads_reject")
    @FormUrlEncoded
    suspend fun getSubAdminAdsReject(
        @Header("Authorization") authorization: String,
        @Field("subadmin_id") subadmin_id:Int
    ):NetworkResponse<RejectAdsResp,ErrorResponse>

    @POST("admin_addedbusiness_claimed")
    @FormUrlEncoded
    suspend fun getCliamBusiness(
        @Header("Authorization") authorization: String,
        @Field("admin_id") admin_id: String,
    ):NetworkResponse<CliamBusinessResp,ErrorResponse>

    @POST("get_types")
    suspend fun getTypesList(
        @Header("Authorization") authorization: String,
    ):NetworkResponse<TypesLIstResp,ErrorResponse>

    @POST("business_register")
    @FormUrlEncoded
    suspend fun getRegister(
        @Header("Authorization") authorization: String,
        @Field("s_email") s_email: String?,
        @Field("s_phone_code") s_phone_code: String?,
        @Field("s_phone") s_phone: String?,
        @Field("s_business") s_business: String?,
        @Field("s_password") s_password: String?,
        @Field("s_username") s_username: String?,
        @Field("s_name") s_name: String?,
        @Field("s_latit") s_latit: String?,
        @Field("s_longit") s_longit: String?,
        @Field("s_type") s_type: String?,
        @Field("s_address") s_address: String?,
        @Field("admin_id") admin_id: String,
        @Field("timezone") timezone: String,
        @Field("zone") zone: String,

    ): NetworkResponse<RegisterResp, ErrorResponse>

    @POST("admin_addedbusiness_pendings")
    @FormUrlEncoded
    suspend fun getBusinessList(
        @Header("Authorization") authorization: String,
        @Field("admin_id") admin_id: String,
    ):NetworkResponse<BusinessListResp,ErrorResponse>

    @GET("user_addedbusiness")
    suspend fun recommend_Business(
        @Header("Authorization") authorization: String
    ): NetworkResponse<RecommmendBusinnessResp, ErrorResponse>

    @POST("add_business_category")
    @FormUrlEncoded
    suspend fun category(
        @Header("Authorization") authorization: String,
        @Field("name") name: String,
        @Field("tag") tag: String,
        ):NetworkResponse<CategoryResp,ErrorResponse>

    @GET("list_business_category")
    suspend fun get_Category(
        @Header("Authorization") authorization: String
    ): NetworkResponse<GetCategoryResp, ErrorResponse>

    @POST("update_business_category")
    @FormUrlEncoded
    suspend fun business_Category(
        @Header("Authorization") authorization: String,
        @Field("s_id") s_id: String,
        @Field("category") category: String,
        ):NetworkResponse<BusinessCategoryResp,ErrorResponse>

    @GET("allbusiness")
    suspend fun all_business(
        @Header("Authorization") authorization: String
    ): NetworkResponse<AllBusinessListResp, ErrorResponse>

    @GET("get_tags")
    suspend fun get_Tags(
        @Header("Authorization") authorization: String
    ): NetworkResponse<GetTagsResp, ErrorResponse>

    @POST("updatedevice")
    @FormUrlEncoded
    suspend fun updatedevice(
        @Header("Authorization") authorization: String,
        @Field("id") id: String?,
        @Field("token") token: String?
    ): NetworkResponse<UpdateDeviceResp, ErrorResponse>

    @POST("user_claimedbusiness")
    suspend fun claimedBusinessList(
        @Header("Authorization") authorization: String,
    ): NetworkResponse<CliamBusinessListResp, ErrorResponse>

    @POST("list_detail_claimedbusiness")
    @FormUrlEncoded
    suspend fun detailClaimBusiness(
        @Header("Authorization") authorization: String,
        @Field("s_id") s_id: String,
        ): NetworkResponse<CliamDetailResp, ErrorResponse>
    @POST("get_states")
    @FormUrlEncoded
    suspend fun states(
        @Header("Authorization") authorization: String,
        @Field("country_id") s_type: String?
    ): NetworkResponse<StatesResp, ErrorResponse>
    @GET("api_s/get_countries")
    suspend fun get_countries(
        @Header("Authorization") authorization: String
    ): NetworkResponse<GetCountriesResp, ErrorResponse>

}
