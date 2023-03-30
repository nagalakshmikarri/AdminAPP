package com.example.amplifieradmin.data.model


import com.google.gson.annotations.SerializedName

data class ApproveClaimBusinessResp(
    val api: String,
    val `data`: List<Any>,
    val msg: String,
    val next: String,
    val status: String
)