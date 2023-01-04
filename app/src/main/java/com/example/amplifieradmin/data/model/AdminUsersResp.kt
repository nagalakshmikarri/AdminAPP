package com.example.amplifieradmin.data.model

data class AdminUsersResp(
    val api: String,
    val `data`: List<AdminUsersData>,
    val msg: String,
    val next: String,
    val status: String
)
data class AdminUsersData(
    val acceptcount: Int,
    val admin_id: String,
    val admin_name: String,
    val admin_password: String,
    val admin_phone: String,
    val admin_status: String,
    val notification: String,
    val rejectcount: Int
)