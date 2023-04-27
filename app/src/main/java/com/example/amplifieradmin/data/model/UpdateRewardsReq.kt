package com.example.amplifieradmin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UpdateRewardsReq(
    @SerializedName("register")
    @Expose
    val register: String = "",
    @SerializedName("home_screen_view")
    @Expose
    val home_screen_view: String = "",
    @SerializedName("friend_accept")
    @Expose
    val friend_accept: String = "",
)
