package com.example.amplifieradmin.data.model

data class AcceptAdsResp(
    val api: String,
    val `data`: List<AcceptAdsData>,
    val msg: String,
    val next: String,
    val status: String
)
data class AcceptAdsData(
    val actiondoneby: String,
    val approved: String,
    val byadmin: String,
    val c_app: String,
    val c_likes: String,
    val c_share: String,
    val c_views: String,
    val catg: String,
    val comment: String,
    val created: String,
    val descript: String,
    val draft: String,
    val edate: String,
    val followers: String,
    val id: String,
    val img: String,
    val lat: String,
    val link: String,
    val link_name: String,
    val link_type: String,
    val long: String,
    val s_business: String,
    val s_phone: String,
    val s_user_img: String,
    val sdate: String,
    val status: String,
    val tag: String,
    val title: String,
    val type: String,
    val user: String
)