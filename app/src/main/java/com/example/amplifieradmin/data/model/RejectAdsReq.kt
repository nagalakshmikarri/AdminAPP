package com.example.amplifieradmin.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RejectAdsReq (
        @SerializedName("subadmin_id")
        @Expose
        val subadmin_id:Int

)
