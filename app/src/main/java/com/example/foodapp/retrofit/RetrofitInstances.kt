package com.example.foodapp.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstances {
        companion object{
            val BASE_URL:String="https://haji91.000webhostapp.com/Revision/"

            fun getRetrofitInstance():Retrofit{
                val gson =GsonBuilder().setLenient().create()
                return Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build()
            }

        }
}