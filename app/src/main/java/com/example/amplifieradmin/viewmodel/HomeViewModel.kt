package com.example.amplifieradmin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amplifier.amplifier.data.network.NetworkResponse
import com.example.amplifieradmin.data.model.*
import com.example.amplifieradmin.data.repository.MainRepository
import com.example.amplifieradmin.ui.main.intent.MainIntent
import com.example.amplifieradmin.viewstate.MainState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MainRepository) : ViewModel() {
    val homeIntent = Channel<MainIntent>(Channel.UNLIMITED)

    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState>
        get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            homeIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.AdminUser -> fetchAdminUser()
                    is MainIntent.SubAdminInfo -> fetchSubAdminInfo(it.subadmin_id)
                    is MainIntent.AdsPending -> fetchAdsPending(it.admin_id, it.id)
                    is MainIntent.AdminAdsAccept -> fetchAdminAdsAccept(it.admin_id, it.id)
                    is MainIntent.AdminAdsReject -> fetchAdminAdsReject(it.admin_id, it.id)
                    is MainIntent.SubAdminAdsAccept -> fetchSubAdminAdsAccept(it.subadmin_id)
                    is MainIntent.SubAdminAdsReject -> fetchSubAdminAdsReject(it.subadmin_id)
                    is MainIntent.CliamBusiness -> fetchCliamBusiness(it.admin_id)
                    is MainIntent.TypeList -> fetchTypeList()
                    is MainIntent.Register -> fetchRegister(
                        it.s_email,
                        it.s_phone_code,
                        it.s_phone,
                        it.s_business,
                        it.s_password,
                        it.s_username,
                        it.s_name,
                        it.s_latit,
                        it.s_longit,
                        it.s_type,
                        it.s_address,
                        it.admin_id,
                        it.timezone,
                        it.zone
                    )
                    is MainIntent.BusinessList -> fetchBusinessList(it.admin_id)
                    is MainIntent.RecommendBusiness -> recommendBusiness()
                    is MainIntent.Category -> category(it.name, it.tag)
                    is MainIntent.GetCategory -> get_Category()
                    is MainIntent.BusinessCategory -> businessCategory(it.s_id, it.category)
                    is MainIntent.AllBusinessList -> allBusinessList()
                    is MainIntent.GetTags -> getTags()
                    is MainIntent.UpdateDevice -> updateDevice(
                        it.id!!,
                        it.token!!
                    )
                    is MainIntent.ClaimBusinessList -> claimBusinessList()
                    is MainIntent.CliamDetail -> cliamDetail(it.s_id)
                    is MainIntent.States -> states(it.country_id)
                    is MainIntent.GetCountries -> get_countrries()
                    is MainIntent.UserApprovedBusiness -> userApprovedBusiness()
                    is MainIntent.EditClaimedBusiness -> editClaimedBusiness(it.id)
                    is MainIntent.ApproveClaimedBusiness -> approveClaimedBusiness(it.approveClaimBusiReq)
                    is MainIntent.ConfirmList->confirmList(it.confirmListReq)
                    is MainIntent.BlockedList->blockedList(it.blockedListReq)
                    is MainIntent.BlockeUser->blockedUser(it.blockUserReq)
                    is MainIntent.ConfirmUser->confirmUser(it.confirmUserReq)
                    else -> {}
                }
            }
        }


    }

    private fun confirmUser(confirmUserReq: ConfirmUserReq) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading


            val response = repository.confirmUser(confirmUserReq)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.ConfirmUser(response.body)
                    } else {
                        MainState.Error(response.body.status)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun blockedUser(blockUserReq: BlockUserReq) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading


            val response = repository.blockedUser(blockUserReq)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.BlockedUser(response.body)
                    } else {
                        MainState.Error(response.body.status)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun blockedList(blockedListReq: BlockedListReq) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading


            val response = repository.blockedList(blockedListReq)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.BlockedList(response.body)
                    } else {
                        MainState.Error(response.body.status)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun confirmList(confirmListReq: ConfirmListReq) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading


            val response = repository.confirmList(confirmListReq)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.ConfirmList(response.body)
                    } else {
                        MainState.Error(response.body.status)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun get_countrries() {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading


            val response = repository.get_countries()
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.GetCounteries(response.body)
                    } else {
                        MainState.Error(response.body.status)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }
    private fun userApprovedBusiness() {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading


            val response = repository.userApprovedBusiness()
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.UserApprovedBusiness(response.body)
                    } else {
                        MainState.Error(response.body.status)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun states(countryId: String) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val req = StatesReq(countryId!!)
            val response = repository.states(req)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.States(response.body)
                    } else {
                        MainState.Error(response.body.status)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun approveClaimedBusiness(approveClaimBusiReq: ApproveClaimBusiReq) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading


            val response = repository.approveClaimedBusiness(approveClaimBusiReq)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.ApproveClaimedBusiness(response.body)
                    } else {
                        MainState.Error(response.body.status)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun cliamDetail(sId: String?) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val req = CliamDetailReq(sId!!)
            val response = repository.cliamDetail(req)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.CliamDetail(response.body)
                    } else {
                        MainState.Error(response.body.status)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun editClaimedBusiness(id: String?) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading


            val response = repository.editClaimedBusiness(id)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.EditClaimedBusiness(response.body)
                    } else {
                        MainState.Error(response.body.status)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun claimBusinessList() {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val response = repository.claimBusinessList()
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.ClaimBusinesssList(response.body)
                    } else {
                        MainState.Error(response.body.status)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun updateDevice(id: String, token: String) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val req = UpdateDeviceReq(id, token)
            val response = repository.updatedevice(req)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.Updatedevice(response.body)
                    } else {
                        MainState.Error(response.body.message)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun getTags() {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val response = repository.get_Tags()
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.Get_Tags(response.body)
                    } else {
                        MainState.Error(response.body.status)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun allBusinessList() {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val response = repository.all_business()
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.All_business(response.body)
                    } else {
                        MainState.Error(response.body.status)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun businessCategory(sId: String, category: String) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val req = BusinessCategoryReq(sId, category)
            val response = repository.business_Category(req)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.business_Category(response.body)
                    } else {
                        MainState.Error(response.body.msg)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun get_Category() {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val response = repository.get_category()
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.Get_Category(response.body)
                    } else {
                        MainState.Error(response.body.status)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun category(name: String, tag: String) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val req = CategoryReq(name, tag)
            val response = repository.category(req)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.Category(response.body)
                    } else {
                        MainState.Error(response.body.status)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun recommendBusiness() {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val response = repository.recommend_Business()
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.RecommendBusiness(response.body)
                    } else {
                        MainState.Error(response.body.status)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun fetchBusinessList(adminId: String) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val req = BusinessListReq(adminId)
            val response = repository.getBusinessList(req)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.BusinessList(response.body)
                    } else {
                        MainState.Error(response.body.msg)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun fetchRegister(
        sEmail: String,
        sPhoneCode: String,
        sPhone: String,
        sBusiness: String,
        sPassword: String,
        sUsername: String,
        sName: String,
        sLatit: String,
        sLongit: String,
        sType: String,
        sAddress: String,
        adminId: String,
        timezone: String,
        zone: String
    ) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val req = RegisterReq(
                sEmail, sPhoneCode, sPhone, sBusiness, sPassword, sUsername, sName, sLatit, sLongit,
                sType, sAddress, adminId, timezone, zone
            )
            val response = repository.getRegister(req)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.Register(response.body)
                    } else {
                        MainState.Error(response.body.msg)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun fetchTypeList() {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val response = repository.getTypeList()
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.TypeList(response.body)
                    } else {
                        MainState.Error(response.body.status)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun fetchCliamBusiness(adminId: String) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val req = CliamBusinessReq(adminId)
            val response = repository.getCliamBusiness(req)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.CliamBusiness(response.body)
                    } else {
                        MainState.Error(response.body.msg)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun fetchSubAdminAdsReject(subadminId: Int) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val req = RejectAdsReq(subadminId)
            val response = repository.getSubAdminAdsReject(req)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.SubAdminAdsReject(response.body)
                    } else {
                        MainState.Error(response.body.msg)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun fetchSubAdminAdsAccept(subadminId: Int) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val req = AcceptAdsReq(subadminId)
            val response = repository.getSubAdminAdsAccept(req)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.SubAdminAdsAccept(response.body)
                    } else {
                        MainState.Error(response.body.msg)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }


    }

    private fun fetchAdminAdsReject(adminId: String, id: String) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val req = RejectReq(adminId, id)
            val response = repository.getAdminAdsReject(req)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.AdminAdsReject(response.body)
                    } else {
                        MainState.Error(response.body.msg)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }


    }

    private fun fetchAdminAdsAccept(adminId: String, id: String) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val req = AcceptReq(adminId, id)
            val response = repository.getAdminAdsAccept(req)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.AdminAdsAccept(response.body)
                    } else {
                        MainState.Error(response.body.msg)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun fetchAdsPending(adminId: String, id: String) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val req = AdsPendingReq(adminId, id)
            val response = repository.getAdsPending(req)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.AdsPending(response.body)
                    } else {
                        MainState.Error(response.body.msg)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }
    }

    private fun fetchSubAdminInfo(subadminId: Int) {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val req = SubAdminInfoReq(subadminId)
            val response = repository.getSubAdminInfo(req)
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.SubAdminInfo(response.body)
                    } else {
                        MainState.Error(response.body.msg)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

    private fun fetchAdminUser() {
        viewModelScope.launch {
            //loading state
            _state.value = MainState.Loading

            val response = repository.getAdminUser()
            _state.value = when (response) {
                is NetworkResponse.Success -> {
                    if (response.body.status == "ok") {
                        MainState.AdminUser(response.body)
                    } else {
                        MainState.Error(response.body.msg)
                    }
                }
                is NetworkResponse.ApiError -> {
                    MainState.Error(response.body.error)
                }
                is NetworkResponse.NetworkError -> {
                    MainState.Error(response.error.message)
                }
                is NetworkResponse.UnknownError -> {
                    MainState.Error(response.error?.message)
                }
            }
        }

    }

}