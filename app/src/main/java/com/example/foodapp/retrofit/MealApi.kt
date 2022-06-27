package com.example.foodapp.retrofit

import com.example.foodapp.pojo.CatogryList
import com.example.foodapp.pojo.MealsByCatogryList
import com.example.foodapp.pojo.MealList
import com.example.foodapp.pojo.RegisterModel
import retrofit2.Call
import retrofit2.http.*

interface MealApi {
    @POST("Register.php")
    @FormUrlEncoded
    fun registers(
        @Field("firstName") firstName: String,
        @Field("secondName") secondName: String,
        @Field("phoneNumber") phoneNumber: String
    ):Call<RegisterModel>

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php?")
    fun getDetailsMeal(@Query("i") id: String): Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("c") catogry: String): Call<MealList>

    @GET("categories.php")
    fun getCatogries(): Call<CatogryList>
    @GET("filter.php")
    fun getPopularItemss(@Query("c") catogry: String): Call<MealsByCatogryList>



}