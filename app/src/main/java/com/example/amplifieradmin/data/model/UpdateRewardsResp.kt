package com.example.amplifieradmin.data.model

data class UpdateRewardsResp(
    val api: String,
    val `data`: List<Any>,
    val errors: List<Any>,
    val msg: String,
    val next: String,
    val status: String
)