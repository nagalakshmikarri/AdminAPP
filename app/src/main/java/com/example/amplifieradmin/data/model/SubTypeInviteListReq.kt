package com.example.amplifieradmin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SubTypeInviteListReq(
    @SerializedName("type_id")
    @Expose
    val type_id: String = ""
)
