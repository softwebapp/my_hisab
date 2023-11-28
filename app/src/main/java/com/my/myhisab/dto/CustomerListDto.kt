package com.my.myhisab.dto


import com.google.gson.annotations.SerializedName

data class CustomerListDto(
    @SerializedName("data")
    val data: ArrayList<Data> = arrayListOf(),
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
) {
    data class Data(
        @SerializedName("contact_number")
        val contactNumber: String?,
        @SerializedName("email")
        val email: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("mature_date")
        val matureDate: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("start_date")
        val startDate: String?,
        @SerializedName("uniqe_id")
        val uniqeId: String?
    )
}