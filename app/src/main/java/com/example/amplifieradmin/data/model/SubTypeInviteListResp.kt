package com.example.amplifieradmin.data.model

data class SubTypeInviteListResp(
    val list: List<SubTypeInviteListRespData>,
    val status: String
)
data class SubTypeInviteListRespData(
    val categories: String,
    val id: String,
    val status: String,
    val type: String,
    val type_id: String
)