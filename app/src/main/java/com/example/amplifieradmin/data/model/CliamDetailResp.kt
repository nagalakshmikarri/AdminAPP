package com.example.amplifieradmin.data.model

data class CliamDetailResp(
    val list: List<CliamDetailRespData>,
    val status: String
)

data class CliamDetailRespData(
    val id: String,
    val refer_userid: String,
    val s_business: String,
    val s_email: String,
    val s_id: String,
    val s_name: Any,
    val s_pass: String,
    val s_phone: String,
    val s_phone_code: String,
    val s_username: String,
    val user_id: String
)