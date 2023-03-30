package com.example.amplifieradmin.data.model

data class GetCountriesResp(
    val list: List<GetCountriesData>,
    val status: String
)
data class GetCountriesData(
    val currency: String,
    val id: String,
    val name: String
)