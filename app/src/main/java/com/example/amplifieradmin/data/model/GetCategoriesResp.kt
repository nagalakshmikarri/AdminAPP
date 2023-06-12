package com.example.amplifieradmin.data.model

data class GetCategoriesResp(
    val list: List<GetCategoriesRespData>,
    val status: String
)

data class GetCategoriesRespData(
    val id: String,
    val name: String,
    var status: Boolean
)