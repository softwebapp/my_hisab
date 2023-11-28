package com.my.myhisab.dto


import com.google.gson.annotations.SerializedName

data class TransctionAllHistoryDto(
    @SerializedName("data")
    val data: ArrayList<Data> = arrayListOf(),
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
) {
    data class Data(
        @SerializedName("amount")
        val amount: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("remark")
        val remark: String?,
        @SerializedName("tr_date")
        val trDate: String?,
        @SerializedName("uniqe_id")
        val uniqeId: String?
    )
}