package com.takeaway.borzikov.dsl

import com.takeaway.borzikov.common.testing.randomBigDecimal
import com.takeaway.borzikov.common.testing.randomDouble
import com.takeaway.borzikov.common.testing.randomInt
import com.takeaway.borzikov.domain.models.RestaurantSortingDetailsModel
import java.math.BigDecimal

class RestaurantSortingDetailsModelDsl {

    var bestMatch: Double = randomDouble()
    var newest: Double = randomDouble()
    var ratingAverage: Double = randomDouble()
    var distance: Int = randomInt()
    var popularity: Double = randomDouble()
    var averageProductPrice: BigDecimal = randomBigDecimal()
    var deliveryCosts: BigDecimal = randomBigDecimal()
    var minCost: BigDecimal = randomBigDecimal()

    fun toOriginal(): RestaurantSortingDetailsModel {
        return RestaurantSortingDetailsModel(
            bestMatch = bestMatch,
            newest = newest,
            ratingAverage = ratingAverage,
            distance = distance,
            popularity = popularity,
            averageProductPrice = averageProductPrice,
            deliveryCosts = deliveryCosts,
            minCost = minCost,
        )
    }
}