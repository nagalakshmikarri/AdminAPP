package com.example.amplifieradmin.data.model

data class LoginResp(
    val api: String,
    val `data`: List<Data>,
    val errors: List<Any>,
    val msg: String,
    val next: String,
    val status: String
)
data class Data(
    val admin_id: String,
    val admin_name: String,
        val admin_phone: String
)