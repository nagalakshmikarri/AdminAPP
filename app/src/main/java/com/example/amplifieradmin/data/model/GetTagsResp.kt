package com.example.amplifieradmin.data.model

data class GetTagsResp(
    val api: String,
    val `data`: List<GetTagsData>,
    val errors: List<Any>,
    val msg: String,
    val next: String,
    val status: String
)
data class GetTagsData(
    val id: String,
    val img: String,
    val title: String
)