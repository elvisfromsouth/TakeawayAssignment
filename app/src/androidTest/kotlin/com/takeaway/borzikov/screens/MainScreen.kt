package com.takeaway.borzikov.screens

import android.view.View
import com.kaspersky.kaspresso.screens.KScreen
import com.takeaway.borzikov.R
import com.takeaway.borzikov.RestaurantListActivity
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher

object MainScreen : KScreen<MainScreen>() {

    override val layoutId: Int = R.layout.activity_restaurant_list
    override val viewClass: Class<*> = RestaurantListActivity::class.java

    val sortTypeButton = KButton { withId(R.id.btn_sorting_type) }
    val inputFilter = KEditText { withId(R.id.et_filter) }
    val restaurants = KRecyclerView({
        withId(R.id.rv_restaurants)
    }, itemTypeBuilder = {
        itemType(MainScreen::Item)
    })

    class Item(parent: Matcher<View>) : KRecyclerItem<Item>(parent) {
        val name: KTextView = KTextView(parent) { withId(R.id.tv_name) }
        val status: KTextView = KTextView(parent) { withId(R.id.tv_status) }
        val sortingDescription: KTextView =
            KTextView(parent) { withId(R.id.tv_sorting_description) }
    }

    fun assertRestaurantCount(targetSize: Int) {
        restaurants {
            assert(getSize() == targetSize)
        }
    }

    fun assertSortingButtonText(text: String) {
        sortTypeButton.hasText(text)
    }

    fun assertRestaurantDescriptionsStartWith(text: String) {
        restaurants {
            children<Item> {
                sortingDescription.startsWithText(text)
            }
        }
    }

    fun assertRestaurantStatus(position: Int, text: String) {
        restaurants {
            childAt<Item>(position) {
                status.hasText(text)
            }
        }
    }

    fun assertRestaurantNameContains(position: Int, text: String) {
        restaurants {
            childAt<Item>(position) {
                name.containsText(text)
            }
        }
    }
}