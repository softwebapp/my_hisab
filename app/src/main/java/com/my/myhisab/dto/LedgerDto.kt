package com.my.myhisab.dto


import com.google.gson.annotations.SerializedName

data class LedgerDto(
    @SerializedName("data")
    val `data`: ArrayList<Data>,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
) {
    data class Data(
        @SerializedName("amount")
        val amount: String?,
        @SerializedName("deposit_amount")
        val depositAmount: String?,
        @SerializedName("from_date")
        val fromDate: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("remaining_amount")
        val remainingAmount: String?,
        @SerializedName("uniqe_id")
        val uniqeId: String?
    )
}