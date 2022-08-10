package com.takeaway.borzikov.tests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.takeaway.borzikov.RestaurantListActivity
import com.takeaway.borzikov.screens.MainScreen
import io.github.kakaocup.kakao.screen.Screen.Companion.idle
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RestaurantListScreenTest {

    @get:Rule
    val activityRule = activityScenarioRule<RestaurantListActivity>()

    @Test
    fun WHEN_applySorting_THEN_sortingNameSetForButton() {
        selectSorting("Rating")
        MainScreen.assertSortingButtonText("Rating")
    }

    @Test
    fun WHEN_applySorting_THEN_restaurantsCountReduced() {
        MainScreen {
            assertRestaurantCount(19)

            inputFilter.typeText("sushi")

            idle()

            assertRestaurantCount(4)
        }
    }

    @Test
    fun WHEN_applySorting_THEN_showItemsWithFilterText() {
        MainScreen {
            inputFilter.typeText("sushi")
            idle()

            (0..3).forEach {
                assertRestaurantNameContains(it, "Sushi")
            }
        }
    }

    @Test
    fun WHEN_chosedAnyTypeOfSort_THEN_restaurantAlwaysSortByStatusCorrectly() {
        selectSorting("Delivery Cost")

        (0..7).forEach {
            MainScreen.assertRestaurantStatus(it, "Open")
        }
        (8..14).forEach {
            MainScreen.assertRestaurantStatus(it, "Order Ahead")
        }
        (15..18).forEach {
            MainScreen.assertRestaurantStatus(it, "Closed")
        }
    }

    @Test
    fun WHEN_applySorting_THEN_itemShortDescriptionStartWithSortName() {
        selectSorting("Best")
        selectSorting("Rating")

        MainScreen.assertRestaurantDescriptionsStartWith("Rating")
    }

    private fun selectSorting(text: String) {
        MainScreen {
            sortTypeButton.click()
            onView(withText(text)).perform(click())
        }
    }
}