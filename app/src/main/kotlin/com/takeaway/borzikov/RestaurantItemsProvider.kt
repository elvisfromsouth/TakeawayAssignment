package com.takeaway.borzikov

import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import com.takeaway.borzikov.adapter.RestaurantListItem
import com.takeaway.borzikov.delegates.SortingTypePreference
import com.takeaway.borzikov.delegates.StringSavedState
import com.takeaway.borzikov.domain.models.RestaurantModel
import com.takeaway.borzikov.mappers.RestaurantItemMapper
import com.takeaway.borzikov.models.RestaurantSort
import javax.inject.Inject

class RestaurantItemsProvider @Inject constructor(
    private val restaurantItemMapper: RestaurantItemMapper,
    sharedPreferences: SharedPreferences,
    savedStateHandle: SavedStateHandle
) {

    private var sortedRestaurantItems = emptyList<RestaurantListItem>()
    private var restaurantModels = listOf<RestaurantModel>()

    private var currentFilter: String by StringSavedState(savedStateHandle, FILTER_SAVED_STATE_KEY)

    var currentSorting: RestaurantSort by SortingTypePreference(
        sharedPreferences,
        SORTING_FIELD_NAME,
        RestaurantSort.BEST
    )
        private set


    fun setup(restaurantModels: List<RestaurantModel>) {
        this.restaurantModels = restaurantModels
        sortedRestaurantItems = restaurantModels.applyRestaurantSort(currentSorting)
            .map { restaurantItemMapper(it, currentSorting) }
    }

    fun getRestaurantItems(
        sorting: RestaurantSort = currentSorting,
        filter: String = currentFilter
    ): List<RestaurantListItem> {
        if (currentSorting != sorting) {
            currentSorting = sorting
            sortedRestaurantItems = restaurantModels.applyRestaurantSort(sorting)
                .map { restaurantItemMapper(it, sorting) }
        }

        currentFilter = filter
        return if (filter.isEmpty()) {
            sortedRestaurantItems
        } else {
            sortedRestaurantItems.filter { it.name.contains(filter, ignoreCase = true) }
        }
    }

    private fun List<RestaurantModel>.applyRestaurantSort(
        sorting: RestaurantSort
    ): List<RestaurantModel> {
        return sortedWith(
            restaurantStatusComparator.then(sorting.comparator)
        )
    }

    companion object {
        private const val FILTER_SAVED_STATE_KEY = "filter"
        private const val SORTING_FIELD_NAME = "sorting"
    }
}