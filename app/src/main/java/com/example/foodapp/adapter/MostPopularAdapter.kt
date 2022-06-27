package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.PopularItemBinding
import com.example.foodapp.pojo.CatogryMeat

class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    lateinit var onItemClick :((CatogryMeat)->Unit)
private var mealsList = ArrayList<CatogryMeat>()
    fun setMeals(mealList:ArrayList<CatogryMeat>){
        this.mealsList =mealList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(
            PopularItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.imgPopularMealItem)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }
    }

    override fun getItemCount(): Int {
return mealsList.size
    }
    class PopularMealViewHolder(var binding: PopularItemBinding):RecyclerView.ViewHolder(binding.root){

    }

}