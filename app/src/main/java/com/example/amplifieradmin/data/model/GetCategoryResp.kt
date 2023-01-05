package com.example.amplifieradmin.data.model

data class GetCategoryResp(
    val list: List<GetCategoryRespData>,
    val status: String
)
data class GetCategoryRespData(
    val id: String,
    val name: String
)