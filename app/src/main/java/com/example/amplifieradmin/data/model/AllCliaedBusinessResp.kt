package com.example.amplifieradmin.data.model

data class AllCliaedBusinessResp(
    val api: String,
    val `data`: List<AllCliaedBusinessRespData>,
    val msg: String,
    val next: String,
    val status: String
)

data class AllCliaedBusinessRespData(
    val businesscategory: String,
    val s_address: String,
    val s_address1: String,
    val s_address2: String,
    val s_address3: String,
    val s_business: String,
    val s_cat: String,
    val s_created: String,
    val s_email: String,
    val s_id: String,
    val s_latit: String,
    val s_longit: String,
    val s_name: String,
    val s_phone: String,
    val s_phone_code: String
)