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
    data class Category(val categoryResp: CategoryResp):MainState()
    data class Get_Category(val getCategoryResp: GetCategoryResp):MainState()
    data class business_Category(val businessCategoryResp: BusinessCategoryResp):MainState()
    data class All_business(val allBusinessListResp: AllBusinessListResp):MainState()
    data class Get_Tags(val getTagsResp: GetTagsResp):MainState()
    data class Updatedevice(val updateDeviceResp: UpdateDeviceResp):MainState()

    data class ClaimBusinesssList(val cliamBusinessListResp: CliamBusinessListResp):MainState()

    data class CliamDetail(val cliamDetailResp: CliamDetailResp):MainState()

    data class EditClaimedBusiness(val editClaimBusinessResponse: EditClaimBusinessResponse):MainState()

    data class States(val statesResp: StatesResp?):MainState()

    data class GetCounteries(val getCountriesResp: GetCountriesResp?) : MainState()

    data class UserApprovedBusiness(val approvedBusinessListResp: ApprovedBusinessListResp?) : MainState()

    data class ApproveClaimedBusiness(val approveClaimBusinessResp: ApproveClaimBusinessResp?) : MainState()

    data class ConfirmList(val confirmedListResp: ConfirmedListResp?):MainState()

    data class BlockedList(val blockedListResp: BlockedListResp?):MainState()

    data class BlockedUser(val blockedUserResp: BlockUserResp?):MainState()

    data class ConfirmUser(val confirmUserResp: ConfirmUserResp?):MainState()

    data class AllJobs(val allJobsResp: AllJobsResp?):MainState()

    data class ConfirmJobs(val confirmedJobsResp: ConfirmedJobsResp?):MainState()

    data class BlockJobs(val blockJobsResp: BlockJobsResp?):MainState()

    data class BlockUserJobs(val blockUserJobResp: BlockUserJobResp?):MainState()

    data class ConfirmUserJobs(val confirmUserJobResp: ConfirmUserJobResp?):MainState()

    data class GettingRewards(val gettingRewardResp: GettingRewardResp?):MainState()

    data class UpdateRewards(val updateRewardsResp: UpdateRewardsResp?):MainState()

    data class ListingInviteTpe(val listInviiteTypeResp: ListInviteTypeResp?):MainState()

    data class AddInviteType(val addInviteTypeResp: AddInviteTypeResp?):MainState()

    data class SubInviteType(val subTypeInviteListResp: SubTypeInviteListResp?):MainState()

    data class AddSubTypeInvite(val addSubTypeInviteResp: AddSubTypeInviteResp?):MainState()

    data class GetCategories(val getCategoriesResp: GetCategoriesResp?):MainState()

    data class SubCategories(val subCategoriesResp: SubCategoriesResp?):MainState()

    data class EditSubTypeCategory(val editSubTypeCategoryResp: EditSubTypeCategoryResp?):MainState()

    data class PriorityList(val priorityListRsp: PriorityListRsp?):MainState()

    data class AllCliamedBusiness(val allCliaedBusinessResp: AllCliaedBusinessResp?):MainState()

    data class GetCities(val getCitiesResp: GetCitiesResp?):MainState()

    data class AddBusinessPriority(val addBusinessPriorityResp: AddBusinessPriorityResp?):MainState()
    data class Error(val error: String?) : MainState()
}