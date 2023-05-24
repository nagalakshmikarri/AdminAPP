package com.example.amplifieradmin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddSubTypeInviteReq(
    @SerializedName("type")
    @Expose
    val type:String="",
    @SerializedName("type_id")
    @Expose
    val type_id:String="",

)
