package com.example.foodapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.MealList
import com.example.foodapp.pojo.MealsByCatogry
import com.example.foodapp.pojo.MealsByCatogryList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatogryMealsViewModel : ViewModel() {
    val mealsLiveData = MutableLiveData<List<MealsByCatogry>>()
    fun getMealByCatogry(catogryName: String) {
        RetrofitInstance.api.getPopularItems(catogryName)
            .enqueue(object : Callback<MealList> {
                override fun onResponse(
                    call: Call<MealList>,
                    response: Response<MealList>

                ) {
                    response.body()!!.let {
                        mealsLiveData.postValue(it.meals)
                    }
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                }

            })
    }
    fun observeMealsLiveData():MutableLiveData<List<MealsByCatogry>>{
        return mealsLiveData

    }
}


