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

    suspend fun claimBusinessList()=apiHelper.cliamBusinessList()

    suspend fun cliamDetail(cliamDetailReq: CliamDetailReq)=apiHelper.cliamDetail(cliamDetailReq)

    suspend fun editClaimedBusiness(id:String?)=apiHelper.editClaimedBusiness(id)

    suspend fun states(statesReq: StatesReq)=apiHelper.states(statesReq)

    suspend fun get_countries() = apiHelper.get_countries()

    suspend fun userApprovedBusiness() = apiHelper.userApprovedBusiness()

    suspend fun approveClaimedBusiness(approveClaimBusiReq: ApproveClaimBusiReq) = apiHelper.approveClaimedBusiness(approveClaimBusiReq)

    suspend fun confirmList(confirmListReq: ConfirmListReq)=apiHelper.confirmList(confirmListReq)

    suspend fun blockedList(blockedListReq: BlockedListReq)=apiHelper.blockedList(blockedListReq)

    suspend fun blockedUser(blockUserReq: BlockUserReq)=apiHelper.blockedUser(blockUserReq)

    suspend fun confirmUser(confirmUserReq: ConfirmUserReq)=apiHelper.confirmUser(confirmUserReq)

    suspend fun allJobs()=apiHelper.allJobs()

    suspend fun confirmJobs()=apiHelper.confirmJobs()

    suspend fun blockJobs()=apiHelper.blockJobs()

    suspend fun blockUserJobs(blockUserJobReq: BlockUserJobReq)=apiHelper.blockUserJobs(blockUserJobReq)

    suspend fun confirmUserJobs(confirmUserJobReq: ConfirmUserJobReq)=apiHelper.confirmUserJobs(confirmUserJobReq)

    suspend fun gettingRewards()=apiHelper.gettingRewards()

    suspend fun updateRewards(updateRewardsReq: UpdateRewardsReq)=apiHelper.updateRewards(updateRewardsReq)

    suspend fun listingInviteType()=apiHelper.listingInviteType()

    suspend fun addInviteType(addInviteTypeReq: AddInviteTypeReq)=apiHelper.addInviteType(addInviteTypeReq)

    suspend fun subInviteType(subTypeInviteListReq: SubTypeInviteListReq)=apiHelper.subInviteType(subTypeInviteListReq)

    suspend fun addSubInviteType(addSubTypeInviteReq: AddSubTypeInviteReq)=apiHelper.addSubTypeInvite(addSubTypeInviteReq)

    suspend fun getCategories()=apiHelper.getCategories()

    suspend fun subCategories(subCategoriesReq: SubCategoriesReq)=apiHelper.subCategories(subCategoriesReq)

    suspend fun editSubTypeCcategory(editSubTypeCategoryReq: EditSubTypeCategoryReq)=apiHelper.editSubTypeCategory(editSubTypeCategoryReq)

    suspend fun priorityList()=apiHelper.priorityList()

    suspend fun allCliamedBusiness(allCliamedBusinessReq: AllCliamedBusinessReq)=apiHelper.allCliamedBusiness(allCliamedBusinessReq)

    suspend fun getCities(getCitiesReq: GetCitiesReq)=apiHelper.getCities(getCitiesReq)

    suspend fun addBusinessPriority(addBusinessPriorityReq: AddBusinessPriorityReq)=apiHelper.addBusinessPriority(addBusinessPriorityReq)
}
