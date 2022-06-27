package com.example.foodapp.activites

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.fragment.HomeFragment
import com.example.foodapp.pojo.MealsByCatogry
import com.example.foodapp.viewModel.MealViewModel
import kotlinx.android.synthetic.main.activity_meal.*

class MealActivity : AppCompatActivity() {
    private lateinit var mealId: String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var mealMvvm: MealViewModel
    private lateinit var binding: ActivityMealBinding
    private lateinit var string_uri: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealMvvm = ViewModelProviders.of(this)[MealViewModel::class.java]
        loadCase()
        getMealInformationFromIntent()
        setInformationViews()
        loadCase()
        mealMvvm.getMealDetails(mealId)
        observerMealDetailsList()
        youtube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(string_uri))
            startActivity(intent)
        }


    }

    private fun observerMealDetailsList() {
        mealMvvm.observerMealDetailsLiveData().observe(this, object : Observer<MealsByCatogry> {
            override fun onChanged(t: MealsByCatogry?) {
                onResponse()
                val meal = t

                binding.tvCatogry.text = "Catogry : ${meal!!.strCategory}"
                binding.tvArea.text = "Area : ${meal.strArea}"
                binding.tvInstructor.text = meal.strInstructions
                string_uri = meal.strYoutube.toString()
            }

        })
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!


    }

    private fun setInformationViews() {
        Glide.with(applicationContext).load(mealThumb).into(binding.imgMealDetails)
        binding.CollapsingToolbar.title = mealName
    }

    private fun loadCase() {
        binding.btnFloat.visibility = View.INVISIBLE
        binding.progress.visibility = View.VISIBLE
        binding.tvInstructor.visibility = View.INVISIBLE
        binding.tvCatogry.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE

    }

    private fun onResponse() {
        binding.btnFloat.visibility = View.VISIBLE
        binding.progress.visibility = View.INVISIBLE
        binding.tvInstructor.visibility = View.VISIBLE
        binding.tvCatogry.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE

    }
}