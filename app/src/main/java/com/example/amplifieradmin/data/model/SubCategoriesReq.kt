package com.example.amplifieradmin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SubCategoriesReq(
    @SerializedName("subtype_id")
    @Expose
    val subtype_id:String,
  @SerializedName("cat_id")
    @Expose
    val cat_id:String,

)
