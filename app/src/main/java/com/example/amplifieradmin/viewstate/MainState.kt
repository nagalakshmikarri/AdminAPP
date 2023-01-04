package com.example.amplifieradmin.viewstate

import com.example.amplifieradmin.data.model.*


sealed  class MainState {
    object Idle : MainState()
    object Loading : MainState()
    data class LoginUser(val loginResp: LoginResp) : MainState()
    data class AdminUser(val adminUsersResp: AdminUsersResp):MainState()
    data class SubAdminInfo(val subAdminInfoResp: SubAdminInfoResp):MainState()
    data class AdsPending(val AdsPendingResp:AdsPendingResp):MainState()
    data class AdminAdsAccept(val AdsPendingResp: AcceptResp):MainState()
    data class AdminAdsReject(val AdsPendingResp: RejectResp):MainState()
    data class SubAdminAdsAccept(val acceptAdsResp: AcceptAdsResp):MainState()
    data class SubAdminAdsReject(val rejectAdsResp: RejectAdsResp):MainState()
    data class CliamBusiness(val cliamBusinessResp: CliamBusinessResp):MainState()
    data class TypeList(val typesLIstResp: TypesLIstResp):MainState()
    data class Register(val registerResp: RegisterResp):MainState()
    data class BusinessList(val businessListResp: BusinessListResp):MainState()
    data class RecommendBusiness(val recommendBusinessResp: RecommmendBusinnessResp):MainState()
    data class Error(val error: String?) : MainState()

}