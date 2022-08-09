package com.takeaway.borzikov.mappers

import androidx.annotation.StringRes
import com.takeaway.borzikov.R
import com.takeaway.borzikov.common.Text
import com.takeaway.borzikov.domain.models.RestaurantStatus
import com.takeaway.borzikov.dsl.createRestaurantModel
import com.takeaway.borzikov.models.RestaurantSort
import org.junit.Test
import java.math.BigDecimal

class RestaurantItemMapperTest {

    private val mapper = RestaurantItemMapper()

    @Test
    fun `WHEN name received THEN name mapped without change`() {
        val targetName = "some name"
        val sort = RestaurantSort.values().random()
        val model = createRestaurantModel {
            name = targetName
        }

        val result = mapper.invoke(model, sort)

        assert(targetName == result.name)
    }

    @Test
    fun `WHEN status is open THEN result status mapped successfully`() {
        val sort = RestaurantSort.values().random()
        val model = createRestaurantModel {
            status = RestaurantStatus.OPEN
        }

        val targetStatus = Text.Resource(R.string.restaurant_open)

        val result = mapper.invoke(model, sort)

        assert(targetStatus == result.status)
    }

    @Test
    fun `WHEN status is close THEN result status mapped successfully`() {
        val sort = RestaurantSort.values().random()
        val model = createRestaurantModel {
            status = RestaurantStatus.CLOSE
        }

        val targetStatus = Text.Resource(R.string.restaurant_closed)

        val result = mapper.invoke(model, sort)

        assert(targetStatus == result.status)
    }

    @Test
    fun `WHEN status is order ahead THEN result status mapped successfully`() {
        val sort = RestaurantSort.values().random()
        val model = createRestaurantModel {
            status = RestaurantStatus.ORDER_AHEAD
        }

        val targetStatus = Text.Resource(R.string.restaurant_order_ahead)

        val result = mapper.invoke(model, sort)

        assert(targetStatus == result.status)
    }

    @Test
    fun `WHEN sort is best THEN sorting description contains bestMatch value`() {
        val sort = RestaurantSort.BEST
        val model = createRestaurantModel {
            sortingValues {
                bestMatch = 1.0
            }
        }

        val targetSortDescription = createSortDescription(
            sortTitleRes = R.string.sorting_best,
            sortValue = "1.0",
        )

        val result = mapper.invoke(model, sort)

        assert(targetSortDescription == result.sortingDescription)
    }

    @Test
    fun `WHEN sort is new THEN sorting description contains newest value`() {
        val sort = RestaurantSort.NEW
        val model = createRestaurantModel {
            sortingValues {
                newest = 20.0
            }
        }

        val targetSortDescription = createSortDescription(
            sortTitleRes = R.string.sorting_new,
            sortValue = "20.0",
        )

        val result = mapper.invoke(model, sort)

        assert(targetSortDescription == result.sortingDescription)
    }

    @Test
    fun `WHEN sort is rating THEN sorting description contains ratingAverage value`() {
        val sort = RestaurantSort.RATING
        val model = createRestaurantModel {
            sortingValues {
                ratingAverage = 300.0
            }
        }

        val targetSortDescription = createSortDescription(
            sortTitleRes = R.string.sorting_rating,
            sortValue = "300.0",
        )

        val result = mapper.invoke(model, sort)

        assert(targetSortDescription == result.sortingDescription)
    }

    @Test
    fun `WHEN sort is distance THEN sorting description contains distance value`() {
        val sort = RestaurantSort.DISTANCE
        val model = createRestaurantModel {
            sortingValues {
                distance = 4000
            }
        }

        val targetSortDescription = createSortDescription(
            sortTitleRes = R.string.sorting_distance,
            sortValue = "4000",
        )

        val result = mapper.invoke(model, sort)

        assert(targetSortDescription == result.sortingDescription)
    }

    @Test
    fun `WHEN sort is distance THEN sorting description contains popularity value`() {
        val sort = RestaurantSort.POPULARITY
        val model = createRestaurantModel {
            sortingValues {
                popularity = 50000.0
            }
        }

        val targetSortDescription = createSortDescription(
            sortTitleRes = R.string.sorting_popularity,
            sortValue = "50000.0",
        )

        val result = mapper.invoke(model, sort)

        assert(targetSortDescription == result.sortingDescription)
    }

    @Test
    fun `WHEN sort is average product price THEN sorting description contains averageProductPrice value`() {
        val sort = RestaurantSort.PRODUCT_PRICE
        val model = createRestaurantModel {
            sortingValues {
                averageProductPrice = BigDecimal(600000.0)
            }
        }

        val targetSortDescription = createSortDescription(
            sortTitleRes = R.string.sorting_average_product_price,
            sortValue = "600000",
        )

        val result = mapper.invoke(model, sort)

        assert(targetSortDescription == result.sortingDescription)
    }

    @Test
    fun `WHEN sort is delivery cost THEN sorting description contains deliveryCosts value`() {
        val sort = RestaurantSort.DELIVERY_COST
        val model = createRestaurantModel {
            sortingValues {
                deliveryCosts = BigDecimal(7000000.0)
            }
        }

        val targetSortDescription = createSortDescription(
            sortTitleRes = R.string.sorting_delivery_cost,
            sortValue = "7000000",
        )

        val result = mapper.invoke(model, sort)

        assert(targetSortDescription == result.sortingDescription)
    }

    @Test
    fun `WHEN sort is min cost THEN sorting description contains minCost value`() {
        val sort = RestaurantSort.MIN_COST
        val model = createRestaurantModel {
            sortingValues {
                minCost = BigDecimal(80000000.0)
            }
        }

        val targetSortDescription = createSortDescription(
            sortTitleRes = R.string.sorting_min_cost,
            sortValue = "80000000",
        )

        val result = mapper.invoke(model, sort)

        assert(targetSortDescription == result.sortingDescription)
    }


    private fun createSortDescription(
        @StringRes
        sortTitleRes: Int,
        sortValue: String
    ): Text {
        return Text.ResourceWithArguments(
            R.string.sorting_value_pattern,
            Text.Resource(sortTitleRes),
            sortValue
        )
    }


}

