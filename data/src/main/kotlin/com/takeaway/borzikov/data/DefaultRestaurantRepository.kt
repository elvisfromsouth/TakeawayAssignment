package com.takeaway.borzikov.data

import com.takeaway.borzikov.data.mappers.RestaurantModelMapper
import com.takeaway.borzikov.domain.RestaurantRepository
import com.takeaway.borzikov.domain.models.RestaurantModel

class DefaultRestaurantRepository(
    private val dataSource: RestaurantsListDataSource,
    private val restaurantModelMapper: RestaurantModelMapper
) : RestaurantRepository {

    override suspend fun getRestaurants(): List<RestaurantModel> {
        return dataSource.getRestaurantsList().restaurants
            .map(restaurantModelMapper)
    }

}