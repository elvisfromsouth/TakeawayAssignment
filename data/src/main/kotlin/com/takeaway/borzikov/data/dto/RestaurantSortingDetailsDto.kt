package com.takeaway.borzikov.data.dto

import androidx.annotation.Keep
import java.math.BigDecimal

@Keep
class RestaurantSortingDetailsDto(
    val bestMatch: Double,
    val newest: Double,
    val ratingAverage: Double,
    val distance: Int,
    val popularity: Double,
    val averageProductPrice: BigDecimal,
    val deliveryCosts: BigDecimal,
    val minCost: BigDecimal,
)