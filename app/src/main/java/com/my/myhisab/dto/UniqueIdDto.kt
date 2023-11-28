package com.my.myhisab.dto


import com.google.gson.annotations.SerializedName

data class UniqueIdDto(
    @SerializedName("data")
    val `data`: ArrayList<Data> = arrayListOf(),
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
) {
    data class Data(
        @SerializedName("contactnumber")
        val contactnumber: String?,
        @SerializedName("email")
        val email: String?,
        @SerializedName("end_date")
        val endDate: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("label")
        val label: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("per_month")
        val perMonth: String?,
        @SerializedName("start_date")
        val startDate: String?,
        @SerializedName("trn_amount")
        val trnAmount: String?,
        @SerializedName("trn_date")
        val trnDate: String?,
        @SerializedName("value")
        val value: String?
    )
}