package com.takeaway.borzikov.domain

import com.takeaway.borzikov.domain.models.RestaurantModel

interface RestaurantRepository {
    suspend fun getRestaurants(): List<RestaurantModel>
}

