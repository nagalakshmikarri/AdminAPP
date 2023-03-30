package com.example.amplifieradmin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class StatesReq(
    @SerializedName("country_id")
    @Expose
    val country_id: String = ""
)
