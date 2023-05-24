package com.example.amplifieradmin.data.model

data class ListInviteTypeResp(
    val list: List<ListInviteTypeRespData>,
    val status: String
)

data class ListInviteTypeRespData(
    val id: String,
    val type: String
)