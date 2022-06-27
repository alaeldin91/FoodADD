package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.MealItemBinding
import com.example.foodapp.pojo.MealList
import com.example.foodapp.pojo.MealsByCatogry
import java.util.zip.Inflater

class CatogryMealsAdapter :RecyclerView.Adapter<CatogryMealsAdapter.CatogryViewHolder>(){
    private var  mealList = ArrayList<MealsByCatogry>()
    fun setMealsList(mealList:List<MealsByCatogry>){
        this.mealList = mealList as ArrayList<MealsByCatogry>
        notifyDataSetChanged()

    }
    inner class CatogryViewHolder(val binding :MealItemBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatogryViewHolder {
        return CatogryViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context)))

    }

    override fun onBindViewHolder(holder: CatogryViewHolder, position: Int) {
Glide.with(holder.itemView).load(mealList[position].strMealThumb).into(holder.binding.imageMeals)
        holder.binding.tvName.text =mealList[position].strMeal
    }

    override fun getItemCount(): Int {
return mealList.size
    }

}