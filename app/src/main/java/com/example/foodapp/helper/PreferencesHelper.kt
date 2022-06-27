package com.example.foodapp.helper

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {
    private val PREF_NAME="SharedPreferences"
    private var sharedPref:SharedPreferences
    val editor : SharedPreferences.Editor
    init {
        sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)
        editor = sharedPref.edit()
    }
    fun put(key:String,values:String){
        editor.putString(key,values).apply()
    }
    fun getString(key: String):String?{
    return sharedPref.getString(key,null)
    }
    fun put(key: String,values: Boolean){
        editor.putBoolean(key,values).apply()
    }
    fun getBoolean(key: String):Boolean{
        return sharedPref.getBoolean(key,false)
    }
fun clearAll(){
    editor.clear().apply()
}

}