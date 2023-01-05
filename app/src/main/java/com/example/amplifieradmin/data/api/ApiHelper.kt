package com.example.amplifieradmin.data.api

import com.amplifier.amplifier.data.network.NetworkResponse
import com.example.amplifieradmin.ErrorResponse
import com.example.amplifieradmin.data.model.*
import com.example.amplifieradmin.ui.main.intent.MainIntent

interface ApiHelper {
    suspend fun getLoginUser(loginReq: LoginReq): NetworkResponse<LoginResp, ErrorResponse>

    suspend fun getAdminUser():NetworkResponse<AdminUsersResp,ErrorResponse>

    suspend fun getSubAdminInfo(subAdminInfoReq: SubAdminInfoReq):NetworkResponse<SubAdminInfoResp,ErrorResponse>

    suspend fun getAdsPending(adsPendingReq: AdsPendingReq):NetworkResponse<AdsPendingResp,ErrorResponse>

    suspend fun getAdminAdsAccept(acceptReq: AcceptReq):NetworkResponse<AcceptResp,ErrorResponse>

    suspend fun getAdminAdsReject(rejectReq: RejectReq):NetworkResponse<RejectResp,ErrorResponse>

    suspend fun getSubAdminAdsAccept(acceptAdsReq:AcceptAdsReq):NetworkResponse<AcceptAdsResp,ErrorResponse>

    suspend fun getSubAdminAdsReject(rejectAdsReq:RejectAdsReq):NetworkResponse<RejectAdsResp,ErrorResponse>

    suspend fun getCliamBusiness(cliamBusinessReq: CliamBusinessReq):NetworkResponse<CliamBusinessResp,ErrorResponse>

    suspend fun getTypeList():NetworkResponse<TypesLIstResp,ErrorResponse>

    suspend fun getRegister(registerReq: RegisterReq): NetworkResponse<RegisterResp, ErrorResponse>

    suspend fun getBusinessList(businessListReq: BusinessListReq):NetworkResponse<BusinessListResp,ErrorResponse>

    suspend fun recommend_Business():NetworkResponse<RecommmendBusinnessResp,ErrorResponse>

    suspend fun category(categoryReq: CategoryReq):NetworkResponse<CategoryResp,ErrorResponse>

    suspend fun get_Category():NetworkResponse<GetCategoryResp,ErrorResponse>
}