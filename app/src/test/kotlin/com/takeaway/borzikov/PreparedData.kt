package com.takeaway.borzikov

import com.takeaway.borzikov.adapter.RestaurantListItem
import com.takeaway.borzikov.domain.models.RestaurantModel
import com.takeaway.borzikov.domain.models.RestaurantStatus
import com.takeaway.borzikov.dsl.RestaurantSortingDetailsModelDsl
import com.takeaway.borzikov.dsl.createRestaurantListItem
import com.takeaway.borzikov.dsl.createRestaurantModel


/**
 * Reording list according to incoming values
 */
fun <T> List<RestaurantTestItem>.reorderAccordingTo(
    vararg targets: T,
    predicate: (RestaurantModel) -> T,
): List<RestaurantListItem> {
    return targets.map { target ->
        first { predicate(it.model) == target }.listItem
    }
}

fun createRestaurantsOnStatusWithName(
    vararg values: Pair<RestaurantStatus, String>,
): List<RestaurantTestItem> {
    return List(values.size) {
        RestaurantTestItem(
            createRestaurantModel {
                status = values[it].first
                name = values[it].second
            },
            createRestaurantListItem {
                name = values[it].second
            }
        )
    }
}

fun createRestaurantsOnSortingValues(
    count: Int,
    init: RestaurantSortingDetailsModelDsl.(Int) -> Unit
): List<RestaurantTestItem> {
    val restaurantStatus = RestaurantStatus.values().random()
    return List(count) {
        RestaurantTestItem(
            createRestaurantModel {
                status = restaurantStatus
                sortingValues {
                    init(this, it)
                }
            },
            createRestaurantListItem {
            }
        )
    }
}