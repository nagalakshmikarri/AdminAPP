package com.example.amplifieradmin.data.model

data class RegisterResp(
    val api: String,
    val `data`: Data,
    val errors: List<RegisterData>,
    val msg: String,
    val next: String,
    val status: String
)
data class RegisterData(
    val admin_id: String,
    val s_address: String,
    val s_business: String,
    val s_created: String,
    val s_email: String,
    val s_id: Int,
    val s_ip: String,
    val s_latit: String,
    val s_longit: String,
    val s_name: String,
    val s_pass: String,
    val s_phone: String,
    val s_phone_code: String,
    val s_status: Int,
    val s_type: String,
    val s_username: String,
    val s_verified: Int,
    val timezone: String,
    val zone: String
)