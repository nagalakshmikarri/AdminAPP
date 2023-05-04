package com.example.amplifieradmin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class GettingRewardResp(
    val api: String,
    val `data`: GettingRewardRespData,
    val errors: List<Any>,
    val msg: String,
    val next: String,
    val status: String
)
@Parcelize
data class GettingRewardRespData(
    val friend_accept: String,
    val home_screen_view: String,
    val id: String,
    val register: String
): Parcelable {
}