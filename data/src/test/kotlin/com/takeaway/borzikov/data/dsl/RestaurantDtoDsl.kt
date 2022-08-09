package com.takeaway.borzikov.data.dsl

import com.takeaway.borzikov.common.testing.randomString
import com.takeaway.borzikov.data.dto.RestaurantDto
import com.takeaway.borzikov.data.dto.RestaurantSortingDetailsDto
import java.util.*

fun createRestaurantDtoDsl(init: RestaurantDtoDsl.() -> Unit): RestaurantDto {
    val dsl = RestaurantDtoDsl()
    dsl.init()
    return dsl.toOriginal()
}

class RestaurantDtoDsl {
    var name: String = randomString()
    var status: String = randomString()
    private var sortingValues: RestaurantSortingDetailsDto = createRestaurantSortingDetailsDto {}

    fun sortingValues(
        init: RestaurantSortingDetailsDtoDsl.() -> Unit
    ) {
        sortingValues = createRestaurantSortingDetailsDto(init)
    }

    fun toOriginal(): RestaurantDto {
        return RestaurantDto(
            name = name,
            status = status,
            sortingValues = sortingValues
        )
    }
}