package com.example.amplifieradmin.data.model

data class EditSubTypeCategoryResp(
    val api: String,
    val `data`: EditSubTypeCategoryRespData,
    val errors: List<Any>,
    val msg: String,
    val next: String,
    val status: String
)
data class EditSubTypeCategoryRespData(
    val categories: String
)