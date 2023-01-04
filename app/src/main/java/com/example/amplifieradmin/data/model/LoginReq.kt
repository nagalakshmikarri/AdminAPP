package com.example.amplifieradmin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import retrofit2.http.Field

data class LoginReq(
    @SerializedName("username")
    @Expose
    val username: String = "",
    @SerializedName("password")
    @Expose
    val password: String = ""
)