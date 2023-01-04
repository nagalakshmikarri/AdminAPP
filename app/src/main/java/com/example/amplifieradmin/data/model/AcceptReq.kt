package com.example.amplifieradmin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AcceptReq(
    @SerializedName("admin_id")
    @Expose
    val admin_id:String="",
    @SerializedName("id")
    @Expose
    val id:String=""
)
