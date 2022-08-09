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
        checkButtonTitle("Rating")
        MainScreen.sortTypeButton.hasText("Rating")
    }

    @Test
    fun WHEN_applySorting_THEN_restaurantsCountReduced() {
        MainScreen {
            restaurants {
                assert(getSize() == 19)
            }

            inputFilter.typeText("sushi")

            idle()
            restaurants {
                assert(getSize() == 4)
            }
        }
    }

    @Test
    fun WHEN_applySorting_THEN_showItemsWithFilterText() {
        MainScreen {
            inputFilter.typeText("sushi")
            idle()

            restaurants {
                childAt<MainScreen.Item>(0) {
                    name.containsText("Sushi")
                }
                childAt<MainScreen.Item>(1) {
                    name.containsText("Sushi")
                }
                childAt<MainScreen.Item>(2) {
                    name.containsText("Sushi")
                }
                childAt<MainScreen.Item>(3) {
                    name.containsText("Sushi")
                }
            }
        }
    }

    @Test
    fun WHEN_chosedAnyTypeOfSort_THEN_restaurantAlwaysSortByStatusCorrectly() {
        checkButtonTitle("Delivery Cost")

        MainScreen {
            restaurants {
                (0..7).forEach {
                    childAt<MainScreen.Item>(it) {
                        status.hasText("Open")
                    }
                }
                (8..14).forEach {
                    childAt<MainScreen.Item>(it) {
                        status.containsText("Order Ahead")
                    }
                }
                (15..18).forEach {
                    childAt<MainScreen.Item>(it) {
                        status.containsText("Closed")
                    }
                }
            }
        }
    }

    @Test
    fun WHEN_applySorting_THEN_itemShortDescriptionStartWithSortName() {
        checkButtonTitle("Best")
        checkButtonTitle("Rating")

        MainScreen {
            restaurants {
                children<MainScreen.Item> {
                    sortingDescription.startsWithText("Rating")
                }
            }
        }
    }

    private fun checkButtonTitle(text: String) {
        MainScreen {
            sortTypeButton.click()
            onView(withText(text)).perform(click())
        }
    }
}