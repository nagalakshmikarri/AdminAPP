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

    suspend fun business_Category(businessCategoryReq: BusinessCategoryReq):NetworkResponse<BusinessCategoryResp,ErrorResponse>

    suspend fun all_business():NetworkResponse<AllBusinessListResp,ErrorResponse>

    suspend fun get_Tags():NetworkResponse<GetTagsResp,ErrorResponse>

    suspend fun updatedevice(updateDeviceReq: UpdateDeviceReq):NetworkResponse<UpdateDeviceResp,ErrorResponse>

    suspend fun cliamBusinessList():NetworkResponse<CliamBusinessListResp,ErrorResponse>

    suspend fun cliamDetail(cliamDetailReq: CliamDetailReq):NetworkResponse<CliamDetailResp,ErrorResponse>

    suspend fun states(statesReq: StatesReq):NetworkResponse<StatesResp,ErrorResponse>

    suspend fun get_countries():NetworkResponse<GetCountriesResp,ErrorResponse>

    suspend fun editClaimedBusiness(id: String?): NetworkResponse<EditClaimBusinessResponse, ErrorResponse>
    suspend fun approveClaimedBusiness(approveClaimBusiReq: ApproveClaimBusiReq): NetworkResponse<ApproveClaimBusinessResp, ErrorResponse>
    suspend fun userApprovedBusiness(): NetworkResponse<ApprovedBusinessListResp, ErrorResponse>

    suspend fun confirmList(confirmListReq: ConfirmListReq):NetworkResponse<ConfirmedListResp,ErrorResponse>

    suspend fun blockedList(blockedListReq: BlockedListReq):NetworkResponse<BlockedListResp,ErrorResponse>

    suspend fun blockedUser(blockedUserReq: BlockUserReq):NetworkResponse<BlockUserResp,ErrorResponse>

    suspend fun confirmUser(confirmUserReq: ConfirmUserReq):NetworkResponse<ConfirmUserResp,ErrorResponse>
}