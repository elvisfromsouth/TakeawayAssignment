package com.takeaway.borzikov.delegates

import android.content.SharedPreferences
import com.takeaway.borzikov.models.RestaurantSort
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SortingTypePreference(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val defaultValue: RestaurantSort
) : ReadWriteProperty<Any, RestaurantSort> {

    private var lastSorting: RestaurantSort? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): RestaurantSort {
        if (lastSorting == null) {
            val sortingName = sharedPreferences.getString(key, null)
            lastSorting = RestaurantSort.values().firstOrNull { it.name == sortingName } ?: defaultValue
        }
        return lastSorting!!
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: RestaurantSort) {
        lastSorting = value
        sharedPreferences.edit()
            .putString(key, value.name)
            .apply()
    }

}

