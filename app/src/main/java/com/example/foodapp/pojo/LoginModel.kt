package com.example.foodapp.pojo


import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("response")
    val response: String,
    @SerializedName("userId")
    val userId: String
)