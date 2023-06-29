package com.example.amplifieradmin.data.api

import android.util.Base64
import com.amplifier.amplifier.data.network.NetworkResponse
import com.example.amplifieradmin.ErrorResponse
import com.example.amplifieradmin.data.model.*

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

    override suspend fun getBusinessList(businessListReq: BusinessListReq): NetworkResponse<BusinessListResp, ErrorResponse> {
        return apiService.getBusinessList("Basic $base64".trim(), businessListReq.admin_id)
    }

    override suspend fun recommend_Business(): NetworkResponse<RecommmendBusinnessResp, ErrorResponse> {
        return apiService.recommend_Business("Basic $base64".trim())
    }

    override suspend fun category(categoryReq: CategoryReq): NetworkResponse<CategoryResp, ErrorResponse> {
        return apiService.category("Basic $base64".trim(), categoryReq.name, categoryReq.tag)
    }

    override suspend fun get_Category(): NetworkResponse<GetCategoryResp, ErrorResponse> {
        return apiService.get_Category("Basic $base64".trim())
    }

    override suspend fun business_Category(businessCategoryReq: BusinessCategoryReq): NetworkResponse<BusinessCategoryResp, ErrorResponse> {
        return apiService.business_Category(
            "Basic $base64".trim(),
            businessCategoryReq.s_id,
            businessCategoryReq.category
        )
    }

    override suspend fun all_business(): NetworkResponse<AllBusinessListResp, ErrorResponse> {
        return apiService.all_business("Basic $base64".trim())
    }

    override suspend fun get_Tags(): NetworkResponse<GetTagsResp, ErrorResponse> {
        return apiService.get_Tags("Basic $base64".trim())
    }

    override suspend fun updatedevice(updateDeviceReq: UpdateDeviceReq): NetworkResponse<UpdateDeviceResp, ErrorResponse> {
        return apiService.updatedevice(
            "Basic $base64".trim(),
            updateDeviceReq.id,
            updateDeviceReq.token
        )
    }

    override suspend fun cliamBusinessList(): NetworkResponse<CliamBusinessListResp, ErrorResponse> {
        return apiService.claimedBusinessList("Basic $base64".trim())

    }

    override suspend fun cliamDetail(cliamDetailReq: CliamDetailReq): NetworkResponse<CliamDetailResp, ErrorResponse> {
        return apiService.detailClaimBusiness("Basic $base64".trim(), cliamDetailReq.s_id)
    }

    override suspend fun editClaimedBusiness(id: String?): NetworkResponse<EditClaimBusinessResponse, ErrorResponse> {
        return apiService.editClaimedBusiness("Basic $base64".trim(), id)
    }

    override suspend fun states(statesReq: StatesReq): NetworkResponse<StatesResp, ErrorResponse> {
        return apiService.states("Basic $base64".trim(), statesReq.country_id)
    }

    override suspend fun get_countries(): NetworkResponse<GetCountriesResp, ErrorResponse> {
        return apiService.get_countries("Basic $base64".trim())
    }

    override suspend fun userApprovedBusiness(): NetworkResponse<ApprovedBusinessListResp, ErrorResponse> {
        return apiService.userApprovedBusiness("Basic $base64".trim())
    }

    override suspend fun approveClaimedBusiness(approveClaimBusiReq: ApproveClaimBusiReq): NetworkResponse<ApproveClaimBusinessResp, ErrorResponse> {
        return apiService.approveClaimedBusiness(
            "Basic $base64".trim(),
            approveClaimBusiReq.s_id,
            approveClaimBusiReq.id,
            approveClaimBusiReq.s_email,
            approveClaimBusiReq.country,
            approveClaimBusiReq.s_address1,
            approveClaimBusiReq.s_address2,
            approveClaimBusiReq.s_address3,
            approveClaimBusiReq.s_latit,
            approveClaimBusiReq.s_longit,
            approveClaimBusiReq.s_type,
            approveClaimBusiReq.admin_id,
            approveClaimBusiReq.s_phone,
            approveClaimBusiReq.s_phone_code,
            approveClaimBusiReq.s_categoty,
            approveClaimBusiReq.state,
            approveClaimBusiReq.city,
            approveClaimBusiReq.s_business,
            approveClaimBusiReq.s_username,
        )
    }

    override suspend fun confirmList(confirmListReq: ConfirmListReq): NetworkResponse<ConfirmedListResp, ErrorResponse> {
        return apiService.confirmedList(
            "Basic $base64".trim(),
            confirmListReq.s_id
        )
    }

    override suspend fun blockedList(blockedListReq: BlockedListReq): NetworkResponse<BlockedListResp, ErrorResponse> {
        return apiService.blockedList(
            "Basic $base64".trim(),
            blockedListReq.s_id
        )
    }

    override suspend fun blockedUser(blockUserReq: BlockUserReq): NetworkResponse<BlockUserResp, ErrorResponse> {
        return apiService.blockedUser(
            "Basic $base64".trim(),
            blockUserReq.s_id
        )
    }

    override suspend fun confirmUser(confirmUserReq: ConfirmUserReq): NetworkResponse<ConfirmUserResp, ErrorResponse> {
        return apiService.confirmUser(
            "Basic $base64".trim(),
            confirmUserReq.s_id
        )
    }

    override suspend fun allJobs():NetworkResponse<AllJobsResp,ErrorResponse>{
        return apiService.allJobs(
            "Basic $base64".trim()
        )
    }

    override suspend fun confirmJobs(): NetworkResponse<ConfirmedJobsResp, ErrorResponse> {
        return apiService.confirmJobs(
            "Basic  $base64".trim()
        )
    }

    override suspend fun blockJobs():NetworkResponse<BlockJobsResp,ErrorResponse>{
        return apiService.blockJobs(
            "Basic $base64".trim()
        )
    }

    override suspend fun blockUserJobs(blockUserJobReq: BlockUserJobReq):NetworkResponse<BlockUserJobResp,ErrorResponse>{
        return apiService.blockUserJobs(
            "Basic $base64".trim(),
            blockUserJobReq.id
        )
    }


    override suspend fun confirmUserJobs(confirmUserJobReq: ConfirmUserJobReq):NetworkResponse<ConfirmUserJobResp,ErrorResponse>{
        return apiService.confirmUserJobs(
            "Basic $base64".trim(),
            confirmUserJobReq.id
        )
    }

    override suspend fun gettingRewards():NetworkResponse<GettingRewardResp,ErrorResponse>{
        return apiService.gettingRewards(
            "Basic $base64".trim()
        )
    }

    override suspend fun updateRewards(updateRewardsReq: UpdateRewardsReq):NetworkResponse<UpdateRewardsResp,ErrorResponse>{
        return apiService.updateRewards(
            "Basic $base64".trim(),
            updateRewardsReq.register,
            updateRewardsReq.home_screen_view,
            updateRewardsReq.friend_accept
        )
    }

    override suspend fun listingInviteType(): NetworkResponse<ListInviteTypeResp, ErrorResponse> {
        return apiService.listInviteType("Basic $base64".trim())
    }

    override suspend fun addInviteType(addInviteTypeReq: AddInviteTypeReq):NetworkResponse<AddInviteTypeResp,ErrorResponse>{
        return apiService.addInviteType("Basic $base64".trim(),addInviteTypeReq.type)
    }

    override suspend fun subInviteType(subTypeInviteListReq: SubTypeInviteListReq):NetworkResponse<SubTypeInviteListResp,ErrorResponse>{
        return apiService.subInviteType("Basic $base64".trim(),subTypeInviteListReq.type_id)
    }

    override suspend fun addSubTypeInvite(addSubTypeInviteReq: AddSubTypeInviteReq): NetworkResponse<AddSubTypeInviteResp, ErrorResponse> {
        return apiService.addSubTypeInvite("Basic $base64".trim(),addSubTypeInviteReq.type,addSubTypeInviteReq.type_id)

    }

    override suspend fun getCategories():NetworkResponse<GetCategoriesResp,ErrorResponse>{
        return apiService.getCategories("Basic $base64".trim())
    }

    override suspend fun subCategories(subCategoriesReq: SubCategoriesReq):NetworkResponse<SubCategoriesResp,ErrorResponse>{
        return apiService.subCategories("Basic $base64".trim(),subCategoriesReq.subtype_id,subCategoriesReq.cat_id)
    }

    override suspend fun editSubTypeCategory(editSubTypeCategoryReq: EditSubTypeCategoryReq):NetworkResponse<EditSubTypeCategoryResp,ErrorResponse>{
        return apiService.editSubTypeCategory("Basic $base64".trim(),editSubTypeCategoryReq.subtype_id)
    }

    override suspend fun priorityList():NetworkResponse<PriorityListRsp,ErrorResponse>{
        return apiService.priorityList("Basic $base64".trim())
    }

    override suspend fun allCliamedBusiness(allCliamedBusinessReq: AllCliamedBusinessReq):NetworkResponse<AllCliaedBusinessResp,ErrorResponse>{
        return apiService.allCliamedBusiness("Basic $base64".trim(),allCliamedBusinessReq.city)
    }

    override suspend fun getCities(getCitiesReq: GetCitiesReq):NetworkResponse<GetCitiesResp,ErrorResponse>{
        return apiService.getCities("Basic $base64".trim(),getCitiesReq.city)
    }
}