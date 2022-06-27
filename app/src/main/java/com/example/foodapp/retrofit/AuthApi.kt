package com.example.foodapp.retrofit

import com.example.foodapp.pojo.LoginModel
import com.example.foodapp.pojo.RegisterModel
import retrofit2.Call
import retrofit2.http.*

interface AuthApi {
    @POST("Register.php")
    @FormUrlEncoded
    fun getRegisters(
        @Field("firstName") firstName: String,
        @Field("secondName") secondName: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("password") password: String
    ):Call<RegisterModel>
    @GET("login.php")
    fun signIn(@Query("tel")phoneNumber: String, @Query("password")password: String):Call<LoginModel>
}