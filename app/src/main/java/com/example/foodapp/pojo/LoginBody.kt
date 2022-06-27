package com.example.foodapp.pojo

import com.google.gson.annotations.SerializedName

data class LoginBody(
    @SerializedName("tel")
    val phone:String,
    @SerializedName("password")
    val password:String
)
