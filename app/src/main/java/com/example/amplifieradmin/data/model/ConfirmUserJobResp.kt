package com.example.amplifieradmin.data.model

data class ConfirmUserJobResp(
    val api: String,
    val `data`: List<Any>,
    val msg: String,
    val next: String,
    val status: String
)