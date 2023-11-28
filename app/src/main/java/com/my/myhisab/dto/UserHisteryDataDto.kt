package com.my.myhisab.dto


import com.google.gson.annotations.SerializedName

data class UserHisteryDataDto(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
) {
    data class Data(
        @SerializedName("email")
        val email: String?,
        @SerializedName("from_date")
        val fromDate: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("to_date")
        val toDate: String?,
        @SerializedName("total_ledger_amount")
        val totalLedgerAmount: String?,
        @SerializedName("total_postoffice_deposit")
        val totalPostofficeDeposit: String?,
        @SerializedName("total_remaining_amount_from_the_customer")
        val totalRemainingAmountFromTheCustomer: Int?,
        @SerializedName("total_transaction_from_customer_side")
        val totalTransactionFromCustomerSide: String?,
        @SerializedName("type")
        val type: String?,
        @SerializedName("uniqe_id")
        val uniqeId: String?,
        @SerializedName("username")
        val username: String?
    )
}