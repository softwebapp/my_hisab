package com.my.myhisab.dto


import com.google.gson.annotations.SerializedName

data class DashboardDto(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("first_half")
    val firstHalf: FirstHalf?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("recent_transaction")
    val recentTransaction: List<RecentTransaction?>?,
    @SerializedName("second_half")
    val secondHalf: SecondHalf?,
    @SerializedName("status")
    val status: String?
) {
    data class Data(
        @SerializedName("toal_customer")
        val toalCustomer: String?,
        @SerializedName("toal_lic")
        val toalLic: String?,
        @SerializedName("toal_rd")
        val toalRd: String?
    )

    data class FirstHalf(
        @SerializedName("due_amount")
        val dueAmount: String?,
        @SerializedName("total_account")
        val totalAccount: String?,
        @SerializedName("total_amount")
        val totalAmount: String?
    )

    data class RecentTransaction(
        @SerializedName("amount")
        val amount: String?,
        @SerializedName("tr_date")
        val trDate: String?,
        @SerializedName("uniqe_id")
        val uniqeId: String?
    )

    data class SecondHalf(
        @SerializedName("due_amount")
        val dueAmount: String?,
        @SerializedName("total_account")
        val totalAccount: String?,
        @SerializedName("total_amount")
        val totalAmount: String?
    )
}