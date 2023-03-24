package com.example.amplifieradmin.data.api

import android.util.Base64
import com.amplifier.amplifier.data.network.NetworkResponse
import com.example.amplifieradmin.ErrorResponse
import com.example.amplifieradmin.data.model.*
import com.example.amplifieradmin.ui.main.intent.MainIntent

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    val authPayload = "amplify:amplify"
    val data = authPayload.toByteArray()
    val base64 = Base64.encodeToString(data, Base64.NO_WRAP)

    override suspend fun getLoginUser(loginReq: LoginReq): NetworkResponse<LoginResp, ErrorResponse> {
        return apiService.getLoginUser("Basic $base64".trim(), loginReq.username, loginReq.password)
    }

    override suspend fun getAdminUser(): NetworkResponse<AdminUsersResp, ErrorResponse> {
        return apiService.getAdminUser("Basic $base64".trim())
    }


    override suspend fun getSubAdminInfo(subAdminInfoReq: SubAdminInfoReq): NetworkResponse<SubAdminInfoResp, ErrorResponse> {
        return apiService.getSubAdminInfo("Basic $base64".trim(), subAdminInfoReq.subadmin_id)
    }

    override suspend fun getAdsPending(adsPendingReq: AdsPendingReq): NetworkResponse<AdsPendingResp, ErrorResponse> {
        return apiService.getSubadminAdsPending(
            "Basic $base64",
            adsPendingReq.admin_id,
            adsPendingReq.id
        )
    }

    override suspend fun getAdminAdsAccept(acceptReq: AcceptReq): NetworkResponse<AcceptResp, ErrorResponse> {
        return apiService.getAdminAdsAccept("Basic $base64", acceptReq.admin_id, acceptReq.id)

    }

    override suspend fun getAdminAdsReject(rejectReq: RejectReq): NetworkResponse<RejectResp, ErrorResponse> {
        return apiService.getAdminAdsReject("Basic $base64", rejectReq.admin_id, rejectReq.id)
    }

    override suspend fun getSubAdminAdsAccept(acceptAdsReq: AcceptAdsReq): NetworkResponse<AcceptAdsResp, ErrorResponse> {
        return apiService.getSubAdminAdsAccept("Basic $base64", acceptAdsReq.subadmin_id)
    }

    override suspend fun getSubAdminAdsReject(rejectAdsReq: RejectAdsReq): NetworkResponse<RejectAdsResp, ErrorResponse> {
        return apiService.getSubAdminAdsReject("Basic $base64", rejectAdsReq.subadmin_id)
    }

    override suspend fun getCliamBusiness(cliamBusinessReq: CliamBusinessReq): NetworkResponse<CliamBusinessResp, ErrorResponse> {
        return apiService.getCliamBusiness("Basic $base64", cliamBusinessReq.admin_id)
    }

    override suspend fun getTypeList(): NetworkResponse<TypesLIstResp, ErrorResponse> {
        return apiService.getTypesList("Basic $base64".trim())
    }

    override suspend fun getRegister(registerReq: RegisterReq): NetworkResponse<RegisterResp, ErrorResponse> {
        return apiService.getRegister(
            "Basic $base64",
            registerReq.s_email,
            registerReq.s_phone_code, registerReq.s_phone,
            registerReq.s_business, registerReq.s_password,
            registerReq.s_username, registerReq.s_name,
            registerReq.s_latit,
            registerReq.s_longit, registerReq.s_type, registerReq.s_address,
            registerReq.admin_id, registerReq.timezone, registerReq.zone
        )
    }

    override suspend fun getBusinessList(businessListReq: BusinessListReq):NetworkResponse<BusinessListResp,ErrorResponse>{
        return apiService.getBusinessList("Basic $base64".trim(),businessListReq.admin_id)
    }

    override suspend fun recommend_Business():NetworkResponse<RecommmendBusinnessResp,ErrorResponse>{
        return apiService.recommend_Business("Basic $base64".trim())
    }

    override suspend fun category(categoryReq: CategoryReq):NetworkResponse<CategoryResp,ErrorResponse>{
        return apiService.category("Basic $base64".trim(),categoryReq.name,categoryReq.tag)
    }

    override suspend fun get_Category():NetworkResponse<GetCategoryResp,ErrorResponse>{
        return apiService.get_Category("Basic $base64".trim())
    }

    override suspend fun business_Category(businessCategoryReq: BusinessCategoryReq):NetworkResponse<BusinessCategoryResp,ErrorResponse>{
        return apiService.business_Category("Basic $base64".trim(),businessCategoryReq.s_id,businessCategoryReq.category)
    }

    override suspend fun all_business():NetworkResponse<AllBusinessListResp,ErrorResponse>{
        return apiService.all_business("Basic $base64".trim())
    }

    override suspend fun get_Tags():NetworkResponse<GetTagsResp,ErrorResponse>{
        return apiService.get_Tags("Basic $base64".trim())
    }

    override suspend fun updatedevice(updateDeviceReq: UpdateDeviceReq):NetworkResponse<UpdateDeviceResp,ErrorResponse>{
        return apiService.updatedevice("Basic $base64".trim(),updateDeviceReq.id,updateDeviceReq.token)
    }

    override suspend fun cliamBusinessList(): NetworkResponse<CliamBusinessListResp, ErrorResponse> {
        return apiService.claimedBusinessList("Basic $base64".trim())

    }

    override suspend fun cliamDetail(cliamDetailReq: CliamDetailReq): NetworkResponse<CliamDetailResp, ErrorResponse> {
        return apiService.detailClaimBusiness("Basic $base64".trim(),cliamDetailReq.s_id)
    }

}