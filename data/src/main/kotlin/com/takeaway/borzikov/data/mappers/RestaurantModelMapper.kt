package com.takeaway.borzikov.data.mappers

import com.takeaway.borzikov.data.dto.RestaurantDto
import com.takeaway.borzikov.domain.models.RestaurantModel
import com.takeaway.borzikov.domain.models.RestaurantStatus
import javax.inject.Inject

class RestaurantModelMapper @Inject constructor(
    private val restaurantSortingDetailsMapper: RestaurantSortingDetailsMapper
) : (RestaurantDto) -> RestaurantModel {

    override fun invoke(dto: RestaurantDto): RestaurantModel {
        return RestaurantModel(
            name = dto.name,
            sortingValues = restaurantSortingDetailsMapper.invoke(dto.sortingValues),
            status = dto.status.toRestaurantStatus()
                ?: throw IllegalStateException("Value of status incorrect: ${dto.status}"),
        )
    }

    private fun String.toRestaurantStatus(): RestaurantStatus? {
        return when (this) {
            "open" -> RestaurantStatus.OPEN
            "order ahead" -> RestaurantStatus.ORDER_AHEAD
            "closed" -> RestaurantStatus.CLOSE
            else -> null
        }
    }
}