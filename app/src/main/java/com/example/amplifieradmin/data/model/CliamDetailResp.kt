package com.example.amplifieradmin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class CliamDetailResp(
    val list: List<CliamDetailRespData>,
    val status: String
)

@Parcelize
data class CliamDetailRespData(
    val id: String?=null,
    val refer_userid: String?=null,
    val s_business: String?=null,
    val s_email: String?=null,
    val s_id: String?=null,
    val s_name: String?=null,
    val s_pass: String?=null,
    val s_phone: String?=null,
    val s_phone_code: String?=null,
    val s_username: String?=null,
    val user_id: String?=null
) : Parcelable