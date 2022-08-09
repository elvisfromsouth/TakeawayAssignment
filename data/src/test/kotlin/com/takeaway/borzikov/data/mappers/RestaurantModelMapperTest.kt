package com.takeaway.borzikov.data.mappers

import com.takeaway.borzikov.data.dsl.createRestaurantDtoDsl
import com.takeaway.borzikov.domain.models.RestaurantStatus
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class RestaurantModelMapperTest {

    private val restaurantSortingDetailsMapper : RestaurantSortingDetailsMapper = mockk()

    private val mapper: RestaurantModelMapper = RestaurantModelMapper(restaurantSortingDetailsMapper)

    @Before
    fun init() {
        every { restaurantSortingDetailsMapper.invoke(any()) }.returns(mockk())
    }

    @Test
    fun `WHEN name mapped THEN name has no changes`() {
        val dto = createRestaurantDtoDsl {
            name = "some name"
            status = "open"
        }

        val targetResult = "some name"

        val result = mapper.invoke(dto)
        assert(result.name == targetResult)
    }

    @Test
    fun `WHEN status is open THEN map is success`() {
        val dto = createRestaurantDtoDsl {
            status = "open"
        }

        val targetResult = RestaurantStatus.OPEN

        val result = mapper.invoke(dto)
        assert(result.status == targetResult)
    }

    @Test
    fun `WHEN status is order ahead THEN map is success`() {
        val dto = createRestaurantDtoDsl {
            status = "order ahead"
        }

        val targetResult = RestaurantStatus.ORDER_AHEAD

        val result = mapper.invoke(dto)
        assert(result.status == targetResult)
    }

    @Test
    fun `WHEN status is closed THEN map is success`() {
        val dto = createRestaurantDtoDsl {
            status = "closed"
        }

        val targetResult = RestaurantStatus.CLOSE

        val result = mapper.invoke(dto)
        assert(result.status == targetResult)
    }

    @Test
    fun `WHEN status is unknown THEN throw exception`() {
        val dto = createRestaurantDtoDsl {
            status = "hello"
        }

        val exception = assertThrows(IllegalStateException::class.java) { mapper.invoke(dto) }
        assert(exception.message == "Value of status incorrect: hello")
    }

}