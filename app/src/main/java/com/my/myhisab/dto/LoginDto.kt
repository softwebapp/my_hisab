package com.my.myhisab.dto


import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("data")
    val data: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
) {
    data class Data(
        @SerializedName("email")
        val email: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("type")
        val type: String?,
        @SerializedName("username")
        val username: String?
    )
}