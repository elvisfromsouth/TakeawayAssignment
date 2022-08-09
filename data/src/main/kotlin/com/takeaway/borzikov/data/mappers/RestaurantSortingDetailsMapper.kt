package com.takeaway.borzikov.data.mappers

import com.takeaway.borzikov.data.dto.RestaurantSortingDetailsDto
import com.takeaway.borzikov.domain.models.RestaurantSortingDetailsModel
import javax.inject.Inject

class RestaurantSortingDetailsMapper @Inject constructor(
) : (RestaurantSortingDetailsDto) -> RestaurantSortingDetailsModel {

    override fun invoke(
        dto: RestaurantSortingDetailsDto
    ): RestaurantSortingDetailsModel {
        return RestaurantSortingDetailsModel(
            bestMatch = dto.bestMatch,
            newest = dto.newest,
            ratingAverage = dto.ratingAverage,
            distance = dto.distance,
            popularity = dto.popularity,
            averageProductPrice = dto.averageProductPrice,
            deliveryCosts = dto.deliveryCosts,
            minCost = dto.minCost,
        )
    }
}