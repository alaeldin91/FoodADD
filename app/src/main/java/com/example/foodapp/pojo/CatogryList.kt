package com.example.foodapp.pojo


import com.google.gson.annotations.SerializedName

data class CatogryList(
    @SerializedName("categories")
    val categories: List<Category>
)