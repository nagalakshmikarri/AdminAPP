package com.example.amplifieradmin.data.model

data class GetCitiesResp(
    val api: String,
    val `data`: List<GetCitiesRespData>,
    val msg: String,
    val next: String,
    val status: String
)
data class GetCitiesRespData(
    val s_city: String
)