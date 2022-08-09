package com.takeaway.borzikov.data

import android.content.res.AssetManager
import com.google.gson.Gson
import com.takeaway.borzikov.data.dto.RestaurantResponse

interface RestaurantsListDataSource {
    fun getRestaurantsList(): RestaurantResponse
}

class LocalFileRestaurantsListDataSource(
    private val assetManager: AssetManager,
    private val gson: Gson,
    private val fileName: String,
) : RestaurantsListDataSource {

    override fun getRestaurantsList(): RestaurantResponse {
        val json = assetManager.openJson(fileName)
        return gson.fromJson(json, RestaurantResponse::class.java)
    }

    private fun AssetManager.openJson(fileName: String): String {
        return open(fileName)
            .bufferedReader()
            .use {
                it.readText()
            }
    }
}