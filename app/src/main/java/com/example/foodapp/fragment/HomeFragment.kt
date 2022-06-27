package com.example.foodapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.activites.CatogryMealsActivity
import com.example.foodapp.activites.MealActivity
import com.example.foodapp.adapter.CatogriesAdapter
import com.example.foodapp.adapter.MostPopularAdapter
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.pojo.CatogryMeat
import com.example.foodapp.pojo.MealsByCatogry
import com.example.foodapp.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homemvvm: HomeViewModel
    private lateinit var randomMeal: MealsByCatogry
    private lateinit var popularAdapterItems: MostPopularAdapter
    private lateinit var catogriesAdapter: CatogriesAdapter

    companion object {
        const val MEAL_ID = "com.example.foodapp.fragment.idMeal"
        const val MEAL_NAME = "com.example.foodapp.fragment.nameMeal"
        const val MEAL_THUMB = "com.example.foodapp.fragment.thumb"
        const val CATOGRY_NAME = "com.example.foodapp.fragment.catogryName"


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homemvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]
        popularAdapterItems = MostPopularAdapter()
        catogriesAdapter = CatogriesAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preaparePopularItemRecyclerView()
        homemvvm.getRandomMeal()
        observerRandomMeal()
        homemvvm.getPopularItems()
        homemvvm.getCatogries()
        observerCatogriesLiveData()
        ObservePopularItemsLiveData()
        OnRandomMealClickListener()
        OnPopularClickListener()
        prepareRecyclerCatogries()
        onCatogryClick()

    }

    private fun onCatogryClick() {
        catogriesAdapter.onItemClick = { category ->
            val intent = Intent(activity, CatogryMealsActivity::class.java)
            intent.putExtra(CATOGRY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareRecyclerCatogries() {
        binding.recyclerCatogries.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = catogriesAdapter
        }
    }

    private fun observerCatogriesLiveData() {
        homemvvm.observeCatogriesLiveData().observe(
            viewLifecycleOwner,
            Observer { catogries ->
                catogriesAdapter.setCatogryList(catogries)


            }
        )
    }

    private fun OnPopularClickListener() {
        popularAdapterItems.onItemClick = { meals ->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meals.idMeal)
            intent.putExtra(MEAL_NAME, meals.strMeal)
            intent.putExtra(MEAL_THUMB, meals.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preaparePopularItemRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapterItems

        }
    }

    private fun ObservePopularItemsLiveData() {
        homemvvm.observePopularItemsLiveData().observe(
            viewLifecycleOwner
        ) { meallist ->
            popularAdapterItems.setMeals(mealList = meallist as ArrayList<CatogryMeat>)
        }
    }

    private fun OnRandomMealClickListener() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homemvvm.obServeRandomMealLiveData().observe(viewLifecycleOwner, { meal ->
            Glide.with(this@HomeFragment).load(meal!!.strMealThumb).into(binding.imgRandomMeal)
            this.randomMeal = meal
        })
    }

}