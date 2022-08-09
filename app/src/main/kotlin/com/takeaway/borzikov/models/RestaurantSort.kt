package com.takeaway.borzikov.models

import androidx.annotation.StringRes
import com.takeaway.borzikov.R
import com.takeaway.borzikov.domain.models.RestaurantModel

enum class RestaurantSort(
    @StringRes val title: Int,
    val comparator: Comparator<RestaurantModel>,
    val value: (RestaurantModel) -> Any,
) {
    BEST(
        title = R.string.sorting_best,
        comparator = compareByDescending<RestaurantModel> { it.sortingValues.bestMatch },
        value = { it.sortingValues.bestMatch }
    ),
    NEW(
        title = R.string.sorting_new,
        comparator = compareByDescending<RestaurantModel> { it.sortingValues.newest },
        value = { it.sortingValues.newest }
    ),
    RATING(
        title = R.string.sorting_rating,
        comparator = compareByDescending<RestaurantModel> { it.sortingValues.ratingAverage },
        value = { it.sortingValues.ratingAverage }
    ),
    DISTANCE(
        title = R.string.sorting_distance,
        comparator = compareBy<RestaurantModel> { it.sortingValues.distance },
        value = { it.sortingValues.distance }
    ),
    POPULARITY(
        title = R.string.sorting_popularity,
        comparator = compareByDescending<RestaurantModel> { it.sortingValues.popularity },
        value = { it.sortingValues.popularity }
    ),
    PRODUCT_PRICE(
        title = R.string.sorting_average_product_price,
        comparator = compareBy<RestaurantModel> { it.sortingValues.averageProductPrice },
        value = { it.sortingValues.averageProductPrice }
    ),
    DELIVERY_COST(
        title = R.string.sorting_delivery_cost,
        comparator = compareBy<RestaurantModel> { it.sortingValues.deliveryCosts },
        value = { it.sortingValues.deliveryCosts }
    ),
    MIN_COST(
        title = R.string.sorting_min_cost,
        comparator = compareBy<RestaurantModel> { it.sortingValues.minCost },
        value = { it.sortingValues.minCost }
    ),
}