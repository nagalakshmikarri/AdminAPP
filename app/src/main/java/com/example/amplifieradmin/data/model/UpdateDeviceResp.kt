package com.example.amplifieradmin.data.model


import com.google.gson.annotations.SerializedName

data class UpdateDeviceResp(
    val api: String,
    val errors: List<Any>,
    val message: String,
    val status: String
)