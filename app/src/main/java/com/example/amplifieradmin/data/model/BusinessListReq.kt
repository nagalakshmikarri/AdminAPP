package com.example.amplifieradmin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BusinessListReq(
    @SerializedName("admin_id")
    @Expose
    val admin_id: String

)
