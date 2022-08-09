package com.takeaway.borzikov.domain.models

import java.math.BigDecimal

data class RestaurantSortingDetailsModel(
    val bestMatch: Double,
    val newest: Double,
    val ratingAverage: Double,
    val distance: Int,
    val popularity: Double,
    val averageProductPrice: BigDecimal,
    val deliveryCosts: BigDecimal,
    val minCost: BigDecimal,
)