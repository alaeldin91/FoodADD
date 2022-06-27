package com.example.foodapp.pojo


import com.google.gson.annotations.SerializedName

data class CatogryMeat(@SerializedName("idMeal")
    val idMeal: String,
    @SerializedName("strMeal")
    val strMeal: String,
    @SerializedName("strMealThumb")
    val strMealThumb: String
)