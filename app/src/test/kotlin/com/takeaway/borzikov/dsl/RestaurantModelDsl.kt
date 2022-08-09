package com.takeaway.borzikov.dsl

import com.takeaway.borzikov.common.testing.randomString
import com.takeaway.borzikov.domain.models.RestaurantModel
import com.takeaway.borzikov.domain.models.RestaurantSortingDetailsModel
import com.takeaway.borzikov.domain.models.RestaurantStatus


fun createRestaurantModel(init: RestaurantModelDsl.() -> Unit): RestaurantModel {
    val dsl = RestaurantModelDsl()
    dsl.init()
    return dsl.toOriginal()
}

class RestaurantModelDsl {
    var name = randomString()
    var status = RestaurantStatus.values().random()
    private var sortingValues: RestaurantSortingDetailsModel =
        RestaurantSortingDetailsModelDsl().toOriginal()

    fun sortingValues(init: RestaurantSortingDetailsModelDsl.() -> Unit) {
        val dsl = RestaurantSortingDetailsModelDsl()
        dsl.init()
        sortingValues = dsl.toOriginal()

    }

    fun toOriginal(): RestaurantModel {
        return RestaurantModel(
            name = name,
            status = status,
            sortingValues = sortingValues,
        )
    }
}


