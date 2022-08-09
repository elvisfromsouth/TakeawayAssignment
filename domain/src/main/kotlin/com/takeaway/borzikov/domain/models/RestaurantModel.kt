package com.takeaway.borzikov.domain.models

data class RestaurantModel(
    val name: String,
    val status: RestaurantStatus,
    val sortingValues: RestaurantSortingDetailsModel,
)