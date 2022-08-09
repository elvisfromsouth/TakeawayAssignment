package com.takeaway.borzikov.data.dsl

import com.takeaway.borzikov.common.testing.randomBigDecimal
import com.takeaway.borzikov.common.testing.randomDouble
import com.takeaway.borzikov.common.testing.randomInt
import com.takeaway.borzikov.data.dto.RestaurantSortingDetailsDto
import java.math.BigDecimal

fun createRestaurantSortingDetailsDto(
    init: RestaurantSortingDetailsDtoDsl.() -> Unit
): RestaurantSortingDetailsDto {
    val dsl = RestaurantSortingDetailsDtoDsl()
    dsl.init()
    return dsl.toOriginal()
}

class RestaurantSortingDetailsDtoDsl {
    var bestMatch: Double = randomDouble()
    var newest: Double = randomDouble()
    var ratingAverage: Double = randomDouble()
    var distance: Int = randomInt()
    var popularity: Double = randomDouble()
    var averageProductPrice: BigDecimal = randomBigDecimal()
    var deliveryCosts: BigDecimal = randomBigDecimal()
    var minCost: BigDecimal = randomBigDecimal()

    fun toOriginal(): RestaurantSortingDetailsDto {
        return RestaurantSortingDetailsDto(
            bestMatch,
            newest,
            ratingAverage,
            distance,
            popularity,
            averageProductPrice,
            deliveryCosts,
            minCost
        )
    }
}

