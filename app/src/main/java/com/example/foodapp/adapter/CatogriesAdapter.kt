package com.example.foodapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.CatogryItemBinding
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.CatogryList

class CatogriesAdapter : RecyclerView.Adapter<CatogriesAdapter.CatogryViewHolder>() {
       var onItemClick :((Category) ->Unit)? = null
    private var categoriesList = ArrayList<Category>()
    fun setCatogryList(categoriesList: List<Category>) {
        this.categoriesList = categoriesList as ArrayList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatogryViewHolder {
        return CatogryViewHolder(CatogryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CatogryViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.imgCatogry)
        holder.binding.tvCatogry.text = categoriesList[position].strCategory
        holder.itemView.setOnClickListener{
            onItemClick!!.invoke(categoriesList[position])

        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    inner class CatogryViewHolder(val binding: CatogryItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}