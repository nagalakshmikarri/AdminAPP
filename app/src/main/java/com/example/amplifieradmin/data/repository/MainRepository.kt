package com.example.amplifieradmin.data.repository

import com.example.amplifieradmin.data.api.ApiHelper
import com.example.amplifieradmin.data.model.*
import com.example.amplifieradmin.ui.main.intent.MainIntent

class MainRepository (private val apiHelper: ApiHelper)  {
    suspend fun getLoginUser(loginReq: LoginReq) = apiHelper.getLoginUser(loginReq)

    suspend fun getAdminUser()=apiHelper.getAdminUser()

    suspend fun getSubAdminInfo(subAdminInfoReq: SubAdminInfoReq)=apiHelper.getSubAdminInfo(subAdminInfoReq)

    suspend fun getAdsPending(adsPendingReq: AdsPendingReq)=apiHelper.getAdsPending(adsPendingReq)

    suspend fun getAdminAdsAccept(acceptReq: AcceptReq)=apiHelper.getAdminAdsAccept(acceptReq)

    suspend fun getAdminAdsReject(rejectReq:RejectReq)=apiHelper.getAdminAdsReject(rejectReq)

    suspend fun getSubAdminAdsAccept(acceptAdsReq: AcceptAdsReq)=apiHelper.getSubAdminAdsAccept(acceptAdsReq)

    suspend fun getSubAdminAdsReject(rejectAdsReq: RejectAdsReq)=apiHelper.getSubAdminAdsReject(rejectAdsReq)

    suspend fun getCliamBusiness(cliamBusinessReq: CliamBusinessReq)=apiHelper.getCliamBusiness(cliamBusinessReq)

    suspend fun getTypeList()=apiHelper.getTypeList()

    suspend fun getRegister(registerReq: RegisterReq)=apiHelper.getRegister(registerReq)

    suspend fun getBusinessList(businessListReq: BusinessListReq)=apiHelper.getBusinessList(businessListReq)

    suspend fun recommend_Business()=apiHelper.recommend_Business()
}