package com.takeaway.borzikov.domain

import com.takeaway.borzikov.domain.models.RestaurantModel
import javax.inject.Inject

interface RestaurantInteractor {
    suspend fun getRestaurants(): List<RestaurantModel>
}


class RestaurantInteractorImpl @Inject constructor(
    private val repository: RestaurantRepository,
) : RestaurantInteractor {
    override suspend fun getRestaurants(): List<RestaurantModel> {
        return repository.getRestaurants()
    }
}