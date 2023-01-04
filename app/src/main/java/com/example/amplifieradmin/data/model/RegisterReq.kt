package com.example.amplifieradmin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterReq(
    @SerializedName("s_email")
    @Expose
    val s_email: String = "",

    @SerializedName("s_phone_code")
    @Expose
    val s_phone_code: String = "",

    @SerializedName("s_phone")
    @Expose
    val s_phone: String = "",

    @SerializedName("s_business")
    @Expose
    val s_business: String = "",

    @SerializedName("s_password")
    @Expose
    val s_password: String = "",

    @SerializedName("s_username")
    @Expose
    val s_username: String = "",

    @SerializedName("s_name")
    @Expose
    val s_name: String = "",

    @SerializedName("s_latit")
    @Expose
    val s_latit: String = "",


    @SerializedName("s_longit")
    @Expose
    val s_longit: String = "" ,

    @SerializedName("s_type")
    @Expose
    val s_type: String = "",

    @SerializedName("s_address")
    @Expose
    val s_address: String = "",
    @SerializedName("admin_id")
    @Expose
    val admin_id:String="",
    @SerializedName("timezone")
    @Expose
    val timezone:String="",
    @SerializedName("zone")
    @Expose
    val zone:String="",
)
