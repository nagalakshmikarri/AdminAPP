package com.example.amplifieradmin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class BlockedListResp(
    val api: String,
    val `data`: List<BlockedListRespData>,
    val msg: String,
    val next: String,
    val status: String
)
@Parcelize
data class BlockedListRespData(
    val admin_id: String,
    val block: String,
    val businesscategory: String,
    val byuser: String,
    val city: String,
    val claim: String,
    val community: String,
    val confirm: String,
    val country: String,
    val created: String,
    val currency: String,
    val hide: String,
    val locatedin: String,
    val miles: String,
    val `public`: String,
    val s_address: String,
    val s_address1: String,
    val s_address2: String,
    val s_address3: String,
    val s_business: String,
    val s_cat: String,
    val s_category: String,
    val s_city: String,
    val s_close: String,
    val s_country: String,
    val s_created: String,
    val s_deleteacc: String,
    val s_desc: String,
    val s_email: String,
    val s_id: String,
    val s_ip: String,
    val s_latit: String,
    val s_link: String,
    val s_link_fb: String,
    val s_link_insta: String,
    val s_link_twitter: String,
    val s_link_url: String,
    val s_longit: String,
    val s_name: String,
    val s_open: String,
    val s_otp1: String,
    val s_pass: String,
    val s_phone: String,
    val s_phone_code: String,
    val s_state: String,
    val s_status: String,
    val s_time: String,
    val s_times: String,
    val s_token: String,
    val s_type: String,
    val s_user_img: String,
    val s_username: String,
    val s_verified: String,
    val state: String,
    val tag: String,
    val timezone: String,
    val weight: String,
    val zone: String,
    val listing_type: String,
): Parcelable