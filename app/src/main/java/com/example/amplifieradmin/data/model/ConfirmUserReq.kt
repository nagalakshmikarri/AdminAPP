package com.example.amplifieradmin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ConfirmUserReq(
    @SerializedName("s_id")
    @Expose
    val s_id:String=""

)
