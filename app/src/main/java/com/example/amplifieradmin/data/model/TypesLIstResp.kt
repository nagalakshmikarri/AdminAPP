package com.example.amplifieradmin.data.model

data class TypesLIstResp(
    val list: List<TypeListData>,
    val status: String
)
data class TypeListData(
    val typ_id: String,
    val typ_title: String
)