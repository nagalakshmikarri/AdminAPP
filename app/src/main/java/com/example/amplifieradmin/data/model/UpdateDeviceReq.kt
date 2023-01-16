package com.example.amplifieradmin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UpdateDeviceReq(
    @SerializedName("id")
    @Expose
    val id: String = "",
    @SerializedName("token")
    @Expose
    val token: String = ""
)
