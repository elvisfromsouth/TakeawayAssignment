package com.takeaway.borzikov.data.mappers

import com.takeaway.borzikov.data.dsl.createRestaurantSortingDetailsDto
import com.takeaway.borzikov.domain.models.RestaurantSortingDetailsModel
import org.junit.Test
import java.math.BigDecimal

class RestaurantSortingDetailsMapperTest {

    private val mapper = RestaurantSortingDetailsMapper()

    @Test
    fun `WHEN receives RestaurantSortingDetails THEN successfully map to RestaurantSortingDetailsModel`() {
        val dto = createRestaurantSortingDetailsDto {
            bestMatch = 1.0
            newest = 2.0
            ratingAverage = 3.0
            distance = 4
            popularity = 5.0
            averageProductPrice = BigDecimal(6)
            deliveryCosts = BigDecimal(7)
            minCost = BigDecimal(8)
        }

        val targetResult = RestaurantSortingDetailsModel(
            bestMatch = 1.0,
            newest = 2.0,
            ratingAverage = 3.0,
            distance = 4,
            popularity = 5.0,
            averageProductPrice = BigDecimal(6),
            deliveryCosts = BigDecimal(7),
            minCost = BigDecimal(8),
        )

        val result = mapper.invoke(dto)
        assert(result == targetResult)
    }
}