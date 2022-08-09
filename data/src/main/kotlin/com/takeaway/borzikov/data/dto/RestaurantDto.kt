package com.takeaway.borzikov.data.dto

import androidx.annotation.Keep

@Keep
class RestaurantDto(
    val name: String,
    val status: String,
    val sortingValues: RestaurantSortingDetailsDto,
)