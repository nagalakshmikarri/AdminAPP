package com.example.amplifieradmin.ui.main.intent

sealed class MainIntent {
    class LoginUser(var username: String, var password: String) : MainIntent()

    object AdminUser : MainIntent()

    class SubAdminInfo(var subadmin_id:Int):MainIntent()

    class AdsPending(var admin_id: String, var id: String) : MainIntent()

    class AdminAdsAccept(var admin_id: String, var id: String) : MainIntent()

    class AdminAdsReject(var admin_id: String, var id: String) : MainIntent()

    class SubAdminAdsAccept(var subadmin_id: Int):MainIntent()

    class SubAdminAdsReject(var subadmin_id: Int):MainIntent()

    class CliamBusiness(var admin_id: String):MainIntent()

    class TypeList():MainIntent()

    class Register(
        var s_email: String, var s_phone_code: String,
        var s_phone: String, var s_business: String,
        var s_password: String, var s_username: String,
        var s_name: String, var s_latit: String,
        var s_longit: String,var s_type: String,
        var s_address:String,var admin_id: String,
        var timezone: String,var zone:String):MainIntent()

    class BusinessList(var admin_id: String):MainIntent()

    object RecommendBusiness:MainIntent()

    class Category(var name:String,var tag:String):MainIntent()

    object GetCategory:MainIntent()

    class BusinessCategory(
        var s_id:String,var category:String):MainIntent()

    object AllBusinessList:MainIntent()

    object GetTags:MainIntent()

    class UpdateDevice(
        var id: String?, var token: String?,
    ) : MainIntent()

    object ClaimBusinessList:MainIntent()

    class CliamDetail(
        var s_id: String?
    ):MainIntent()

    class States(
        var country_id: String
    ) : MainIntent()
    class GetCountries() : MainIntent()

}