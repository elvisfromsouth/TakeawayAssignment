package com.takeaway.borzikov.tests

import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import com.takeaway.borzikov.RestaurantItemsProvider
import com.takeaway.borzikov.RestaurantTestItem
import com.takeaway.borzikov.adapter.RestaurantListItem
import com.takeaway.borzikov.common.testing.randomString
import com.takeaway.borzikov.createRestaurantsOnSortingValues
import com.takeaway.borzikov.createRestaurantsOnStatusWithName
import com.takeaway.borzikov.domain.models.RestaurantStatus
import com.takeaway.borzikov.mappers.RestaurantItemMapper
import com.takeaway.borzikov.models.RestaurantSort
import com.takeaway.borzikov.reorderAccordingTo
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RestaurantItemsProviderTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var restaurantItemMapper: RestaurantItemMapper

    @MockK(relaxUnitFun = true)
    lateinit var sharedPreferences: SharedPreferences

    @MockK(relaxUnitFun = true)
    lateinit var savedState: SavedStateHandle

    lateinit var provider: RestaurantItemsProvider

    @Before
    fun init() {
        every { savedState.get<String>(any()) } returns ""
        every { sharedPreferences.getString(any(), any()) } returns ""
        every { sharedPreferences.edit() } returns mockk(relaxed = true)
        provider = RestaurantItemsProvider(
            restaurantItemMapper = restaurantItemMapper,
            sharedPreferences = sharedPreferences,
            savedStateHandle = savedState,
        )
    }

    @Test
    fun `WHEN restaurants sort THEN apply base sorting by status`() {
        val restaurants = createRestaurantsOnStatusWithName(
            RestaurantStatus.CLOSE to randomString(),
            RestaurantStatus.OPEN to randomString(),
            RestaurantStatus.ORDER_AHEAD to randomString(),
        )
        val targetItems = restaurants.reorderAccordingTo(
            RestaurantStatus.OPEN,
            RestaurantStatus.ORDER_AHEAD,
            RestaurantStatus.CLOSE
        ) { it.status }

        sortingTest(
            restaurants,
            targetItems,
            RestaurantSort.BEST,
        )
    }

    @Test
    fun `WHEN applies text filter THEN sorts by name`() {
        val restaurants = createRestaurantsOnStatusWithName(
            RestaurantStatus.CLOSE to "a",
            RestaurantStatus.OPEN to "ab",
            RestaurantStatus.ORDER_AHEAD to "123"
        )
        val targetItems = restaurants.reorderAccordingTo("ab", "a") { it.name }

        sortingTest(
            restaurants,
            targetItems,
            RestaurantSort.BEST,
            "a"
        )
    }

    @Test
    fun `WHEN applies bad text filter THEN received empty list`() {
        val restaurants = createRestaurantsOnStatusWithName(
            RestaurantStatus.CLOSE to "a",
            RestaurantStatus.OPEN to "ab",
            RestaurantStatus.ORDER_AHEAD to "123"
        )
        val targetItems = emptyList<RestaurantListItem>()

        sortingTest(
            restaurants,
            targetItems,
            RestaurantSort.BEST,
            "SOMETHING"
        )
    }

    @Test
    fun `WHEN applies best sort THEN sort apply successfully`() {
        val values = listOf(-1.0, 1.0, 0.0)
        val restaurants = createRestaurantsOnSortingValues(values.size) { index ->
            bestMatch = values[index]
        }

        val targetItems = restaurants.reorderAccordingTo(
            1.0,
            0.0,
            -1.0
        ) { it.sortingValues.bestMatch }

        sortingTest(
            restaurants,
            targetItems,
            RestaurantSort.BEST,
        )
    }

    @Test
    fun `WHEN applies newest sort THEN sort apply successfully`() {
        val values = listOf(-1.0, 1.0, 0.0)
        val restaurants = createRestaurantsOnSortingValues(values.size) { index ->
            newest = values[index]
        }

        val targetItems = restaurants.reorderAccordingTo(
            1.0,
            0.0,
            -1.0
        ) { it.sortingValues.newest }

        sortingTest(
            restaurants,
            targetItems,
            RestaurantSort.NEW,
        )
    }

    @Test
    fun `WHEN applies rating sort THEN sort apply successfully`() {
        val values = listOf(-1.0, 1.0, 0.0)
        val restaurants = createRestaurantsOnSortingValues(values.size) { index ->
            ratingAverage = values[index]
        }

        val targetItems = restaurants.reorderAccordingTo(
            1.0,
            0.0,
            -1.0
        ) { it.sortingValues.ratingAverage }

        sortingTest(
            restaurants,
            targetItems,
            RestaurantSort.RATING,
        )
    }

    @Test
    fun `WHEN applies distance sort THEN sort apply successfully`() {
        val values = listOf(-1, 1, 0)
        val restaurants = createRestaurantsOnSortingValues(values.size) { index ->
            distance = values[index]
        }

        val targetItems = restaurants.reorderAccordingTo(
            -1,
            0,
            1
        ) { it.sortingValues.distance }

        sortingTest(
            restaurants,
            targetItems,
            RestaurantSort.DISTANCE,
        )
    }

    @Test
    fun `WHEN applies popularity sort THEN sort apply successfully`() {
        val values = listOf(-1.0, 1.0, 0.0)
        val restaurants = createRestaurantsOnSortingValues(values.size) { index ->
            popularity = values[index]
        }

        val targetItems = restaurants.reorderAccordingTo(
            1.0,
            0.0,
            -1.0
        ) { it.sortingValues.popularity }

        sortingTest(
            restaurants,
            targetItems,
            RestaurantSort.POPULARITY,
        )
    }

    @Test
    fun `WHEN applies product price sort THEN sort apply successfully`() {
        val values = listOf(-1.0, 1.0, 0.0)
        val restaurants = createRestaurantsOnSortingValues(values.size) { index ->
            averageProductPrice = values[index].toBigDecimal()
        }
        val targetItems = restaurants.reorderAccordingTo(
            (-1.0).toBigDecimal(),
            (0.0).toBigDecimal(),
            (1.0).toBigDecimal(),
        ) { it.sortingValues.averageProductPrice }

        sortingTest(
            restaurants,
            targetItems,
            RestaurantSort.PRODUCT_PRICE,
        )
    }

    @Test
    fun `WHEN applies delivery cost sort THEN sort apply successfully`() {
        val values = listOf(-1.0, 1.0, 0.0)
        val restaurants = createRestaurantsOnSortingValues(values.size) { index ->
            deliveryCosts = values[index].toBigDecimal()
        }
        val targetItems = restaurants.reorderAccordingTo(
            (-1.0).toBigDecimal(),
            (0.0).toBigDecimal(),
            (1.0).toBigDecimal()
        ) { it.sortingValues.deliveryCosts }

        sortingTest(
            restaurants,
            targetItems,
            RestaurantSort.DELIVERY_COST,
        )
    }

    @Test
    fun `WHEN applies min cost sort THEN sort apply successfully`() {
        val values = listOf(-1.0, 1.0, 0.0)
        val restaurants = createRestaurantsOnSortingValues(values.size) { index ->
            minCost = values[index].toBigDecimal()
        }
        val targetItems = restaurants.reorderAccordingTo(
            (-1.0).toBigDecimal(),
            (0.0).toBigDecimal(),
            (1.0).toBigDecimal(),
        ) { it.sortingValues.minCost }

        sortingTest(
            restaurants,
            targetItems,
            RestaurantSort.MIN_COST,
        )
    }

    private fun sortingTest(
        restaurants: List<RestaurantTestItem>,
        targetItems: List<RestaurantListItem>,
        sorting: RestaurantSort,
        filter: String = ""
    ) {
        restaurants.forEach {
            every { restaurantItemMapper.invoke(it.model, any()) }.returns(it.listItem)
        }

        provider.setup(restaurants.map { it.model })
        val result = provider.getRestaurantItems(sorting, filter)

        assert(targetItems == result)
    }

}