package com.example.amplifieradmin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApproveClaimBusiReq(
    val s_id: String? = "" ,
    val id: String? = "" ,
    val s_email: String? = "",
    val country: String? = "",
    val s_address1: String? = "",
    val s_address2: String? = "",
    val s_address3: String? = "",
    val s_latit: String? = "",
    val s_longit: String? = "",
    val s_type: String? = "",
    val admin_id: String? = "",
    val s_phone: String? = "",
    val s_phone_code: String? = "",
    val s_categoty: String? = "",
    val state: String? = "",
    val city: String? = "",
    val s_business: String? = "",
    val s_username: String? = "",
)
