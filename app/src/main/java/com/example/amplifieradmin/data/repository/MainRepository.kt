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

    suspend fun category(categoryReq: CategoryReq)=apiHelper.category(categoryReq)

    suspend fun get_category()=apiHelper.get_Category()

    suspend fun business_Category(businessCategoryReq: BusinessCategoryReq)=apiHelper.business_Category(businessCategoryReq)

    suspend fun all_business()=apiHelper.all_business()

    suspend fun get_Tags()=apiHelper.get_Tags()

    suspend fun updatedevice(updateDeviceReq: UpdateDeviceReq)=apiHelper.updatedevice(updateDeviceReq)
}
