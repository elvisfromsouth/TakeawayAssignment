package com.takeaway.borzikov.tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.takeaway.borzikov.RestaurantListActivity
import com.takeaway.borzikov.averageProductPriceRestaurantOrder
import com.takeaway.borzikov.bestRestaurantOrder
import com.takeaway.borzikov.deliveryCostRestaurantOrder
import com.takeaway.borzikov.distanceRestaurantOrder
import com.takeaway.borzikov.minCostRestaurantOrder
import com.takeaway.borzikov.newRestaurantOrder
import com.takeaway.borzikov.popularityRestaurantOrder
import com.takeaway.borzikov.ratingRestaurantOrder
import com.takeaway.borzikov.screens.MainScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RestaurantListScreenSortTest {

    @get:Rule
    val activityRule = activityScenarioRule<RestaurantListActivity>()

    @Test
    fun WHEN_applyBestSort_THEN_showRestaurantInCorrectOrder() {
        assertSortingOrder("Best", bestRestaurantOrder)
    }

    @Test
    fun WHEN_applyNewSort_THEN_showRestaurantInCorrectOrder() {
        assertSortingOrder("New", newRestaurantOrder)
    }

    @Test
    fun WHEN_applyRatingSort_THEN_showRestaurantInCorrectOrder() {
        assertSortingOrder("Rating", ratingRestaurantOrder)
    }

    @Test
    fun WHEN_applyDistanceSort_THEN_showRestaurantInCorrectOrder() {
        assertSortingOrder("Distance", distanceRestaurantOrder)
    }

    @Test
    fun WHEN_applyPopularitySort_THEN_showRestaurantInCorrectOrder() {
        assertSortingOrder("Popularity", popularityRestaurantOrder)
    }

    @Test
    fun WHEN_applyAverageProductPriceSort_THEN_showRestaurantInCorrectOrder() {
        assertSortingOrder("Average Product Price", averageProductPriceRestaurantOrder)
    }

    @Test
    fun WHEN_applyDeliveryCostSort_THEN_showRestaurantInCorrectOrder() {
        assertSortingOrder("Delivery Cost", deliveryCostRestaurantOrder)
    }

    @Test
    fun WHEN_applyMinCostSort_THEN_showRestaurantInCorrectOrder() {
        assertSortingOrder("Min Cost", minCostRestaurantOrder)
    }

    private fun assertSortingOrder(
        sortingName: String,
        order: List<String>
    ) {
        MainScreen {
            sortTypeButton.click()
            onView(withText(sortingName)).perform(click())

            order.forEachIndexed { index, text ->
                assertRestaurantNameContains(index, text)
            }
        }
    }

}