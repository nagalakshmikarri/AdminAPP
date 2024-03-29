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
    ): NetworkResponse<LoginResp, ErrorResponse>

    @POST("admin_users")
    suspend fun getAdminUser(
        @Header("Authorization") authorization: String,
    ): NetworkResponse<AdminUsersResp, ErrorResponse>

    @POST("subadmin_info")
    @FormUrlEncoded
    suspend fun getSubAdminInfo(
        @Header("Authorization") authorization: String,
        @Field("subadmin_id") subadmin_id: Int
    ): NetworkResponse<SubAdminInfoResp, ErrorResponse>

    @POST("subadmin_ads_pending")
    @FormUrlEncoded
    suspend fun getSubadminAdsPending(
        @Header("Authorization") authorization: String,
        @Field("admin_id") admin_id: String,
        @Field("id") id: String?,
    ): NetworkResponse<AdsPendingResp, ErrorResponse>

    @POST("admin_ads_accept")
    @FormUrlEncoded
    suspend fun getAdminAdsAccept(
        @Header("Authorization") authorization: String,
        @Field("admin_id") admin_id: String,
        @Field("id") id: String?,
    ): NetworkResponse<AcceptResp, ErrorResponse>

    @POST("admin_ads_reject")
    @FormUrlEncoded
    suspend fun getAdminAdsReject(
        @Header("Authorization") authorization: String,
        @Field("admin_id") admin_id: String,
        @Field("id") id: String?,
    ): NetworkResponse<RejectResp, ErrorResponse>

    @POST("subadmin_ads_accept")
    @FormUrlEncoded
    suspend fun getSubAdminAdsAccept(
        @Header("Authorization") authorization: String,
        @Field("subadmin_id") subadmin_id: Int
    ): NetworkResponse<AcceptAdsResp, ErrorResponse>

    @POST("subadmin_ads_reject")
    @FormUrlEncoded
    suspend fun getSubAdminAdsReject(
        @Header("Authorization") authorization: String,
        @Field("subadmin_id") subadmin_id: Int
    ): NetworkResponse<RejectAdsResp, ErrorResponse>

    @POST("admin_addedbusiness_claimed")
    @FormUrlEncoded
    suspend fun getCliamBusiness(
        @Header("Authorization") authorization: String,
        @Field("admin_id") admin_id: String,
    ): NetworkResponse<CliamBusinessResp, ErrorResponse>

    @POST("get_types")
    suspend fun getTypesList(
        @Header("Authorization") authorization: String,
    ): NetworkResponse<TypesLIstResp, ErrorResponse>

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
    ): NetworkResponse<BusinessListResp, ErrorResponse>

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
    ): NetworkResponse<CategoryResp, ErrorResponse>

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
    ): NetworkResponse<BusinessCategoryResp, ErrorResponse>

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

    @POST("edit_claimedbusiness")
    @FormUrlEncoded
    suspend fun editClaimedBusiness(
        @Header("Authorization") authorization: String,
        @Field("id") id: String?,
    ): NetworkResponse<EditClaimBusinessResponse, ErrorResponse>


    @POST("get_states")
    @FormUrlEncoded
    suspend fun states(
        @Header("Authorization") authorization: String,
        @Field("country_id") s_type: String?
    ): NetworkResponse<StatesResp, ErrorResponse>

    @GET("get_countries")
    suspend fun get_countries(
        @Header("Authorization") authorization: String
    ): NetworkResponse<GetCountriesResp, ErrorResponse>

    @GET("user_approvedbusiness")
    suspend fun userApprovedBusiness(
        @Header("Authorization") authorization: String
    ): NetworkResponse<ApprovedBusinessListResp, ErrorResponse>


    @POST("approve_claimedbusiness")
    @FormUrlEncoded
    suspend fun approveClaimedBusiness(
        @Header("Authorization") authorization: String,
        @Field("s_id") s_id: String?,
        @Field("id") id: String?,
        @Field("s_email") s_email: String?,
        @Field("country") country: String?,
        @Field("s_address1") s_address1: String?,
        @Field("s_address2") s_address2: String?,
        @Field("s_address3") s_address3: String?,
        @Field("s_latit") s_latit: String?,
        @Field("s_longit") s_longit: String?,
        @Field("s_type") s_type: String?,
        @Field("admin_id") admin_id: String?,
        @Field("s_phone") s_phone: String?,
        @Field("s_phone_code") s_phone_code: String?,
        @Field("s_categoty") s_categoty: String?,
        @Field("state") state: String?,
        @Field("city") city: String?,
        @Field("s_business") s_business: String?,
        @Field("s_username") s_username: String?,
    ): NetworkResponse<ApproveClaimBusinessResp, ErrorResponse>

    @POST("user_confirmed_addedbusiness")
    @FormUrlEncoded
    suspend fun confirmedList(
        @Header("Authorization") authorization: String,
        @Field("s_id") s_id: String?,
    ): NetworkResponse<ConfirmedListResp, ErrorResponse>

    @POST("user_blocked_addedbusiness")
    @FormUrlEncoded
    suspend fun blockedList(
        @Header("Authorization") authorization: String,
        @Field("s_id") s_id: String?,
    ): NetworkResponse<BlockedListResp, ErrorResponse>

    @POST("block_user_addedbusiness")
    @FormUrlEncoded
    suspend fun blockedUser(
        @Header("Authorization") authorization: String,
        @Field("s_id") s_id: String?,
    ): NetworkResponse<BlockUserResp, ErrorResponse>

    @POST("confirm_user_addedbusiness")
    @FormUrlEncoded
    suspend fun confirmUser(
        @Header("Authorization") authorization: String,
        @Field("s_id") s_id: String?,
    ): NetworkResponse<ConfirmUserResp, ErrorResponse>

    @POST("admin_jobs")
    suspend fun allJobs(
        @Header("Authorization") authorization: String,
    ): NetworkResponse<AllJobsResp, ErrorResponse>

    @POST("admin_confirmed_jobs")
    suspend fun confirmJobs(
        @Header("Authorization") authorization: String,
    ): NetworkResponse<ConfirmedJobsResp, ErrorResponse>

    @POST("admin_blocked_jobs")
    suspend fun blockJobs(
        @Header("Authorization") authorization: String,
    ): NetworkResponse<BlockJobsResp, ErrorResponse>

    @POST("block_user_addedjobs")
    @FormUrlEncoded
    suspend fun blockUserJobs(
        @Header("Authorization") authorization: String,
        @Field("id") id: String?,
    ): NetworkResponse<BlockUserJobResp, ErrorResponse>

    @POST("confirm_user_addedjobs")
    @FormUrlEncoded
    suspend fun confirmUserJobs(
        @Header("Authorization") authorization: String,
        @Field("id") id: String?,
    ): NetworkResponse<ConfirmUserJobResp, ErrorResponse>

    @POST("get_rewards_settings")
    suspend fun gettingRewards(
        @Header("Authorization") authorization: String,
    ): NetworkResponse<GettingRewardResp, ErrorResponse>

    @POST("update_rewards_settings")
    @FormUrlEncoded
    suspend fun updateRewards(
        @Header("Authorization") authorization: String,
        @Field("register") register: String?,
        @Field("home_screen_view") home_screen_view: String?,
        @Field("friend_accept") friend_accept: String?,
    ): NetworkResponse<UpdateRewardsResp, ErrorResponse>

    @POST("list_invite_type")
    suspend fun listInviteType(
        @Header("Authorization") authorization: String,
    ): NetworkResponse<ListInviteTypeResp, ErrorResponse>

    @POST("add_invite_type")
    @FormUrlEncoded
    suspend fun addInviteType(
        @Header("Authorization") authorization: String,
        @Field("type") type: String?
    ): NetworkResponse<AddInviteTypeResp, ErrorResponse>

    @POST("list_invite_subtype")
    @FormUrlEncoded
    suspend fun subInviteType(
        @Header("Authorization") authorization: String,
        @Field("type_id") type_id: String?
    ): NetworkResponse<SubTypeInviteListResp, ErrorResponse>

    @POST("add_invite_subtype")
    @FormUrlEncoded
    suspend fun addSubTypeInvite(
        @Header("Authorization") authorization: String,
        @Field("type") type: String?,
        @Field("type_id") type_id: String?
    ): NetworkResponse<AddSubTypeInviteResp, ErrorResponse>

    @POST("get_categories")
    suspend fun getCategories(
        @Header("Authorization") authorization: String,
    ): NetworkResponse<GetCategoriesResp, ErrorResponse>

    @POST("update_subtype_categories")
    @FormUrlEncoded
    suspend fun subCategories(
        @Header("Authorization") authorization: String,
        @Field("subtype_id") subtype_id: String?,
        @Field("cat_id") cat_id: String?
    ): NetworkResponse<SubCategoriesResp, ErrorResponse>

    @POST("edit_subtype_categories")
    @FormUrlEncoded
    suspend fun editSubTypeCategory(
        @Header("Authorization") authorization: String,
        @Field("subtype_id") subtype_id: String?,
    ): NetworkResponse<EditSubTypeCategoryResp, ErrorResponse>
    @POST("allbusiness_priority")
    suspend fun priorityList(
        @Header("Authorization") authorization: String,
    ): NetworkResponse<PriorityListRsp, ErrorResponse>
    @POST("allbusiness_claimed")
    @FormUrlEncoded
    suspend fun allCliamedBusiness(
        @Header("Authorization") authorization: String,
        @Field("city") city: String?,
        ): NetworkResponse<AllCliaedBusinessResp, ErrorResponse>
    @POST("get_cities_list")
    @FormUrlEncoded
    suspend fun getCities(
        @Header("Authorization") authorization: String,
        @Field("city") city: String?,
    ): NetworkResponse<GetCitiesResp, ErrorResponse>
    @POST("add_business_priority")
    @FormUrlEncoded
    suspend fun addBusinessPriority(
        @Header("Authorization") authorization: String,
        @Field("s_id") s_id: String?,
        @Field("duration") duration: String?,
        @Field("start_date") start_date: String?,
        @Field("amount") amount: String?,
        @Field("rank") rank: String?,
    ): NetworkResponse<AddBusinessPriorityResp, ErrorResponse>

}
