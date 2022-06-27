package com.example.foodapp.pojo


import com.google.gson.annotations.SerializedName

data class MealsByCatogryList(
    @SerializedName("meals")
    val meals: List<CatogryMeat>
)