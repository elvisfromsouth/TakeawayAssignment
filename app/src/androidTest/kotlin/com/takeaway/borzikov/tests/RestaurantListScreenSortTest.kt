package com.takeaway.borzikov.tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.takeaway.borzikov.*
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
        testSortingOrder("Best", bestRestaurantOrder)
    }

    @Test
    fun WHEN_applyNewSort_THEN_showRestaurantInCorrectOrder() {
        testSortingOrder("New", newRestaurantOrder)
    }

    @Test
    fun WHEN_applyRatingSort_THEN_showRestaurantInCorrectOrder() {
        testSortingOrder("Rating", ratingRestaurantOrder)
    }

    @Test
    fun WHEN_applyDistanceSort_THEN_showRestaurantInCorrectOrder() {
        testSortingOrder("Distance", distanceRestaurantOrder)
    }

    @Test
    fun WHEN_applyPopularitySort_THEN_showRestaurantInCorrectOrder() {
        testSortingOrder("Popularity", popularityRestaurantOrder)
    }

    @Test
    fun WHEN_applyAverageProductPriceSort_THEN_showRestaurantInCorrectOrder() {
        testSortingOrder("Average Product Price", averageProductPriceRestaurantOrder)
    }

    @Test
    fun WHEN_applyDeliveryCostSort_THEN_showRestaurantInCorrectOrder() {
        testSortingOrder("Delivery Cost", deliveryCostRestaurantOrder)
    }

    @Test
    fun WHEN_applyMinCostSort_THEN_showRestaurantInCorrectOrder() {
        testSortingOrder("Min Cost", minCostRestaurantOrder)
    }


    private fun testSortingOrder(
        sortingName: String,
        order: List<String>
    ) {
        MainScreen {
            sortTypeButton.click()
            onView(withText(sortingName)).perform(click())

            restaurants {
                order.forEachIndexed { index, text ->
                    childAt<MainScreen.Item>(index) {
                        name.startsWithText(text)
                    }
                }
            }
        }
    }

}