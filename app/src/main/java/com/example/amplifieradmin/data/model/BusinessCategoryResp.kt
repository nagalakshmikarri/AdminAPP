package com.example.amplifieradmin.data.model

data class BusinessCategoryResp(
    val api: String,
    val `data`: String,
    val errors: List<Any>,
    val msg: String,
    val next: String,
    val status: String
)