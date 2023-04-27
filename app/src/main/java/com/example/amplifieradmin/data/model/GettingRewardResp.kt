package com.example.amplifieradmin.data.model

data class GettingRewardResp(
    val api: String,
    val `data`: GettingRewardRespData,
    val errors: List<Any>,
    val msg: String,
    val next: String,
    val status: String
)
data class GettingRewardRespData(
    val friend_accept: String,
    val home_screen_view: String,
    val id: String,
    val register: String
)