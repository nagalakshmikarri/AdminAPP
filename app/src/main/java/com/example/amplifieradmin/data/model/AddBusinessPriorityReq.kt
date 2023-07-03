package com.example.amplifieradmin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AddBusinessPriorityReq(
    @SerializedName("s_id")
    @Expose
    val s_id: String = "",

    @SerializedName("duration")
    @Expose
    val duration: String = "",

    @SerializedName("start_date")
    @Expose
    val start_date: String = "",

    @SerializedName("amount")
    @Expose
    val amount: String = "",

    @SerializedName("rank")
    @Expose
    val rank: String = "",

    )
