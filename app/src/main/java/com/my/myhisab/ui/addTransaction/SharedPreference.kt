package com.my.myhisab.ui.addTransaction

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson

class SharedPreference {

    fun saveFavorites(context: Context, favorites: List<OfflineTransactionDto?>?) {
        val editor: SharedPreferences.Editor
        val settings: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = settings.edit()
        val gson = Gson()
        val jsonFavorites = gson.toJson(favorites)
        editor.putString(FAVORITES, jsonFavorites)
        editor.commit()
//        editor.apply()
    }

    fun addFavorite(context: Context, product: OfflineTransactionDto) {
        var favorites = getFavorites(context)
//        if (favorites.isNotEmpty()) favorites = ArrayList()
        favorites.add(product)
        saveFavorites(context, favorites)
    }


    fun removeFavorite(context: Context, product: OfflineTransactionDto) {
        val favorites = getFavorites(context)
        for (i in 0 until favorites.size ) {
            if (favorites[i].id == product.id) {
                val cc =OfflineTransactionDto(favorites[i].id , favorites[i].offileUniqueNumber,favorites[i].offlineUid,
                    favorites[i].offlineDate,favorites[i].offlineAmount,favorites[i].offlineRemark)
                favorites.remove(cc)
                favorites.remove(product)
//                favorites.removeAt(i)
                Log.d("TAG", favorites.toString())
            }
        }
//        val editor: SharedPreferences.Editor
//        val settings: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//        editor = settings.edit()
//        val gson = Gson()
//        val jsonFavorites = gson.toJson(favorites)
//        editor.putString(FAVORITES, jsonFavorites)
//        editor.apply();

        saveFavorites(context, favorites)


    }

    fun getFavorites(context: Context): ArrayList<OfflineTransactionDto> {
        var favorites: ArrayList<OfflineTransactionDto>
        val settings: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (settings.contains(FAVORITES)) {
            val jsonFavorites = settings.getString(FAVORITES, null)
            val gson = Gson()
            val favoriteItems = gson.fromJson(jsonFavorites, Array<OfflineTransactionDto>::class.java)
            favorites = arrayListOf(*favoriteItems)
            favorites = ArrayList(favorites)
        } else return arrayListOf()
        return favorites
    }

    companion object {
        const val PREFS_NAME = "PRODUCT_APP"
        const val FAVORITES = "Product_Favorite"
    }

}