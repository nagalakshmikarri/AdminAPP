package com.example.amplifieradmin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddInviteTypeReq(
    @SerializedName("type")
    @Expose
    val type:String=""
)
