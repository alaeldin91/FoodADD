package com.example.foodapp.pojo


import com.google.gson.annotations.SerializedName

data class RegisterModel(
    @SerializedName("response")
    val response: String,
    @SerializedName("result_code")
    val resultCode: Int
)