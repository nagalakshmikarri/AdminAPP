package com.example.amplifieradmin.data.model

data class SubAdminInfoResp(
    val accepted: Int,
    val api: String,
    val `data`: Data,
    val msg: String,
    val next: String,
    val pendings: Int,
    val rejected: Int,
    val status: String
)
data class AdminData(
    val admin_id: String,
    val admin_name: String,
    val admin_password: String,
    val admin_phone: String,
    val admin_status: String,
    val notification: String
)