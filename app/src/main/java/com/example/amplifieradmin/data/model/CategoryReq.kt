package com.example.amplifieradmin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CategoryReq(
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("tag")
    @Expose
    val tag: String


)
