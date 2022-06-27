package com.example.foodapp.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.R
import com.example.foodapp.adapter.CatogryMealsAdapter
import com.example.foodapp.databinding.ActivityCatogryMealsBinding
import com.example.foodapp.fragment.HomeFragment
import com.example.foodapp.viewModel.CatogryMealsViewModel

class CatogryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCatogryMealsBinding
    lateinit var catogryMealsAdapter: CatogryMealsAdapter
    private lateinit var catogryMealsViewModel: CatogryMealsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_catogry_meals)
        binding = ActivityCatogryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRecycleView()
        catogryMealsViewModel = ViewModelProviders.of(this)[CatogryMealsViewModel::class.java]
        catogryMealsViewModel.getMealByCatogry(intent.getStringExtra(HomeFragment.CATOGRY_NAME)!!)
        catogryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealslist ->
            mealslist.forEach {
                Log.d("test", it.strMeal.toString())
                catogryMealsAdapter.setMealsList(mealslist)
            }

        })

    }

    private fun prepareRecycleView() {
        catogryMealsAdapter = CatogryMealsAdapter()
        binding.reMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = catogryMealsAdapter
        }
    }
}