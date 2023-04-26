package com.example.amplifieradmin.data.model

data class ConfirmedJobsResp(
    val api: String,
    val `data`: List<ConfirmedJobsRespData>,
    val msg: String,
    val next: String,
    val status: String
)
data class ConfirmedJobsRespData(
    val address: String,
    val block: String,
    val confirm: String,
    val description: String,
    val designation: String,
    val id: String,
    val image: String,
    val lat: String,
    val lng: String,
    val name: String,
    val phone: String,
    val title: String,
    val todate: String,
    val type: String,
    val user_id: String,
    val user_img: String
)