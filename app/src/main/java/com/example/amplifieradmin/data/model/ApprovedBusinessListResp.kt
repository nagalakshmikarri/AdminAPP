package com.example.amplifieradmin.data.model


import com.google.gson.annotations.SerializedName

data class ApprovedBusinessListResp(
    val api: String,
    val `data`: List<Data>,
    val msg: String,
    val next: String,
    val status: String
) {
    data class Data(
        val businesscategory: String,
        @SerializedName("s_address")
        val sAddress: String,
        @SerializedName("s_address1")
        val sAddress1: String,
        @SerializedName("s_address2")
        val sAddress2: String,
        @SerializedName("s_address3")
        val sAddress3: String,
        @SerializedName("s_business")
        val sBusiness: String,
        @SerializedName("s_cat")
        val sCat: String,
        @SerializedName("s_created")
        val sCreated: String,
        @SerializedName("s_email")
        val sEmail: String,
        @SerializedName("s_id")
        val sId: String,
        @SerializedName("s_latit")
        val sLatit: String,
        @SerializedName("s_longit")
        val sLongit: String,
        @SerializedName("s_name")
        val sName: String,
        @SerializedName("s_phone")
        val sPhone: String,
        @SerializedName("s_phone_code")
        val sPhoneCode: String
    )
}