package com.example.amplifieradmin.data.model

import com.google.gson.annotations.SerializedName

data class StatesResp(
    @SerializedName("list")
    val stateList: List<StatesData>,
    val status: String
)
data class StatesData(
    val cid: String,
    val id: String,
    val name: String
)