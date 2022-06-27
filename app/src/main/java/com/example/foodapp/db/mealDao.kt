package com.example.foodapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodapp.pojo.MealsByCatogry

@Dao
interface mealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(mealsByCatogry: MealsByCatogry){

    }
    @Delete
    suspend fun delete(mealsByCatogry: MealsByCatogry){
    }
    @Query("SELECT * FROM mealInformation")
    fun getAllMeals():LiveData<List<MealsByCatogry>>

}