package com.my.myhisab.dto


import com.google.gson.annotations.SerializedName

data class AllTransactionDto(
    @SerializedName("data")
    val `data`: ArrayList<Data> = arrayListOf(),
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
) {
    data class Data(
        @SerializedName("amount")
        val amount: String?,
        @SerializedName("contactnumber")
        val contactnumber: String?,
        @SerializedName("email")
        val email: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("remark")
        val remark: String?,
        @SerializedName("tr_date")
        val trDate: String?,
        @SerializedName("transaction_by")
        val transactionBy: String?,
        @SerializedName("uniqe_id")
        val uniqeId: String?,
        @SerializedName("username")
        val username: String?
    )
}