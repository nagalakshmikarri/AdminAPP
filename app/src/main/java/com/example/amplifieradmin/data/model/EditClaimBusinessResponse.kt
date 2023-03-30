package com.example.amplifieradmin.data.model


import com.google.gson.annotations.SerializedName

data class EditClaimBusinessResponse(
    val list: List,
    val status: String
) {
    data class List(
        val businesscategory: String,
        val businesstype: String,
        val id: String,
        @SerializedName("refer_userid")
        val referUserid: String,
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
        @SerializedName("s_pass")
        val sPass: String,
        @SerializedName("s_phone")
        val sPhone: String,
        @SerializedName("s_phone_code")
        val sPhoneCode: String,
        @SerializedName("s_username")
        val sUsername: String,
        @SerializedName("user_id")
        val userId: String
    )
}