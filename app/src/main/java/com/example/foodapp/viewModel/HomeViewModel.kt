package com.example.foodapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.*
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private var randomMealLiveData = MutableLiveData<MealsByCatogry>()
    private var popularItemsLiveData = MutableLiveData<List<CatogryMeat>>()
    private var catogriesLiveData = MutableLiveData<List<Category>>()
    fun getRandomMeal() {
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: MealsByCatogry = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home Fragment is ", t.message.toString())
            }

        })
    }
    fun getCatogries(){
        RetrofitInstance.api.getCatogries().enqueue(object :Callback<CatogryList>{
            override fun onResponse(call: Call<CatogryList>, response: Response<CatogryList>) {
        response.body()?.let {catogryList ->
        catogriesLiveData.postValue(catogryList.categories)

        }
            }

            override fun onFailure(call: Call<CatogryList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
    fun getPopularItems(){
        RetrofitInstance.api.getPopularItemss("Seafood").enqueue(object : Callback<MealsByCatogryList>{
            override fun onResponse(call: Call<MealsByCatogryList>, response: Response<MealsByCatogryList>) {
                if (response.body() !=null){
                    popularItemsLiveData.value = response.body()!!.meals

                }

            }

            override fun onFailure(call: Call<MealsByCatogryList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun obServeRandomMealLiveData(): LiveData<MealsByCatogry> {
        return randomMealLiveData

    }
    fun observePopularItemsLiveData(): MutableLiveData<List<CatogryMeat>> {
        return popularItemsLiveData

    }
    fun observeCatogriesLiveData():LiveData<List<Category>>{
        return catogriesLiveData

    }
}