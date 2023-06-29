package com.example.amplifieradmin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AllCliamedBusinessReq(
    @SerializedName("city")
    @Expose
    val city:String="",

    )
